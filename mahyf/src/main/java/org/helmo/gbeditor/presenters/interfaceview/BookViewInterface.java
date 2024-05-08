package org.helmo.gbeditor.presenters.interfaceview;

import org.helmo.gbeditor.presenters.BookPresenter;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.views.ViewInterface;

/**
 * Interface de la BookView
 * @author franc
 *
 */
public interface BookViewInterface extends ViewInterface{

	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue 
	 * @param p BookPresenter qui est le présenter de BookView
	 */
	public void setPresenter(BookPresenter p);
	
	/**
	 * Méthode qui permet d'afficher les 
	 * informations de base d'un livre
	 */
	public void updateBookInformation(ModelViewBook modelViewBook);
		
	/**
	 * Méthode qui permet d'afficher le titre du livre
	 */
	public void displayTitle(String title);
		
	/***
	 * Méthode qui permet d'afficher un bouton qui 
	 * permet d'éditer d'un livre
	 */
	public void displayEditButton();
	
	/**
	 * Méthode qui permet d'afficher un message
	 * qui indique que le livre est publié
	 */
	public void displayIsPublishedText();
	
}
