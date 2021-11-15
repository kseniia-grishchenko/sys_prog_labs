package com.univ.automate;

public class StateMachine {
    private State initialState;
    private State currentState;

    public StateMachine(State initialState) {
        this.initialState = initialState;
        currentState = initialState;
    }

    public State switchState(char symbol) {
        State nextState = currentState.getNextStateByTransition(symbol);
        if(nextState != null){
            currentState = nextState;
        }
        return nextState;
    }

    public boolean canStop() {
        return currentState.getIsFinal();
    }

    public void reset() {
        currentState = initialState;
    }
}
