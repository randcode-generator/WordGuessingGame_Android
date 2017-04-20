package com.example.eric.wordguessinggame;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class WordManager implements Parcelable {
    private String[] words = {"ARGUE", "ARRAY", "FUN", "FUNNY"};
    String word = "";
    public WordManager() {
        setNewWord();
    }

    protected WordManager(Parcel in) {
        word = in.readString();
    }

    public static final Creator<WordManager> CREATOR = new Creator<WordManager>() {
        @Override
        public WordManager createFromParcel(Parcel in) {
            return new WordManager(in);
        }

        @Override
        public WordManager[] newArray(int size) {
            return new WordManager[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(word);
    }

    public void setNewWord() {
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
