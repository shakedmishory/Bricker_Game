package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import bricker.brick_strategies.puck_startegies.DirectionStrategy;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a collision strategy for adding pucks.
 * This strategy creates two puck GameObjects upon collision,
 * each with a specified initial direction strategy.
 */
public class AddPucksStrategy implements CollisionStrategy {

    private static final float SCALE_BALL_SIZE = 0.75F;
    private  final Renderable puckImage;
    private final Sound collisionSound;
    private final int ballSize;
    private final GameObjectCollection gameObjects;
    private final DirectionStrategy[] puckStrategies;
    private final BasicCollisionStrategy basicCollisionStrategy;
    private final String puckTag;

    /**
     * Constructs a new AddPucksStrategy instance.
     *
     * @param puckImage             The renderable representing the puck.
     * @param collisionSound        The sound played upon collision.
     * @param ballSize              Size of the puck.
     * @param gameObjects           The collection of GameObjects in the game.
     * @param puckStrategies        The array of direction strategies for initial movement direction of puck.
     * @param basicCollisionStrategy The basic collision strategy for handling collisions.
     * @param puckTag               The tag for identifying the pucks.
     */
    public AddPucksStrategy(Renderable puckImage, Sound collisionSound, int ballSize,
                            GameObjectCollection gameObjects, DirectionStrategy[] puckStrategies,
                            BasicCollisionStrategy basicCollisionStrategy, String puckTag) {
        this.puckImage = puckImage;
        this.collisionSound = collisionSound;
        this.ballSize = ballSize;
        this.gameObjects = gameObjects;
        this.puckStrategies = puckStrategies;
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.puckTag = puckTag;
    }


    /**
     * Handles collision of add puck strategy.
     * Upon collision, creates two puck GameObjects with specified direction strategies.
     *
     * @param firstObj  The first GameObject involved in the collision - a brick.
     * @param secondObj The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject firstObj, GameObject secondObj) {
        basicCollisionStrategy.onCollision(firstObj, secondObj);
        Vector2 brickLocation = firstObj.getCenter();
        GameObject firstPuck = new Puck(brickLocation, new Vector2(ballSize * SCALE_BALL_SIZE,
                ballSize * SCALE_BALL_SIZE), this.puckImage, this.collisionSound, puckStrategies[0]);
        GameObject secondPuck = new Puck(brickLocation, new Vector2(ballSize * SCALE_BALL_SIZE,
                ballSize * SCALE_BALL_SIZE), this.puckImage, this.collisionSound, puckStrategies[1]);
        firstPuck.setTag(puckTag);
        firstPuck.setTag(puckTag);
        gameObjects.addGameObject(firstPuck);
        gameObjects.addGameObject(secondPuck);
    }

}

