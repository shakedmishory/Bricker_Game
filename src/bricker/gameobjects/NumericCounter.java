package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;

/**
 * Represents a numeric counter GameObject.
 * This counter displays a numerical value, representing the number of remaining lives.
 */
public class NumericCounter extends GameObject {

    private static final int BUFFER_FROM_WALL = 40;
    private static final int COUNTER_DIMENSION = 20;
    private static final int RED_COLOR = 1;
    private static final int YELLOW_COLOR = 2;
    private static final int MAX_LIFE = 4;
    private Counter lifeCounter;
    private TextRenderable textRenderable;


    /**
     * Constructs a new NumericCounter instance.
     *
     * @param lifeCounter       The counter to display the numeric value.
     * @param windowDimensions  The dimensions of the game window.
     * @param textRenderable    The text renderable to display the counter number.
     */
    public NumericCounter(Counter lifeCounter, Vector2 windowDimensions, TextRenderable textRenderable) {
        super(new Vector2(windowDimensions.x()- BUFFER_FROM_WALL,
                windowDimensions.y() - BUFFER_FROM_WALL), new Vector2(COUNTER_DIMENSION,
                COUNTER_DIMENSION), textRenderable);
        this.textRenderable = textRenderable;
        this.lifeCounter = lifeCounter;
        setCounter();
    }


    /**
     * Updates the counter's value and color based on the current value of the life counter.
     */
    private void setCounter() {
        int curr_count = lifeCounter.value();
        textRenderable.setString(String.valueOf(curr_count));
        switch (curr_count) {
            case RED_COLOR:
                textRenderable.setColor(Color.RED);
                break;
            case YELLOW_COLOR:
                textRenderable.setColor(Color.YELLOW);
                break;
            default:
                textRenderable.setColor(Color.GREEN);
        }
    }

    /**
     * Update method, updates counter's value and color when the life counter.
     * Updates only if lives amount is less than the maximum number that is allowed.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (lifeCounter.value() <= MAX_LIFE) {
            setCounter();
        }
    }
}
