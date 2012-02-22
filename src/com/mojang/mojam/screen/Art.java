package com.mojang.mojam.screen;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mojang.mojam.MojamComponent;

public class Art {
	public static Bitmap[][] floorTiles = cut("/res/floortiles.png", 32, 32);
	public static int[][] floorTileColors = getColors(floorTiles);

	public static Bitmap[][] wallTiles = cut("/res/floortiles.png", 32, 56, 0, 104);
	public static Bitmap[][] treasureTiles = cut("/res/treasure.png", 32, 56);
	public static int[][] wallTileColors = getColors(wallTiles);

	public static Bitmap[][] darkness = cut("/res/dark.png", 32, 32);
	public static Bitmap[][] bullets = cut("/res/bullets.png", 16, 16);
	public static Bitmap[][] lordLard = cut("/res/lord_lard_sheet.png", 32, 32);
	public static Bitmap[][] herrSpeck = cut("/res/herr_von_speck_sheet.png", 32,
			32);
	public static Bitmap[] titles = cut("/res/titles.png", 128);
	public static Bitmap[][] buttons = cut("/res/buttons/buttons.png", 128, 24);
	public static Bitmap checked = load("/res/buttons/checked.png");
	public static Bitmap unchecked = load("/res/buttons/unchecked.png");
	public static Bitmap[][] font = cut("/res/gamfont.png", 8, 8);
	public static Bitmap[][] mapIcons = cut("/res/mapicons.png", 5, 5);
	public static Bitmap titleScreen = load("/res/screen/TITLESCREEN.png");
	public static Bitmap gameOverScreen = load("/res/screen/game_over.png");
	public static Bitmap pauseScreen = load("/res/screen/pause.png");
	public static Bitmap panel = load("/res/panel.png");
	public static Bitmap shadow = load("/res/shadow.png");
	public static Bitmap[][] slave = cut("/res/mob/slave.png", 32, 32);
	public static Bitmap[][] mummy = cut("/res/mob/enemy_mummy_anim_48.png", 48, 48);
	public static Bitmap[][] snake = cut("/res/mob/enemy_snake_anim_48.png", 48, 48);
	public static Bitmap[][] bat = cut("/res/mob/enemy_bat_32.png", 32, 32);
	public static Bitmap batShadow = load("/res/mob/shadow.png");
	public static Bitmap[][] turret = cut("/res/turret.png", 32, 32);
	public static Bitmap[][] mobSpawner = cut("/res/spawner.png", 32, 40);
	public static Bitmap[][] rails = cut("/res/rails.png", 32, 38);
	public static Bitmap[][] bullet = cut("/res/bullet.png", 16, 16);
	public static Bitmap[][] muzzle = cut("/res/muzzle.png", 16, 16);
	public static Bitmap[][] harvester = cut("/res/building/bot_vacuum.png", 32, 56);
	public static Bitmap[][] startLordLard = cut("/res/start_lordlard.png", 32, 32);
	public static Bitmap[][] startHerrSpeck = cut("/res/start_herrspeck.png", 32,
			32);

	public static Bitmap[][] pickupCoinBronzeSmall = cut(
			"/res/pickup/pickup_coin_bronze_small_8.png", 8, 8);
	public static Bitmap[][] pickupCoinBronze = cut(
			"/res/pickup/pickup_coin_bronze_16.png", 16, 16);
	public static Bitmap[][] pickupCoinSilverSmall = cut(
			"/res/pickup/pickup_coin_silver_small_8.png", 8, 8);
	public static Bitmap[][] pickupCoinSilver = cut(
			"/res/pickup/pickup_coin_silver_16.png", 16, 16);
	public static Bitmap[][] pickupCoinGoldSmall = cut(
			"/res/pickup/pickup_coin_gold_small_8.png", 8, 8);
	public static Bitmap[][] pickupCoinGold = cut(
			"/res/pickup/pickup_coin_gold_16.png", 16, 16);
	public static Bitmap[][] pickupGemEmerald = cut(
			"/res/pickup/pickup_gem_emerald_12.png", 16, 16);
	public static Bitmap[][] pickupGemRuby = cut(
			"/res/pickup/pickup_gem_ruby_12.png", 16, 16);
	public static Bitmap[][] pickupGemDiamond = cut(
			"/res/pickup/pickup_gem_diamond_24.png", 24, 24);
	public static Bitmap[][] shineSmall = cut(
			"/res/pickup/effect_shine_small_13.png", 13, 13);
	public static Bitmap[][] shineBig = cut("/res/pickup/effect_shine_big_13.png",
			13, 13);

