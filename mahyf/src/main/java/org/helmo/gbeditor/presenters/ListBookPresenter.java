package org.helmo.gbeditor.presenters;

import java.util.List;

import org.helmo.gbeditor.presenters.interfaceview.ListBookViewInterface;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;

/**
 * ListBookPresenter qui est le présenter de la ListBookView
 * @author franc
 *
 */
public class ListBookPresenter implements PresenterInterface{
	private final MainPresenter mainPresenter;
	private final ListBookViewInterface listBookView;
	
	/**
	 * Constructeur du ListBookPresenter
	 * @param listBookView ListBookViewInterface qui est la vue de la liste des livres
	 * @param mainPresenter MainPresenter qui est le presenter principal
	 */
	public ListBookPresenter(ListBookViewInterface listBookView, MainPresenter mainPresenter) {
		listBookView.setPresener(this);
		this.listBookView = listBookView;
		this.mainPresenter = mainPresenter;	
	}
		
	/**
	 * Méthode qui permet de retourner à la page précédente (mainView)
	 */
	public void goBack() {
		listBookView.displayNoBookFoundMessage("");
		mainPresenter.switchToMainView();
	}
	
	/**
	 * Méthode qui permet d'afficher tous les livres 
	 * @param listBookModelView List<ModelViewBook> la liste des livres
	 */
	public void displayAllBook(List<ModelViewBook> listBookModelView) {
		if(listBookModelView.size() == 0) {
			listBookView.displayNoBookFoundMessage("Cette auteur ne possède aucun livre");
		}else {
			listBookView.updateListBook(listBookModelView);
		}
	}
}
