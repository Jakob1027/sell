package com.jakob.sell.enums;

import lombok.Getter;

/**
 * 订单状态枚举类
 * @author Jakob
 */
@Getter
public enum  OrderStatusEnum {
    NEW(0,"新订单"),
    FINISHED(1,"订单完成"),
    CANCELED(2,"已取消"),
    ;

    private Integer code;

    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
