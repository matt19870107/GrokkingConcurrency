package com.matt.homework5;

import com.matt.homework3.ImprovedMap;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class ImprovedTestHarnessMapCases {

    @Test
    public void testImprovedMapPerformance() throws InterruptedException, BrokenBarrierException {
        ImprovedTestHarness improvedTestHarness = new ImprovedTestHarness();
        ImprovedMap<Integer, Integer> improvedMap = new ImprovedMap<>();
        long duration = improvedTestHarness.timeTasks(25, ()-> IntStream.range(0,5000).forEach(num -> improvedMap.put(num,num)));
        System.out.println("ImprovedMap Duration:" + duration);
    }

    @Test
    public void testConcurrentMapPerformance() throws InterruptedException, BrokenBarrierException {
        ImprovedTestHarness improvedTestHarness = new ImprovedTestHarness();
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        long duration = improvedTestHarness.timeTasks(25, ()-> IntStream.range(0,5000).forEach(num -> concurrentHashMap.put(num,num)));
        System.out.println("ConcurrentHashMap Duration:" + duration);
    }

    @Test
    public void testSynchronizedMapPerformance() throws InterruptedException, BrokenBarrierException {
        ImprovedTestHarness improvedTestHarness = new ImprovedTestHarness();
        Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        long duration = improvedTestHarness.timeTasks(25, ()-> IntStream.range(0,5000).forEach(num -> synchronizedMap.put(num,num)));
        System.out.println("SynchronizedMap Duration:" + duration);
    }


    /*
    * Testing Result:
    *
    * ImprovedMap Duration:23547500
    * SynchronizedMap Duration:22031100
    * ConcurrentHashMap Duration:15236300
    *
    * */

}
