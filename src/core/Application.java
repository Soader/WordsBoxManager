package core;

import mvp.IMainWindow;
import mvp.Presenter;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pja on 2015-08-12.
 */
public class Application {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IMainWindow mainWindow = new MainWindow();
                    Presenter presenter = new Presenter(mainWindow);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
