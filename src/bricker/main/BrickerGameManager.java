package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Manages the game logic for the Bricker game.
 */
public class BrickerGameManager extends GameManager {
    private static final int BRICKS_ROWS = 7;
    private static final int BRICKS_COLS = 8;
    private static final float BALL_SPEED = 180;
    private static final float BRICK_HEIGHT = 15;
    private static final int BEGINNING_LIFE_NUM = 3;
    private static final int SPACE_BETWEEN_BRICKS = 5;
    private static final int BALL_SIZE = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 15;
    private static final int HEART_SIZE = 20;
    private static final int WALL_WIDTH = 6;
    private static final int PADDLE_BUFFER = 30;
    private static final int FALLEN_HEART_BUFFER = 5;
    private static final int MAX_COLLISION_FOR_CAMERA = 4;
    private static final int USER_ARGS_NUMBER = 2;
    private static final int CHANGE_DIRECTION = (-1);
    private static final float SCALE_TO_CENTER = 0.5F;
    private static final String MAIN_BALL_TAG = "MainBall";

    private static final String FALLEN_HEART_TAG = "fallenHeart";
    private static final String BASE_PADDLE_TAG = "BasePaddle";
    private static final String NAME_OF_GAME = "Bricker";
    private static final String WIN_MESSAGE = "You Win!";
    private static final String LOSE_MESSAGE = "You Lose!";
    private static final String PLAY_AGAIN_MESSAGE = " Play Again?";
    private static final String BALL_PATH = "assets/ball.png";
    private static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final String PADDLE_PATH = "assets/paddle.png";
    private static final String BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String BRICK_PATH = "assets/brick.png";
    private static final String HEART_PATH = "assets/heart.png";
    private static final String FALLEN_HEART_TO_REMOVE_TAG = "fallenHeartToRemove";
    private static final String SPECIAL_PADDLE_TO_REMOVE_TAG = "specialPaddleToRemove";
    private static final int COUNTERS_LAYER = Layer.BACKGROUND;
    private static final int BACKGROUND_LAYER = Layer.BACKGROUND;
    private static final int WALLS_LAYER = Layer.STATIC_OBJECTS;
    private static final String PUCK_PATH = "assets/mockBall.png";
    private static final String PUCK_TAG = "puck";
    private final int bricksRow;
    private final int bricksCol;
    private boolean isCameraOpen;
    private Counter cameraCounter;
    private Counter lifeCounter;
    private Counter paddleCounter;
    private Ball ball;
    private Vector2 windowDimensions;
    private ImageReader imageReader;
    private Renderable ballImage;
    private Renderable heartImage;
    private Renderable paddleImage;
    private Renderable brickImage;
    private Renderable puckImage;

    private WindowController windowController;
    private UserInputListener inputListener;
    private BricksController bricksController;
    private Sound collisionSound;

