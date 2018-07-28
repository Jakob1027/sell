package com.jakob.sell.service.impl;

import com.jakob.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jakob
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory category = categoryService.findOne(1);
        Assert.assertEquals(Integer.valueOf(1),category.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> all = categoryService.findAll();
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> categoryTypeList = Arrays.asList(1, 2, 3);
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(categoryTypeList);
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    @Transactional
    public void save() {
        ProductCategory category = categoryService.save(new ProductCategory("男生最爱", 4));
        Assert.assertEquals(Integer.valueOf(4),category.getCategoryType());
    }
}