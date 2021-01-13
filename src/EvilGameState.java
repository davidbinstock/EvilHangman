import java.util.ArrayList;
/**
 * This class takes care of the evil versions of the 
 * 
 * @author david binstock
 *
 */
public class EvilGameState {
	//=============================================================================================================================================
	// = INSTANCE VARIABLES 
	//=============================================================================================================================================
	private WordManager wordManager;
	private String[] lettersArray;
	private ArrayList<String> guessedLettersArrayList;
	private ArrayList<String> wrongLettersArrayList;
	
	//=============================================================================================================================================
	// = CONSTRUCTOR(S)
	//=============================================================================================================================================
	public EvilGameState() {
		wordManager = new WordManager();
		guessedLettersArrayList = new ArrayList<String>();
		wrongLettersArrayList = new ArrayList<String>();
	}
	
	// if a file name is given, pass that along to the word manager to be used by the dictionary
	// primarily for testing
	public EvilGameState(String dictionaryFileName) {
		wordManager = new WordManager(dictionaryFileName);
		guessedLettersArrayList = new ArrayList<String>();
		wrongLettersArrayList = new ArrayList<String>();
	}
	
	//=============================================================================================================================================
	// = PRIMARY METHODS
	//=============================================================================================================================================
	
	/**
	 * This method "initializes" a new game, selecting a random word length,
	 * resetting the guessed letters, and creating the blank dashes to display
	 * to the user.
	 */
	public void initializeGame() {
		int wordLength = wordManager.getRandomWordLength();
		initializeLetters(wordLength);
		guessedLettersArrayList.clear();
		wrongLettersArrayList.clear();
//		System.out.println("--------- (Evil) Game Initialized-----------");
//		System.out.println("---------Word length is: " + wordLength + "-----------");
	}
	

	/**
	 * This method indicates whether the word has been successfully guessed
	 * or not; i.e. has the user won the game. It returns true or false accordingly.
	 * 
	 * @return boolean: true if the word has been guessed (all letters correctly guessed),
	 * false if the word has not been guessed.
	 */
	public boolean isWordGuessed() {
		for(int i=0; i < lettersArray.length ; i++) {	// loop through the letters array
			if(lettersArray[i].equals("_")) {			// if any position is still and underscore, then word has not been guessed
				return false;							// return false
			}
		}
		return true;									// otherwise, all letters have been correctly guessed; return true
	}
	
	/**
	 * This method indicates whether a given letter (as a String) was already
	 * guessed, and returns true or false accordingly.
	 * 
	 * @param letter (as a String)
	 * @return boolean: true if letter was already guessed, false if the letter
	 * has not been guessed
	 */
	public boolean wasLetterGuessed(String letter) {
		letter = letter.toLowerCase();
		for(int i=0; i < guessedLettersArrayList.size(); i++) { // loop through guessed letters
			if(guessedLettersArrayList.get(i).equals(letter)) { // if any of them math the passed in letter, it was already guessed
				return true;
			}
		}
		return false;											// otherwise the letter has not been guessed
	}
	
	/**
	 * This method given a letter (as a String) checks if the letter is correct or not
	 * and returns true or false accordingly. Additionally, it adds the letter to the 
	 * list of guessed letters, and, if wrong, the list of wrong letters. 
	 * 
	 * @param letter (as a String)
	 * @return boolean: true if the letter is correct, false if it is not
	 */
	public boolean guessLetter(String letter) {
		letter = letter.toLowerCase();
		// add letter to guessed letters array
		guessedLettersArrayList.add(letter);
		
		// call wordManager guessLetter method
		boolean isCorrect = wordManager.guessLetter(lettersArray, letter);
		
		// if letter is correct, update letters and return true
		// if letter is incorrect, add to wrong letters and return false
		if(isCorrect) {
			updateLetters(letter);
			return true;
		}else {
			wrongLettersArrayList.add(letter);
			return false;
		}
	}
	
	/**
	 * This method prints out the current word with any 
	 * correctly guessed letters displayed, and all unguessed
	 * letters as underscores
	 */
	public void printLetters() {
		String printString = "";
		
		// construct the print string based on the lettersArray
		for(int i=0; i < lettersArray.length; i++) {
			printString += lettersArray[i] + "  ";
		}
		
		// print out the resulting string
		System.out.println(printString);
	}
	
	public void printWrongLetters() {
		System.out.print("Wrong Guesses: ");
		String printString = "";
		for(int i=0; i < wrongLettersArrayList.size(); i++) {
			printString += wrongLettersArrayList.get(i) + " ";
		}
		System.out.println(printString);
	}
	
	/**
	 * This method returns the "correct" answer which is
	 * really just one random word that satisfies the current
	 * word family 
	 * 
	 * @return the "correct" word (as a String)
	 */
	public String getAnswer() {
		return wordManager.getOneCorrectWord();
	}
	
	//=============================================================================================================================================
	// = HELPER METHODS / METHODS FOR TESTING
	//=============================================================================================================================================
	private void updateLetters(String letter) {
		lettersArray = wordManager.getCurrentLettersArray();
	}
	
	private void initializeLetters(int wordLength) {
		lettersArray = new String [wordLength];
		for(int i=0; i < lettersArray.length ; i++) {
			lettersArray[i]= "_";
		}
	}
	
	/**
	 * FOR TESTING ONLY
	 * @return
	 */
	public String[] getLettersArray() {
		return lettersArray;
	}
	
	/**
	 * FOR TESTING ONLY
	 * @return
	 */
	public ArrayList<String> getGuessedLettersArrayList() {
		return guessedLettersArrayList;
	}
	
	/**
	 * FOR TESTING ONLY
	 * @return
	 */
	public ArrayList<String> getWrongLettersArrayList() {
		return wrongLettersArrayList;
	}
	
	/**
	 * FOR TESTING ONLY
	 * @param length
	 */
	public void setWordLength(int length) {
		wordManager.setWordLength(length);
		initializeLetters(length);
	}
	

}
