package org.helmo.gbeditor.models;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class BookTests {

	@Test
	void createBook() {	
		 Book book = new Book(new Author("Francis", "Plad", 210208), "TITRE", new Isbn(5, 210208), "Resume", false, null);
		 
		 assertEquals("Francis", book.getAuthor().getFirstName());
		 assertEquals("Plad", book.getAuthor().getName());
		 assertEquals("TITRE", book.getTitle());
		 assertEquals("Resume", book.getResume());
		 assertEquals(0, book.getListPage().size());
	} 
	
	@Test
	void publishBook() {
		 Book book = new Book(new Author("Francis", "Plad", 210208), "TITRE", new Isbn(5, 210208), "Resume", false, null);
		 book.publishBook();
		 
		 assertTrue(book.isBookPublished());
	}
	
	@Test
	void getCurrentPage() {
		Page firstPage = new Page(1, "TEXTPAGE");
		Book book = new Book(new Author("Francis", "Plad", 210208), "TITRE", new Isbn(5, 210208), "Resume", false, null);
		book.addPage(firstPage);
		
		assertEquals(firstPage, book.getCurrentPage(0));	
	}
	
	@Test
	void updateBookInforamtion() {
		Book book = new Book(new Author("Francis", "Plad", 210208), "title", new Isbn(5, 210208), "Resume", false, null);
		book.updateBookInforamtion("TITLE", "2-210208-02-5", "RESUME");
		
		assertEquals("TITLE", book.getTitle());	
		assertEquals("2-210208-02-5", book.getIsbn().getIsbnNumber());	
		assertEquals("RESUME", book.getResume());	
	}
	
	@Test
	void checkBookGoodCase() {
		StringBuilder msgError = new StringBuilder();
		assertTrue(new Book().checkBook("Title", new Isbn("2-210208-01-7"), "Resume", msgError));
		assertEquals("", msgError.toString());
	}
	
	@Test
	void book2FieldsEmptyError() {	
		 StringBuilder builder = new StringBuilder();		 
		 
		 assertFalse(new Book().checkBook("", new Isbn("2-210208-01-7"), "", builder));
		 assertEquals("Veuillez remplir tous les champs. \n", builder.toString());
	}
	
	@Test
	void book1FieldEmptyError() {	
		 StringBuilder builder = new StringBuilder();		 
		 
		 assertFalse(new Book().checkBook("Title", new Isbn("2-210208-01-7"), "", builder));
		 assertEquals("Veuillez remplir tous les champs. \n", builder.toString());
	}
	
	@Test
	void tooLargeTitleError() {	
		 StringBuilder builder = new StringBuilder();		 
		 
		 assertFalse(new Book().checkBook("TitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitle", new Isbn("2-210208-01-7"), "resume", builder));
		 assertEquals("Maximum 150 caractères pour le titre. \n", builder.toString());
	}
	
	@Test
	void tooLargeResumeError() {	
		 StringBuilder builder = new StringBuilder();		 
		 
		 assertFalse(new Book().checkBook("title", new Isbn("2-210208-01-7"), "ResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResumeResume", builder));
		 assertEquals("Maximum 500 caractères pour la description. \n", builder.toString());
	}
	
	@Test
	void firstPageGoodCase() {
		StringBuilder builder = new StringBuilder();	
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 210208), "Resume", false, null);
		
		assertTrue(book.managerInsertPage(1, "Texte", builder));
		assertEquals(1, book.getListPage().size());
	}
	
	@Test
	void numPageOneButNotFirst() {
		StringBuilder builder = new StringBuilder();	
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 210208), "Resume", false, null);
		
		assertFalse(book.managerInsertPage(2, "Texte", builder));
		assertEquals("La première page doit porter le numéro 1 \n", builder.toString());
	}
	
	@Test
	void isSideOfAnotherPageGoodCase() {
		StringBuilder builder = new StringBuilder();
		Page firstPage = new Page(1, "TEXTPAGE");
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);
		book.addPage(firstPage);
		
		assertTrue(book.managerInsertPage(2, "Texte", builder));
		assertEquals("", builder.toString());
		assertEquals(2, book.getListPage().size());
	}
	
	@Test
	void isSideOfAnotherPageError() {
		StringBuilder builder = new StringBuilder();
		Page firstPage = new Page(1, "TEXTPAGE");
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);
		book.addPage(firstPage);
		
		assertFalse(book.managerInsertPage(3, "Texte", builder));
		assertEquals("La page doit se trouver avant ou après une page existante \n", builder.toString());
	}
	
	@Test
	void checkIfChoiceLinkToPageFalse() {
		Page firstPage = new Page(1, "TEXTPAGE");
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);
		book.addPage(firstPage);
		
		assertEquals(0, book.getNbrLinkedChoice(1));
		assertFalse(book.checkIfChoiceLinkToPage(1));
	}
	
	@Test
	void checkIfChoiceLinkToPageTrue() {
		Page firstPage = new Page(1, "TEXTPAGE");
		Page secondPage = new Page(1, "TEXTPAGE");
		secondPage.addNewChoiceToPage(new Choice(1, "choice", 2, 1));
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);
		book.addPage(firstPage);
		book.addPage(secondPage);
		
		assertEquals(1, book.getNbrLinkedChoice(1));
		assertTrue(book.checkIfChoiceLinkToPage(1));
	}
	
	@Test
	void checkIfChoiceLinkToPageTrueMultipleChoices() {
		Page firstPage = new Page(1, "TEXTPAGE");
		Page secondPage = new Page(2, "TEXTPAGE");
		secondPage.addNewChoiceToPage(new Choice(1, "choice", 2, 1));
		secondPage.addNewChoiceToPage(new Choice(1, "choice", 2, 6));
		
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);
		book.addPage(firstPage);
		book.addPage(secondPage);
		
		assertEquals(1, book.getNbrLinkedChoice(1));
		assertTrue(book.checkIfChoiceLinkToPage(1));
	}
	
	@Test
	void deletePagelast() {
		Page firstPage = new Page(1, "TEXTPAGE");
		Page secondPage = new Page(2, "TEXTPAGE");
		Page thirdPage = new Page(3, "TEXTPAGE");
		
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);
		book.addPage(firstPage);
		book.addPage(secondPage);
		book.addPage(thirdPage);
		
		book.deletePage(2);

		assertEquals(2, book.getListPage().size());
		assertEquals(1, book.getListPage().get(0).getNumPage());
		assertEquals(2, book.getListPage().get(1).getNumPage());
	}
	
	@Test
	void deletePageFirst() {
		Page firstPage = new Page(1, "TEXTPAGE");
		Page secondPage = new Page(2, "TEXTPAGE");
		Page thirdPage = new Page(3, "TEXTPAGE");
		
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);
		book.addPage(firstPage);
		book.addPage(secondPage);
		book.addPage(thirdPage);
		
		book.deletePage(0);

		assertEquals(2, book.getListPage().size());
		assertEquals(1, book.getListPage().get(0).getNumPage());
		assertEquals(2, book.getListPage().get(1).getNumPage());
	}
	
	@Test
	void checkIfPageExistTrue() {
		StringBuilder builder = new StringBuilder();
		Page firstPage = new Page(1, "TEXTPAGE");		
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);
		book.addPage(firstPage);
		
		assertTrue(book.checkIfPageExist(1, builder));
		assertEquals("", builder.toString());
	}
	
	@Test
	void checkIfPageExistFalse() {
		StringBuilder builder = new StringBuilder();
		Page firstPage = new Page(1, "TEXTPAGE");		
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);
		book.addPage(firstPage);
		
		assertFalse(book.checkIfPageExist(2, builder));
		assertEquals("La page vers laquelle le choix pointe n'existe pas. \n", builder.toString());
	}
	
	@Test
	void checkIfBookCanBePublishedTrue() {
		Page firstPage = new Page(1, "TEXTPAGE");		
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);
		book.addPage(firstPage);
		
		assertTrue(book.checkIfBookCanBePublished());
	}
	
	@Test
	void checkIfBookCanBePublishedFalse() {
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);		
		assertFalse(book.checkIfBookCanBePublished());
	}
	
	@Test
	void hashCodeSame() {
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);		
		Book book2 = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);		
		assertEquals(book.hashCode(), book2.hashCode());
	}
	
	@Test
	void hashCodeNotSame() {
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);		
		Book book2 = new Book(new Author("Francis", "Plad", 210208), "TITRE", new Isbn(5, 210208), "Resume", false, null);		
		assertNotEquals(book.hashCode(), book2.hashCode());
	}
	
	@Test
	void equalsSameObject() {
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);		
		
		assertTrue(book.equals(book));
	}
	
	@Test
	void equalsNullValue() {
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);		
		
		assertFalse(book.equals(null));
	}
	
	@Test
	void equalsOtherTypeObject() {
		Book book = new Book(new Author("Francis", "Plad", 667667), "TITRE", new Isbn(5, 667667), "Resume", false, null);		
		Choice choice = new Choice(1, "BLA", 1, 2);
		assertFalse(book.equals(choice));
	}
	
	@Test
	void equalsTrue() {
		Book book = new Book(new Author("Francis", "Plad", 210208), "TITRE", new Isbn(5, 210208), "Resume", false, null);		
		Book book2 = new Book(new Author("Jean", "Deupont", 210208), "TITRE", new Isbn(5, 210208), "Resume", false, null);		
		assertTrue(book.equals(book2));
	}
}
