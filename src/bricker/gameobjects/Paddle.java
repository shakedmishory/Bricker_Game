package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * Represents a paddle GameObject in the game.
 */
public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 350;
    private static final int MIN_DIST_FROM_FRAME = 5;
    private final float wallWidth;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;


    /**
     * Construct a new paddle instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates.
     *                      (0,0) is the top-left corner of the window.
     * @param dimensions    paddle dimensions.
     * @param renderable    The renderable representing a paddle.
     * @param inputListener     Input listener to handle user keyboard input.
     * @param windowDimensions  The dimensions of the game window.
     * @param wallWidth         The width of the walls of the game window.
     */

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions, float wallWidth) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.wallWidth = wallWidth;
    }

    /**
     * Updates paddle's position according to user input.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (this.getTopLeftCorner().x() + Vector2.LEFT.x() - MIN_DIST_FROM_FRAME > wallWidth) {
                movementDir = movementDir.add(Vector2.LEFT);
            }
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (this.getTopLeftCorner().x() + this.getDimensions().x() + Vector2.RIGHT.x()
                    + MIN_DIST_FROM_FRAME < windowDimensions.x() - wallWidth) {
                movementDir = movementDir.add(Vector2.RIGHT);
            }
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }

}



