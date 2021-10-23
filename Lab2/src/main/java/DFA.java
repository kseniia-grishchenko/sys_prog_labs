import java.io.FileNotFoundException;
import java.util.*;

class DFA {
    Set<Character> alphabet;

    Set<Integer> states;

    // states.contains(startState);
    Integer startState;

    // states.containsAll(finalStates);
    Set<Integer> finalStates;

    // states.containsAll(transitionFunctions.keySet();
    // alphabet.containsAll(new HashSet<Character>(transitionFunctions.values()));
    Map<Integer, Map<Character, Integer>> transitionFunction;

    private DFA(Scanner fileScanner) {
        String preAlphabet = "abcdefghijklmnopqrstuvwxyz";
        int alphabetSize = fileScanner.nextInt(); // <= 26
        alphabet = new HashSet<>();
        for (int i = 0; i < alphabetSize; ++i) {
            alphabet.add(preAlphabet.charAt(i));
        }

        int numberOfStates = fileScanner.nextInt();
        states = new HashSet<>(numberOfStates);
        for (int i = 0; i < numberOfStates; ++i) {
            states.add(i);
        }

        startState = fileScanner.nextInt();

        int numberOfFinalStates = fileScanner.nextInt();
        finalStates = new HashSet<>(numberOfFinalStates);
        for (int i = 0; i < numberOfFinalStates; ++i) {
            finalStates.add(fileScanner.nextInt());
        }

        transitionFunction = new HashMap<>(numberOfStates);
        for (Integer state : states) {
            transitionFunction.put(state, new HashMap<>());
        }

        while (fileScanner.hasNext()) {
            Integer fromState = fileScanner.nextInt();
            Character viaLetter = fileScanner.next().charAt(0);
            Integer toState = fileScanner.nextInt();
            transitionFunction.get(fromState).put(viaLetter, toState);
        }
    }

    DFA(String pathname) throws FileNotFoundException {
        this(Main.getScanner(pathname));
    }


    private Set<Character> getAcceptableLetters() {
        Set<Character> acceptableLetters = new HashSet<>();
        for (Character viaLetter : alphabet) {
            Integer toState = transitionFunction.get(startState).get(viaLetter);
            if (finalStates.contains(toState)) {
                acceptableLetters.add(viaLetter);
            }
        }
        return acceptableLetters;
    }

    Set<Character> getNotAcceptableLetters() {
        Set<Character> notAcceptableLetters = new HashSet<>(alphabet);
        notAcceptableLetters.removeAll(getAcceptableLetters());
        return notAcceptableLetters;
    }
}