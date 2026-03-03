package com.cv;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: xutu
 * @since: 2025/10/24 10:38
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== 测试工具类示例 ===");
        
        // 测试数据生成器
        TestDataGenerator generator = new TestDataGenerator();
        
        // 生成随机字符串
        String randomString = generator.generateRandomString(10);
        System.out.println("随机字符串: " + randomString);
        
        // 生成随机整数
        int randomInt = generator.generateRandomInt(1, 100);
        System.out.println("随机整数: " + randomInt);
        
        // 生成随机列表
        List<Integer> randomList = generator.generateRandomList(5, 1, 100);
        System.out.println("随机列表: " + randomList);
        
        // 测试断言工具
        TestAssertionUtils assertionUtils = new TestAssertionUtils();
        
        // 测试相等性断言
        boolean isEqual = assertionUtils.assertEqual(5, 5, "测试相等性");
        System.out.println("相等性测试结果: " + isEqual);
        
        // 测试列表断言
        List<Integer> expected = List.of(1, 2, 3, 4, 5);
        List<Integer> actual = List.of(1, 2, 3, 4, 5);
        boolean isListEqual = assertionUtils.assertListEqual(expected, actual, "测试列表相等性");
        System.out.println("列表相等性测试结果: " + isListEqual);
        
        System.out.println("=== 测试工具类示例执行完成 ===");
    }
    
    // 测试数据生成器
    public static class TestDataGenerator {
        private final Random random = new Random();
        
        // 生成指定长度的随机字符串
        public String generateRandomString(int length) {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(characters.length());
                sb.append(characters.charAt(index));
            }
            return sb.toString();
        }
        
        // 生成指定范围的随机整数
        public int generateRandomInt(int min, int max) {
            return random.nextInt(max - min + 1) + min;
        }
        
        // 生成指定大小和范围的随机列表
        public List<Integer> generateRandomList(int size, int min, int max) {
            List<Integer> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(generateRandomInt(min, max));
            }
            return list;
        }
    }
    
    // 测试断言工具
    public static class TestAssertionUtils {
        // 断言两个值相等
        public boolean assertEqual(Object expected, Object actual, String message) {
            if (expected == null && actual == null) {
                System.out.println("✓ " + message + ": 两个值都为null");
                return true;
            }
            if (expected == null || actual == null) {
                System.out.println("✗ " + message + ": 一个值为null，另一个不为null");
                return false;
            }
            boolean isEqual = expected.equals(actual);
            if (isEqual) {
                System.out.println("✓ " + message + ": " + expected + " 等于 " + actual);
            } else {
                System.out.println("✗ " + message + ": " + expected + " 不等于 " + actual);
            }
            return isEqual;
        }
        
        // 断言两个列表相等
        public boolean assertListEqual(List<?> expected, List<?> actual, String message) {
            if (expected == null && actual == null) {
                System.out.println("✓ " + message + ": 两个列表都为null");
                return true;
            }
            if (expected == null || actual == null) {
                System.out.println("✗ " + message + ": 一个列表为null，另一个不为null");
                return false;
            }
            if (expected.size() != actual.size()) {
                System.out.println("✗ " + message + ": 列表大小不同 - 预期: " + expected.size() + "，实际: " + actual.size());
                return false;
            }
            for (int i = 0; i < expected.size(); i++) {
                if (!expected.get(i).equals(actual.get(i))) {
                    System.out.println("✗ " + message + ": 索引 " + i + " 处的值不同 - 预期: " + expected.get(i) + "，实际: " + actual.get(i));
                    return false;
                }
            }
            System.out.println("✓ " + message + ": 两个列表相等");
            return true;
        }
    }
    
    // 测试服务类（用于测试）
    public static class Calculator {
        public int add(int a, int b) {
            return a + b;
        }
        
        public int subtract(int a, int b) {
            return a - b;
        }
        
        public int multiply(int a, int b) {
            return a * b;
        }
        
        public int divide(int a, int b) {
            if (b == 0) {
                throw new IllegalArgumentException("除数不能为0");
            }
            return a / b;
        }
    }
}