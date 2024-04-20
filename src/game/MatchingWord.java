package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

public class MatchingWord extends GameManagement implements GameInterface {
    private static final int MAX = 33482;
    private String path = "resources/data/HangmanData.txt";
    private ArrayList<String> charStartsList;
    private Hashtable<Character, ArrayList<String>> charStarts = new Hashtable<>();
    public Hashtable<String, Integer> answersList = new Hashtable<>();
    private int point;
    public Character characterKey = 'b';

    public MatchingWord() {
        point = 0;
        health = 3;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void increasePoint() {
        point++;
    }

    public void decreaseHealth() {
        health--;
    }

    public void insertFromFile() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bf.readLine()) != null) {
                char x = line.charAt(0);

                // ktra xem trong table đã có chưa.
                if (charStarts.containsKey(x)) {
                    charStarts.get(x).add(line);
                } else {
                    charStartsList = new ArrayList<>();
                    charStartsList.add(line);
                    charStarts.put(x, charStartsList);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("không tìm thấy file");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file");
        } catch (Exception e) {
            System.out.println("Lỗi khác với file");
        }
    }

    @Override
    public void start() {
        this.insertFromFile();
        Scanner input = new Scanner(System.in);
        System.out.println("------------------Matching Word --------------");
        System.out.println("------------------  NEW GAME --------------");

        String wordStart = this.randomWord();
        characterKey = wordStart.charAt(wordStart.length() - 1);
        System.out.printf("%s\n", wordStart);
        answersList.put(wordStart, 1);
        while (true) {
            System.out.print("\t\t");
            String s = input.nextLine();
            s = s.toLowerCase();
            if (checkAnswers(s) == true) {
                answersList.put(s, 1);
                characterKey = s.charAt(s.length() - 1);
            }
            if (isEndGame() == true) {
                if (point == 10)
                    System.out.println("--------------YOU WIN !---------");
                else
                    System.out.println("--------------YOU LOSE !---------");
                break;
            }
            String wordContinue = this.randomWord();
            characterKey = wordContinue.charAt(wordContinue.length() - 1);
            System.out.printf("%s\n", wordContinue);
        }
    }


    public boolean checkAnswers(String s) {
        if (s.charAt(0) != characterKey) {
            System.out.println("----------------Từ này không bắt đầu bằng chữ ----------------" + characterKey);
            health -= 1;
            return false;
        }
        // kiểm tra từ này đã được dùng chưa
        if (answersList.containsKey(s)) {
            System.out.println("----------------Từ này đã được dùng rồi!----------------");
            health -= 1;
            return false;
        }

        // kiểm tra từ này có trong từ điển hay không
        charStartsList = charStarts.get(characterKey);
        if (charStartsList.contains(s)) {
            point += 1;
            return true;
        }
        System.out.println("----------------Không có trong từ điển----------------");
        health -= 1;
        return false;
    }

    public boolean isEndGame() {
        if (point == 10 || health == 0) {
            return true;
        }
        return false;
    }

    /**
     * random 1 từ bắt đầu bằng chữ cái.
     */
    public String randomWord() {
        if (characterKey == null || Character.isLetter(characterKey) == false) {
            return null;
        }
        if (charStarts.containsKey(characterKey)) {
            charStartsList = charStarts.get(characterKey);
            Random rand = new Random();
            int size = charStartsList.size();
            int index = rand.nextInt(size);
            return charStartsList.get(index);
        }
        System.out.println("có j đó sai");
        return null;
    }

}