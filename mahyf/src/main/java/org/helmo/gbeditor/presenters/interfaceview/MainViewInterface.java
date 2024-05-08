package org.helmo.gbeditor.presenters.interfaceview;

import org.helmo.gbeditor.presenters.MainPresenter;
import org.helmo.gbeditor.presenters.modelview.ModelViewAuthor;
import org.helmo.gbeditor.views.ViewInterface;

/**
 * Interface de la MainView
 * @author franc
 *
 */
public interface MainViewInterface extends ViewInterface{

	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue
	 * @param mainPresenter qui est le présenter de la MainView
	 */
	public void setPresenter(MainPresenter mainPresenter);
		
	/**
	 * Méthode qui permet d'afficher quelque chose à l'utilisateur
	 * @param info String qui est ce que l'on souhaite afficher � l'utilisateur
	 */
	public void displayInfo(String info);
	
	/**
	 * Méthode qui permet de mettre � jour le nom complet de l'auteur dans le header et dans le CreaNewBookView
	 * @param authorFullName
	 */
	public void upDateAuthor(ModelViewAuthor author);
	
	/**
	 * Méthode qui permet de changer de vue 
	 * @param pageName String qui est le nom de la page vers laquelle on veut aller
	 * @param pageTitle String qui est le titre de la page vers laquelle on veut aller
	 */
	public void goTo(String pageName,String pageTitle);

}
