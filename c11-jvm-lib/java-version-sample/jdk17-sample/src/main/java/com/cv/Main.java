package com.cv;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: xutu
 * @since: 2025/10/24 10:43
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Java 17 特性示例 ===");
        
        // 1. 文本块
        testTextBlocks();
        
        // 2. 密封类
        testSealedClasses();
        
        // 3. 记录类
        testRecordClasses();
        
        // 4. switch表达式增强
        testEnhancedSwitch();
        
        // 5. Pattern Matching for instanceof
        testPatternMatching();
        
        System.out.println("=== Java 17 特性示例执行完成 ===");
    }
    
    // 测试文本块
    private static void testTextBlocks() {
        System.out.println("\n1. 文本块:");
        
        // 使用文本块创建多行字符串
        String html = """
            <html>
                <body>
                    <h1>Hello, Java 17!</h1>
                    <p>This is a text block example.</p>
                </body>
            </html>
            """;
        
        System.out.println(html);
    }
    
    // 测试密封类
    private static void testSealedClasses() {
        System.out.println("\n2. 密封类:");
        
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        
        System.out.println("Circle area: " + circle.area());
        System.out.println("Rectangle area: " + rectangle.area());
    }
    
    // 测试记录类
    private static void testRecordClasses() {
        System.out.println("\n3. 记录类:");
        
        // 创建记录类实例
        Person person = new Person("Alice", 30, "alice@example.com");
        
        // 访问记录类的属性
        System.out.println("Name: " + person.name());
        System.out.println("Age: " + person.age());
        System.out.println("Email: " + person.email());
        
        // 记录类的toString()方法
        System.out.println("Person: " + person);
    }
    
    // 测试switch表达式增强
    private static void testEnhancedSwitch() {
        System.out.println("\n4. switch表达式增强:");
        
        Day day = Day.MONDAY;
        String activity = switch (day) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Work";
            case SATURDAY, SUNDAY -> "Rest";
        };
        
        System.out.println("Today is " + day + ", activity: " + activity);
    }
    
    // 测试Pattern Matching for instanceof
    private static void testPatternMatching() {
        System.out.println("\n5. Pattern Matching for instanceof:");
        
        Object obj = "Hello, Java 17!";
        
        // 使用模式匹配
        if (obj instanceof String s) {
            System.out.println("Object is a String: " + s.toUpperCase());
        } else {
            System.out.println("Object is not a String");
        }
    }
    
    // 密封类示例
    sealed interface Shape permits Circle, Rectangle {
        double area();
    }
    
    static final class Circle implements Shape {
        private final double radius;
        
        public Circle(double radius) {
            this.radius = radius;
        }
        
        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }
    
    static final class Rectangle implements Shape {
        private final double width;
        private final double height;
        
        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }
        
        @Override
        public double area() {
            return width * height;
        }
    }
    
    // 记录类示例
    record Person(String name, int age, String email) {
        // 记录类可以有自定义构造函数
        public Person {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
        }
    }
    
    // 枚举类示例
    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}