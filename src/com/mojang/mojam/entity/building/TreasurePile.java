package com.mojang.mojam.entity.building;

import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Bitmap;

public class TreasurePile extends Building {

	private int treasures;
	private int initial;

	public TreasurePile(double x, double y, int team) {
		super(x, y, team);
		setStartHealth(20);
		freezeTime = 10;
		minimapIcon = 5;
		isImmortal = true;
	}

	@Override
	public void tick() {
		super.tick();
		if (freezeTime > 0)
			return;
	}

	@Override
	public Bitmap getSprite() {
		int percent=(treasures*100/initial);
		if(percent>67){
			return Art.treasureTiles[0][0];
		}else if(percent>33){
			return Art.treasureTiles[1][0];
		}else if(percent>0){
			return Art.treasureTiles[2][0];
		}else{
			return Art.treasureTiles[3][0];
		}
		
	}
	
	public void setTreasures(int amount){
		this.treasures=amount;
		this.initial=amount;
		
	}
	
	public boolean takeTreasure(){
		if(treasures>0){
			treasures--;
			return true;
		}else{
			return false;
		}
	}
	public void addTreasure(){
		treasures++;
	}
	public int getRemainingTreasure(){
		return treasures;
	}

	@Override
	public void use(Entity user) {

	}

	@Override
	public boolean isHighlightable() {
		return false;
	}

}
