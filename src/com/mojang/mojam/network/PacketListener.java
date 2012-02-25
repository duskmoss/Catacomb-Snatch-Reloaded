package com.mojang.mojam.network;

import com.mojang.mojam.network.packet.Packet;

public interface PacketListener {

	public void handle(Packet packet);

}
