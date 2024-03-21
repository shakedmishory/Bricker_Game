package bricker.gameobjects;

import bricker.brick_strategies.puck_startegies.DirectionStrategy;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a puck GameObject in the game, inheriting from Ball class.
 */
public class Puck extends Ball {
    private static final float PUCK_SPEED = 100;

    /**
     * Constructs a new Puck instance.
     *
     * @param topLeftCorner     Position of the object, in window coordinates (pixels).
     *                          (0,0) is the top-left corner of the window.
     * @param dimensions        Puck dimensions.
     * @param renderable        The renderable representing a puck.
     * @param collisionSound    The sound to be played when puck collides with an object.
     * @param directionStrategy The strategy to set the initial direction of the puck.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                DirectionStrategy directionStrategy) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        directionStrategy.setDirection(this, PUCK_SPEED);
    }
}
