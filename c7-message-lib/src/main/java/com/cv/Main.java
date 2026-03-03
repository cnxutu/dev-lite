package com.cv;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: xutu
 * @since: 2025/10/24 10:38
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 消息传递示例 ===");
        
        // 1. 简单消息队列示例
        testSimpleMessageQueue();
        
        // 2. 发布/订阅模式示例
        testPublishSubscribe();
        
        // 3. 消息确认机制示例
        testMessageAcknowledgment();
        
        // 4. 消息持久化示例
        testMessagePersistence();
        
        System.out.println("=== 所有示例执行完成 ===");
    }
    
    // 简单消息队列示例
    private static void testSimpleMessageQueue() throws InterruptedException {
        System.out.println("\n1. 简单消息队列示例:");
        
        // 创建阻塞队列作为消息队列
        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
        
        // 启动消费者线程
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                while (true) {
                    String message = messageQueue.take();
                    System.out.println("消费者接收消息: " + message);
                    // 模拟处理时间
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // 发送消息
        for (int i = 1; i <= 5; i++) {
            String message = "消息 " + i;
            messageQueue.put(message);
            System.out.println("生产者发送消息: " + message);
            Thread.sleep(200);
        }
        
        // 等待消息处理完成
        Thread.sleep(3000);
        executor.shutdown();
    }
    
    // 发布/订阅模式示例
    private static void testPublishSubscribe() throws InterruptedException {
        System.out.println("\n2. 发布/订阅模式示例:");
        
        // 创建发布/订阅管理器
        PubSubManager pubSubManager = new PubSubManager();
        
        // 注册订阅者
        pubSubManager.subscribe("topic1", message -> {
            System.out.println("订阅者1 接收消息: " + message);
        });
        
        pubSubManager.subscribe("topic1", message -> {
            System.out.println("订阅者2 接收消息: " + message);
        });
        
        pubSubManager.subscribe("topic2", message -> {
            System.out.println("订阅者3 接收消息: " + message);
        });
        
        // 发布消息
        pubSubManager.publish("topic1", "这是topic1的消息");
        pubSubManager.publish("topic2", "这是topic2的消息");
        pubSubManager.publish("topic1", "这是topic1的另一条消息");
        
        // 等待消息处理完成
        Thread.sleep(1000);
    }
    
    // 消息确认机制示例
    private static void testMessageAcknowledgment() throws InterruptedException {
        System.out.println("\n3. 消息确认机制示例:");
        
        // 创建带确认机制的消息队列
        AcknowledgmentQueue<String> ackQueue = new AcknowledgmentQueue<>();
        
        // 启动消费者线程
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                while (true) {
                    AcknowledgmentQueue.Message<String> message = ackQueue.take();
                    System.out.println("消费者接收消息: " + message.getContent());
                    
                    // 模拟处理
                    Thread.sleep(800);
                    
                    // 确认消息处理完成
                    message.acknowledge();
                    System.out.println("消费者确认消息处理完成");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // 发送消息
        for (int i = 1; i <= 3; i++) {
            String messageContent = "需要确认的消息 " + i;
            ackQueue.put(messageContent);
            System.out.println("生产者发送消息: " + messageContent);
            Thread.sleep(500);
        }
        
        // 等待消息处理完成
        Thread.sleep(5000);
        executor.shutdown();
    }
    
    // 消息持久化示例
    private static void testMessagePersistence() throws InterruptedException {
        System.out.println("\n4. 消息持久化示例:");
        
        // 创建持久化消息队列
        PersistentMessageQueue<String> persistentQueue = new PersistentMessageQueue<>();
        
        // 发送消息
        for (int i = 1; i <= 3; i++) {
            String message = "持久化消息 " + i;
            persistentQueue.put(message);
            System.out.println("生产者发送持久化消息: " + message);
        }
        
        // 模拟系统重启
        System.out.println("模拟系统重启...");
        Thread.sleep(1000);
        
        // 重启后恢复消息
        System.out.println("系统重启后恢复消息:");
        while (!persistentQueue.isEmpty()) {
            String message = persistentQueue.take();
            System.out.println("恢复并处理消息: " + message);
            Thread.sleep(500);
        }
    }
    
    // 发布/订阅管理器
    static class PubSubManager {
        private final java.util.Map<String, java.util.List<java.util.function.Consumer<String>>> subscribers = new java.util.HashMap<>();
        
        public void subscribe(String topic, java.util.function.Consumer<String> subscriber) {
            subscribers.computeIfAbsent(topic, k -> new java.util.ArrayList<>()).add(subscriber);
        }
        
        public void publish(String topic, String message) {
            java.util.List<java.util.function.Consumer<String>> topicSubscribers = subscribers.get(topic);
            if (topicSubscribers != null) {
                for (java.util.function.Consumer<String> subscriber : topicSubscribers) {
                    subscriber.accept(message);
                }
            }
        }
    }
    
    // 带确认机制的消息队列
    static class AcknowledgmentQueue<T> {
        private final BlockingQueue<Message<T>> queue = new LinkedBlockingQueue<>();
        
        public void put(T content) throws InterruptedException {
            queue.put(new Message<>(content));
        }
        
        public Message<T> take() throws InterruptedException {
            return queue.take();
        }
        
        static class Message<T> {
            private final T content;
            private boolean acknowledged = false;
            
            public Message(T content) {
                this.content = content;
            }
            
            public T getContent() {
                return content;
            }
            
            public void acknowledge() {
                this.acknowledged = true;
            }
            
            public boolean isAcknowledged() {
                return acknowledged;
            }
        }
    }
    
    // 持久化消息队列（模拟）
    static class PersistentMessageQueue<T> {
        private final BlockingQueue<T> queue = new LinkedBlockingQueue<>();
        
        public void put(T message) throws InterruptedException {
            // 模拟持久化存储
            System.out.println("持久化存储消息: " + message);
            queue.put(message);
        }
        
        public T take() throws InterruptedException {
            return queue.take();
        }
        
        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }
}