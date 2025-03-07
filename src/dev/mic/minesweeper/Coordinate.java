package dev.mic.minesweeper;

public record Coordinate(int x, int y) {

    @Override
    public String toString() {
        return "(" + x + ":" + y + ")";
    }

    public static Coordinate sum(Coordinate c1, Coordinate c2){
        return new Coordinate(c1.x() + c2.x(), c1.y() + c2.y());
    }
}
