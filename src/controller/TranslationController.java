package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class TranslationController extends DictionaryController {
    enum Language {
        English("Tiếng Anh", "en"),
        Vietnamese("Tiếng Việt", "vi"),
        Chinese("Tiếng Trung", "zh-tw"),
        French("Tiếng Pháp", "fr"),
        AutoDetect("Phát hiện ngôn ngữ", "auto");

        private final String displayName;
        private final String code;

        Language(String displayName, String code) {
            this.displayName = displayName;
            this.code = code;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getCode() {
            return code;
        }
    }

    @FXML
    private TextArea sourceLangField, toLangField;
    @FXML
    private Button translateBtn, soundbtn1, soundbtn2;
    @FXML
    private ComboBox<Language> comboBox1;
    @FXML
    private ComboBox<Language> comboBox2;

    String lang_first;
    String lang_second;

    public String translateText(String input) {
        String translation = "";
        try {
            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + lang_first +
                    "&tl=" + lang_second + "&dt=t&q=" + URLEncoder.encode(input, "UTF-8");
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            String result = reader.readLine();
            reader.close();

            JsonArray jsonData = JsonParser.parseString(result).getAsJsonArray();
            JsonArray translationItems = jsonData.get(0).getAsJsonArray();

            for (JsonElement item : translationItems) {
                String translationLine = item.getAsJsonArray().get(0).getAsString();
                translation += " " + translationLine;
            }

            if (translation.length() > 1) {
                translation = translation.substring(1);
            }
        } catch (IOException | JsonIOException | JsonSyntaxException e) {
            e.printStackTrace();
        } 
        return translation;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Language> list = FXCollections.observableArrayList(Language.values());
        comboBox1.setItems(list);
        comboBox2.setItems(list);

        comboBox1.setOnAction(this::handleComboBox1Action);
        comboBox2.setOnAction(this::handleComboBox2Action);

        translateBtn.setOnAction(this::handleTranslateButtonClick);

        sourceLangField.textProperty().addListener((observable, oldValue, newValue) -> {
            translateBtn.setDisable(newValue.trim().isEmpty());
        });

        translateBtn.setDisable(true);
        toLangField.setEditable(false);

        soundbtn1.setOnAction(e -> speakText(sourceLangField.getText()));
        soundbtn2.setOnAction(e -> speakText(toLangField.getText()));
    }
    @FXML
    private void handleComboBox1Action(ActionEvent event) {
        Language selectedLanguage = comboBox1.getSelectionModel().getSelectedItem();
        if (selectedLanguage != null) {
            lang_first = selectedLanguage.getCode();
        }
    }

    @FXML
    private void handleComboBox2Action(ActionEvent event) {
        Language selectedLanguage = comboBox2.getSelectionModel().getSelectedItem();
        if (selectedLanguage != null) {
            lang_second = selectedLanguage.getCode();
        }
    }

    @FXML
    private void handleTranslateButtonClick(ActionEvent event) {
        String inputText = sourceLangField.getText();
        String translation = translateText(inputText);
        toLangField.setText(translation);
    }

    private void speakText(String text) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.speak(text);
        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }
}
