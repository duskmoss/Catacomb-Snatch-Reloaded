package com.mojang.mojam.level;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.mojang.mojam.entity.mob.Mob;
import com.mojang.mojam.math.Vec2;

public class RecursivePather extends PathFinder {

	public RecursivePather(Level level, Mob mob) {
		super(level, mob);
	}

	@Override
	public Path getTruePath(Vec2 gridStart, Vec2 gridGoal) {
		nodes.clear();
		if (!canWalk(gridStart)){
			return new Path(false);
		}
		if (!canWalk(gridGoal)){
			return new Path(false);
		}

		Node start = createNode(gridStart);
		Node goal = createNode(gridGoal);
		
		if (start.pos.equals(goal.pos)){
			return new Path(true);
		}
		
		return getShortestPath(start, goal);		
		
	}
	
	private Path getShortestPath(Node start, Node goal){
		if(start==goal){
			return new Path(true);
		}
		start.visited=true;
		Path shortestPath=new Path(false);
		addNeighbors(start);
		List<Node> neighbors = start.getNeighbors();
		ArrayList<Path> neighborPaths = new ArrayList<Path>(neighbors.size());
		for(Node neighbor : neighbors){
			if(neighbor.visited){
				continue;
			}
			Path neighborPath = getShortestPath(neighbor, goal);
			if(neighborPath.isFinished){
				neighborPaths.add(neighborPath);
			}
		}
		if(!neighborPaths.isEmpty()){
			Collections.sort(neighborPaths);
			shortestPath=neighborPaths.get(0);
		}
		if(shortestPath.isFinished){
			shortestPath.addNodeFront(start);
		}
		return shortestPath;
	}

	@Override
	public Path getTruePathNearby(Vec2 gridStart, Vec2 gridGoal) {
		nodes.clear();
		if (!canWalk(gridStart)){
			return new Path(false);
		}
		if (!canWalk(gridGoal)){
			return new Path(false);
		}

		Node start = createNode(gridStart);
		Node goal = createNode(gridGoal);
		
		if (start.pos.equals(goal.pos)){
			return new Path(true);
		}
		
		return getShortestPathNearby(start, goal);		
	}

	private Path getShortestPathNearby(Node start, Node goal){
		Path shortestPath=new Path(false);
		addNeighbors(start);
		start.visited=true;
		List<Node> neighbors = start.getNeighbors();
		for(Node neighbor : neighbors){
			if(neighbor==goal){
				return new Path(true);
			}
		}
		ArrayList<Path> neighborPaths = new ArrayList<Path>(neighbors.size());
		for(Node neighbor : neighbors){
			if(neighbor.visited){
				continue;
			}
			Path neighborPath = getShortestPath(neighbor, goal);
			if(neighborPath.isFinished){
				neighborPaths.add(neighborPath);
			}
		}
		if(!neighborPaths.isEmpty()){
			Collections.sort(neighborPaths);
			shortestPath=neighborPaths.get(0);
		}
		if(shortestPath.isFinished){
			shortestPath.addNodeFront(start);
		}
		return shortestPath;
	}
}
