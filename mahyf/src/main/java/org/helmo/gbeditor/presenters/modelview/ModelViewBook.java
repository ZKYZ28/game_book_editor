package org.helmo.gbeditor.presenters.modelview;

import java.util.ArrayList;
import java.util.List;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Page;

/**
 * ModelView d'un livre
 * @author franc
 *
 */
public class ModelViewBook {
	private final Book book;
	private final List<ModelViewPage> listPage;
	
	/**
	 * Constructeur d'un ModelViewBook.
	 * @param book Book qui est le livre que l'on souhaite convertir en ModelViewBook
	 */
	public ModelViewBook(Book book) {
		this.book = book;
		this.listPage = initListPage(book.getListPage());
	}
	
	
	private List<ModelViewPage> initListPage(List<Page> listPageDto) {
		List<ModelViewPage> listPage = new ArrayList<ModelViewPage>();
		
		for(Page page : listPageDto) {
			listPage.add(new ModelViewPage(page));
		}
		return listPage;
	}

	/**
	 * Méthode qui permet de retourner un ModelViewAuthor
	 * @return ModelViewAuthor l'auteur du livre sous forme de ModelView
	 */
	public ModelViewAuthor getAuthor() {
		return new ModelViewAuthor(book.getAuthor());
	}

	/**
	 * Méthode qui permet de retourner le titre du livre
	 * @return String le titre du livre 
	 */
	public String getTitle() {
		return book.getTitle();
	}

	/**
	 * Méthode qui permet de retourner le numéro isbn du livre
	 * @return String le numéro isbn
	 */
	public String getIsbn() {
		return book.getIsbn().getIsbnNumber();
	}

	/**
	 * Méthode qui permet de retourner le résumé du livre
	 * @return String le résumé du livre
	 */
	public String getResume() {
		return book.getResume();
	}

	public List<ModelViewPage> getListPage() {
		return listPage;
	}
	
//	/**
//	 * Méthode qui permet d'obtenir le numéro isbn
//	 * @return String qui est le numéro isbn
//	 */
//	public String getIsbnNumber(){
//		return this.isbn;
//	}
	
	/***
	 * Méthode qui permet de retourner si le livre est publié ou non 
	 * @return True si le livre est publié sinon false
	 */
	public boolean isBookPublished() {
		return this.book.isBookPublished();
	}
}
