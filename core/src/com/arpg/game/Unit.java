package com.arpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class Unit {
    protected GameScreen gs;
    protected TextureRegion texture;
    protected TextureRegion hpTexture;
    protected Vector2 position;
    protected Direction direction;
    protected Vector2 tmp;
    protected Circle area;
    protected Stats stats;
    protected float damageTimer;
    protected Weapon weapon;
    protected float attackTime;

    public Vector2 getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public Circle getArea() {
        return area;
    }

    public Unit(GameScreen gameScreen) {
        this.gs = gameScreen;
        this.hpTexture = Assets.getInstance().getAtlas().findRegion("monsterHp");
        this.position = new Vector2(0.0f, 0.0f);
        this.area = new Circle(0, 0, 32);
        this.tmp = new Vector2(0.0f, 0.0f);
        this.direction = Direction.DOWN;
    }

    public void takeDamage(int amount, Color color) {
        stats.decreaseHp(amount);
        damageTimer = 1.0f;
        gs.getInfoController().setup(position.x, position.y + 30, "-" + amount, color);
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        if (damageTimer > 0.0f) {
            batch.setColor(1.0f, 1.0f - damageTimer, 1.0f - damageTimer, 1.0f);
        }
        batch.draw(texture, position.x - 40, position.y - 40);
        if (stats.getHp() < stats.getHpMax()) {
            batch.setColor(1.0f, 1.0f, 1.0f, 0.9f);
            batch.draw(hpTexture, position.x - 40, position.y + 40, 80 * ((float) stats.getHp() / stats.getHpMax()), 12);
        }
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(batch, "" + stats.getLevel(), position.x, position.y + 50);
    }

    public abstract void update(float dt);
}
