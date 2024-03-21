package bricker.brick_strategies;

import bricker.gameobjects.BricksController;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;


/**
 * A basic collision strategy, handling basic behavior when collision with Brick happens.
 *
 */
public class BasicCollisionStrategy implements CollisionStrategy{

    private final BricksController bricksController;
    private final int bricksLayer;
    private final GameObjectCollection gameObjects;

    /**
     * Constructs a BasicCollisionStrategy instance.
     *
     * @param gameObjects     The collection of GameObjects.
     * @param bricksController The BricksController instance managing the bricks.
     * @param bricksLayer     The layer where bricks are placed.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjects, BricksController bricksController,
                                  int bricksLayer){
        this.gameObjects = gameObjects;
        this.bricksController = bricksController;
        this.bricksLayer = bricksLayer;
    }

    /**
     * Basic behavior of Handling a collision with a brick.
     * Remove brick from game and decrease the counter for number of bricks in game.
     *
     * @param firstObj  The first GameObject involved in the collision - a brick.
     * @param secondObj The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject firstObj, GameObject secondObj) {
        if(gameObjects.removeGameObject(firstObj, bricksLayer)){
            bricksController.getBrickCounter().decrement();
        }
    }



}
