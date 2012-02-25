package com.mojang.mojam;

import com.mojang.mojam.entity.Bullet;
import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.entity.player.Player;

public class ComputerPlayer extends Player {

	public ComputerPlayer(int x, int y, int team) {
		super(x, y, team);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void collide(Entity entity, double xa, double ya) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hurt(Entity source, int damage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hurt(Bullet bullet) {
		// TODO Auto-generated method stub

	}

}
