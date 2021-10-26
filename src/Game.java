import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Point;

public class Game {
    public int size;
    public boolean isDone = false;
    public Square[][] gameTable;
    public Point start, end;

    Game(int __SIZE__) {
        setClassVariables(__SIZE__);
        generateGameTable(__SIZE__);
        setStartPoint(gameTable, __SIZE__, 0);
        setEndPoint(gameTable, __SIZE__, (int) Math.pow(__SIZE__, 2) - 1);
    }

    Game() {
    }

    private void setClassVariables(int size) {
        this.size = size;
        // setMinimumSize
    }

    private void generateGameTable(int __size__) {
        gameTable = new Square[__size__][__size__];
        for (int i = 0; i < gameTable.length; i++) {
            for (int j = 0; j < gameTable.length; j++) {
                gameTable[i][j] = new Square();
            }
        }

    }

    private void setStartPoint(Square[][] __game__, int __size__, int start) {
        int x = start / __size__;
        int y = start % __size__;
        this.start = new Point(x, y);
        if (__game__[x][y] != null) {
            __game__[x][y].setOrder(1);
            __game__[x][y].setDirection(new Random().nextInt(3) + 3);
        }
    }

    private void setEndPoint(Game.Square[][] __game__, int __size__, int end) {
        int x = end / __size__;
        int y = end % __size__;
        this.end = new Point(x, y);
        int[] points = { 1, 7, 8 };
        if (__game__[x][y] != null) {
            __game__[x][y].setOrder((int) Math.pow(__size__, 2));
            __game__[x][y].setDirection(points[new Random().nextInt(3)]);
        }
    }

    private boolean test(int x, int y, int[][] test) {
        try {
            if (test[x][y] == (int) test[x][y]) {
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        return false;
    }

    Point getFactor(int direction) {
        switch (direction) {
        case 1:
            return new Point(-1, 0);
        case 2:
            return new Point(-1, 1);
        case 3:
            return new Point(0, 1);
        case 4:
            return new Point(1, 1);
        case 5:
            return new Point(1, 0);
        case 6:
            return new Point(1, -1);
        case 7:
            return new Point(0, -1);
        case 8:
            return new Point(-1, -1);
        default:
            return null;
        }
    }

    private List<Integer> getPossibleDirections(Point position, int __size__) {
        int x = position.x;
        int y = position.y;
        int[][] test = new int[__size__][__size__];
        List<Integer> possibleDirections = new ArrayList<>();

        // 1 means up direction
        if (test(x - 1, y, test))
            possibleDirections.add(1);

        // 2 means up right direction
        if (test(x - 1, y + 1, test))
            possibleDirections.add(2);

        // 3 means right direction
        if (test(x, y + 1, test))
            possibleDirections.add(3);

        // 4 means down right direction
        if (test(x + 1, y + 1, test))
            possibleDirections.add(4);

        // 5 means down direction
        if (test(x + 1, y, test))
            possibleDirections.add(5);

        // 6 means down left direction
        if (test(x + 1, y - 1, test))
            possibleDirections.add(6);

        // 7 means left direction
        if (test(x, y - 1, test))
            possibleDirections.add(7);

        // 8 means left up direction
        if (test(x - 1, y - 1, test))
            possibleDirections.add(8);

        return possibleDirections;
    }

    public boolean canMove() {
        return false;
    }

    Point multiplyFactor(Point position, int times) {
        return new Point(position.x * times, position.y * times);
    }

    Point addPoints(Point first, Point second) {
        return new Point(first.x + second.x, first.y + second.y);
    }

    List<Point> findOptionsInDirection(int direction, Game.Square[][] __game__, Point currentPosition) {
        List<Point> options = new ArrayList<Point>();
        Point factor = getFactor(direction);
        int counter = 1;
        Point cursor = addPoints(currentPosition, multiplyFactor(factor, counter));
        int[][] dummy = new int[this.size][this.size];
        while (test(cursor.x, cursor.y, dummy)) {
            if (__game__[cursor.x][cursor.y].order == 0) {
                options.add(new Point(cursor.x, cursor.y));
            }
            counter++;
            cursor = addPoints(currentPosition, multiplyFactor(factor, counter));
        }
        return options;
    }

    void movePointInDirection(Game.Square[][] __game__, int direction, Point currentPosition) {

    }

    List<Point> findNextOptions(Game.Square[][] __game__, Point currentPosition) {
        List<Integer> directions = getPossibleDirections(currentPosition, this.size);
        List<Point> options = new ArrayList<>();
        for (Integer direction : directions) {
            List<Point> optionsInDirections = findOptionsInDirection(direction, __game__, currentPosition);
            options.addAll(optionsInDirections);
        }
        // Collections.shuffle(options);
        return options;
    }

    private Boolean done() {
        return isDone = true;
    }

    class Square {
        int direction = 0;
        int order = 0;

        Square(int _direction, int _order) {
            this.direction = _direction;
            this.order = _order;
        }

        public Square() {
        }

        public int getDirection() {
            return direction;
        }

        public int getOrder() {
            return order;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        @Override
        public String toString() {
            return "Square{ direction=" + direction + ", order=" + order + "}";
        }
    }

    public static void main(String[] args) {
        int[][] test = new int[5][5];
        Game g = new Game(4);
        List<Point> point = g.findOptionsInDirection(3, g.gameTable, new Point(0, 0));
        System.out.println();
    }

}
