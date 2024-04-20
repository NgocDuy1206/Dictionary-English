package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManagement {
   protected int point;
   protected int health;
   private int curGame = 1;

   public GameManagement() {
   }

   public void setPoint(int point) {
      this.point = point;
   }

   public int getPoint() {
      return this.point;
   }

   protected void printGap() {
      for (int i = 0; i < 30; ++i) {
         System.out.println();
      }

      System.out.println("----------------------------------------");
   }

   public void printMenu() {
      System.out.println("Welcome to the game!");
      System.out.println("\n\n\n----------------------------------------\n\n\n");

      Scanner sc = new Scanner(System.in);
      while (curGame != 0) {
         printGameOptions();
         curGame = readUserInput(sc);
         handleGameSelection(curGame);
      }
   }

   private void printGameOptions() {
      System.out.println("[0] Back to menu");
      System.out.println("[1] Hangman");
      System.out.println("[2] Guess The Word");
      System.out.println("[3] Multiple Choice");
      System.out.println("[4] Matching Word");
      System.out.println("Your action: ");
   }

   private int readUserInput(Scanner scanner) {
      int userInput;
      try {
         userInput = scanner.nextInt();
         if (userInput < 0 || userInput > 4) {
            throw new InputMismatchException("Invalid action!");
         }
         printGap();
      } catch (InputMismatchException e) {
         userInput = -1;
         System.out.println("Invalid input! Please enter a valid option.");
         scanner.nextLine(); // Clear the input buffer
      }
      return userInput;
   }

   private void handleGameSelection(int selectedGame) {
      switch (selectedGame) {
         case 0:
            break;
         case 1:
            //startGame(new Hangman());
            break;
         case 2:
            startGame(new GuessTheWord());
            break;
         case 3:
            startGame(new MultipleChoice());
            break;
         case 4:
            startGame(new MatchingWord());
            break;
      }
   }

   private static void startGame(GameInterface game) {
      game.start();
   }

   /**
    * main để test game.
    */
   public static void main(String[] args) {
      GameManagement test = new GameManagement();
      test.printMenu();
   }
}