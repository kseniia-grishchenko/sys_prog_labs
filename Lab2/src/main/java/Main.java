import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.format("Please enter the name of file with DFA: ");
            String pathname = scanner.next();
            DFA dfa = new DFA(pathname);

            Set<Character> notAcceptableLetters = dfa.getNotAcceptableLetters();
            System.out.println("Not acceptable letters:");
            printIterableInline(notAcceptableLetters, "\t", " ");
        } catch (FileNotFoundException ex) {
            System.out.println("Invalid file pathname.");
        }
}

    private static void printIterableInline(Set<Character> iterable, String indent, String separator) {
        System.out.print(indent);
        for (Character t : iterable) {
            System.out.print(t + separator);
        }
        System.out.println();
    }

    static Scanner getScanner(String pathname) throws FileNotFoundException {
        File file = new File(pathname);

        if (!file.exists()) {
            System.out.format("File '%s' does not exist.%n", pathname);
        }

        if (!file.canRead()) {
            System.out.format("Cannot read file '%s'.%n", pathname);
        }

        return new Scanner(file);
    }
}
