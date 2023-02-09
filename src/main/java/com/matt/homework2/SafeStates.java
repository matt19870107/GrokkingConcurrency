package com.matt.homework2;

import java.util.Arrays;

public class SafeStates {
    private String[] states = new String[]{"AK", "AL", "AD"};
    
    public String[] getStates(){return Arrays.copyOf(states, states.length);}

}
