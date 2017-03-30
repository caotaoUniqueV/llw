package com.linwang.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.Account;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class RedisSessionDAO extends AbstractSessionDAO {

	private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
	/**
	 * shiro-redis的session对象前缀
	 */
	private final String SHIRO_REDIS_SESSION_PRE = "shiro_redis_session:";
	
	/**
	 * 存放uid的对象前缀
	 */
	private final String SHIRO_SHESSIONID_PRE = "shiro_sessionid:";
	
	/**
	 * 存放uid 当前状态的前缀
	 */
	private final String UID_PRE = "uid:";
	
	/**
	 * 存放用户权限的前缀
	 */
	private final String PERMISSION_PRE ="permission:";

    private JedisPoolManager redisManager;

	private long expire;
	
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}
	
	public RedisSessionDAO(){
//		redisManager=new JedisPoolManager();
	}

	/**
	 * save session
	 * 
	 * @param session
	 * @throws UnknownSessionException
	 */
	private void saveSession(Session session) throws UnknownSessionException {
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			return;
		}
	    session.setTimeout(expire);
	    Long redisExpire = expire/1000;
	    int timeout = redisExpire.intValue();
	    	JedisPool jedisPool = this.redisManager.getJedisPool();
		Jedis jedis = null;
		try {
			jedis =  jedisPool.getResource();			
			//保存用户会话
			jedis.setex(this.getByteKey(this.SHIRO_REDIS_SESSION_PRE,session.getId()), timeout, SerializeUtils.serialize(session));
			String uid = this.getUserId(session);
			if (null != uid && !"".equals(uid)){
				//保存用户会话对应的UID
				jedis.setex(this.getByteKey(this.SHIRO_SHESSIONID_PRE,session.getId()),timeout, uid.getBytes());
				//保存在线UID
				jedis.setex(this.getByteKey(this.UID_PRE,uid), timeout,"online".getBytes());
			}
            jedisPool.returnResource(jedis);
		}catch(Exception ex){
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(ex);
		}
		
		
	}
	
	public void delete(Session session) {
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			return;
		}
		
		JedisPool jedisPool = this.redisManager.getJedisPool();
		Jedis jedis = null;
		try {
			jedis =  jedisPool.getResource();
			
			//删除用户会话
			jedis.del(this.getByteKey(this.SHIRO_REDIS_SESSION_PRE,session.getId()));
			//获取缓存的用户会话对应的UID
			byte[] uid = jedis.get(this.getByteKey(this.SHIRO_SHESSIONID_PRE,session.getId()));
			
			//删除用户会话对应的UID
			jedis.del(this.getByteKey(this.SHIRO_SHESSIONID_PRE,session.getId()));
			
			//删除在线UID
			jedis.del(this.getByteKey(this.UID_PRE,new String(uid)));
			
			//删除用户缓存的权限
			jedis.del(this.getByteKey(this.PERMISSION_PRE, new String(uid)));
			
            jedisPool.returnResource(jedis);
		}catch(Exception ex){
			if (jedis != null) {
				jedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(ex);
		}
	
 	}

	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();

		Set<byte[]> keys = redisManager
				.keys(this.SHIRO_REDIS_SESSION_PRE + "*");
		if (keys != null && keys.size() > 0) {
			for (byte[] key : keys) {
				Session s = (Session) SerializeUtils.deserialize(redisManager
						.get(key));
				sessions.add(s);
			}
		}

		return sessions;
	}
	
	public boolean isOnLine(String uid){
		
		Set<byte[]>keys = redisManager.keys(this.UID_PRE + uid);
		if (keys != null && keys.size() > 0){
		  return true;
		}
		return false;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.saveSession(session);
		return sessionId;
	}

	protected Session doReadSession(Serializable sessionId) {
		if (sessionId == null) {
			logger.error("session id is null");
			return null;
		}
		Session s =null;
		logger.debug("#####Redis.SessionId=" + new String(getByteKey(this.SHIRO_REDIS_SESSION_PRE,sessionId)));
		if(SerializeUtils.deserialize(redisManager.get(this.getByteKey(this.SHIRO_REDIS_SESSION_PRE,sessionId)))!=null){
			s = (Session) SerializeUtils.deserialize(redisManager.get(this
					.getByteKey(this.SHIRO_REDIS_SESSION_PRE,sessionId)));
		}
		return s;
	}
	
	/**
	 * 获得byte[]型的key
	 * 
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(String preKey,Serializable sessionId) {
		String key = preKey + sessionId;
		return key.getBytes();
	
	}
	
	/**
	 * 获取用户唯一标识
	 * @param session
	 * @return
	 */
	private String getUserId(Session session){
		SimplePrincipalCollection pricipal = (SimplePrincipalCollection)session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
        if (null != pricipal){
//        	Account account = ((Account) pricipal.getPrimaryPrincipal());
        	return  (String)pricipal.getPrimaryPrincipal();
        }
        return  "";
	}
	//setter和getter省略

	public JedisPoolManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(JedisPoolManager redisManager) {
		this.redisManager = redisManager;
	}

	public long getExpire() {
		return expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}

	public String getSHIRO_REDIS_SESSION_PRE() {
		return SHIRO_REDIS_SESSION_PRE;
	}

	public String getSHIRO_SHESSIONID_PRE() {
		return SHIRO_SHESSIONID_PRE;
	}

	public String getUID_PRE() {
		return UID_PRE;
	}

	public String getPERMISSION_PRE() {
		return PERMISSION_PRE;
	}
	
	

}