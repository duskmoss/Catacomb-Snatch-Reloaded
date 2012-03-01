package com.mojang.mojam.gui.menu;

import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mojang.mojam.gui.ArrowButton;
import com.mojang.mojam.gui.Button;
import com.mojang.mojam.gui.Font;
import com.mojang.mojam.gui.MenuButton;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Bitmap;
import com.mojang.mojam.screen.Screen;

public class LevelSelectMenu extends GuiMenu {
	
	

	public HashMap<String, String> levels;
	private HashMap<String, Bitmap> levelImages;
	private ArrayList<String> levelList;
	public static File levelDirectory;
	public String currentSelection = "/res/levels/level1.bmp";  
	private Button selectButton, cancelButton;
	public boolean reload;
	private int gameWidth;

	public LevelSelectMenu(int gameWidth, int selectId) {
		super();
		this.gameWidth = gameWidth;
		selectButton = addButton(new MenuButton(selectId, selectId-1000, 100, 350));
		cancelButton = addButton(new MenuButton(GuiMenu.CANCEL_JOIN_ID, 4, 250, 350));
		loadLevels();
		addButton(new SpecialButton(2000, 30, 275, this));
		addButton(new SpecialButton(2001, 30, 300, this));
		addButton(new ArrowButton(KeyEvent.VK_LEFT, 180, 150, this));
		addButton(new ArrowButton(KeyEvent.VK_RIGHT, 450, 150, this));
	}
	public static void makeLevelDirectory(){
		String fileSeperator = System.getProperty("file.separator");
		if(System.getProperty("os.name").equals("Windows")){
			levelDirectory = new File(System.getenv("APPDATA")+fileSeperator+"CatacombSnatchReloaded"+fileSeperator+"levels");

		}else{
			levelDirectory = new File(System.getProperty("user.home")+fileSeperator+".CatacombSnatchReloaded"+fileSeperator+"levels");
		}
		if(!levelDirectory.exists()){
			levelDirectory.mkdirs();
		}
	}

	private void loadLevels(){
		levels = new HashMap<String, String>(1);
		levelImages = new HashMap<String, Bitmap>(1);
		levelList = new ArrayList<String>(1);
		levels.put("/res/levels/level1.bmp", "Original");
		levelImages.put("/res/levels/level1.bmp", Art.load("/res/levels/level1.bmp"));
		levelList.add("/res/levels/level1.bmp");
		File[] files = levelDirectory.listFiles(new BmpFilter());
		for(File file : files){
			String filename = file.getName();
			filename = filename.substring(0, filename.length()-4);
			levels.put(file.getPath(), filename);
			levelImages.put(file.getPath(), Art.extLoad(file.getPath()));
			levelList.add(file.getPath());
		}
		for(int i=0; i<levelList.size();i++){
			String level = levelList.get(i);
			addButton(new FileButton(i, 30, 60+10*i , level, this));
		}
		
	}
	
	public String getSelection(){
		return currentSelection;
	}

	@Override
	public void render(Screen screen) {
		if(reload){
			loadLevels();
			reload=false;
		}
		screen.clear(0);
		Font.draw(screen, "Choose Level:", (gameWidth-Font.getStringWidth("Chose Level:"))/2, 10);
		screen.blit(levelImages.get(currentSelection), 244, 80, 4);
		super.render(screen);
	}
	public void changeSelection(int id) {
		if(id==KeyEvent.VK_RIGHT){
			int index=levelList.indexOf(currentSelection)+1;
			if(index==levelList.size()){
				index=0;
			}
			currentSelection=levelList.get(index);
		}
		if(id==KeyEvent.VK_LEFT){
			int index=levelList.indexOf(currentSelection)-1;
			if(index==-1){
				index=levelList.size()-1;
			}
			currentSelection=levelList.get(index);
			
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(KeyEvent.VK_LEFT);
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			selectButton.postClick();
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelButton.postClick();
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelButton.postClick();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT){
			changeSelection(e.getKeyCode());
		}
			
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	private class FileButton extends Button{
		
		private LevelSelectMenu menu;
		private String level, name;
		
				
		public FileButton(int id, int x, int y, String level, LevelSelectMenu menu) {
			super(id);
			this.name = menu.levels.get(level);
			this.x = x;
			this.y = y;
			this.w = Font.getStringWidth(name);
			this.h = 10;
			this.menu = menu;
			this.level = level;
		}
		@Override
		public void postClick() {
			menu.currentSelection = level; 
		}
		
		@Override
		public void render(Screen screen){
			Font.draw(screen, name, this.x, this.y);
		}
		
	}
	
	private class SpecialButton extends MenuButton{
		
		private LevelSelectMenu menu;

		public SpecialButton(int id, int x, int y, LevelSelectMenu menu) {
			super(id, id-2000, x, y, true);
			this.menu = menu;
		}
		@Override
		public void postClick() {
			if(id==2001){
				try {
					Desktop.getDesktop().open(LevelSelectMenu.levelDirectory);
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			if(id==2000){
				menu.reload = true;
			}
		}
		
	}
	
	private class BmpFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			return (name.endsWith(".bmp")||name.endsWith(".BMP"));
		}

	}

}
