package com.mojang.mojam.entity.mob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.entity.building.Base;
import com.mojang.mojam.entity.building.TreasurePile;
import com.mojang.mojam.level.Path;
import com.mojang.mojam.level.PathFinder;
import com.mojang.mojam.level.RecursivePather;
import com.mojang.mojam.level.tile.RailTile;
import com.mojang.mojam.level.tile.Tile;
import com.mojang.mojam.math.Vec2;
import com.mojang.mojam.network.TurnSynchronizer;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Bitmap;
import com.mojang.mojam.screen.Screen;

public class RailDroid extends Mob {
	private int dir = 0;
	private int lDir = 4;
	private int noTurnTime = 0;
	private int pauseTime = 0;
	public boolean carrying = false;
	public boolean pathFound = false;
	public int swapTime = 0;
	private ArrayList<TreasurePile> treasurePiles;
	private Vec2 startingPoint;
	private PathFinder pathFinder;
	private Path path;
	private TreasurePile fromPile;

	public RailDroid(double x, double y) {
		super(x, y, Team.Neutral);
		this.setSize(10, 8);
		deathPoints = 1;
		startingPoint = pos.clone();
		
	}
	@Override
	public void init(){
		super.init();
		ArrayList<Entity> tempPiles =level.getAllEntities(TreasurePile.class);
		treasurePiles=new ArrayList<TreasurePile>(tempPiles.size());
		for(Entity pile : tempPiles){
			treasurePiles.add((TreasurePile) pile);
		}
		pathFinder = new RecursivePather(level, this);
	}

	@Override
	public void tick() {
		if(!pathFound){
			if(carrying){
				path=pathFinder.getPath(pos, startingPoint);
			}else{
				ArrayList<Path> tempPaths = new ArrayList<Path>(treasurePiles.size());
				for(int i=0; i<treasurePiles.size(); i++){
					TreasurePile pile=treasurePiles.get(i);
					if(pile.getRemainingTreasure()<1){
						treasurePiles.remove(i);
					}
					Path tempPath = pathFinder.getPathNearby(pos, pile.pos);
					if(tempPath.isFinished){
						tempPaths.add(tempPath);
					}
				}
				if(!tempPaths.isEmpty()){
					Collections.sort(tempPaths);
					path=tempPaths.get(0);
				}else{
					path = new Path(false);
				}
			}
			if(path.isFinished && !path.isDone()){
				pathFound=true;
				System.out.println("path found!");
			}
		}
		

		xBump = yBump = 0;

		super.tick();
		if (freezeTime > 0)
			return;
		if (swapTime > 0)
			swapTime--;
		boolean hadPaused = pauseTime > 0;
		if (pauseTime > 0) {
			pauseTime--;
			if (pauseTime > 0)
				return;
		}
		int xt = (int) (pos.x / Tile.WIDTH);
		int yt = (int) (pos.y / Tile.HEIGHT);

		boolean cr = level.getTile(xt, yt) instanceof RailTile;
		boolean lr = level.getTile(xt - 1, yt) instanceof RailTile;
		boolean rr = level.getTile(xt + 1, yt) instanceof RailTile;
		boolean ur = level.getTile(xt, yt - 1) instanceof RailTile;
		boolean dr = level.getTile(xt, yt + 1) instanceof RailTile;
		xd *= 0.4;
		yd *= 0.4;

		double xcd = pos.x - (xt * Tile.WIDTH + 16);
		double ycd = pos.y - (yt * Tile.HEIGHT + 16);
		boolean centerIsh = xcd * xcd + ycd * ycd < 2 * 2;
		boolean xcenterIsh = xcd * xcd < 2 * 2;
		boolean ycenterIsh = ycd * ycd < 2 * 2;

		if (!xcenterIsh){
			ur = false;
			dr = false;
		}

		if (!ycenterIsh){
			lr = false;
			rr = false;
		}

		int lWeight = 0;
		int uWeight = 0;
		int rWeight = 0;
		int dWeight = 0;

		if (noTurnTime > 0)
			noTurnTime--;

		if (noTurnTime == 0 && (!cr || dir == 0 || centerIsh)) {
			noTurnTime = 4;
			// int nd = 0;
			if (dir == 1 && lr)
				lWeight += 16;
			if (dir == 2 && ur)
				uWeight += 16;
			if (dir == 3 && rr)
				rWeight += 16;
			if (dir == 4 && dr)
				dWeight += 16;

			if (lWeight + uWeight + rWeight + dWeight == 0) {
				if ((dir == 1 || dir == 3)) {
					if (ur)
						uWeight += 4;
					if (dr)
						dWeight += 4;
				}
				if ((dir == 2 || dir == 4)) {
					if (lr)
						lWeight += 4;
					if (rr)
						rWeight += 4;
				}
			}
			if (lWeight + uWeight + rWeight + dWeight == 0) {
				if (lr)
					lWeight += 1;
				if (ur)
					uWeight += 1;
				if (rr)
					rWeight += 1;
				if (dr)
					dWeight += 1;
			}

			if (dir == 1)
				rWeight = 0;
			if (dir == 2)
				dWeight = 0;
			if (dir == 3)
				lWeight = 0;
			if (dir == 4)
				uWeight = 0;

			int totalWeight = lWeight + uWeight + rWeight + dWeight;
			if (totalWeight == 0) {
				dir = 0;
			} else {
				int res = TurnSynchronizer.synchedRandom.nextInt(totalWeight);
				// dir = 0;
				dir = (((dir - 1) + 2) & 3) + 1;

				uWeight += lWeight;
				rWeight += uWeight;
				dWeight += rWeight;

				if (res < lWeight)
					dir = 1;
				else if (res < uWeight)
					dir = 2;
				else if (res < rWeight)
					dir = 3;
				else if (res < dWeight)
					dir = 4;
			}
			if(pathFound){
				Vec2 nextStep = path.getWorldPos(path.getIndex());
				if(nextStep.dist(pos)<Tile.WIDTH){
					path.next();
					if(path.isDone()){
						pathFound=false;
					}
				}
				double nextX =  nextStep.x-pos.x;
				double nextY =  nextStep.y-pos.y;
				if(nextX>.5*Tile.WIDTH&&rr){
					dir=3;
				}
				if(nextX<-.5*Tile.WIDTH&&lr){
					dir=1;
				}
				if(nextY>.5*Tile.HEIGHT&&dr){
					dir=4;
				}
				if(nextY<-.5*Tile.HEIGHT&&ur){
					dir=2;
				}
			}

			// dir = nd;
		}

		if (cr) {
			double r = 1;
			if (!(dir == 1 || dir == 3) && xcd < -r)
				xd += 0.3;
			if (!(dir == 1 || dir == 3) && xcd > +r)
				xd -= 0.3;
			if (!(dir == 2 || dir == 4) && ycd < -r)
				yd += 0.3;
			if (!(dir == 2 || dir == 4) && ycd > +r)
				yd -= 0.3;
			// if (!(dir == 1 || dir == 3) && xcd >= -r && xcd <= r) xd = -xcd;
			// if (!(dir == 2 || dir == 4) && ycd >= -r && ycd <= r) yd = -ycd;
		}
		double speed = 0.7;
		if (dir > 0)
			lDir = dir;
		if (dir == 1)
			xd -= speed;
		if (dir == 2)
			yd -= speed;
		if (dir == 3)
			xd += speed;
		if (dir == 4)
			yd += speed;

		Vec2 oldPos = pos.clone();
		move(xd, yd);
		if (dir > 0 && oldPos.distSqr(pos) < 0.1 * 0.1) {
			if (hadPaused) {
				dir = (((dir - 1) + 2) & 3) + 1;
				noTurnTime = 0;
			} else {
				pauseTime = 10;
				noTurnTime = 0;
			}
		}

		if (!carrying && swapTime == 0) {
			Set<Entity> pilesInRange = level.getEntities(getBB().grow(32), TreasurePile.class);
			boolean treasureInRange=false;
			for(Entity ent : pilesInRange){
				pathFound = false;
				TreasurePile pile = (TreasurePile) ent;
				if(pile.takeTreasure()){
					treasureInRange=true;
					fromPile=pile;
					break;
				}
			}
			if (treasureInRange) {
				swapTime = 30;
				carrying = true;
			}
		}
		if (carrying && swapTime == 0) {
			Set<Entity> basesInRange = level.getEntities(getBB().grow(16), Base.class);
			for(Entity ent : basesInRange){
				Base base = (Base) ent;
				carrying=false;
				fromPile=null;
				pathFound = false;
				level.addScore(base.team, 2);
			}
		}
		// level.getTile(xt, yt)
	}

