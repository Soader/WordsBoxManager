package core;
import java.io.Serializable;
import java.util.Date;


public class Word implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String polish;
	public String english;
	public int repeatNumber;
	public long ID;
	public int lastRepeated;
	
	public Word(String polish, String english){
		Date date = new Date();
		ID = date.getTime();
		repeatNumber = 0;
		this.polish = polish;
		this.english = english;
		lastRepeated = (int)(date.getTime() / ManagerWindow.DIVIDER);
	}
	
	public void repeated(){
		Date date = new Date();
		lastRepeated = (int)(date.getTime() / ManagerWindow.DIVIDER);
		repeatNumber++;
	}

}
