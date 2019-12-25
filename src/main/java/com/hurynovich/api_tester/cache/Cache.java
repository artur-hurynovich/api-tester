package com.hurynovich.api_tester.cache;

import com.hurynovich.api_tester.cache.cache_key.CacheKey;

public interface Cache<K extends CacheKey, V> {

    V get(K key);

}
