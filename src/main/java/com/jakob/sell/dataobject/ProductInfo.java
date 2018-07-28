package com.jakob.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品
 * @author Jakob
 */
@Entity
@Data
public class ProductInfo {
    /**
     * CREATE TABLE `product_info` (
     * 	`product_id` VARCHAR (32) NOT NULL,
     * 	`product_name` VARCHAR (64) NOT NULL COMMENT '商品名称',
     * 	`product_price` DECIMAL (8, 2) NOT NULL COMMENT '单价',
     * 	`product_stock` INT NOT NULL COMMENT '库存',
     * 	`product_description` VARCHAR (64) COMMENT '描述',
     * 	`product_icon` VARCHAR (512) COMMENT '小图',
     * 	`product_status` TINYINT (3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
     * 	`category_type` INT NOT NULL COMMENT '类目编号',
     * 	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     * 	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     * 	PRIMARY KEY (`product_id`)
     * ) COMMENT '商品表';
     */

    @Id
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 商品状态 0正常1下架. */
    private Integer productStatus;

    /** 类目编号. */
    private Integer categoryType;
}
