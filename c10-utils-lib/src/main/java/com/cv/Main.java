package com.cv;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author: xutu
 * @since: 2025/10/24 10:38
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== 通用工具函数示例 ===");
        
        // 测试字符串工具
        testStringUtils();
        
        // 测试日期时间工具
        testDateTimeUtils();
        
        // 测试集合工具
        testCollectionUtils();
        
        // 测试数学工具
        testMathUtils();
        
        // 测试数组工具
        testArrayUtils();
        
        System.out.println("=== 所有工具函数示例执行完成 ===");
    }
    
    // 测试字符串工具
    private static void testStringUtils() {
        System.out.println("\n1. 字符串工具:");
        
        StringUtils stringUtils = new StringUtils();
        
        // 测试字符串为空
        System.out.println("isBlank(null): " + stringUtils.isBlank(null));
        System.out.println("isBlank(\"\"): " + stringUtils.isBlank(""));
        System.out.println("isBlank(\"   \"): " + stringUtils.isBlank("   "));
        System.out.println("isBlank(\"hello\"): " + stringUtils.isBlank("hello"));
        
        // 测试字符串反转
        System.out.println("reverse(\"hello\"): " + stringUtils.reverse("hello"));
        
        // 测试字符串是否为数字
        System.out.println("isNumber(\"123\"): " + stringUtils.isNumber("123"));
        System.out.println("isNumber(\"123.45\"): " + stringUtils.isNumber("123.45"));
        System.out.println("isNumber(\"hello\"): " + stringUtils.isNumber("hello"));
    }
    
    // 测试日期时间工具
    private static void testDateTimeUtils() {
        System.out.println("\n2. 日期时间工具:");
        
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        
        // 测试获取当前时间
        System.out.println("getCurrentDateTime(): " + dateTimeUtils.getCurrentDateTime());
        
        // 测试格式化时间
        System.out.println("formatDateTime(LocalDateTime.now()): " + dateTimeUtils.formatDateTime(LocalDateTime.now()));
        
        // 测试解析时间字符串
        String dateStr = "2023-12-25 10:30:00";
        LocalDateTime dateTime = dateTimeUtils.parseDateTime(dateStr);
        System.out.println("parseDateTime(\"2023-12-25 10:30:00\"): " + dateTime);
    }
    
    // 测试集合工具
    private static void testCollectionUtils() {
        System.out.println("\n3. 集合工具:");
        
        CollectionUtils collectionUtils = new CollectionUtils();
        
        // 测试集合是否为空
        List<String> emptyList = new ArrayList<>();
        List<String> nonEmptyList = Arrays.asList("a", "b", "c");
        System.out.println("isEmpty(emptyList): " + collectionUtils.isEmpty(emptyList));
        System.out.println("isEmpty(nonEmptyList): " + collectionUtils.isEmpty(nonEmptyList));
        
        // 测试集合合并
        List<String> list1 = Arrays.asList("a", "b");
        List<String> list2 = Arrays.asList("c", "d");
        List<String> mergedList = collectionUtils.mergeLists(list1, list2);
        System.out.println("mergeLists([a,b], [c,d]): " + mergedList);
        
        // 测试集合去重
        List<String> duplicateList = Arrays.asList("a", "b", "a", "c", "b");
        List<String> uniqueList = collectionUtils.removeDuplicates(duplicateList);
        System.out.println("removeDuplicates([a,b,a,c,b]): " + uniqueList);
    }
    
    // 测试数学工具
    private static void testMathUtils() {
        System.out.println("\n4. 数学工具:");
        
        MathUtils mathUtils = new MathUtils();
        
        // 测试求最大值
        System.out.println("max(1, 5, 3, 9, 2): " + mathUtils.max(1, 5, 3, 9, 2));
        
        // 测试求最小值
        System.out.println("min(1, 5, 3, 9, 2): " + mathUtils.min(1, 5, 3, 9, 2));
        
        // 测试求和
        System.out.println("sum(1, 5, 3, 9, 2): " + mathUtils.sum(1, 5, 3, 9, 2));
        
        // 测试求平均值
        System.out.println("average(1, 5, 3, 9, 2): " + mathUtils.average(1, 5, 3, 9, 2));
    }
    
    // 测试数组工具
    private static void testArrayUtils() {
        System.out.println("\n5. 数组工具:");
        
        ArrayUtils arrayUtils = new ArrayUtils();
        
        // 测试数组是否为空
        int[] emptyArray = {};
        int[] nonEmptyArray = {1, 2, 3};
        System.out.println("isEmpty(emptyArray): " + arrayUtils.isEmpty(emptyArray));
        System.out.println("isEmpty(nonEmptyArray): " + arrayUtils.isEmpty(nonEmptyArray));
        
        // 测试数组转列表
        Integer[] array = {1, 2, 3, 4, 5};
        List<Integer> list = arrayUtils.toList(array);
        System.out.println("toList([1,2,3,4,5]): " + list);
        
        // 测试数组排序
        int[] unsortedArray = {5, 2, 8, 1, 9};
        int[] sortedArray = arrayUtils.sort(unsortedArray);
        System.out.println("sort([5,2,8,1,9]): " + Arrays.toString(sortedArray));
    }
    
    // 字符串工具类
    public static class StringUtils {
        // 检查字符串是否为空
        public boolean isBlank(String str) {
            return str == null || str.trim().isEmpty();
        }
        
        // 反转字符串
        public String reverse(String str) {
            if (str == null) {
                return null;
            }
            return new StringBuilder(str).reverse().toString();
        }
        
        // 检查字符串是否为数字
        public boolean isNumber(String str) {
            if (str == null) {
                return false;
            }
            return Pattern.matches("^-?\\d+(\\.\\d+)?$", str);
        }
    }
    
    // 日期时间工具类
    public static class DateTimeUtils {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // 获取当前时间
        public LocalDateTime getCurrentDateTime() {
            return LocalDateTime.now();
        }
        
        // 格式化时间
        public String formatDateTime(LocalDateTime dateTime) {
            return dateTime.format(formatter);
        }
        
        // 解析时间字符串
        public LocalDateTime parseDateTime(String dateTimeStr) {
            return LocalDateTime.parse(dateTimeStr, formatter);
        }
    }
    
    // 集合工具类
    public static class CollectionUtils {
        // 检查集合是否为空
        public boolean isEmpty(Collection<?> collection) {
            return collection == null || collection.isEmpty();
        }
        
        // 合并两个列表
        public <T> List<T> mergeLists(List<T> list1, List<T> list2) {
            List<T> result = new ArrayList<>();
            if (list1 != null) {
                result.addAll(list1);
            }
            if (list2 != null) {
                result.addAll(list2);
            }
            return result;
        }
        
        // 去除集合中的重复元素
        public <T> List<T> removeDuplicates(List<T> list) {
            if (list == null) {
                return null;
            }
            return new ArrayList<>(new HashSet<>(list));
        }
    }
    
    // 数学工具类
    public static class MathUtils {
        // 求多个数的最大值
        public int max(int... numbers) {
            if (numbers == null || numbers.length == 0) {
                throw new IllegalArgumentException("至少需要一个数字");
            }
            int max = numbers[0];
            for (int num : numbers) {
                if (num > max) {
                    max = num;
                }
            }
            return max;
        }
        
        // 求多个数的最小值
        public int min(int... numbers) {
            if (numbers == null || numbers.length == 0) {
                throw new IllegalArgumentException("至少需要一个数字");
            }
            int min = numbers[0];
            for (int num : numbers) {
                if (num < min) {
                    min = num;
                }
            }
            return min;
        }
        
        // 求多个数的和
        public int sum(int... numbers) {
            int sum = 0;
            for (int num : numbers) {
                sum += num;
            }
            return sum;
        }
        
        // 求多个数的平均值
        public double average(int... numbers) {
            if (numbers == null || numbers.length == 0) {
                throw new IllegalArgumentException("至少需要一个数字");
            }
            return (double) sum(numbers) / numbers.length;
        }
    }
    
    // 数组工具类
    public static class ArrayUtils {
        // 检查数组是否为空
        public boolean isEmpty(int[] array) {
            return array == null || array.length == 0;
        }
        
        // 数组转列表
        public <T> List<T> toList(T[] array) {
            if (array == null) {
                return null;
            }
            return Arrays.asList(array);
        }
        
        // 数组排序
        public int[] sort(int[] array) {
            if (array == null) {
                return null;
            }
            int[] sortedArray = array.clone();
            Arrays.sort(sortedArray);
            return sortedArray;
        }
    }
}