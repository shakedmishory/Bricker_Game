package bricker.brick_strategies;

import bricker.brick_strategies.puck_startegies.BasicRandomStrategy;
import bricker.brick_strategies.puck_startegies.CircleUnitStrategy;
import bricker.brick_strategies.puck_startegies.DirectionStrategy;
import bricker.gameobjects.BricksController;
import java.util.Random;


/**
 * Factory class for creating various collision strategies for the Bricker game.
 */
public class StrategyFactory {

    /**
     * The maximum number of strategies per brick.
     */
    private static final int MAX_STRATEGIES_PER_BRICK = 3;

    /**
     * Strategy index for pucks.
     */
    private static final int PUCKS_STRATEGY = 0;

    /**
     * Strategy index for paddles.
     */
    private static final int PADDLE_STRATEGY = 1;

    /**
     * Strategy index for life.
     */
    private static final int LIFE_STRATEGY = 2;

    /**
     * Strategy index for camera.
     */
    private static final int CAMERA_STRATEGY = 3;

    /**
     * Strategy index for double strategy.
     */
    private static final int DOUBLE_STRATEGY = 4;

    /**
     * The minimum number of strategies required.
     */
    private static final int MIN_STRATEGIES_NUM = 2;

    /**
     * The strategies manager for managing strategies.
     */
    private final StrategiesManager strategiesManager;

    /**
     * The bricks controller for controlling bricks in the game.
     */
    private final BricksController bricksController;

    /**
     * The layer of bricks in the game.
     */
    private final int bricksLayer;

    /**
     * The basic collision strategy for handling collisions.
     */
    private BasicCollisionStrategy basicCollisionStrategy;


    /**
     * Constructs a new StrategyFactory instance.
     *
     * @param strategiesManager The StrategiesManager instance for managing game strategies.
     * @param bricksController  The BricksController instance for managing bricks in the game.
     * @param bricksLayer       The layer index for bricks.
     */
    public StrategyFactory(StrategiesManager strategiesManager, BricksController bricksController,
                           int bricksLayer) {
        this.strategiesManager = strategiesManager;
        this.bricksController = bricksController;
        this.bricksLayer = bricksLayer;
        this.basicCollisionStrategy = createBasicStrategy();
    }

    /**
     * Creates the basic collision strategy.
     *
     * @return The BasicCollisionStrategy instance.
     */
    private BasicCollisionStrategy createBasicStrategy() {
        return new BasicCollisionStrategy(strategiesManager.getGameObjects(),
                bricksController, bricksLayer);
    }

    /**
     * Creates the puck strategy.
     *
     * @return The AddPucksStrategy instance.
     */
    private AddPucksStrategy createPuckStrategy() {
        DirectionStrategy[] puckStrategies = {new BasicRandomStrategy(), new CircleUnitStrategy()};
        return new AddPucksStrategy(strategiesManager.getPuckImage(), strategiesManager.getCollisionSound(),
                strategiesManager.getBallSize(), strategiesManager.getGameObjects(), puckStrategies,
                basicCollisionStrategy, strategiesManager.getPuckTag());
    }

    /**
     * Creates the paddle strategy.
     *
     * @return The AddPaddleStrategy instance.
     */
    private AddPaddleStrategy createPaddleStrategy() {
        return new AddPaddleStrategy(strategiesManager.getGameObjects(),
                strategiesManager.getPaddleImage(), strategiesManager.getPaddleWidth(),
                strategiesManager.getPaddleHeight(), strategiesManager.getInputListener(),
                strategiesManager.getWindowDimensions(), strategiesManager.getPaddleCounter(),
                strategiesManager.getWallWidth(), basicCollisionStrategy,
                strategiesManager.getSpecialPaddleToRemoveTag());
    }

    /**
     * Creates the life strategy.
     *
     * @return The AddLifeStrategy instance.
     */
    private AddLifeStrategy createLifeStrategy() {
        return new AddLifeStrategy(strategiesManager.getGameObjects(),
                strategiesManager.getHeartSize(), strategiesManager.getHeartImage(),
                strategiesManager.getLifeCounter(), basicCollisionStrategy,
                strategiesManager.getBasePaddleTag(), strategiesManager.getFallenHeartTag(),
                strategiesManager.getFallenHeartToRemoveTag());
    }

    /**
     * Creates the camera strategy.
     *
     * @return The CameraStrategy instance.
     */
    private CameraStrategy createCameraStrategy() {
        return new CameraStrategy(strategiesManager.getBrickerGameManager(),
                strategiesManager.getWindowDimensions(),
                basicCollisionStrategy, strategiesManager.getMainBallTag());
    }

    /**
     * A helper function that helps choose strategies for double strategy behavior.
     *
     * @return Array of indexes.
     */
    private int[] createDoubleStrategyHelper(){
        Random random = new Random();
        int[] collisionStrategiesIdx = new int[MAX_STRATEGIES_PER_BRICK];
        int currStrategiesNum = 0;
        int requiredStrategiesNum = MIN_STRATEGIES_NUM;
        while (currStrategiesNum < requiredStrategiesNum && currStrategiesNum < MAX_STRATEGIES_PER_BRICK) {
            int randomIndex = random.nextInt(DOUBLE_STRATEGY + 1);
            if (randomIndex == DOUBLE_STRATEGY) {
                requiredStrategiesNum++;
            } else {
                collisionStrategiesIdx[currStrategiesNum] = randomIndex;
                currStrategiesNum++;
            }
        }
        int [] newCollisionIdxArray = new int[currStrategiesNum];
        System.arraycopy(collisionStrategiesIdx, 0, newCollisionIdxArray, 0,
                currStrategiesNum);
        return newCollisionIdxArray;
    }

    /**
     * Creates the double strategy.
     *
     * @return The DoubleStrategy instance.
     */
    private DoubleStrategy createDoubleStrategy() {
        int[] collisionStrategiesIdx = createDoubleStrategyHelper();
        CollisionStrategy[] CollisionsArray = new CollisionStrategy[collisionStrategiesIdx.length];
        for (int i = 0; i < collisionStrategiesIdx.length; i++){
            CollisionsArray[i] = createStrategy(collisionStrategiesIdx[i]);
        }
        return new DoubleStrategy(CollisionsArray, basicCollisionStrategy);
    }


    /**
     * Creates a specific collision strategy based on the given index.
     *
     * @param index The index of the collision strategy to create.
     * @return The created CollisionStrategy instance.
     */
    public CollisionStrategy createStrategy(int index) {
        switch (index) {
            case PUCKS_STRATEGY:
                return createPuckStrategy();
            case PADDLE_STRATEGY:
                return createPaddleStrategy();
            case LIFE_STRATEGY:
                return createLifeStrategy();
            case CAMERA_STRATEGY:
                return createCameraStrategy();
            case DOUBLE_STRATEGY:
                return createDoubleStrategy();
            default:
                return basicCollisionStrategy;
        }
    }

}
