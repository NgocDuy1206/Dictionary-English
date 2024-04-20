package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import game.MatchingWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MatchingWordController extends GameController {
    @FXML
    private Label used;

    @FXML 
    private Label pointMax;

    @FXML
    private ImageView used1;

    @FXML
    private Button add;

    @FXML
    private Label correct;

    @FXML
    private ImageView correct1;

    @FXML
    private Button replayBtn;

    @FXML
    private TextField word;

    @FXML
    private Label wrong;

    @FXML
    private ImageView wrong1;

    @FXML
    private VBox viewArea;

    @FXML
    private Label yourPoint;

    @FXML
    void addAnswers(MouseEvent event) {
        getAnswer(null);
    }

    @FXML
    void getAnswer(ActionEvent event) {
        inputAnswers = word.getText();
        if (game != null) {
            if (game.checkAnswers(inputAnswers) && !answersList.contains(inputAnswers)) {
                point++;
                updateCorrect();
                addToAnswerList(inputAnswers);
                addToListView(inputAnswers);
                display();

                // lặp lại quá trình 
                String wordKey = game.randomWord();
                game.characterKey = wordKey.charAt(wordKey.length() - 1);
                addToListView(wordKey);
                addToAnswerList(wordKey);
                display();
                updatePoint();
                word.clear();
            } else {
                if (answersList.contains(inputAnswers)) {
                    updateUsed();
                } else {
                    updateWrong();
                }
            }
        }
        word.clear();
    }

    @FXML
    void playAgain(MouseEvent event) {
        point_Max = Math.max(point_Max, point);
        setupStart();
    }

    private static final int MAX = 7;
    private MatchingWord game;
    private List<String> answersList;
    private List<StackPane> listView;
    private String inputAnswers = "";
    private int point;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new MatchingWord();
        game.insertFromFile();
        setupStart();
    }

    private void display() {
        viewArea.getChildren().clear();
        viewArea.getChildren().addAll(listView);
        alignmentLabel();
    }

    private void alignmentLabel() {
        for (int i = 0; i < listView.size(); i++) {
            if (i % 2 == 1) {
                listView.get(i).setAlignment(Pos.CENTER_RIGHT);
            } else {
                listView.get(i).setAlignment(Pos.CENTER_LEFT);
            }
        }
    }

    private void addToListView(String word) {
        if (listView.size() >= MAX) {
            listView.remove(0);
        }
        Label x = new Label(word);
        x.setStyle("-fx-background-color:   white;\r\n" + //
                "    -fx-background-radius: 30;\r\n" + //
                "    -fx-border-radius: 30;\r\n" + //
                "    -fx-border-color: green;" + //
                "    -fx-padding: 2px;" + //
                "    -fx-effect: dropshadow(three-pass-box, #ededed, 15, 0, 0, 2) ;");
        StackPane y = new StackPane(x);
        y.setAlignment(Pos.CENTER_LEFT);
        listView.add(y);
    }

    private void addToAnswerList(String word) {
        answersList.add(word);
    }

    private void updatePoint() {
        yourPoint.setText("Point: " + point);
        yourPoint.setVisible(true);
    }

    private void updateCorrect() {
        correct.setVisible(true);
        correct1.setVisible(true);
        wrong.setVisible(false);
        wrong1.setVisible(false);
        wrong.setVisible(false);
        wrong1.setVisible(false);
        used.setVisible(false);
        used1.setVisible(false);
    }

    private void updateUsed() {
        correct.setVisible(false);
        correct1.setVisible(false);
        wrong.setVisible(false);
        wrong1.setVisible(false);
        used.setVisible(true);
        used1.setVisible(true);
    }

    private void updateWrong() {
        correct.setVisible(false);
        correct1.setVisible(false);
        wrong.setVisible(true);
        wrong1.setVisible(true);
        used.setVisible(false);
        used1.setVisible(false);
    }

    private void setUpPointMax() {
        pointMax.setText("Point Max: " + point_Max);
        pointMax.setVisible(true);
    }

    private void setupStart() {
        correct.setVisible(false);
        correct1.setVisible(false);
        wrong.setVisible(false);
        wrong1.setVisible(false);
        used.setVisible(false);
        used1.setVisible(false);
        word.setEditable(true);

        // set up thuộc tính
        point = 0;
        answersList = new ArrayList<>();
        listView = new ArrayList<>();
        updatePoint();
        setUpPointMax();
        viewArea.getChildren().clear();

        // set up game.
        String wordKey = game.randomWord();
        game.characterKey = wordKey.charAt(wordKey.length() - 1);
        addToListView(wordKey);
        addToAnswerList(wordKey);
        viewArea.getChildren().addAll(listView);
    }
}
