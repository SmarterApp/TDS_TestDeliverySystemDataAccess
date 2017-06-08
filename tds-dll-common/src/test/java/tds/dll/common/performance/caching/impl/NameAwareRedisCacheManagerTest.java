package tds.dll.common.performance.caching.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;
import tds.dll.common.performance.caching.CacheType;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static tds.dll.common.performance.caching.CacheType.LongTerm;
import static tds.dll.common.performance.caching.CacheType.MediumTerm;
import static tds.dll.common.performance.caching.CacheType.ShortTerm;

@RunWith(MockitoJUnitRunner.class)
public class NameAwareRedisCacheManagerTest {

    private static final long shortTerm = 1;
    private static final long mediumTerm = 2;
    private static final long longTerm = 3;

    @Mock
    private RedisTemplate mockRedisTemplate;

    private NameAwareRedisCacheManager cacheManager;

    @Before
    public void setup() {
        cacheManager = new NameAwareRedisCacheManager(mockRedisTemplate, shortTerm, mediumTerm, longTerm);
    }

    @Test
    public void itShouldConfigureExpirationTimesBasedUponName() throws Exception {
        final Map<String, Long> expirationMap = cacheManager.asExpires(shortTerm, mediumTerm, longTerm);
        assertThat(expirationMap).containsOnly(
            entry(ShortTerm, shortTerm),
            entry(MediumTerm, mediumTerm),
            entry(LongTerm, longTerm));
    }
}