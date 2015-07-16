package core;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class RepeatManager {

	private static Todays todaysObject;
	private static File pathTodays, pathWords;
	private static ArrayList<Word> words;

	public static boolean initRepeats(Language lang) {
		pathTodays = new File(lang.path + "todays.ser");
		pathWords = new File(lang.path + "words.ser");
		words = new ArrayList<Word>();
		if(!loadWords())
			return false;
		lang.wordsNumber = words.size();
		if (!helloAgain())
			if(!createTodays())
				return false;
		lang.repeatsNumber = todaysObject.repeatList.size();
		clear();
		return true;
	}
	
	public static int getRepeatsNumber(Language lang){
		
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		File file = new File(lang.path + "todays.ser");
		Todays tod;
		
		try {
			if (!file.exists())
				return 0;
			fileIn = new FileInputStream(file);
			objectIn = new tools.HackedObjectInputStream(fileIn);
			tod = (Todays) objectIn.readObject();
			return
				tod.repeatList.size();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (fileIn != null)
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (objectIn != null)
				try {
					objectIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		
		
	}

	// checks for second run of the application at the same day
	private static boolean helloAgain() {
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		Date date = new Date();

		try {
			if (!pathTodays.exists())
				return false;
			fileIn = new FileInputStream(pathTodays);
			objectIn = new tools.HackedObjectInputStream(fileIn);
			todaysObject = (Todays) objectIn.readObject();
			if (todaysObject.day == date.getTime() / 86400000) {
				return true;
			} else
				return false;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fileIn != null)
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (objectIn != null)
				try {
					objectIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

	private static boolean createTodays() {
		Random rand = new Random();
		Date date = new Date();
		int day = (int) (date.getTime() / ManagerWindow.DIVIDER);
		todaysObject = new Todays();
		for (Word go : words) {
			if ((day - go.lastRepeated) >= (2 * go.repeatNumber)
					&& rand.nextInt(100) < (50 + 15 * (day - go.lastRepeated - 2 * go.repeatNumber))) {
				todaysObject.repeatList.add(go);
			}
		}
		Collections.shuffle(todaysObject.repeatList);
		FileOutputStream fileout;
		try {
			fileout = new FileOutputStream(pathTodays);
			ObjectOutputStream objectout = new ObjectOutputStream(fileout);
			objectout.writeObject(todaysObject);
			fileout.close();
			objectout.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	private static boolean loadWords() {
		if (pathWords.exists()) {
			try {
				FileInputStream fileinput = new FileInputStream(pathWords);
				ObjectInputStream objectinput = new tools.HackedObjectInputStream(fileinput);
				words = (ArrayList<Word>) objectinput.readObject();
				fileinput.close();
				objectinput.close();
				return true;
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			
			
		}
		else
			return false;
	}

	private static void clear() {
		todaysObject = null;
		pathTodays = null;
		pathWords = null;
		words = null;
	}
	

}
