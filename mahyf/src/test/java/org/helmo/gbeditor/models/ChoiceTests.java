package org.helmo.gbeditor.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChoiceTests {

	@Test
	void allGetChoie() {
		Choice choice = new Choice(1, "TEXT", 1, 2);
		
		assertEquals(1, choice.getNumChoice());
		assertEquals("TEXT", choice.getTextChoice());
		assertEquals(1, choice.getNumFromPage());
		assertEquals(2, choice.getNumGoToPage());
	}
	
	@Test
	void downNumChoice() {
		Choice choice = new Choice(3, "TEXT", 1, 2);
		choice.downNumChoice();
		assertEquals(2, choice.getNumChoice());
	}
	
	@Test
	void allSetChoice() {
		Choice choice = new Choice(3, "TEXT", 1, 2);
		choice.setNumFromPage(2);
		choice.setNumGoToPage(4);
		
		assertEquals(2, choice.getNumFromPage());
		assertEquals(4, choice.getNumGoToPage());
	}
	
	@Test
	void checkChoiceValidityGoodCase() {		
		StringBuilder builder = new StringBuilder();
		Choice choice = new Choice();
			
		assertTrue(choice.checkChoiceValidity("SUPER", "2", 1, builder));	
		assertEquals("", builder.toString());		
	}
	
	@Test
	void currentPageIsZero() {		
		StringBuilder builder = new StringBuilder();
		Choice choice = new Choice();
		
		assertFalse(choice.checkChoiceValidity("BLA", "2", 0, builder));
		assertEquals("Veuillez sélectionner la page à laquelle vous voulez ajouter le choix \n", builder.toString());		
	}
	
	@Test
	void emptyField() {		
		StringBuilder builder = new StringBuilder();
		Choice choice = new Choice();
		
		assertFalse(choice.checkChoiceValidity("BLA", "", 2, builder));
		assertEquals("Veuillez remplir tous les champs \n", builder.toString());		
	}

	@Test
	void badFormatNumGoPage() {		
		StringBuilder builder = new StringBuilder();
		Choice choice = new Choice();
		
		
		assertFalse(choice.checkChoiceValidity("BLA", "5a", 2, builder));
		assertEquals("Envoie à la page : doit uniqument contenir des chiffres\n", builder.toString());		
	}
	
	@Test
	void samePageInChoice() {		
		StringBuilder builder = new StringBuilder();
		Choice choice = new Choice();
				
		assertFalse(choice.checkChoiceValidity("BLA", "5", 5, builder));
		assertEquals("Une page ne peut pas avoir un choix qui la référence elle-même\n", builder.toString());		
	}
	
	
}
