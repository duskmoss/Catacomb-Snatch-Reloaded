package com.mojang.mojam.level.pathing;

import java.util.HashSet;
import java.util.Set;

import com.mojang.mojam.math.Vec2;

public class Node implements Comparable<Node> {
	public Vec2 pos;
	public Set<Node> neighbors = new HashSet<Node>(4);

	public Node(Vec2 pos) {
		this.pos = pos.clone().floor();
	}

	public void addNeighbor(Node n) {
		neighbors.add(n);
	}

	public Set<Node> getNeighbors() {
		return neighbors;
	}

	public int compare(Node n1, Node n2) {
		if (n1.priority == n2.priority)
			return 0;
		return n1.priority < n2.priority ? -1 : 1;
	}

	@Override
	public int compareTo(Node o) {
		return compare(this, o);
	}
	
	
	public boolean equals(Node other){
		return other.pos.equals(pos);
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof Node){
			Node other = (Node) o;
			return equals(other);
		}else{
			return false;
		}
	}
	
	public String toString(){
		return (int) pos.x + "_" + (int) pos.y;
	}
	
	public static String getHash(Vec2 pos) {
		return (int) pos.x + "_" + (int) pos.y;
	}

	public boolean visited = false;
	public double pathDistance = 0;
	public double heuristicDistance = Double.MAX_VALUE;
	public Node parent = null;
	public double priority = 0;

}
