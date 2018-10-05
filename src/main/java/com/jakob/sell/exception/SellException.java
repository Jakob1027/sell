package com.jakob.sell.exception;

import com.jakob.sell.enums.SellExceptionEnum;

/**
 * @author Jakob
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(SellExceptionEnum sellExceptionEnum) {
        super(sellExceptionEnum.getMsg());
        code = sellExceptionEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
