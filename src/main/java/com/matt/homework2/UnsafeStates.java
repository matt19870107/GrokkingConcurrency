package com.matt.homework2;

public class UnsafeStates {
    private String[] states = new String[]{"AK", "AL", "AD"};

    private final String[] finalStates = new String[]{"AK", "AL", "AD"};

    public String[] getStates(){return states;}

    public String[] getFinalStates(){return finalStates;}
}
