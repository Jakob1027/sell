package com.jakob.sell.service.impl;

import com.jakob.sell.dataobject.ProductInfo;
import com.jakob.sell.dto.CartDTO;
import com.jakob.sell.enums.ProductStatusEnum;
import com.jakob.sell.enums.SellExceptionEnum;
import com.jakob.sell.exception.SellException;
import com.jakob.sell.repository.ProductInfoRepository;
import com.jakob.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jakob
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;


    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if(productInfo == null) {
                throw new SellException(SellExceptionEnum.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if(productInfo == null) {
                throw new SellException(SellExceptionEnum.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result<0) {
                throw new SellException(SellExceptionEnum.PRODUCT_STOCK_NOT_ENOUGH);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }
}
