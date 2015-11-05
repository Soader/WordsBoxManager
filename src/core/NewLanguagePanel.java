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

import tools.Resources;


public class NewLanguagePanel extends JPanel{
	private Image eng_img = Resources.getInstance().getIcon("/langBG.png").getImage();
	private ImageIcon icon = Resources.getInstance().getIcon("/englishbg.png");
	private JLabel lbl_name = new JLabel("Language: ");
	private JLabel lbl_icon = new JLabel("Icon: ");
	private JLabel lbl_font = new JLabel("BiggerFont: ");
	private JLabel lbl_iconImg = new JLabel();
	private String[] str_icons = {"englishbg", "japanbg"};
	private JComboBox cb = new JComboBox(str_icons);
	private JTextField txtfld_name; 
	private JCheckBox checkbox = new JCheckBox();
	private JButton btn_create = new JButton("Create");
	private Color fieldColor = new Color(163, 190, 255);
	
	public NewLanguagePanel(double width, double height){
		int x = (int) width;
		int y = (int) height;
		int ButX = 70;
		int ButY = 30;
		int logoX = 70;
		int logoY = 70;

		setOpaque(false);
		setPreferredSize(new Dimension(x, y));
		setLayout(null);
		
		lbl_name.setBounds(10, 10, 70, 20);
		add(lbl_name);
		
		txtfld_name = new JTextField(){
			private static final long serialVersionUID = 1L;
			@Override public void setBorder(Border border) {}
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
				icon = Resources.getInstance().getIcon("/" + language + ".png");
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
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(eng_img, 0, 0, getWidth(), getHeight(), null);
	}
	
}
