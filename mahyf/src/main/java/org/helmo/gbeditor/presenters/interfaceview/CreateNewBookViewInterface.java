package org.helmo.gbeditor.presenters.interfaceview;

import org.helmo.gbeditor.presenters.CreateNewBookPresenter;
import org.helmo.gbeditor.views.ViewInterface;

/**
 * Interface de la CreateNewBookView
 * @author franc
 *
 */
public interface CreateNewBookViewInterface extends ViewInterface{

	/**
	 * Méthode qui peremt d'assigner un les presenter de la vue 
	 * @param p CreateNewBookPresenter qui est le présenter de la createNewBookView
	 */
	public void setPresenter(CreateNewBookPresenter p);
	
	/**
	 * Méthode qui permet de mettre à jour CreateBookView
	 * Le numéro Isbn est mis à jour et les champs présents sont vidés
	 */
	public void updateCreateBookView(String isbn, String authorFullName);
}
