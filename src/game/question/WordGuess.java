package game.question;

import java.util.Random;

import word.Word;

public class WordGuess extends Word {
    private int position;

    public WordGuess() {
        super();
        randomPosition();
    }

    public WordGuess(String word, String explain) {
        super(word, explain);
        randomPosition();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void randomPosition() {
        String word = super.getWordTarget();
        int n = word.length();
        Random rand = new Random();
        if (n <= 1) {
            position = 0;
        } else {
            position = rand.nextInt(n - 1);
        }
    }

    public boolean checkAnswers(char answer) {
        char correctAnswers = Character.toUpperCase(super.getWordTarget().charAt(position));
        answer = Character.toUpperCase(answer);
        //System.out.println(answer + "    " + correctAnswers);
        if (answer == correctAnswers) {
            return true;
        }
        return false;
    } 

    public void printWordGuess() {
        String s = super.getWordTarget();
        String wordGuess = s.substring(0, position) + "_" + s.substring(position + 1, s.length());
        System.out.println("----------------" + wordGuess + "------------");
    }

    public String wordGuess() {
        String s = super.getWordTarget();
        String wordGuess = s.substring(0, position) + "_" + s.substring(position + 1, s.length());
        return wordGuess;
    }

    public void printWord() {
        System.out.println(super.getWordTarget() + "  :\t\t" + super.getWordExplain());
    }
}