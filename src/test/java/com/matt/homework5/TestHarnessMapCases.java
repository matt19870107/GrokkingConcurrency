package com.matt.homework5;

import com.matt.homework3.ImprovedMap;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class TestHarnessMapCases {

    @Test
    public void testImprovedMapPerformance() throws InterruptedException {
        TestHarness testHarness = new TestHarness();
        ImprovedMap<Integer, Integer> improvedMap = new ImprovedMap<>();
        long duration = testHarness.timeTasks(25, ()-> IntStream.range(0,5000).forEach(num -> improvedMap.put(num,num)));
        System.out.println("ImprovedMap Duration:" + duration);
    }

    @Test
    public void testConcurrentMapPerformance() throws InterruptedException {
        TestHarness testHarness = new TestHarness();
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        long duration = testHarness.timeTasks(25, ()-> IntStream.range(0,5000).forEach(num -> concurrentHashMap.put(num,num)));
        System.out.println("ConcurrentHashMap Duration:" + duration);
    }

    @Test
    public void testSynchronizedMapPerformance() throws InterruptedException {
        TestHarness testHarness = new TestHarness();
        Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        long duration = testHarness.timeTasks(25, ()-> IntStream.range(0,5000).forEach(num -> synchronizedMap.put(num,num)));
        System.out.println("SynchronizedMap Duration:" + duration);
    }


    /*
    * Testing Result:
    *
    * ImprovedMap Duration:32258600
    * SynchronizedMap Duration:24808900
    * ConcurrentHashMap Duration:23646400
    *
    * */

}
