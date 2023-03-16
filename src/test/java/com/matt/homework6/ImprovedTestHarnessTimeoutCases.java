package com.matt.homework6;

import com.matt.homework3.ImprovedMap;
import com.matt.homework5.ImprovedTestHarness;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.IsEqual.equalTo;

public class ImprovedTestHarnessTimeoutCases {

    @Test
    public void testTimeoutThreadsCanBeCancelled() throws InterruptedException, BrokenBarrierException {
        ImprovedTestHarness improvedTestHarness = new ImprovedTestHarness();
        Runnable runnableLastOneSeconds = () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        long duration = improvedTestHarness.timeTasks(25, 1, runnableLastOneSeconds);
        long durationInSeconds = TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS);;
        assertThat(durationInSeconds, equalTo(1L));
    }

    @Test
    public void testThreadsCanBeFinishedWithinOneSecond() throws InterruptedException, BrokenBarrierException {
        ImprovedTestHarness improvedTestHarness = new ImprovedTestHarness();
        Runnable runnableLastOneSeconds = () -> {
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        long duration = improvedTestHarness.timeTasks(25, 1, runnableLastOneSeconds);
        long durationInSeconds = TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS);;
        assertThat(durationInSeconds, lessThan(1L));
    }



}
