package com.univ.automate;

public class StateMachineFactory {
    public static StateMachine operatorStateMachine() {
        State initial = new State(false);
        State plus = new State(true);
        State minus = new State(true);
        State mult = new State(true);
        State pow = new State(true);
        State div = new State(true);
        State amp = new State(true);
        State xor = new State(true);
        State line = new State(true);
        State eq = new State(true);
        State tw_div = new State(true);// //
        State wave = new State(true);// ~
        State lt = new State(false);// <
        State vlt = new State(true);// <<
        State gt = new State(false);// >
        State vgt = new State(true);// >>
        State perc = new State(true);// %

        lt.addTransition(new SymbolTransition('<', vlt)); //< to <<
        gt.addTransition(new SymbolTransition('>', vgt));// > to >>

        plus.addTransition(new SymbolTransition('=', eq));
        minus.addTransition(new SymbolTransition('=', eq));
        mult.addTransition(new SymbolTransition('=', eq));
        pow.addTransition(new SymbolTransition('=', eq));
        div.addTransition(new SymbolTransition('=', eq));
        amp.addTransition(new SymbolTransition('=', eq));
        xor.addTransition(new SymbolTransition('=', eq));
        line.addTransition(new SymbolTransition('=', eq));
        mult.addTransition(new SymbolTransition('*', pow));
        tw_div.addTransition(new SymbolTransition('=', eq));
        vlt.addTransition(new SymbolTransition('=', eq));
        vgt.addTransition(new SymbolTransition('=', eq));
        perc.addTransition(new SymbolTransition('=', eq));
        div.addTransition(new SymbolTransition('/', tw_div));// / to //

        initial.addTransition(new SymbolTransition('+', plus));
        initial.addTransition(new SymbolTransition('-', minus));
        initial.addTransition(new SymbolTransition('*', mult));
        initial.addTransition(new SymbolTransition('/', div));
        initial.addTransition(new SymbolTransition('&', amp));
        initial.addTransition(new SymbolTransition('^', xor));
        initial.addTransition(new SymbolTransition('|', line));
        initial.addTransition(new SymbolTransition('=', eq));
        initial.addTransition(new SymbolTransition('~', wave));
        initial.addTransition(new SymbolTransition('<', lt));
        initial.addTransition(new SymbolTransition('>', gt));
        initial.addTransition(new SymbolTransition('%', perc));

        return new StateMachine(initial);
    }

    public static StateMachine dotStateMachine() {
        State initial = new State(false);
        State dot = new State(true);
        initial.addTransition(new SymbolTransition('.', dot));
        return new StateMachine(initial);
    }

    public static StateMachine comparisonOperatorStateMachine() {
        State initial = new State(false);
        State lt = new State(true);
        State gt = new State(true);
        State nt = new State(false);
        State eq = new State(true); // <=, >=, ==,!=
        State tw_eq = new State(true);
        State eq2 = new State(false); // =

        lt.addTransition(new SymbolTransition('=', eq));
        gt.addTransition(new SymbolTransition('=', eq));
        nt.addTransition(new SymbolTransition('=', eq));
        eq2.addTransition(new SymbolTransition('=', tw_eq));

        initial.addTransition(new SymbolTransition('<', lt));
        initial.addTransition(new SymbolTransition('>', gt));
        initial.addTransition(new SymbolTransition('!', nt));
        initial.addTransition(new SymbolTransition('=', eq2));

        return new StateMachine(initial);
    }

    public static StateMachine bracketStateMachine() {
        State initial = new State(false);
        State lb = new State(true);
        State rb = new State(true);
        State lfb = new State(true);
        State rfb = new State(true);
        State lsb = new State(true);
        State rsb = new State(true);

        initial.addTransition(new SymbolTransition('(', lb));
        initial.addTransition(new SymbolTransition(')', rb));
        initial.addTransition(new SymbolTransition('{', lfb));
        initial.addTransition(new SymbolTransition('}', rfb));
        initial.addTransition(new SymbolTransition('[', lsb));
        initial.addTransition(new SymbolTransition(']', rsb));

        return new StateMachine(initial);
    }

    public static StateMachine separatorStateMachine() {
        State initial = new State(false);
        State tp = new State(true);
        State c = new State(true);
        State sem = new State(true);

        initial.addTransition(new SymbolTransition(':', tp));
        initial.addTransition(new SymbolTransition(',', c));
        initial.addTransition(new SymbolTransition(';', sem));

        return new StateMachine(initial);
    }

    public static StateMachine numberStateMachine() {
        State initial = new State(false);
        State digit = new State(true);
        TransitionFunction transitionFunction = Character::isDigit;
        initial.addTransition(new FuncTransition(transitionFunction, digit));
        digit.addTransition(new FuncTransition(transitionFunction, digit));
        return new StateMachine(initial);
    }

    public static StateMachine identifierStateMachine() {
        State initial = new State(false);
        State identifier = new State(true);
        TransitionFunction startNameTransition = (c) -> c == '_' || Character.isLetter(c);
        initial.addTransition(new FuncTransition(startNameTransition, identifier));
        TransitionFunction nameTransition = (c) -> c == '_' || Character.isLetter(c) || Character.isDigit(c);
        identifier.addTransition(new FuncTransition(nameTransition, identifier));
        return new StateMachine(initial);
    }

    public static StateMachine doubleQuoteStringStateMachine() {
        State initial = new State(false);
        State simple = new State(false);
        State end = new State(true);
        State slash = new State(false);
        TransitionFunction strSymbols = (c) -> c != '\"' && c != '\\';
        TransitionFunction notSlash = (c) -> c != '\\';

        simple.addTransition(new FuncTransition(strSymbols, simple));
        simple.addTransition(new SymbolTransition('\\', slash));
        slash.addTransition(new SymbolTransition('\\', slash));
        slash.addTransition(new FuncTransition(notSlash, simple));
        simple.addTransition(new SymbolTransition('\"', end)); // end string
        initial.addTransition(new SymbolTransition('\"', simple)); // start string

        return new StateMachine(initial);
    }

    public static StateMachine singleQuoteStringStateMachine() {
        State initial = new State(false);
        State simple = new State(false);
        State end = new State(true);
        State slash = new State(false);
        TransitionFunction strSymbols = (c) -> c != '\'' && c != '\\';
        TransitionFunction notSlash = (c) -> c != '\\';

        simple.addTransition(new FuncTransition(strSymbols, simple));
        simple.addTransition(new SymbolTransition('\\', slash));
        slash.addTransition(new SymbolTransition('\\', slash));
        slash.addTransition(new FuncTransition(notSlash, simple));
        simple.addTransition(new SymbolTransition('\'', end)); // end string
        initial.addTransition(new SymbolTransition('\'', simple)); // start string

        return new StateMachine(initial);
    }

    public static StateMachine whitespaceStateMachine() {
        State initial = new State(false);
        State whiteSpace = new State(true);
        TransitionFunction funcTransition = (c) -> c == ' ' || c == '\t';
        whiteSpace.addTransition(new FuncTransition(funcTransition, whiteSpace));
        initial.addTransition(new FuncTransition(funcTransition, whiteSpace));
        return new StateMachine(initial);
    }

    public static StateMachine newLineStateMachine() {
        State initial = new State(false);
        State newLine = new State(true);
        initial.addTransition(new SymbolTransition('\n', newLine));
        return new StateMachine(initial);
    }
}
