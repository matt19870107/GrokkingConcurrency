package com.matt.homework5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImprovedTestHarness {
    public long timeTasks(int nThreads, final Runnable task) throws BrokenBarrierException, InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        try{
            CyclicBarrier startBarrier = new CyclicBarrier(nThreads + 1);
            CyclicBarrier endBarrier = new CyclicBarrier(nThreads + 1);

            for(int i = 0; i< nThreads; i++){
                Runnable runnable = () -> {
                    try{
                        startBarrier.await();
                        try{
                            task.run();
                        } finally {
                            endBarrier.await();
                        }
                    }catch (InterruptedException | BrokenBarrierException e) {
                        Thread.currentThread().interrupt();
                    }
                };
                pool.execute(runnable);
            }

            startBarrier.await();
            long start = System.nanoTime();
            endBarrier.await();
            long end = System.nanoTime();
            return end - start;
        }finally {
            pool.shutdown();
        }

    }
}
