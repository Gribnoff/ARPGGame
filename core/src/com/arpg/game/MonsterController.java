package com.arpg.game;

import com.arpg.game.utils.ObjectPool;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class MonsterController extends ObjectPool<Monster> {
    private GameScreen gs;

    @Override
    protected Monster newObject() {
        return new Monster(gs);
    }

    public MonsterController(GameScreen gs) {
        this.gs = gs;
    }

    public void render(SpriteBatch batch, BitmapFont fontLvl, BitmapFont fontHP) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch, fontLvl, fontHP);
        }
    }

    public void setup(int level) {
        int currentLevel = MathUtils.random(level, level + 2);
        String[] patterns = {"Skeleton", "Reaper"};
        Monster pattern = (MathUtils.random() < 0.9) ?
                gs.getBestiary().getPatternFromTitle(patterns[0]) :
                gs.getBestiary().getPatternFromTitle(patterns[1]);
        getActiveElement().setup(currentLevel, -1, -1, pattern);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }
}
