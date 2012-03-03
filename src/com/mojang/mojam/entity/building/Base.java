package com.mojang.mojam.entity.building;

import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Bitmap;

public class Base extends Building {
	public Base(double x, double y, int team) {
		super(x, y, team);
		setStartHealth(20);
		freezeTime = 10;
	}

	@Override
	public void tick() {
		super.tick();
		if (freezeTime > 0)
			return;
	}

	@Override
	public Bitmap getSprite() {
		return Art.floorTiles[3][2];
	}
	
	@Override
	protected boolean shouldBlock(Entity e) {
		return false;
	}

	@Override
	public boolean isHighlightable() {
		return false;
	}
}
