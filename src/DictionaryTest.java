import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.io.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryTest {
	private Dictionary dict = new Dictionary("short_list.txt");
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testGetDictionaryLengthCorrectLength() {
		assertEquals(57,dict.getDictionaryLengthForTestingOnly());
	}
	@Test
	void testGetDictionaryLengthZeroLength() {
		assertFalse(0 == dict.getDictionaryLengthForTestingOnly());
	}
	
	@Test
	void testGetRandomWordIsString() {
		String word = dict.getRandomWord();
		assertTrue(word instanceof String);
	}
	
	@Test
	void testGetRandomWordIsInDictionary() {
		String word = dict.getRandomWord();
		
		// Scan the dictionary to make make sure the word is in there
		File dFile = new File("short_list.txt");
		try {
			Scanner s = new Scanner(dFile);
			boolean inDictionary = false;
			while(s.hasNext()) {
				if(word.equals(s.next())) {
					inDictionary = true;
				}
			}
			assertTrue(inDictionary);
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testGetWordsWithLengthKnownWords() {
		ArrayList<String> words;
		words = dict.getWordsWithLength(3);
		
		String knownWord1 = "one";
		String knownWord2 = "sea";
		String knownWord3 = "dog";
		String knownWord4 = "pug";
		
		// length of array list should be 4
		assertEquals(4, words.size());
		// array list should have all the above words in it
		assertTrue(words.contains(knownWord1));
		assertTrue(words.contains(knownWord2));
		assertTrue(words.contains(knownWord3));
		assertTrue(words.contains(knownWord4));

	}
	
	@Test
	void testGetWordsWithLengthCorrectLength() {
		ArrayList<String> words;
		
		words = dict.getWordsWithLength(4);
		for(int i=0; i < words.size(); i++) {
			assertEquals(4, words.get(i).length());
		}
		
		words = dict.getWordsWithLength(12);
		for(int i=0; i < words.size(); i++) {
			assertEquals(12, words.get(i).length());
		}
	}
	
	@Test
	void testGetWordsWithLengthZero() {
		ArrayList<String> words;
		words = dict.getWordsWithLength(0);
		// shouldn't be any words with length 0
		assertEquals(0, words.size());
		
	}
	
	@Test
	void testGetWordsWithLength99() {
		ArrayList<String> words;
		words = dict.getWordsWithLength(99);
		// shouldn't be any words with length 99
		assertEquals(0, words.size());
		
	}

}
