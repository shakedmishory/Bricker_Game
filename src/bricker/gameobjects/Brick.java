package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a brick GameObject in the game.
 */
public class Brick extends GameObject {

    private final CollisionStrategy collisionStrategy;

    /**
     * Construct a new brick instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      (0,0) is the top-left corner of the window.
     * @param dimensions    brick dimensions.
     * @param renderable    The renderable representing a brick.
     * @param collisionStrategy The strategy to handle when there is a collision with this brick.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Handles collision with brick events.
     *
     * @param other The GameObject with which the brick collided.
     * @param collision Details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collisionStrategy.onCollision(this, other);
    }
}
