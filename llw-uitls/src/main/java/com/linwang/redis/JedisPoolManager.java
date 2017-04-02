package com.linwang.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import com.linwang.uitls.StaticProp;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * JedisPool 管理类 
 * 用于单个redis 集群， 每个redis集群由master-salve组成
 */
public class JedisPoolManager {

	private static Log log = LogFactory.getLog(JedisPoolManager.class);

	private JedisPool jedisPool;//非切片连接池
	
//	public JedisPoolManager(JedisPoolConfig config,String host,int port){
//		jedisPool = new JedisPool(config,host,port);
//	}
	
	public JedisPoolManager(){
//		initialPool();
	}
	
//	/**
//     * 初始化非切片池
//  */
    private void initialPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxActive(Integer.parseInt(StaticProp.sysConfig.get("redis.maxActive"))); 
        config.setMaxIdle(Integer.parseInt(StaticProp.sysConfig.get("redis.maxIdle"))); 
        config.setMaxWait(Integer.parseInt(StaticProp.sysConfig.get("redis.maxWait"))); 
        config.setTestOnBorrow(Boolean.valueOf(StaticProp.sysConfig.get("redis.testOnBorrow"))); 
        jedisPool = new JedisPool(config,StaticProp.sysConfig.get("redis.host"),Integer.parseInt(StaticProp.sysConfig.get("redis.port")));
    }
	/**
	 * redis的List集合 ，向key这个list添加元素
	 */
	public long rpush(String key, String string) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long ret = jedis.rpush(key, string);
			return ret;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 获取key这个List，从第几个元素到第几个元素 LRANGE key start
	 * stop返回列表key中指定区间内的元素，区间以偏移量start和stop指定。
	 * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。
	 * 也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
	 */
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<String> ret = jedis.lrange(key, start, end);
			return ret;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 将哈希表key中的域field的值设为value。
	 */
	public void hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 向key赋值
	 */
	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 向key赋值
	 */
	public void set(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 获取key的值
	 */
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String value = jedis.get(key);
			return value;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 获取key的值
	 */
	public byte[] get(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] value = jedis.get(key);
			return value;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}

	}
	/**
	 * 将多个field - value(域-值)对设置到哈希表key中。
	 */
	public void hmset(String key, Map<String, String> map) {
		Jedis jedis = null;
		try {
			 jedis = jedisPool.getResource();
			jedis.hmset(key, map);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	  * 给key赋值，并生命周期设置为seconds
	 */
	public void setex(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			 jedis = jedisPool.getResource();
			jedis.setex(key, seconds, value);
		 } catch (Exception e) {
			log.error(e);
			if (jedis != null) {
 				jedisPool.returnBrokenResource(jedis);
 			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 给key赋值，并生命周期设置为seconds
	 */
	public byte[] setex(byte[] key, byte[] value, int seconds) {
		Jedis jedis = null;
		try {
			 jedis = jedisPool.getResource();
			jedis.setex(key, seconds, value);
			return value;
		} catch (Exception e) {
			log.error(e);
 			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
		

	}
	/**
	 * 为给定key设置生命周期
	 */
	public void expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			 jedis = jedisPool.getResource();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			 throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 检查key是否存在
	 */
	public boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			boolean bool = jedis.exists(key);
			return bool;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
        /**
	 * 检查key是否存在
	 */
	public boolean exists(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		    Set<byte[]> hashSet = jedis.keys(key);
		    if (null != hashSet && hashSet.size() >0 ){
		    	return true;
		    }else{
		    	return false;
		    }

		} catch (Exception e) {
			 log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 返回key值的类型 none(key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表)
	 */
	public String type(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String type = jedis.type(key);
			return type;
 		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 从哈希表key中获取field的value
	 */
	public String hget(String key, String field) {
		Jedis jedis = null;
		try {
			 jedis = jedisPool.getResource();
			String value = jedis.hget(key, field);
			return value;
 		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 返回哈希表key中，所有的域和值
	 */
	public Map<String, String> hgetAll(String key) {
		Jedis jedis = null;
		try {
			 jedis = jedisPool.getResource();
			Map<String, String> map = jedis.hgetAll(key);
			return map;
		} catch (Exception e) {
			 log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}

	}
	/**
	 * 返回哈希表key中，所有的域和值
	 */
	public Set<?> smembers(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			 Set<?> set = jedis.smembers(key);
 			return set;
 		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			 throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}	
	/**
	 * 返回匹配的 keys 列表
	 */
	 public Set<byte[]> keys(String pattern) {
 		Jedis jedis = null;
 		try {
			jedis = jedisPool.getResource();
			Set<byte[]> keys = jedis.keys(pattern.getBytes());
			return keys;
 		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}

	}
	/**
	 * 移除set集合中的member元素
	 */
	public void delSetObj(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.srem(key, field);
		} catch (Exception e) {
 			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 删除元素
	 */
	public void del(byte[] key) {
		 Jedis jedis = null;
 		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
 		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 判断member元素是否是集合key的成员。是（true），否则（false）
	 */
	public boolean isNotField(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			 boolean bool = jedis.sismember(key, field);
			 return bool;
		} catch (Exception e) {
			 log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 如果key已经存在并且是一个字符串，将value追加到key原来的值之后
	 */
	public void append(String key, String value) {
		Jedis jedis = null;
 		try {
 			jedis = jedisPool.getResource();
			jedis.append(key, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
 			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 清空当前的redis 库
	 */
	public void flushDB() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
 			jedis.flushDB();
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}

	 }
	/**
	 * 返回当前redis库所存储数据的大小
	 */
	public Long dbSize() {
		
		Long dbSize = 0L;
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			 jedis.dbSize();
			return dbSize;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		}finally{
			jedisPool.returnResource(jedis);
		}

	}
	
	public JedisPool getJedisPool() {
			return jedisPool;
	}
	public void setJedisPool(JedisPool jedisPool) {
			this.jedisPool = jedisPool;
	}
	/**
	 * 关闭 Redis
	 */
	public void destory() {
		jedisPool.destroy();
	}
	
}
