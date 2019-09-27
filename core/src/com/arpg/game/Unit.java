package com.arpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class Unit {
    protected GameScreen gs;
    protected TextureRegion texture;
    protected Vector2 position;
    protected Direction direction;
    protected Vector2 tmp;
    protected Circle area;
    protected int level;
    protected float speed;
    protected int hp, hpMax;
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
        this.level = 1;
        this.position = new Vector2(0.0f, 0.0f);
        this.area = new Circle(0, 0, 32);
        this.tmp = new Vector2(0.0f, 0.0f);
        this.direction = Direction.DOWN;
    }

    public void takeDamage(int amount, Color color) {
        hp -= amount;
        damageTimer = 1.0f;
        gs.getInfoController().setup(position.x, position.y + 30, "-" + amount, color);
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update(float dt);
}
