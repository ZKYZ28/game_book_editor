package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.presenters.interfaceview.BookViewInterface;
import org.helmo.gbeditor.presenters.interfaceview.ListBookViewInterface;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;

/**
 * BookPresenter qui est le présenter de la BookView
 * @author franc
 *
 */
public class BookPresenter implements PresenterInterface{
	private final MainPresenter mainPresenter;
	private BookViewInterface bookView;
	
	
	/**
	 * Constructeur du BookPresenter.
	 * @param listBookView Interface de la listBookView
	 * @param mainPresenter MainPresenter qui est le presenter principal
	 */
	public BookPresenter(ListBookViewInterface listBookView, MainPresenter mainPresenter) {
		listBookView.setPresenterBook(this);
		this.mainPresenter = mainPresenter;
	}
	
	/**
	 * Méthode qui permet de donner la BookViewInterface
	 * @param bookView Interface de la BookView
	 */
	public void setView(BookViewInterface bookView) {
		this.bookView = bookView;
	}
	

	/**
	 * Méthode qui permet de d'aller à la DetailBookView.
	 * Les informations complètes du livre vont être chargées. 
	 * @param book ModelViewBook qui est qui est le livre dont on souhaite afficher les détails
	 */
	public void switchToDetailBookView(ModelViewBook book) {		
		mainPresenter.switchToDetailBookView(book);
	}
	
	/**
	 * Méthode qui vérifie si le livre est publié ou non
	 * @param bookModelViewBook le livre qu'on vérifie
	 */
	public void checkIfBookIsPublished(final ModelViewBook book) {
		if(book.isBookPublished()) {
			bookView.displayIsPublishedText();
		}else {
			bookView.displayEditButton();
		}
	}

	/**
	 * Méthode qui vérifie si la taille du titre n'est pas 
	 * trop longue si c'est le cas elle va le raccourcir.
	 * @param title String qui est le titre du livre
	 */
	public void checkIfTitleIsNotTooLong(String title) {
		if((title.length() > 15)) {
			bookView.displayTitle(title.substring(0, 15) + "...");
		}else {
			bookView.displayTitle(title);
		}		
	}
}
