package dev.mic.minesweeper;

public abstract class CardinalPoint {
    public static final Coordinate UP = new Coordinate(0,-1);
    public static final Coordinate UP_RIGHT = new Coordinate(1,-1);
    public static final Coordinate RIGHT = new Coordinate(1,0);
    public static final Coordinate DOWN_RIGHT = new Coordinate(1,1);
    public static final Coordinate DOWN = new Coordinate(0,1);
    public static final Coordinate DOWN_LEFT = new Coordinate(-1,1);
    public static final Coordinate LEFT = new Coordinate(-1,0);
    public static final Coordinate UP_LEFT = new Coordinate(-1,-1);
}
