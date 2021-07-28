package com.gwdtz.springboot.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @program: springboot
 * @Date: 2021-3-9 15:19
 * @Author: Miss.Chenmf
 * @Description:
 */
@Component
public class RedisUtil {
    private static JedisPool jedisPool;
    private  static final int Expire=60 * 60 ;


    public static void setJedisPool(JedisPool jedisPool) {
        RedisUtil.jedisPool = jedisPool;
    }

    public static String redisGet(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            //logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    public static void redisPut(String key, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key,val);
            jedis.expire(key,Expire);
        } catch (Exception e) {
            e.printStackTrace();
            //logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }
    public static<T> boolean hmset(String key, T t) throws Exception{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String data= JSON.toJSONString(t);
            jedis.set(key,data);
            jedis.expire(key,Expire);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            close(jedis);
        }
    }
    public static<T> T  hmget(String key,Class<T> tClass) throws Exception{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String data=jedis.get(key);
            T t=(T)JSON.parseObject(data,tClass);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return null;

    }
    private static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
    public static boolean isredisblank(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return false;
    }
    public static void del(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(jedis);
        }
    }

}
