package org.helmo.gbeditor.presenters.interfaceview;

import java.util.List;

import org.helmo.gbeditor.presenters.BookPresenter;
import org.helmo.gbeditor.presenters.ListBookPresenter;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.views.ViewInterface;

/**
 * Interface de la ListBookView
 * @author franc
 *
 */
public interface ListBookViewInterface extends ViewInterface{
	
	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue
	 * @param p qui est le présenter de la ListBookView
	 */
	public void setPresener(ListBookPresenter p);
	
	/**
	 * Méthode qui peremt d'assigner un le presenter à la BookView
	 * @param bookP qui est le présenter de la BookView
	 */
	public void setPresenterBook(BookPresenter bookP);
	
	/**
	 * Méthode qui permet de mettre à jour la liste des livres 
	 * affichées 
	 */
	public void updateListBook(List<ModelViewBook> listBookModelView);
	
	/**
	 * Méthode qui permet d'afficher un message 
	 * pour indiquer qu'aucun livre n'a été trouvé
	 */
	public void displayNoBookFoundMessage(String msg);
	
	/**
	 * Méthode qui permet de demander au présenter le check de la list
	 * des livres
	 */
	public void displayBooks(List<ModelViewBook> listBookModelView);
}
