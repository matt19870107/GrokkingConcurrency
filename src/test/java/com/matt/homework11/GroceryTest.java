package com.matt.homework11;

import com.matt.homework9.Grocery;
import com.matt.homework9.ImprovedGrocery;
import com.matt.homework9.SynchronizedGrocery;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


public class GroceryTest {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(com.matt.homework9.GroceryTest.class.getSimpleName())
                .forks(1)
                .measurementIterations(4)
                .warmupIterations(3)
                .resultFormat(ResultFormatType.JSON)
                .result("./src/test/java/com/matt/homework11/result.json")
                .build();

        new Runner(opt).run();
    }


    @Benchmark
    public void addToFairLockGrocery() throws InterruptedException {
        Grocery grocery = new FairLockGrocery();
        for(int i = 0; i < 5; i++) {
            Thread addFruitsThread = new Thread(() -> IntStream.range(0, 100_000).forEach(number -> grocery.addFruit(number, "Apple " + number)));
            Thread addVegetablesThread = new Thread(() -> IntStream.range(0, 100_000).forEach(number -> grocery.addVegetable(number, "Cabbage " + number)));
            addFruitsThread.start();
            addVegetablesThread.start();

            addFruitsThread.join();
            addVegetablesThread.join();
            Thread.sleep(40);
        }

    }

    @Benchmark
    public void addToNonfairLockGrocery() throws InterruptedException {
        Grocery grocery = new NonfairLockGrocery();
        for(int i = 0; i < 5; i++) {
            Thread addFruitsThread = new Thread(() -> IntStream.range(0, 100_000).forEach(number -> grocery.addFruit(number, "Apple " + number)));
            Thread addVegetablesThread = new Thread(() -> IntStream.range(0, 100_000).forEach(number -> grocery.addVegetable(number, "Cabbage " + number)));

            addFruitsThread.start();
            addVegetablesThread.start();

            addFruitsThread.join();
            addVegetablesThread.join();
            Thread.sleep(40);
        }

    }


}
