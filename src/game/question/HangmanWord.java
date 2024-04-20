package game.question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HangmanWord {
    public String word;
    public boolean[] list; 

    private static final int MAX_CHAR = 15;
    public List<Character> listChar = new ArrayList<>();
    public boolean[] list1 = new boolean[MAX_CHAR];
    private int charCorrect = 0;

    public HangmanWord(String word) {
        this.word = word;
        list = new boolean[word.length()];
        for (int i = 0; i < word.length(); i++) {
            listChar.add(word.charAt(i));
        }
        randChar();
    }

    public void printInfo() {
        int len = word.length();
        for (int i = 0; i < len; i++) {
            if (list[i] == true) {
                System.out.print(word.charAt(i));
            } else {
                System.out.print("_");
            }
        }
        System.out.println("\n-------------Gợi ý chữ cái -------------");
        for (int i = 0; i < MAX_CHAR; i++) {
            if (list1[i] == false) {
                System.out.print(listChar.get(i) + " ");
            } else {
                System.out.print("* ");
            }
        }
        System.out.println("\n");
    }

    public void randChar() {
        int k = MAX_CHAR - word.length();
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            int n = rand.nextInt('z' - 'a');
            char t = (char) ('a' + n);
            listChar.add(t);
        }
        Collections.shuffle(listChar);
    }

    public boolean checkAnswers(char answers) {
        answers = Character.toLowerCase(answers);
        for (int i = 0; i < MAX_CHAR; i++) {
            if (answers == listChar.get(i) && list1[i] == false) {
                list1[i] = true;
                boolean check = false;
                for (int j = 0; j < word.length(); j++) {
                    if (word.charAt(j) == answers && list[j] == false) {
                        list[j] = true;
                        charCorrect++;
                        check = true;
                        break;
                    }
                }
                if (check == true) {
                    return true;
                } else 
                    return false;
            }
        }
        System.out.println("-------Chữ này không nằm trong gợi ý------");
        return false;
    }

    public boolean completedWord() {
        return charCorrect == word.length();
    }
}
