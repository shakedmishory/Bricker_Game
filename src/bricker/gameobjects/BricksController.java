package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.StrategiesManager;
import bricker.brick_strategies.StrategyFactory;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Random;

/**
 * Represents a controller for managing all brick GameObjects.
 * This controller handles the creation of bricks with randomly chosen collision strategies.
 */
public class BricksController extends GameObject {

    private static final int PROBABILITY_BOUND = 10;
    private static final int BRICKS_LAYER = Layer.STATIC_OBJECTS;
    private final Counter brickCount;
    private final float spaceBetweenBricks;
    private final int bricksRow;
    private final int bricksCol;
    private final float brickWidth;
    private final float brickHeight;
    private final StrategyFactory strategyFactory;
    private final GameObjectCollection gameObjects;
    private final Renderable brickImage;

    /**
     * Constructs a new BricksController instance.
     *
     * @param brickImage         The renderable representing a brick.
     * @param spaceBetweenBricks The space between bricks.
     * @param bricksRow          Number of brick rows.
     * @param bricksCol          Number of brick cols.
     * @param brickWidth         The width of each brick.
     * @param brickHeight        The height of each brick.
     * @param gameObjects        The collection of GameObjects in the game.
     * @param strategiesManager  The manager for brick collision strategies.
     *                           Holds reqired parameters to create different strategies.
     */
    public BricksController(Renderable brickImage, float spaceBetweenBricks,
                            int bricksRow, int bricksCol, float brickWidth, float brickHeight,
                            GameObjectCollection gameObjects, StrategiesManager strategiesManager) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.gameObjects = gameObjects;
        this.brickCount = new Counter();
        this.brickImage = brickImage;
        this.spaceBetweenBricks = spaceBetweenBricks;
        this.bricksRow = bricksRow;
        this.bricksCol = bricksCol;
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
        this.strategyFactory = new StrategyFactory(strategiesManager, this, BRICKS_LAYER);
        createBricksMatrix();
    }

    /**
     * Chooses a collision strategy randomly based on a predefined probabilities.
     *
     * @return A randomly chosen collision strategy.
     */
    private CollisionStrategy chooseCollisionRandomly(){
        Random random = new Random();
        int index = random.nextInt(PROBABILITY_BOUND);
        return strategyFactory.createStrategy(index);
    }

    /**
     * Creates all bricks with randomly chosen collision strategies.
     */
    private void createBricksMatrix(){
        float currX = spaceBetweenBricks;
        float currY = spaceBetweenBricks;
        for (int i = 0; i < bricksRow; i++){
            for (int j = 0; j < bricksCol; j++) {
                CollisionStrategy collisionStrategy = chooseCollisionRandomly();
                GameObject brick = new Brick(new Vector2(currX, currY), new Vector2(brickWidth, brickHeight),
                        brickImage, collisionStrategy);
                gameObjects.addGameObject(brick, BRICKS_LAYER);
                brickCount.increment();
                currX += (brickWidth + spaceBetweenBricks);
            }
            currY += (brickHeight + spaceBetweenBricks);
            currX = spaceBetweenBricks;
        }
    }

    /**
     * Get method for the counter of current bricks number in the game.
     *
     * @return The counter for the number of bricks.
     */
    public Counter getBrickCounter(){
        return brickCount;
    }
}
