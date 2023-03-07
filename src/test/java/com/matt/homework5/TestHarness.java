package com.matt.homework5;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class TestHarness {
    @Test
    void testConcurrentMapPerformanceByBarrierHarness() throws BrokenBarrierException, InterruptedException {
         BarrierHarness barrierHarness = new BarrierHarness();
         long duration = barrierHarness.timeTasks(25,()->{
             Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
             IntStream.range(0,5000).forEach(number -> concurrentHashMap.put(number, number));
         });
         System.out.println("BarrierHarness Duration:" + duration);
    }

   @Test
    void testConcurrentMapPerformanceByHarness() throws InterruptedException {
        Harness harness = new Harness();
        long duration = harness.timeTasks(25,()->{
            Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
            IntStream.range(0,5000).forEach(number -> concurrentHashMap.put(number, number));
        });
        System.out.println("Harness Duration:" + duration);
    }
}
