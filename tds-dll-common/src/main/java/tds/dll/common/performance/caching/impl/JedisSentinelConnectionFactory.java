package tds.dll.common.performance.caching.impl;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * This class is a copy of Spring's JedisConnectionFactory with support for a JedisSentinelPool.
 */
public class JedisSentinelConnectionFactory implements InitializingBean, DisposableBean, RedisConnectionFactory {

    private final static Logger LOG = LoggerFactory.getLogger(JedisSentinelConnectionFactory.class);

    private final String masterName;
    private final Set<String> sentinels = newHashSet();

    private JedisPoolConfig poolConfig = new JedisPoolConfig();
    private int timeout = Protocol.DEFAULT_TIMEOUT;
    private String password;
    private int dbIndex = 0;
    private boolean convertPipelineAndTxResults = true;
    private Pool<Jedis> pool;

    public JedisSentinelConnectionFactory(final String masterName,
                                          final Set<String> sentinels) {
        this.masterName = masterName;
        this.sentinels.addAll(sentinels);
    }

    @Override
    public JedisConnection getConnection() {
        final Jedis jedis = fetchJedisConnector();
        final JedisConnection connection = createConnection(jedis);
        connection.setConvertPipelineAndTxResults(convertPipelineAndTxResults);
        return postProcessConnection(connection);
    }

    @Override
    public boolean getConvertPipelineAndTxResults() {
        return convertPipelineAndTxResults;
    }

    @Override
    public void afterPropertiesSet() {
        pool = createPool();
    }

    @Override
    public void destroy() {
        if (pool == null) return;

        try {
            pool.destroy();
        } catch (final Exception ex) {
            LOG.warn("Cannot properly close Jedis pool", ex);
        }
        pool = null;
    }

    @Override
    public DataAccessException translateExceptionIfPossible(final RuntimeException ex) {
        return JedisConverters.toDataAccessException(ex);
    }

    /**
     * @return password for authentication
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return Returns the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * @param timeout The timeout to set.
     */
    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }

    /**
     * @return Returns the poolConfig
     */
    public JedisPoolConfig getPoolConfig() {
        return poolConfig;
    }

    /**
     * @param poolConfig The poolConfig to set.
     */
    public void setPoolConfig(final JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    /**
     * @return Returns the database index
     */
    public int getDatabase() {
        return dbIndex;
    }

    /**
     * @param index database index
     */
    public void setDatabase(final int index) {
        Assert.isTrue(index >= 0, "invalid DB index (a positive index required)");
        this.dbIndex = index;
    }

    /**
     * @param convertPipelineAndTxResults Whether or not to convert pipeline and tx results
     */
    public void setConvertPipelineAndTxResults(final boolean convertPipelineAndTxResults) {
        this.convertPipelineAndTxResults = convertPipelineAndTxResults;
    }

    /**
     * Returns a Jedis instance to be used as a Redis connection. The instance can be newly created or retrieved from a
     * pool.
     *
     * @return Jedis instance ready for wrapping into a {@link RedisConnection}.
     */
    protected Jedis fetchJedisConnector() {
        try {
            return pool.getResource();
        } catch (final Exception ex) {
            throw new RedisConnectionFailureException("Cannot get Jedis connection", ex);
        }
    }

    /**
     * Post process a newly retrieved connection. Useful for decorating or executing initialization commands on a new
     * connection. This implementation simply returns the connection.
     *
     * @param connection    The jedis connection
     * @return processed connection
     */
    protected JedisConnection postProcessConnection(final JedisConnection connection) {
        return connection;
    }

    @VisibleForTesting
    Pool<Jedis> createPool() {
        return new JedisSentinelPool(masterName, sentinels, poolConfig, timeout, password);
    }

    @VisibleForTesting
    JedisConnection createConnection(final Jedis jedis) {
        return new JedisConnection(jedis, pool, dbIndex);
    }
}
