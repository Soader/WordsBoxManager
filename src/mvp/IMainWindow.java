package mvp;


import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by pja on 2015-08-11.
 */
public interface IMainWindow {
    //public JFrame frame = null;

    public void addMinimizeButton(ActionListener listener);
    public JFrame getMainFrame();



}
