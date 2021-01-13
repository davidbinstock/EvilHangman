import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
/**
 * This class handles the portion of the code that deals choosing and keeping track of the 
 * word/word families
 * 
 * @author david binstock
 *
 */
public class WordManager {
	//=============================================================================================================================================
	// = INSTANCE VARIABLES 
	//=============================================================================================================================================
	private String currentWord;
	private int wordLength;
	private String dictionaryFileName;
	private Dictionary dictionary;
	private ArrayList<String> wordPool;
	private String currentFamilyKey;
	
	
	//=============================================================================================================================================
	// = CONSTRUCTOR(S)
	//=============================================================================================================================================
	public WordManager() {
		dictionaryFileName = "engDictionary.txt";
		dictionary = new Dictionary(dictionaryFileName);
	}
	 
	// if a file name is given, use that for the dicitonary
	// primarily for testing
	public WordManager(String fileName) {
		dictionaryFileName = fileName;
		dictionary = new Dictionary(dictionaryFileName);
	}
	
	//=============================================================================================================================================
	// = PRIMARY METHODS
	//=============================================================================================================================================
	
	/**
	 * This method chooses a random word from the dictionary
	 * and returns its length
	 * 
	 * @return int 
	 */
	public int getRandomWordLength() {
		wordLength = dictionary.getRandomWord().length();
		wordPool = dictionary.getWordsWithLength(wordLength);
		return wordLength;
	}
	

	
	/**
	 * This method takes in the current lettersArray and a guessed letter. It 
	 * finds all possible word families given the new letter and chooses the one
	 * with the largest number of words. If the guessed letter is part of the word 
	 * family, the method returns true, otherwise it returns false. 
	 * 
	 * @param lettersArray
	 * @param nextLetter
	 * @return boolean true if the guessed letter is part of the largest word family
	 * false if it is not
	 */
	public boolean guessLetter(String[] lettersArray, String nextLetter) {
		HashMap<String, ArrayList<String>> newFamilies = getWordFamilies(lettersArray, nextLetter);
		findLargestWordFamily(newFamilies);
		return currentFamilyKey.contains(nextLetter);
	}
	

	/**
	 * Given a HashMap of word families, this method determines which word family is the largest. 
	 * It then sets the currentFamilyKey equal to the key of the largest family and sets 
	 * wordPool equal to the corresponding array of words.
	 * 
	 * @param wordFamiliesMap
	 */
	public void findLargestWordFamily(HashMap<String, ArrayList<String>> wordFamiliesMap) {
		String maxFamilyKey="";
		int maxFamilySize = 0;
		for (String familyKey : wordFamiliesMap.keySet()){
			if(wordFamiliesMap.get(familyKey).size() > maxFamilySize) {
				maxFamilySize = wordFamiliesMap.get(familyKey).size();
				maxFamilyKey = familyKey;
			}
		}
		
		wordPool = wordFamiliesMap.get(maxFamilyKey);
		currentFamilyKey = maxFamilyKey;

//		System.out.println("Max Family Size: "+ maxFamilySize + " familyKey: " + maxFamilyKey);
	}
	
