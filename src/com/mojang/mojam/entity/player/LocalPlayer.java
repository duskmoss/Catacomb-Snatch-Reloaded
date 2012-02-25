package com.mojang.mojam.entity.player;

import java.util.Random;

import com.mojang.mojam.Keys;
import com.mojang.mojam.MojamComponent;
import com.mojang.mojam.entity.Bullet;
import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.entity.IUsable;
import com.mojang.mojam.entity.animation.SmokePuffAnimation;
import com.mojang.mojam.entity.building.Building;
import com.mojang.mojam.entity.loot.Loot;
import com.mojang.mojam.entity.mob.RailDroid;
import com.mojang.mojam.entity.mob.Team;
import com.mojang.mojam.level.tile.RailTile;
import com.mojang.mojam.level.tile.Tile;
import com.mojang.mojam.math.Vec2;
import com.mojang.mojam.network.PacketHandler;
import com.mojang.mojam.network.TurnSynchronizer;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class LocalPlayer extends Player{

	public static final int COST_RAIL = 10;
	public static final int COST_DROID = 50;
	public static final int COST_REMOVE_RAIL = 15;
	public static final int REGEN_INTERVAL = 60 * 3;

	public Keys keys;
	public double xd, yd;
	private Entity selected = null;
	static final int RailDelayTicks = 15;
	private int lastRailTick = -999;
	private final static int INTERACT_DISTANCE = 20 * 20; // Sqr
	private int regenDelay = 0;

	public LocalPlayer(Keys keys, int x, int y, int team, PacketHandler hand) {
		super(x, y, team);
		this.keys = keys;
		handler = hand;
	}


	
	@Override
	public void tick() {
		time++;
		minimapIcon = time / 3 % 4;
		if (minimapIcon == 3) {
			minimapIcon = 1;
		}

		if (regenDelay > 0) {
			regenDelay--;
			if (regenDelay == 0) {
				if (health < maxHealth)
					health++;
				regenDelay = REGEN_INTERVAL;
			}
		}
		
		if(flashTime){
			flashTime=false;
		}
		if (hurtTime > 0) {
			hurtTime--;
		}
		if (freezeTime > 0) {
			freezeTime--;
		}
		if (muzzleTicks > 0) {
			muzzleTicks--;
		}
		
		double xa = 0;
		double ya = 0;
		moving=false;
		
		if (keys.up.isDown) {
			ya--;
			moving=true;
		}
		if (keys.down.isDown) {
			ya++;
			moving=true;
		}
		if (keys.left.isDown) {
			xa--;
			moving=true;
		}
		if (keys.right.isDown) {
			xa++;
			moving=true;
		}
		
		if (moving) {
			if ((carrying == null && steps % 10 == 0) || (steps % 20 == 0)) {
				MojamComponent.soundPlayer.playSound("/res/sound/Step "
						+ (TurnSynchronizer.synchedRandom.nextInt(2) + 1)
						+ ".wav", (float) pos.x, (float) pos.y, true);
			}
			steps++;
		}
		

		double xaf = 0;
		double yaf = 0;
		firing = false;

		if (keys.fireup.isDown) {
			yaf--;
			firing = true;
		}
		if (keys.firedown.isDown) {
			yaf++;
			firing = true;
		}
		if (keys.fireleft.isDown) {
			xaf--;
			firing = true;
		}
		if (keys.fireright.isDown) {
			xaf++;
			firing = true;
		}

		if (!firing && moving) {
			xAim *= 0.7;
			yAim *= 0.7;
			xAim += xa;
			yAim += ya;
			facing = (int) ((Math.atan2(-xAim, yAim) * 8 / (Math.PI * 2) + 8.5)) & 7;
		}

		if (firing) {
			xAim *= 0.7;
			yAim *= 0.7;
			xAim += xaf;
			yAim += yaf;
			facing = (int) ((Math.atan2(-xAim, yAim) * 8 / (Math.PI * 2) + 8.5)) & 7;
		}
		
		

		if (xa != 0 || ya != 0) {
			facing2 = (int) ((Math.atan2(-xa, ya) * 8 / (Math.PI * 2) + 8.5)) & 7;
			int diff = facing - facing2;
			if (diff >= 4) {
				diff -= 8;
			}
			if (diff < -4) {
				diff += 8;
			}

			if (carrying != null) {
				if (diff > 2 || diff < -4) {
					walkTime--;
				} else {
					walkTime++;
				}
			}
			if (diff > 2 || diff < -4) {
				walkTime--;
			} else {
				walkTime++;
			}

			Random random = TurnSynchronizer.synchedRandom;
			if (walkTime >= nextWalkSmokeTick) {
				level.addEntity(new SmokePuffAnimation(this, Art.fxDust12,
						35 + random.nextInt(10)));
				nextWalkSmokeTick += (15 + random.nextInt(15));
			}
			if (random.nextDouble() < 0.02f)
				level.addEntity(new SmokePuffAnimation(this, Art.fxDust12,
						35 + random.nextInt(10)));

			double dd = Math.sqrt(xa * xa + ya * ya);
			double speed = getSpeed() / dd;

			xa *= speed;
			ya *= speed;

			xd += xa;
			yd += ya;
		}

		if (freezeTime > 0) {
			move(xBump, yBump);
		} else {
			move(xd + xBump, yd + yBump);

		}
		xd *= 0.4;
		yd *= 0.4;
		xBump *= 0.8;
		yBump *= 0.8;
		muzzleImage = (muzzleImage + 1) & 3;

		if (carrying == null && firing) {
			wasShooting = true;
			if (takeDelay > 0) {
				takeDelay--;
			}
			if (shootDelay-- <= 0) {
				double dir = Math.atan2(yAim, xAim) + (TurnSynchronizer.synchedRandom.nextFloat() - TurnSynchronizer.synchedRandom.nextFloat()) * 0.1;

				xa = Math.cos(dir);
				ya = Math.sin(dir);
				xd -= xa;
				yd -= ya;
				Entity bullet = new Bullet(this, xa, ya);
				level.addEntity(bullet);
				muzzleTicks = 3;
				muzzleX = bullet.pos.x + 7 * xa - 8;
				muzzleY = bullet.pos.y + 5 * ya - 8 + 1;
				shootDelay = 5;
				MojamComponent.soundPlayer.playSound("/res/sound/Shot 1.wav",
						(float) pos.x, (float) pos.y);
			}
		} else {
			if (wasShooting) {
				suckRadius = 60;
			}
			wasShooting = false;
			if (suckRadius > 0) {
				suckRadius--;
			}
			takeDelay = 15;
			shootDelay = 0;
		}

		int x = (int) pos.x / Tile.WIDTH;
		int y = (int) pos.y / Tile.HEIGHT;

		if (keys.build.isDown && !keys.build.wasDown) {
			if (level.getTile(x, y).isBuildable()) {
				if (money >= COST_RAIL && time - lastRailTick >= RailDelayTicks) {
					lastRailTick = time;
					level.placeTile(x, y, new RailTile(level.getTile(x, y)),
							this);
					payCost(COST_RAIL);
				}
			} else if (level.getTile(x, y) instanceof RailTile) {
				if ((y < 8 && team == Team.Team2)
						|| (y > level.height - 9 && team == Team.Team1)) {
					if (money >= COST_DROID) {
						level.addEntity(new RailDroid(pos.x, pos.y));
						payCost(COST_DROID);
					}
				} else {

					if (money >= COST_REMOVE_RAIL
							&& time - lastRailTick >= RailDelayTicks) {
						lastRailTick = time;
						if (((RailTile) level.getTile(x, y)).remove()) {
							payCost(COST_REMOVE_RAIL);
						}
					}
					MojamComponent.soundPlayer.playSound(
							"/res/sound/Track Place.wav", (float) pos.x,
							(float) pos.y);
				}
			}
		}

		if (carrying != null) {
			carrying.setPos(pos.x, pos.y - 20);
			carrying.tick();

			if (keys.use.wasPressed()) {
				Vec2 buildPos = pos.clone();
				Tile tile = level.getTile(buildPos);
				boolean allowed = false;
				if (tile != null && tile.isBuildable()) {
					allowed = true;
				}
				/*
				 * for (Entity e : level.getEntities(x -
				 * Building.MIN_BUILDING_DISTANCE, y -
				 * Building.MIN_BUILDING_DISTANCE, x +
				 * Building.MIN_BUILDING_DISTANCE, y +
				 * Building.MIN_BUILDING_DISTANCE, Building.class)) { if
				 * (e.pos.distSqr(buildPos) < Building.MIN_BUILDING_DISTANCE) {
				 * allowed = false; break; } }
				 */
				if (allowed
						&& (!(carrying instanceof IUsable) || (carrying instanceof IUsable && ((IUsable) carrying)
								.isAllowedToCancel()))) {
					carrying.removed = false;
					carrying.xSlide = xAim * 3;
					carrying.ySlide = yAim * 3;
					carrying.freezeTime = 10;
					carrying.setPos(buildPos.x, buildPos.y);
					level.addEntity(carrying);
					carrying = null;
				}
				// }
			}
		} else {
			Entity closest = null;
			double closestDist = Double.MAX_VALUE;
			for (Entity e : level.getEntitiesSlower(pos.x - INTERACT_DISTANCE,
					pos.y - INTERACT_DISTANCE, pos.x + INTERACT_DISTANCE, pos.y
							+ INTERACT_DISTANCE, Building.class)) {
				double dist = e.pos.distSqr(getInteractPosition());
				if (dist <= INTERACT_DISTANCE && dist < closestDist) {
					closestDist = dist;
					closest = e;
				}
			}
			if (selected != null) {
				((IUsable) selected).setHighlighted(false);
			}
			if (closest != null && ((IUsable) closest).isHighlightable()) {
				selected = closest;
				((IUsable) selected).setHighlighted(true);
			}

			if (selected != null) {
				if (selected.pos.distSqr(getInteractPosition()) > INTERACT_DISTANCE) {
					((IUsable) selected).setHighlighted(false);
					selected = null;
				} else if (selected instanceof IUsable && keys.use.wasPressed()) {
					((IUsable) selected).use(this);
				}
			}

		}

		handler.Sync(pos.x, pos.y, xAim, yAim, firing);

		level.reveal(x, y, 5);

	}

	public void payCost(int cost) {
		money -= cost;

		while (cost > 0) {
			Random random = TurnSynchronizer.synchedRandom;
			double dir = random.nextDouble() * Math.PI
					* 2;
			Loot loot = new Loot(pos.x, pos.y, Math.cos(dir), Math.sin(dir),
					cost / 2);
			loot.makeUntakeable();
			level.addEntity(loot);

			cost -= loot.getValue();
		}
	}

	public void addMoney(int s) {
		if (s > 0)
			money += s;
	}


	
	@Override
	public void render(Screen screen) {
		super.render(screen);
	}
	
	@Override 
	public void pickup(Building b) {
		super.pickup(b);
		handler.pickup(b);
		
	}
	
	@Override
	public void take(Loot loot) {
		super.take(loot);
		money += loot.getValue();
	}

	
	@Override
	public void collide(Entity entity, double xa, double ya) {
		xd += xa * 0.4;
		yd += ya * 0.4;
	}


	public int getMoney() {
		return money;
	}

	public boolean useMoney(int cost) {
		if (cost > money) {
			return false;
		}

		money -= cost;
		return true;
	}

	private Vec2 getInteractPosition() {
		return pos.add(new Vec2(Math
				.cos((facing) * (Math.PI) / 4 + Math.PI / 2), Math.sin((facing)
				* (Math.PI) / 4 + Math.PI / 2)).scale(30));
	}

	@Override
	public void hurt(Entity source, int damage) {
		if (isImmortal) {
			return;
		}

		if (hurtTime == 0) {
			hurtTime = 25;
			freezeTime = 15;
			health -= damage;
			regenDelay = REGEN_INTERVAL;

			if (health <= 0) {
				handler.notify(name + " has died!");
				carrying = null;
				dropAllMoney();
				pos.set(startX, startY);
				health = maxHealth;
			} else {

				double dist = source.pos.dist(pos);
				xBump = (pos.x - source.pos.x) / dist * 10;
				yBump = (pos.y - source.pos.y) / dist * 10;
			}
		}
	}


	
	@Override
	public void hurt(Bullet bullet) {
		hurt(bullet, 1);
	}
}
