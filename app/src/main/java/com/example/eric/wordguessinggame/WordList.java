package com.example.eric.wordguessinggame;

import java.util.Random;

public class WordList {
    private String[] words = {"ARGUE", "ARRAY", "FUN", "FUNNY"};
    String word = "";
    public WordList() {
        Random r = new Random();
        int index = r.nextInt(words.length);
        word = words[index];
    }

    public String getRandomWord() {
        return word;
    }

    public char[] getRandomWordAsArray() {
        char []s = new char[word.length()];
        for(int i = 0; i < word.length(); i++) {
            s[i] = word.charAt(i);
        }
        return s;
    }
}
