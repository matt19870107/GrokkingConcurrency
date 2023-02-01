package com.matt.homework1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UnSafeSequenceTest {

    @Test
    void testUnSafeSequenceWithMultipleThreads(){

        UnsafeSequence sequence = new UnsafeSequence();

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int value = 0;
            for(int i=1; i <= 20; i++) {
                value = sequence.getNext();
                try {
                    Thread.sleep(100);// Do something
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return value;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int value = 0;
            for(int i=1; i <= 20; i++) {
                value = sequence.getNext();
                try {
                    Thread.sleep(100);// Do something
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            return value;
        });
        List<CompletableFuture<Integer>> futures = List.of(future1, future2);
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();

        assertNotEquals(40, sequence.getValue());
    }

}
