package com.matt.homework4;

import com.matt.homework3.ImprovedMap;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MapPerformanceTest {

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }


    @Fork(value = 1, warmups = 1)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void testImprovedMapPerformance(){
        ImprovedMap<String, Integer> improvedMap = new ImprovedMap<>();
        randomPut5000ValuesByThreads(improvedMap);
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void testSynchronizedMapPerformance(){
        Map<String, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        randomPut5000ValuesByThreads(synchronizedMap);
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void testConcurrentMapPerformance(){
        Map<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        randomPut5000ValuesByThreads(concurrentHashMap);
    }

    private void randomPut5000ValuesByThreads(ImprovedMap<String, Integer> map){
        Runnable runnable = () -> IntStream.range(0,1000).forEach(number -> map.put(UUID.randomUUID().toString(), number));
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(runnable);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(runnable);
        List<CompletableFuture<Void>> futures = List.of(future1, future2);
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
    }

    private void randomPut5000ValuesByThreads(Map<String, Integer> map){
        Runnable runnable = () -> IntStream.range(0,1000).forEach(number -> map.put(UUID.randomUUID().toString(), number));
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(runnable);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(runnable);
        List<CompletableFuture<Void>> futures = List.of(future1, future2);
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
    }

}
