package com.matt.homework8;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class OrderingDeadlock {

    private static final int PARTIES = 2;
    private final CyclicBarrier startBarrier = new CyclicBarrier(PARTIES);
    private final List<LockObject> lockObjects = List.of(
            new LockObject(),
            new LockObject(),
            new LockObject()
    );

    private final List<LockObject> reversedLockObjects = Lists.reverse(lockObjects);

    private void lock(List<LockObject> lockObjects, int index) throws BrokenBarrierException, InterruptedException {
        if(index < lockObjects.size()){
            synchronized (lockObjects.get(index)){
                if(index == 0) startBarrier.await();
                lock(lockObjects, ++index);
            }
        }
    }

    public void lockAll(){

        Runnable runnable1 = () -> {
            try {
                lock(lockObjects, 0);
            } catch (BrokenBarrierException e) {
                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable runnable2 = () -> {
            try {
                lock(reversedLockObjects, 0);
            } catch (BrokenBarrierException e) {
                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        new Thread(runnable1).start();
        new Thread(runnable2).start();

    }

    public static void main(String[] args){
        OrderingDeadlock orderingDeadlock = new OrderingDeadlock();
        orderingDeadlock.lockAll();
    }

    public static class LockObject{
    }
}
