package com.mojang.mojam.level.tile;

import com.mojang.mojam.level.Level;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class SandTile extends Tile {
	@Override
	public void init(Level level, int x, int y) {
		super.init(level, x, y);
		img = 5;
		minimapColor = Art.floorTileColors[img & 7][img / 8];
	}

	@Override
	public void render(Screen screen) {
		super.render(screen);
	}
}
