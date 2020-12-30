import javafx.beans.WeakInvalidationListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TicTacToe<IsMaximizing> {

    // O = player, X = computer

    public static Scanner scan = new Scanner(System.in);

    //variables / lists / arrays
    public static int Scores;
    public static Boolean IsTie = false;
    public static int PlayerPosition;
    public static String Winner = null;
    public static String GameFinishedMessage;
    public static boolean[] SpaceTaken = {true, true, true, true, true, true, true, true, true,};
    public static int SpacesLeft = 9;

    public static char GameBoard[][] = {{' ', '|', ' ', '|', ' '},
                                        {'-', '+', '-', '+', '-'},
                                        {' ', '|', ' ', '|', ' '},
                                        {'-', '+', '-', '+', '-'},
                                        {' ', '|', ' ', '|', ' '},};

    //MAIN MAIN MAIN
    public static void main(String[] args) {
        Initialization(GameBoard);

        while (true) {

            ComputerMove();

            GameFinished();

            if (Winner != null || IsTie) {
                System.out.println("");
                System.out.println(GameFinishedMessage);
                break;
            }

            delay();

            PlayerMove();

            GameFinished();

            if (Winner != null || IsTie) {
                System.out.println("");
                System.out.println(GameFinishedMessage);
                break;
            }

        }

    }

    public static void Initialization(char GameBoard[][]) {
        System.out.println("Hello, welcome to TicTacToe");
        System.out.println("This AI was programmed to never lose...");
        System.out.println("You are O and the Computer is X, the computer will go first");
        System.out.println("To place a X, enter an integer value from 1 - 9; 1 being the first box and 9 being the last (horizontal then vertical)");
        System.out.println("");
    }

    public static void PrintGameBoard() {
        for (int i = 0; i < 5; i++) {
            System.out.println(GameBoard[i]);
        }
    }

    public static void PlayerMove() {
        System.out.print("Enter your placement (1-9): ");

        do {
            PlayerPosition = Integer.parseInt(scan.nextLine());
        } while (SpaceCheck(PlayerPosition));

        System.out.println("Player Move...");

        switch (PlayerPosition) {
            case 1:
                GameBoard[0][0] = 'O';
                break;
            case 2:
                GameBoard[0][2] = 'O';
                break;
            case 3:
                GameBoard[0][4] = 'O';
                break;
            case 4:
                GameBoard[2][0] = 'O';
                break;
            case 5:
                GameBoard[2][2] = 'O';
                break;
            case 6:
                GameBoard[2][4] = 'O';
                break;
            case 7:
                GameBoard[4][0] = 'O';
                break;
            case 8:
                GameBoard[4][2] = 'O';
                break;
            default:
                GameBoard[4][4] = 'O';

        }

        PrintGameBoard();
        SpaceTaken[PlayerPosition - 1] = false;
        SpacesLeft -= 1;

    }

    public static boolean SpaceCheck(int Position) {
        return !SpaceTaken[Position - 1];
    }

    public static String GameFinished() {
        Winner = null;
        //HORIZONTAL
        for (int i = 0; i < 5; i += 2) {
            if (GameBoard[i][0] == GameBoard[i][2] && GameBoard[i][2] == GameBoard[i][4] && GameBoard[i][0] != ' ') {
                Winner = Character.toString(GameBoard[i][0]);;
            }
        }
        //VERTICAL
        for (int i = 0; i < 5; i += 2) {
            if (GameBoard[0][i] == GameBoard[2][i] && GameBoard[2][i] == GameBoard[4][i] && GameBoard[0][i] != ' ') {
                Winner = Character.toString(GameBoard[0][i]);;
            }
        }
        //DIAGONAL
        if (GameBoard[0][0] == GameBoard[2][2] && GameBoard[2][2] == GameBoard[4][4] && GameBoard[0][0] != ' ') {
            Winner = Character.toString(GameBoard[0][0]);;
        }
        if (GameBoard[0][4] == GameBoard[2][2] && GameBoard[2][2] == GameBoard[4][0] && GameBoard[0][4] != ' ') {
            Winner = Character.toString(GameBoard[0][4]);;
        }


        if (Winner == null && SpacesLeft <= 0) {
            IsTie = true;
            GameFinishedMessage = "Its a Tie";
            return "Tie";
        } else {
            GameFinishedMessage = "The winner is " + Winner;
            return Winner;
        }
    }

    public static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


    public static void ComputerMove() {
        int BestScore = (int) Double.NEGATIVE_INFINITY;
        int[] BestMove = new int[2];
        for (int i = 0; i < 5; i += 2) {
            for (int j = 0; j < 5; j += 2) {
                if (GameBoard[i][j] == ' ') {
                    GameBoard[i][j] = 'X';
                    int Score = MiniMax(GameBoard, false);
                    GameBoard[i][j] = ' ';

                    if (Score > BestScore) {
                        BestScore = Score;
                        BestMove[0] = i;
                        BestMove[1] = j;
                    }

                }
            }
        }
        System.out.println("Computer Move...");
        SpaceTaken[BestMove[0] * 3 / 2 + BestMove[1] / 2] = false;
        SpacesLeft -= 1;
        GameBoard[BestMove[0]][BestMove[1]] = 'X';
        PrintGameBoard();

    }


    public static int MiniMax(char[][] board, Boolean IsMaximizing) {
        String Result = GameFinished();
        if (Result != null) {
            if (Result.equals("O")) {
                Scores = -10;
            } else if (Result.equals("X")) {
                Scores = 10;
            } else {
                Scores = 0;
            }
            return Scores;
        }

        if (IsMaximizing) {
            int BestScore = (int) Double.NEGATIVE_INFINITY;
            for (int i = 0; i < 5; i += 2) {
                for (int j = 0; j < 5; j += 2) {
                    if (GameBoard[i][j] == ' ') {
                        GameBoard[i][j] = 'X';
                        int Score = MiniMax(GameBoard,false);
                        GameBoard[i][j] = ' ';
                        BestScore = Math.max(Score, BestScore);
                    }
                }
            }
            return BestScore;
        } else {
            int BestScore = (int) Double.POSITIVE_INFINITY;
            for (int i = 0; i < 5; i += 2) {
                for (int j = 0; j < 5; j += 2) {
                    if (GameBoard[i][j] == ' ') {
                        GameBoard[i][j] = 'O';
                        int Score = MiniMax(GameBoard,true);
                        GameBoard[i][j] = ' ';
                        BestScore = Math.min(Score, BestScore);
                    }
                }
            }
            return BestScore;
        }
    }

}
