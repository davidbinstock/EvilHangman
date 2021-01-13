import java.util.*;
/**
 * This is the main controller class which runs the game and gets user input
 * 
 * @author david binstock
 *
 */
public class GameController {
	//=============================================================================================================================================
	// = INSTANCE VARIABLES 
	//=============================================================================================================================================
//	private NormalGameState normalGameState; // normalGameState could be substituted for evilGameState (variable would need to be renamed in code below)
	private EvilGameState evilGameState;
	private String mode;
	private int numOfGuesses;
	private Scanner scnr;

	
	//=============================================================================================================================================
	// = CONSTRUCTOR
	//=============================================================================================================================================
	public GameController(){
//		normalGameState = new NormalGameState();
		evilGameState = new EvilGameState();
		scnr = new Scanner(System.in);
	}
	
	//=============================================================================================================================================
	// = PRIMARY METHODS
	//=============================================================================================================================================
	/**
	 * This is the entry point for the game. Will keep calling
	 * the play() method until the user opts to quit.
	 */
	public void run() {
		System.out.println("Welcome to Hangman!!");
		
		boolean quit = false;
		// stay in loop until user indicates they want to quit
		while(!quit) {
			// call the play() method to begin a game
			play();
			
			// once the game is finished ask the user if they would like to play again and act accordingly
			System.out.println("Would you like to play again? Type any key to indicate 'yes' and play again. Enter 'q' to quit ");
			String replayInput = scnr.next().toLowerCase();
			if(replayInput.equals("q")) {
				quit = true;              // set quit to true to exit loop and finish program
			}
		}
	}
	
	
	/**
	 * This is the main method that executes the gameplay, displaying the board, prompting the
	 * user for input, checking the guess, and keeping track of the number of guesses. 
	 */
	public void play() {
		// declare some variables that will be used
		String guessedletter;
		boolean isCorrect;
		
		// initialize the game (set word length, reset gamestate) and (re)set number of guesses
		evilGameState.initializeGame();
		numOfGuesses = 10;
		
		// perform this loop until the user is out of guesses or the word is correctly guessed
		while(numOfGuesses !=0 && !evilGameState.isWordGuessed()) {
			
			// Display "board" and prompt user for a guess
			System.out.println("-------------------------\n");
			System.out.println("Guess a letter");		
			evilGameState.printLetters();
			System.out.println("\nGuesses left: " + numOfGuesses);
			evilGameState.printWrongLetters();
			
			// get a (validated) guess from the user
			guessedletter = getLetter();
			
			
			// determine if the guessed letter is in the word
			// note: guessLetter method will update the gameState accordingly
			isCorrect = evilGameState.guessLetter(guessedletter);
			
			// let the user know if they guessed correctly or not
			if(!isCorrect) {
				System.out.println("I'm sorry but that guess is wrong...");
				numOfGuesses--;												// if incorrect, decrement the number of guesses 
			}
			else {
				System.out.println("Well done! You've guessed correctly!");
			}
		}
		
		// While loop has finished, so game is over; either the user has won or lost
		System.out.println("-------------------------\n");
		if(evilGameState.isWordGuessed()) {
			evilGameState.printLetters();
			System.out.println("Congratulations! You've correctly guessed the word and won the game!!");
		}else {
			System.out.println("You're out of guesses. You lose! Good day sir!");
			System.out.println("The correct answer was: " + evilGameState.getAnswer() + "\n");
		}
	}
	
	//=============================================================================================================================================
	// = HELPER METHODS
	//=============================================================================================================================================
	/**
	 * private method which returns a validated input
	 * from the user; i.e. a single alphabetic letter
	 * 
	 * @return a single alphabetic letter (as a string)
	 */
	private String getLetter() {
		String input="";
		boolean valid = false;
		while(!valid) {
			input = scnr.next();			// get input
			if(isValidInput(input)) {			// check if its valid
				valid = true;				// if yes, set valid to true to end while loop
			}
		}
		return input.toLowerCase();				// return the validated input, as a lowercase
	}
	
	
	/**
	 * a private method to validate the input. Checks to make sure the
	 * input is a single letter, an alphabetic letter, and not already 
	 * guessed. Returns true or false accordingly. 
	 * 
	 * @param input
	 * @return boolean true or false
	 */
	private boolean isValidInput(String input) {
		char[] inputAsCharArray = input.toCharArray();
		
		// check if the input was a single character
		if(inputAsCharArray.length > 1) {
			System.out.println("Please enter a single letter");
			return false;
		}
		// check if entry was alphabetic letter
		if(!Character.isLetter(inputAsCharArray[0])) {
			System.out.println("Please enter an alphabetic letter");
			return false;
		}
		// check whether letter was guessed. Depending on mode, use a different Game State
		if(evilGameState.wasLetterGuessed(input)) {
			System.out.println("This letter was already guessed. Please enter another guess.");
			return false;
		}	
		
		// otherwise return true
		return true;
	}
	
	/**
	 * FOR TESTING ONLY
	 * @param input
	 * @return
	 */
	public boolean isValidInputTest(String input) {
		return isValidInput(input);
	}
}
