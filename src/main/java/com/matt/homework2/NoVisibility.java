package com.matt.homework2;

public class NoVisibility {
    public static boolean ready;
    public static int number;

    public static class ReaderThread extends Thread{
        public void run(){
            while (!ready){
                Thread.yield();
                System.out.println(number);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
            new ReaderThread().start();
            number = 42;
            ready = true;
            Thread.sleep(10000000);
    }
}
