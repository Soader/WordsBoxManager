package core;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.SwingConstants;

import java.awt.Dimension;

import modules.LanguageModule;
import modules.QuickerModule;
import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.UIManager;
import javax.swing.border.Border;

import sun.security.action.GetLongAction;

import com.sun.awt.AWTUtilities;

import java.awt.Cursor;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Iterator;

public class ManagerWindow {
	private JFrame frame;
	private JPanel modulesPanel, langModule, quickModule, langPanel,
			quickPanel;
	private int MouseX, MouseY;
	private Image eng_img, jap_img;
	public static final long DIVIDER = 86400000;
	public ArrayList<Language> langList = new ArrayList<Language>();
	private ArrayList<Language> quickList = new ArrayList<Language>();
	public Language activeLang, activeQuicker;
	private JLabel logo, langName, lblDelete, lblRepeatsNum;
	private NewLanguagePanel newLangPanel;
	private SliderPanel slider;
	private int panel_width = 170; // ?
	private Rectangle modulesPanelRectangle = new Rectangle(0, 130, 500, 300);
	private Rectangle frameRectangle = new Rectangle(100, 100, 850, 480);
	private Rectangle langModuleRectangle = new Rectangle(0, 0, 170, 240);
	private Rectangle quickModuleRectangle = new Rectangle(0, 0, 270, 240);
	public static final boolean DEBUG = true;
	private static final boolean ACTIVITY_PANEL = false;
	private static final int LANG_SLIDER_NUM = 3;
	private static final int QUICK_SLIDER_NUM = 5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Hello in new branch");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerWindow window = new ManagerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManagerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setBounds(frameRectangle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setContentPane(new TranslucentPane());
		frame.getContentPane().setLayout(null);

		// creates activity panel showing last activity of other users, needs DB
		// connection
		if (ACTIVITY_PANEL) {
			ActivityPanel activityPanel = new ActivityPanel(300, 155);
			frame.getContentPane().add(activityPanel);
			activityPanel.loadNotes();
		}

		// creates main panel for sub-panels like language panel, activity panel
		modulesPanel = new JPanel();
		modulesPanel.setBounds(modulesPanelRectangle);
		modulesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		modulesPanel.setOpaque(false);
		frame.getContentPane().add(modulesPanel);

		// creates container for language module
		/*
		 * langModule = new JPanel(); langModule.setBounds(langModuleRectangle);
		 * langModule.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		 * langModule.setOpaque(false);
		 */
		// modulesPanel.add(langModule);



		// creates panel for creating new language
		/*
		 * newLangPanel = new
		 * NewLanguagePanel(langModule.getBounds().getWidth(),
		 * langModule.getBounds().getHeight()); addLanguageSetup();
		 */

		// creates "next" button for language panel
		/*
		 * JLabel lblNext = new JLabel(); lblNext.setBounds(modulesPanel.getX()
		 * + modulesPanel.getWidth() / 2 + panel_width / 2 - 20, 120, 35, 35);
		 * lblNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		 * lblNext.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		 * getClass().getResource("/arrow_next.png"))));
		 * lblNext.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseReleased(MouseEvent e) { JLabel lab =
		 * (JLabel) e.getSource(); if (e.getX() > 0 && e.getY() > 0 && e.getX()
		 * < lab.getWidth() && e.getY() < lab.getHeight()) { int index =
		 * langList.indexOf(activeLang); if (index + 1 < langList.size())
		 * activeLang = langList.get(index + 1); if (activeLang != null) {
		 * panelSetup(activeLang); sliderUpdate(); } }
		 * 
		 * } }); frame.getContentPane().add(lblNext);
		 * 
		 * // creates "previous" button for language panel JLabel lblPrevious =
		 * new JLabel(); lblPrevious.setBounds(modulesPanel.getX() +
		 * modulesPanel.getWidth() / 2 - panel_width / 2 - 15, 120, 35, 35);
		 * lblPrevious
		 * .setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		 * lblPrevious.setIcon(new
		 * ImageIcon(Toolkit.getDefaultToolkit().getImage(
		 * getClass().getResource("/arrow_prev.png"))));
		 * lblPrevious.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseReleased(MouseEvent e) { JLabel lab =
		 * (JLabel) e.getSource(); if (e.getX() > 0 && e.getY() > 0 && e.getX()
		 * < lab.getWidth() && e.getY() < lab.getHeight()) { int index =
		 * langList.indexOf(activeLang); if (index - 1 >= 0) activeLang =
		 * langList.get(index - 1);
		 * 
		 * if (activeLang != null) { panelSetup(activeLang); sliderUpdate(); } }
		 * 
		 * } }); frame.getContentPane().add(lblPrevious);
		 */
		decorateInterface();

