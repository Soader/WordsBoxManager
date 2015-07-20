package core;


import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Language implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String name, path, biggerfont;
	public ImageIcon icon, icon_min;
	private boolean newpanel;
	public int repeatsNumber, wordsNumber;
	
	
	
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

	public boolean isNewPanel(){
		return newpanel;
	}
	
	public Language(boolean newPanel){
		this.icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/new_icon.png")));
		scaleIcon();
		this.newpanel = true;
		
	}
	
	public void scaleIcon(){
		this.icon_min = new ImageIcon(icon.getImage().getScaledInstance(SliderPanel.SIZE, SliderPanel.SIZE, Image.SCALE_SMOOTH));
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
