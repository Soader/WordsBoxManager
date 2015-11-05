package core;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Todays implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long day;
	private ArrayList<Word> repeatList = new ArrayList<Word>();
	
	public Todays(){
		Date date = new Date();
		day = date.getTime() / MainWindow.DIVIDER;
	}

	public long getDay() {
		return day;
	}

	public ArrayList<Word> getRepeatList() {
		return repeatList;
	}
	
}
