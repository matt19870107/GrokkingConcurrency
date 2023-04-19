package com.matt.homework7;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;

public class TimingThreadPoolTest {

    @Test
    void testStartCorePoolThreadWhenNewTaskComing()throws InterruptedException, BrokenBarrierException {
        ImprovedTestHarness improvedTestHarness = new ImprovedTestHarness();
        Runnable runnableLastOneSeconds = () -> {
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        improvedTestHarness.timeTasks(100, 1, runnableLastOneSeconds, false);

        //Thread.sleep(5000);
    }



    @Test
    void testStartCorePoolPreThreadTaskComing()throws InterruptedException, BrokenBarrierException {
        ImprovedTestHarness improvedTestHarness = new ImprovedTestHarness();
        Runnable runnableLastOneSeconds = () -> {
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        improvedTestHarness.timeTasks(100, 1, runnableLastOneSeconds, true);

        //Thread.sleep(5000);
    }

}
