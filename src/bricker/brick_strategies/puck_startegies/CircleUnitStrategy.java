package bricker.brick_strategies.puck_startegies;

import danogl.GameObject;
import danogl.util.Vector2;
import java.util.Random;

/**
 * CircleUnitStrategy is a strategy that sets the direction of a GameObject randomly within the circle unit.
 *
 */
public class CircleUnitStrategy implements DirectionStrategy{

    /**
     * Constructs a new CircleUnitStrategy.
     * This constructor is public by default and is used to create instances of CircleUnitStrategy.
     */
    public CircleUnitStrategy() {
    }
    
    /**
     * Sets the direction of the given GameObject randomly within a circle unit.
     *
     * @param gameObject The GameObject for which to set the direction.
     * @param speed      The speed at which the GameObject should move.
     */
    @Override
    public void setDirection(GameObject gameObject, float speed) {
        Random random = new Random();
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * speed;
        float velocityY = (float) Math.sin(angle) * speed;
        gameObject.setVelocity(new Vector2(velocityX, velocityY));
    }
}
