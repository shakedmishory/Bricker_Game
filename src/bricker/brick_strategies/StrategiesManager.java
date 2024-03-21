package bricker.brick_strategies;

import bricker.gameobjects.BricksController;
import bricker.main.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * Manages the strategies and resources used in the Bricker game.
 */
public class StrategiesManager {

    private final Renderable ballImage;
    private final String specialPaddleToRemoveTag;
    private final Renderable heartImage;
    private final Renderable paddleImage;
    private final Renderable puckImage;
    private final Sound collisionSound;
    private final int ballSize;
    private final int paddleWidth;
    private final int paddleHeight;
    private final int wallWidth;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final Counter paddleCounter;
    private final int heartSize;
    private final Counter lifeCounter;
    private final GameObjectCollection gameObjects;
    private final BrickerGameManager brickerGameManager;
    private final String mainBallTag;
    private String basePaddleTag;
    private final String fallenHeartTag;
    private final String fallenHeartToRemoveTag;
    private final String puckTag;

    /**
     * Constructs a new StrategiesManager instance.
     *
     * @param ballImage               The image of the ball.
     * @param heartImage              The image of the heart.
     * @param paddleImage             The image of the paddle.
     * @param puckImage               The image of the puck.
     * @param collisionSound          The sound played on collision.
     * @param ballSize                The size of the ball.
     * @param paddleWidth             The width of the paddle.
     * @param paddleHeight            The height of the paddle.
     * @param wallWidth               The width of the game wall.
     * @param inputListener           The user input listener.
     * @param windowDimensions        The dimensions of the game window.
     * @param paddleCounter           The counter for the number of paddles.
     * @param heartSize               The size of the heart.
     * @param lifeCounter             The counter for the number of lives.
     * @param gameObjects             The collection of GameObjects in the game.
     * @param brickerGameManager      The game manager for the Bricker game.
     * @param mainBallTag             The tag for the main ball GameObject.
     * @param basePaddleTag           The tag for the base paddle GameObject.
     * @param fallenHeartTag          The tag for the fallen heart GameObject.
     * @param fallenHeartToRemoveTag  The tag for removing the fallen heart GameObject.
     * @param specialPaddleToRemoveTag The tag for removing the special paddle GameObject.
     * @param puckTag                 The tag for the puck GameObject.
     */
    public StrategiesManager(Renderable ballImage, Renderable heartImage, Renderable paddleImage,
                             Renderable puckImage, Sound collisionSound, int ballSize, int paddleWidth,
                             int paddleHeight, int wallWidth, UserInputListener inputListener,
                             Vector2 windowDimensions, Counter paddleCounter, int heartSize,
                             Counter lifeCounter, GameObjectCollection gameObjects,
                             BrickerGameManager brickerGameManager, String mainBallTag,
                             String basePaddleTag, String fallenHeartTag, String fallenHeartToRemoveTag,
                             String specialPaddleToRemoveTag, String puckTag) {
        this.specialPaddleToRemoveTag = specialPaddleToRemoveTag;
        this.ballImage = ballImage;
        this.heartImage = heartImage;
        this.paddleImage = paddleImage;
        this.puckImage = puckImage;
        this.collisionSound = collisionSound;
        this.ballSize = ballSize;
        this.paddleWidth = paddleWidth;
        this.paddleHeight = paddleHeight;
        this.wallWidth = wallWidth;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.paddleCounter = paddleCounter;
        this.heartSize = heartSize;
        this.lifeCounter = lifeCounter;
        this.gameObjects = gameObjects;
        this.brickerGameManager = brickerGameManager;
        this.mainBallTag = mainBallTag;
        this.basePaddleTag = basePaddleTag;
        this.fallenHeartTag = fallenHeartTag;
        this.fallenHeartToRemoveTag = fallenHeartToRemoveTag;
        this.puckTag = puckTag;
    }


    /**
     * Returns the game manager for the Bricker game.
     *
     * @return The BrickerGameManager instance.
     */
    public BrickerGameManager getBrickerGameManager() {
        return brickerGameManager;
    }

    /**
     * Returns the image of the puck.
     *
     * @return The Renderable puck image.
     */
    public Renderable getPuckImage() {
        return puckImage;
    }

    /**
     * Returns the counter for the number of lives.
     *
     * @return The Counter for lives.
     */
    public Counter getLifeCounter() {
        return lifeCounter;
    }

    /**
     * Returns the counter for the number of paddles.
     *
     * @return The Counter for paddles.
     */
    public Counter getPaddleCounter() {
        return paddleCounter;
    }

    /**
     * Returns the image of the heart.
     *
     * @return The Renderable heart image.
     */
    public Renderable getHeartImage() {
        return heartImage;
    }

    /**
     * Returns the image of the paddle.
     *
     * @return The Renderable paddle image.
     */
    public Renderable getPaddleImage() {
        return paddleImage;
    }

    /**
     * Returns the size of the ball.
     *
     * @return The size of the ball.
     */
    public int getBallSize() {
        return ballSize;
    }

    /**
     * Returns the size of the heart.
     *
     * @return The size of the heart.
     */
    public int getHeartSize() {
        return heartSize;
    }

    /**
     * Returns the height of the paddle.
     *
     * @return The height of the paddle.
     */
    public int getPaddleHeight() {
        return paddleHeight;
    }

    /**
     * Returns the width of the paddle.
     *
     * @return The width of the paddle.
     */
    public int getPaddleWidth() {
        return paddleWidth;
    }

    /**
     * Returns the width of the game wall.
     *
     * @return The width of the game wall.
     */
    public int getWallWidth() {
        return wallWidth;
    }

    /**
     * Returns the collision sound.
     *
     * @return The collision sound.
     */
    public Sound getCollisionSound() {
        return collisionSound;
    }

    /**
     * Returns the user input listener.
     *
     * @return The UserInputListener instance.
     */
    public UserInputListener getInputListener() {
        return inputListener;
    }

    /**
     * Returns the dimensions of the game window.
     *
     * @return The Vector2 window dimensions.
     */
    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    /**
     * Returns the collection of GameObjects in the game.
     *
     * @return The GameObjectCollection.
     */
    public GameObjectCollection getGameObjects() {
        return gameObjects;
    }

    /**
     * Returns the tag for the main ball GameObject.
     *
     * @return The main ball tag.
     */
    public String getMainBallTag() {
        return mainBallTag;
    }

    /**
     * Returns the tag for the base paddle GameObject.
     *
     * @return The base paddle tag.
     */
    public String getBasePaddleTag() {
        return basePaddleTag;
    }

    /**
     * Returns the tag for the fallen heart GameObject.
     *
     * @return The fallen heart tag.
     */
    public String getFallenHeartTag() {
        return fallenHeartTag;
    }

    /**
     * Returns the tag for removing the fallen heart GameObject.
     *
     * @return The tag for removing the fallen heart.
     */
    public String getFallenHeartToRemoveTag() {
        return fallenHeartToRemoveTag;
    }

    /**
     * Returns the tag for removing the special paddle GameObject.
     *
     * @return The tag for removing the special paddle.
     */
    public String getSpecialPaddleToRemoveTag() {
        return specialPaddleToRemoveTag;
    }

    /**
     * Returns the tag for the puck GameObject.
     *
     * @return The puck tag.
     */
    public String getPuckTag() {
        return puckTag;
    }
}
