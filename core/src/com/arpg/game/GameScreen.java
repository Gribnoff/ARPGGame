package com.arpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen {
    private Map map;
    private Hero hero;
    private Monster monster;
    private InfoController infoController;
    private BitmapFont font24;
    private Vector2 mouse;
    private Vector2 tmp;

    public Monster getMonster() {
        return monster;
    }

    public InfoController getInfoController() {
        return infoController;
    }

    public Map getMap() {
        return map;
    }

    public GameScreen(SpriteBatch batch) {
        super(batch);
    }

    public Hero getHero() {
        return hero;
    }

    @Override
    public void show() {
        this.map = new Map();
        this.hero = new Hero(this);
        this.monster = new Monster(this);
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        this.infoController = new InfoController();
        this.mouse = new Vector2(0.0f, 0.0f);
        this.tmp = new Vector2(0.0f, 0.0f);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ScreenManager.getInstance().getCamera().position.set(hero.getPosition(), 0.0f);
        ScreenManager.getInstance().getCamera().update();
        batch.setProjectionMatrix(ScreenManager.getInstance().getCamera().combined);

        batch.begin();
        map.render(batch);
        if (hero.isActive()) {
            hero.render(batch);
        }
        if (monster.isActive()) {
            monster.render(batch);
        }
        infoController.render(batch, font24);
        batch.end();
    }

    public void update(float dt) {
        mouse.set(Gdx.input.getX(), Gdx.input.getY());
        ScreenManager.getInstance().getViewport().unproject(mouse);
        if (hero.isActive()) {
            hero.update(dt);
        }
        if (monster.isActive()) {
            monster.update(dt);
        }
        infoController.update(dt);
    }

    // Strength Dexterity Vitality Intelligent
    // Goblin:
    // Str = Level * 0.2
    // Dex = Level * 0.5
    // Vit = Level * 0.4
    // Int = Level * 0.05
    // Dragon:
    // Str = Level * 10
    // Dex = Level * 8
    // Vit = Level * 50
    // Int = Level * 10
    // factory.createMonster("Dragon", 5);
}
