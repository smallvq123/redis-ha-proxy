package com.baidu.unbiz.redis.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 随机数产生工具类
 *
 * @author zhangxu04
 */
public class RandomUtil {

    /**
     * 产生[0-limit]之间的一个不重复随机list
     * 
     * @param limit
     * @return 随机化的list
     */
    public static List<Integer> randomizeWithinLimit(int limit) {
        List<Integer> list = new ArrayList<Integer>(limit);
        for (int i = 0; i < limit; i++) {
            list.add(i);
        }
        Collections.shuffle(list, new Random());
        return list;
    }

}
