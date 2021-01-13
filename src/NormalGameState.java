import java.util.ArrayList;
/**
 * PLEAS NOTE: THIS CLASS IS NOT ACTUALLY USED IN THE IMPLEMENTATION OF EVIL HANGMAN BUT IS INCLUDED FOR COMPLETENESS
 * 
 * This is the original normal version of the GameState class
 * @author david binstock
 *
 */
public class NormalGameState {
	//=============================================================================================================================================
	// = INSTANCE VARIABLES 
	//=============================================================================================================================================
	private WordManager wordManager;
	private String word;
	private String[] lettersArray;
	private ArrayList<String> guessedLettersArrayList;
	private ArrayList<String> wrongLettersArrayList;
	
	//=============================================================================================================================================
	// = CONSTRUCTORS 
	//=============================================================================================================================================
	public NormalGameState() {
		wordManager = new WordManager();
		guessedLettersArrayList = new ArrayList<String>();
		wrongLettersArrayList = new ArrayList<String>();
	}
	// if a file name is given, pass that along to the word manager to be used by the dictionary
	// primarily for testing
	public NormalGameState(String dictionaryFileName) {
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
		word = wordManager.getInitialWord();
		initializeLetters();
		guessedLettersArrayList.clear();
		wrongLettersArrayList.clear();
//		System.out.println("---------Game Initialized-----------");
//		System.out.println("---------Word is: " + word + "-----------");
	}
	

	/**
	 * This method indicates whether the word has been successfully guessed
	 * or not; i.e. has the user won the game. It returns true or false accordingly.
	 * 
	 * @return boolean: true if the word has been guessed (all letters correctly guessed),
	 * false if the word has not been guessed.
	 */
	public boolean isWordGuessed() {
		for(int i=0; i < lettersArray.length ; i++) {
			if(lettersArray[i] == "_") {
				return false;
			}
		}
		return true;
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
		for(int i=0; i < guessedLettersArrayList.size(); i++) {
			if(guessedLettersArrayList.get(i).equals(letter)) {
				return true;
			}
		}
		return false;
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
		// add letter to guessed letters array regardless
		guessedLettersArrayList.add(letter);
		
		if(wordManager.isLetterInWord(letter)) {
			updateLetters(letter);
			return true;
		}else {
			wrongLettersArrayList.add(letter);
			return false;
		}
	}
	
	public void updateLetters(String letter) {
		for(int i=0; i < word.length(); i++) {
			if(word.substring(i, i+1).equals(letter) ) {
				lettersArray[i]= letter;
			}
		}
	}
	
	public void printLetters() {
		
		// print the letters and dashes
		String printString = "";
		for(int i=0; i < lettersArray.length; i++) {
			printString += lettersArray[i] + "  ";
		}
		System.out.println(printString);
	}
	
	public void printWrongLetters() {
		System.out.print("Wrong Guesses:");
		String printString = "";
		for(int i=0; i < wrongLettersArrayList.size(); i++) {
			printString += wrongLettersArrayList.get(i) + "  ";
		}
		System.out.println(printString);
	}
	
	public String getAnswer() {
		return word;
	}
		
	//=============================================================================================================================================
	// = HELPER METHODS & METHODS FOR TESTING
	//=============================================================================================================================================
	private void initializeLetters() {
		lettersArray = new String [word.length()];
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
	public void setWord(String word) {
		wordManager.setWord(word);
		this.word = word;
		initializeLetters();
	}
	
}
