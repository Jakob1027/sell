package com.jakob.sell.enums;

import lombok.Getter;

/**
 * 产品状态枚举类
 * @author Jakob
 */
@Getter
public enum  ProductStatusEnum {

    UP(0,"在售"),DOWN(1,"下架");

    private Integer code;
    private String msg;

    ProductStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
