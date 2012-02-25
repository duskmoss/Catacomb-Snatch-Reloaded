package com.mojang.mojam.entity.player;

import java.util.Random;

import com.mojang.mojam.MojamComponent;
import com.mojang.mojam.entity.Bullet;
import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.entity.animation.SmokePuffAnimation;
import com.mojang.mojam.network.TurnSynchronizer;
import com.mojang.mojam.screen.Art;

public class RemotePlayer extends Player {

	private boolean moving;
	private boolean firing;

	public RemotePlayer(int x, int y, int team) {
		super(x, y, team);

		
	}
	
	@Override
	public void tick() {
		time++;
		minimapIcon = time / 3 % 4;
		if (minimapIcon == 3) {
			minimapIcon = 1;
		}
		if (hurtTime > 0) {
			hurtTime--;
		}
		if (muzzleTicks > 0) {
			muzzleTicks--;
		}
		if(flashTime){
			flashTime=false;
		}
		facing = (int) ((Math.atan2(-xAim, yAim) * 8 / (Math.PI * 2) + 8.5)) & 7;
		
		if (moving) {
			if ((carrying == null && steps % 10 == 0) || (steps % 20 == 0)) {
				MojamComponent.soundPlayer.playSound("/res/sound/Step "
						+ (TurnSynchronizer.synchedRandom.nextInt(2) + 1)
						+ ".wav", (float) pos.x, (float) pos.y, true);
			}
			steps++;
			Random random = TurnSynchronizer.synchedRandom;
			if (steps >= nextWalkSmokeTick) {
				level.addEntity(new SmokePuffAnimation(this, Art.fxDust12,
						35 + random.nextInt(10)));
				nextWalkSmokeTick += (15 + random.nextInt(15));
			}
			if (random.nextDouble() < 0.02f){
				level.addEntity(new SmokePuffAnimation(this, Art.fxDust12,
						35 + random.nextInt(10)));
			}
		}
		
		if (firing) {
			MojamComponent.soundPlayer.playSound("/res/sound/Shot 1.wav",
					(float) pos.x, (float) pos.y);
		}
		
		muzzleImage = (muzzleImage + 1) & 3;
		
		if (firing) {
			wasShooting = true;
			if (takeDelay > 0) {
				takeDelay--;
			}
			if (shootDelay-- <= 0) {
				double dir = Math.atan2(yAim, xAim)	+ (TurnSynchronizer.synchedRandom.nextFloat() - TurnSynchronizer.synchedRandom.nextFloat()) * 0.1;

				double xa = Math.cos(dir);
				double ya = Math.sin(dir);
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
	}
	
	public void sync(double x, double y, double xa, double ya, boolean fire){
		moving=((pos.x-x)>0||(pos.y-y)>0);
		pos.x=x;
		pos.y=y;
		xAim=xa;
		yAim=ya;
		firing=fire;
				
	}

	
	
	@Override
	public void collide(Entity entity, double xa, double ya) {
	}

		
	@Override
	public void hurt(Entity source, int damage) {
		if (hurtTime == 0) {
			hurtTime = 25;
		}

	}

	
	@Override
	public void hurt(Bullet bullet) {
		hurt(bullet, 1);

	}


}
