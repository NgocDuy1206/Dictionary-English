package dictionary;

import java.io.*;
import java.util.Scanner;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import word.*;

public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();

    public void insertFromFile1(String name) {
        try {
            name = "resources/data/" + name;
            FileReader fr = new FileReader(name);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] anh_viet = line.split("\t");
                Word word = new Word(anh_viet[0], anh_viet[1]);
                dictionary.put(anh_viet[0], word);
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error open file");
        } catch (IOException e) {
            System.out.println("Error read file");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void insertFromFile(String name) {
        try {
            String path = "resources/data/" + name;
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
    
            String line;
            String englishWord = "";
            StringBuilder meaningBuilder = new StringBuilder();
    
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("|")) {
                    if (!englishWord.isEmpty()) {
                        Word word = new Word();
                        word.setWordTarget(englishWord.trim());
                        word.setWordExplain(meaningBuilder.toString().trim());
                        dictionary.put(englishWord, word);
    
                        // Reset giá trị cho từ mới và nghĩa mới
                        englishWord = "";
                        meaningBuilder = new StringBuilder();
                    }
                    // Xử lý từ tiếp theo
                    englishWord = line.replace("|", "").trim();
                } else {
                    // Xử lý nghĩa của từ
                    meaningBuilder.append(line).append("\n");
                }
            }
    
            // Xử lý từ cuối cùng trong tệp
            if (!englishWord.isEmpty()) {
                Word word = new Word();
                word.setWordTarget(englishWord.trim());
                word.setWordExplain(meaningBuilder.toString().trim());
                dictionary.put(englishWord, word);
            }
    
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }
    
    
    
    public void dictionaryExportToFile(String name) {
        try {
            name = "resources/data/" + name;

            FileWriter fr = new FileWriter(name);
            BufferedWriter br = new BufferedWriter(fr);
            for (Word word : dictionary.values()) {
                br.write(word.getWordTarget() + "\t" + word.getWordExplain());
                br.newLine();
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Error read file");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void dictionarySearch(String key) {
        Set<String> keySet = dictionary.keySet();
        int len_key = key.length();
        boolean check = false;
        for (String word : keySet) {
            int len_word = word.length();
            if (len_word < len_key)
                continue;
            else {
                String tmp = word.substring(0, len_key);
                if (tmp.compareTo(key) == 0) {
                    check = true;
                    System.out.println(word + "\n");
                }
            }
        }
        if (check == false) {
            System.out.println("Không có từ nào như vậy!");
        }
    }

    public void dictionaryLookUp(String word_target) {
        int flag = 0;
        System.out.printf("%-10s | %-32s | %-32s\n", "STT", "English", "Vietnamese");
        for (Word word : dictionary.values()) {
            if (word.getWordTarget().equals(word_target)) {
                flag++;
                System.out.printf("%-10s | %-32s | %-32s\n", flag, word.getWordTarget(), word.getWordExplain());
            }
        }
        if (flag == 0)
            System.out.println("Không có trong từ điển!");
    }

    public ObservableList<String> lookUp(String word_target) {
        ObservableList<String> results = FXCollections.observableArrayList();

        for (Word word : dictionary.values()) {
            if (word.getWordTarget().equals(word_target)) {
                results.add(word.getWordTarget());
            }
        }

        return results;
    }

    public ObservableList<String> search(String key) {
        ObservableList<String> results = FXCollections.observableArrayList();

        Set<String> keySet = dictionary.keySet();
        int len_key = key.length();

        for (String word : keySet) {
            int len_word = word.length();
            if (len_word < len_key)
                continue;
            else {
                String tmp = word.substring(0, len_key);
                if (tmp.compareTo(key) == 0) {
                    results.add(word);
                }
            }
        }
        
        return results;
    }

    public void dictionaryDelete(String word_target) {
        if (dictionary.containsKey(word_target)) {
            dictionary.remove(word_target);
            if (!dictionary.containsKey(word_target))
                System.out.println("xoa thanh cong");
            else
                System.out.println("xoa khong thanh cong");
        } else
            System.out.println("khong co trong tu dien");
    }

    public void dictionaryUpdate(Word newWord) {
        if (dictionary.containsKey(newWord.getWordTarget())) {
            dictionary.replace(newWord.getWordTarget(), newWord);
            
            System.out.println("Update thanh cong");
        } else
            System.out.println(newWord.getWordTarget() + " khong co trong tu dien");
    }

    public void dictionaryAdd(Word newWord) {
        if (!dictionary.containsKey(newWord.getWordTarget())) {
            dictionary.put(newWord.getWordTarget(), newWord);
            System.out.println("Add thành công");
        } else {
            System.out.println("Add không thành công");
        }
    }

    public void insertFromCommandLine() {
        Scanner sn = new Scanner(System.in);
        int counterWord;
        System.out.println("Nhap so tu:");
        counterWord = Integer.parseInt(sn.nextLine());

        for (int i = 0; i < counterWord; i++) {
            System.out.println("Nhap tu Tieng Anh");
            String wordTarget = sn.nextLine();
            System.out.println("Nhap giai thich nghia bang Tieng Viet");
            String wordExplain = sn.nextLine();
            Word word = new Word(wordTarget, wordExplain);
            dictionary.put(wordTarget, word);
        }
        sn.close();
    }

    public void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }
    
    public Dictionary getDictionary() {
        return this.dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

}
