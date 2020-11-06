package com.awei.ad.index;

/**
 * @description: 共用索引接口（索引的键和值）
 * @author: PENGLW
 * @date: 2020/11/5
 */
public interface IndexAware<K, V> {

    /**
     * 获取索引值
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 增加索引
     *
     * @param key
     * @param value
     */
    void add(K key, V value);

    /**
     * 更新索引
     *
     * @param key
     * @param value
     */
    void update(K key, V value);

    /**
     * 删除索引
     *
     * @param key
     * @param value
     */
    void delete(K key, V value);
}
