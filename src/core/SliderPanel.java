package core;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.Resources;



public class SliderPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private  int icons;
	private  static final int ICON_SIZE = 30;
	private  static final int BUTTON_SIZE = 35;
	private  static final int BORDER_SIZE = 3;
	private  JLabel[] slots;
	private  JLabel nextButton, previousButton;
	private int width;
	
	public SliderPanel(int iconsNumber){
		if(iconsNumber > 0)
			icons = iconsNumber;
		else
			icons = 0;
		slots = new JLabel[icons];
		setLayout(null);
		setOpaque(false);
		
		previousButton = new JLabel();
		previousButton.setBounds(0, 0, BUTTON_SIZE, BUTTON_SIZE);
		previousButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		previousButton.setIcon(Resources.getInstance().getIcon("/arrow_prev.png"));
		add(previousButton);
		
		
		for(int i = 0; i < icons; i++){
			slots[i] = new JLabel();
			if(i == icons/2){
				slots[i].setBorder(BorderFactory.createLineBorder(Color.YELLOW, BORDER_SIZE));
				slots[i].setBounds((ICON_SIZE + 6)*i - BORDER_SIZE + BUTTON_SIZE, 0, ICON_SIZE+BORDER_SIZE*2, ICON_SIZE+BORDER_SIZE*2);
			}
			else
				slots[i].setBounds((ICON_SIZE + 6)*i + BUTTON_SIZE, BORDER_SIZE, ICON_SIZE, ICON_SIZE);
			
			add(slots[i]);
		}
		
		width = 2*BUTTON_SIZE + (ICON_SIZE + 6) * icons;
		
		nextButton = new JLabel();
		nextButton.setBounds(width - BUTTON_SIZE, 0, BUTTON_SIZE, BUTTON_SIZE);
		nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nextButton.setIcon(Resources.getInstance().getIcon("/arrow_next.png"));
		add(nextButton);
		
	}
	
	public int getWidth(){
		return width;
	}

	public static int getIconSize() {
		return ICON_SIZE;
	}

	
}
