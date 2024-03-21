package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

/**
 * Represents a collision strategy that manage the game camera.
 * This strategy sets up the game camera to follow the ball.
 * happens only when a collision involves the main ball occurs.
 */
public class CameraStrategy implements CollisionStrategy {
    /**
     * The scale factor for positioning the camera.
     */
    private static final float CAMERA_POSITION_SCALE = 1.2f;

    /**
     * The game manager managing the game.
     */
    private final GameManager gameManager;

    /**
     * The dimensions of the game window.
     */
    private final Vector2 windowDimensions;

    /**
     * The basic collision strategy for handling collisions.
     */
    private final BasicCollisionStrategy basicCollisionStrategy;

    /**
     * The tag for identifying the main ball GameObject.
     */
    private final String mainBallTag;



    /**
     * Constructs a new CameraStrategy instance.
     *
     * @param brickerGameManager     The BrickerGameManager instance managing the game.
     * @param windowDimensions       The window dimensions passed as a vector.
     * @param basicCollisionStrategy The basic collision strategy for handling collisions.
     * @param mainBallTag            The tag identifying the main ball GameObject.
     */
    public CameraStrategy(BrickerGameManager brickerGameManager, Vector2 windowDimensions,
                          BasicCollisionStrategy basicCollisionStrategy, String mainBallTag) {
        this.gameManager = brickerGameManager;
        this.windowDimensions = windowDimensions;
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.mainBallTag = mainBallTag;
    }


    /**
     * Handles collision in case of camera strategy.
     * Executes the basic collision strategy and sets up the game camera to follow the ball, only if the
     *  collision involves the main ball.
     *
     * @param firstObj  The first GameObject involved in the collision - a brick.
     * @param secondObj The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject firstObj, GameObject secondObj) {
        basicCollisionStrategy.onCollision(firstObj, secondObj);
        if ((secondObj.getTag().equals(mainBallTag)) && (gameManager.camera() == null)) {
            gameManager.setCamera(new Camera(secondObj, Vector2.ZERO,
                    windowDimensions.mult(CAMERA_POSITION_SCALE),
                    windowDimensions));
        }
    }

}