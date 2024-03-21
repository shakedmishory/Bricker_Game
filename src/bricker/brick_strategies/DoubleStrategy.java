package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Represents a collision strategy for applying multiple collision strategies simultaneously.
 */
public class DoubleStrategy implements CollisionStrategy{
    private final CollisionStrategy[] collisionStrategies;
    private final BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Constructs a new DoubleStrategy instance.
     *
     * @param collisionStrategies     The array of collision strategies to be executed.
     * @param basicCollisionStrategy  The basic collision strategy for handling collisions.
     */
    public DoubleStrategy(CollisionStrategy[] collisionStrategies, BasicCollisionStrategy
            basicCollisionStrategy) {
        this.collisionStrategies = collisionStrategies;
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Handles collision for double strategy case.
     * Executes the basic collision strategy and then delegates collision handling to each specified
     * strategy.
     *
     * @param firstObj  The first GameObject involved in the collision - a brick.
     * @param secondObj The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject firstObj, GameObject secondObj) {
        basicCollisionStrategy.onCollision(firstObj, secondObj);
        for (CollisionStrategy collisionStrategy: collisionStrategies){
            collisionStrategy.onCollision(firstObj, secondObj);
        }
    }


}
