package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import game.question.HangmanWord;

public class Hangman extends GameInterface {
    private List<String> list = new ArrayList<>();

    public Hangman() {
        point = 0;
        health = 3;
        path = "resources/data/HangmanData.txt";
    }

    public void insertFromFile() {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bf = new BufferedReader(fr);
            String line;

            while ((line = bf.readLine()) != null) {
                list.add(line);
            }
            bf.close();
            fr.close();
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
        insertFromFile();
        System.out.println("--------------NEW GAME--------------");
        Scanner input = new Scanner(System.in);
        int index = 0;
        while (true) {
            HangmanWord x = new HangmanWord(this.randWord());
            index ++;
            int health_word = 3;
            while (x.completedWord() == false) {
                System.out.print("\n---Word " + index + " :\t");
                x.printInfo();
                System.out.print("Nhập chữ cái bạn đoán: ");
                String s = input.nextLine();
                char answers = s.charAt(0);
                if (x.checkAnswers(answers) == false) {
                    health_word--;
                } 
                if (health_word == 0) {
                    System.out.println("Hết lượt đoán. Đáp án : " + x.word);
                    printInfo();
                    break;
                }
            }
            if (x.completedWord() == true) {
                System.out.println("-------Correct-------");
                point += 1;
            } else {
                health -= 1;
            }
            System.out.print("\n\n\n");
            if (isEndGame()) {
                printEndGame();
                break;
            }
        }
    }

    private String randWord() {
        int n = list.size();
        Random rand = new Random();
        int index = rand.nextInt(n - 1);
        return list.get(index);
    }

}
