package com.arpg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Map {
    public enum BlockType {
        EMPTY, WALL;
    }

    public static final int CELL_SIZE = 80;
    public static final int MAP_SIZE_X = 20;
    public static final int MAP_SIZE_X_PX = MAP_SIZE_X * CELL_SIZE;
    public static final int MAP_SIZE_Y = 10;
    public static final int MAP_SIZE_Y_PX = MAP_SIZE_Y * CELL_SIZE;

    private BlockType[][] data;
    private TextureRegion textureWall;
    private TextureRegion textureGrass;

    public Map() {
        this.data = new BlockType[MAP_SIZE_X][MAP_SIZE_Y];

        this.textureGrass = Assets.getInstance().getAtlas().findRegion("Grass");
        this.textureWall = Assets.getInstance().getAtlas().findRegion("Wall");
        for (int i = 0; i < MAP_SIZE_X; i++) {
            for (int j = 0; j < MAP_SIZE_Y; j++) {
                data[i][j] = BlockType.EMPTY;
                if (MathUtils.random() < 0.05) {
                    data[i][j] = BlockType.WALL;
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < MAP_SIZE_X; i++) {
            for (int j = 0; j < MAP_SIZE_Y; j++) {
                batch.draw(textureGrass, i * 80, j * 80);
                if (data[i][j] == BlockType.WALL) {
                    batch.draw(textureWall, i * 80 , j * 80 );
                }
            }
        }
    }

    public boolean isCellPassable(Vector2 position) {
        if (position.x < 0.0f || position.y < 0.0f) {
            return false;
        }
        int cellX = (int) (position.x / 80);
        int cellY = (int) (position.y / 80);
        if (cellX < 0 || cellX >= MAP_SIZE_X || cellY < 0 || cellY >= MAP_SIZE_Y) {
            return false;
        }
        return data[cellX][cellY] == BlockType.EMPTY;
    }
}
