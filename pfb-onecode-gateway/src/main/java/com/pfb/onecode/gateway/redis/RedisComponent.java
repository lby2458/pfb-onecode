package com.pfb.onecode.gateway.redis;


import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * 封装RedisTemplate
 * @author libingyang
 * @date 2018年5月31日上午10:57:54
 */

public class RedisComponent {
	
	private static final Logger logger = Logger.getLogger(RedisComponent.class);
	
	@Resource
	public RedisTemplate redisTemplate;
	/**
	 * 无过期时间
	 * @param key
	 * @param vlaue
	 * @return:void
	 * @author:libingyang
	 * @date:2018年5月31日 下午1:46:45
	 */
	public void set(String key, String vlaue){
		try {
			redisTemplate.opsForValue().set(key, vlaue);
		} catch (Exception e) {
			 logger.error("访问缓存服务异常！", e);
	         throw e;
		}
	}
	 /**
     * 需要设置过期时间的参数
     *
     * @param key
     * @param vlaue
     * @param expireTime
     *            过期时间（默认单位为秒）
     * @throws Exception
     */
    public void set(String key, String vlaue, long expireTime) throws Exception {
        try {
            this.set(key, vlaue, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("访问缓存服务异常！", e);
            throw e;
        }
    }

    /**
     * 需要设置过期时间的参数和时间单位
     *
     * @param key
     * @param vlaue
     * @param expireTime
     *            过期时间
     * @param unit
     *            时间单位
     * @throws Exception
     */
    public void set(String key, String vlaue, long expireTime, TimeUnit unit)
            throws Exception {
        try {
            redisTemplate.opsForValue().set(key, vlaue, expireTime, unit);
        } catch (Exception e) {
            logger.error("访问缓存服务异常！", e);
            throw e;
        }
    }
    /**
     * 根据key获取数据
     *
     * @param key
     * @return
     * @throws Exception
     */
    public String get(String key) throws Exception {
        try {
            return (String)redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("访问缓存服务异常！", e);
            throw e;
        }
    }
    /**
     * 模糊查找key列表
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) throws Exception{
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            logger.error("访问缓存服务异常！", e);
            throw e;
        }
    }
    /**
     * 根据key删除数据
     *
     * @param key
     * @throws Exception
     */
    public void delete(String key) throws Exception {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("访问缓存服务异常！", e);
            throw e;
        }
    }
    /**
     * 根据key集合删除数据
     * @param keys
     */
    public void delete(Collection<String> keys)throws Exception{
        try {
            redisTemplate.delete(keys);
        } catch (Exception e) {
            logger.error("访问缓存服务异常！", e);
            throw e;
        }
    }
    /**
     * 通过key获取过期时间
     *
     * @param key
     * @return
     */
    public long getExpireTime(String key) throws Exception{
        try {
            return redisTemplate.opsForValue().getOperations().getExpire(key);
        } catch (Exception e) {
            logger.error("访问缓存服务异常！", e);
            throw e;
        }
    }
}
