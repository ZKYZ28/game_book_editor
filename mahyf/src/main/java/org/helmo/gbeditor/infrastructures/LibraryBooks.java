package org.helmo.gbeditor.infrastructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.helmo.gbeditor.models.Book;

/**
 * Bibiliothèque d'un auteur
 * @author franc
 *
 */
public class LibraryBooks {
	private Map<Book, Integer> librabyBooks = new HashMap<Book, Integer>();;
	
	
	/**
	 * Constructeur de base de la LibrabyBooks
	 */
	public LibraryBooks() {}
	
	/**
	 * Méthode qui permet de donner la Map des livres à la bibliothèque
	 * @param librabyBooks Map<Book, Integer> ensemble des livres et des ids
	 */
	public void setLibrabyBooks(Map<Book, Integer> librabyBooks) {
		this.librabyBooks = librabyBooks;
	}
	
	public Map<Book, Integer> getLibrabyBooks(){
		return this.librabyBooks;
	}
	
	/**
	 * Méthode qui permet de retourner un livre sur base de son numéro ISBN
	 * @param isbnNumber String qui est le numéro ISBN
	 * @return
	 */
	public Book getBook(String isbnNumber) {
		Book book = null;
		
		for(Entry<Book, Integer> entry : this.librabyBooks.entrySet()) {
			if(entry.getKey().getIsbn().getIsbnNumber().equals(isbnNumber)) {
				book = entry.getKey();
			}
		}
		return book;
	}
	
	/**
	 * Méthode qui permet d'ajouter un Book
	 * à la librabyBooks. 
	 * @param idBook int : Id du Book que l'on souhaite ajouter
	 * @param book Book : le livre l'on souhaite ajouter
	 */
	public void addBook(final Book book, final int idBook) {
		librabyBooks.put(book, idBook);
	}
	
	/**
	 * Méthode qui permet de mettre à jour un livre dans la map
	 * @param isbnNumber String le numéro isbn du livre que l'on 
	 * souhaite mettre à jour.
	 * @param newBook Book le livre par lequel on veut le remplacer
	 */
	public void updateBook(String isbnNumber, Book newBook) {
		Book book = getBook(isbnNumber);
		int idBook = this.librabyBooks.get(book);	

		this.librabyBooks.remove(book);		
		this.librabyBooks.put(newBook, idBook);
	}
	
}
