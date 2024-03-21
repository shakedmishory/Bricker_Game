package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a fallen heart GameObject in the game.
 * This heart adds life to the player, if collected by colliding with the base paddle.
 * This heart is used for special behavior of a brick - add life behavior.
 */
public class FallenHeart extends GameObject {
    private final Counter lifeCounter;
    private final String basePaddleTag;
    private final String fallenHeartToRemoveTag;

    /**
     * Constructs a new FallenHeart instance.
     *
     * @param topLeftCorner          Position of the object, in window coordinates (pixels).
     *                               (0,0) is the top-left corner of the window.
     * @param dimensions             Fallen heart dimensions.
     * @param renderable             The renderable representing heart.
     * @param lifeCounter            The counter to track the number of life.
     * @param basePaddleTag          The tag associated with the base paddle.
     * @param fallenHeartToRemoveTag The tag associated with this fallen heart when it needs to be removed
     *                               from gameObject collection.
     */
    public FallenHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Counter lifeCounter,
                       String basePaddleTag, String fallenHeartToRemoveTag) {
        super(topLeftCorner, dimensions, renderable);
        this.lifeCounter = lifeCounter;
        this.basePaddleTag = basePaddleTag;
        this.fallenHeartToRemoveTag = fallenHeartToRemoveTag;
    }

    /**
     * Determines whether the fallen heart should collide with another GameObject.
     * Fallen heart should only collide with base paddle object.
     *
     * @param other The GameObject to check if fallen heart collides with.
     * @return True if the heart should collide with the other GameObject, otherwise returns false.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return super.shouldCollideWith(other) && (other.getTag().equals(basePaddleTag));
    }

    /**
     * Handles fallen heart collision events.
     * This method increments the life counter if collides with the base paddle,
     * and sets the fallen hearts tag to remove tag.
     *
     * @param other     The GameObject with which the fallen heart collided.
     * @param collision The details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        lifeCounter.increment();
        this.setTag(fallenHeartToRemoveTag);
    }


}
