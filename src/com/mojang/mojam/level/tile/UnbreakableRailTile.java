package com.mojang.mojam.level.tile;

public class UnbreakableRailTile extends RailTile {
	public UnbreakableRailTile(Tile parent) {
		super(parent);
	}

	@Override
	public boolean remove() {
		return false;
	}

	@Override
	public boolean isBuildable() {
		return false;
	}
}
