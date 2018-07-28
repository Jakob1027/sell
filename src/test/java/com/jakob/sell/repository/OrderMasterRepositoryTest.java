package com.jakob.sell.repository;

import com.jakob.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author Jakob
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void save() {
        /**
         * CREATE TABLE `order_master` (
         *   `order_id` varchar(32) NOT NULL,
         *   `buyer_name` varchar(32) NOT NULL COMMENT '买家姓名',
         *   `buyer_phone` varchar(32) NOT NULL COMMENT '买家电话',
         *   `buyer_address` varchar(128) NOT NULL COMMENT '买家地址',
         *   `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
         *   `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
         *   `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态,默认0新下单',
         *   `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态,默认0未支付',
         *   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
         *   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
         *   PRIMARY KEY (`order_id`),
         *   KEY `idx_buyer_openid` (`buyer_openid`)
         * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';
         */
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("Jakob");
        orderMaster.setBuyerPhone("157");
        orderMaster.setBuyerAddress("江干区");
        orderMaster.setBuyerOpenid("11111");
        orderMaster.setOrderAmount(new BigDecimal(76));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findByBuyerOpenId() {
        Pageable pageable = new PageRequest(0,3);

        Page<OrderMaster> page = repository.findByBuyerOpenid("11111", pageable);

        Assert.assertNotEquals(0,page.getTotalElements());

    }
}