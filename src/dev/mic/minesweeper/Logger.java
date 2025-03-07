package dev.mic.minesweeper;

public abstract class Logger {

    public static void println(String text){
        print(text + '\n');
    }

    public static void print(String text){
        System.out.print(text);
    }

    public static void println(String text, Color color){
        print(text + '\n', color);
    }

    public static void print(String text, Color color){
        System.out.print(color + text + Color.RESET);
    }
}
