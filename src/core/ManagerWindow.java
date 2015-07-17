package core;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import modules.LanguageModule;
import modules.QuickerModule;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;
import java.awt.Cursor;
import java.awt.event.MouseMotionAdapter;

public class ManagerWindow {
	public static final String BACKGROUND_IMAGE = "C:\\Users\\pja\\Documents\\tlo.png";
	private JFrame frame;
	private JPanel modulesPanel;
	private int MouseX, MouseY;
	public static final long DIVIDER = 86400000;
	private Rectangle modulesPanelRectangle = new Rectangle(0, 130, 500, 300);
	private Rectangle frameRectangle = new Rectangle(100, 100, 850, 480);
	public static final boolean DEBUG = true;
	private static final boolean ACTIVITY_PANEL = false;

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

		decorateWindow();
		modulesPanel.add(new LanguageModule());
		modulesPanel.add(new QuickerModule());
	}

	/**
	 * Creates decoration for main window with exit and minimize buttons
	 */
	private void decorateWindow() {

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
						BACKGROUND_IMAGE));

		backgroundLabel.setBounds(0, 0, frameRectangle.width,
				frameRectangle.height);
		frame.getContentPane().add(backgroundLabel);
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
