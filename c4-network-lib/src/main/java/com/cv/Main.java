package com.cv;

import com.squareup.okhttp.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

/**
 * @author: xutu
 * @since: 2025/10/24 10:36
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== 网络操作示例 ===");
        
        // 1. 标准HttpURLConnection示例
        testHttpURLConnection();
        
        // 2. OkHttp示例
        testOkHttp();
        
        // 3. Spring WebClient同步示例
        testWebClientSync();
        
        // 4. Spring WebClient异步示例
        testWebClientAsync();
        
        // 5. 网络错误处理和重试示例
        testErrorHandlingAndRetry();
        
        System.out.println("=== 所有示例执行完成 ===");
    }
    
    // 标准HttpURLConnection示例
    private static void testHttpURLConnection() {
        System.out.println("\n1. 标准HttpURLConnection示例:");
        
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                System.out.println("Response Body: " + response.substring(0, 100) + "...");
            }
            
            connection.disconnect();
        } catch (IOException e) {
            System.err.println("HttpURLConnection Error: " + e.getMessage());
        }
    }
    
    // OkHttp示例
    private static void testOkHttp() {
        System.out.println("\n2. OkHttp示例:");
        
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://jsonplaceholder.typicode.com/posts/1")
            .build();
        
        try {
            Response response = client.newCall(request).execute();
            System.out.println("Response Code: " + response.code());
            
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println("Response Body: " + responseBody.substring(0, 100) + "...");
            }
            
            response.close();
        } catch (IOException e) {
            System.err.println("OkHttp Error: " + e.getMessage());
        }
    }
    
    // Spring WebClient同步示例
    private static void testWebClientSync() {
        System.out.println("\n3. Spring WebClient同步示例:");
        
        WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");
        
        try {
            String response = webClient.get()
                .uri("/posts/1")
                .retrieve()
                .bodyToMono(String.class)
                .block(Duration.ofSeconds(5));
            
            System.out.println("Response Body: " + response.substring(0, 100) + "...");
        } catch (Exception e) {
            System.err.println("WebClient Sync Error: " + e.getMessage());
        }
    }
    
    // Spring WebClient异步示例
    private static void testWebClientAsync() {
        System.out.println("\n4. Spring WebClient异步示例:");
        
        WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");
        
        webClient.get()
            .uri("/posts/1")
            .retrieve()
            .bodyToMono(String.class)
            .subscribe(
                response -> System.out.println("Async Response Body: " + response.substring(0, 100) + "..."),
                error -> System.err.println("WebClient Async Error: " + error.getMessage()),
                () -> System.out.println("WebClient Async Completed")
            );
        
        // 等待异步操作完成
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // 网络错误处理和重试示例
    private static void testErrorHandlingAndRetry() {
        System.out.println("\n5. 网络错误处理和重试示例:");
        
        WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");
        
        // 故意使用错误的URL来测试错误处理和重试
        webClient.get()
            .uri("/non-existent-endpoint")
            .retrieve()
            .bodyToMono(String.class)
            .retry(2) // 重试2次
            .timeout(Duration.ofSeconds(3))
            .subscribe(
                response -> System.out.println("Unexpected response: " + response),
                error -> System.err.println("Error after retries: " + error.getMessage()),
                () -> System.out.println("Operation completed")
            );
        
        // 等待操作完成
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}