package com.jakob.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakob.sell.dataobject.OrderDetail;
import com.jakob.sell.dto.OrderDTO;
import com.jakob.sell.enums.SellExceptionEnum;
import com.jakob.sell.exception.SellException;
import com.jakob.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Jakob
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        Gson gson = new Gson();
        List<OrderDetail> orderDetailList;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误，string={}",orderForm.getItems());
            throw new SellException(SellExceptionEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
