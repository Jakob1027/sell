package com.jakob.sell.service;

import com.jakob.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Jakob
 */
public interface OrderService {
    /** 创建订单. */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询订单. */
    OrderDTO findOne(String orderId);

    /** 查询订单列表. */
    Page<OrderDTO> findList(String buyerOpenid,Pageable pageable);

    /** 取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单. */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单. */
    OrderDTO pay(OrderDTO orderDTO);
}
