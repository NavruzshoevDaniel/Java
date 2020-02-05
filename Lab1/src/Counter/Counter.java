package Counter;

import java.io.*;
import java.util.*;


public class Counter {
    private String nameFile;
    private HashMap<String, Integer> words;
    private int count;

    private void addWord(String word) {
        count++;
        words.merge(word, 1, Integer::sum);

    }

    private void patrition(String line) {

        StringBuilder word = new StringBuilder();
        for (char c : line.toCharArray()) {

            if (Character.isLetterOrDigit(c)) {
                word.append(c);
            } else if(!word.toString().equals("")){
                addWord(word.toString());
                word.setLength(0);
            }
        }

        if (!word.toString().equals("")) {
            addWord(word.toString());
        }
    }

    private void parse() {
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(nameFile);
            br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                patrition(line);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                System.err.format("IOException: %s%n", ex);
            }
        }
    }

    private Map<String, Integer> reverseSortWords() {
        LinkedHashMap<String, Integer> sortedWords = new LinkedHashMap<>();

        words.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedWords.put(x.getKey(), x.getValue()));

        return sortedWords;
    }


    public Counter(String nameFile) {
        this.nameFile = nameFile;
        words = new HashMap<String, Integer>();
        count = 0;
    }

    public void outCSV() {

        parse();
        Map<String, Integer> sortedWords = reverseSortWords();
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter("output.csv");

            for (Map.Entry<String, Integer> word : sortedWords.entrySet()) {
                fileWriter.write(word.getKey() + "," + word.getValue().toString() + ","
                        + (float) word.getValue() / count * 100 + System.lineSeparator());
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException ex) {
                System.err.format("IOException: %s%n", ex);
            }
        }

    }
}
