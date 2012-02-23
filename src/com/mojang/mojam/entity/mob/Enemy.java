package com.mojang.mojam.entity.mob;

import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.screen.Bitmap;

public class Enemy extends Mob {

	public Enemy(double x, double y) {
		super(x, y, Team.Enemy);
		// TODO Auto-generated constructor stub
	}

	public void collide(Entity entity, double xa, double ya, int dmg) {
		super.collide(entity, xa, ya);

		if (entity instanceof Mob) {
			Mob mob = (Mob) entity;
			if (isEnemyOf(mob)) {
				mob.hurt(this, dmg);
			}
		}
	}

	@Override
	public Bitmap getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

}
