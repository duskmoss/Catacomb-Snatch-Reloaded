package com.mojang.mojam.level;

import java.util.HashMap;
import com.mojang.mojam.entity.mob.Mob;
import com.mojang.mojam.level.tile.Tile;
import com.mojang.mojam.math.Vec2;

public abstract class PathFinder {

	protected Level level;
	protected Mob mob;
	protected HashMap<String, Node> nodes = new HashMap<String, Node>();
	Vec2 toGrid = new Vec2((1.0/Tile.WIDTH),(1.0/Tile.HEIGHT));
	private static final Vec2[] dirs = { new Vec2(-1, 0), new Vec2(1, 0),
				new Vec2(0, 1), new Vec2(0, -1) };

	public PathFinder(Level level, Mob mob) {
		this.level = level;
		this.mob = mob;
	}

	public Path getPath(Vec2 strt, Vec2 gl) {
		Vec2 gridStart=strt.mul(toGrid);
		Vec2 gridGoal=gl.mul(toGrid);
		return getTruePath(gridStart, gridGoal);
	}
	
	public Path getPathNearby(Vec2 strt, Vec2 gl) {
		Vec2 gridStart=strt.mul(toGrid);
		Vec2 gridGoal=gl.mul(toGrid);
		return getTruePathNearby(gridStart, gridGoal);
	}
	
	public abstract Path getTruePath(Vec2 gridStart, Vec2 gridGoal);

	
	public abstract Path getTruePathNearby(Vec2 gridStart, Vec2 gridGoal); 

	protected boolean canWalk(Vec2 gridPos) {
		Tile tile = level.getTile((int) gridPos.x, (int) gridPos.y);
		if (tile == null)
			return false;
		return tile.canPass(mob);
	}

	protected void addNeighbors(Node n) {
		Vec2 p = n.pos;
		for (Vec2 d : dirs)
			if (canWalk(p.add(d)))
				n.addNeighbor(getNode(p.add(d)));
	}

	private Node getNode(Vec2 p) {
		String hash = Node.getHash(p);
		Node n = nodes.get(hash);
		return n == null ? createNode(p) : n;
	}

	protected Node createNode(Vec2 p) {
		Node n = new Node(p);
		nodes.put(Node.getHash(p), n);
		return n;
	}

}