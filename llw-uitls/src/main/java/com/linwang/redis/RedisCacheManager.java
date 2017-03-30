package com.linwang.redis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisCacheManager implements CacheManager {

	private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

	// fast lookup by name map
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	
	 /** 
     * The Redis key prefix for caches  
     */  
    private String keyPrefix = "shiro_redis_cache:";  

	private JedisPoolManager redisManager;

	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		logger.debug("获取名称为: " + name + " 的RedisCache实例");
		System.out.println("获取名称为: " + name + " 的RedisCache实例");
		Cache c = caches.get(name);
		if (c == null) {
			c = new RedisCache<K, V>(redisManager,keyPrefix);
			caches.put(name, c);
		}
		return c;
	}
        //setter和getter方法省略

	public JedisPoolManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(JedisPoolManager redisManager) {
		this.redisManager = redisManager;
	}

	public ConcurrentMap<String, Cache> getCaches() {
		return caches;
	}
	
}
