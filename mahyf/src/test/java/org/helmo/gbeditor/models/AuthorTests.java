package org.helmo.gbeditor.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AuthorTests {

	@Test
	void authorGoodCase() {
		Author author = new Author("Francois", "Mahy", 667667);
		
		assertEquals("Francois", author.getFirstName());
		assertEquals("Mahy", author.getName());
	}
	
	@Test
	void authorNull() {
		Author author = new Author(null, null, 667667);
		
		assertEquals("Auteur", author.getFirstName());
		assertEquals("Anonyme", author.getName());
	}
	
	@Test
	void authorGetFullName() {
		Author author = new Author("Francois", "Mahy", 667667);
		String authorFullName = author.getAuthorFullName();
		
		assertEquals("Francois Mahy", authorFullName);
	}
	
	@Test
	void authorEmptyError() {
		Author author = new Author();
		StringBuilder builder = new StringBuilder();
		
		assertFalse(author.checkValidityUser("", "Mahy", "667667", builder));
		assertEquals("Veuillez remplir tous les champs \n", builder.toString());	
	}
	
	@Test
	void authorFirstNameError() {
		Author author = new Author();
		StringBuilder builder = new StringBuilder();		
		
		assertFalse(author.checkValidityUser("86668", "Mahy", "667667", builder));
		assertEquals("Prénom non valide\n", builder.toString());	
	}
	
	@Test
	void authorNameError() {
		Author author = new Author();
		StringBuilder builder = new StringBuilder();
		
		assertFalse(author.checkValidityUser("Francis", "4868", "667667", builder));
		assertEquals("Nom non valide\n", builder.toString());	
	}
	
	@Test
	void authorMatriculeError() {
		Author author = new Author();
		StringBuilder builder = new StringBuilder();		
		
		assertFalse(author.checkValidityUser("Francis", "Mahy", "6676", builder));
		assertEquals("Matricule non valide, 6 chiffres \n", builder.toString());	
	}
	
	@Test
	void checkValidityUserGoodCase() {
		Author author = new Author();
		StringBuilder builder = new StringBuilder();		
		
		assertTrue(author.checkValidityUser("Francis", "Mahy", "210208", builder));
		assertEquals("", builder.toString());
	}

}
