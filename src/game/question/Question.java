package game.question;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String question;
    private String correctAnswers;
    private List<String> listAnswers = new ArrayList<>();

    public Question() {

    }

    public Question(String question, String correctAnswers) {
        this.question = question;
        this.correctAnswers = correctAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<String> getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(List<String> listAnswers) {
        this.listAnswers = listAnswers;
    }

    public boolean checkAnswers(String answer) {
        if (correctAnswers.equalsIgnoreCase(answer)) {
            return true;
        } else
            return false;
    }

    public void printQuestion(int stt) {
        String s = "Question " + Integer.toString(stt) + ": ";
        s += question + "\n";
        s += "A. " + listAnswers.get(0) + "\n"
                + "B. " + listAnswers.get(1) + "\n"
                + "C. " + listAnswers.get(2) + "\n"
                + "D. " + listAnswers.get(3) + "\n";
        System.out.println(s);
    }

    public boolean checkAnswers(char userAnswers) {
        userAnswers = Character.toUpperCase(userAnswers);
        int index = userAnswers - 'A';
        String answers = listAnswers.get(index);
        if (correctAnswers.equals(answers) == true) {
            return true;
        } else {
            return false;
        }
    }
}