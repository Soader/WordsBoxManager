package modules;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import core.Language;
import core.RepeatManager;

public abstract class ModulePanel extends JPanel {

	protected static final String MODULE_DIR = "modules";
	private static ArrayList<ModulePanel> modules = new ArrayList<ModulePanel>();
	protected Language selectedLanguage;
	private static final long serialVersionUID = 1L;
	protected ArrayList<Language> list = new ArrayList<Language>();
	private Slider slider;
	private ContentPanel contentPanel;
	protected CreationPanel creationPanel;
	protected ModulePanel module;
	protected Image bg_img = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(getClass().getResource("/langBG.png"))).getImage();
	protected String listPath;
	protected Color fieldColor = new Color(163, 190, 255);
	

	public ModulePanel(String s) {
		if (s == null)
			return;
		listPath = s;
		module = this;
		modules.add(this);
		

		// loads a list of languages from memory
		try {
			File file = new File(listPath);
			System.out.println("s = [" + s + "]");
			if (file.exists()) {
				FileInputStream in = new FileInputStream(file);
				ObjectInputStream oin = new tools.HackedObjectInputStream(in);
				list = (ArrayList<Language>) oin.readObject();
				in.close();
				oin.close();
			}
		} catch (IOException | ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		
		// calculates words to repeat for every language on the list
		for (Language go : list)
			RepeatManager.initRepeats(go);

		// adds special "fake" language to the end of the list, that enables
		// creating new languages
		list.add(new Language(true));
		
		selectedLanguage = list.get(0);
		

		// creates files for every languages if they don't exist
		for (Language go : list) {
			if (!go.newpanel && !new File(go.path).exists()) {
				boolean success = new File(go.path).mkdirs();
				if (!success) {
					System.out.println("Path creation failed: " + go.path);
					continue;
				}
			}
		}

		setLayout(new BorderLayout());
		setOpaque(false);
	}

	public void addSlider(int cellsNumber) {

		slider = new Slider(cellsNumber);
		slider.update();
		Box buttonBar = Box.createHorizontalBox();
		buttonBar.add(Box.createHorizontalGlue());
		buttonBar.add(slider);
		add(buttonBar, BorderLayout.NORTH);
	}

	public void addContentPanel(ModulePanel.ContentPanel panel) {
		contentPanel = panel;
		contentPanel.setOpaque(false);
		add(BorderLayout.CENTER, contentPanel);
		contentPanel.update();
	}

	public void addCreationPanel(CreationPanel panel) {
		creationPanel = panel;
		if (contentPanel != null) {
			creationPanel.setPreferredSize(contentPanel.getPreferredSize());
		}
	}

	public ArrayList<Language> getList() {
		return list;
	}

	public Language getSelectedLanguage() {
		return selectedLanguage;
	}

	public ContentPanel getContentPanel() {
		return contentPanel;
	}

	public Slider getSlider() {
		return slider;
	}

	public void setSelectedLanguage(Language language) {
		selectedLanguage = language;
		slider.update();
		contentPanel.update();
	}

	public Image getBackgroundImage() {
		return bg_img;
	}
	
	protected ArrayList<ModulePanel> getModulesList(){
		return modules;
	}

	// ------------- inner classes ----------------//

	public abstract class ContentPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public ContentPanel() {

		}

		public abstract void update();

		@Override
		protected void paintComponent(Graphics g) { // ?????????
			super.paintComponent(g);
			g.drawImage(bg_img, 0, 0, getWidth(), getHeight(), null);
		}

	}

	protected class Slider extends JPanel {

		public static final int ICON_SIZE = 30;
		public static final int BUTTON_SIZE = 35;
		public static final int BORDER_SIZE = 3;
		public int num;
		public JLabel[] slots;
		public JLabel nextButton, previousButton;
		private static final long serialVersionUID = 1L;
		private int width;
		private ImageIcon previousIcon = new ImageIcon(Toolkit
				.getDefaultToolkit().getImage(
						getClass().getResource("/arrow_prev.png")));
		private ImageIcon nextIcon = new ImageIcon(Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/arrow_next.png")));

		public Slider(int iconsNumber) {
			if (iconsNumber >= 1)
				num = iconsNumber;
			else
				num = 1;
			slots = new JLabel[num];
			setLayout(null);
			setOpaque(false);

			width = 2 * BUTTON_SIZE + (ICON_SIZE + 6) * num;
			setPreferredSize(new Dimension(width, 40));

			// creates "previous" button for language panel
			previousButton = new JLabel();
			previousButton.setBounds(0, 0, BUTTON_SIZE, BUTTON_SIZE);
			previousButton.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));
			previousButton.setIcon(previousIcon);

			previousButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					JLabel lab = (JLabel) e.getSource();
					if (e.getX() > 0 && e.getY() > 0
							&& e.getX() < lab.getWidth()
							&& e.getY() < lab.getHeight()) {
						if (selectedLanguage == null) {
							System.out.println("Selected Language is null"); // to
																				// do
							return;
						}
						int index = list.indexOf(selectedLanguage);
						if (index - 1 >= 0) {
							selectedLanguage = list.get(index - 1);
							update();
						}
					}

				}
			});
			add(previousButton);

			// creates "next" button for language panel
			nextButton = new JLabel();
			nextButton.setBounds(width - BUTTON_SIZE, 0, BUTTON_SIZE,
					BUTTON_SIZE);
			nextButton
					.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			nextButton.setIcon(nextIcon);

			nextButton.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					JLabel lab = (JLabel) e.getSource();
					if (e.getX() > 0 && e.getY() > 0
							&& e.getX() < lab.getWidth()
							&& e.getY() < lab.getHeight()) {
						if (selectedLanguage == null) {
							System.out.println("Selected Language is null"); // to
																				// do
							return;
						}
						int index = list.indexOf(selectedLanguage);
						if (index + 1 < list.size()) {
							selectedLanguage = list.get(index + 1);
							update();

						}
					}
				}
			});
			add(nextButton);

			for (int i = 0; i < num; i++) {
				slots[i] = new JLabel();
				if (i == num / 2) {
					slots[i].setBorder(BorderFactory.createLineBorder(
							Color.YELLOW, BORDER_SIZE));
					slots[i].setBounds((ICON_SIZE + 6) * i - BORDER_SIZE
							+ BUTTON_SIZE, 0, ICON_SIZE + BORDER_SIZE * 2,
							ICON_SIZE + BORDER_SIZE * 2);
				} else
					slots[i].setBounds((ICON_SIZE + 6) * i + BUTTON_SIZE,
							BORDER_SIZE, ICON_SIZE, ICON_SIZE);

				add(slots[i]);
			}
		}

		protected void update() {
			int index = list.indexOf(selectedLanguage);

			for (int i = 0; i < num; i++) {
				if (i - num / 2 + index >= 0
						&& i - num / 2 + index < list.size()) {
					slider.slots[i]
							.setIcon(list.get(i - num / 2 + index).icon_min);
				} else
					slots[i].setIcon(null);
			}

			if (selectedLanguage.newpanel) {
				if (contentPanel != null) {
					module.remove(contentPanel);
					module.repaint();
					module.revalidate();
				}
				creationPanel.reset();
				module.add(BorderLayout.CENTER, creationPanel);
				module.repaint();
				module.revalidate();

			} else {
				if (creationPanel != null)
					module.remove(creationPanel);
				if (contentPanel != null) {
					module.add(BorderLayout.CENTER, contentPanel);
					contentPanel.update();
				}

			}
		}

		public int getWidth() {
			return width;

		}
		
		protected ArrayList<Language> getList(){
			return list;
		}

	}

	protected abstract class CreationPanel extends JPanel {

		public CreationPanel() {

		}

		protected abstract void reset();

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bg_img, 0, 0, getWidth(), getHeight(), null);
		}
	}

}
