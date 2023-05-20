package com.matt.homework11;

import com.matt.homework9.Grocery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NonfairLockGrocery implements Grocery {

    private final List<String> fruits = new ArrayList<>();
    private final List<String> vegetables = new ArrayList<>();

    Lock fruitsLock = new ReentrantLock();

    Lock vegetablesLock = new ReentrantLock();

    @Override
    public void addFruit(int index, String fruit) {

        fruitsLock.lock();
        try{
            fruits.add(index, fruit);
        }finally {
            fruitsLock.unlock();
        }

    }

    @Override
    public void addVegetable(int index, String vegetable) {

        vegetablesLock.lock();
        try {
            vegetables.add(index, vegetable);
        }finally {
            vegetablesLock.unlock();
        }

    }
}
