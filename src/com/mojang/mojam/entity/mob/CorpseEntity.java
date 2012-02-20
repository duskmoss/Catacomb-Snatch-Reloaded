package com.mojang.mojam.entity.mob;

import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Bitmap;

public class CorpseEntity extends Mob {
	public CorpseEntity(double x, double y) {
		super(x, y, Team.Neutral);
		setStartHealth(2);
		freezeTime = 10;
	}

	@Override
	public void tick() {
		super.tick();
		if (freezeTime > 0)
			return;

		xd *= 0.2;
		yd *= 0.2;
	}

	@Override
	public Bitmap getSprite() {
		return Art.floorTiles[3][1];
	}
}
