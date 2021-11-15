package com.univ.automate;

import com.univ.helper.Pair;

public class Automate {
    private StateMachine stateMachine;

    public Automate(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    public Pair<Integer, Integer> match(String text, int fromPos) {
        stateMachine.reset();
        int curPos = fromPos;
        while (curPos < text.length() && stateMachine.switchState(text.charAt(curPos)) != null) {
            curPos++;
        }
        if (stateMachine.canStop()) {
            return new Pair<>(fromPos, curPos);
        } else {
            return new Pair<>(null, null);
        }
    }
}
