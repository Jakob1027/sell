package com.jakob.sell.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jakob
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {
    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1() {
        String name = "jakob";
        String password = "hello";

        logger.debug("debug");
        logger.info("info");
        logger.error("error");
        logger.info("name:{},password:{}",name,password);
    }




}
