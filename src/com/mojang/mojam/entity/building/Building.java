package com.mojang.mojam.entity.building;

import java.io.Serializable;

import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.entity.IUsable;
import com.mojang.mojam.entity.mob.Mob;
import com.mojang.mojam.entity.player.LocalPlayer;
import com.mojang.mojam.entity.player.Player;
import com.mojang.mojam.math.BB;
import com.mojang.mojam.network.TurnSynchronizer;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Bitmap;
import com.mojang.mojam.screen.Screen;

public class Building extends Mob implements IUsable, Serializable {
	
	private static final long serialVersionUID = 4511300519202209008L;

	public static final int SPAWN_INTERVAL = 60;

	public int spawnTime = 0;
	public boolean highlight = false;

	public Building(double x, double y, int team) {
		super(x, y, team);
		setStartHealth(20);
		freezeTime = 10;
		spawnTime = TurnSynchronizer.synchedRandom.nextInt(SPAWN_INTERVAL);
	}

	@Override
	public void render(Screen screen) {
		super.render(screen);
		renderMarker(screen);
	}

	protected void renderMarker(Screen screen) {
		if (highlight) {
			BB bb = getBB();
			bb = bb.grow((getSprite().w - (bb.x1 - bb.x0))
					/ (3 + Math.sin(System.currentTimeMillis() * .01)));
			int width = (int) (bb.x1 - bb.x0);
			int height = (int) (bb.y1 - bb.y0);
			Bitmap marker = new Bitmap(width, height);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if ((x < 2 || x > width - 3 || y < 2 || y > height - 3)
							&& (x < 5 || x > width - 6)
							&& (y < 5 || y > height - 6)) {
						int i = x + y * width;
						marker.pixels[i] = 0xffffffff;
					}
				}
			}
			screen.blit(marker, bb.x0, bb.y0 - 4);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (freezeTime > 0) {
			return;
		}
		if (hurtTime <= 0)
			health = maxHealth;

		xd = 0.0;
		yd = 0.0;
	}

	@Override
	public Bitmap getSprite() {
		return Art.floorTiles[3][2];
	}

	@Override
	public boolean move(double xBump, double yBump) {
		return false;
	}

	@Override
	public void slideMove(double xa, double ya) {
		super.move(xa, ya);
	}

	//
	// Upgrade
	//
	protected void upgradeComplete(int upgradeLevel) {
	}

	private int upgradeLevel = 0;
	private int maxUpgradeLevel = 0;
	private int[] upgradeCosts = null;

	public boolean upgrade(LocalPlayer p) {
		if (upgradeLevel >= maxUpgradeLevel)
			return false;

		final int cost = upgradeCosts[upgradeLevel];
		if (cost > p.getMoney())
			return false;

		++upgradeLevel;
		p.payCost(cost);
		upgradeComplete(upgradeLevel);
		return true;
	}

	void makeUpgradeableWithCosts(int[] costs) {
		maxUpgradeLevel = 0;
		if (costs == null)
			return;

		upgradeCosts = costs;
		maxUpgradeLevel = costs.length - 1;
		upgradeComplete(0);
	}

	@Override
	public void use(Entity user) {
		if (user instanceof Player) {
			((LocalPlayer) user).pickup(this);
		}
	}

	@Override
	public boolean isHighlightable() {
		return true;
	}

	@Override
	public void setHighlighted(boolean hl) {
		highlight = hl;
	}

	@Override
	public boolean isAllowedToCancel() {
		return true;
	}
}
