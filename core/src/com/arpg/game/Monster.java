package com.arpg.game;

import com.arpg.game.utils.Poolable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

public class Monster extends Unit implements Poolable {
    private float aiTimer;
    private float aiTimerTo;

    public boolean isActive() {
        return hp > 0;
    }

    public void setup(int level) {
        this.aiTimerTo = 0.0f;
        this.level = level;
        this.hpMax = 10 + 5 * level;
        this.hp = hpMax;
        this.speed = 120.0f + 5 * level;
        this.weapon = new Weapon("Short Dark Sword", 0.8f, 2, 5);
    }

    public Monster(GameScreen gameScreen) {
        super(gameScreen);
        this.texture = Assets.getInstance().getAtlas().findRegion("Skeleton");
        do {
            this.position.set(MathUtils.random(0, Map.MAP_SIZE_X_PX), MathUtils.random(0, Map.MAP_SIZE_Y_PX));
        } while (!gameScreen.getMap().isCellPassable(position));
        this.area.setPosition(position);
        this.aiTimerTo = 0.0f;
    }

    @Override
    public void update(float dt) {
        aiTimer += dt;
        attackTime += dt;

        if (damageTimer > 0.0f) {
            damageTimer -= dt;
        }

        if (aiTimer > aiTimerTo) {
            aiTimer = 0.0f;
            aiTimerTo = MathUtils.random(2.0f, 4.0f);
            direction = Direction.values()[MathUtils.random(0, 3)];
        }

        tmp.set(position).add(direction.getX() * speed * dt, direction.getY() * speed * dt);
        if (gs.getMap().isCellPassable(tmp)) {
            position.set(tmp);
            area.setPosition(position);
        }

        tryToAttack();
    }

    public void tryToAttack() {
        if (attackTime > weapon.getAttackPeriod()) {
            attackTime = 0.0f;
            tmp.set(position).add(direction.getX() * 60, direction.getY() * 60);
            if (gs.getHero().getArea().contains(tmp)) {
                gs.getHero().takeDamage(weapon.getDamage(), Color.RED);
            }
        }
    }
}
