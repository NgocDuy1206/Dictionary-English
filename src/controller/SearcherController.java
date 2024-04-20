package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.net.URL;
import java.util.ResourceBundle;

import word.Word;

public class SearcherController extends DictionaryController {
    private ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private TextField searchTerm;

    @FXML
    private Button cancelBtn, saveBtn;

    @FXML
    private Label englishWord, headerList, notAvailableAlert;

    @FXML
    private TextArea explanation;

    @FXML
    private ListView<String> listResults;

    @FXML
    private Pane headerOfExplanation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTerm.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (searchTerm.getText().isEmpty()) {
                    cancelBtn.setVisible(false);
                    setListDefault(0);
                } else {
                    cancelBtn.setVisible(true);
                    handleOnKeyTyped();
                }
            }
        });
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTerm.clear();
                cancelBtn.setVisible(false);
                setListDefault(0);
            }
        });
        listResults.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleMouseClickAWord(event);
            }
        });

        explanation.setEditable(false);
        saveBtn.setVisible(false);
        cancelBtn.setVisible(false);
        notAvailableAlert.setVisible(false);
    }
    
    @FXML
    private void handleOnKeyTyped() {
        list = dm.search(searchTerm.getText().trim());
        //System.out.println(list);

        if (list.isEmpty()) {
            notAvailableAlert.setVisible(true);
        } else {
            notAvailableAlert.setVisible(false);
            headerList.setText("Kết quả");
            listResults.setItems(list);
        }
    }

    @FXML
    private void handleMouseClickAWord(MouseEvent arg0) {
        String selectedWord = listResults.getSelectionModel().getSelectedItem();
        //System.out.println(selectedWord);
        if (selectedWord != null) {
            englishWord.setText(dictionary.get(selectedWord).getWordTarget());
            explanation.setText(dictionary.get(selectedWord).getWordExplain());
            headerOfExplanation.setVisible(true);
            explanation.setVisible(true);
            explanation.setEditable(false);
            saveBtn.setVisible(false);
        }
    }

    @FXML
    private void handleClickEditBtn() {
        explanation.setEditable(true);
        saveBtn.setVisible(true);
    }

    @FXML
    private void handleClickSoundBtn() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin");
        String selectedWord = listResults.getSelectionModel().getSelectedItem();
        if (voice != null) {
            voice.allocate();
            voice.speak(dictionary.get(selectedWord).getWordTarget());
        } else throw new IllegalStateException("Cannot find voice: kevin");
    }

    @FXML
    private void handleClickSaveBtn() {
        Word newWord = new Word(englishWord.getText(), explanation.getText());
        dm.dictionaryUpdate(newWord);

        saveBtn.setVisible(false);
        explanation.setEditable(false);
    }

    @FXML
    private void handleClickDeleteBtn() {
        String selectedWord = listResults.getSelectionModel().getSelectedItem();
        dm.dictionaryDelete(selectedWord);
        refreshAfterDeleting();
    }

    @FXML
    private void refreshAfterDeleting() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(englishWord.getText())) {
                list.remove(i);
                break;
            }
        }
        listResults.setItems(list);
        headerOfExplanation.setVisible(false);
        explanation.setVisible(false);
    }

    private void setListDefault(int index) {

    }
}
