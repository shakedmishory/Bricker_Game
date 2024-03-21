package bricker.brick_strategies;

import bricker.gameobjects.FallenHeart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a collision strategy for adding life to the player.
 * This strategy creates a fallen heart GameObject upon collision,
 * which increases the player's life count if collides with basic paddle.
 */
public class AddLifeStrategy implements CollisionStrategy{

    private static final Vector2 HEART_VELOCITY = Vector2.DOWN.mult(100);
    private final int heartSize;
    private final Renderable heartImage;
    private final Counter lifeCounter;
    private final BasicCollisionStrategy basicCollisionStrategy;
    private final String basePaddleTag;
    private final String fallenHeartTag;
    private final String fallenHeartToRemoveTag;
    private final GameObjectCollection gameObjects;

    /**
     * Constructs a new AddLifeStrategy instance.
     *
     * @param gameObjects           The collection of GameObjects in the game.
     * @param heartSize             The size of the fallen heart GameObject.
     * @param heartImage            A renderable representing the fallen heart.
     * @param lifeCounter           The counter for the player's remaining lives.
     * @param basicCollisionStrategy The basic collision strategy for handling collisions.
     * @param basePaddleTag         The tag for the base paddle GameObject.
     * @param fallenHeartTag        The tag for the fallen heart GameObject.
     * @param fallenHeartToRemoveTag A tag that indicates that the fallen heart game object
     *                               should be removed.
     */
    public AddLifeStrategy(GameObjectCollection gameObjects, int heartSize, Renderable heartImage,
                           Counter lifeCounter, BasicCollisionStrategy basicCollisionStrategy,
                           String basePaddleTag, String fallenHeartTag, String fallenHeartToRemoveTag) {
        this.gameObjects = gameObjects;
        this.heartSize = heartSize;
        this.heartImage = heartImage;
        this.lifeCounter = lifeCounter;
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.basePaddleTag = basePaddleTag;
        this.fallenHeartTag = fallenHeartTag;
        this.fallenHeartToRemoveTag = fallenHeartToRemoveTag;
    }

    /**
     * Handles collision of Add life strategy.
     * Upon collision, creates a fallen heart GameObject.
     *
     * @param firstObj  The first GameObject involved in the collision - a brick.
     * @param secondObj The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject firstObj, GameObject secondObj) {
        basicCollisionStrategy.onCollision(firstObj, secondObj);
        GameObject heart = new FallenHeart(firstObj.getCenter(), new Vector2(heartSize, heartSize),
                this.heartImage, lifeCounter, basePaddleTag, fallenHeartToRemoveTag);
        heart.setTag(fallenHeartTag);
        heart.setVelocity(HEART_VELOCITY);
        gameObjects.addGameObject(heart);
    }

}
