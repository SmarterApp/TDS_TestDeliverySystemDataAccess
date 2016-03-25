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
package tds.dll.common.performance.caching;

import java.util.concurrent.Callable;

public interface CachingService {
    void setEnabled(Boolean enabled);
    Boolean isEnabled();

    void setValue(String cacheName, String key, Object value);
    void setValue(String key, Object value);

    Object getValue(String cacheName, String key);
    Object getValue(String key);

    void removeAll(String cacheName);
    void removeAll();

    void remove(String cacheName, String key);
    void remove(String key);

    <T> T cacheableCall(Callable<T> func, String cacheName, String className, String methodName, Object... params) throws Exception;
    <T> T cacheableCall(Callable<T> func, String key) throws Exception;
    <T> T cacheableCall(Callable<T> func, String cacheName, String key) throws Exception;

    String generateKey(String className, String methodName, Object... params);
}
