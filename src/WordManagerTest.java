import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class WordManagerTest {
	
	private WordManager wm = new WordManager("short_list.txt");
	
	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	void testGetRandomWordLengthIsGreaterThanZero() {
		assertTrue(wm.getRandomWordLength() > 0);
	}
	
	@Test
	void testGetRandomWordLengthIsLessThanTwelve() {
		// run several times to confirm
		for(int i=0; i < 50; i++) {
			assertTrue(wm.getRandomWordLength() < 12);
		}
	}

	@Test
	void testGuessLetterTrue1() {
		wm.setWordLength(3); //setting word pool to be all 3 letter words
		String[] letters = {"_" , "_" , "_"};
		// Four 3-letter words: one, sea, dog, pug
		// "g" is in same position for 2 of them, so will be in the largest "word family" 
		assertTrue(wm.guessLetter(letters, "g"));
	}
	@Test
	void testGuessLetterTrue2() {
		wm.setWordLength(5); //setting word pool to be all 5 letter words
		String[] letters = {"b" , "l" , "o", "o", "_"};
		// guessing d should be correct, as there is only 1-word left 
		assertTrue(wm.guessLetter(letters, "d"));
	}
	
	@Test
	void testGuessLetterFirstGuessE() {
		wm.setWordLength(5); //setting word pool to be all 5 letter words
		String[] letters = {"_" , "_" , "_", "_", "_"};
		// for 5-letters, E on the first guess should be false 
		assertFalse(wm.guessLetter(letters, "e"));
	}
	
	//========================================================================================
	//= Test Case 1
	//= 5-letter word, no correct letters yet, letter "e" is guessed
	//========================================================================================
	
	HashMap<String, ArrayList<String>> setUp1() {
		wm.setWordLength(5); //setting word pool to be all 5 letter words
		String[] letters = {"_" , "_" , "_", "_", "_"};
		HashMap<String, ArrayList<String>> wordFamMap = wm.getWordFamilies(letters, "e");
		wm.findLargestWordFamily(wordFamMap);
		return wordFamMap;
	}
		
		
	@Test
	void testGetWordFamilies1() {
		// get the word families
		HashMap<String, ArrayList<String>> wordFamMap = setUp1();
		
		// there should be 5 word families
		assertEquals(5,wordFamMap.size());
		
		// these are the five word families that should result
		String[] familyKeys = {"____e", "_e___", "_____", "_e_e_","__e_e"};
		
		// each key should be in the hashmap
		for(int i=0; i < familyKeys.length; i++) {
			assertTrue(wordFamMap.containsKey(familyKeys[i]));
		}
	}
	
	@Test
	void testFindLargestWordFamily1() {
		setUp1();
		
		// wordpool is now the max family size which should be 11
		assertEquals(11,wm.getWordPool().size());
		
		// current familly key is "_____" (5 underscores)
		assertEquals("_____" ,wm.getCurrentFamilyKey());
	}
	
	@Test
	void testGetCurrentLettersArray1() {
		setUp1();
		// this is the expected letter array
		String[] expectedletters = {"_" , "_" , "_", "_", "_"};
		// get the lettersArray returned by the method
		String[] letters = wm.getCurrentLettersArray();
		
		assertArrayEquals(expectedletters, letters);
	}
	
	@Test
	void testGetOneCorrectWord1() {
		setUp1();
		String word = wm.getOneCorrectWord();
		
		assertEquals(5, word.length());  // word must be of length 5
		assertFalse(word.contains("e")); //because e was guessed, e cannot be in the word
	}
	
	//========================================================================================
	//= Test Case 2
	//= 4-letter word, no correct letters yet, letter "z" is guessed
	//========================================================================================
	HashMap<String, ArrayList<String>> setUp2() {
		wm.setWordLength(4); //setting word pool to be all 4 letter words
		String[] letters = {"_" , "_" , "_", "_"};
		HashMap<String, ArrayList<String>> wordFamMap = wm.getWordFamilies(letters, "z");
		wm.findLargestWordFamily(wordFamMap);
		return wordFamMap;
		
	}
	
	@Test
	void testGetWordFamilies2() {
		HashMap<String, ArrayList<String>> wordFamMap = setUp2();
		assertEquals(1,wordFamMap.size());
		
		// there is only one word family that should result
		String familyKey = "____";
		
		// the key should be in the hashmap
		assertTrue(wordFamMap.containsKey(familyKey));
	}
	
	@Test
	void testFindLargestWordFamily2() {
		setUp2();
		
		// wordpool is now the max family size which should be 12
		assertEquals(12,wm.getWordPool().size());
		// current familly key is "_____" (4 underscores)
		assertEquals("____" ,wm.getCurrentFamilyKey());
	}
	
	@Test
	void testGetCurrentLettersArray2() {
		setUp2();
		
		// this is the expected letter array
		String[] expectedletters = {"_" , "_" , "_", "_"};
		// get the lettersArray returned by the method
		String[] letters = wm.getCurrentLettersArray();
		
		assertArrayEquals(expectedletters, letters);
	}
	
	@Test
	void testGetOneCorrectWord2() {
		setUp2();
		String word = wm.getOneCorrectWord();
		
		assertEquals(4, word.length());  // word must be of length 4
		assertFalse(word.contains("z")); // because z was guessed, z cannot be in the word
	}
	
	//========================================================================================
	//= Test Case 3
	//= 8-letter word, current letters are "e_e_tri_" , letter "l" is guessed
	//= at this point the only possible word is "electric"
	//========================================================================================
	HashMap<String, ArrayList<String>> setUp3() {
		wm.setWordLength(8); //setting word pool to be all 8 letter words
		String[] letters = {"e" , "_" , "e", "_","t" , "r" , "i", "_"};
		HashMap<String, ArrayList<String>> wordFamMap = wm.getWordFamilies(letters, "l");
		wm.findLargestWordFamily(wordFamMap);
		return wordFamMap;
		
	}
	
	@Test
	void testGetWordFamilies3() {
		HashMap<String, ArrayList<String>> wordFamMap = setUp3();
		assertEquals(1,wordFamMap.size());
		
		// there is only one word family that should result
		String familyKey = "ele_tri_";
		
		// the key should be in the hashmap
		assertTrue(wordFamMap.containsKey(familyKey));
	}
	
	@Test
	void testFindLargestWordFamily3() {
		setUp3();
		
		// wordpool is now the max family size which should be 1
		assertEquals(1,wm.getWordPool().size());
		// current familly key is "ele_tri_" 
		assertEquals("ele_tri_" ,wm.getCurrentFamilyKey());
	}
	
	@Test
	void testGetCurrentLettersArray3() {
		setUp3();
		
		// this is the expected letter array
		String[] expectedletters = {"e" , "l" , "e", "_","t" , "r" , "i", "_"};
		// get the lettersArray returned by the method
		String[] letters = wm.getCurrentLettersArray();
		
		assertArrayEquals(expectedletters, letters);
	}
	
	@Test
	void testGetOneCorrectWord3() {
		setUp3();
		String word = wm.getOneCorrectWord();
		
		assertEquals(8, word.length());  // word must be of length 8
		assertEquals("electric", word);  // there is only one word, it must be electric!
	}
	
	//========================================================================================
	//= Test Case 4
	//= 3-letter word, current letters are "cu_" , letter "p" is guessed
	//= there is no word in the short_list dictionary that fits this template
	//========================================================================================
	HashMap<String, ArrayList<String>> setUp4() {
		wm.setWordLength(3); //setting word pool to be all 3 letter words
		String[] letters = {"c" , "u" , "_"};
		HashMap<String, ArrayList<String>> wordFamMap = wm.getWordFamilies(letters, "p");
		wm.findLargestWordFamily(wordFamMap);
		return wordFamMap;
		
	}
	@Test
	void testGetWordFamilies4() {
		HashMap<String, ArrayList<String>> wordFamMap = setUp4();
		
		// we should not get any results for the above inputs
		assertEquals(0,wordFamMap.size());
		assertTrue(wordFamMap.isEmpty());
	}
	
	@Test
	void testFindLargestWordFamily4() {
		setUp4();
		
		// we should not get any results for the above inputs
		assertEquals(null, wm.getWordPool());
		// current family key should be an empty string "" 
		assertEquals("" ,wm.getCurrentFamilyKey());
	}

	@Test
	void testGetCurrentLettersArray4() {
		setUp4();
		
		// the expected letter array is empty
		String[] expectedletters = {};
		// get the lettersArray returned by the method
		String[] letters = wm.getCurrentLettersArray();
		
		assertArrayEquals(expectedletters, letters);
	}
	
	@Test
	void testGetOneCorrectWord4() {
		setUp4();
		String word = wm.getOneCorrectWord();
		
		assertEquals("", word);  // returns empty string because there is no match
	}

	@Test
	void testGetInitialWordIsGreaterThanZero() {
		assertTrue(wm.getInitialWord().length() > 0);
	}
	
	@Test
	void testGetInitialWordIsLessThanTwelve() {
		// run several times to confirm
		for(int i=0; i < 50; i++) {
			assertTrue(wm.getInitialWord().length() < 12);
		}
	}
	@Test
	void testSetWordLengthWordPoolMatch() {
		// set the length to 5
		wm.setWordLength(5);
		// get the wordPool
		ArrayList<String> wordPool = wm.getWordPool();
		// all words should have length 5
		for(int i=0; i < wordPool.size(); i++) {
			assertEquals(5,wordPool.get(i).length());
		}
	}
	
	@Test
	void testSetWordCurrenWordMatch() {
		String word = "hello";
		// set the word
		wm.setWord(word);
		// currentword should match the set word
		assertEquals(word,wm.getCurrentWord());
	}
	@Test
	void testSetWordPoolMatch() {
		String word = "hello";
		// set the word
		wm.setWord(word);
		// get the wordPool
		ArrayList<String> wordPool = wm.getWordPool();
		// all words should have the same length as the set word
		for(int i=0; i < wordPool.size(); i++) {
			assertEquals(word.length(),wordPool.get(i).length());
		}
	}
	@Test
	void testIsLetterInWordTrue() {
		String word = "hello";
		// set the word
		wm.setWord(word);
		
		// "l" should be in word
		assertTrue(wm.isLetterInWord("l"));
		// "h" should be in word
		assertTrue(wm.isLetterInWord("h"));
		// "0" should be in word
		assertTrue(wm.isLetterInWord("o"));
	}
	@Test
	void testIsLetterInWordFalse() {
		String word = "hello";
		// set the word
		wm.setWord(word);
		
		// "x" should not be in word
		assertFalse(wm.isLetterInWord("x"));
		// "a" should not be in word
		assertFalse(wm.isLetterInWord("a"));
		// "f" should not be in word
		assertFalse(wm.isLetterInWord("f"));
	}



}
