package org.helmo.gbeditor.repositories;

import java.util.List;

import org.helmo.gbeditor.infrastructures.LibraryBooks;
import org.helmo.gbeditor.models.Author;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Choice;
import org.helmo.gbeditor.models.Page;

/**
 * Interface utilisée pour l'interaction entre les présenters et le storage/libraryBooks
 * @author franc
 *
 */
public interface StorageInterface {
	
	/**
     * Méthode qui permet de modifier le matricule de l'auteur
     * @param matriculeAuthor int qui est le matricule de l'auteur
     */
 	public void setMatriculeAuthor(int matriculeAuthor);

 	
 	/**
	 * Méthode qui permet de récupérer un livre sur base de son numéro isbn
	 * @param isbn String le numéro isbn du livre
	 * @return Book le livre auquel appartient le numéro isbn
	 */
 	public Book getBook(String isbn);
 	
    /**
     * Méthode qui permet de récupérer l'ensemble des livres d'une bibliothèque
     * @return List<Book> l'ensemble des livres d'un bibliothèque
     */
    public List<Book> getListBooks();
 	
    /**
     * Méthode qui permet de d'enregistrer un auteur
     * @param author Authro qui est l'auteur que l'on veut enregistrer
     * @throws StorageException
     */
 	public void saveAuthor(Author author) throws StorageException;
   
    /**
     * Méthode qui permet d'insérer un Book dans la base de données 
     * @param book Book qui est le livre que l'on souhaite ajouter
     * @throws Exception
     */
   public void insertBook(final Book book) throws StorageException;
   
   /**
    * Méthode qui permet d'insérer un Choice dans la base de données
    * @param choice
    * @throws Exception
    */
   public void insertChoice(final Choice choice, final Book book) throws StorageException;
   
   /**
    * Méthode qui permet de mettre à jour les choix existants
    */
   public void updateChoices(int numPage, Book book, List<Choice> choiceList) throws StorageException;
   
	/**
	 * Méthode qui permet de passer un livre comme publié
	 * @param idBook int id du livre
	 * @throws Exception
	 */
   public void updateBookPusblished(Book book) throws StorageException;
   
   /**
    * Méthode qui permet de mettre à jour les informations de base d'un 
    * livre dans la base de données
    * @param idBook int id du livre
    * @param title String tittre du livre
    * @param isbn String numéro isbn du livre
    * @param descirption String description du livre
    * @throws Exception
    */
   public void updateBook(Book book,  String title, String isbn, String descirption) throws StorageException;
   
   /**
    * Méthode qui permet de mettre à jour toutes les pages d'un livre
    * Méthode qui permet de mettre à jour toutes les pages d'un livre 
    * @param idBook int id livre
    * @param numPage int numéro de la page
    * @param listPage SortedSet<Page> les pages du livre
    * @throws Exception
    */
   public void updatePages(Book book)throws StorageException;
   
	/**
	 * Méthode qui permet de lire tous les livres d'un auteur sur base de son matricule
	 * @return Map<Integer, Book> tous les livres d'un auteur
	 * @throws StorageException
	 */
   public void loadLibrabyBooks() throws StorageException;
   
   /**
    * Méthode qui permet de load toutes les pages liées à un livre
    * @param idBook int id du livre
    * @return SortedSet<Page> ensemble des pages du livre
    * @throws Exception
    */
   public List<Page> loadPages(Book book) throws StorageException;
   
   
	/**
	 * Méthode qui permet de mettre à jour un livre dans 
	 * la libraryBooks
	 * @param Book le livre que l'on veut mettre à jour
	 */
   public void updateBookLibrary(Book book);

   /**
    * Méthode qui permet d'assigner la librairie de livre au storage
    * @param librabyBooks LibraryBooks qui est la bibliothèque des livres
    */
   void setLibrabyBooks(LibraryBooks librabyBooks);

   /**
    * Méthode qui permet de savoir si un numéro isbn est déjà utilisé
    * par un autre livre
    * @param isbn String qui est le numéro isbn du livre
    * @return true si le numéro isbn est déjà utilisé sinon false
    * @throws StorageException Exception lancée en cas d'erreur lors de la requête sql
    */
   public boolean checkIfIsbnExist(String isbn) throws StorageException;
	   	   
}
