import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Collections;
public class Game {
    public int size;
    public boolean isDone = false;
    public Square[][] gameTable;
    public Point start, end;

    private Boolean shouldContinue = true;

    Game(int __SIZE__) {
        setClassVariables(__SIZE__);
        generateGameTable(__SIZE__);
        setStartPoint(gameTable, __SIZE__, 1);
        setEndPoint(gameTable, __SIZE__, (int) Math.pow(__SIZE__, 2) - 1);
        buildGameTable(new Point(0, 0), new Point(__SIZE__ - 1, __SIZE__ - 1), new Point(0, 0), 2);
    }

    Game() {
    }

    void validateGame(Game.Square[][] __game__) {

    }

    void buildGameTable(Point __startPoint, Point __endPoint, Point currentPosition, int order) {

        if (!shouldContinue)
            return;
        if (order == 16) {
            shouldContinue = false;
            printGame(this.gameTable);
            validateGame(this.gameTable);
            return;
        }
        List<Point> options = findNextOptions(this.gameTable, currentPosition);

        if (order == 15) {
            // System.out.println("Hazırlanıyor. -> " + options.size());
            eliminateOptions(options, this.size);
            //System.out.println("Tamamlandı. -> " + options.size());

        }

        for (Point option : options) {
            chooseOption(this.gameTable, this.size, order, option);
            buildGameTable(__startPoint, __endPoint, option, order + 1);
            resetOption(this.gameTable, this.size, option);
        }

    }
    private void printGame(Square[][] __game__) {
        int row = __game__.length;
        int col = __game__[0].length;
        System.out.println(" ------------------- GAME -----------------");
        System.out.println("");
        
        for (int i = 0; i < row; ++i) {
            for(int j = 0; j < col; ++j) {
                System.out.println(__game__[i][j]);
            }
            System.out.println("");
        } 

        System.out.println("");        
        System.out.println(" ------------------- GAME -----------------");
        
    }
    private void eliminateOptions(List<Point> options, int __SIZE__) {
        if (options.size() == 0)
            return;
        for (Iterator<Point> iterator = options.iterator(); iterator.hasNext();) {
            Point option = iterator.next();
            if (checkLastOrderConstraint(__SIZE__, option) == false) {
                iterator.remove();
            }
        }
    }

    private Boolean checkLastOrderConstraint(int __SIZE__, Point option) {
        
        if ((option.y == (__SIZE__ - 1)) || (option.x == option.y) || (option.x == (__SIZE__ - 1))) {
            return true;
        } else {
            return false;
        }
    }

    void doesEndsCorrectly() {

    }

    private void chooseOption(Game.Square[][] __game__, int __SIZE__, int order, Point option) {
        __game__[option.x][option.y].setOrder(order);
        List<Integer> directions = getPossibleDirections(option, __SIZE__);
        int index = new Random().nextInt(directions.size());
        Integer next = order == 15 ? chooseDirectionForFifteen(option, __SIZE__) : directions.get(index);
        __game__[option.x][option.y].setDirection(next);
    }
    private int chooseDirectionForFifteen(Point option, int __SIZE__) {
        if(option.x == (__SIZE__-1)) return 3;
        else if(option.y == (__SIZE__-1)) return 5;
        else if (option.x == option.y) return 4; 
        else return -1;
    }
    private void resetOption(Game.Square[][] __game__, int __SIZE__, Point option) {
        __game__[option.x][option.y].reset();
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
        int x = 0;
        int y = 0;
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
            //__game__[x][y].setDirection(points[new Random().nextInt(3)]);
        }
    }

    // test if a cell(square or point) is inside array and is integer
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
        Collections.shuffle(options);
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

        public void reset() {
            this.direction = 0;
            this.order = 0;
        }

        @Override
        public String toString() {
            return "Square{ direction=" + direction + ", order=" + order + "}";
        }
    }

    public static void main(String[] args) {
        int[][] test = new int[5][5];
        Game g = new Game(4);
        // List<Point> point = g.findOptionsInDirection(3, g.gameTable, new Point(0,
        // 0));
        // List<Point> points = g.findNextOptions(g.gameTable, new Point(0, 0));
        System.out.println();
    }

}
