package com.mojang.mojam.level.pathing;

import java.util.HashSet;
import java.util.Set;

import com.mojang.mojam.entity.mob.Mob;
import com.mojang.mojam.level.Level;
import com.mojang.mojam.math.Vec2;

public class BreadthFirstPather extends PathFinder {
	
	private Set<Node> checkedNodes = new HashSet<Node>(100);

	public BreadthFirstPather(Level level, Mob mob) {
		super(level, mob);
	}

	@Override
	public Path getTruePath(Vec2 gridStart, Vec2 gridGoal) {
		nodes.clear();
		Set<Node> startNodes=new HashSet<Node>(1);
		if (!canWalk(gridStart)){
			return new Path(false);
		}
		if (!canWalk(gridGoal)){
			return new Path(false);
		}

		Node start = createNode(gridStart);
		start.parent=null;
		startNodes.add(start);
		checkedNodes.add(start);
		Set<Node> goals = new HashSet<Node>(1);
		goals.add(createNode(gridGoal));
		return processLayers(startNodes, goals);
	}
	
	@Override
	public Path getAnyTruePathNearby(Vec2 gridStart, Set<Vec2> gridGoals) {
		nodes.clear();
		Set<Node> startNodes=new HashSet<Node>(1);
		if (!canWalk(gridStart)){
			return new Path(false);
		}
		Node start = createNode(gridStart);
		start.parent=null;
		startNodes.add(start);
		checkedNodes.add(start);
		Set<Node> goals = new HashSet<Node>(gridGoals.size()*4);
		for(Vec2 goalPos : gridGoals){
			Node goal= createNode(goalPos);
			addNeighbors(goal);
			goals.addAll(goal.getNeighbors());
		}
		return processLayers(startNodes, goals);
	}

	@Override
	public Path getTruePathNearby(Vec2 gridStart, Vec2 gridGoal) {
		nodes.clear();
		Set<Node> startNodes=new HashSet<Node>(1);
		if (!canWalk(gridStart)){
			return new Path(false);
		}
		Node start = createNode(gridStart);
		start.parent=null;
		startNodes.add(start);
		checkedNodes.add(start);
		Node goal = createNode(gridGoal);
		addNeighbors(goal);
		Set<Node> goals = goal.getNeighbors();
		return processLayers(startNodes, goals);
	}
	
	public Path processLayers(Set<Node> inputNodes, Set<Node> goals){
		for(Node goalNode : goals){
			if(inputNodes.contains(goalNode)){
				return reconstructPath(goalNode);
			}
		}
		Set<Node> layerNodes = new HashSet<Node>(inputNodes.size()*4);
		for(Node node: inputNodes){	
			addNeighbors(node);
			Set<Node> neighbors = node.getNeighbors();
			for(Node neighbor: neighbors){
				if(checkedNodes.add(neighbor)){
					neighbor.parent = node;
					layerNodes.add(neighbor);
				}
			}
		}
		if(layerNodes.isEmpty()){
			return new Path(false);
		}else{
			return processLayers(layerNodes, goals);
		}
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
