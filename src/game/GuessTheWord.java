package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import game.question.WordGuess;

public class GuessTheWord extends GameManagement implements GameInterface {

    private static final int MAX = 33482;
    private String path = "resources/data/WordList.txt";
    private List<WordGuess> dataList = new ArrayList<>();

    public GuessTheWord() {
        point = 0;
        health = 3;
    }

    public void insertFromFile() {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bf = new BufferedReader(fr);
            String line;

            while ((line = bf.readLine()) != null) {
                String[] s = line.split("\t");
                dataList.add(new WordGuess(s[0], s[1]));
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
        this.insertFromFile();
        System.out.println("------------------NEW GAME----------------------");
        while (true) {
            WordGuess wordGuess = randWord();
            wordGuess.printWordGuess();
            Scanner input = new Scanner(System.in);
            String s;
            System.out.print(" Nhập chữ cái bạn đoán: ");
            while ((s = input.nextLine()).length() > 1) {
                System.out.print(" Nhập lại chữ cái bạn đoán: ");
            }
            if (wordGuess.checkAnswers(s.charAt(0)) == true) {
                point += 10;
                System.out.println("--------------CORRECT--------------" );
            } else {
                health -= 1;
                System.out.println("--------------INCORRECT------------" );
            }
            printInfo();
            wordGuess.printWord();
            // this.printWord();
            System.out.println("\n\n\n\n");
            if (isEndGame() == true) {
                if (health == 0) {
                    System.out.println("--------------YOU LOSE------------" );
                } else {
                     System.out.println("--------------YOU WIN------------" );
                }
                break;
            }
        }
        playAgain();
        System.out.println("\n\n\n\n");
    }

    private boolean isEndGame() {
        if (point == 10 || health == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void printInfo() {
        System.out.println("------------Point:  " + point + "------------" );
        System.out.println("------------Health: " + health + "------------" );
    }

    public WordGuess randWord() {
        Random rand = new Random();
        int index = rand.nextInt(MAX);
        return dataList.get(index);
    }

    private void playAgain() {
        boolean check = true;
        Scanner input = new Scanner(System.in);
        while (check) {
            System.out.println("Do you want to play again? [Y/n]\t");
            char userInput = input.next().charAt(0);
            if (userInput == 'Y' || userInput == 'y') {
                point = 0;
                health = 3;
                start();
            } else if (userInput == 'N' || userInput == 'n') {
                check = false;
            }
        }
    }
}