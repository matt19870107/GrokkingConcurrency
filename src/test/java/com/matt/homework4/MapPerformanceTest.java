package com.matt.homework4;

import com.matt.homework3.ImprovedMap;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MapPerformanceTest {

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }


    @Fork(value = 2, warmups = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void testImprovedMapPerformance(){
        ImprovedMap<String, Integer> improvedMap = new ImprovedMap<>();
        Consumer<Integer> consumer = number -> improvedMap.put(UUID.randomUUID().toString(), number);
        randomPut5000ValuesByThreads(consumer);
    }

    @Fork(value = 2, warmups = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void testSynchronizedMapPerformance(){
        Map<String, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        Consumer<Integer> consumer = number -> synchronizedMap.put(UUID.randomUUID().toString(), number);
        randomPut5000ValuesByThreads(consumer);
    }

    @Fork(value = 2, warmups = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void testConcurrentMapPerformance(){
        Map<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Consumer<Integer> consumer = number -> concurrentHashMap.put(UUID.randomUUID().toString(), number);
        randomPut5000ValuesByThreads(consumer);
    }

    private void randomPut5000ValuesByThreads( Consumer<Integer> consumer){
        Runnable runnable = () -> IntStream.range(0,5000).forEach(number -> consumer.accept(number));
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(runnable);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(runnable);
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(runnable);
        CompletableFuture<Void> future4 = CompletableFuture.runAsync(runnable);
        List<CompletableFuture<Void>> futures = List.of(future1, future2,future3,future4);
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
    }

}
