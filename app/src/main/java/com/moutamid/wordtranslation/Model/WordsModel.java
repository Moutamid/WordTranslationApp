package com.moutamid.wordtranslation.Model;

public class WordsModel {

    private int no;
    private String word;
    private String letter;
    private String  timestamp;
    private int interval;
    private boolean seen;
    
    public WordsModel(){

    }

    public WordsModel(int no, String word, String letter, String timestamp, int interval, boolean seen) {
        this.no = no;
        this.word = word;
        this.letter = letter;
        this.timestamp = timestamp;
        this.interval = interval;
        this.seen = seen;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
