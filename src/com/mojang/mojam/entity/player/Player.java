package com.mojang.mojam.entity.player;

import java.awt.Point;

import com.mojang.mojam.entity.Bullet;
import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.entity.building.Building;
import com.mojang.mojam.entity.loot.Loot;
import com.mojang.mojam.entity.loot.LootCollector;
import com.mojang.mojam.entity.mob.Mob;
import com.mojang.mojam.entity.mob.Team;
import com.mojang.mojam.entity.particle.Sparkle;
import com.mojang.mojam.level.tile.Tile;
import com.mojang.mojam.math.Vec2;
import com.mojang.mojam.network.PacketHandler;
import com.mojang.mojam.network.TurnSynchronizer;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Bitmap;
import com.mojang.mojam.screen.Screen;

public abstract class Player extends Mob implements LootCollector{
	
	protected int startX;
	protected int startY;
	
	protected PacketHandler handler;
	
	protected String name;
	
	protected boolean moving, firing;
	protected int screenW, screenH;

	public boolean flashTime = false;
	protected int time=0;
	protected int steps=0;
	protected int nextWalkSmokeTick=0;
	protected boolean wasShooting;
	protected int shootDelay=0;
	protected int takeDelay=0;
	protected int muzzleImage = 0;
	protected int facing=0;
	protected int facing2=0;
	protected double yAim=1;
	protected double xAim=1;
	protected int muzzleTicks=0;
	protected double muzzleX=0;
	protected double muzzleY=0;
	protected int suckRadius=0;
	protected int walkTime=0;
	protected int score;
	public int money=1000;
	protected Vec2 mouseLocation;
	protected boolean mouseDown;


	public Player(int x, int y, int team) {
		super(x, y, team);
		startX = x;
		startY = y;
		
		if(team==Team.Team1){
			name="Lord Lard";
		}
		if(team==Team.Team2){
			name="Herr Von Speck";
		}

	}

	public abstract void tick();

	public void render(Screen screen){
		screenW=screen.w;
		screenH=screen.h;
		Bitmap[][] sheet = Art.lordLard;
		if (team == Team.Team2) {
			sheet = Art.herrSpeck;
		}

		int frame = (walkTime / 4 % 6 + 6) % 6;

		int facing = this.facing + (carrying != null ? 8 : 0);
		double xmuzzle = muzzleX + ((facing == 0) ? 4 : 0);
		double ymuzzle = muzzleY - ((facing == 0) ? 4 : 0);

		boolean behind = (facing >= 3 && facing <= 5);

		if (muzzleTicks > 0 && behind) {
			screen.blit(Art.muzzle[muzzleImage][0], xmuzzle, ymuzzle);
		}

		if (hurtTime % 2 != 0) {
			screen.colorBlit(sheet[frame][facing], pos.x - Tile.WIDTH / 2,
					pos.y - Tile.HEIGHT / 2 - 8, 0x80ff0000);
		} else if (flashTime) {
			screen.colorBlit(sheet[frame][facing], pos.x - Tile.WIDTH / 2,
					pos.y - Tile.HEIGHT / 2 - 8, 0x80ffff80);
		} else {
			screen.blit(sheet[frame][facing], pos.x - Tile.WIDTH / 2, pos.y
					- Tile.HEIGHT / 2 - 8);
		}

		if (muzzleTicks > 0 && !behind) {
			screen.blit(Art.muzzle[muzzleImage][0], xmuzzle, ymuzzle);
		}

		renderCarrying(screen, (frame == 0 || frame == 3) ? -1 : 0);
	}

	public abstract void collide(Entity entity, double xa, double ya);
	
	public void dropAllMoney() {

		money /= 2;
		while (money > 0) {
			double dir = TurnSynchronizer.synchedRandom.nextDouble() * Math.PI
					* 2;
			Loot loot = new Loot(pos.x, pos.y, Math.cos(dir), Math.sin(dir),
					money / 2);
			level.addEntity(loot);

			money -= loot.getValue();
		}
		money = 0;
	}
	
	
	@Override
	public void take(Loot loot) {
		loot.remove();
		level.addEntity(new Sparkle(pos.x, pos.y, -1, 0));
		level.addEntity(new Sparkle(pos.x, pos.y, +1, 0));
	}
	
	@Override
	public double getSuckPower() {
		return suckRadius / 60.0;
	}


	@Override
	public boolean canTake() {
		return takeDelay > 0;
	}


	@Override
	public void flash() {
		flashTime = true;
	}

	public Bitmap getSprite(){
		return null;
	}

	public void notifySucking(){
		
	}
	
	public void pickup(Building b) {
		level.removeEntity(b);
		carrying = b;
		carrying.onPickup();
	}
	
	public void setFacing(int facing) {
		this.facing = facing;
	}
	
	@Override
	protected boolean shouldBlock(Entity e) {
		if (carrying != null && e instanceof Bullet
				&& ((Bullet) e).owner == carrying) {
			return false;
		}
		return true;
	}

	public abstract void hurt(Entity source, int damage);

	public abstract void hurt(Bullet bullet);

	@Override
	public String getDeathSound() {
		return "/res/sound/Death.wav";
	}
	
	public void addScore(int score) {
		this.score += score;
		if(this.score>=level.TARGET_SCORE){
			handler.endGame(team);
		}

	}

	public int getScore() {
		return score;
	}

	public void setMouse(boolean down, Point location) {
		mouseDown = down;
		mouseLocation = new Vec2(location.x+(pos.x - (screenW / 2)), location.y+(pos.y - ((screenH - 24) / 2)));
		
	}

}