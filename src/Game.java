import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class Game {
    public int size, row, col;
    public boolean isDone = false;
    public Square[][] gameTable, question, answer;
    public Point start, end;
    ControlPanel.ThreadSlayer slayer;
    private Boolean shouldContinue = true;
    private Boolean shouldIncludeClues = false;

    Game(int row, int col, Boolean includeClues, int numberofClues, ControlPanel.ThreadSlayer slyr) {
        this.row = row;
        this.col = col;
        this.size = row * col;
        this.shouldIncludeClues = includeClues;
        this.numberofclues = numberofClues;
        this.slayer = slyr;
        gameInit(row, col);
    }

    Game(int __SIZE__) {

    }

    Game() {
    }

    void gameInit(int row, int col) {
        // setClassVariables(__SIZE__);
        setClassVariables();
        generateGameTable();
        setStartPoint(gameTable);
        setEndPoint(gameTable);
        buildGameTable(new Point(0, 0), new Point(this.row - 1, this.col - 1), new Point(0, 0), 2);
    }

    void validateGame(Game.Square[][] __game__) {

    }

    Point minus(Point first, Point second) {
        return new Point(first.x - second.x, first.y - second.y);
    }

    Boolean isEqual(Point first, Point second) {
        return first.x == second.x && first.y == second.y;
    }

    Boolean isDiogonalToLastSquare(Point position) {
        int[][] test = new int[this.row][this.col];
        Point temp = new Point(this.end.x, this.end.y);
        while (test(temp.x, temp.y, test)) {
            if (isEqual(temp, position)) {
                return true;
            }
            temp = addPoints(temp, new Point(-1, -1));
        }
        return false;
    }

    void buildGameTable(Point __startPoint, Point __endPoint, Point currentPosition, int order) {

        if (!shouldContinue || slayer.shouldKill) {
            return;
        }
        if (order == this.size) {
            if ((currentPosition.x == (this.row - 1)) || (currentPosition.y == (this.col - 1))
                    || isDiogonalToLastSquare(currentPosition)) {
                gameTable[currentPosition.x][currentPosition.y]
                        .setDirection(chooseDirectionForLastSquare(currentPosition));
                int numberOfSolutions = solveGame(this.gameTable);
                if (numberOfSolutions == 1) {
                    shouldContinue = false;
                }
            }
        }
        List<OptionWithDirection> options = findNextOptions(this.gameTable, currentPosition);

        for (OptionWithDirection optionWD : options) {
            chooseOption(this.gameTable, currentPosition, order, optionWD);
            buildGameTable(__startPoint, __endPoint, optionWD.option, order + 1);
            resetOption(this.gameTable, optionWD.option);
        }

    }

    public void printGame(Square[][] __game__) {

        System.out.println(" ------------------- GAME -----------------");

        for (int i = 0; i < this.row; ++i) {
            for (int j = 0; j < this.col; ++j) {
                System.out.print("[" + (__game__[i][j].order < 10 ? "0" + __game__[i][j].order : __game__[i][j].order)
                        + " " + getDirectionSymbol(__game__[i][j].direction) + "]");
            }
            System.out.println("");
        }

        System.out.println(" ------------------- GAME -----------------");

    }

    private void chooseOption(Game.Square[][] __game__, Point currentPosition, int order,
            OptionWithDirection optionWD) {
        Point option = optionWD.option;
        __game__[option.x][option.y].setOrder(order);
        __game__[currentPosition.x][currentPosition.y].setDirection(optionWD.relativeDirection);
    }

    private int chooseDirectionForLastSquare(Point option) {
        if (option.x == (this.row - 1))
            return 3;
        else if (option.y == (this.col - 1))
            return 5;
        else if (isDiogonalToLastSquare(option))
            return 4;
        else
            return -1;
    }

    private void resetOption(Game.Square[][] __game__, Point option) {
        __game__[option.x][option.y].reset();
    }

    private void setClassVariables() {
        this.isDone = false;
        this.shouldContinue = true;
    }

    private void generateGameTable() {
        gameTable = new Square[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                gameTable[i][j] = new Square();
            }
        }
    }

    private void setStartPoint(Square[][] __game__) {
        int x = 0;
        int y = 0;
        this.start = new Point(x, y);
        if (__game__[x][y] != null) {
            __game__[x][y].setOrder(1);
        }
    }

    private void setEndPoint(Game.Square[][] __game__) {
        int x = this.row - 1;
        int y = this.col - 1;
        this.end = new Point(x, y);
        if (__game__[x][y] != null) {
            __game__[x][y].setOrder(this.size);
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

    private List<Integer> getPossibleDirections(Point position) {
        int x = position.x;
        int y = position.y;
        int[][] test = new int[this.row][this.col];
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

    Point multiplyFactor(Point position, int times) {
        return new Point(position.x * times, position.y * times);
    }

    Point addPoints(Point first, Point second) {
        return new Point(first.x + second.x, first.y + second.y);
    }

    OptionWithDirection findOptionInDirectionInClueCase(int direction, Game.Square[][] __game__, Point currentPosition,
            int order) {
        if (!shouldIncludeClues)
            return null;
        Point factor = getFactor(direction);
        int counter = 1;
        Point cursor = addPoints(currentPosition, multiplyFactor(factor, counter));
        int[][] dummy = new int[row][col];
        while (test(cursor.x, cursor.y, dummy)) {
            if (__game__[cursor.x][cursor.y].order == order) {
                return new OptionWithDirection(new Point(cursor.x, cursor.y), direction);
            }
            counter++;
            cursor = addPoints(currentPosition, multiplyFactor(factor, counter));
        }
        return null;
    }

    List<OptionWithDirection> findOptionsInDirection(int direction, Game.Square[][] __game__, Point currentPosition) {
        List<OptionWithDirection> options = new ArrayList<OptionWithDirection>();
        Point factor = getFactor(direction);
        int counter = 1;
        Point cursor = addPoints(currentPosition, multiplyFactor(factor, counter));
        int[][] dummy = new int[row][col];
        while (test(cursor.x, cursor.y, dummy)) {
            if (__game__[cursor.x][cursor.y].order == 0) {
                options.add(new OptionWithDirection(new Point(cursor.x, cursor.y), direction));
            }
            counter++;
            cursor = addPoints(currentPosition, multiplyFactor(factor, counter));
        }
        return options;
    }

    List<OptionWithDirection> findNextOptions(Game.Square[][] __game__, Point currentPosition) {
        List<Integer> directions = getPossibleDirections(currentPosition);
        List<OptionWithDirection> optionsWithDirection = new ArrayList<>();
        for (Integer direction : directions) {
            List<OptionWithDirection> optionsInDirections = findOptionsInDirection(direction, __game__,
                    currentPosition);
            optionsWithDirection.addAll(optionsInDirections);
        }
        Collections.shuffle(optionsWithDirection);
        return optionsWithDirection;
    }

    class OptionWithDirection {
        public OptionWithDirection(Point _point, int _direction) {
            this.option = _point;
            this.relativeDirection = _direction;
        }

        Point option;
        int relativeDirection; // the arrow points to this option
    }

    public Boolean done() {
        return isDone = true;
    }

    class Square {
        int direction = 0;
        int order = 0;

        Square() {
        }

        Square(int _direction, int _order) {
            this.direction = _direction;
            this.order = _order;
        }

        Square(int _direction) {
            this.direction = _direction;
        }

        public Square(Game.Square square) {
            this.order = square.getOrder();
            this.direction = square.getDirection();
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

    static String getDirectionSymbol(int direction) {
        switch (direction) {
        case 1:
            return "\u2191";
        case 2:
            return "\u2197";
        case 3:
            return "\u2192";
        case 4:
            return "\u2198";
        case 5:
            return "\u2193";
        case 6:
            return "\u2199";
        case 7:
            return "\u2190";
        case 8:
            return "\u2196";
        default:
            return ".";
        }
    }

    /**
     * after this point uniqueness check and solve operations implemented -> base
     * case --> if(order is 15 and only option is in the last correct place then it
     * is a solition increment solution count) -> first get the square -> find
     * options for square's direction -> for each of an option choose option recurse
     * reset the option
     */
    int numberofclues = 3;

    Game.Square[][] createSolutionTemplate(Game.Square[][] invalidQuestion) {
        Game.Square[][] template = new Game.Square[this.row][this.col];
        List<Integer> clues = new ArrayList<Integer>();
        if (shouldIncludeClues) {
            for (int index = 0; index < this.numberofclues; index++) {
                int random = new Random().nextInt(this.size - 2) + 2;
                clues.add(random);

            }
        }
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                template[i][j] = new Game.Square(invalidQuestion[i][j].getDirection());
            }
        }
        if (shouldIncludeClues) {
            for (int clue : clues) {
                template[clue / this.row][clue % this.col]
                        .setOrder(invalidQuestion[clue / this.row][clue % this.col].getOrder());
            }
        }
        setStartPoint(template);
        setEndPoint(template);
        return template;
    }

    class IntegerContainer {
        public Integer integer = 0;

        void incrementByOne() {
            this.integer++;
        }
    }

    Integer solveGame(Game.Square[][] invalidQuestion) {
        Game.Square[][] template = createSolutionTemplate(invalidQuestion);
        IntegerContainer container = new IntegerContainer();
        solve(template, 2, new Point(0, 0), container);
        if (container.integer == 1) {
            copyValidatedQuestion(template);
            copyAnswer(invalidQuestion);
            System.out.println("Question :");
            printGame(this.question);
            System.out.println("Answer :");
            printGame(this.answer);
        }
        return container.integer;
    }

    // this might look messy, but it should be
    void solve(Game.Square[][] template, Integer order, Point currentPosition, IntegerContainer container) {
        if (container.integer > 1)
            return;
        if (order == this.size) {
            if ((currentPosition.x == (this.row - 1)
                    && template[currentPosition.x][currentPosition.y].getDirection() == 3)
                    || (currentPosition.y == (this.col - 1)
                            && template[currentPosition.x][currentPosition.y].getDirection() == 5)
                    || (isDiogonalToLastSquare(currentPosition)
                            && template[currentPosition.x][currentPosition.y].getDirection() == 4)) {
                container.incrementByOne();
            }
            return;
        }
        int nextDirection = template[currentPosition.x][currentPosition.y].getDirection();
        List<OptionWithDirection> optionsInDirection = findOptionsInDirection(nextDirection, template, currentPosition);
        OptionWithDirection optionInDirectionClueCase = findOptionInDirectionInClueCase(nextDirection, template,
                currentPosition, order);
        if (optionInDirectionClueCase != null) {
            solve(template, order + 1, optionInDirectionClueCase.option, container);
            return;
        }
        for (OptionWithDirection optionWD : optionsInDirection) {
            Square temp = template[optionWD.option.x][optionWD.option.y];
            temp.setOrder(order);
            solve(template, order + 1, optionWD.option, container);
            template[optionWD.option.x][optionWD.option.y].setOrder(0);
        }

    }

    void copyValidatedQuestion(Game.Square[][] validQuestion) {
        if (question == null)
            question = new Square[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                question[i][j] = new Game.Square(validQuestion[i][j]);
            }
        }
    }

    void copyAnswer(Game.Square[][] validAnswer) {
        if (this.answer == null)
            this.answer = new Square[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                this.answer[i][j] = new Game.Square(validAnswer[i][j]);
            }
        }
    }

    public void stop() {
        this.shouldContinue = false;
    }

    public static void main(String[] args) {
        // int[][] test = new int[6][6];
        // List<OptionWithDirection> optionsWithDirection =
        // g.findOptionsInDirection(direction, __game__, currentPosition)
        // List<Point> points = g.findNextOptions(g.gameTable, new Point(0, 0));
        // System.out.println("\u2197");
    }

}
