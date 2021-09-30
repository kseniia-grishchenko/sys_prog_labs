package com.company;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

class WordOccurrences {
    public static void main(String... args) {
        Scanner userPathInput = new Scanner(System.in);
        Scanner inputFile = null;
        Formatter outputFile = null;
        String filePath;
        String outputFilePath;
        String nextLine;
        Map<String, Integer> wordFreq = new HashMap<>();
        Set<String> words = null;
        TreeSet<String> sortedWords = null;

        try {
            System.out.println("Enter path for the input file: ");
            filePath = userPathInput.nextLine();
            outputFilePath = filePath.replaceAll(".txt", "_Out.txt");
            userPathInput.close();

            inputFile = new Scanner(Paths.get(filePath));
            outputFile = new Formatter(outputFilePath);


            while(inputFile.hasNext()) {
                nextLine = inputFile.nextLine();
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

            words = wordFreq.keySet();
            sortedWords = new TreeSet<>(words);

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
            }
        }

        catch(IOException | NoSuchElementException | IllegalStateException e) {
            e.printStackTrace();
        }

        finally {
            assert inputFile != null;
            inputFile.close();
            outputFile.close();
        }
    }
}
