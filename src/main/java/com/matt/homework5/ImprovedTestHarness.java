package com.matt.homework5;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

public class ImprovedTestHarness {

    private static final Logger logger = Logger.getLogger(ImprovedTestHarness.class);

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

    public long timeTasks(int nThreads, int timeoutInSeconds, final Runnable task) throws BrokenBarrierException, InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        try{
            CyclicBarrier startBarrier = new CyclicBarrier(nThreads + 1);
            CyclicBarrier endBarrier = new CyclicBarrier(nThreads + 1);

            for(int i = 0; i< nThreads; i++){
                Runnable runnable = () -> {
                    try{
                        startBarrier.await();
                        Future<?> future = null;
                        try{
                            future = Executors.newSingleThreadExecutor().submit(task);
                            future.get(timeoutInSeconds,TimeUnit.SECONDS);
                        } catch (ExecutionException e) {
                            // Will be cancelled too.
                            logger.error("Cancelled due to exception", e);
                        } catch (TimeoutException e) {
                            //Will be cancelled finally
                            logger.error("Cancelled due to timeout", e);
                        } finally {
                            endBarrier.await();
                            future.cancel(true);

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
