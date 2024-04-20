package dictionary;

import java.util.Scanner;

import game.GameManagement;
import word.*;

public class DictionaryCommandLine {
    private DictionaryManagement DM = new DictionaryManagement();

    public DictionaryCommandLine() {
        System.out.println("Welcome to My Application");
    }

    public void menu() {
        System.out.println("[0]     Exit");
        System.out.println("[1]     Add");
        System.out.println("[2]     Remove");
        System.out.println("[3]     Update");
        System.out.println("[4]     Display");
        System.out.println("[5]     Lookup");
        System.out.println("[6]     Search");
        System.out.println("[7]     Game");
        System.out.println("[8]     Import from file");
        System.out.println("[9]     Export to file");
    }

    public void yourAction() {
        try (Scanner input = new Scanner(System.in)) {
            String opt;
            do {
                menu();
                System.out.println("Your Action:");
                opt = input.next();
                input.nextLine();
                switch (opt) {
                    case "0":
                        break;
                    case "1": {
                        System.out.println("Nhap tu Tieng Anh");
                        String wordTarget = input.nextLine();
                        System.out.println("Nhap giai thich nghia bang Tieng Viet");
                        String wordExplain = input.nextLine();
                        Word word = new Word(wordTarget, wordExplain);
                        DM.dictionaryAdd(word);
                        break;
                    }
                    case "2": {
                        System.out.println("Nhap tu Tieng Anh can xoa");
                        String word_target = input.nextLine();
                        DM.dictionaryDelete(word_target);
                        break;
                    }
                    case "3": {
                        System.out.println("Nhap tu Tieng Anh can update");
                        String wordTarget = input.nextLine();
                        System.out.println("Nhập nghĩa cần update:");
                        String wordExplain = input.nextLine();
                        Word word = new Word(wordTarget, wordExplain);
                        DM.dictionaryUpdate(word);
                        break;
                    }
                    case "4": {
                        this.showAllWords();
                        break;
                    }
                    case "5": {
                        System.out.println("Nhap tu Tieng Viet can look up");
                        String word_explain = input.nextLine();
                        DM.dictionaryLookUp(word_explain);
                        break;
                    }
                    case "6": {
                        System.out.println("Nhap tu Tieng Anh can search");
                        String word_target = input.nextLine();
                        DM.dictionarySearch(word_target);
                        break;
                    }
                    case "7": {
                        GameManagement game = new GameManagement();
                        game.printMenu();
                        System.out.println("hihi");
                        break;
                    }
                    case "8": {
                        DM.insertFromFile1("WordList.txt");
                        break;
                    }
                    case "9": {
                        DM.dictionaryExportToFile("file_export.txt");
                        break;
                    }
                    default:
                        System.out.println("Action not supported!");
                }

            }while (opt.compareTo("0") != 0);
        }
    }
    public void showAllWords() {
        System.out.printf("%-10s | %-32s | %-32s\n", "STT", "English", "Vietnamese");
        int i = 1;
        for (Word word:DM.getDictionary().values()) {
            System.out.printf("%-10s | %-32s | %-32s\n",i, word.getWordTarget(), word.getWordExplain());
            i++;
        }
    }

    public void dictionaryBasic() {
        //DM.insertFromCommandLine();
        DM.insertFromFile("WordList.txt");
        DM.dictionaryExportToFile("file_export.txt");
    }
}
