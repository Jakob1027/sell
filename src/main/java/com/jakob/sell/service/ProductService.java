package com.jakob.sell.service;

import com.jakob.sell.dataobject.ProductInfo;
import com.jakob.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Jakob
 */
public interface ProductService {
    /**
     * 根据id查询商品
     * @param productId
     * @return
     */
    ProductInfo findOne(String productId);

    /**
     * 查询所有在售商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询所有商品
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存商品
     * @param productInfo 待保存商品
     * @return 保存之后的商品对象
     */
    ProductInfo save(ProductInfo productInfo);

    /** 加库存. */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减库存. */
    void decreaseStock(List<CartDTO> cartDTOList);

}
