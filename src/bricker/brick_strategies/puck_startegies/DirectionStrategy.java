package bricker.brick_strategies.puck_startegies;

import danogl.GameObject;

/**
 * interface for setting the direction and speed of puck GameObject.
 *
 */
public interface DirectionStrategy {
    /**
     * Sets the direction and speed of puck.
     *
     * @param gameObject The GameObject for which to set the direction and speed.
     * @param speed      The speed at which the GameObject should move.
     */
    public void setDirection(GameObject gameObject, float speed);
}
