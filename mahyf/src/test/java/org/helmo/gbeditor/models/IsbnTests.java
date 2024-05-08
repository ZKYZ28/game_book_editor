package org.helmo.gbeditor.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IsbnTests {
	
	@Test
	void firstIsbn() {
		Isbn isbn = new Isbn(0, 210208);		
		assertEquals("2-210208-01-7", isbn.getIsbnNumber());
	}
	
	@Test
	void giveIsbnNumberInContr() {
		Isbn isbn = new Isbn("2-210208-01-7");
		assertEquals("2-210208-01-7", isbn.getIsbnNumber());
	}
	
	@Test
	void oneBookIsbn() {
		Isbn isbn = new Isbn(1, 210208);
		assertEquals("2-210208-02-5", isbn.getIsbnNumber());
	}
	
	@Test
	void lastNumberXIsbn() {
		Isbn isbn = new Isbn(4, 210208);
		assertEquals("2-210208-05-X", isbn.getIsbnNumber());
	}
	
	@Test
	void lastNumber0Isbn() {
		Isbn isbn = new Isbn(12, 210208);
		assertEquals("2-210208-13-0", isbn.getIsbnNumber());
	}
	
	@Test
	void Books7Isbn() {
		Isbn isbn = new Isbn(7, 210208);
		assertEquals("2-210208-08-4", isbn.getIsbnNumber());
	}

	@Test
	void twoNumbersIsbn() {	
		Isbn isbn = new Isbn(9, 210208);
		assertEquals("2-210208-10-6", isbn.getIsbnNumber());
	}
	
	@Test
	void setIsbnNumber() {
		Isbn isbn = new Isbn(0, 210208);
		isbn.setIsbnNumber("2-210208-02-5");
		assertEquals("2-210208-02-5", isbn.getIsbnNumber());
	}
	
	@Test
	void checkFormatGoodCase() {
		StringBuilder msgError = new StringBuilder();
		Isbn isbn = new Isbn(0, 210208);
		
		assertTrue(isbn.checkIsbnNumber(msgError));
	}
	
	@Test
	void checkFormatGoodCaseX() {
		StringBuilder msgError = new StringBuilder();
		Isbn isbn = new Isbn(4, 210208);
		
		assertTrue(isbn.checkIsbnNumber(msgError));
	}
	
	@Test
	void checkFormatGoodCase0() {
		StringBuilder msgError = new StringBuilder();
		Isbn isbn = new Isbn(12, 210208);
		
		assertTrue(isbn.checkIsbnNumber(msgError));
	}
	
	@Test
	void checkIsbnEmptyError() {	
		Isbn isbn = new Isbn("");
		StringBuilder builder = new StringBuilder();
		isbn.checkIsbnNumber(builder);
		assertEquals("Numéro Isbn non valide \n", builder.toString());
	}
	
	@Test
	void checkIsbnFormatError() {	
		Isbn isbn = new Isbn("2-56");
		StringBuilder builder = new StringBuilder();
		isbn.checkIsbnNumber(builder);
		assertEquals("Format Isbn non valide \n", builder.toString());
	}
	
	@Test
	void equalsTrue() {
		Isbn isbn = new Isbn(0, 210208);
		Isbn isbn2 = new Isbn(0, 210208);
		
		assertTrue(isbn.equals(isbn2));
	}
	
	@Test
	void equalsTrueSameObject() {
		Isbn isbn = new Isbn(0, 210208);
		
		assertTrue(isbn.equals(isbn));
	}
	
	@Test
	void equalsFalse() {
		Isbn isbn = new Isbn(0, 210208);
		Isbn isbn2 = new Isbn(0, 176598);
		
		assertFalse(isbn.equals(isbn2));
	}
	
	@Test
	void equalsFalseCausedByNull() {
		Isbn isbn = new Isbn(0, 210208);
		Isbn isbn2 = null;
		
		assertFalse(isbn.equals(isbn2));
	}
	
	@Test
	void equalsFalseCausedByOtherClass() {
		Isbn isbn = new Isbn(0, 210208);
		Author author = new Author("Francis", "Ma", 210208);
		
		assertFalse(isbn.equals(author));
	}
	
	
	@Test
	void hashCodeSame() {
		Isbn isbn = new Isbn(0, 210208);
		Isbn isbn2 = new Isbn(0, 210208);
		
		assertTrue(isbn.hashCode() == isbn2.hashCode());
	}
		
	@Test
	void hashCodeNotSame() {
		Isbn isbn = new Isbn(0, 210208);
		Isbn isbn2 = new Isbn(0, 176598);
		
		assertFalse(isbn.hashCode() == isbn2.hashCode());
	}
}
