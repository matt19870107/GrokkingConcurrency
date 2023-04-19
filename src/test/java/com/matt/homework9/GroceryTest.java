package com.matt.homework9;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class GroceryTest {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(GroceryTest.class.getSimpleName())
                .forks(1)
                .measurementIterations(4)
                .warmupIterations(3)
                .resultFormat(ResultFormatType.JSON)
                .result("./result.json")
                .build();

        new Runner(opt).run();
    }


    @Benchmark
    public void addToSynchronizedGrocery() throws InterruptedException {
        SynchronizedGrocery grocery = new SynchronizedGrocery();
        Thread addFruitsThread = new Thread(() -> IntStream.range(0, 1_000_000).forEach(number -> grocery.addFruit(number, "Apple " + number)));
        Thread addVegetablesThread = new Thread(() -> IntStream.range(0, 1_000_000).forEach(number -> grocery.addVegetable(number, "Cabbage " + number)));

        addFruitsThread.start();
        addVegetablesThread.start();

        addFruitsThread.join();
        addVegetablesThread.join();
    }

    @Benchmark
    public void addToImprovedGrocery() throws InterruptedException {
        ImprovedGrocery improvedGrocery = new ImprovedGrocery();
        Thread addFruitsThread = new Thread(() -> IntStream.range(0, 1_000_000).forEach(number -> improvedGrocery.addFruit(number, "Apple " + number)));
        Thread addVegetablesThread = new Thread(() -> IntStream.range(0, 1_000_000).forEach(number -> improvedGrocery.addVegetable(number, "Cabbage " + number)));

        addFruitsThread.start();
        addVegetablesThread.start();

        addFruitsThread.join();
        addVegetablesThread.join();
    }
}
