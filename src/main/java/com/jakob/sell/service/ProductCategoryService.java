package com.jakob.sell.service;

import com.jakob.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * @author Jakob
 */
public interface ProductCategoryService {
    /**
     * 根据id查询类目
     * @param categoryId the id of the category you want to find
     * @return the category you want to find
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有类目
     * @return all category
     */
    List<ProductCategory> findAll();

    /**
     * 根据类目编号表查询类目列表
     * @param categoryTypeList the list of categoryType of which the categoryType you want to find is.
     * @return the list of categoryType you want to find.
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 保存类目
     * @param productCategory the productCategory to be saved.
     * @return the productCategory after saved.
     */
    ProductCategory save(ProductCategory productCategory);

}
