package core;

import java.awt.*;
import javax.swing.*;

import modules.LanguageModule;
import modules.QuickerModule;
import mvp.IMainWindow;

import java.awt.event.*;

public class MainWindow implements IMainWindow{
	public ImageIcon BACKGROUND_IMAGE = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/tlo.png")));
	private JFrame frame;
	private JPanel modulesPanel;
	private int MouseX, MouseY;
	public static final long DIVIDER = 86400000;
	private JButton minimizeBut;
	private Rectangle modulesPanelRectangle = new Rectangle(0, 130, 500, 300);
	private Rectangle frameRectangle = new Rectangle(100, 100, 850, 480);
	private Rectangle minButBounds = new Rectangle(frameRectangle.width - 60, 3, 20, 14);
	private Rectangle exitButBounds = new Rectangle(frameRectangle.width - 30, 3, 20, 14);
	private Rectangle dragLabelBounds = new Rectangle(0, 0, frameRectangle.width - 70, 13);
	private Rectangle backgroundLabelBounds = new Rectangle(0, 0, frameRectangle.width,	frameRectangle.height);
	public static final boolean DEBUG = true;
	private static final boolean ACTIVITY_PANEL = false;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public MainWindow() {
		buildGUI();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void buildGUI() {

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
		minimizeBut = new JButton();
		decorateWindow();
		modulesPanel.add(new LanguageModule());
		modulesPanel.add(new QuickerModule());
		frame.setVisible(true);
	}

	/**
	 * Creates decoration for main window with exit and minimize buttons
	 */
	private void decorateWindow() {

		//addMinimizeButton();
		addExitButton();
		addDragLabel();
		addBackgroundLabel();
	}

	private void addBackgroundLabel() {
		JLabel backgroundLabel = new JLabel("");
		backgroundLabel.setIcon(BACKGROUND_IMAGE);
		backgroundLabel.setBounds(backgroundLabelBounds);
		frame.getContentPane().add(backgroundLabel);
	}

	private void addDragLabel() {
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
		dragLabel.setBounds(dragLabelBounds);
		frame.getContentPane().add(dragLabel);
	}

	private void addExitButton() {
		JLabel exit = new JLabel("");
		exit.setToolTipText("exit");
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
		exit.setBounds(exitButBounds);
		frame.getContentPane().add(exit);
	}

	public void addMinimizeButton(ActionListener listener) {
		minimizeBut.addActionListener(listener);
		JLabel minimize = new JLabel("");
		minimize.setToolTipText("minimize");
		minimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JLabel lab = (JLabel) e.getSource();
				if (e.getX() > 0 && e.getY() > 0 && e.getX() < lab.getWidth()
						&& e.getY() < lab.getHeight()){
					minimizeBut.doClick();
				}

			}
		});
		minimize.setBounds(minButBounds);
		frame.getContentPane().add(minimize);
	}

	@Override
	public JFrame getMainFrame() {
		return frame;
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
