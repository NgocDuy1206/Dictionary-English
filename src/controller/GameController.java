package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class GameController extends DictionaryController {
    @FXML
    Button multiplechoicebtn, guessthewordbtn, hangmanbtn;
    @FXML
    AnchorPane container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        multiplechoicebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent("/views/MultipleChoiceGui.fxml");
                System.out.println("Đang ở giao diện chơi game trắc nghiệm");
            }
        });

        guessthewordbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent("/views/GuessTheWordGui.fxml");
                System.out.println("Đang ở giao diện chơi game đoán từ");
            }
        });

        hangmanbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent("/views/MatchingWordGui.fxml");
                System.out.println("Đang ở giao diện chơi game nối từ");
            }
        });
    }
    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    @FXML
    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            setNode(component);
        } catch (IOException e) {
            System.out.println("lỗi");
            e.printStackTrace();
        }
    }
}
