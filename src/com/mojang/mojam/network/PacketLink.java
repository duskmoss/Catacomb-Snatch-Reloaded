package com.mojang.mojam.network;

import com.mojang.mojam.network.packet.Packet;

public interface PacketLink {

	public void sendPacket(Packet packet);

	public void tick();

	public void setPacketListener(PacketListener packetListener);

}
