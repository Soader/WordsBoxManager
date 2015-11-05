package core;
import java.io.Serializable;
import java.util.Date;


public class Word implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String polish;
	private String english;
	private int repeatNumber;
	private long ID;
	private int lastRepeated;
	
	public Word(String polish, String english){
		Date date = new Date();
		ID = date.getTime();
		repeatNumber = 0;
		this.polish = polish;
		this.english = english;
		lastRepeated = (int)(date.getTime() / MainWindow.DIVIDER);
	}
	
	public void repeated(){
		Date date = new Date();
		lastRepeated = (int)(date.getTime() / MainWindow.DIVIDER);
		repeatNumber++;
	}

	public int getRepeatNumber() {
		return repeatNumber;
	}

	public int getLastRepeated() {
		return lastRepeated;
	}

	
	
}
