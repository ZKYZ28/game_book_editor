package org.helmo.gbeditor.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PageTests {

	@Test
	void pageConstructeurBase() {
		Page page = new Page(1, "TEXT");
		
		assertEquals(1, page.getNumPage());
		assertEquals("TEXT", page.getTextPage());
	}
	
	@Test
	void pageConstructeurWithChoie() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 1, 2);
		choiceList.add(choice);
		Page page = new Page(1, "TEXT", choiceList);
				
		assertEquals(1, page.getNumPage());
		assertEquals("TEXT", page.getTextPage());
		assertEquals(choiceList, page.getChoiceList());
	}
	
	
	@Test
	void checkPageValidityGoodCase() {		
		StringBuilder builder = new StringBuilder();
		Page page = new Page();
			
		assertTrue(page.checkPageValidity("1", "TEXT", builder));	
		assertEquals("", builder.toString());
	}
	
	@Test
	void emptyField() {		
		StringBuilder builder = new StringBuilder();
		Page page = new Page();
			
		assertFalse(page.checkPageValidity("", "TEXT", builder));
		assertEquals("Veuillez remplir tous les champs \n", builder.toString());		
	}	
	
	@Test
	void numPageBadFormat() {		
		StringBuilder builder = new StringBuilder();
		Page page = new Page();
			
		assertFalse(page.checkPageValidity("1a", "TEXT", builder));
		assertEquals("Numéro de page non valide\n", builder.toString());		
	}
	
	@Test
	void addChoiceToPage() {
		Choice choice = new Choice(1, "BLA", 1, 2);
		Page page = new Page(1, "TEXT");
		
		page.addNewChoiceToPage(choice);
				
		assertEquals(1, page.getChoiceList().size());
	}
	
	@Test
	void upNumPage() {
		Page page = new Page(1, "TEXT");
		page.upNumPage();
		
		assertEquals(2, page.getNumPage());
	}
	
	@Test
	void downNumPage() {
		Page page = new Page(1, "TEXT");
		page.downNumPage();
		
		assertEquals(0, page.getNumPage());
	}
	
	@Test
	void updateChoiceUp() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 1, 2);
		Choice choice2 = new Choice(1, "BLA", 3, 4);
		Choice choice3 = new Choice(1, "BLA", 2, 7);
		Choice choice4 = new Choice(1, "BLA", 3, 1);
		
		choiceList.add(choice);
		choiceList.add(choice2);
		choiceList.add(choice3);
		choiceList.add(choice4);
		
		Page page = new Page(1, "TEXT", choiceList);
		page.updateChoiceNumPage(2, 1);		
		
		assertEquals(1, choice.getNumFromPage());
		assertEquals(3, choice.getNumGoToPage());
		
		assertEquals(4, choice2.getNumFromPage());
		assertEquals(5, choice2.getNumGoToPage());
		
		assertEquals(3, choice3.getNumFromPage());
		assertEquals(8, choice3.getNumGoToPage());
		
		assertEquals(4, choice4.getNumFromPage());
		assertEquals(1, choice4.getNumGoToPage());
	}
	
	@Test
	void updateChoiceDown() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 2, 3);
		Choice choice2 = new Choice(1, "BLA", 1, 4);
		Choice choice3 = new Choice(1, "BLA", 2, 7);
		Choice choice4 = new Choice(1, "BLA", 3, 1);
		
		choiceList.add(choice);
		choiceList.add(choice2);
		choiceList.add(choice3);
		choiceList.add(choice4);
		
		Page page = new Page(1, "TEXT", choiceList);
		page.updateChoiceNumPage(2, -1);		
		
		assertEquals(1, choice.getNumFromPage());
		assertEquals(2, choice.getNumGoToPage());
		
		assertEquals(1, choice2.getNumFromPage());
		assertEquals(3, choice2.getNumGoToPage());
		
		assertEquals(1, choice3.getNumFromPage());
		assertEquals(6, choice3.getNumGoToPage());
		
		assertEquals(2, choice4.getNumFromPage());
		assertEquals(1, choice4.getNumGoToPage());
	}
	
	@Test
	void removeChoicesThatPointOneChoice() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 2, 3);
		Choice choice2 = new Choice(1, "BLA", 1, 4);
		Choice choice3 = new Choice(1, "BLA", 2, 7);
		Choice choice4 = new Choice(1, "BLA", 3, 1);
		
		choiceList.add(choice);
		choiceList.add(choice2);
		choiceList.add(choice3);
		choiceList.add(choice4);
		
		Page page = new Page(1, "TEXT", choiceList);
		page.deleteChoice(4);
		
		assertEquals(3, page.getChoiceList().size());
	} 
	
	@Test
	void removeChoicesThatPointMultipleChoice() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 1, 2);
		Choice choice2 = new Choice(1, "BLA", 5, 2);
		Choice choice3 = new Choice(1, "BLA", 3, 4);
		Choice choice4 = new Choice(1, "BLA", 3, 2);
		
		choiceList.add(choice);
		choiceList.add(choice2);
		choiceList.add(choice3);
		choiceList.add(choice4);
		
		Page page = new Page(1, "TEXT", choiceList);
		page.deleteChoiceIfPointToDeletePage(2);
		
		assertEquals(1, page.getChoiceList().size());
	} 
	
	@Test
	void numberOfLinkedChoicesZero() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 1, 2);
		Choice choice2 = new Choice(1, "BLA", 5, 2);
		Choice choice3 = new Choice(1, "BLA", 3, 4);
		Choice choice4 = new Choice(1, "BLA", 3, 2);
		
		choiceList.add(choice);
		choiceList.add(choice2);
		choiceList.add(choice3);
		choiceList.add(choice4);
		
		Page page = new Page(1, "TEXT", choiceList);
			
		assertEquals(0, page.countNumberOfChoiceLinkToPage(5));
	} 
	
	@Test
	void numberOfLinkedChoicesOne() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 1, 2);
		Choice choice2 = new Choice(1, "BLA", 5, 2);
		Choice choice3 = new Choice(1, "BLA", 3, 4);
		Choice choice4 = new Choice(1, "BLA", 3, 2);
		
		choiceList.add(choice);
		choiceList.add(choice2);
		choiceList.add(choice3);
		choiceList.add(choice4);
		
		Page page = new Page(1, "TEXT", choiceList);
			
		assertEquals(1, page.countNumberOfChoiceLinkToPage(4));
	} 
	
	@Test
	void numberOfLinkedChoicesMultiple() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 1, 2);
		Choice choice2 = new Choice(1, "BLA", 5, 2);
		Choice choice3 = new Choice(1, "BLA", 3, 4);
		Choice choice4 = new Choice(1, "BLA", 3, 2);
		
		choiceList.add(choice);
		choiceList.add(choice2);
		choiceList.add(choice3);
		choiceList.add(choice4);
		
		Page page = new Page(1, "TEXT", choiceList);
			
		assertEquals(3, page.countNumberOfChoiceLinkToPage(2));
	} 
	
	@Test
	void deleteChoiceFirst() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 1, 2);		
		choiceList.add(choice);
		
		Page page = new Page(1, "TEXT", choiceList);
		page.deleteChoice(1);
		
		assertEquals(0, page.getChoiceList().size());
	} 

	@Test
	void deleteChoiceMidle() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 1, 2);		
		Choice choice2 = new Choice(2, "BLA", 5, 2);
		Choice choice3 = new Choice(3, "BLA", 3, 4);
		Choice choice4 = new Choice(4, "BLA", 3, 2);
		
		choiceList.add(choice);
		choiceList.add(choice2);
		choiceList.add(choice3);
		choiceList.add(choice4);

		
		Page page = new Page(1, "TEXT", choiceList);
		page.deleteChoice(2);
		
		assertEquals(3, page.getChoiceList().size());
		
		assertEquals(2, choice3.getNumChoice());
		assertEquals(3, choice4.getNumChoice());
	} 
	
	@Test
	void deleteChoiceLast() {
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice(1, "BLA", 1, 2);		
		Choice choice2 = new Choice(2, "BLA", 5, 2);
		Choice choice3 = new Choice(3, "BLA", 3, 4);
		Choice choice4 = new Choice(4, "BLA", 3, 2);
		
		choiceList.add(choice);
		choiceList.add(choice2);
		choiceList.add(choice3);
		choiceList.add(choice4);

		
		Page page = new Page(1, "TEXT", choiceList);
		page.deleteChoice(4);
		
		assertEquals(3, page.getChoiceList().size());
		
		assertEquals(1, choice.getNumChoice());
		assertEquals(2, choice2.getNumChoice());
		assertEquals(3, choice3.getNumChoice());
	} 
	
	@Test
	void compareToSameNum() {
		Page page = new Page(1, "TEXT");
		Page page2 = new Page(1, "TEXT2");
		
		assertEquals(0, page.compareTo(page2));
	}
	
	@Test
	void compareToNotSameNum() {
		Page page = new Page(1, "TEXT");
		Page page2 = new Page(2, "TEXT2");
		
		assertEquals(-1, page.compareTo(page2));
	}
	
	@Test
	void compareToNull() {
		Page page = new Page(1, "TEXT");
		Page page2 = null;
		
		assertEquals(-2147483648, page.compareTo(page2));
	}
}
