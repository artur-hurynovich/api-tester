package com.hurynovich.api_tester.cache;

public interface Cache<K, V> {

	V get(K key);

}
