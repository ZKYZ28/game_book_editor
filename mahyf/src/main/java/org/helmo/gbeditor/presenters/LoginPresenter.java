package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.models.Author;
import org.helmo.gbeditor.presenters.interfaceview.LoginViewInterface;
import org.helmo.gbeditor.repositories.StorageException;
import org.helmo.gbeditor.repositories.StorageInterface;

/**
 * LoginPresenter qui est le présenter de la LoginView
 * @author franc
 *
 */
public class LoginPresenter implements PresenterInterface{
	private final MainPresenter mainPresenter;
	private final StorageInterface storage;
	
	
	/** 
	 * Constructeur du LoginPresenter
	 * @param loginView LoginView qui est la vue du login
	 * @param storage StorageInterface qui est l'interface qui permet d'intéragir avec le système de stockage
	 * @param mainPresenter MainPresenter qui est le presenter principal
	 */
	public LoginPresenter(final LoginViewInterface loginView, StorageInterface storage, MainPresenter mainPresenter) {
		loginView.setPresenter(this);	
		this.storage = storage;
		this.mainPresenter = mainPresenter;
	}
	
	
	 
	/**
	 * Méthode permattant d'enregistrer un Author si les données fournies sont valides
	 * @param author ModelViewAuthor qui est le modelView de l'auteur
	 */
	public void logUser(String firstName, String name, String matricule) {
		try {
			StringBuilder msgError = new StringBuilder();
			if(new Author().checkValidityUser(firstName, name, matricule, msgError)) {
				Author authorModel = new Author(firstName, name, Integer.parseInt(matricule));	
				storage.saveAuthor(authorModel);
				saveAuthor(authorModel);
			}
			mainPresenter.displayInfo(msgError.toString());
		}catch(StorageException e) {
			mainPresenter.displayInfo("Erreur lors de la sauvegarde de l'auteur. " + e.getMessage());
		}		
	}
	
	/**
	 * Méthode qui permet d'enregistrer l'auteur
	 * @param author Author qui est l'auteur que je souhaite enregistrer
	 */
	private void saveAuthor(Author author) {
		try {
			storage.setMatriculeAuthor(author.getMatricule());
			storage.loadLibrabyBooks();
			mainPresenter.setAuthor(author);
			mainPresenter.switchToMainView();
		} catch (StorageException e) {
			mainPresenter.displayInfo("Erreur lors de la récupération des livres liés à cet auteur");
		}
	}
}
