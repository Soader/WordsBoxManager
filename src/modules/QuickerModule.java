package modules;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import core.Language;

public class QuickerModule extends ModulePanel {

	private static final int SLIDER_SIZE = 5;
	private static final String QUICKER_DIR = "quickers";
	private static final String LIST_PATH = MODULE_DIR + "/" + QUICKER_DIR
			+ "/quickList.ser";
	private static final Rectangle QUICKER_MODULE_RECTANGLE = new Rectangle(0,
			0, 270, 240);
	private static final long serialVersionUID = 1L;

	public QuickerModule() {
		super(LIST_PATH);
		addContentPanel(new QuickerContentPanel());
		addCreationPanel(new NewQuickerPanel(QUICKER_MODULE_RECTANGLE.width,
				QUICKER_MODULE_RECTANGLE.height));
		addSlider(SLIDER_SIZE);
	}

	private class QuickerContentPanel extends ContentPanel {

		private static final long serialVersionUID = 1L;
		private int width = QUICKER_MODULE_RECTANGLE.width;
		private int height = QUICKER_MODULE_RECTANGLE.height;
		private int logoWidth = 70;
		private int logoHeight = 70;
		private int butWidth = 70;
		private int butHeight = 30;
		private JLabel logo = new JLabel();
		private JLabel langName = new JLabel();
		private JLabel lblWords = new JLabel(" Words: ");
		private JLabel lblWordsNum = new JLabel();
		private JLabel lblMove = new JLabel(" Move to: ");
		private JButton runBut = new JButton("Run");
		private JLabel lblDelete = new JLabel();
		private JComboBox<Language> cbLanguages;
		private DefaultComboBoxModel<Language> model = new DefaultComboBoxModel<Language>();
		private JButton moveBut = new JButton("Move");
		private ModulePanel languageModule = null;

		public QuickerContentPanel() {
			// creates label displaying language logo (icon)
			logo.setBounds(width / 2 - logoWidth / 2, 30, logoWidth, logoHeight);

			// creates label displaying language name
			langName.setBounds(width / 2 - (width / 2 - 10), 5, width - 20, 20);
			langName.setHorizontalAlignment(SwingConstants.CENTER);

			// creates labels displaying language repeats (number of words to
			// repeat)
			lblWords.setBounds(10, 130, 70, 20);
			lblWordsNum.setBounds(75, 130, 70, 20);

			for (ModulePanel mod : getModulesList()) {
				if (mod.getClass().equals(LanguageModule.class)) {
					languageModule = mod;
				}
			}

			if (languageModule != null) {
				languageListComboBoxSetup();
			}

			buttonsSetup();

			setLayout(null);
			setPreferredSize(new Dimension(QUICKER_MODULE_RECTANGLE.width,
					QUICKER_MODULE_RECTANGLE.height));
			add(logo);
			add(lblDelete);
			add(langName);
			add(lblWords);
			add(lblWordsNum);
			add(runBut);
		}

		 private JButton getButtonSubComponent(Container container) {
		      if (container instanceof JButton) {
		         return (JButton) container;
		      } else {
		         Component[] components = container.getComponents();
		         for (Component component : components) {
		            if (component instanceof Container) {
		               return getButtonSubComponent((Container)component);
		            }
		         }
		      }
		      return null;
		   }

