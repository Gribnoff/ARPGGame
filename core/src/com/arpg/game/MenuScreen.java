package com.arpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen extends AbstractScreen {
    private Stage stage;
    private BitmapFont font24;

    public MenuScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        this.stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("smButton");
        textButtonStyle.font = font24;
        skin.add("smallButtonStyle", textButtonStyle);

        Button btnNewGame = new TextButton("New game", skin, "smallButtonStyle");
        Button btnExit = new TextButton("Exit", skin, "smallButtonStyle");
        Group menuGroup = new Group();
        menuGroup.addActor(btnNewGame);
        menuGroup.addActor(btnExit);
        btnNewGame.setPosition(0, 50);
        btnExit.setPosition(10, 0);

        btnNewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
            }
        });

        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        menuGroup.setPosition(550, 350);
        stage.addActor(menuGroup);

        skin.dispose();
    }

    @Override
    public void render(float delta) {
        ScreenManager.getInstance().getCamera().position.set(ScreenManager.HALF_WORLD_WIDTH, ScreenManager.HALF_WORLD_HEIGHT, 0);
        ScreenManager.getInstance().getCamera().update();
        batch.setProjectionMatrix(ScreenManager.getInstance().getCamera().combined);
        stage.draw();
    }
}
