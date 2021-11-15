package com.univ.automate;

public interface Transition {
    boolean isPossible(char c);
    State getState();
}
