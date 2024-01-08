//package com.bcs2024.knapsack.algorithm;
//
//import model.PentominoBuilder;
//import model.PentominoDatabase;
//import model.UI;
//import ui.BotOptionsScreen;
//import ui.GameOverScreen;
//import ui.PauseScreen;
////
////import javax.swing.Timer;
////import javax.swing.*;
////import java.awt.*;
////import java.io.File;
////import java.io.FileWriter;
////import java.util.List;
//import java.util.*;
//
///**
// * The {@code com.bcs2024.knapsack.algorithm.Game} class represents the main game logic for Pentris.
// * It handles game initialization, piece movement, scoring, and game state management.
// */
//public class Game {
//
//    public final int VERTICAL_GRID_SIZE = 15;
//    public final int HORIZONTAL_GRID_SIZE = 5;
//    public int score;
//    public int[][] field;
//    public int[][] activeShape;
//    public int actualID;
//    public int actualX;
//    public int actualY;
//    public int actualRotation;
//    public int maxHeight;
//    public List<Integer> upComingShapes;
//    public int waitingMS;
//    public int highestScore;
//    public boolean gameOver = false;
//    private final Random random = new Random();
//    public UI ui;
//    public Bot bot;
//    public Timer gameTimer;
//    private GBot gBot;
//    private static List<double[]> allWeights;
//    private double[] actualWeights;
//    public static int count;
//    public static int repetitions = 100;
//    public static int countReps = 0;
//    public static boolean sorted = false;
//    private static final char[] SEQUENCE = {'I', 'U', 'L', 'X', 'Y', 'Z', 'W', 'P', 'F', 'T', 'N', 'V'};
//    private int countPiece = 0;
//    public static PlayMode playMode;
//    private GameOverScreen gameOverScreen;
//    private BotOptionsScreen botOptions;
//
//    /**
//     * Constructs a new com.bcs2024.knapsack.algorithm.Game instance.
//     * Initializes the game field, upcoming shapes, and sets up the game UI.
//     */
//    public Game() {
//        PentominoBuilder.makeDatabase();
//        this.waitingMS = 400;
//        this.upComingShapes = new ArrayList<>();
//        this.actualX = 0;
//        this.actualY = 0;
//        this.actualRotation = 0;
//        this.score = 0;
//        this.maxHeight = 0;
//        this.highestScore = getHighestScore();
//        count = 0;
//        allWeights = new ArrayList<>();
//        this.actualWeights = new double[6];
//
//        for (int i = 0; i < 3; i++) {
//            int temp = 0;
//            if (sorted) {
//                temp = charToId(SEQUENCE[this.countPiece]);
//                this.countPiece++;
//            } else {
//                temp = random.nextInt(12);
//            }
//            upComingShapes.add(temp);
//        }
//        this.field = new int[VERTICAL_GRID_SIZE][HORIZONTAL_GRID_SIZE];
//        initializeField(this.field);
//
//        final JFrame frame = new JFrame("Pentris");
//        this.ui = new UI(VERTICAL_GRID_SIZE, HORIZONTAL_GRID_SIZE, 40, this, frame);
//
//        initializePiece();
//
//        if (playMode == PlayMode.TrainTheBot) {
//            this.gBot = new GBot();
//            if (countReps == 0) {
//                GBot.firstWeights();
//            }
//            this.gBot.initialiseWeights();
//            allWeights = this.gBot.weights;
//            GBot.repetitions = allWeights.size();
//        }
//
//        this.bot = new Bot(this);
//    }
//
//    /**
//     * Converts a character representing a pentomino piece to its corresponding ID.
//     *
//     * @param letter The character representing a pentomino piece.
//     * @return The ID of the pentomino piece.
//     */
//    private int charToId(final char letter) {
//        return switch (letter) {
//            case 'X' -> 0;
//            case 'I' -> 1;
//            case 'Z' -> 2;
//            case 'T' -> 3;
//            case 'U' -> 4;
//            case 'V' -> 5;
//            case 'W' -> 6;
//            case 'Y' -> 7;
//            case 'L' -> 8;
//            case 'P' -> 9;
//            case 'N' -> 10;
//            case 'F' -> 11;
//            default -> -1;
//        };
//    }
//
//    /**
//     * Enumeration for different play modes in the game.
//     */
//    public enum PlayMode {
//        SinglePlayer,
//        RBBot,
//        TrainedBot,
//        TrainTheBot,
//    }
//
//    /**
//     * Starts the main game loop.
//     * This loop handles piece movement and game state updates.
//     */
//    public void startGameLoop() {
//        gameTimer = new Timer(waitingMS, e -> {
//            if (!canMoveDown()) {
//                clearRow();
//                maxHeight = getMaxHeight();
//                initializePiece();
//                waitingMS = 400;
//            } else {
//                moveDown();
//            }
//
//            SwingUtilities.invokeLater(() -> ui.setState(field));
//        });
//
//        gameTimer.start();
//    }
//
//    /**
//     * Starts the game with bot play.
//     * The bot will automatically play the game based on predefined logic.
//     */
//    public void botPlay() {
//        greedyBotPlay();
//        final int wait = 0;
//        gameTimer = new Timer(wait, e -> {
//            if (!canMoveDown()) {
//                clearRow();
//                maxHeight = getMaxHeight();
//                initializePiece();
//                greedyBotPlay();
//            } else {
//                moveDown();
//            }
//
//            SwingUtilities.invokeLater(() -> ui.setState(field));
//        });
//
//        gameTimer.start();
//    }
//
//    /**
//     * Starts the game based on the current play mode.
//     */
//    public void startGame() {
//        botPlay();
//    }
//
//    /**
//     * Retrieves the best position and rotation for the current piece based on the bot's strategy.
//     * This method is used when the game is played in bot mode.
//     *
//     * @return An array of two integers, where the first integer is the column position and the second is the rotation index.
//     */
//    private int[] getGreedyPositions() {
//        return bot.calculate(this.actualWeights);
//    }
//
//    /**
//     * Exits the bot options window.
//     * This method is called to close or exit the bot options screen.
//     */
//    public void exitBotOptionsWindow() {
//        botOptions.exitWindow();
//    }
//
//    /**
//     * Executes the bot's play strategy.
//     * This method is called in each iteration of the game loop when the game is in bot mode.
//     */
//    private void greedyBotPlay() {
//        final int[] res = getGreedyPositions();
//
//        final int maxIterations = 1000;
//
//        for (int i = 0; i < maxIterations && this.actualY != 0; i++) {
//            if (this.actualY < 0) {
//                moveRight();
//            } else {
//                moveLeft();
//            }
//        }
//
//        for (int i = 0; i < maxIterations && this.actualRotation != res[1]; i++) {
//
//            rotateShape();
//            SwingUtilities.invokeLater(() -> ui.setState(field));
//        }
//
//        for (int i = 0; i < maxIterations && this.actualY != res[0]; i++) {
//            if (this.actualY < res[0]) {
//                moveRight();
//            } else {
//                moveLeft();
//            }
//            SwingUtilities.invokeLater(() -> ui.setState(this.field));
//        }
//    }
//
//    /**
//     * Initializes the next piece to be played in the game.
//     * This method selects the next piece from the upcoming shapes, sets its position, and checks if it can be placed on the game field.
//     */
//    private void initializePiece() {
//        final int id = upComingShapes.get(0);
//        upComingShapes.remove(0);
//        if (sorted) {
//            upComingShapes.add(charToId(SEQUENCE[this.countPiece]));
//            if (this.countPiece < 11) {
//                this.countPiece++;
//            } else {
//                this.countPiece = 0;
//            }
//        } else {
//            upComingShapes.add(random.nextInt(12));
//        }
//        ui.makeUpcomingMatrix(upComingShapes);
//        actualRotation = 0;
//        activeShape = PentominoDatabase.data[id][actualRotation];
//        actualX = 0;
//        actualY = HORIZONTAL_GRID_SIZE / 2 - activeShape[0].length / 2;
//        actualID = id;
//
//        if (canPlace(field, activeShape, actualX, actualY)) {
//            addPiece(field, activeShape, actualID, actualX, actualY);
//        } else {
//            stopGame();
//        }
//    }
//
//    /**
//     * Checks if the current piece can move down.
//     * This method determines if there is space for the current piece to move one step down on the game field.
//     *
//     * @return {@code true} if the piece can move down; {@code false} otherwise.
//     */
//    public boolean canMoveDown() {
//        removePiece(field, activeShape, actualX, actualY);
//        if (canPlace(field, activeShape, actualX + 1, actualY)) {
//            addPiece(field, activeShape, actualID, actualX, actualY);
//            return true;
//        }
//        addPiece(field, activeShape, actualID, actualX, actualY);
//        return false;
//    }
//
//    /**
//     * Moves the current piece one step down.
//     * This method updates the position of the current piece on the game field to one step lower.
//     */
//    public void moveDown() {
//        if (canMoveDown()) {
//            removePiece(field, activeShape, actualX, actualY);
//            addPiece(field, activeShape, actualID, actualX + 1, actualY);
//            actualX++;
//        }
//    }
//
//    /**
//     * Moves the current piece one step to the left.
//     * This method updates the position of the current piece on the game field to one step left.
//     */
//    public void moveLeft() {
//        removePiece(field, activeShape, actualX, actualY);
//
//        if (canPlace(field, activeShape, actualX, actualY - 1)) {
//            addPiece(field, activeShape, actualID, actualX, actualY - 1);
//            actualY--;
//        } else {
//            addPiece(field, activeShape, actualID, actualX, actualY);
//        }
//
//        SwingUtilities.invokeLater(() -> ui.setState(field));
//    }
//
//    /**
//     * Moves the current piece one step to the right.
//     * This method updates the position of the current piece on the game field to one step right.
//     */
//    public void moveRight() {
//        removePiece(field, activeShape, actualX, actualY);
//
//        if (canPlace(field, activeShape, actualX, actualY + 1)) {
//            addPiece(field, activeShape, actualID, actualX, actualY + 1);
//            actualY++;
//        } else {
//            addPiece(field, activeShape, actualID, actualX, actualY);
//        }
//
//        SwingUtilities.invokeLater(() -> ui.setState(field));
//    }
//
//    /**
//     * Moves the current piece down as far as possible.
//     * This method rapidly moves the current piece down until it reaches the bottom or lands on another piece.
//     */
//    public void moveFastDown() {
//        while (canMoveDown()) {
//            moveDown();
//        }
//    }
//
//    /**
//     * Rotates the current piece to its next rotation state.
//     * This method changes the orientation of the current piece, if possible, to its next rotation.
//     */
//    public void rotateShape() {
//        removePiece(field, activeShape, actualX, actualY);
//        final int temp = actualRotation;
//        final int newRotation = (actualRotation + 1) % (PentominoDatabase.data[actualID].length);
//        final int[][] newShape = PentominoDatabase.data[actualID][newRotation];
//
//        if ((playMode == PlayMode.SinglePlayer) && (actualY - (newShape[0].length - this.activeShape[0].length) / 2 >= 0)) {
//            actualY -= (newShape[0].length - this.activeShape[0].length) / 2;
//        }
//
//        if (canPlace(field, newShape, actualX, actualY)) {
//            actualRotation = newRotation;
//            activeShape = newShape;
//            addPiece(field, activeShape, actualID, actualX, actualY);
//        } else {
//            activeShape = PentominoDatabase.data[this.actualID][temp];
//            addPiece(field, activeShape, actualID, actualX, actualY);
//        }
//
//        SwingUtilities.invokeLater(() -> ui.setState(field));
//    }
//
//    /**
//     * Clears any filled rows on the game field.
//     * This method checks for and clears any rows that are completely filled, adjusting the game field accordingly.
//     */
//    private void clearRow() {
//        final List<Integer> toClear = new ArrayList<>();
//        for (int i = 0; i < field.length; i++) {
//            boolean canClear = true;
//            for (int j = 0; j < field[0].length; j++) {
//                if (field[i][j] == -1) {
//                    canClear = false;
//                    break;
//                }
//            }
//
//            if (canClear) {
//                toClear.add(i);
//            }
//        }
//
//        if (!toClear.isEmpty()) {
//            for (final int i : toClear) {
//                moveDownByOne(i);
//                score += 1;
//            }
//        }
//
//        SwingUtilities.invokeLater(() -> ui.setState(field));
//    }
//
//    /**
//     * Moves all rows above a specified position down by one.
//     * This method is used as part of the row clearing process.
//     *
//     * @param pos The position above which rows should be moved down.
//     */
//    private void moveDownByOne(final int pos) {
//        for (int j = 0; j < HORIZONTAL_GRID_SIZE; j++) {
//            field[pos][j] = -1;
//        }
//        for (int i = pos - 1; i > 0; i--) {
//            for (int j = 0; j < HORIZONTAL_GRID_SIZE; j++) {
//                if (field[i][j] != -1) {
//                    field[i + 1][j] = field[i][j];
//                    field[i][j] = -1;
//                }
//            }
//        }
//    }
//
//    /**
//     * Calculates the maximum height reached by the pieces on the game field.
//     * This method determines the height of the tallest stack of pieces.
//     *
//     * @return The maximum height reached on the game field.
//     */
//    private int getMaxHeight() {
//        for (int i = 0; i < VERTICAL_GRID_SIZE; i++) {
//            for (int j = 0; j < HORIZONTAL_GRID_SIZE; j++) {
//                if (field[i][j] != -1) {
//                    return (VERTICAL_GRID_SIZE - i);
//                }
//            }
//        }
//        return 0;
//    }
//
//    /**
//     * Initializes the game field.
//     * This method sets up the game field, typically filling it with default values.
//     *
//     * @param field The game field represented as a 2D array.
//     */
//    public void initializeField(final int[][] field) {
//        for (int i = 0; i < field.length; i++) {
//            for (int j = 0; j < field[0].length; j++) {
//                field[i][j] = -1;
//            }
//        }
//    }
//
//    /**
//     * Adds a piece to the game field.
//     * This method places a piece on the game field at the specified position.
//     *
//     * @param field  The game field represented as a 2D array.
//     * @param piece  The piece to be added, represented as a 2D array.
//     * @param pentID The ID of the pentomino piece.
//     * @param row    The row position where the piece is to be placed.
//     * @param col    The column position where the piece is to be placed.
//     */
//    public void addPiece(final int[][] field, final int[][] piece, final int pentID, final int row, final int col) {
//        for (int i = 0; i < piece.length; i++) {
//            for (int j = 0; j < piece[0].length; j++) {
//                if (piece[i][j] != 0) {
//                    field[i + row][j + col] = pentID;
//                }
//            }
//        }
//    }
//
//    /**
//     * Removes a piece from the game field.
//     * This method clears the position of a piece on the game field, effectively removing it.
//     *
//     * @param field The game field represented as a 2D array.
//     * @param piece The piece to be removed, represented as a 2D array.
//     * @param row   The row position of the piece.
//     * @param col   The column position of the piece.
//     */
//    public void removePiece(final int[][] field, final int[][] piece, final int row, final int col) {
//        for (int i = 0; i < piece.length; i++) {
//            for (int j = 0; j < piece[0].length; j++) {
//                if (piece[i][j] != 0) {
//                    field[i + row][j + col] = -1;
//                }
//            }
//        }
//    }
//
//    /**
//     * Checks if a piece can be placed at a specified position on the game field.
//     * This method determines if the specified position is free for the piece to occupy without overlapping other pieces or going out of bounds.
//     *
//     * @param field The game field represented as a 2D array.
//     * @param piece The piece to be placed, represented as a 2D array.
//     * @param row   The row position where the piece is to be placed.
//     * @param col   The column position where the piece is to be placed.
//     * @return {@code true} if the piece can be placed at the specified position; {@code false} otherwise.
//     */
//    public boolean canPlace(final int[][] field, final int[][] piece, final int row, final int col) {
//        if (row < 0 ||
//                col < 0 ||
//                row + piece.length > field.length ||
//                col + piece[0].length > field[0].length) {
//            return false;
//        }
//
//        for (int i = 0; i < piece.length; i++) {
//            for (int j = 0; j < piece[0].length; j++) {
//                if (piece[i][j] != 0 && field[i + row][j + col] != -1) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Stops the current game.
//     * This method is called when the game is over, either when the player loses or chooses to stop. It handles game over logic, records scores, and transitions to the game over screen.
//     */
//    public void stopGame() {
//        if (!this.gameOver) {
//            gameTimer.stop();
//            this.gameOver = true;
//            Bot.results += this.score + " - " + Arrays.toString(this.bot.weights) + "\n";
//            this.bot.writeResults();
//            recordHighestScore();
//            count++;
//            if (playMode == PlayMode.TrainTheBot) {
//                ui.exitWindow();
//                GBot.results.add(this.score);
//                System.out.println(
//                        "bot number " + count + " has score " + this.score + ": " + Arrays.toString(this.actualWeights));
//            }
//            if (count == repetitions && playMode == PlayMode.TrainTheBot) {
//                System.out.println(
//                        "-------FINISHED " + (countReps + 1) + " ROUND-------");
//                countReps++;
//                GBot.rewriteResults();
//                GBot.combineResults();
//                runGBot();
//            }
//            if (playMode == PlayMode.SinglePlayer || playMode == PlayMode.TrainedBot || playMode == PlayMode.RBBot) {
//                final Point location = ui.getFrameLocation();
//                gameOverScreen = new GameOverScreen(this.score);
//                gameOverScreen.setLocation(location);
//                ui.exitWindow();
//
//            }
//        }
//    }
//
//    /**
//     * Pauses the current game.
//     * This method is called to pause the game, typically when the pause button is pressed. It stops the game timer and displays the pause screen.
//     */
//    public void pauseGame() {
//        this.gameTimer.stop();
//        final PauseScreen pause = new PauseScreen(ui.getMainFrame(), this); // Use the frame from UI
//        pause.display();
//
//    }
//
//    /**
//     * Resumes the current game.
//     * This method is called to resume the game from a paused state. It restarts the game timer and hides the pause screen.
//     */
//    public void resumeGame() {
//        this.gameTimer.start();
//    }
//
//    /**
//     * Exits the game prematurely.
//     * This method is called to exit the game before it naturally concludes, such as when the player chooses to quit to the main menu.
//     */
//    public void exitGamePrematurely() {
//        ui.exitWindow();
//    }
//
//    /**
//     * Records the highest score achieved in the game.
//     * This method checks if the current score is higher than the recorded highest score and updates the record if necessary.
//     */
//    private void recordHighestScore() {
//        try {
//            final File myObj = new File("src/main/java/metrics/score.txt");
//
//            final Scanner scan = new Scanner(myObj);
//            int recordedScore = 0;
//            if (scan.hasNextLine()) {
//                recordedScore = Integer.parseInt(scan.nextLine());
//            }
//
//            if (score > recordedScore) {
//                final FileWriter scoreWrite = new FileWriter("src/main/java/metrics/score.txt");
//                scoreWrite.write(Integer.toString(score));
//                scoreWrite.close();
//                scan.close();
//            }
//        } catch (final Exception ex) {
//            System.out.println(ex);
//        }
//    }
//
//    /**
//     * Retrieves the highest score recorded.
//     * This method reads the highest score from a file and returns it.
//     *
//     * @return The highest score recorded.
//     */
//    private int getHighestScore() {
//        int recordedScore = 0;
//        try {
//            final File myObj = new File("src/main/java/metrics/score.txt");
//
//            final Scanner scan = new Scanner(myObj);
//            if (scan.hasNextLine()) {
//                recordedScore = Integer.parseInt(scan.nextLine());
//            }
//            scan.close();
//        } catch (final Exception ex) {
//            System.out.println(ex);
//        }
//
//        return recordedScore;
//    }
//
//    /**
//     * Runs the genetic bot for training purposes.
//     * This method initializes and starts the genetic bot, which is used to train and improve the bot's performance.
//     */
//    private static void runGBot() {
//        SwingUtilities.invokeLater(() -> {
//            for (int i = 0; i < repetitions; i++) {
//                final Game game = new Game();
//                game.actualWeights = allWeights.get(i);
//                game.startGame();
//            }
//        });
//    }
//
//    /**
//     * Handles input for selecting the play mode.
//     * This method sets the game's play mode based on the input parameter.
//     *
//     * @param param The input parameter representing the selected play mode.
//     */
//    public static void handleInput(final int param) {
//        switch (param) {
//            case 1 -> playMode = PlayMode.SinglePlayer;
//            case 2 -> playMode = PlayMode.RBBot;
//            case 3 -> playMode = PlayMode.TrainedBot;
//            case 4 -> playMode = PlayMode.TrainTheBot;
//            default -> System.out.println("Not Valid Action");
//        }
//    }
//
//    /**
//     * Initializes the game based on the selected play mode.
//     * This method starts the game loop or bot play based on the chosen mode of play.
//     *
//     * @param input The input parameter representing the selected play mode.
//     */
//    public static void initialiseGame(final int input) {
//        handleInput(input);
//        if (playMode != null) {
//            switch (playMode) {
//                case TrainTheBot -> runGBot();
//                case SinglePlayer -> {
//                    final Game game = new Game();
//                    game.startGameLoop();
//
//                    break;
//                }
//                default -> {
//                    final Game game = new Game();
//                    game.botPlay();
//                }
//            }
//        }
//    }
//}
