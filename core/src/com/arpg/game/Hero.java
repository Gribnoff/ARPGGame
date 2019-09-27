package com.arpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Hero extends Unit {
    private TextureRegion hpTexture;

    public boolean isActive() {
        return hp > 0;
    }

    public Hero(GameScreen gameScreen) {
        super(gameScreen);
        this.hpTexture = Assets.getInstance().getAtlas().findRegion("monsterHp");
        this.texture = Assets.getInstance().getAtlas().findRegion("Knight");
        do {
            this.position.set(MathUtils.random(0, Map.MAP_SIZE_X_PX), MathUtils.random(0, Map.MAP_SIZE_Y_PX));
        } while (!gameScreen.getMap().isCellPassable(position));
        this.area.setPosition(position);
        this.speed = 320.0f;
        this.hpMax = 50;
        this.hp = hpMax;
        this.weapon = new Weapon("Short Sword", 0.5f, 1, 3);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (damageTimer > 0.0f) {
            batch.setColor(1.0f, 1.0f - damageTimer, 1.0f - damageTimer, 1.0f);
        }
        batch.draw(texture, position.x - 40, position.y - 40);
        if (hp < hpMax) {
            batch.setColor(1.0f, 1.0f, 1.0f, 0.9f);
            batch.draw(hpTexture, position.x - 40, position.y + 40, 80 * ((float) hp / hpMax), 12);
        }
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void update(float dt) {
        float speedMod = 1.0f;
        attackTime += dt;

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            speedMod = 1.2f;
        }

        if (damageTimer > 0.0f) {
            damageTimer -= dt;
        }

        boolean btnPressed = false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction = Direction.LEFT;
            btnPressed = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction = Direction.RIGHT;
            btnPressed = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction = Direction.DOWN;
            btnPressed = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            direction = Direction.UP;
            btnPressed = true;
        }

        if (btnPressed) {
            tmp.set(position);
            tmp.add(direction.getX() * speed * speedMod * dt, direction.getY() * speed * speedMod * dt);
            if (gs.getMap().isCellPassable(tmp)) {
                position.set(tmp);
                area.setPosition(position);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            gs.getInfoController().setup(position.x, position.y, "Hello", Color.GOLD);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            attack();
        }
    }

    public void attack() {
        if (attackTime > weapon.getAttackPeriod()) {
            attackTime = 0.0f;
            tmp.set(position).add(direction.getX() * 60, direction.getY() * 60);
            if (gs.getMonster().getArea().contains(tmp)) {
                gs.getMonster().takeDamage(weapon.getDamage(), Color.WHITE);
            }
        }
    }
}
