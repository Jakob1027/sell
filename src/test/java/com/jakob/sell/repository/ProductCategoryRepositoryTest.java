package com.jakob.sell.repository;

import com.jakob.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jakob
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        ProductCategory category = repository.findOne(1);
        System.out.println(category);
    }
    @Test
    public void saveTest() {
        ProductCategory c = new ProductCategory("女生最爱",2);
        c = repository.save(c);
        Assert.assertNotNull(c);
//        Assert.assertNotEquals(null,c);
    }
    @Test
    public void updateTest() {
        ProductCategory category = repository.findOne(1);
        category.setCategoryType(3);
        repository.save(category);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(1,2,3);
        List<ProductCategory> productCategoryList = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,productCategoryList.size());
    }

}