	@Override
	public Bitmap getSprite() {
		if (lDir == 1)
			return Art.slave[1][1];
		if (lDir == 2)
			return Art.slave[0][1];
		if (lDir == 3)
			return Art.slave[1][0];
		if (lDir == 4)
			return Art.slave[0][0];
		return Art.slave[0][0];
	}

	@Override
	public void handleCollision(Entity entity, double xa, double ya) {
		super.handleCollision(entity, xa, ya);
		if (entity instanceof RailDroid) {
			RailDroid other = (RailDroid) entity;
			if (other.carrying != carrying && carrying) {
				if (lDir == 1 && other.pos.x > pos.x - 4)
					return;
				if (lDir == 2 && other.pos.y > pos.y - 4)
					return;
				if (lDir == 3 && other.pos.x < pos.x + 4)
					return;
				if (lDir == 4 && other.pos.y < pos.y + 4)
					return;

				if (other.lDir == 1 && pos.x > other.pos.x - 4)
					return;
				if (other.lDir == 2 && pos.y > other.pos.y - 4)
					return;
				if (other.lDir == 3 && pos.x < other.pos.x + 4)
					return;
				if (other.lDir == 4 && pos.y < other.pos.y + 4)
					return;

				if (other.swapTime == 0 && swapTime == 0) {
					other.swapTime = swapTime = 15;

					boolean tmp = other.carrying;
					other.carrying = carrying;
					carrying = tmp;
					
					TreasurePile tmpPile = other.fromPile;
					other.fromPile = fromPile;
					fromPile = tmpPile;
					
					other.pathFound = false;
					pathFound = false;
					
				}
			}
		}
	}

	@Override
	public void die(){
		super.die();
		if(carrying){
			fromPile.addTreasure();
		}
	}
	@Override
	protected boolean shouldBlock(Entity e) {
		// if (e instanceof Player && ((Player) e).team == team) return false;
		return super.shouldBlock(e);
	}

	@Override
	public void render(Screen screen) {
		super.render(screen);
		if (carrying) {
			screen.blit(Art.bullets[0][0], pos.x - 8, pos.y - 24 - yOffs);

		}
	}

}
