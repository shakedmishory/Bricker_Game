package bricker.brick_strategies;
import danogl.GameObject;

/**
 * An interface representing handling collisions between GameObjects.
 */
public interface CollisionStrategy {
    /**
     * Method to handle a collision between two GameObjects.
     *
     * @param firstObj  The first GameObject involved in the collision.
     * @param secondObj The second GameObject involved in the collision.
     */
    public void onCollision(GameObject firstObj ,GameObject secondObj);

}
