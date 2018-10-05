package com.jakob.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Jakob
 */
@Data
@Entity
public class OrderDetail {
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

    /** 订单详情id. */
    @Id
    private String detailId;

    /** 订单id. */
    private String orderId;

    /** 商品id. */
    private String productId;

    /** 商品名字. */
    private String productName;

    /** 商品价格. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 商品小图. */
    private String productIcon;
}
