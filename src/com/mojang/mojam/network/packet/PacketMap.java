package com.mojang.mojam.network.packet;

import java.util.HashMap;
import java.util.Map;


public class PacketMap {
	private static Map<Integer, Class<? extends Packet>> idToClassMap = new HashMap<Integer, Class<? extends Packet>>();
	private static Map<Class<? extends Packet>, Integer> classToIdMap = new HashMap<Class<? extends Packet>, Integer>();

	static void map(int id, Class<? extends Packet> clazz) {
		if (idToClassMap.containsKey(id))
			throw new IllegalArgumentException("Duplicate packet id:" + id);
		if (classToIdMap.containsKey(clazz))
			throw new IllegalArgumentException("Duplicate packet class:"
					+ clazz);
		idToClassMap.put(id, clazz);
		classToIdMap.put(clazz, id);
	}

	static {
		map(10, StartGamePacket.class);
		map(11, TurnPacket.class);

		
		map(101, PauseCommand.class);
		map(102, EndGameCommand.class);
		map(103, ChatCommand.class);
		map(104, SyncCommand.class);
		map(105, PickupCommand.class);
	}
	
	static int get( Class<? extends Packet> clazz){
		return classToIdMap.get(clazz);
	}
	static Class<? extends Packet> get(int id){
		return idToClassMap.get(id);
	}
}
