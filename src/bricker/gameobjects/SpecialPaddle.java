package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a special type of paddle GameObject, belongs to special behavior of a brick.
 * This paddle appears in the middle of the game board, and has a limited
 * number of collisions until disappears.
 */
public class SpecialPaddle extends Paddle{
    private static final int MAX_COLLISION_NUM = 4;
    private final String specialPaddleToRemove;
    private Counter collisionsLeft;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         (0,0) is the top-left corner of the window.
     * @param dimensions       Paddle dimensions.
     * @param renderable       The renderable representing a paddle
     * @param inputListener          handle user keyboard input.
     * @param windowDimensions       The dimensions of the game window.
     * @param wallWidth              The width of the walls on either side of the game window.
     * @param specialPaddleToRemove  The tag associated with this paddle when it needs to be
     *                              removed from the game.
     */


    public SpecialPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                         UserInputListener inputListener, Vector2 windowDimensions, float wallWidth,
                         String specialPaddleToRemove) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, wallWidth);
        this.specialPaddleToRemove = specialPaddleToRemove;
        this.collisionsLeft = new Counter(MAX_COLLISION_NUM);
    }

    /**
     * Handles collision with special paddle events.
     * Decreases the number of collisions left for the special paddle until it needs to be removed,
     * and sets its tag to remove tag when no collisions are left.
     *
     * @param other     The GameObject with which the paddle collided.
     * @param collision The details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionsLeft.decrement();
        if (collisionsLeft.value() == 0){
            this.setTag(specialPaddleToRemove);
        }
    }


}
