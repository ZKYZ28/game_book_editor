package org.helmo.gbeditor.presenters.interfaceview;

import java.util.List;

import org.helmo.gbeditor.presenters.EditBookPresenter;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.presenters.modelview.ModelViewChoice;
import org.helmo.gbeditor.presenters.modelview.ModelViewPage;
import org.helmo.gbeditor.views.ViewInterface;

/**
 * Interface de la EditBookView
 * @author franc
 *
 */
public interface EditBookViewInterface extends ViewInterface{
	
	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue 
	 * @param editBookPresenter le présenter de la editBookView
	 */
	public void setPresenter(EditBookPresenter editBookPresenter);
	
	/**
	 * Méthode qui permet d'afficher un message relatif 
	 * à la création d'un nouvelle page
	 */
	public void displayMessageNewPage(String msgError);
	
	/**
	 * Message qui permet d'afficher que 
	 * aucun choix n'est encore lié à la page
	 */
	public void displayNoChoiceFound(String msg);
	
	/**
	 * Méthode qui permet d'afficher un message relatif 
	 * à la création d'un nouveau choix
	 */
	public void displayMessageNewChoice(String msg);
	
	/**
	 * Méthode qui permet de mettre à jour les informations affichées
	 * @param book
	 */
	public void updateDisplayInformation(ModelViewBook book);
	
	/**
	 * Méthode qui permet de clear la liste des choix affichés
	 */
	public void clearDispayChoice();
	
	/**
	 * Méthode qui permet de mettre à jours les pages affichées
	 */
	public void updateDisplayPages(List<ModelViewPage> listPage);
	
	/**
	 * Méthode qui permet de mettre à jours les choix affichés
	 */
	public void updateDisplayChoice(List<ModelViewChoice> listChoice);
	
	/**
	 * Méthode qui permet d'afficher une pop qui demande la confirmation 
	 * pour la suppression d'un page
	 */
	public void displayPopUpDeletePage(int nbrChoiceLink);
	
	/**
	 * Méthode qui permet de reset les champs liés 
	 * à la création d'une page
	 */
	public void resetDisplayInfoNewPage();
	
	/**
	 * Méthode qui réinitialiser la partie qui s'occuper d'afficher les choix
	 * à la création d'un nouveau choix
	 */
	public void resetDisplayInfoNewChoice();
	
	/**
	 * Méthode qui permet d'afficher que aucune page n'a été trouvée
	 * @param msg String le message que l'on veut afficher
	 */
	public void displayNoPageFound(String msg);
	
	/**
	 * Méthode qui permet de clear la liste des pages affichées
	 */
	public void clearDisplayPage();
}
