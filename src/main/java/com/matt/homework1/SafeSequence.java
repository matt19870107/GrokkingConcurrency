package com.matt.homework1;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SafeSequence {
    @GuardedBy("this") private int value;

    public synchronized int getNext(){
        return value++;
    }

    public int getValue() {
        return value;
    }
}
