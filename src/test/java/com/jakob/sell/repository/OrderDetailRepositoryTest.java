package com.jakob.sell.repository;

import com.jakob.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jakob
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save() {
        /**
         * CREATE TABLE `order_detail` (
         *   `detail_id` varchar(32) NOT NULL,
         *   `order_id` varchar(32) NOT NULL,
         *   `product_id` varchar(32) NOT NULL,
         *   `product_name` varchar(64) NOT NULL COMMENT '商品名称',
         *   `product_price` decimal(8,2) NOT NULL COMMENT '商品价格',
         *   `product_quantity` int(11) NOT NULL COMMENT '商品数量',
         *   `product_icon` varchar(512) NOT NULL COMMENT '商品小图',
         *   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
         *   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
         *   PRIMARY KEY (`detail_id`),
         *   KEY `idx_order_id` (`order_id`)
         * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情';
         */
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123457");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("123457");
        orderDetail.setProductName("皮皮虾");
        orderDetail.setProductPrice(new BigDecimal(50));
        orderDetail.setProductQuantity(3);
        orderDetail.setProductIcon("http://xxxx.jpg");

        OrderDetail result = repository.save(orderDetail);

        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> result = repository.findByOrderId("123457");

        Assert.assertEquals(0,result.size());
    }
}