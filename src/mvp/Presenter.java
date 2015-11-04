package mvp;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by pja on 2015-08-11.
 */
public class Presenter {
    private IModel model;
    private IMainWindow view;
    private JFrame frame;

//    public Presenter(IModel model, IMainWindow view){
//        if(model == null || view == null){
//            throw new NullPointerException();
//        }
//        this.model = model;
//        this.view = view;
//        this.frame = view.getMainFrame();
//    }

    public Presenter(IMainWindow view){
        if(view == null){
            throw new NullPointerException();
        }
        this.view = view;
        this.frame = view.getMainFrame();
        bindActions();
    }

    private void bindActions(){
        view.addMinimizeButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(Frame.ICONIFIED);
            }
        });
    }
}
