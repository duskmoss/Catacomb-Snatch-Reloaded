package com.mojang.mojam.gui;


import java.util.Map;
import java.util.Stack;
import java.util.Date;
import java.util.TreeMap;

public class ChatStack extends Stack<String> {
	

	private static final long serialVersionUID = -6781192201779876079L;
	public static final int MAX=3;
	
	private Map<String, Long> times = new TreeMap<String, Long>();
	
	@Override
	public String push(String s){
		if(size()==MAX){
			cut();
		}
		super.push(s);
		times.put(s, new Date().getTime());
		return s;
	}
	
	private void cut(){
		String s=remove(0);
		times.remove(s);
		
	}
	
	public void trim(){ 
		if(isEmpty()){
			return;
		}
		String s=get(0);
		long expTime= times.get(s)+5000;
		long curTime= new Date().getTime();
		if(expTime<curTime){
			cut();
		}
		
	}

}
