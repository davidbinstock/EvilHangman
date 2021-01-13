import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {
	
	private GameController gc = new GameController();
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testIsValidInputMultipleCharacters() {
		assertFalse(gc.isValidInputTest("test"));
	}
	
	@Test
	void testIsValidInputSingleNumber() {
		assertFalse(gc.isValidInputTest("9"));
	}
	@Test
	void testIsValidInputSingleSymbol() {
		assertFalse(gc.isValidInputTest("$"));
	}
	@Test
	void testIsValidInputSinglePunctuation() {
		assertFalse(gc.isValidInputTest(","));
	}
	
	@Test
	void testIsValidInputSingleLetterLowerTrue() {
		assertTrue(gc.isValidInputTest("d"));
	}
	@Test
	void testIsValidInputSingleLetterUpperTrue() {
		assertTrue(gc.isValidInputTest("D"));
	}

}
