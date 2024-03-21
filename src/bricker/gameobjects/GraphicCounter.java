package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * Represents a graphic counter GameObject.
 * This counter displays hearts representing the player's remaining lives.
 */
public class GraphicCounter extends GameObject {

    private static final int SPACE_BETWEEN_HEARTS = 6;
    private static final int BUFFER_FROM_WALL = 40;
    private static final int MAX_LIFE = 4;
    private static final int HEARTS_LAYER = Layer.BACKGROUND;

    private final Counter lifeCounter;
    private GameObject[] hearts;
    private final int beginningLifeNum;
    private int heartCounter;
    private final Renderable heartImage;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;
    private final int heartSize;

    /**
     * Constructs a new GraphicCounter instance.
     *
     * @param lifeCounter      The counter representing the player's remaining lives.
     * @param beginningLifeNum       The initial number of lives.
     * @param heartImage       The image renderable representing a heart.
     * @param windowDimensions The dimensions of the game window.
     * @param gameObjects      The game objects collection.
     * @param heartSize        The size of each heart image.
     */
    public GraphicCounter(Counter lifeCounter, int beginningLifeNum, Renderable heartImage,
                          Vector2 windowDimensions, GameObjectCollection gameObjects, int heartSize) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.lifeCounter = lifeCounter;
        this.beginningLifeNum = beginningLifeNum;
        this.heartImage = heartImage;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.heartSize = heartSize;
        initializeHearts();
    }


    /**
     * Initializes the hearts representing the player's lives.
     */
    private void initializeHearts() {
        heartCounter = beginningLifeNum;
        hearts = new GameObject[beginningLifeNum+1];
        float currX = SPACE_BETWEEN_HEARTS;
        for (int i = 0; i < beginningLifeNum; i++) {
            hearts[i] = new GameObject(new Vector2(currX, windowDimensions.y() - BUFFER_FROM_WALL),
                    new Vector2(heartSize, heartSize), this.heartImage);
            gameObjects.addGameObject(hearts[i], HEARTS_LAYER);
            currX += (heartSize + SPACE_BETWEEN_HEARTS);
        }
    }

    /**
     * Updates the graphic counter based on changes in life counter.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (heartCounter > lifeCounter.value()) {
            gameObjects.removeGameObject(hearts[heartCounter - 1], HEARTS_LAYER);
            heartCounter--;
        } else if (heartCounter < lifeCounter.value() && (lifeCounter.value() <= MAX_LIFE)) {
            float currX = heartCounter*(heartSize+SPACE_BETWEEN_HEARTS) + SPACE_BETWEEN_HEARTS;
            GameObject heartToAdd = new GameObject(new Vector2(currX,
                    windowDimensions.y() - BUFFER_FROM_WALL), new Vector2(heartSize, heartSize),
                    this.heartImage);
            hearts[heartCounter] = heartToAdd;
            gameObjects.addGameObject(heartToAdd, HEARTS_LAYER);
            heartCounter++;
        }
    }
}

