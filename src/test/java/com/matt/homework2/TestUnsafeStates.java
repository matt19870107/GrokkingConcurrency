package com.matt.homework2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUnsafeStates {

    @Test
    void testStatesCanBeChanged() throws InterruptedException {
        UnsafeStates unsafeStates = new UnsafeStates();

        String[] states = unsafeStates.getStates();
        String[] orginalStates = Arrays.copyOf(states,states.length);

        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(()->{
            String[] currentStates = unsafeStates.getStates();
            currentStates[currentStates.length-1] = "AC";
            latch.countDown();
        });
        latch.await();
        assertThat(orginalStates, not(equalTo(unsafeStates.getStates())));
        executorService.shutdown();
    }

    @Test
    void testFinalStatesCanBeChanged() throws InterruptedException {
        UnsafeStates unsafeStates = new UnsafeStates();

        String[] finalStates = unsafeStates.getFinalStates();
        String[] orginalStates = Arrays.copyOf(finalStates,finalStates.length);

        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(()->{
            String[] currentStates = unsafeStates.getFinalStates();
            currentStates[currentStates.length-1] = "AC";
            latch.countDown();
        });
        latch.await();
        assertThat(orginalStates, not(equalTo(unsafeStates.getFinalStates())));
        executorService.shutdown();
    }
}