	public static Bitmap bomb = load("/res/bomb.png");

	public static Bitmap[][] fxEnemyDie = cut("/res/effects/fx_enemydie_64.png",
			64, 64);
	public static Bitmap[][] fxSteam24 = cut("/res/effects/fx_steam1_24.png", 24,
			24);
	public static Bitmap[][] fxSteam12 = cut("/res/effects/fx_steam2_12.png", 12,
			12);
	public static Bitmap[][] fxBombSplosion = cut(
			"/res/effects/fx_bombsplosion_big_32.png", 32, 32);
	public static Bitmap[][] fxBombSplosionSmall = cut(
			"/res/effects/fx_bombsplosion_small_32.png", 32, 32);
	public static Bitmap[][] fxDust12 = cut("/res/effects/fx_dust2_12.png", 12, 12);
	public static Bitmap[][] fxDust24 = cut("/res/effects/fx_dust1_24.png", 24, 24);
	

	private static Bitmap[][] cut(String string, int w, int h) {
		return cut(string, w, h, 0, 0);
	}

	private static Bitmap[][] cut(String string, int w, int h, int bx, int by) {
		try {
			BufferedImage bi = ImageIO.read(MojamComponent.class
					.getResource(string));

			int xTiles = (bi.getWidth() - bx) / w;
			int yTiles = (bi.getHeight() - by) / h;

			Bitmap[][] result = new Bitmap[xTiles][yTiles];

			for (int x = 0; x < xTiles; x++) {
				for (int y = 0; y < yTiles; y++) {
					result[x][y] = new Bitmap(w, h);
					bi.getRGB(bx + x * w, by + y * h, w, h,
							result[x][y].pixels, 0, w);
				}
			}

			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static int[][] getColors(Bitmap[][] tiles) {
		int[][] result = new int[tiles.length][tiles[0].length];
		for (int y = 0; y < tiles[0].length; y++) {
			for (int x = 0; x < tiles.length; x++) {
				result[x][y] = getColor(tiles[x][y]);
			}
		}
		return result;
	}

	private static int getColor(Bitmap bitmap) {
		int r = 0;
		int g = 0;
		int b = 0;
		for (int i = 0; i < bitmap.pixels.length; i++) {
			int col = bitmap.pixels[i];
			r += (col >> 16) & 0xff;
			g += (col >> 8) & 0xff;
			b += (col) & 0xff;
		}

		r /= bitmap.pixels.length;
		g /= bitmap.pixels.length;
		b /= bitmap.pixels.length;

		return 0xff000000 | r << 16 | g << 8 | b;
	}

	private static Bitmap load(String string) {
		try {
			BufferedImage bi = ImageIO.read(MojamComponent.class
					.getResource(string));

			int w = bi.getWidth();
			int h = bi.getHeight();

			Bitmap result = new Bitmap(w, h);
			bi.getRGB(0, 0, w, h, result.pixels, 0, w);

			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static Bitmap[] cut(String string, int h) {
		try {
			BufferedImage bi = ImageIO.read(MojamComponent.class
					.getResource(string));

			int yTiles = bi.getHeight() / h;
			int w = bi.getWidth();

			Bitmap[] result = new Bitmap[yTiles];

			for (int y = 0; y < yTiles; y++) {
				result[y] = new Bitmap(w, h);
				bi.getRGB(0, y * h, w, h, result[y].pixels, 0, w);
			}

			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}