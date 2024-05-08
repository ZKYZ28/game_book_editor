package org.helmo.gbeditor.infrastructures;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.helmo.gbeditor.models.Author;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Choice;
import org.helmo.gbeditor.models.Isbn;
import org.helmo.gbeditor.models.Page;
import org.helmo.gbeditor.repositories.StorageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SqlBookStorageTests {

	BookStorageFactory factory = new BookStorageFactory(
			"org.apache.derby.jdbc.EmbeddedDriver",
            "jdbc:derby:derby;create=true;",
            "",
            ""	
    );
	
	
    @BeforeEach
    public void setup() throws Exception {
        try(SqlBookStorage storage = factory.newStorageSession()) {
            try {
            	storage.setup();
                
            } catch(Exception ex) {
           	 	fail();
            }
        }
    }

    @AfterEach
    public void teardown() throws Exception {
        try(SqlBookStorage storage = factory.newStorageSession()) {
            storage.tearDown();
        }
    }
    
    
    @Test
    public void insertBook() {
    	//SETUP BD
    	try(SqlBookStorage storage = factory.newStorageSession()){
    		LibraryBooks librabyBooks = new LibraryBooks();
			storage.setLibrabyBooks(librabyBooks);
			
		//INSERTION AUTEUR	
    		Author author = new Author("Francis", "Mahy", 210208);
    		storage.saveAuthor(author);    	
    		storage.setMatriculeAuthor(210208);
    		
    	//INSERTION LIVRE	
    		Book book = new Book(author, "TITLE", new Isbn("2-210208-01-7"), "RESUME", false, null);   		
    		try {
    			storage.insertBook(book);
    		}catch(StorageException e) {
    			fail();
    		}
       		
    		//CLEAR LIBRARY
    		librabyBooks.getLibrabyBooks().clear();
    		assertEquals(0, storage.getListBooks().size());
    		
    		//LOAD LIBRARY 
    		storage.loadLibrabyBooks();
    		assertEquals(1, storage.getListBooks().size());
    		
    		Book bookL = storage.getBook("2-210208-01-7");
    		
    		//ASSERT
    		assertEquals(book.getAuthor().getAuthorFullName(), bookL.getAuthor().getAuthorFullName());
    		assertEquals(book.getTitle(), bookL.getTitle());
    		assertEquals(book.getIsbn().getIsbnNumber(), bookL.getIsbn().getIsbnNumber());
    		assertEquals(book.getResume(), bookL.getResume());
    		assertEquals(book.isBookPublished(), bookL.isBookPublished());
    		
    	}catch(Exception e) {
    		fail();
    	} 
    }
    
    @Test
    public void insertPage() {
    	//SETUP BD
    	try(SqlBookStorage storage = factory.newStorageSession()){
    		LibraryBooks librabyBooks = new LibraryBooks();
			storage.setLibrabyBooks(librabyBooks);
			
		//INSERTION AUTEUR	
    		Author author = new Author("Francis", "Mahy", 210208);
    		storage.saveAuthor(author);    	
    		storage.setMatriculeAuthor(210208);
    		
    	//INSERTION LIVRE	
    		Book book = new Book(author, "TITLE", new Isbn("2-210208-01-7"), "RESUME", false, null);   		
    		try {
    			storage.insertBook(book);
    		}catch(StorageException e) {
    			fail();
    		}
    		
    		
    		book.addPage(new Page(1, "TEXTPAGE"));
       		storage.updatePages(book);

    		List<Page> listPage = storage.loadPages(book);
    		
    		assertEquals(1, listPage.get(0).getNumPage());
    		assertEquals("TEXTPAGE", listPage.get(0).getTextPage());
    		assertEquals(0, listPage.get(0).getChoiceList().size());
    		
    	}catch(Exception e) {
    		fail();
    	} 
    }
    
    
    @Test
    public void insertPageWithChoice() {
    	//SETUP BD
    	try(SqlBookStorage storage = factory.newStorageSession()){
    		LibraryBooks librabyBooks = new LibraryBooks();
			storage.setLibrabyBooks(librabyBooks);
			
		//INSERTION AUTEUR	
    		Author author = new Author("Francis", "Mahy", 210208);
    		storage.saveAuthor(author);    	
    		storage.setMatriculeAuthor(210208);
    		
    	//INSERTION LIVRE	
    		Book book = new Book(author, "TITLE", new Isbn("2-210208-01-7"), "RESUME", false, null);   		
    		try {
    			storage.insertBook(book);
    		}catch(StorageException e) {
    			fail();
    		}
    		
    		//Insertion de la page et du choix
    		Page page = new Page(1, "TEXTPAGE");
    		page.addNewChoiceToPage(new Choice(1, "TEXTCHOICE", 1, 2));
    		book.addPage(page);
       		storage.updatePages(book);

    		Page pageL = storage.loadPages(book).get(0);
    		Choice choiceL =pageL.getChoiceList().get(0);
    		
    		assertEquals(1, pageL.getChoiceList().size());
    		assertEquals(1, choiceL.getNumChoice());
    		assertEquals("TEXTCHOICE", choiceL.getTextChoice());
    		assertEquals(1, choiceL.getNumFromPage());
    		assertEquals(2, choiceL.getNumGoToPage());
    		
    	}catch(Exception e) {
    		fail();
    	} 
    }
    
    
    @Test
    public void insertChoice() {
    	//SETUP BD
    	try(SqlBookStorage storage = factory.newStorageSession()){
    		LibraryBooks librabyBooks = new LibraryBooks();
			storage.setLibrabyBooks(librabyBooks);
			
		//INSERTION AUTEUR	
    		Author author = new Author("Francis", "Mahy", 210208);
    		storage.saveAuthor(author);    	
    		storage.setMatriculeAuthor(210208);
    		
    	//INSERTION LIVRE	
    		Book book = new Book(author, "TITLE", new Isbn("2-210208-01-7"), "RESUME", false, null);   		
    		try {
    			storage.insertBook(book);
    		}catch(StorageException e) {
    			fail();
    		}    		
    		
    		//INSERTION DE LA PAGE
    		book.addPage(new Page(1, "TEXTPAGE"));
       		storage.updatePages(book);

       		//INSERTION DU CHOIX
    		storage.insertChoice(new Choice(1, "TEXTCHOICE", 1, 2), book);
    		
    		//LOAD
    		List<Page> listPage = storage.loadPages(book);
    		Page pageL = listPage.get(0);
    		Choice choiceL = pageL.getChoiceList().get(0);
    		
    		//ASSERT
    		assertEquals(1, pageL.getChoiceList().size());
    		assertEquals("TEXTCHOICE", choiceL.getTextChoice());
    		assertEquals(1, choiceL.getNumFromPage());
    		assertEquals(2, choiceL.getNumGoToPage());
    		
    	}catch(Exception e) {
    		fail();
    	} 
    }
    
    
    @Test
    public void deletePage() {
    	//SETUP BD
    	try(SqlBookStorage storage = factory.newStorageSession()){
    		LibraryBooks librabyBooks = new LibraryBooks();
			storage.setLibrabyBooks(librabyBooks);
			
		//INSERTION AUTEUR	
    		Author author = new Author("Francis", "Mahy", 210208);
    		storage.saveAuthor(author);    	
    		storage.setMatriculeAuthor(210208);
    		
    	//INSERTION LIVRE	
    		Book book = new Book(author, "TITLE", new Isbn("2-210208-01-7"), "RESUME", false, null);   		
    		try {
    			storage.insertBook(book);
    		}catch(StorageException e) {
    			fail();
    		}
    		
    		
    		book.addPage(new Page(1, "TEXTPAGE"));
       		storage.updatePages(book);

    		List<Page> listPage = storage.loadPages(book);  		
    		assertEquals(1, listPage.get(0).getNumPage());
    		assertEquals("TEXTPAGE", listPage.get(0).getTextPage());
    		assertEquals(0, listPage.get(0).getChoiceList().size());
    		
    		book.deletePage(0);
    		storage.updatePages(book);
    		
    		List<Page> listPageAfterDelete = storage.loadPages(book);  		
    		assertEquals(0, listPageAfterDelete.size());
    		
    	}catch(Exception e) {
    		fail();
    	} 
    }
    
    
    @Test
    public void deleteChoice() {
    	//SETUP BD
    	try(SqlBookStorage storage = factory.newStorageSession()){
    		LibraryBooks librabyBooks = new LibraryBooks();
			storage.setLibrabyBooks(librabyBooks);
			
		//INSERTION AUTEUR	
    		Author author = new Author("Francis", "Mahy", 210208);
    		storage.saveAuthor(author);    	
    		storage.setMatriculeAuthor(210208);
    		
    	//INSERTION LIVRE	
    		Book book = new Book(author, "TITLE", new Isbn("2-210208-01-7"), "RESUME", false, null);   		
    		try {
    			storage.insertBook(book);
    		}catch(StorageException e) {
    			fail();
    		}
    		
    		//Insertion de la page et du choix
    		Page page = new Page(1, "TEXTPAGE");
    		page.addNewChoiceToPage(new Choice(1, "TEXTCHOICE", 1, 2));
    		book.addPage(page);
       		storage.updatePages(book);
       		
       		Page pageL = storage.loadPages(book).get(0);
    		
       		assertEquals(1, pageL.getChoiceList().size());
       		
       		book.getCurrentPage(0).deleteChoice(1);
       		storage.updateChoices(1, book, book.getCurrentPage(0).getChoiceList());
       		
       		Page pageL2 = storage.loadPages(book).get(0);
    		
       		assertEquals(0, pageL2.getChoiceList().size());
       		
    	}catch(Exception e) {
    		fail();
    	} 
    }
    
    
    @Test
    public void updateBookPusblished() {
    	//SETUP BD
    	try(SqlBookStorage storage = factory.newStorageSession()){
    		LibraryBooks librabyBooks = new LibraryBooks();
			storage.setLibrabyBooks(librabyBooks);
			
		//INSERTION AUTEUR	
    		Author author = new Author("Francis", "Mahy", 210208);
    		storage.saveAuthor(author);    	
    		storage.setMatriculeAuthor(210208);
    		
    	//INSERTION LIVRE	
    		Book book = new Book(author, "TITLE", new Isbn("2-210208-01-7"), "RESUME", false, null);   		
    		try {
    			storage.insertBook(book);
    		}catch(StorageException e) {
    			fail();
    		}
       		
    		//CLEAR LIBRARY
    		librabyBooks.getLibrabyBooks().clear();
    		assertEquals(0, storage.getListBooks().size());
    		
    		//LOAD LIBRARY 
    		storage.loadLibrabyBooks();
    		assertEquals(1, storage.getListBooks().size());
    		
    		//Vérification que le livre n'est bien pas publié
    		Book bookNotPublished = storage.getBook("2-210208-01-7");
    		assertFalse(bookNotPublished.isBookPublished());
    	      
    		//Publication du livre
    	    storage.updateBookPusblished(book);
    	    
    	    //CLEAR LIBRARY
    	    librabyBooks.getLibrabyBooks().clear();
    		assertEquals(0, storage.getListBooks().size());
    		
    		//RELOAD DU BOOK PUBLIE
    		storage.loadLibrabyBooks();
    		Book bookPublished = storage.getBook("2-210208-01-7");
    	    assertTrue(bookPublished.isBookPublished());
    		
    	}catch(Exception e) {
    		fail();
    	} 
    }
    
    @Test
    public void updateBook() {
    	//SETUP BD
    	try(SqlBookStorage storage = factory.newStorageSession()){
    		LibraryBooks librabyBooks = new LibraryBooks();
			storage.setLibrabyBooks(librabyBooks);
			
		//INSERTION AUTEUR	
    		Author author = new Author("Francis", "Mahy", 210208);
    		storage.saveAuthor(author);    	
    		storage.setMatriculeAuthor(210208);
    		
    	//INSERTION LIVRE	
    		Book book = new Book(author, "TITLE", new Isbn("2-210208-01-7"), "RESUME", false, null);   		
    		try {
    			storage.insertBook(book);
    			storage.updateBook(book, "titelChanged", "2-210208-02-5", "resumeChanged");
    		}catch(StorageException e) {
    			fail();
    		}
       		
    		//CLEAR LIBRARY
    		librabyBooks.getLibrabyBooks().clear();
    		assertEquals(0, storage.getListBooks().size());
    		
    		//LOAD LIBRARY 
    		storage.loadLibrabyBooks();
    		assertEquals(1, storage.getListBooks().size());
    		
    		Book bookL = storage.getBook("2-210208-02-5");
    		
    		//ASSERT
    		assertEquals("titelChanged", bookL.getTitle());
    		assertEquals("2-210208-02-5", bookL.getIsbn().getIsbnNumber());
    		assertEquals("resumeChanged", bookL.getResume());
    		
    	}catch(Exception e) {
    		fail();
    	} 
    }
    
    
    public void checkIfIsbnExistTrue() {
    	//SETUP BD
    	try(SqlBookStorage storage = factory.newStorageSession()){
    		LibraryBooks librabyBooks = new LibraryBooks();
			storage.setLibrabyBooks(librabyBooks);
			
		//INSERTION AUTEUR	
    		Author author = new Author("Francis", "Mahy", 210208);
    		storage.saveAuthor(author);    	
    		storage.setMatriculeAuthor(210208);
    		
    	//INSERTION LIVRE	
    		Book book = new Book(author, "TITLE", new Isbn("2-210208-01-7"), "RESUME", false, null);   		
    		try {
    			storage.insertBook(book);
    		}catch(StorageException e) {
    			fail();
    		}
       	
    		assertFalse(storage.checkIfIsbnExist("2-210208-01-7"));
    		
    	}catch(Exception e) {
    		fail();
    	} 
    }
    
    @Test
    public void checkIfIsbnExistFalse() {
    	//SETUP BD
    	try(SqlBookStorage storage = factory.newStorageSession()){
    		assertFalse(storage.checkIfIsbnExist("2-210208-01-7"));
    	}catch(Exception e) {
    		fail();
    	} 
    }
}
