package tds.dll.common.performance.caching.impl;

import com.google.common.collect.ImmutableList;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import tds.dll.common.performance.caching.CacheType;

import java.util.HashMap;
import java.util.Map;

/**
 * This implementation of a RedisCacheManager configures internal caches based upon the cache name.
 */
public class NameAwareRedisCacheManager extends RedisCacheManager {

    public NameAwareRedisCacheManager(final RedisTemplate redisOperations,
                                      final long shortTerm,
                                      final long mediumTerm,
                                      final long longTerm) {
        super(redisOperations);
        super.setExpires(asExpires(shortTerm, mediumTerm, longTerm));
        super.setDefaultExpiration(longTerm);
        super.setCacheNames(ImmutableList.of(CacheType.ShortTerm, CacheType.MediumTerm, CacheType.LongTerm));
    }

    /**
     * Convert the configured short, medium, and long-term expirations into the expected expiration format.
     *
     * @param shortTerm     Short-term expiration in seconds
     * @param mediumTerm    Medium-term expiration in seconds
     * @param longTerm      Long-term expiration in seconds
     * @return  A cache-name expiration map
     */
    Map<String, Long> asExpires(final long shortTerm,
                                final long mediumTerm,
                                final long longTerm) {
        final Map<String, Long> expireMap = new HashMap<>();
        expireMap.put(CacheType.ShortTerm, shortTerm);
        expireMap.put(CacheType.MediumTerm, mediumTerm);
        expireMap.put(CacheType.LongTerm, longTerm);
        return expireMap;
    }
}