    /**
     * Constructs a new BrickerGameManager instance with the specified window title, window dimensions,
     * bricks row, and bricks column.
     *
     * @param windowTitle    The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param bricksRow      The number of rows of bricks in the game.
     * @param bricksCol      The number of columns of bricks in the game.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int bricksRow, int bricksCol) {
        super(windowTitle, windowDimensions);
        this.bricksRow = bricksRow;
        this.bricksCol = bricksCol;
        this.isCameraOpen = false;
    }


    /**
     * Creates the ball GameObject and adds it to the game objects' collection.
     */
    private void createBall() {
        Ball ball = new Ball(Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), this.ballImage,
                this.collisionSound);
        ball.setTag(MAIN_BALL_TAG);
        this.ball = ball;
        resetBall();
        this.gameObjects().addGameObject(ball);
    }

    /**
     * Creates the paddle GameObject and adds it to the game objects' collection.
     */
    private void createPaddle() {
        paddleCounter = new Counter(1);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                this.paddleImage,
                inputListener, windowDimensions, WALL_WIDTH);
        paddle.setTag(BASE_PADDLE_TAG);
        paddle.setCenter(new Vector2(windowDimensions.x() * SCALE_TO_CENTER,
                (int) windowDimensions.y() - PADDLE_BUFFER));
        gameObjects().addGameObject(paddle);
    }

    /**
     * Creates the walls GameObjects and adds them to the game objects' collection.
     */
    private void createWalls() {
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(WALL_WIDTH, windowDimensions.y()),
                null);
        gameObjects().addGameObject(leftWall, WALLS_LAYER);
        // right wall
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - WALL_WIDTH, 0),
                new Vector2(WALL_WIDTH, windowDimensions.y()), null);
        gameObjects().addGameObject(rightWall, WALLS_LAYER);
        // upper wall
        GameObject upperWall = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), WALL_WIDTH),
                null);
        gameObjects().addGameObject(upperWall, WALLS_LAYER);
    }

    /**
     * Creates the background GameObject and adds it to the game objects' collection.
     */
    private void createBackground() {
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(windowDimensions), backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, BACKGROUND_LAYER);
    }


    /**
     * Creates the bricks matrix using the BricksController and adds it to the game objects collection.
     */
    private void createBricksMatrix() {
        StrategiesManager strategiesManager = new StrategiesManager(this.ballImage, this.heartImage,
                this.paddleImage, this.puckImage, this.collisionSound,
                BALL_SIZE, PADDLE_WIDTH, PADDLE_HEIGHT, WALL_WIDTH, inputListener, windowDimensions,
                paddleCounter, HEART_SIZE, lifeCounter, gameObjects(), this, MAIN_BALL_TAG,
                BASE_PADDLE_TAG,
                FALLEN_HEART_TAG, FALLEN_HEART_TO_REMOVE_TAG, SPECIAL_PADDLE_TO_REMOVE_TAG, PUCK_TAG);
        float brickWidth = (windowDimensions.x() - (float) (SPACE_BETWEEN_BRICKS * (bricksCol + 1))) /
                bricksCol;
        this.bricksController = new BricksController(this.brickImage, SPACE_BETWEEN_BRICKS, bricksRow,
                bricksCol, brickWidth, BRICK_HEIGHT, gameObjects(), strategiesManager);
    }


    /**
     * Creates the numeric and graphic counters for displaying life count.
     */
    private void createCounters() {
        TextRenderable textRenderable = new TextRenderable("");
        GameObject numericCounter = new NumericCounter(lifeCounter, windowDimensions, textRenderable);
        gameObjects().addGameObject(numericCounter, COUNTERS_LAYER);

        GameObject graphicCounter = new GraphicCounter(lifeCounter, BEGINNING_LIFE_NUM, heartImage,
                windowDimensions, gameObjects(), HEART_SIZE);
        gameObjects().addGameObject(graphicCounter, COUNTERS_LAYER);
    }

    /**
     * Initializes the game with the provided resources and settings.
     *
     * @param imageReader      The image reader for loading images.
     * @param soundReader      The sound reader for loading sounds.
     * @param inputListener    The user input listener for handling user input.
     * @param windowController The window controller for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.ballImage = imageReader.readImage(BALL_PATH, true);
        this.brickImage = imageReader.readImage(BRICK_PATH, false);
        this.heartImage = imageReader.readImage(HEART_PATH, true);
        this.paddleImage = imageReader.readImage(PADDLE_PATH, true);
        this.puckImage = imageReader.readImage(PUCK_PATH, true);
        this.lifeCounter = new Counter(BEGINNING_LIFE_NUM);
        this.cameraCounter = new Counter();

        // create game objects
        createBall();
        createPaddle();
        createWalls();
        createBackground();
        createCounters();
        createBricksMatrix();
    }

    /**
     * Checks if the given GameObject is a special paddle and removes it from the game if it is.
     *
     * @param gameObject The GameObject to check.
     */
    private void checkSpecialPaddle(GameObject gameObject) {
        if (gameObject.getTag().equals(SPECIAL_PADDLE_TO_REMOVE_TAG)) {
            gameObjects().removeGameObject(gameObject);
            paddleCounter.decrement();
        }
    }


    /**
     * Checks the limits of the puck GameObject and removes it from the game if it exceeds the window bounds.
     *
     * @param gameObject The puck GameObject to check.
     */
    private void checkPuckLimits(GameObject gameObject) {
        if (gameObject.getTag().equals(PUCK_TAG) && gameObject.getCenter().y() > windowDimensions.y()) {
            gameObjects().removeGameObject(gameObject);
        }

    }

    /**
     * Checks the limits of the fallen heart GameObject and removes it from the game if it exceeds the
     * window bounds.
     *
     * @param gameObject The fallen heart GameObject to check.
     */
    private void checkForHeartLimits(GameObject gameObject) {
        if (gameObject.getTag().equals(FALLEN_HEART_TAG)) {
            if (gameObject.getCenter().y() > windowDimensions.y() - FALLEN_HEART_BUFFER) {
                gameObjects().removeGameObject(gameObject);
            }
        } else if (gameObject.getTag().equals(FALLEN_HEART_TO_REMOVE_TAG)) {
            gameObjects().removeGameObject(gameObject);
        }
    }

    /**
     * Checks if the camera should be enabled based on the ball's collision counter.
     */
    private void checkCamera() {
        if (this.camera() != null && !isCameraOpen) {
            isCameraOpen = true;
            cameraCounter.increaseBy(ball.getCollisionCounter());
        }
        if (ball.getCollisionCounter() - cameraCounter.value() >= MAX_COLLISION_FOR_CAMERA) {
            setCamera(null);
            cameraCounter.reset();
            isCameraOpen = false;
        }
    }

    /**
     * Checks if the game should end based on the ball's position and the remaining life count.
     * Displays a game over prompt and resets the game if necessary.
     */
    private void checkForGameEnd() {
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if (bricksController.getBrickCounter().value() <= 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = WIN_MESSAGE;
        }
        if (ballHeight > windowDimensions.y()) {
            lifeCounter.decrement();
            if (lifeCounter.value() == 0) {
                prompt = LOSE_MESSAGE;
            } else {
                resetBall();
            }
        }
        if (!prompt.isEmpty()) {
            prompt += PLAY_AGAIN_MESSAGE;
            if (windowController.openYesNoDialog(prompt)) {
                lifeCounter.reset();
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * Resets the ball's position and velocity to the initial state.
     */
    private void resetBall() {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random random = new Random();
        if (random.nextBoolean()) {
            ballVelX *= CHANGE_DIRECTION;
        }
        if (random.nextBoolean()) {
            ballVelY *= CHANGE_DIRECTION;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        ball.setCenter(windowDimensions.mult(SCALE_TO_CENTER));
    }

    /**
     * Updates the game state based on the elapsed time since the last update.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        checkCamera();

        for (GameObject gameObject : gameObjects()) {
            checkSpecialPaddle(gameObject);
            checkForHeartLimits(gameObject);
            checkPuckLimits(gameObject);
        }
    }


    /**
     * The main method of the game.
     * Parses the user arguments for brick rows and columns, and initializes the game accordingly.
     *
     * @param args The user arguments.
     */
    public static void main(String[] args) {
        if (args.length == USER_ARGS_NUMBER) {
            int bricksCol = Integer.parseInt(args[0]);
            int bricksRow = Integer.parseInt(args[1]);
            new BrickerGameManager(NAME_OF_GAME, new Vector2(700, 500), bricksRow, bricksCol).run();
        } else {
            new BrickerGameManager(NAME_OF_GAME, new Vector2(700, 500),
                    BRICKS_ROWS, BRICKS_COLS).run();
        }
    }
}
