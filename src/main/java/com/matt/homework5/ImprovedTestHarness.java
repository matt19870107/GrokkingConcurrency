package com.matt.homework5;


import java.util.concurrent.*;
import java.util.logging.Logger;

public class ImprovedTestHarness {

    private static final Logger logger = Logger.getLogger("ImprovedTestHarness");

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
                        ExecutorService singlePool = Executors.newSingleThreadExecutor();
                        try{
                            future = singlePool.submit(task);
                            future.get(timeoutInSeconds,TimeUnit.SECONDS);
                        } catch (ExecutionException e) {
                            // Will be cancelled too.
                            logger.fine("Cancelled due to exception");
                        } catch (TimeoutException e) {
                            //Will be cancelled finally
                            logger.fine("Cancelled due to exception");
                        } finally {
                            endBarrier.await();
                            future.cancel(true);
                            singlePool.shutdown();
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
