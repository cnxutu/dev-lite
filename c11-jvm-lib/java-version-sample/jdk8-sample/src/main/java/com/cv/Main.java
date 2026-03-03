package com.cv;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: xutu
 * @since: 2025/10/24 10:43
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Java 8 特性示例 ===");
        
        // 1. Lambda表达式
        testLambdaExpressions();
        
        // 2. Stream API
        testStreamAPI();
        
        // 3. Optional
        testOptional();
        
        // 4. 方法引用
        testMethodReferences();
        
        // 5. 日期时间API
        testDateTimeAPI();
        
        System.out.println("=== Java 8 特性示例执行完成 ===");
    }
    
    // 测试Lambda表达式
    private static void testLambdaExpressions() {
        System.out.println("\n1. Lambda表达式:");
        
        // 实现Runnable接口
        Runnable runnable = () -> System.out.println("Hello from Lambda Runnable");
        runnable.run();
        
        // 实现Comparator接口
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        System.out.println("原始列表: " + names);
        
        // 使用Lambda表达式排序
        names.sort((s1, s2) -> s1.compareTo(s2));
        System.out.println("排序后: " + names);
    }
    
    // 测试Stream API
    private static void testStreamAPI() {
        System.out.println("\n2. Stream API:");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // 过滤偶数
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("偶数: " + evenNumbers);
        
        // 映射并求和
        int sum = numbers.stream()
            .map(n -> n * 2)
            .reduce(0, Integer::sum);
        System.out.println("所有数乘以2的和: " + sum);
        
        // 查找最大值
        Optional<Integer> max = numbers.stream()
            .max(Integer::compare);
        System.out.println("最大值: " + max.orElse(0));
    }
    
    // 测试Optional
    private static void testOptional() {
        System.out.println("\n3. Optional:");
        
        // 创建Optional
        Optional<String> optionalEmpty = Optional.empty();
        Optional<String> optionalWithValue = Optional.of("Hello Optional");
        
        // 检查是否有值
        System.out.println("optionalEmpty.isPresent(): " + optionalEmpty.isPresent());
        System.out.println("optionalWithValue.isPresent(): " + optionalWithValue.isPresent());
        
        // 获取值
        String value1 = optionalWithValue.orElse("Default Value");
        String value2 = optionalEmpty.orElse("Default Value");
        System.out.println("optionalWithValue值: " + value1);
        System.out.println("optionalEmpty值: " + value2);
        
        // 使用ifPresent
        optionalWithValue.ifPresent(s -> System.out.println("Optional有值: " + s));
    }
    
    // 测试方法引用
    private static void testMethodReferences() {
        System.out.println("\n4. 方法引用:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // 引用静态方法
        System.out.println("使用静态方法引用:");
        names.forEach(System.out::println);
        
        // 引用实例方法
        System.out.println("使用实例方法引用:");
        names.stream()
            .map(String::toUpperCase)
            .forEach(System.out::println);
    }
    
    // 测试日期时间API
    private static void testDateTimeAPI() {
        System.out.println("\n5. 日期时间API:");
        
        // 获取当前日期时间
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        System.out.println("当前日期时间: " + now);
        
        // 格式化日期
        String formattedDate = now.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("格式化日期: " + formattedDate);
        
        // 日期计算
        java.time.LocalDateTime tomorrow = now.plusDays(1);
        System.out.println("明天此时: " + tomorrow);
    }
}