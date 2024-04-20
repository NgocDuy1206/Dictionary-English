package controller;

import java.net.URL;
import java.util.ResourceBundle;

import game.GuessTheWord;
import game.question.WordGuess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class GuessTheWordController extends GameController {
    @FXML
    TextArea worddisplay;
    @FXML
    TextField answer;
    @FXML
    Label wordcorrect, wordwrong;
    @FXML
    ImageView wordcorrect1, wordwrong1;
    @FXML
    Button nextwordbtn;

    private GuessTheWord game;
    private WordGuess wordGuess;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new GuessTheWord();
        game.insertFromFile();
        wordcorrect.setVisible(false);
        wordcorrect1.setVisible(false);
        wordwrong.setVisible(false);
        wordwrong1.setVisible(false);
        displayWord();
        setupWordDisplay();
        setupAnswerField();
        nextwordbtn.setOnAction(event -> displayWord());
    }

    @FXML
    private void displayWord() {
        if (game != null) {
            wordGuess = game.randWord();
            worddisplay.setText(wordGuess.wordGuess());
        }
    }

    @FXML
    private void setupWordDisplay() {
        worddisplay.setWrapText(true);
        worddisplay.setEditable(false);
    }

    @FXML
    private void setupAnswerField() {
        answer.setEditable(true);
        answer.setOnAction(this::handleAnswer);
    }

    private void handleAnswer(ActionEvent event) {
        if (wordGuess != null && game != null) {
            String userInput = answer.getText().trim().toLowerCase();
            char userChar = userInput.charAt(0);

            if (wordGuess.checkAnswers(userChar)) {
                wordcorrect.setVisible(true);
                wordcorrect1.setVisible(true);
                wordwrong.setVisible(false);
                wordwrong1.setVisible(false);
                worddisplay.setText("Từ đúng: " + wordGuess.getWordTarget() + "\nNghĩa Tiếng Việt: " + wordGuess.getWordExplain());
            } else {
                wordcorrect.setVisible(false);
                wordcorrect1.setVisible(false);
                wordwrong.setVisible(true);
                wordwrong1.setVisible(true);
            }
        }
    }
}
