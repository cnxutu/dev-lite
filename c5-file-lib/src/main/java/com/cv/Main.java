package com.cv;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.Serializable;

/**
 * @author: xutu
 * @since: 2025/10/24 10:36
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== 文件操作示例 ===");
        
        // 1. 基本文件读写操作
        testBasicFileOperations();
        
        // 2. 文件复制和移动
        testFileCopyAndMove();
        
        // 3. 目录操作
        testDirectoryOperations();
        
        // 4. 文件属性操作
        testFileAttributes();
        
        // 5. 序列化和反序列化
        testSerialization();
        
        System.out.println("=== 所有示例执行完成 ===");
    }
    
    // 基本文件读写操作
    private static void testBasicFileOperations() {
        System.out.println("\n1. 基本文件读写操作:");
        
        String fileName = "test.txt";
        
        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Hello, File Operations!");
            writer.newLine();
            writer.write("This is a test file.");
            System.out.println("文件写入成功");
        } catch (IOException e) {
            System.err.println("文件写入错误: " + e.getMessage());
        }
        
        // 读取文件
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("文件内容:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("文件读取错误: " + e.getMessage());
        }
    }
    
    // 文件复制和移动
    private static void testFileCopyAndMove() {
        System.out.println("\n2. 文件复制和移动:");
        
        String sourceFile = "test.txt";
        String copyFile = "test_copy.txt";
        String moveFile = "moved_test.txt";
        
        try {
            // 复制文件
            Files.copy(Paths.get(sourceFile), Paths.get(copyFile), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件复制成功");
            
            // 移动文件
            Files.move(Paths.get(copyFile), Paths.get(moveFile), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件移动成功");
        } catch (IOException e) {
            System.err.println("文件操作错误: " + e.getMessage());
        }
    }
    
    // 目录操作
    private static void testDirectoryOperations() {
        System.out.println("\n3. 目录操作:");
        
        String directoryName = "test_dir";
        String subDirectoryName = "test_dir/sub_dir";
        
        try {
            // 创建目录
            Files.createDirectory(Paths.get(directoryName));
            System.out.println("目录创建成功");
            
            // 创建多级目录
            Files.createDirectories(Paths.get(subDirectoryName));
            System.out.println("多级目录创建成功");
            
            // 列出目录内容
            System.out.println("目录内容:");
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryName))) {
                for (Path path : stream) {
                    System.out.println(path.getFileName());
                }
            }
        } catch (IOException e) {
            System.err.println("目录操作错误: " + e.getMessage());
        }
    }
    
    // 文件属性操作
    private static void testFileAttributes() {
        System.out.println("\n4. 文件属性操作:");
        
        String fileName = "test.txt";
        
        try {
            Path path = Paths.get(fileName);
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            
            System.out.println("文件大小: " + attrs.size() + " bytes");
            System.out.println("文件创建时间: " + attrs.creationTime());
            System.out.println("文件最后修改时间: " + attrs.lastModifiedTime());
            System.out.println("是否为目录: " + attrs.isDirectory());
            System.out.println("是否为常规文件: " + attrs.isRegularFile());
        } catch (IOException e) {
            System.err.println("文件属性操作错误: " + e.getMessage());
        }
    }
    
    // 序列化和反序列化
    private static void testSerialization() {
        System.out.println("\n5. 序列化和反序列化:");
        
        String fileName = "person.ser";
        
        // 创建Person对象
        Person person = new Person(1, "Alice", 25);
        
        // 序列化对象
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(person);
            System.out.println("对象序列化成功");
        } catch (IOException e) {
            System.err.println("序列化错误: " + e.getMessage());
        }
        
        // 反序列化对象
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Person deserializedPerson = (Person) ois.readObject();
            System.out.println("对象反序列化成功:");
            System.out.println("ID: " + deserializedPerson.getId());
            System.out.println("Name: " + deserializedPerson.getName());
            System.out.println("Age: " + deserializedPerson.getAge());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("反序列化错误: " + e.getMessage());
        }
    }
    
    // 可序列化的Person类
    static class Person implements Serializable {
        private int id;
        private String name;
        private int age;
        
        public Person(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
        
        public int getId() {
            return id;
        }
        
        public String getName() {
            return name;
        }
        
        public int getAge() {
            return age;
        }
    }
}