package com.cv;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author: xutu
 * @since: 2025/10/24 10:35
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("=== 异步处理示例 ===");
        
        // 1. CompletableFuture 示例
        testCompletableFuture();
        
        // 2. RxJava 示例
        testRxJava();
        
        // 3. 线程池示例
        testThreadPool();
        
        // 4. 并行流示例
        testParallelStream();
        
        System.out.println("=== 所有示例执行完成 ===");
    }
    
    // CompletableFuture 示例
    private static void testCompletableFuture() throws InterruptedException, ExecutionException {
        System.out.println("\n1. CompletableFuture 示例:");
        
        // 异步执行任务
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 1 running in thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Result 1";
        });
        
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 2 running in thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Result 2";
        });
        
        // 组合多个任务结果
        CompletableFuture<String> combinedFuture = future1.thenCombine(future2, (result1, result2) -> {
            return "Combined result: " + result1 + " + " + result2;
        });
        
        // 等待结果
        String result = combinedFuture.get();
        System.out.println("CompletableFuture result: " + result);
    }
    
    // RxJava 示例
    private static void testRxJava() {
        System.out.println("\n2. RxJava 示例:");
        
        Observable.range(1, 5)
            .subscribeOn(Schedulers.computation())
            .map(i -> {
                System.out.println("RxJava processing " + i + " in thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return i * 2;
            })
            .observeOn(Schedulers.single())
            .subscribe(
                result -> System.out.println("RxJava result: " + result),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("RxJava completed")
            );
        
        // 等待RxJava执行完成
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // 线程池示例
    private static void testThreadPool() throws InterruptedException {
        System.out.println("\n3. 线程池示例:");
        
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        
        List<Future<Integer>> futures = new ArrayList<>();
        
        // 提交任务
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            Future<Integer> future = executorService.submit(() -> {
                System.out.println("ThreadPool task " + taskId + " running in thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return taskId * 10;
            });
            futures.add(future);
        }
        
        // 获取结果
        for (int i = 0; i < futures.size(); i++) {
            try {
                Integer result = futures.get(i).get();
                System.out.println("ThreadPool task " + (i + 1) + " result: " + result);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        // 关闭线程池
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }
    
    // 并行流示例
    private static void testParallelStream() {
        System.out.println("\n4. 并行流示例:");
        
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        
        List<Integer> results = numbers.parallelStream()
            .map(i -> {
                System.out.println("ParallelStream processing " + i + " in thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return i * 3;
            })
            .collect(Collectors.toList());
        
        System.out.println("ParallelStream results: " + results);
    }
}