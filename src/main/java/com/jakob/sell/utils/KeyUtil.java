package com.jakob.sell.utils;

import java.util.Random;

/**
 * @author Jakob
 */
public class KeyUtil {
    public synchronized static String genUniqueKey() {
        Random random = new Random();

        int tail = random.nextInt(900000) + 100000;
        return System.currentTimeMillis()+String.valueOf(tail);
    }
}
