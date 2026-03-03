package com.cv;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Properties;
import java.util.Map;

/**
 * @author: xutu
 * @since: 2025/10/24 10:38
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== 配置操作示例 ===");
        
        // 1. 属性文件操作
        testPropertiesFile();
        
        // 2. YAML配置文件操作
        testYamlFile();
        
        // 3. JSON配置文件操作
        testJsonFile();
        
        // 4. 配置验证
        testConfigValidation();
        
        // 5. 动态配置加载
        testDynamicConfig();
        
        System.out.println("=== 所有示例执行完成 ===");
    }
    
    // 属性文件操作
    private static void testPropertiesFile() {
        System.out.println("\n1. 属性文件操作:");
        
        // 创建属性文件
        String propsFile = "config.properties";
        
        // 写入属性文件
        try (OutputStream output = new FileOutputStream(propsFile)) {
            Properties properties = new Properties();
            properties.setProperty("app.name", "Config App");
            properties.setProperty("app.version", "1.0.0");
            properties.setProperty("database.url", "jdbc:mysql://localhost:3306/test");
            properties.setProperty("database.username", "root");
            properties.setProperty("database.password", "password");
            properties.store(output, "Application Configuration");
            System.out.println("属性文件写入成功");
        } catch (IOException e) {
            System.err.println("写入属性文件错误: " + e.getMessage());
        }
        
        // 读取属性文件
        try (InputStream input = new FileInputStream(propsFile)) {
            Properties properties = new Properties();
            properties.load(input);
            System.out.println("读取属性文件:");
            properties.forEach((key, value) -> {
                System.out.println(key + " = " + value);
            });
        } catch (IOException e) {
            System.err.println("读取属性文件错误: " + e.getMessage());
        }
    }
    
    // YAML配置文件操作
    private static void testYamlFile() {
        System.out.println("\n2. YAML配置文件操作:");
        
        // 创建YAML文件
        String yamlFile = "config.yaml";
        
        // 写入YAML文件
        try (FileWriter writer = new FileWriter(yamlFile)) {
            Yaml yaml = new Yaml();
            Map<String, Object> config = new java.util.HashMap<>();
            Map<String, Object> app = new java.util.HashMap<>();
            app.put("name", "Config App");
            app.put("version", "1.0.0");
            Map<String, Object> database = new java.util.HashMap<>();
            database.put("url", "jdbc:mysql://localhost:3306/test");
            database.put("username", "root");
            database.put("password", "password");
            config.put("app", app);
            config.put("database", database);
            yaml.dump(config, writer);
            System.out.println("YAML文件写入成功");
        } catch (IOException e) {
            System.err.println("写入YAML文件错误: " + e.getMessage());
        }
        
        // 读取YAML文件
        try (FileReader reader = new FileReader(yamlFile)) {
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(reader);
            System.out.println("读取YAML文件:");
            System.out.println("App Name: " + ((Map<?, ?>) config.get("app")).get("name"));
            System.out.println("App Version: " + ((Map<?, ?>) config.get("app")).get("version"));
            System.out.println("Database URL: " + ((Map<?, ?>) config.get("database")).get("url"));
            System.out.println("Database Username: " + ((Map<?, ?>) config.get("database")).get("username"));
        } catch (IOException e) {
            System.err.println("读取YAML文件错误: " + e.getMessage());
        }
    }
    
    // JSON配置文件操作
    private static void testJsonFile() {
        System.out.println("\n3. JSON配置文件操作:");
        
        // 创建JSON文件
        String jsonFile = "config.json";
        
        // 写入JSON文件
        try (FileWriter writer = new FileWriter(jsonFile)) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> config = new java.util.HashMap<>();
            Map<String, Object> app = new java.util.HashMap<>();
            app.put("name", "Config App");
            app.put("version", "1.0.0");
            Map<String, Object> database = new java.util.HashMap<>();
            database.put("url", "jdbc:mysql://localhost:3306/test");
            database.put("username", "root");
            database.put("password", "password");
            config.put("app", app);
            config.put("database", database);
            mapper.writerWithDefaultPrettyPrinter().writeValue(writer, config);
            System.out.println("JSON文件写入成功");
        } catch (IOException e) {
            System.err.println("写入JSON文件错误: " + e.getMessage());
        }
        
        // 读取JSON文件
        try (FileReader reader = new FileReader(jsonFile)) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> config = mapper.readValue(reader, Map.class);
            System.out.println("读取JSON文件:");
            System.out.println("App Name: " + ((Map<?, ?>) config.get("app")).get("name"));
            System.out.println("App Version: " + ((Map<?, ?>) config.get("app")).get("version"));
            System.out.println("Database URL: " + ((Map<?, ?>) config.get("database")).get("url"));
            System.out.println("Database Username: " + ((Map<?, ?>) config.get("database")).get("username"));
        } catch (IOException e) {
            System.err.println("读取JSON文件错误: " + e.getMessage());
        }
    }
    
    // 配置验证
    private static void testConfigValidation() {
        System.out.println("\n4. 配置验证:");
        
        // 模拟配置验证
        String propsFile = "config.properties";
        
        try (InputStream input = new FileInputStream(propsFile)) {
            Properties properties = new Properties();
            properties.load(input);
            
            // 验证必要的配置项
            System.out.println("验证配置项:");
            validateConfig(properties, "app.name", "应用名称");
            validateConfig(properties, "app.version", "应用版本");
            validateConfig(properties, "database.url", "数据库URL");
            validateConfig(properties, "database.username", "数据库用户名");
            validateConfig(properties, "database.password", "数据库密码");
        } catch (IOException e) {
            System.err.println("读取属性文件错误: " + e.getMessage());
        }
    }
    
    // 动态配置加载
    private static void testDynamicConfig() {
        System.out.println("\n5. 动态配置加载:");
        
        // 模拟动态配置加载
        DynamicConfigLoader configLoader = new DynamicConfigLoader("config.properties");
        
        // 初始加载
        System.out.println("初始配置:");
        System.out.println("App Name: " + configLoader.getProperty("app.name"));
        System.out.println("App Version: " + configLoader.getProperty("app.version"));
        
        // 模拟配置文件更新
        System.out.println("模拟配置文件更新...");
        try (OutputStream output = new FileOutputStream("config.properties")) {
            Properties properties = new Properties();
            properties.setProperty("app.name", "Updated Config App");
            properties.setProperty("app.version", "2.0.0");
            properties.setProperty("database.url", "jdbc:mysql://localhost:3306/test");
            properties.setProperty("database.username", "root");
            properties.setProperty("database.password", "password");
            properties.store(output, "Updated Application Configuration");
        } catch (IOException e) {
            System.err.println("更新属性文件错误: " + e.getMessage());
        }
        
        // 重新加载配置
        configLoader.reload();
        System.out.println("更新后的配置:");
        System.out.println("App Name: " + configLoader.getProperty("app.name"));
        System.out.println("App Version: " + configLoader.getProperty("app.version"));
    }
    
    // 验证配置项
    private static void validateConfig(Properties properties, String key, String description) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            System.err.println(description + " 配置项缺失");
        } else {
            System.out.println(description + " 配置有效: " + value);
        }
    }
    
    // 动态配置加载器
    static class DynamicConfigLoader {
        private String configFile;
        private Properties properties;
        
        public DynamicConfigLoader(String configFile) {
            this.configFile = configFile;
            reload();
        }
        
        public void reload() {
            try (InputStream input = new FileInputStream(configFile)) {
                properties = new Properties();
                properties.load(input);
            } catch (IOException e) {
                System.err.println("加载配置文件错误: " + e.getMessage());
                properties = new Properties();
            }
        }
        
        public String getProperty(String key) {
            return properties.getProperty(key);
        }
        
        public String getProperty(String key, String defaultValue) {
            return properties.getProperty(key, defaultValue);
        }
    }
}