package com.mojang.mojam.level.pathing;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Set;

import com.mojang.mojam.entity.mob.Mob;
import com.mojang.mojam.level.Level;
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
		
		if (start.equals(goal)){
			return new Path(true);
		}	
		return getShortestPath(start, goal);		
		
	}
	
	private Path getShortestPath(Node start, Node goal){
		Path shortestPath=new Path(false);
		if(start.equals(goal)){
			shortestPath= new Path(true);
			shortestPath.addNodeFront(start);
			return shortestPath;
		}
		addNeighbors(start);
		start.visited=true;
		Set<Node> neighbors = start.getNeighbors();
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
		start.visited=false;
		return shortestPath;
	}

	@Override
	public Path getTruePathNearby(Vec2 gridStart, Vec2 gridGoal) {
		nodes.clear();
		if (!canWalk(gridStart)){
			return new Path(false);
		}
		Node start = createNode(gridStart);
		Node goal = createNode(gridGoal);
		addNeighbors(goal);
		Set<Node> neighbors = goal.getNeighbors();
		if (neighbors.isEmpty()){
			return new Path(false);
		}
		return getShortestPathNearby(start, goal);		
	}

	private Path getShortestPathNearby(Node start, Node goal){
		Path shortestPath=new Path(false);
		addNeighbors(start);
		Set<Node> neighbors = goal.getNeighbors();
		for(Node neighbor : neighbors){
			if(neighbor.equals(start)){
				shortestPath = new Path(true);
				shortestPath.addNodeFront(neighbor);
				return shortestPath;
			}
		
		}
		start.visited=true;
		neighbors = start.getNeighbors();
		ArrayList<Path> neighborPaths = new ArrayList<Path>(neighbors.size());
		for(Node neighbor : neighbors){
			if(neighbor.visited){
				continue;
			}
			Path neighborPath = getShortestPathNearby(neighbor, goal);
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
		start.visited=false;
		return shortestPath;
	}

	@Override
	public Path getAnyTruePathNearby(Vec2 gridStart, Set<Vec2> gridGoals) {
		return oneAtATime(gridStart, gridGoals);
	}
}
