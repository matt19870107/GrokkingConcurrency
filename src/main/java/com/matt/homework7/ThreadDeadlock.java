package com.matt.homework7;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadlock {
    static ExecutorService exec = Executors.newSingleThreadExecutor();
    public static class RenderPageTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit(() -> "header");
            footer = exec.submit(() -> "footer");
            String page = "page";
            return header.get() + page + footer.get();

        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService exec1 = Executors.newSingleThreadExecutor();

        String str = exec.submit(new ThreadDeadlock.RenderPageTask()).get();
        System.out.println(str);
    }
}
