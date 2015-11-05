package core;


import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import tools.Resources;


public class Language implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name, path, biggerfont;
	private ImageIcon icon, icon_min;
	private boolean newpanel;
	private int repeatsNumber, wordsNumber;
	
	
	
	public Language(String name, String path, ImageIcon icon, boolean biggerfont){
		this.name = name;
		this.path = path;
		this.icon = icon;
		this.repeatsNumber = 0;
		this.wordsNumber = 0;
		scaleIcon();
		if(biggerfont) 
			this.biggerfont = "jap";
		else
			this.biggerfont = "nobigger";
	}

	public boolean isCreator(){
		return newpanel;
	}
	
	public Language(boolean newPanel){
		this.icon = Resources.getInstance().getIcon("/new_icon.png");
		scaleIcon();
		this.newpanel = true;
		
	}
	
	public void scaleIcon(){
		this.icon_min = new ImageIcon(icon.getImage().getScaledInstance(SliderPanel.getIconSize(), SliderPanel.getIconSize(), Image.SCALE_SMOOTH));
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isNewpanel() {
		return newpanel;
	}

	public void setNewpanel(boolean newpanel) {
		this.newpanel = newpanel;
	}

	public int getRepeatsNumber() {
		return repeatsNumber;
	}

	public void setRepeatsNumber(int repeatsNumber) {
		this.repeatsNumber = repeatsNumber;
	}

	public int getWordsNumber() {
		return wordsNumber;
	}

	public void setWordsNumber(int wordsNumber) {
		this.wordsNumber = wordsNumber;
	}

	public String getBiggerfont() {
		return biggerfont;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public ImageIcon getIcon_min() {
		return icon_min;
	}

	public void setIcon_min(ImageIcon icon_min) {
		this.icon_min = icon_min;
	}

	public void setBiggerfont(String biggerfont) {
		this.biggerfont = biggerfont;
	}

	
	
	
	
	
}
