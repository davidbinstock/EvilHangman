import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NormalGameStateTest {
	private String[] alphabetArray = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	NormalGameState egs = new NormalGameState("short_list.txt");

	@BeforeEach
	void setUp() throws Exception {
		// to reset/ initialize the gameState
		egs.initializeGame();
	}

	@Test
	void testInitializeGame() {
		// guess some letters
		egs.guessLetter("s");
		egs.guessLetter("e");
		egs.guessLetter("t");
		
		//guessed letters and wrong letters arrays should NOT be empty
		assertFalse(egs.getGuessedLettersArrayList().isEmpty());
		assertFalse(egs.getWrongLettersArrayList().isEmpty());
		
		// now initialize game again to clear everything
		egs.initializeGame();
		
		//guessed letters and wrong letters arrays should be empty
		assertTrue(egs.getGuessedLettersArrayList().isEmpty());
		assertTrue(egs.getWrongLettersArrayList().isEmpty());
		
		// letters array should be all underscores
		String[] lettersArray = egs.getLettersArray();
		for(int i=0; i < lettersArray.length; i++) {
			assertTrue(lettersArray[i].equals("_"));
		}
	}

	@Test
	void testIsWordGuessedNoGuesses() {
		egs.initializeGame(); // make sure game state is reset
		// word should not be guessed at this point
		assertFalse(egs.isWordGuessed());
	}
	
	@Test
	void testIsWordGuessedTrue1() {
		egs.initializeGame();
		egs.setWord("toe"); // set word
		
		// guess all proper letters
		egs.guessLetter("t");
		egs.guessLetter("o");
		egs.guessLetter("e");
		
		// word should now be guessed at this point
		assertTrue(egs.isWordGuessed());
	}
	@Test
	void testIsWordGuessedTrue2() {
		//guess all letters
		for(int i=0; i < alphabetArray.length; i++) {
			egs.guessLetter(alphabetArray[i]);
		}
			
		// word should now be guessed at this point
		assertTrue(egs.isWordGuessed());
	}

	@Test
	void testWasLetterGuessedTrue() {
		egs.guessLetter("e");
		assertTrue(egs.wasLetterGuessed("e"));
	}
	@Test
	void testWasLetterGuessedCaseInsensitive1() {
		egs.guessLetter("E");
		assertTrue(egs.wasLetterGuessed("e"));
	}
	
	@Test
	void testWasLetterGuessedCaseInsensitive2() {
		egs.guessLetter("t");
		assertTrue(egs.wasLetterGuessed("T"));
	}
	
	@Test
	void testWasLetterGuessedFalse() {
		assertFalse(egs.wasLetterGuessed("g"));
	}

	@Test
	void testGuessLetterResultIsBoolean() {
		//result should be a boolean
		assertTrue(egs.guessLetter("c") == true || egs.guessLetter("c") == false);
	}
	
	@Test
	void testGuessLetterAddedToGuessedLetters() {
		egs.guessLetter("c");
		assertTrue(egs.getGuessedLettersArrayList().contains("c"));
	}
	
	@Test
	void testGuessLetterPlacedInCorrectArrays() {
		
		for(int i=0; i < alphabetArray.length; i++) {
			String nextLetter = alphabetArray[i];
			boolean isCorrect = egs.guessLetter(nextLetter);
			assertTrue(egs.getGuessedLettersArrayList().contains(nextLetter));
			if(!isCorrect) {
				assertTrue(egs.getWrongLettersArrayList().contains(nextLetter));	
			}else {
				assertFalse(egs.getWrongLettersArrayList().contains(nextLetter));	
			}
		}
		
	}


	@Test
	void testGetAnswerWeSet() {
		egs.initializeGame();
		String word = "goodbye";
		egs.setWord(word); // set word length

		// word should now be same word we set
		assertEquals(word,egs.getAnswer());
	}
	
	@Test
	void testGetAnswerSameWord() {
		
		// The same word should be pulled if the word was already guessed
		String word1 = egs.getAnswer();
		String word2 = egs.getAnswer();
		assertEquals(word1,word2);
	}
}
