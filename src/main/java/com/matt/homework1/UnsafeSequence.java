package com.matt.homework1;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class UnsafeSequence {
    private int value;

    public int getNext(){
        return value++;
    }

    public int getValue() {
        return value;
    }

}
