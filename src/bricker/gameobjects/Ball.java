package bricker.gameobjects;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Random;

/**
 * Represents a ball GameObject.
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCounter = 0;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates.
     *                      (0,0) is the top-left corner of the window.
     * @param dimensions    Balls dimensions.
     * @param renderable    The renderable representing ball.
     * @param collisionSound The sound to be played when collision happens.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions ,Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    /**
     * Handles collision with ball events.
     *
     * @param other The GameObject with which the ball collided.
     * @param collision The details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.collisionCounter += 1;
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        collisionSound.play();
    }


    /**
     * Counts number of collisions the ball has encountered.
     *
     * @return The collision counter value.
     */
    public int getCollisionCounter(){
        return this.collisionCounter;
    }
}
