package com.arpg.game;

import com.badlogic.gdx.Gdx;

public class HUD {
    private GameScreen gs;
    private Hero hero;

    public HUD(GameScreen gs) {
        this.gs = gs;
        this.hero = gs.getHero();
    }

    public void render() {
        gs.getFont24().draw(gs.getHUDBatch(),
                String.format("Level %d Exp %d/%d\nHealth %d/%d",
                        hero.stats.getLevel(),
                        hero.stats.getExp(),
                        hero.stats.getExpTo(hero.stats.getLevel()),
                        hero.stats.getHp(),
                        hero.stats.getHpMax()),
                10, Gdx.graphics.getHeight());
    }

    public void update() {

    }
}
