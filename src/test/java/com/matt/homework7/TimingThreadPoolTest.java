package com.matt.homework7;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;

public class TimingThreadPoolTest {

    /*
     * 3月 27, 2023 9:58:31 下午 com.matt.homework7.TimingThreadPool terminated
     * 信息: Terminated: avg time=935912968ns
    * */
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


        Thread.sleep(5000);
    }



    /*
     *  3月 27, 2023 9:58:49 下午 com.matt.homework7.TimingThreadPool terminated
     *  信息: Terminated: avg time=935223780ns
     * */
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

        Thread.sleep(5000);
    }

}
