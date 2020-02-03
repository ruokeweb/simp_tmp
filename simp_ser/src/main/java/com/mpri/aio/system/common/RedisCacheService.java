package com.mpri.aio.system.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.mpri.aio.base.model.DataEntity;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.system.model.SysArea;

@Component
public class RedisCacheService<S extends CrudService, T extends DataEntity<T>> {

	private T t;

	private S s;

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	/**
	 * 放置缓存
	 * 
	 * @param service
	 * @param dataEntity
	 * @param cacheName
	 * @param kv         是否加载key-value的缓存
	 */
	public void putCache(S s, T t, String cacheBaseName, String cacheName) {

		Map<String, T> baseMap = new HashMap<>();

		Map<String, List<T>> listMap = new HashMap<>();

		// 每次初始化前，移除redis中对应缓存
		// redisTemplate.delete(cacheBaseName);
		// 循环新增缓存
		List<T> list = s.loadAllListBy(t);
		for (T o : list) {
			String baseKey = o.getId();

			baseMap.put(baseKey, o);

			// 加入key-value缓存
			if (cacheName != null) {

				List<T> kvList;
				// 判断是否存在该key
				boolean hasKey = redisTemplate.opsForHash().hasKey(cacheName, o.getCacheKey());
				if (listMap.containsKey(o.getCacheKey())) {
					kvList = listMap.get(o.getCacheKey());
					// 放入新值
					kvList.add(o);

				} else {
					kvList = new ArrayList<T>();
					kvList.add(o);
				}
				listMap.put(o.getCacheKey(), kvList);
			}
		}
		// 基准缓存
		redisTemplate.opsForHash().putAll(cacheBaseName, baseMap);
		// 键值缓存
		redisTemplate.opsForHash().putAll(cacheName, listMap);
		System.out.println("-------------------->" + cacheBaseName + "：加载完成：" + baseMap.size());
		System.out.println("-------------------->" + cacheName + "：加载完成：" + listMap.size());

	}

	/**
	 * 更新某具体缓存值
	 * 
	 * @param cacheName
	 * @param key
	 * @param t
	 */
	public void updateCache(T t, String cacheBaseName, String cacheName) {
		// 基准缓存更新
		redisTemplate.opsForHash().put(cacheBaseName, t.getId(), t);

		// 键值缓存更新
		if (cacheName != null) {
			String listKey = t.getCacheKey();
			List<T> list = (List<T>) redisTemplate.opsForHash().get(cacheName, listKey);

			boolean isNew = true;

			// 循环变更list中值
			for (T o : list) {
				if (o.getId().equals(t.getId())) {
					list.remove(o);
					list.add(t);
					isNew = false;
					break;
				}
			}

			// 是否是新增数据
			if (isNew) {
				list.add(t);
			}
			redisTemplate.opsForHash().put(cacheName, t.getCacheKey(), list);
		}

	}

	/**
	 * 返回id-obj的缓存对象Map
	 * 
	 * @param t
	 * @param key
	 * @return
	 */
	public Map<String, T> getBaseCache(T t, String cacheName) {
		Map<String, T> cacheMap = new HashMap<String, T>();
		// 读取缓存
		List<Object> list = redisTemplate.opsForHash().values(cacheName);
		// 循环赋值
		for (Object o : list) {
			T new_t = (T) o;
			cacheMap.put(new_t.getId(), new_t);
		}
		// 返回缓存对象
		return cacheMap;
	}

	
	/**
	 * 返回id-name的缓存对象Map
	 * 
	 * @param t
	 * @param key
	 * @return
	 */
	public Map<String, String> getKeyCache(SysArea area, String cacheName) {
		Map<String, String> cacheMap = new HashMap<String, String>();
		// 读取缓存
		List<Object> list = redisTemplate.opsForHash().values(cacheName);
		// 循环赋值
		for (Object o : list) {
			SysArea new_t = (SysArea) o;
			cacheMap.put(new_t.getId(), new_t.getName());
		}
		// 返回缓存对象
		return cacheMap;
	}	
	
	/**
	 * 根据key获取对象
	 * 
	 * @param t
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public Object getCacheByKey(String cacheName, String key) {

		return redisTemplate.opsForHash().get(cacheName, key);
	}

	/**
	 * 获取键值对缓存
	 * 
	 * @param t
	 * @param cacheName
	 * @return
	 */
	public Map<String, List<T>> getCache(T t, String cacheName) {
		Map<String, List<T>> cacheMap = new HashMap<String, List<T>>();
		Map<Object, Object> cacheObjMap = redisTemplate.opsForHash().entries(cacheName);
		// map循环赋值-强转
		cacheObjMap.forEach((k, v) -> cacheMap.put(k.toString(), (List<T>) v));
		return cacheMap;
	}

	/**
	 * 删除指定key的缓存
	 */
	public void deleteCacheByKey(String key) {
		redisTemplate.delete(key);
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public S getS() {
		return s;
	}

	public void setS(S s) {
		this.s = s;
	}

}
