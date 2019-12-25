package com.hurynovich.api_tester.cache;

import com.hurynovich.api_tester.cache.cache_key.CacheKey;

public interface Cache<K extends CacheKey, V> {

    V put(K key, V value);

    V get(K key);

}
