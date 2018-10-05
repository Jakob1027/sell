package com.jakob.sell.service;

import com.jakob.sell.dto.OrderDTO;

/**
 * @author Jakob
 */
public interface BuyerService {

    /**
     * 查询一个订单.
     */
    OrderDTO findOrderOne(String openid, String orderId);

    /**
     * 取消订单.
     */
    OrderDTO cancelOrder(String openid, String orderId);
}
