package com.mojang.mojam.level.pathing;

import java.util.PriorityQueue;
import java.util.Set;

import com.mojang.mojam.entity.mob.Mob;
import com.mojang.mojam.level.Level;
import com.mojang.mojam.math.Vec2;

public class AStarPather extends PathFinder {
	public AStarPather(Level level, Mob mob) {
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
		if (start.equals(goal))
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
	public Path getTruePathNearby(Vec2 gridStart, Vec2 gridGoal) {
		nodes.clear();
		
		Node goal = createNode(gridGoal);
		addNeighbors(goal);
		Set<Node> neighbors = goal.getNeighbors();
		
		if (!canWalk(gridStart)){
			return new Path(false);
		}
		if (neighbors.size()==0){
			return new Path(false);
		}

		Node start = createNode(gridStart);
		for(Node node : neighbors){
			if (start.equals(node)){
				return new Path(true);
			}
		}

		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		queue.offer(start);

		Path bestPath = new Path(false);
		while (queue.size() != 0) {
			Node current = queue.poll();
			if (current.visited){
				continue;
			}

			addNeighbors(current);
			current.visited = true;

			neighbors=goal.getNeighbors();
			for(Node node : neighbors){
				if (current.equals(node)){
					bestPath = reconstructPath(node);
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
				neighbor.priority = neighbor.heuristicDistance;
				if (neighbor.parent == null) {
					neighbor.parent = current;
					queue.offer(neighbor);
				} else{
					neighbor.parent = current;
				}

				
			}
		}

		return bestPath;	
	}

	@Override
	public Path getAnyTruePathNearby(Vec2 gridStart, Set<Vec2> gridGoals) {
		return oneAtATime(gridStart, gridGoals);
	}


	protected Path reconstructPath(Node lastNode) {
		Path path = new Path(true);
		Node node = lastNode;
		while (node != null) {
			path.addNodeFront(node);
			node = node.parent;
		}
		return path;
	}

}
