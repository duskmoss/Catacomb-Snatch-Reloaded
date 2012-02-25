package com.mojang.mojam.network.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.mojang.mojam.entity.building.Building;
import com.mojang.mojam.network.CommandListener;

public class PickupCommand extends NetworkCommand {
	byte[] bytes;

	public PickupCommand() {
		
	}
	public PickupCommand(Building b){
		try {
			ByteArrayOutputStream bytesout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bytesout);
			out.writeObject(b);
			out.flush();
			out.close();
			bytesout.flush();
			bytes=bytesout.toByteArray();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public void read(DataInputStream dis) throws IOException {
		int length=dis.readInt();
		System.out.println(length);
		bytes=new byte[length];
		dis.readFully(bytes);
	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		dos.writeInt(bytes.length);
		dos.write(bytes);

	}
	
	@Override
	public void handle(int i, CommandListener commandListener) {
		commandListener.handle(i, this);
		
	}
	public Building getBuilding() {
		Building b = null;
		try {
			ByteArrayInputStream bytesin = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(bytesin);
			b = (Building) in.readObject();
			in.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

}
