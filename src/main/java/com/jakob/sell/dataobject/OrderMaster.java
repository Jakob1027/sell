package com.jakob.sell.dataobject;

import com.jakob.sell.enums.OrderStatusEnum;
import com.jakob.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jakob
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {
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

    /** 订单id. */
    @Id
    private String orderId;

    /** 买家姓名. */
    private String buyerName;

    /** 买家电话. */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态,默认0新下单 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态,默认0未支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
