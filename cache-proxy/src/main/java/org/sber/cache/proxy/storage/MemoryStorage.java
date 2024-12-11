package org.sber.cache.proxy.storage;

import org.sber.cache.exception.CacheNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class MemoryStorage implements Storage {
    private final Map<Object, Object> cache = new HashMap<>();

    @Override
    public Object get(Object key) throws CacheNotFoundException {
        if (!cache.containsKey(key)) {
            throw new CacheNotFoundException("кэш не найден");
        }
        return cache.get(key);
    }

    @Override
    public void store(Object key, Object value) {
        cache.put(key, value);
    }
}
