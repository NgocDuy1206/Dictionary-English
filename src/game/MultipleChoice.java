package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import game.question.Question;

public class MultipleChoice extends GameManagement implements GameInterface {
    private static final int MAX = 33482;
    private String path = "resources/data/MultipleChoiceData.txt";
    private List<Question> questionList = new ArrayList<>();

    public MultipleChoice() {
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
    public void insertQuestionFromFile() {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bf = new BufferedReader(fr);
            String line;

            while ((line = bf.readLine()) != null) {
                String[] s = line.split("\\s+");

                String answers = s[0];

                String question = "";
                for (int i = 1; i < s.length; i++) {
                    String k = s[i].substring(0, s[i].length() - 1);
                    if (answers.equalsIgnoreCase(s[i]) || answers.equalsIgnoreCase(k)) {
                        question += "___" + " ";
                    } else {
                        question += s[i] + " ";
                    }
                }
                questionList.add(new Question(question, answers));
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

    public List<String> randomAnswers(String answer) {
        List<String> list = new ArrayList<>();
        Random rand = new Random();
        list.add(answer);
        while (list.size() < 5) {
            int index = rand.nextInt(MAX);
            list.add(questionList.get(index).getCorrectAnswers());
        }

        //đảo đáp án.
        Collections.shuffle(list); 
        return list;
    }

    private void printInfo() {
        System.out.println("----------Your point:  " + this.point + " --------");
        System.out.println("----------Your health: " + this.health + " --------" + "\n\n");
    }

    public void start() {
        insertQuestionFromFile();
        Scanner input = new Scanner(System.in);
        int stt = 1;
        System.out.println("-------------NEW GAME ------------");

        while (true) {
            Question x = this.randomQuestion();
            x.setListAnswers(randomAnswers(x.getCorrectAnswers()));
            x.printQuestion(stt);
            stt++;

            System.out.print("Your Answers: ");
            char userAnswers = input.next().charAt(0);
            userAnswers = Character.toUpperCase(userAnswers);
            if (x.checkAnswers(userAnswers) == true) {
                System.out.println("\n\n\n\nCORRECT !");
                point += 1;
            } else {
                System.out.println("\n\n\n\nINCORRECT ! \t\t CORRECT ANSWERS: " + x.getCorrectAnswers());
                health -= 1;
            }
            printInfo();
            if (isEndGame() == true) {
                if (point == 10)
                    System.out.println("--------------YOU WIN !---------");
                else
                    System.out.println("--------------YOU LOSE !---------");
                break;
            }
        }
        playAgain();
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
            } else check = false;
        }
    }

    public Question randomQuestion() {
        Random rand = new Random();
        int index = rand.nextInt(MAX);
        questionList.get(index).toString();
        return questionList.get(index);
    }

    private boolean isEndGame() {
        return point == 10 || health == 0;
    }
}