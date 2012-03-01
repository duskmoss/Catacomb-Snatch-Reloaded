package com.mojang.mojam.network.packet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.mojang.mojam.MojamComponent;
import com.mojang.mojam.gui.menu.LevelSelectMenu;
import com.mojang.mojam.network.Packet;
import com.mojang.mojam.network.PacketListener;

public class LevelPacket extends Packet {
	
	private byte[] bytes;
	private String name;
	
	public LevelPacket() {
	}

	public LevelPacket(String lvl) {
		File level = new File(lvl);
		name=level.getName();
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(level));
			in.read(bytes);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void read(DataInputStream dis) throws IOException {
		byte[] name = new byte[dis.readInt()];
		dis.readFully(name);
		this.name = new String(name);
		bytes = new byte[dis.readInt()];
		dis.readFully(bytes);

	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		byte[] name = this.name.getBytes();
		dos.writeInt(name.length);
		dos.write(name);
		dos.writeInt(bytes.length);
		dos.write(bytes);

	}
	
	public String getLevel(){
		String path=LevelSelectMenu.levelDirectory.getPath();
		File level = new File(path+name);
		if(level.exists()){
			level.renameTo(new File(path+name+System.currentTimeMillis()));
			level= new File(path+name);
			MojamComponent.levelWasRenamed(name,name+System.currentTimeMillis());
		}
		try {
			level.createNewFile();
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(level));
			out.write(bytes);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return level.getPath();
	}

	@Override
	public void handle(PacketListener packetListener) {
		packetListener.handle(this);
		
	}

}
