package com.hurynovich.api_tester.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.hurynovich.api_tester.cache.cache_key.CacheKey;
import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;

public class GenericCache<K extends CacheKey, V> implements Cache<K, V> {

    private final com.github.benmanes.caffeine.cache.Cache<K, V> storage;

    public GenericCache(final long duration, final @NonNull TimeUnit timeUnit) {
        storage = Caffeine.newBuilder().expireAfterWrite(duration, timeUnit).build();
    }

    @Override
    public V put(final K key, final V value) {
        storage.put(key, value);

        return value;
    }

    @Override
    public V get(final K key) {
        return storage.getIfPresent(key);
    }

    @Override
    public void invalidate(final K key) {
        storage.invalidate(key);
    }

}
