package bricker.brick_strategies.puck_startegies;

import danogl.GameObject;
import danogl.util.Vector2;
import java.util.Random;

/**
 * BasicRandomStrategy is a basic strategy that sets the direction of a puck GameObject randomly.
 */
public class BasicRandomStrategy implements DirectionStrategy {

    /**
     * Constructs a new BasicRandomStrategy.
     * This constructor is public by default and is used to create instances of BasicRandomStrategy.
     */
    public BasicRandomStrategy() {
    }

    /**
     * Sets the direction of the given GameObject randomly.
     *
     * @param gameObject The GameObject for which to set the direction.
     * @param speed      The speed at which the GameObject should move.
     */
    @Override
    public void setDirection(GameObject gameObject, float speed) {
        float ballVelX = speed;
        float ballVelY = speed;
        Random random = new Random();
        if (random.nextBoolean()) {
            ballVelX *= -1;
        }
        if (random.nextBoolean()) {
            ballVelY *= -1;
        }
        gameObject.setVelocity(new Vector2(ballVelX, ballVelY));
    }
}

