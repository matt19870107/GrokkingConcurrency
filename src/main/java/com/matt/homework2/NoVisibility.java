package com.matt.homework2;

public class NoVisibility {
    public static boolean ready;
    public static int number;

    public static class ReaderThread extends Thread{
        public void run(){
            while (!ready){
                System.out.println("++ " + number);
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread reader = new ReaderThread();
        reader.start();
        Thread.sleep(1000);
        number = 42;
        ready = true;
        for(int i=1; i<1000; i++){
            number = + i;
        }
        System.out.println("Main Thread is done!");

    }
}
