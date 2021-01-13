import java.util.*;
import java.io.*;
/**
 * This class deals with reading from the dictionary file
 * 
 * @author david binstock
 *
 */
public class Dictionary {
	//=============================================================================================================================================
	// = INSTANCE VARIABLES 
	//=============================================================================================================================================
	private String dicitonaryFileName;
	private File dictionaryFile;
	private Random randomGenerator;
	
	//=============================================================================================================================================
	// = CONSTRUCTOR
	//=============================================================================================================================================
	public Dictionary(String filename) {
		dicitonaryFileName = filename;
		dictionaryFile = new File(dicitonaryFileName);
		randomGenerator = new Random();
	}
	
	//=============================================================================================================================================
	// = PRIMARY METHODS
	//=============================================================================================================================================
	/**
	 * this method returns a random word from the dictionary
	 * 
	 * @return
	 */
	public String getRandomWord() {
		int wordNum = randomGenerator.nextInt(getDictionaryLength()+1);
		String selectedWord = "";
		try {
			Scanner s = new Scanner(dictionaryFile);
			for(int i=0; i < wordNum-1 ; i++) {
				s.next();
			}
			selectedWord = s.next();
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("-----Dictionary file not found-------");
			e.printStackTrace();
		}
		return selectedWord;
		
	}
	
	/**
	 * This method returns all words with a given length.
	 * @param wordLength (as an int)
	 * @return ArrayList[String]; an ArrayList of all words of the given length
	 */
	public ArrayList<String> getWordsWithLength(int wordLength) {
		ArrayList<String> wordList = new ArrayList<String>();
		try {
			Scanner s = new Scanner(dictionaryFile);
			String currWord;
			while(s.hasNext()) {
				currWord = s.next();
				if(currWord.length() == wordLength) {
					wordList.add(currWord);
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("-----Dictionary file not found-------");
			e.printStackTrace();
		}
		return wordList;
	}

	//=============================================================================================================================================
	// = HELPER METHODS
	//=============================================================================================================================================

	/**
	 * This method returns the total number of entries (words) that are 
	 * in the dictionary 
	 * 
	 * @return int representing the number of words in the dictionary
	 */
	private int getDictionaryLength() {
		int numOfWords = 0;
		try {	
			Scanner s = new Scanner(dictionaryFile);
			while (s.hasNext()) {
				s.next();
				numOfWords++;
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("-----Dictionary file not found-------");
			e.printStackTrace();
		}
		return numOfWords;
	}
	
	/**
	 * This is for TESTING ONLY
	 * @return
	 */
	public int getDictionaryLengthForTestingOnly() {
		return getDictionaryLength();
	}
	
}
