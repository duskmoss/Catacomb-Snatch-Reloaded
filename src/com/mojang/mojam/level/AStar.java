package com.mojang.mojam.level;

import java.util.List;
import java.util.PriorityQueue;

import com.mojang.mojam.entity.mob.Mob;
import com.mojang.mojam.math.Vec2;

public class AStar extends PathFinder {
	public AStar(Level level, Mob mob) {
		super(level, mob);
	}
	
	@Override
	public Path getTruePath(Vec2 gridStart, Vec2 gridGoal){

		nodes.clear();
		if (!canWalk(gridStart)){
			return new Path(false);
		}
		if (!canWalk(gridGoal)){
			return new Path(false);
		}

		Node start = createNode(gridStart);
		Node goal = createNode(gridGoal);
		if (start.pos.equals(goal.pos))
			return new Path(true);

		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		queue.offer(start);

		Path bestPath = new Path(false);
		while (queue.size() != 0) {
			Node current = queue.poll();
			if (current.visited)
				continue;

			if (current == goal) {
				bestPath = reconstructPath(goal);
				break;
			}

			addNeighbors(current);
			current.visited = true;

			for (Node neighbor : current.getNeighbors()) {
				if (neighbor.visited)
					continue;
				if (!canWalk(neighbor.pos))
					continue;

				double distance = current.pathDistance
						+ current.pos.dist(neighbor.pos);
				if (neighbor.parent != null	&& distance >= neighbor.pathDistance){
					continue;
				}

				neighbor.pathDistance = distance;
				neighbor.heuristicDistance = distance + neighbor.pos.dist(goal.pos);
				neighbor.priority = neighbor.heuristicDistance;
				if (neighbor.parent == null) {
					queue.offer(neighbor);
				}

				neighbor.parent = current;
			}
		}

		return bestPath;
	}
	
	@Override
	public Path getTruePathNearby(Vec2 strt, Vec2 gl) {
		Vec2 gridStart=strt.mul(toGrid);
		Vec2 gridGoal=gl.mul(toGrid);
		nodes.clear();
		
		Node goal = createNode(gridGoal);
		addNeighbors(goal);
		List<Node> neighbors = goal.getNeighbors();
		
		if (!canWalk(gridStart)){
			return new Path(false);
		}
		if (neighbors.size()==0){
			return new Path(false);
		}

		Node start = createNode(gridStart);
		for(Node node : neighbors){
			if (start.pos.equals(node.pos)){
				return new Path(true);
			}
		}

		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		queue.offer(start);

		Path bestPath = new Path(false);
		while (queue.size() != 0) {
			Node current = queue.poll();
			if (current.visited)
				continue;

			addNeighbors(current);
			current.visited = true;

			neighbors=current.getNeighbors();
			for(Node node : neighbors){
				if (goal.pos.equals(node.pos)){
					bestPath = reconstructPath(goal);
					break;
				}
			}

			
			for (Node neighbor : current.getNeighbors()) {
				if (neighbor.visited)
					continue;
				if (!canWalk(neighbor.pos))
					continue;

				double distance = current.pathDistance
						+ current.pos.dist(neighbor.pos);
				if (neighbor.parent != null
						&& distance >= neighbor.pathDistance)
					continue;

				neighbor.pathDistance = distance;
				neighbor.heuristicDistance = neighbor.pos.dist(goal.pos)
						+ distance;
				if (neighbor.parent == null) {
					neighbor.priority = neighbor.heuristicDistance;
					queue.offer(neighbor);
				} else{
					neighbor.priority = neighbor.heuristicDistance;
				}

				neighbor.parent = current;
			}
		}

		return bestPath;	
	}
	


	protected Path reconstructPath(Node goalNode) {
		Path path = new Path(true);
		Node node = goalNode;
		while (node != null) {
			path.addNodeFront(node);
			node = node.parent;
		}
		return path;
	}
}
