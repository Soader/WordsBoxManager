package modules;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Language;
import core.RepeatManager;

public abstract class ModulePanel extends JPanel {

    protected static final String MODULE_DIR = "modules";
    private static ArrayList<ModulePanel> modules = new ArrayList<ModulePanel>();
    protected Language currentLanguage;
    private static final long serialVersionUID = 1L;
    protected ArrayList<Language> list = new ArrayList<Language>();
    private Slider slider;
    private ContentPanel contentPanel;
    protected CreationPanel creationPanel;
    protected ModulePanel module;
    protected Image bg_img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/langBG.png"))).getImage();
    protected String listPath;
    protected Color fieldColor = new Color(163, 190, 255);


    public ModulePanel(String path) {
        if (path == null) { // TODO null check, exception
            return;
        }
        listPath = path;
        module = this;
        modules.add(this);

        loadLanguageList();// loads a list of languages from memory
        calculateWordsToRepeat();
        list.add(new Language(true));// adds special "fake" language to the end of the list, that enables creating new languages
        currentLanguage = list.get(0);
        createLanguageFiles();// creates files for every languages if they don't exist
        setLayout(new BorderLayout());
        setOpaque(false);
    }

    private void calculateWordsToRepeat() {
        for (Language lang : list) {
            RepeatManager.initRepeats(lang);// calculates words to repeat for every language on the list
        }
    }

    private void createLanguageFiles() {
        for (Language lang : list) {
            if (!lang.isNewPanel() && !new File(lang.path).exists()) {
                boolean success = new File(lang.path).mkdirs();
                if (!success) {
                    System.out.println("Path creation failed: " + lang.path);
                }
            }
        }
    }

    private void loadLanguageList() {
        try {
            File file = new File(listPath);
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
    }

    public void addSlider(int cellsNumber) {
        // TODO validate parameter
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

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public ContentPanel getContentPanel() {
        return contentPanel;
    }

    public Slider getSlider() {
        return slider;
    }

    public void setCurrentLanguage(Language language) {
        currentLanguage = language;
        slider.update();
        contentPanel.update();
    }

    public Image getBackgroundImage() {
        return bg_img;
    }

    protected ArrayList<ModulePanel> getModulesList() {
        return modules;
    }

    // ------------- inner classes ----------------//

    public abstract class ContentPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        public abstract void update();

        @Override
        protected void paintComponent(Graphics g) { // ?????????
            super.paintComponent(g);
            g.drawImage(bg_img, 0, 0, getWidth(), getHeight(), null);
        }

    }

    protected class Slider extends JPanel {

        private static final int ICON_SIZE = 30;
        private static final int BUTTON_SIZE = 35;
        private static final int BORDER_SIZE = 3;
        private int slotsNumber;
        private JLabel[] slots;
        private JLabel nextButton, previousButton;
        private static final long serialVersionUID = 1L;
        private int width;
        private ImageIcon previousIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/arrow_prev.png")));
        private ImageIcon nextIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/arrow_next.png")));

        public Slider(int iconsNumber) {
            if (iconsNumber >= 1) {
                slotsNumber = iconsNumber;
            }
            else {
                slotsNumber = 1;
            }
            setLayout(null);
            setOpaque(false);

            width = 2 * BUTTON_SIZE + (ICON_SIZE + 6) * slotsNumber;
            setPreferredSize(new Dimension(width, 40));

            createPreviousButton();
            createNextButton();
            createSliderSlots();
        }

        private void createSliderSlots() {
            slots = new JLabel[slotsNumber];
            for (int slot = 0; slot < slotsNumber; slot++) {
                slots[slot] = new JLabel();
                if (slot == slotsNumber / 2) {
                    setSelectedSlotBounds(slot);
                } else
                    setSlotBounds(slot);

                add(slots[slot]);
            }
        }

        private void setSlotBounds(int slot) {
            slots[slot].setBounds((ICON_SIZE + 6) * slot + BUTTON_SIZE,
                    BORDER_SIZE, ICON_SIZE, ICON_SIZE);
        }

        private void setSelectedSlotBounds(int slot) {
            slots[slot].setBorder(BorderFactory.createLineBorder(
                    Color.YELLOW, BORDER_SIZE));
            slots[slot].setBounds((ICON_SIZE + 6) * slot - BORDER_SIZE
                            + BUTTON_SIZE, 0, ICON_SIZE + BORDER_SIZE * 2,
                    ICON_SIZE + BORDER_SIZE * 2);
        }

        private void createNextButton() {
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
                        if (currentLanguage == null) {
                            System.out.println("Selected Language is null"); // to
                            // do
                            return;
                        }
                        int index = list.indexOf(currentLanguage);
                        if (index + 1 < list.size()) {
                            currentLanguage = list.get(index + 1);
                            update();

                        }
                    }
                }
            });
            add(nextButton);
        }

        private void createPreviousButton() {
            previousButton = new JLabel();
            previousButton.setBounds(0, 0, BUTTON_SIZE, BUTTON_SIZE);
            previousButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            previousButton.setIcon(previousIcon);

            previousButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    JLabel lab = (JLabel) e.getSource();
                    if (e.getX() > 0 && e.getY() > 0
                            && e.getX() < lab.getWidth()
                            && e.getY() < lab.getHeight()) {
                        if (currentLanguage == null) {
                            System.out.println("Selected Language is null"); // to
                            // do
                            return;
                        }
                        int index = list.indexOf(currentLanguage);
                        if (index - 1 >= 0) {
                            currentLanguage = list.get(index - 1);
                            update();
                        }
                    }
                }
            });
            add(previousButton);
        }

        protected void update() {
            updateSliderSlots();
            updateModulePanel();
        }

        private void updateSliderSlots() {
            int currentLanguageIndex = list.indexOf(currentLanguage);
            for (int slot = 0; slot < slotsNumber; slot++) {
                int languageIndexForSlot = slot + currentLanguageIndex - (slotsNumber / 2);
                if (isSlotUsedByLanguage(languageIndexForSlot)) {
                    slider.slots[slot].setIcon(list.get(languageIndexForSlot).icon_min);
                } else {
                    slots[slot].setIcon(null);
                }
            }
        }

        private boolean isSlotUsedByLanguage(int languageIndex) {
            return languageIndex >= 0 && languageIndex < list.size();
        }

        public int getWidth() {
            return width;

        }

        protected ArrayList<Language> getList() {
            return list;
        }
    }

    private void updateModulePanel() {
        if (currentLanguage.isNewPanel()) {
            updateCreationPanel();
        } else {
            updateContentPanel();
        }
    }

    private void updateContentPanel() {
        if (creationPanel != null)
            module.remove(creationPanel);
        if (contentPanel != null) {
            module.add(BorderLayout.CENTER, contentPanel);
            contentPanel.update();
        }
    }

    private void updateCreationPanel() {
        if (contentPanel != null) {
            module.remove(contentPanel);
            module.repaint();
            module.revalidate();
        }
        creationPanel.reset();
        module.add(BorderLayout.CENTER, creationPanel);
        module.repaint();
        module.revalidate();
    }

    protected abstract class CreationPanel extends JPanel {

        protected abstract void reset();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg_img, 0, 0, getWidth(), getHeight(), null);
        }
    }

}
