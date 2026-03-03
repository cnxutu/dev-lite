package com.cv;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author: xutu
 * @since: 2025/10/24 10:48
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Java 21 特性示例 ===");
        
        // 1. 虚拟线程
        testVirtualThreads();
        
        // 2. switch表达式的模式匹配
        testPatternMatchingSwitch();
        
        // 3. 序列集合
        testSequencedCollections();
        
        // 4. String模板
        testStringTemplates();
        
        // 5. 未命名变量
        testUnnamedVariables();
        
        System.out.println("=== Java 21 特性示例执行完成 ===");
    }
    
    // 测试虚拟线程
    private static void testVirtualThreads() throws InterruptedException {
        System.out.println("\n1. 虚拟线程:");
        
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // 提交多个任务
            for (int i = 1; i <= 5; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    System.out.println("任务 " + taskId + " 运行在虚拟线程: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        }
        
        // 等待所有虚拟线程完成
        Thread.sleep(500);
    }
    
    // 测试switch表达式的模式匹配
    private static void testPatternMatchingSwitch() {
        System.out.println("\n2. switch表达式的模式匹配:");
        
        Object obj = 42;
        String result = switch (obj) {
            case Integer i -> "这是一个整数: " + i;
            case String s -> "这是一个字符串: " + s;
            case Double d -> "这是一个浮点数: " + d;
            default -> "未知类型";
        };
        
        System.out.println(result);
    }
    
    // 测试序列集合
    private static void testSequencedCollections() {
        System.out.println("\n3. 序列集合:");
        
        // 创建一个列表
        List<String> list = List.of("a", "b", "c", "d", "e");
        
        // 获取第一个元素
        System.out.println("第一个元素: " + list.getFirst());
        
        // 获取最后一个元素
        System.out.println("最后一个元素: " + list.getLast());
        
        // 反向遍历
        System.out.println("反向遍历:");
        list.reversed().forEach(System.out::println);
    }
    
    // 测试String模板
    private static void testStringTemplates() {
        System.out.println("\n4. String模板:");
        
        String name = "Alice";
        int age = 30;
        
        // 使用String模板
        String message = STR."Hello, name}! You are age} years old.";
        System.out.println(message);
    }
    
    // 测试未命名变量
    private static void testUnnamedVariables() {
        System.out.println("\n5. 未命名变量:");
        
        List<String> names = List.of("Alice", "Bob", "Charlie");
        
        // 使用未命名变量
        names.forEach(_ -> System.out.println("处理一个名字"));
        
        // 在try-with-resources中使用
        try (var _ = System.out) {
            System.out.println("使用未命名变量");
        }
    }
    
    // String模板处理器
    static class STR {
        public static String $(String template, Object... values) {
            // 简单实现，实际Java 21中的实现更复杂
            String result = template;
            for (Object value : values) {
                result = result.replaceFirst("\{\}", value.toString());
            }
            return result;
        }
        
        public static String $(String template, Object value) {
            return template.replace("\{}", value.toString());
        }
    }
}