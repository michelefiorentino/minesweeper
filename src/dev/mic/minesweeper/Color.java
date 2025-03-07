package dev.mic.minesweeper;

public enum Color {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    WHITE("\u001B[37m"),
    ORANGE("\u001B[38;5;208m"),
    BROWN("\u001B[38;5;94m"),
    PURPLE("\u001B[38;5;129m"),
    PINK("\u001B[38;5;205m"),
    GOLD("\u001B[38;5;220m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
