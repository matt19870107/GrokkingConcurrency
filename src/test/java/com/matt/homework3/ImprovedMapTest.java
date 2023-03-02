package com.matt.homework3;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class ImprovedMapTest {
    @Test
    void testPutIsThreadSafe(){
        ImprovedMap<String, Integer> improvedMap = new ImprovedMap<>();
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> randomPut50000Values(improvedMap));
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> randomPut50000Values(improvedMap));
        List<CompletableFuture<Void>> futures = List.of(future1, future2);
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();

        assertThat(improvedMap.size(), equalTo(100000));
    }

    private void randomPut50000Values(ImprovedMap<String, Integer> map){
        IntStream.range(0,50000).forEach(number -> map.put(UUID.randomUUID().toString(), number));
    }
}
