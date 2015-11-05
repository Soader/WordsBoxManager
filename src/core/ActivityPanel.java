package core;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;


public class ActivityPanel extends JPanel {

	private int width = 300;
	private int height = 200;
	private static final int MAX_NOTES = 3;
	private Database db;
	private ArrayList<String> notes= new ArrayList<String>();
	
	public ActivityPanel(int x, int y){
		
		setBounds(x, y, width, height);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	}
	
	public void loadNotes(){
		
		
		SwingWorker worker = new SwingWorker<ArrayList<String>, Void>() {
		    @Override
		    public ArrayList<String> doInBackground() {
		    	db = new Database("sql551861", "nU3%nY6!", "sql551861", "sql5.freemysqlhosting.net", 3306);
				if(!db.connectToSource())
					return null;
		        final ArrayList<String> innerNotes = db.getLogs(MAX_NOTES);
		        return innerNotes;
		    }

		    @Override
		    public void done() {
		    	
		        try {
		            notes = get();
		    		if(notes == null)
		    			return;
		    		for(String go : notes){
		    			JLabel label = new JLabel(go);
		    			label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		    			add(label);
		    		}
		    		revalidate();
	    			repaint();
		        } catch (InterruptedException ignore) {}
		        catch (java.util.concurrent.ExecutionException e) {
		            String why = null;
		            Throwable cause = e.getCause();
		            if (cause != null) {
		                why = cause.getMessage();
		            } else {
		                why = e.getMessage();
		            }
		            System.err.println("Error retrieving file: " + why);
		        }
		    }
		};
		worker.execute();
		
	}
}
