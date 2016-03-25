/*******************************************************************************
 * Educational Online Test Delivery System
 * Copyright (c) 2016 Regents of the University of California
 *
 * Distributed under the AIR Open Source License, Version 1.0
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 *
 * SmarterApp Open Source Assessment Software Project: http://smarterapp.org
 * Developed by Fairway Technologies, Inc. (http://fairwaytech.com)
 * for the Smarter Balanced Assessment Consortium (http://smarterbalanced.org)
 ******************************************************************************/
package tds.dll.common.performance.caching.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import tds.dll.common.performance.caching.CacheKeyGenerator;
import tds.dll.common.performance.caching.CacheType;
import tds.dll.common.performance.caching.CachingService;

import java.util.concurrent.Callable;

public class CachingServiceImpl implements CachingService {
    private CacheManager cacheManager;
    private Boolean enabled = true;
    private String defaultCache = CacheType.ShortTerm;

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isEnabled() {
        return enabled && cacheManager != null;
    }

    @Autowired
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setValue(String key, Object value) {
        setValue(defaultCache, key, value);
    }

    public void setValue(String cacheName, String key, Object value) {
        if (!isEnabled()) {
            return;
        }

        cacheManager.getCache(cacheName).put(key, value);
    }

    public Object getValue(String key) {
        return getValue(defaultCache, key);
    }

    public Object getValue(String cacheName, String key) {
        if (!isEnabled()) {
            return null;
        }

        Cache.ValueWrapper result = cacheManager.getCache(cacheName).get(key);

        if (result != null) {
            return result.get();
        }

        return null;
    }

    public void removeAll(String cacheName) {
        cacheManager.getCache(cacheName).clear();
    }


    public void removeAll() {
        removeAll(defaultCache);
    }

    public void remove(String cacheName, String key) {
        cacheManager.getCache(cacheName).evict(key);
    }

    public void remove(String key) {
        remove(defaultCache, key);
    }

    public <T> T cacheableCall(Callable<T> func, String key) throws Exception {
        return cacheableCall(func, defaultCache, key);
    }

    public <T> T cacheableCall(Callable<T> func, String cacheName, String className, String methodName, Object... params) throws Exception {
        return cacheableCall(func, cacheName, generateKey(className, methodName, params));
    }

    public <T> T cacheableCall(Callable<T> func, String cacheName, String key) throws Exception {
        T result = (T)getValue(cacheName, key);

        if (result == null) {
            result = func.call();

            setValue(key, result);
        }

        return result;
    }

    public String generateKey(String className, String methodName, Object... params) {
        return new CacheKeyGenerator().generate(className, methodName, params);
    }
}
