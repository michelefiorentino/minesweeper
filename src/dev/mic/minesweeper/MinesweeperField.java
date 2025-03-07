package dev.mic.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinesweeperField {
    private int rows;
    private int cols;
    private int nMines;
    private char[][] tiles;

    private final char fill = '■';
    private final char mine = '*';
    private final char clear = '+';
    private final char explosion = 'X';
    private final char flagged = 'F';

    private final Random random = new Random();

    private static final Coordinate[] cps = new Coordinate[]{
        CardinalPoint.UP,
        CardinalPoint.UP_RIGHT,
        CardinalPoint.RIGHT,
        CardinalPoint.DOWN_RIGHT,
        CardinalPoint.DOWN,
        CardinalPoint.DOWN_LEFT,
        CardinalPoint.LEFT,
        CardinalPoint.UP_LEFT,
    };
    private List<Coordinate> flags;

    public MinesweeperField(int rows, int cols, int nMines){
        this.rows = rows;
        this.cols = cols;
        this.nMines = nMines;
        this.flags = new ArrayList<>();
        this.initTiles();
    }

    private void initTiles(){
        tiles = new char[rows][cols];
        var mineCoords = getRandCoordinates();
        for(int y = 0; y<rows; y++)
            for(int x=0; x<cols; x++)
                tiles[y][x] = isMineCoord(y, x, mineCoords) ? mine : fill;
    }

    private List<Coordinate> getRandCoordinates(){
        var bin = new ArrayList<Integer>(rows * cols);
        int count = 0;

        for(int y = 0; y<rows; y++)
            for(int x=0; x<cols; x++)
                bin.add(count++);


        List<Coordinate> coords = new ArrayList<>(nMines);
        for(int i = 0; i< nMines; i++){
            int rndIndex = random.nextInt(rows * cols - i);
            int uniqueRnd = bin.get(rndIndex);
            coords.add(makeCoord(uniqueRnd));
            bin.remove(rndIndex);
        }

        return coords;
    }

    private Coordinate makeCoord(int index){
        int y = index / cols;
        int x = index % cols;
        return new Coordinate(x,y);
    }

    private boolean isMineCoord(int y, int x, List<Coordinate> minesCoords){
        for(var coord : minesCoords)
            if(x == coord.x() && y == coord.y())
                return true;
        return false;
    }

    public boolean dig(Coordinate coord){
        if(tiles[coord.y()][coord.x()] == mine) {
            tiles[coord.y()][coord.x()] = explosion;
            return false;
        }

        int nMines = countSurroundingMines(coord);
        if(nMines != 0){
            tiles[coord.y()][coord.x()] = Character.forDigit(nMines, 10);
        } else {
            tiles[coord.y()][coord.x()] = clear;
            for(var cp : cps){
                var c = Coordinate.sum(coord, cp);
                if(!isOutOfBound(c) && tiles[c.y()][c.x()] != clear)
                    dig(c);
            }
        }
        return true;
    }

    private int countSurroundingMines(Coordinate coord){
        int count = 0;
        for(var cp : cps){
            var c = Coordinate.sum(coord, cp);
            if(!isOutOfBound(c) && tiles[c.y()][c.x()] == mine)
                count++;
        }
        return count;
    }

    private boolean isOutOfBound(Coordinate coord){
        return coord.x() < 0 || coord.x() >= cols || coord.y() < 0 || coord.y() >= rows;
    }

    public void addFlag(Coordinate coord){
        if(isFlag(coord.x(), coord.y())){
            flags.removeIf(c -> c.x() == coord.x() && c.y() == coord.y());
        } else {
            flags.add(coord);
        }
    }

    private boolean isFlag(int col, int row){
        for(var flag : flags)
            if(flag.x() == col && flag.y() == row)
                return true;
        return false;
    }

    public void draw(boolean hideMines){
        Logger.print("    ");
        for(int x=0; x<cols; x++){
            int r = x / 10;
            String rc = r > 0 ? String.valueOf(r) : " ";
            Logger.print( rc + " ");
        }
        Logger.print("\n    ");
        for(int x=0; x<cols; x++){
            int r = x % 10;
            Logger.print( r + " ");
        }
        Logger.print(" x");

        Logger.print("\n  ╔");
        for(int x=0; x< (cols * 2) + 1; x++){
            Logger.print("═");
        }
        Logger.println("╗");

        for(int y = 0; y<rows; y++){
            if(y < 10)
                Logger.print(" " + y + "║ ");
            else
                Logger.print(y + "║ ");

            for(int x=0; x<cols; x++){
                char tile = tiles[y][x] == mine && hideMines? fill : tiles[y][x];
                if(isFlag(x, y) && hideMines)
                    tile = flagged;

                Color color = getColorForTile(x,y, hideMines);
                Logger.print(tile + " ", color);
            }
            Logger.print("║\n");
        }
        Logger.print(" y╚");
        for(int x=0; x< (cols * 2) + 1; x++){
            Logger.print("═");
        }
        Logger.println("╝\n");
    }

    public void drawInfo(){
        Logger.println("   To Dig: " + countToDig() + ", Flagged: " + flags.size() + ", Mines: " + nMines + "\n");
    }

    public Color getColorForTile(int x, int y, boolean hideMines){
        if(isFlag(x,y))
            return Color.RED;

        if(tiles[y][x] == mine && hideMines)
            return Color.GREEN;

        return switch (tiles[y][x]) {
            case fill -> Color.GREEN;
            case '1' -> Color.BLUE;
            case '2' -> Color.ORANGE;
            case '3' -> Color.MAGENTA;
            case '4' -> Color.PURPLE;
            case '5' -> Color.BROWN;
            case '6' -> Color.YELLOW;
            case '7' -> Color.PINK;
            case '8' -> Color.GOLD;
            default -> Color.WHITE;
        };
    }

    private int countToDig(){
        int countDigged = 0;
        for(int y=0; y<rows; y++)
            for(int x=0; x<cols; x++)
                countDigged += tiles[y][x] == fill? 1 : 0;
        return countDigged;
    }

    public boolean isVictory(){
        return countToDig() == 0;
    }
}
