package com.jakob.sell.enums;

import lombok.Getter;

/**
 * @author Jakob
 */
@Getter
public enum  SellExceptionEnum {

    PARAM_ERROR(1,"参数错误"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_NOT_ENOUGH(11,"商品库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDER_STATUS_ERROR(13,"订单状态异常"),
    ORDER_DETAIL_EMPTY(14,"订单详情为空"),
    ORDER_UPDATE_FAIL(15,"订单更新失败"),
    PAY_STATUS_ERROR(16,"支付状态异常"),
    CART_EMPTY(17,"购物车为空"),
    ORDER_OWNER_ERROR(18,"订单不属于当前用户")
    ;


    private Integer code;
    private String msg;

    SellExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
