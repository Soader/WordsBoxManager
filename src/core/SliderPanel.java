package core;
import java.awt.Color;


import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class SliderPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public int num;
	public static final int SIZE = 30;
	public static final int BUTTON_SIZE = 35;
	public static final int BORDER_SIZE = 3;
	public JLabel[] slots;
	public JLabel nextButton, previousButton;
	private int width;
	
	public SliderPanel(int iconsNumber){
		if(iconsNumber > 0)
			num = iconsNumber;
		else
			num = 0;
		slots = new JLabel[num];
		setLayout(null);
		setOpaque(false);
		
		previousButton = new JLabel();
		previousButton.setBounds(0, 0, BUTTON_SIZE, BUTTON_SIZE);
		previousButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		previousButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/arrow_prev.png"))));
		add(previousButton);
		
		
		for(int i = 0; i < num; i++){
			slots[i] = new JLabel();
			if(i == num/2){
				slots[i].setBorder(BorderFactory.createLineBorder(Color.YELLOW, BORDER_SIZE));
				slots[i].setBounds((SIZE + 6)*i - BORDER_SIZE + BUTTON_SIZE, 0, SIZE+BORDER_SIZE*2, SIZE+BORDER_SIZE*2);
				//slots[i].setBounds(, 0, SIZE+10, SIZE+10);
			}
			else
				slots[i].setBounds((SIZE + 6)*i + BUTTON_SIZE, BORDER_SIZE, SIZE, SIZE);
			
			add(slots[i]);
		}
		
		width = 2*BUTTON_SIZE + (SIZE + 6) * num;
		
		nextButton = new JLabel();
		nextButton.setBounds(width - BUTTON_SIZE, 0, BUTTON_SIZE, BUTTON_SIZE);
		nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nextButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/arrow_next.png"))));
		
		add(nextButton);
		
	}
	
	public int getWidth(){
		return width;
		
	}
}
