package com.jakob.sell.controller;

import com.jakob.sell.VO.ResultVO;
import com.jakob.sell.converter.OrderForm2OrderDTOConverter;
import com.jakob.sell.dto.OrderDTO;
import com.jakob.sell.enums.SellExceptionEnum;
import com.jakob.sell.exception.SellException;
import com.jakob.sell.form.OrderForm;
import com.jakob.sell.service.BuyerService;
import com.jakob.sell.service.OrderService;
import com.jakob.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jakob
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确,orderForm={}", orderForm);
            throw new SellException(SellExceptionEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(SellExceptionEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam String openid,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(SellExceptionEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> list = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(list.getContent());
    }

    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam String openid,
                                     @RequestParam String orderId) {
        return ResultVOUtil.success(buyerService.findOrderOne(openid, orderId));
    }

    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam String openid,
                           @RequestParam String orderId) {
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
