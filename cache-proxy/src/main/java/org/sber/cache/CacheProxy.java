package org.sber.cache;

import org.sber.cache.proxy.CachedInvocationHandler;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class CacheProxy {
    private final File cacheDir;

    public CacheProxy(File cacheDir) {
        Objects.requireNonNull(cacheDir);
        if (!cacheDir.isDirectory()) {
            throw new IllegalArgumentException("%s не является директорией"
                    .formatted(cacheDir.getAbsolutePath()));
        }

        this.cacheDir = cacheDir;
    }

    /**
     * Оборачивает объект с помощью прокси, который кэширует результаты вызова методов
     *
     * @param delegate объект, который нужно обернуть кеширующим прокси
     * @return закэшированную версию объекта
     */
    public Object cache(Object delegate) {
        return Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new CachedInvocationHandler(delegate, cacheDir)
        );
    }
}