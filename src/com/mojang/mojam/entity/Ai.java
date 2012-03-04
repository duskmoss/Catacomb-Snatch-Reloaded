package com.mojang.mojam.entity;

public interface Ai {
	
	public static final int ASTAR_TYPE = 1001;
	public static final int BREADTH_TYPE = 1002;
	public static final int RECURSIVE_TYPE = 1003;
	
	public abstract void Path();

}
