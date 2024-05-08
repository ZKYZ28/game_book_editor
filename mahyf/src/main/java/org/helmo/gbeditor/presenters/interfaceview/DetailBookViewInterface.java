package org.helmo.gbeditor.presenters.interfaceview;

import org.helmo.gbeditor.presenters.DetailBookPresenter;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.views.ViewInterface;

/**
 * Interface de la vue DetailBookView
 * @author franc
 *
 */
public interface DetailBookViewInterface extends ViewInterface{
	
	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue 
	 * @param p qui est le présenter de la DetailBookView
	 */
	public void setPresenter(DetailBookPresenter p);
	
	/**
	 * Méthode utilisée par la MainView pour mettre les infos 
	 * de la DetailBookView lors du changement de fenêtre
	 * @param book ModelViewBook livre dont on veut afficher les détails
	 */
	public void updateDetailBookView(ModelViewBook book);
	
	/**
	 * Méthode qui permet d'afficher un message qui indique 
	 * que le livre ne contient aucune page
	 */
	public void displayNoPageMessage();
	
	/**
	 * Méthode qui permet d'afficher toutes les pages
	 * d'un livre
	 */
	public void displayAllPage(ModelViewBook book);
	
}
