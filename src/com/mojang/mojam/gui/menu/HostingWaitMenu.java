package com.mojang.mojam.gui.menu;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import com.mojang.mojam.gui.MenuButton;
import com.mojang.mojam.gui.Font;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class HostingWaitMenu extends GuiMenu {

	int textHeight;
	private ArrayList<String> intIps = new ArrayList<String>(1);
	private String extIp;

	public HostingWaitMenu() {
		super();
		textHeight = 100;
		getIp();
		addButton(new MenuButton(GuiMenu.CANCEL_JOIN_ID, 4, 250, 180));
	}
	private void getIp(){
	
		try {
			for (Enumeration<NetworkInterface> ifaces = 
		               NetworkInterface.getNetworkInterfaces();
		             ifaces.hasMoreElements(); )
		        {
		            NetworkInterface iface = ifaces.nextElement();
		            if(iface.isLoopback()){
		            	continue;
		            }
		            for (Enumeration<InetAddress> addresses =
		                   iface.getInetAddresses();
		                 addresses.hasMoreElements(); )
		            {
		                InetAddress address = addresses.nextElement();
		                if(address instanceof Inet4Address){
		                	intIps.add(address.toString().substring(1));
		                }
		            }
		        }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		try {
			URL whatismyip = new URL("http://automation.whatismyip.com/n09230945.asp");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			extIp = in.readLine();
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}

	@Override
	public void render(Screen screen) {

		screen.clear(0);
		screen.blit(Art.backDrop, 0, 0);
		Font.draw(screen, "Waiting for client to join...", 100, textHeight);
		int height=220;
		for(String intIp: intIps){
			Font.draw(screen, "Internal IP: "+intIp, 100, height);
			height += 10;
		}
		Font.draw(screen, "External IP: "+extIp+" over port 3000", 100, height);
		super.render(screen);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
