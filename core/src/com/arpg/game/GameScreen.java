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
    private Bestiary bestiary;
    private MonsterController monsterController;
    private InfoController infoController;
    private EffectController effectController;
    private BitmapFont font24;
    private BitmapFont font12;
    private Vector2 mouse;
    private Vector2 tmp;
    private float spawnTimer;

    public MonsterController getMonsterController() {
        return monsterController;
    }

    public EffectController getEffectController() {
        return effectController;
    }

    public Bestiary getBestiary() {
        return bestiary;
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
        this.bestiary = new Bestiary();
        this.monsterController = new MonsterController(this);
        for (int i = 0; i < 5; i++) {
            this.monsterController.setup(1);
        }
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        this.font12 = Assets.getInstance().getAssetManager().get("fonts/font12.ttf");
        this.infoController = new InfoController();
        this.effectController = new EffectController();
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
            hero.render(batch, font24, font12);
        }
        monsterController.render(batch, font24, font12);
        effectController.render(batch);
        infoController.render(batch, font24);
        batch.end();
    }

    public void update(float dt) {
        spawnTimer += dt;
        if (spawnTimer > 1.0f) {
            spawnTimer = 0.0f;
            monsterController.setup(hero.stats.getLevel());
        }
        mouse.set(Gdx.input.getX(), Gdx.input.getY());
        ScreenManager.getInstance().getViewport().unproject(mouse);
        if (hero.isActive()) {
            hero.update(dt);
        }
        monsterController.update(dt);
        effectController.update(dt);
        infoController.update(dt);
    }
}
