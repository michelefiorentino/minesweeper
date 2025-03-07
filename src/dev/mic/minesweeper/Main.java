package dev.mic.minesweeper;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if(args.length != 3){
            Logger.println("Invalid args -> they should be: rows cols nMines");
            return;
        }

        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        int nMines = Integer.parseInt(args[2]);

        if(rows > 99){
            Logger.println("max rows: 100");
            return;
        }
        if(cols > 99){
            Logger.println("max cols: 100");
            return;
        }
        if(nMines >= rows*cols - 1) {
            Logger.println("there can't be more mines than: number of tiles - 1");
            return;
        }


        var field = new MinesweeperField(rows, cols, nMines);

        var alive = true;
        while(alive){
            if(field.isVictory())
                break;

            field.draw(true);
            field.drawInfo();
            int x = getParsedX(cols);
            int y = getParsedY(rows);
            int action = getAction();
            Coordinate coordinate = new Coordinate(x, y);
            if(action == 'f')
                field.addFlag(coordinate);
            else
                alive = field.dig(coordinate);
            clearConsole();
        }

        field.draw(false);
        if(alive)
            Logger.print("You won!");
        else
            Logger.print("Defeat...");
    }

    private static int getParsedX(int cols){
        String input;
        do {
            Logger.print("Insert X: ");
            input = scanner.nextLine();
        } while(input.isBlank() || !isNumber(input) || Integer.parseInt(input) < 0 || Integer.parseInt(input) >= cols);
        return Integer.parseInt(input);
    }

    private static int getParsedY(int rows){
        String input;
        do {
            Logger.print("Insert Y: ");
            input = scanner.nextLine();
        } while(input.isBlank() || !isNumber(input) || Integer.parseInt(input) < 0 || Integer.parseInt(input) >= rows);
        return Integer.parseInt(input);
    }

    private static char getAction(){
        String input;
        do {
            Logger.print("Flag(1)/Dig(0): ");
            input = scanner.nextLine();
            if(input.isBlank())
                continue;
            if(input.charAt(0) == '1')
                return 'f';
            if(input.charAt(0) == '0')
                return 'f';
        } while(input.length() != 1 || input.charAt(0) != 'f' && input.charAt(0) != 'd');
        return input.charAt(0);
    }

    private static boolean isNumber(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*
    private static void clearConsole(){
        for(int i=0; i<50; i++)
            Logger.println();
    }*/

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}