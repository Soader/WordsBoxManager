package core;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Todays implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long day;
	public ArrayList<Word> repeatList = new ArrayList<Word>();
	
	public Todays(){
		Date date = new Date();
		day = date.getTime() / ManagerWindow.DIVIDER;
	}
}
