package com.hurynovich.api_tester.cache;

import com.hurynovich.api_tester.cache.cache_key.CacheKey;

import java.util.HashMap;
import java.util.Map;

public class GenericCache<K extends CacheKey, V> implements Cache<K, V> {

    private final Map<K, V> storage = new HashMap<>();

    @Override
    public V put(final K key, final V value) {
        return storage.put(key, value);
    }

    @Override
    public V get(final K key) {
        return storage.get(key);
    }

    @Override
    public void invalidate(final K key) {
        storage.remove(key);
    }

}
