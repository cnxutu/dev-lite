package com.cv;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author: xutu
 * @since: 2025/10/24 10:37
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("=== 缓存操作示例 ===");
        
        // 1. 简单内存缓存示例
        testSimpleMemoryCache();
        
        // 2. Guava Cache示例
        testGuavaCache();
        
        // 3. Caffeine Cache示例
        testCaffeineCache();
        
        // 4. 缓存过期和刷新示例
        testCacheExpiration();
        
        // 5. 缓存加载器示例
        testCacheLoader();
        
        System.out.println("=== 所有示例执行完成 ===");
    }
    
    // 简单内存缓存示例
    private static void testSimpleMemoryCache() {
        System.out.println("\n1. 简单内存缓存示例:");
        
        Map<String, String> cache = new HashMap<>();
        
        // 存储数据
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        System.out.println("缓存数据存储成功");
        
        // 获取数据
        System.out.println("获取key1: " + cache.get("key1"));
        System.out.println("获取key2: " + cache.get("key2"));
        
        // 删除数据
        cache.remove("key1");
        System.out.println("删除key1后获取: " + cache.get("key1"));
    }
    
    // Guava Cache示例
    private static void testGuavaCache() throws ExecutionException {
        System.out.println("\n2. Guava Cache示例:");
        
        // 创建Guava Cache
        com.google.common.cache.Cache<String, String> guavaCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();
        
        // 存储数据
        guavaCache.put("guavaKey1", "guavaValue1");
        guavaCache.put("guavaKey2", "guavaValue2");
        System.out.println("Guava Cache数据存储成功");
        
        // 获取数据
        System.out.println("获取guavaKey1: " + guavaCache.getIfPresent("guavaKey1"));
        System.out.println("获取guavaKey2: " + guavaCache.getIfPresent("guavaKey2"));
        
        // 使用get方法，如果键不存在则计算
        String value = guavaCache.get("guavaKey3", () -> "guavaValue3");
        System.out.println("获取guavaKey3 (计算): " + value);
    }
    
    // Caffeine Cache示例
    private static void testCaffeineCache() {
        System.out.println("\n3. Caffeine Cache示例:");
        
        // 创建Caffeine Cache
        Cache<String, String> caffeineCache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();
        
        // 存储数据
        caffeineCache.put("caffeineKey1", "caffeineValue1");
        caffeineCache.put("caffeineKey2", "caffeineValue2");
        System.out.println("Caffeine Cache数据存储成功");
        
        // 获取数据
        System.out.println("获取caffeineKey1: " + caffeineCache.getIfPresent("caffeineKey1"));
        System.out.println("获取caffeineKey2: " + caffeineCache.getIfPresent("caffeineKey2"));
        
        // 使用get方法，如果键不存在则计算
        String value = caffeineCache.get("caffeineKey3", k -> "caffeineValue3");
        System.out.println("获取caffeineKey3 (计算): " + value);
    }
    
    // 缓存过期和刷新示例
    private static void testCacheExpiration() throws InterruptedException {
        System.out.println("\n4. 缓存过期和刷新示例:");
        
        // 创建带有过期时间的Caffeine Cache
        Cache<String, String> cacheWithExpiration = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(2, TimeUnit.SECONDS) // 2秒过期
            .build();
        
        // 存储数据
        cacheWithExpiration.put("expireKey", "expireValue");
        System.out.println("存储数据: " + cacheWithExpiration.getIfPresent("expireKey"));
        
        // 等待3秒，让缓存过期
        System.out.println("等待3秒让缓存过期...");
        Thread.sleep(3000);
        
        // 再次获取数据
        System.out.println("过期后获取数据: " + cacheWithExpiration.getIfPresent("expireKey"));
    }
    
    // 缓存加载器示例
    private static void testCacheLoader() throws ExecutionException {
        System.out.println("\n5. 缓存加载器示例:");
        
        // 创建带有加载器的Guava Cache
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .build(
                key -> {
                    // 模拟从数据库或其他数据源加载数据
                    System.out.println("从数据源加载数据 for key: " + key);
                    return "Loaded value for " + key;
                }
            );
        
        // 获取数据（首次会触发加载）
        System.out.println("首次获取key1: " + loadingCache.get("key1"));
        
        // 再次获取数据（从缓存获取）
        System.out.println("再次获取key1: " + loadingCache.get("key1"));
        
        // 获取另一个键的数据
        System.out.println("获取key2: " + loadingCache.get("key2"));
    }
}