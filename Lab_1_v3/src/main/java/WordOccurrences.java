import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

class WordOccurrences {
    Scanner inputFile = null;
    Formatter outputFile = null;

    public HashMap<String, Integer> main() throws Exception {
        String filePath = getPath();

        String outputFilePath = filePath.replaceAll(".txt", "_Out.txt");
        outputFile = new Formatter(outputFilePath);

        Map<String, Integer> wordFreq =  process(filePath);
        HashMap<String, Integer> dict = groupWords(wordFreq);
        inputFile.close();
        outputFile.close();
        return dict;
    }

    public String getPath() throws IOException {
        System.out.println("Enter file path:");
        Scanner input = new Scanner(System.in);
        String filePath = input.nextLine();
        input.close();
        return filePath;
    }

    public Map<String, Integer> process(String path) throws IOException, NoSuchFieldException {
        try {
            inputFile = new Scanner(Paths.get(path));
            Map<String, Integer> wordFreq = new HashMap<>();

            while(inputFile.hasNext()) {
                String nextLine = inputFile.nextLine();
                if(nextLine.isEmpty()) {
                    nextLine = " ";
                }

                String[] tokens = nextLine.split(" ");

                for(String token : tokens) {
                    String word = token.toLowerCase();
                    word = word.replaceAll("[^A-Za-zА-Яа-я\\і\\І]", "");

                    if(wordFreq.containsKey(word)) {
                        int count = wordFreq.get(word);
                        wordFreq.put(word, count + 1);
                    }
                    else {
                        wordFreq.put(word, 1);
                    }
                }
            }
            return wordFreq;
        }
        catch (NoSuchFileException e) {
            throw new NoSuchFieldException("File not found");
        }
    }

    public HashMap<String, Integer> groupWords(Map<String, Integer> wordFreq) {
        Set<String> words = wordFreq.keySet();
        TreeSet<String> sortedWords = new TreeSet<>(words);

        HashMap<String, Integer> dict = new HashMap<>();

        int max = 30;
        for(String word : sortedWords) {
            max = Math.max(word.length(), max);
        }
        String format = "%-" + max + "s\t%s%n";

        System.out.printf(format, "Word", "Frequency");
        outputFile.format(format, "Word", "Frequency");
        for(String word : sortedWords) {
            System.out.printf(format, word, wordFreq.get(word));
            outputFile.format(format, word, wordFreq.get(word));
            dict.put(word, wordFreq.get(word));
        }
        return dict;
    }
}