		private void languageListComboBoxSetup() {

			cbLanguages = new JComboBox<Language>();
			for (Language lang : languageModule.getList()) {
				if (!lang.newpanel) {
					model.addElement(lang);

				}
			}
			cbLanguages.setModel(model);
			JButton arrowBut;
			arrowBut = getButtonSubComponent(cbLanguages);
			
			if(arrowBut != null){
				arrowBut.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						model.removeAllElements();
						for (Language lang : languageModule.getList()) {
							if (!lang.newpanel) {
								model.addElement(lang);
							}
						}
						cbLanguages.setModel(model);
						repaint();revalidate();
						
					}
				});
			}
			
		/*	cbLanguages.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					model.removeAllElements();
					for (Language lang : languageModule.getList()) {
						if (!lang.newpanel) {
							model.addElement(lang);
						}
					}
					cbLanguages.setModel(model);

				}
			});*/

			cbLanguages.setBounds(75, 150, 110, 20);
			cbLanguages.setBackground(fieldColor);
			add(cbLanguages);

			moveBut.setBounds(195, 150, 65, 20);
			moveBut.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			add(moveBut);

			lblMove.setBounds(10, 150, 70, 20);
			add(lblMove);
		}

		private void buttonsSetup() {
			// creates "run" button starting the WordsBox application

			runBut.setBounds(width / 2 - butWidth / 2, height - butHeight * 2,
					butWidth, butHeight);
			runBut.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if (currentLanguage.newpanel)
							return;
						File file = new File("WordsBox.jar");
						if (file.exists()) {
							ProcessBuilder pb = new ProcessBuilder("java",
									"-jar", "WordsBox.jar",
									currentLanguage.name,
									currentLanguage.path, "quicker");
							pb.start();
						} else {
							System.out.println("Can't find " + file);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			});

			// creates "delete quicker" button
			lblDelete.setIcon(new ImageIcon(Toolkit.getDefaultToolkit()
					.getImage(getClass().getResource("/delete_icon.png"))));
			lblDelete.setBounds(140, 220, 15, 15);
			lblDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblDelete.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					JLabel lab = (JLabel) e.getSource();
					if (e.getX() > 0 && e.getY() > 0
							&& e.getX() < lab.getWidth()
							&& e.getY() < lab.getHeight()) {
						int user = JOptionPane.showConfirmDialog(null,
								"Are you sure you want to delete \n\n"
										+ currentLanguage.name,
								"Delete quicker", 0);
						if (user == 0) {
							langListDelete(currentLanguage);
							currentLanguage = list.get(0);
							getSlider().update();
							getContentPanel().update();
						}
					}
				}
			});

		}

		private void langListDelete(Language lang) {
			Language temp = null;

			Iterator<Language> i = list.iterator();
			while (i.hasNext()) {
				Language s = i.next();
				if (s.newpanel) {
					temp = s;
					i.remove();
				}
			}

			list.remove(lang);

			// TODO delete path

			FileOutputStream fo;
			ObjectOutputStream ob;
			try {
				fo = new FileOutputStream(LIST_PATH);
				ob = new ObjectOutputStream(fo);
				ob.writeObject(list);
				fo.close();
				ob.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (temp != null)
				list.add(temp);
			for (Language go : list)
				if (!go.newpanel && !new File(go.path).exists()) {
					boolean success = new File(go.path).mkdirs();
					if (!success) {
						System.out.println("Path creation failed: " + go.path);
						continue;
					}
				}
		}

		@Override
		public void update() {
			langName.setText(currentLanguage.name);
			logo.setIcon(currentLanguage.icon);
			lblWordsNum.setText(currentLanguage.wordsNumber + "");
			repaint();
			revalidate();
		}
	}

	private class NewQuickerPanel extends CreationPanel {

		private static final long serialVersionUID = 1L;
		public ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/englishbg.png")));
		public JLabel lbl_name = new JLabel("Language: ");
		public JLabel lbl_icon = new JLabel("Icon: ");
		public JLabel lbl_font = new JLabel("BiggerFont: ");
		public JLabel lbl_iconImg = new JLabel();
		private String[] str_icons = { "englishbg", "japanbg" };
		public JComboBox cb = new JComboBox(str_icons);
		public JTextField txtfld_name;
		public JCheckBox checkbox = new JCheckBox();
		public JButton btn_create = new JButton("Create");
		private Color fieldColor = new Color(163, 190, 255);

		// START constructor
		public NewQuickerPanel(double width, double height) {
			int x = (int) width;
			int y = (int) height;
			int ButX = 70;
			int ButY = 30;
			int logoX = 70;
			int logoY = 70;

			if (true) {
				setOpaque(false);
				setPreferredSize(new Dimension(x, y));
				setLayout(null);

				lbl_name.setBounds(10, 10, 70, 20);
				add(lbl_name);

				txtfld_name = new JTextField() {
					@Override
					public void setBorder(Border border) {

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
						JComboBox combo = (JComboBox) e.getSource();
						String language = (String) combo.getSelectedItem();
						icon = new ImageIcon(Toolkit.getDefaultToolkit()
								.getImage(
										getClass().getResource(
												"/" + language + ".png")));
						lbl_iconImg.setIcon(icon);
					}
				});
				add(cb);

				lbl_iconImg.setBounds(x / 2 - logoX / 2, 60, logoX, logoY);
				lbl_iconImg.setIcon(icon);
				add(lbl_iconImg);

				lbl_font.setBounds(10, 135, 70, 20);
				add(lbl_font);

				checkbox.setBounds(95, 135, 20, 20);
				checkbox.setOpaque(false);
				add(checkbox);

				btn_create.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (txtfld_name.getText().isEmpty())
							return;
						String path = txtfld_name.getText().substring(0,
								Math.min(3, txtfld_name.getText().length()));
						path = path.toLowerCase();
						File file = new File(MODULE_DIR + "/" + QUICKER_DIR
								+ "/" + path);
						int i = 0;
						while (file.exists()) {
							file = new File(MODULE_DIR + "/" + QUICKER_DIR
									+ "/" + path + i);
							i++;
						}

						langListSave(txtfld_name.getText(), file.getPath()
								+ "/", icon, checkbox.isSelected());

						module.remove(creationPanel);
						if (list.size() > 0)
							currentLanguage = list.get(list.size() - 2);
						else
							return;

						if (module.getContentPanel() != null) {
							module.add(getContentPanel());
							getSlider().update();
						} else {
							System.out.println("Content panel is null");
						}
						module.repaint();
						module.revalidate();

					}
				});
				btn_create
						.setBounds(x / 2 - ButX / 2, y - ButY * 2, ButX, ButY);
				add(btn_create);
			}

		}

		// END constructor

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
		 *            field than normal. Useful for languages like Japanese,
		 *            Chinese
		 */
		private void langListSave(String name, String path, ImageIcon icon,
				boolean biggerfont) {
			Language temp = null;

			Iterator<Language> i = list.iterator();
			while (i.hasNext()) {
				Language s = i.next();
				if (s.newpanel) {
					temp = s;
					i.remove();
				}
			}

			list.add(new Language(name, path, icon, biggerfont));

			File f = new File(MODULE_DIR + "/" + QUICKER_DIR + "/");
			if (!f.exists()) {
				f.mkdirs();
			}

			FileOutputStream fo;
			ObjectOutputStream ob;
			try {
				fo = new FileOutputStream(LIST_PATH);
				ob = new ObjectOutputStream(fo);
				ob.writeObject(list);
				fo.close();
				ob.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (temp != null)
				list.add(temp);
			for (Language go : list)
				if (!go.newpanel && !new File(go.path).exists()) {
					boolean success = new File(go.path).mkdirs();
					if (!success) {
						System.out.println("Path creation failed: " + go.path);
						continue;
					}
				}
		}

		@Override
		protected void reset() {
			creationPanel = new NewQuickerPanel(QUICKER_MODULE_RECTANGLE.width,
					QUICKER_MODULE_RECTANGLE.height);

		}

	}

}
