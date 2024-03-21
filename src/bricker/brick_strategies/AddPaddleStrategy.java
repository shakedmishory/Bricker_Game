package bricker.brick_strategies;

import bricker.gameobjects.SpecialPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a collision strategy for adding a paddle to the game.
 * This strategy creates a special paddle GameObject upon collision,
 * maximum one extra paddle at time.
 */
public class AddPaddleStrategy  implements CollisionStrategy{

    /**
     * The maximum number of paddles allowed in the game.
     */
     private static final int MAX_PADDLE_AMOUNT = 2;

    /**
     * The scale factor for positioning the paddle.
     */
    private static final float SCALE_PADDLE_POSITION = 0.5F;
    /**
     * The collection of GameObjects in the game.
     */
    private final GameObjectCollection gameObjects;

    /**
     * A renderable representing the paddle.
     */
    private final Renderable paddleImage;

    /**
     * The width of the paddle.
     */
    private final int paddleWidth;

    /**
     * The height of the paddle.
     */
    private final int paddleHeight;

    /**
     * User input listener for paddle control.
     */
    private final UserInputListener inputListener;

    /**
     * The dimensions of the game window.
     */
    private final Vector2 windowDimensions;

    /**
     * The counter for the number of paddles.
     * Maximum 2 at a time.
     */
    private Counter paddleCounter;

    /**
     * The width of the game wall.
     */
    private final float wallWidth;

    /**
     * The basic collision strategy for handling collisions.
     */
    private final BasicCollisionStrategy basicCollisionStrategy;

    /**
     * The tag for removing the special paddle GameObject.
     */
    private final String specialPaddleToRemove;

    /**
     * Constructs a new AddPaddleStrategy instance.
     *
     * @param gameObjects            The collection of GameObjects in the game.
     * @param paddleImage            A renderable representing the paddle.
     * @param paddleWidth            The width of the paddle.
     * @param paddleHeight           The height of the paddle.
     * @param inputListener          User input listener for paddle control.
     * @param windowDimensions       The dimensions of the game window.
     * @param paddleCounter          The counter for the number of paddles. Maximum 2 at a time.
     * @param wallWidth              The width of the game wall.
     * @param basicCollisionStrategy The basic collision strategy for handling collisions.
     * @param specialPaddleToRemove  The tag for removing the special paddle GameObject.
     */
    public AddPaddleStrategy(GameObjectCollection gameObjects, Renderable paddleImage,
                             int paddleWidth, int paddleHeight, UserInputListener inputListener,
                             Vector2 windowDimensions, Counter paddleCounter, float wallWidth,
                             BasicCollisionStrategy basicCollisionStrategy, String specialPaddleToRemove) {
        this.gameObjects = gameObjects;
        this.paddleImage = paddleImage;
        this.paddleWidth = paddleWidth;
        this.paddleHeight = paddleHeight;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.paddleCounter = paddleCounter;
        this.wallWidth = wallWidth;
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.specialPaddleToRemove = specialPaddleToRemove;
    }

    /**
     * Handles collision for add paddle strategy case.
     * Upon collision, creates a special paddle GameObject if the maximum number
     * of paddles has not been reached.
     *
     * @param firstObj  The first GameObject involved in the collision - a brick.
     * @param secondObj The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject firstObj, GameObject secondObj) {
        basicCollisionStrategy.onCollision(firstObj, secondObj);
        if (paddleCounter.value() < MAX_PADDLE_AMOUNT) {
            GameObject secondPaddle = new SpecialPaddle(Vector2.ZERO, new Vector2(paddleWidth,
                    this.paddleHeight),
                    paddleImage, inputListener, windowDimensions, wallWidth, specialPaddleToRemove);
            secondPaddle.setCenter(new Vector2(windowDimensions.x() * SCALE_PADDLE_POSITION,
                    windowDimensions.y() * SCALE_PADDLE_POSITION));
            paddleCounter.increment();
            gameObjects.addGameObject(secondPaddle);
        }
    }


}
