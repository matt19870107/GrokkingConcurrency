package com.matt.homework9;

import java.util.ArrayList;
import java.util.List;

public class ImprovedGrocery implements Grocery{

    private final List<String> fruits = new ArrayList<>();
    private final List<String> vegetables = new ArrayList<>();
    @Override
    public void addFruit(int index, String fruit) {
        synchronized (fruits) {
            fruits.add(index, fruit);
        }
    }

    @Override
    public void addVegetable(int index, String vegetable) {
        synchronized (vegetables) {
            vegetables.add(index, vegetable);
        }
    }
}
