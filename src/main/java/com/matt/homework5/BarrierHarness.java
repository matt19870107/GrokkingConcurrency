package com.matt.homework5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

public class BarrierHarness {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException, BrokenBarrierException {

        AtomicLong start = new AtomicLong();
        AtomicLong end = new AtomicLong();

        CyclicBarrier waitAllBarrier = new CyclicBarrier(2);

        Runnable startRunnable = ()-> start.set(System.nanoTime());
        Runnable endRunnable = ()-> {
            end.set(System.nanoTime());
            try {
                waitAllBarrier.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        };

        CyclicBarrier startBarrier = new CyclicBarrier(nThreads, startRunnable);
        CyclicBarrier endBarrier = new CyclicBarrier(nThreads, endRunnable);

        for(int i = 0; i< nThreads; i++){
            Thread t = new Thread(){
                public void run(){
                    try{
                        startBarrier.await();
                        try{
                           task.run();
                        } finally {
                            endBarrier.await();
                        }
                    }catch (BrokenBarrierException e) {
                        throw new RuntimeException(e);
                    }catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            t.start();
        }
        waitAllBarrier.await();
        return end.get() - start.get();
    }
}