		createLanguageModule();

		createQuickerModule();

		// sets the first language from list as actually selected language in
		// the language panel
		/*
		 * if (langList.size() > 0) activeLang = langList.get(0);
		 */

		// initialize language panel if language number (excluding the "fake"
		// one) is higher than 0
		/*
		 * if (activeLang != null && !activeLang.newpanel) {
		 * languagePanelInitialize(); } else { sliderSetup();
		 * langModule.add(newLangPanel); }
		 */

	

	
	}

	/**
	 * Initializes language panel
	 */
	private void languagePanelInitialize() {
		int x = langModule.getBounds().width;
		int y = langModule.getBounds().height;
		int ButX = 70;
		int ButY = 30;
		int logoX = 70;
		int logoY = 70;

		// loads background image
		eng_img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/langBG.png"))).getImage();

		// creates language panel
		langPanel = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(eng_img, 0, 0, getWidth(), getHeight(), null);
			}

		};
		langPanel.setOpaque(false);
		langPanel.setPreferredSize(new Dimension(x, y));
		langPanel.setLayout(null);

		// creates "delete language" button
		lblDelete = new JLabel();
		lblDelete.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/delete_icon.png"))));
		lblDelete.setBounds(140, 220, 15, 15);
		lblDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDelete.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				JLabel lab = (JLabel) e.getSource();
				if (e.getX() > 0 && e.getY() > 0 && e.getX() < lab.getWidth()
						&& e.getY() < lab.getHeight()) {

					langListDelete(activeLang);
					activeLang = langList.get(0);
					sliderUpdate();
					panelSetup(activeLang);

				}

			}
		});
		langPanel.add(lblDelete);

		// creates label displaying language logo (icon)
		logo = new JLabel();
		logo.setBounds(x / 2 - logoX / 2, 30, logoX, logoY);
		langPanel.add(logo);

		// creates label displaying language name
		langName = new JLabel();
		langName.setBounds(x / 2 - (x / 2 - 10), 5, x - 20, 20);
		langName.setHorizontalAlignment(SwingConstants.CENTER);
		langPanel.add(langName);

		// creates labels displaying language repeats (number of words to
		// repeat)
		JLabel lblRepeats = new JLabel("Repeats: ");
		lblRepeats.setBounds(10, 130, 70, 20);
		langPanel.add(lblRepeats);

		lblRepeatsNum = new JLabel();
		lblRepeatsNum.setBounds(85, 130, 70, 20);
		langPanel.add(lblRepeatsNum);

		// creates "run" button starting the WordsBox application
		JButton runBut = new JButton("Run");
		runBut.setBounds(x / 2 - ButX / 2, y - ButY * 2, ButX, ButY);
		runBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (activeLang.newpanel)
						return;
					File file = new File("WordsBox.jar");
					if (file.exists()) {
						ProcessBuilder pb = new ProcessBuilder("java", "-jar",
								"WordsBox.jar", activeLang.name,
								activeLang.path, activeLang.biggerfont);
						pb.start();
					} else {
						System.out.println("Can't find " + file);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		langPanel.add(runBut);

		langModule.add(langPanel);

		panelSetup(activeLang);
		sliderSetup();

		// creates files for every languages if they don't exist
		for (Language go : langList) {
			if (!go.newpanel && !new File(go.path).exists()) {
				boolean success = new File(go.path).mkdirs();
				if (!success) {
					System.out.println("Path creation failed: " + go.path);
					continue;
				}
			}
		}

	}

	private void quickPanelInitialize() {
		// loads background image
		eng_img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/langBG.png"))).getImage();

		// creates quicker panel
		quickPanel = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(eng_img, 0, 0, getWidth(), getHeight(), null);
			}

		};
		quickPanel.setOpaque(false);
		quickPanel.setPreferredSize(new Dimension(quickModuleRectangle.width,
				quickModuleRectangle.height));
		quickPanel.setLayout(null);

		if (!quickList.isEmpty())
			activeQuicker = quickList.get(0);

	}

	private void quickUpdate() {
		if (activeQuicker == null)
			return;
		if (activeQuicker.newpanel) {
			quickModule.add(new NewLanguagePanel(quickModuleRectangle.width,
					quickModuleRectangle.height));
		} else {
			quickModule.add(quickPanel);
		}
	}

	/**
	 * Adds new language to the list and saves the list to the memory.
	 * 
	 * @param name
	 *            of the language
	 * @param path
	 *            to the language
	 * @param icon
	 *            image of the language
	 * @param biggerfont
	 *            true if the language has to have bigger font in the answer
	 *            field than normal. Useful for languages like Japanese, Chinese
	 */
	private void langListSave(String name, String path, ImageIcon icon,
			boolean biggerfont) {
		Language temp = null;

		Iterator<Language> i = langList.iterator();
		while (i.hasNext()) {
			Language s = i.next();
			if (s.newpanel) {
				temp = s;
				i.remove();
			}
		}

		langList.add(new Language(name, path, icon, biggerfont));

		FileOutputStream fo;
		ObjectOutputStream ob;
		try {
			fo = new FileOutputStream("langList.ser");
			ob = new ObjectOutputStream(fo);
			ob.writeObject(langList);
			fo.close();
			ob.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (temp != null)
			langList.add(temp);
		for (Language go : langList)
			if (!go.newpanel && !new File(go.path).exists()) {
				boolean success = new File(go.path).mkdirs();
				if (!success) {
					System.out.println("Path creation failed: " + go.path);
					continue;
				}
			}
	}

	/**
	 * Removes language from the list and saves the list
	 * 
	 * @param language
	 *            to remove
	 */
	private void langListDelete(Language lang) {
		Language temp = null;

		Iterator<Language> i = langList.iterator();
		while (i.hasNext()) {
			Language s = i.next();
			if (s.newpanel) {
				temp = s;
				i.remove();
			}
		}

		langList.remove(lang);

		// TODO delete path

		FileOutputStream fo;
		ObjectOutputStream ob;
		try {
			fo = new FileOutputStream("langList.ser");
			ob = new ObjectOutputStream(fo);
			ob.writeObject(langList);
			fo.close();
			ob.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (temp != null)
			langList.add(temp);
		for (Language go : langList)
			if (!go.newpanel && !new File(go.path).exists()) {
				boolean success = new File(go.path).mkdirs();
				if (!success) {
					System.out.println("Path creation failed: " + go.path);
					continue;
				}
			}
	}

	/**
	 * Refreshes language panel, adjusts the content of the panel to the
	 * language parameter
	 * 
	 * @param language
	 *            to which the panel has to be adjusted
	 */
	private void panelSetup(Language lang) {
		if (lang.newpanel) {

			langModule.removeAll();
			newLangPanel = new NewLanguagePanel(langModule.getBounds()
					.getWidth(), langModule.getBounds().getHeight());
			addLanguageSetup();
			langModule.add(newLangPanel);

			langModule.repaint();
			langModule.revalidate();
		} else {
			langModule.removeAll();
			langModule.add(langPanel);

			langModule.repaint();
			langModule.revalidate();

			logo.setIcon(lang.icon);
			langName.setText(lang.name);
			lblRepeatsNum.setText(lang.repeatsNumber + "");
		}

	}

	/**
	 * Setups "add language" panel. Specifies how the button works.
	 */
	private void addLanguageSetup() {
		newLangPanel.btn_create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (newLangPanel.txtfld_name.getText().isEmpty())
					return;
				String path = newLangPanel.txtfld_name.getText()
						.substring(
								0,
								Math.min(3, newLangPanel.txtfld_name.getText()
										.length()));
				path = path.toLowerCase();
				File file = new File("modules/" + path);
				int i = 0;
				while (file.exists()) {
					file = new File("modules/" + path + i);
					i++;
				}

				langListSave(newLangPanel.txtfld_name.getText(), file.getPath()
						+ "/", newLangPanel.icon,
						newLangPanel.checkbox.isSelected());

				langModule.remove(newLangPanel);
				if (langList.size() > 0)
					activeLang = langList.get(langList.size() - 2);
				else
					return;

				if (langPanel != null) {
					langModule.add(langPanel);
					panelSetup(activeLang);
					sliderUpdate();
				} else {
					languagePanelInitialize();
				}
				langModule.repaint();
				langModule.revalidate();

			}
		});
	}

	/**
	 * Creates and setups language slider
	 */
	private void sliderSetup() {

		slider = new SliderPanel(LANG_SLIDER_NUM);
		slider.setBounds(modulesPanel.getX() + langModule.getWidth() / 2
				- slider.getWidth() / 2, 120, slider.getWidth(), 60);

		frame.getContentPane().add(slider, 1);
		for (Language go : langList)
			go.scaleIcon();
		sliderUpdate();

	}

	/**
	 * Updates language slider
	 */
	private void sliderUpdate() {
		int index;
		index = langList.indexOf(activeLang);

		for (int i = 0; i < slider.num; i++) {
			if (i - slider.num / 2 + index >= 0
					&& i - slider.num / 2 + index < langList.size()) {
				slider.slots[i].setIcon(langList
						.get(i - slider.num / 2 + index).icon_min);
			} else
				slider.slots[i].setIcon(null);
		}

	}

	/**
	 * Creates interface buttons
	 */
	private void decorateInterface() {

		// creates minimize button (main frame)
		JLabel minimize = new JLabel("");
		minimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JLabel lab = (JLabel) e.getSource();
				if (e.getX() > 0 && e.getY() > 0 && e.getX() < lab.getWidth()
						&& e.getY() < lab.getHeight())
					frame.setState(Frame.ICONIFIED);
			}
		});
		minimize.setBounds(frameRectangle.width - 60, 3, 20, 14);
		frame.getContentPane().add(minimize);

		// creates exit button (main frame)
		JLabel exit = new JLabel("");
		exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JLabel lab = (JLabel) e.getSource();
				if (e.getX() > 0 && e.getY() > 0 && e.getX() < lab.getWidth()
						&& e.getY() < lab.getHeight())
					System.exit(0);
			}
		});
		exit.setBounds(frameRectangle.width - 30, 3, 20, 14);
		frame.getContentPane().add(exit);

		// creates invisible label for dragging the main frame
		JLabel dragLabel = new JLabel("");
		dragLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MouseX = e.getX();
				MouseY = e.getY();
			}
		});
		dragLabel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				frame.setLocation(e.getXOnScreen() - MouseX, e.getYOnScreen()
						- MouseY);
			}
		});
		dragLabel.setBounds(0, 0, frameRectangle.width - 70, 13);
		frame.getContentPane().add(dragLabel);
		
		// creates label for displaying background image
		JLabel backgroundLabel = new JLabel("");
		backgroundLabel
				.setIcon(new ImageIcon(
						//"C:\\Users\\Pawel\\Documents\\Grafika\\WordsBox\\BGTrans2.png"));
						"C:\\Users\\pja\\Documents\\tlo.png"));

		backgroundLabel.setBounds(0, 0, frameRectangle.width,
				frameRectangle.height);
		frame.getContentPane().add(backgroundLabel);
	}

	/**
	 * Creates language module
	 */
	private void createLanguageModule() {
		LanguageModule l = new LanguageModule(langList);
		modulesPanel.add(l);
	}
	
	/**
	 * Creates quickers module
	 */
	private void createQuickerModule() {
		
		// creates container for "quickers" module
		
		
		
		// loads a list of "quickers" from memory
		try {
			File file = new File("quickList.ser");
			if (file.exists()) {
				FileInputStream in = new FileInputStream(file);
				ObjectInputStream oin = new ObjectInputStream(in);
				quickList = (ArrayList<Language>) oin.readObject();
				in.close();
				oin.close();
			}
		} catch (IOException | ClassNotFoundException e2) {
			e2.printStackTrace();
		}

		quickList.add(new Language(true));

		
		modulesPanel.add(new QuickerModule(quickList));
		//quickPanelInitialize();
		//quickUpdate();
	}

	
	/**
	 * Class used for creating translucent frame.
	 * 
	 * @author Pawel
	 * 
	 */
	public class TranslucentPane extends JPanel {

		private static final long serialVersionUID = 1L;

		public TranslucentPane() {
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setComposite(AlphaComposite.SrcOver.derive(1));
			g2d.setColor(getBackground());
			g2d.fillRect(0, 0, getWidth(), getHeight());

		}

	}

}
