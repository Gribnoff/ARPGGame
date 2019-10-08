package com.arpg.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Achievements {
    private int score;
    private int monstersKilled;
    private int potionsUsed;

    public int getScore() {
        return score;
    }

    public int getMonstersKilled() {
        return monstersKilled;
    }

    public int getPotionsUsed() {
        return potionsUsed;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void addMonstersKilled() {
        this.monstersKilled++;
    }

    public void addPotionsUsed() {
        this.potionsUsed++;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "SCORE: " + score + "\nMONSTERS KILLED: " + monstersKilled + "\nPOTIONS USED: " + potionsUsed, 20, 100);
    }
}
