package com.zjc.arithmetic;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName : LruCache
 * @Author : zhangjiacheng
 * @Date : 2021/6/9
 * @Description : TODO
 */
public class LruCache extends LinkedHashMap {

    private final int CACHE_SIZE;

    public LruCache(int cacheSize) {
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        CACHE_SIZE = cacheSize;

    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > CACHE_SIZE;
    }

}
