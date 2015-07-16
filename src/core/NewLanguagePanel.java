package core;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class NewLanguagePanel extends JPanel{
	private Image eng_img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
			getClass().getResource("/langBG.png"))).getImage();
	public ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
			getClass().getResource("/englishbg.png")));
	public JLabel lbl_name = new JLabel("Language: ");
	public JLabel lbl_icon = new JLabel("Icon: ");
	public JLabel lbl_font = new JLabel("BiggerFont: ");
	public JLabel lbl_iconImg = new JLabel();
	private String[] str_icons = {"englishbg", "japanbg"};
	public JComboBox cb = new JComboBox(str_icons);
	public JTextField txtfld_name; 
	public JCheckBox checkbox = new JCheckBox();
	public JButton btn_create = new JButton("Create");
	private Color fieldColor = new Color(163, 190, 255);
	
	// START constructor
	public NewLanguagePanel(double width, double height){
		int x = (int) width;
		int y = (int) height;
		int ButX = 70;
		int ButY = 30;
		int logoX = 70;
		int logoY = 70;
		
		//eng_img = eng_img.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
		eng_img = Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/langBG.png"));
	//	eng_img = eng_img.getScaledInstance(eng_img.getWidth(null)/2, eng_img.getHeight(null)/2, Image.SCALE_SMOOTH);

		
		if(true){
			setOpaque(false);
			setPreferredSize(new Dimension(x, y));
			setLayout(null);
			
			lbl_name.setBounds(10, 10, 70, 20);
			add(lbl_name);
			
			txtfld_name = new JTextField(){
			    @Override public void setBorder(Border border) {
			        
			    }
			};
			txtfld_name.setBounds(85, 10, 75, 20);
			txtfld_name.setBackground(fieldColor);
			add(txtfld_name);
			
			lbl_icon.setBounds(10, 35, 70, 20);
			add(lbl_icon);
			
			cb.setBounds(50, 35, 110, 20);
			cb.setBackground(fieldColor);
			cb.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox combo = (JComboBox)e.getSource();
					String language = (String)combo.getSelectedItem();
					icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/" + language + ".png")));
					lbl_iconImg.setIcon(icon);
				}
			});
			add(cb);
			
			lbl_iconImg.setBounds(x/2 - logoX/2, 60, logoX, logoY);
			lbl_iconImg.setIcon(icon);
			add(lbl_iconImg);
			
			lbl_font.setBounds(10, 135, 70, 20);
			add(lbl_font);
			
			checkbox.setBounds(95, 135, 20, 20);
			checkbox.setOpaque(false);
			add(checkbox);
	
			btn_create.setBounds(x / 2 - ButX / 2, y - ButY * 2, ButX, ButY);
			add(btn_create);
		}
			
	}
	
	// END constructor
		
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(eng_img, 0, 0, getWidth(), getHeight(), null);
	}
	
}
