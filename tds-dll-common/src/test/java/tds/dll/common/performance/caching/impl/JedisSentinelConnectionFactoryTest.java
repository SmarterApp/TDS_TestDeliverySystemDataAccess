/***************************************************************************************************
 * Educational Online Test Delivery System
 * Copyright (c) 2017 Regents of the University of California
 *
 * Distributed under the AIR Open Source License, Version 1.0
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 *
 * SmarterApp Open Source Assessment Software Project: http://smarterapp.org
 * Developed by Fairway Technologies, Inc. (http://fairwaytech.com)
 * for the Smarter Balanced Assessment Consortium (http://smarterbalanced.org)
 **************************************************************************************************/

package tds.dll.common.performance.caching.impl;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JedisSentinelConnectionFactoryTest {

    private static final String MasterName = "myMaster";

    private static final Set<String> Sentinels = ImmutableSet.of(
        "host1:5000",
        "host2:5000",
        "host2:5001");

    @Mock
    private Pool<Jedis> mockJedisPool;

    @Mock
    private Jedis mockJedis;

    @Mock
    private JedisConnection mockJedisConnection;

    private JedisSentinelConnectionFactory connectionFactory;

    @Before
    public void setup() {
        connectionFactory = spy(new JedisSentinelConnectionFactory(MasterName, Sentinels));

        doReturn(mockJedisConnection)
            .when(connectionFactory)
            .createConnection(isA(Jedis.class));

        doReturn(mockJedisPool)
            .when(connectionFactory)
            .createPool();

        when(mockJedisPool.getResource()).thenReturn(mockJedis);
    }

    @Test
    public void itShouldProvideAJedisConnection() {
        connectionFactory.afterPropertiesSet();
        final JedisConnection connection = connectionFactory.getConnection();
        verify(connection).setConvertPipelineAndTxResults(connectionFactory.getConvertPipelineAndTxResults());
        verify(connectionFactory).postProcessConnection(connection);
        verify(mockJedisPool).getResource();
    }

    @Test
    public void itShouldStoreAndRetrieveProperties() {
        connectionFactory.setConvertPipelineAndTxResults(false);
        connectionFactory.setDatabase(1);
        connectionFactory.setPassword("password");
        connectionFactory.setTimeout(1234);

        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setBlockWhenExhausted(true);
        connectionFactory.setPoolConfig(poolConfig);

        assertThat(connectionFactory.getConvertPipelineAndTxResults()).isFalse();
        assertThat(connectionFactory.getDatabase()).isEqualTo(1);
        assertThat(connectionFactory.getPassword()).isEqualTo("password");
        assertThat(connectionFactory.getTimeout()).isEqualTo(1234);
        assertThat(connectionFactory.getPoolConfig()).isEqualTo(poolConfig);
    }

    @Test
    public void itShouldDestroyThePoolOnShutdown() {
        connectionFactory.destroy();
        verifyZeroInteractions(mockJedisPool);

        connectionFactory.afterPropertiesSet();
        connectionFactory.destroy();
        verify(mockJedisPool).destroy();
    }
}