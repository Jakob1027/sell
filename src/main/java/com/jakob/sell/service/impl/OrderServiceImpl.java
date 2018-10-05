package com.jakob.sell.service.impl;

import com.jakob.sell.converter.OrderMaster2OrderDTOConverter;
import com.jakob.sell.dataobject.OrderDetail;
import com.jakob.sell.dataobject.OrderMaster;
import com.jakob.sell.dataobject.ProductInfo;
import com.jakob.sell.dto.CartDTO;
import com.jakob.sell.dto.OrderDTO;
import com.jakob.sell.enums.OrderStatusEnum;
import com.jakob.sell.enums.PayStatusEnum;
import com.jakob.sell.enums.SellExceptionEnum;
import com.jakob.sell.exception.SellException;
import com.jakob.sell.repository.OrderDetailRepository;
import com.jakob.sell.repository.OrderMasterRepository;
import com.jakob.sell.service.OrderService;
import com.jakob.sell.service.ProductService;
import com.jakob.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jakob
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
//        List<CartDTO> cartDTOList = new ArrayList<>();

        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            //1.查询商品信息
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());

            if (productInfo == null) {
                throw new SellException(SellExceptionEnum.PRODUCT_NOT_EXIST);
            }

            //计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //2.创建订单详情
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);

//            cartDTOList.add(new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()));
        }


        //3.创建订单
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4.扣库存
        List<CartDTO> cartDTOList =
                orderDTO.getOrderDetailList().stream()
                        .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                        .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        //1.判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态错误,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(SellExceptionEnum.ORDER_STATUS_ERROR);
        }

        //2.修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updatedOrderMaster = orderMasterRepository.save(orderMaster);
        if (updatedOrderMaster == null) {
            log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(SellExceptionEnum.ORDER_UPDATE_FAIL);
        }

        //3.返还库存
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderDTO.getOrderId());
        if (CollectionUtils.isEmpty(orderDetailList)) {
            log.error("【取消订单】订单详情为空,orderId={}", orderDTO.getOrderId());
            throw new SellException(SellExceptionEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDetailList.stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //4.如果已支付，需要退款
        if (orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }

        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态错误,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(SellExceptionEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updatedOrderMaster = orderMasterRepository.save(orderMaster);
        if (updatedOrderMaster == null) {
            log.error("【完结订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(SellExceptionEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【支付订单】订单状态错误,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(SellExceptionEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【支付订单】支付状态错误,orderId={},payStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(SellExceptionEnum.PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updatedOrderMaster = orderMasterRepository.save(orderMaster);
        if (updatedOrderMaster == null) {
            log.error("【支付订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(SellExceptionEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}