	/**
	 * This method iterates through the existing pool of words and sorts them into different
	 * word families. It returns a HashMap with word family structures as the key and an arrayList
	 * of mathing words as the value.
	 * 
	 * @param lettersArray
	 * @param nextLetter
	 * @return HashMap[String, ArrayList[String]]: a HashMap of word families
	 */
	public HashMap<String, ArrayList<String>> getWordFamilies(String[] lettersArray, String nextLetter) {
		HashMap<String, ArrayList<String>> wordFamiliesMap = new HashMap<>();
		
		wordpoolsearch:
		for(int i=0; i < wordPool.size(); i++) {      			// loop over words of same length
			String currWord = wordPool.get(i);           		// get next word
			
			// make sure the words are the proper length
			if(currWord.length() != lettersArray.length) {
				continue wordpoolsearch;						// skip to the next iteration if the word is not the proper length
			}
			// make sure word conforms to existing word family
			for(int j=0; j < currWord.length(); j++ ) {		
				String currWordLetter = Character.toString(currWord.charAt(j));
				if(!lettersArray[j].equals("_")) {
					if(!currWordLetter.equals(lettersArray[j])) {
						continue wordpoolsearch;				// skip to the next iteration if the word does not match the current word family
					}
				}
			}
			
			// construct "family key"
			String familyKey = "";
			for(int j=0; j < currWord.length(); j++ ) {		// for each letter in the word
				String currWordLetter = Character.toString(currWord.charAt(j));
				
				if(currWordLetter.equals(nextLetter)) {		// if it matches the guessed letter
					familyKey += nextLetter;				// concatenate that to the familyKey
				}else {
					familyKey += lettersArray[j];			// otherwise concat the existing letter (or underscore)
				}
			}

			// check if family key is in hashmap
			// if yes, add current word to the value arrayList
			if(wordFamiliesMap.containsKey(familyKey)) {
				wordFamiliesMap.get(familyKey).add(currWord);
			}
			// if not, add new entry to hashmap with key: familyKey
			else {
				ArrayList<String> wordFamilyList = new ArrayList<>();
				wordFamilyList.add(currWord);
				wordFamiliesMap.put(familyKey, wordFamilyList);
			}
		}
		
		// Print out of all Family Keys and their size
//		System.out.println("family hashmap created");
//		wordFamiliesMap.forEach((k,v) -> {
//			System.out.println(k + ": "+ v.size());
//		});
		
		return wordFamiliesMap;
		
	}
	
	/**
	 * This method returns a new lettersArray based on the current
	 * word family, with blank positions represented as underscores.
	 * 
	 * @return String array
	 */
	public String[] getCurrentLettersArray() {
		// make string with proper length
		String[] lettersArray = new String[currentFamilyKey.length()];
		
		// copy all letters from the current familyKey to the string
		for(int i=0; i < currentFamilyKey.length(); i++ ) {						
			lettersArray[i]  = Character.toString(currentFamilyKey.charAt(i));
		}
		
		//return the array
		return lettersArray;
	}
	
	/**
	 * This method chooses a random word from the existing wordPool
	 * (i.e. words that match the current word family)
	 * 
	 * @return String a random word that matches the current word family
	 */
	public String getOneCorrectWord() {
		// if wordPool is empty then return an empty string
		if(wordPool == null) {
			return "";
		}
		// otherwise return a random word from the ArrayList
		Random r = new Random();
		int index = r.nextInt(wordPool.size());  // get a random integer between 0 and the total number of words
		return wordPool.get(index);				 // return the word at that index
	}
	
	//=============================================================================================================================================
	// = HELPER METHODS / METHODS FOR TESTING
	//=============================================================================================================================================
	
	public void setWordLength(int length) {
		wordLength = length;
		wordPool = dictionary.getWordsWithLength(wordLength);
		System.out.println("Word Length set to: " + length);
	}
	
	/**
	 * This method is to access the currentFamilyKey - 
	 * Primarily for testing purposes
	 * @return
	 */
	public String getCurrentFamilyKey() {
		return currentFamilyKey;
	}
	
	/**
	 * This method is to access the current wordPool -
	 * Primarily for testing purposes
	 * @return
	 */
	public ArrayList<String> getWordPool() {
		return wordPool;
	}
	/**
	 * This method is to access the current word -
	 * Primarily for testing purposes
	 * @return
	 */
	public String getCurrentWord() {
		return currentWord;
	}
	
	//=============================================================================================================================================
	// = Methods only for Normal Hangman
	//=============================================================================================================================================
	public String getInitialWord() {
		currentWord = dictionary.getRandomWord();
		wordLength = currentWord.length();
		return currentWord;
	}
	
	/**
	 * Sets current word to the given word. Also sets wordPool to an array
	 * of all word with the same length.
	 * @param word
	 */
	public void setWord(String word) {
		currentWord = word;
		wordPool = dictionary.getWordsWithLength(currentWord.length());
	}
	
	public boolean isLetterInWord(String letter) {
		return currentWord.contains(letter);
	}

}
