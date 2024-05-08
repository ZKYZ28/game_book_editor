package org.helmo.gbeditor.presenters;


import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Isbn;
import org.helmo.gbeditor.presenters.interfaceview.CreateNewBookViewInterface;
import org.helmo.gbeditor.repositories.StorageException;
import org.helmo.gbeditor.repositories.StorageInterface;


/***
 * CreateNewBookPresenter qui est le présenter de la CreateNewBookView
 * @author franc
 *
 */
public class CreateNewBookPresenter implements PresenterInterface{
	private final MainPresenter mainPresenter;
	private final CreateNewBookViewInterface createNewBookView;
	
	private final StorageInterface storage;
	
	/**
	 * Construeur du CreateNewBookPresenter. 
	 * @param createNewBookView CreateNewBookView qui est 
	 * @param storage StorageInterface qui est l'interface qui permet d'intéragir avec le système de stockage
	 * @param mainPresenter MainPresenter qui est le presenter principal
	 */
	public CreateNewBookPresenter(final CreateNewBookViewInterface createNewBookView, final StorageInterface storage, MainPresenter mainPresenter) {
		createNewBookView.setPresenter(this);	
		this.createNewBookView = createNewBookView;
		this.storage = storage; 
		this.mainPresenter = mainPresenter;
	}
	   
	
	/**
	 * Méthode qui permet de créer un nouveau livre si ses inforamtions sont valides. 
	 * Si elles ne le sont pas elle va afficher un message d'erreur. 
	 * @param title String qui est le titre du livre
	 * @param isbn Isbn qui est le numéro isbn du livre
	 * @param resume String qui est le résumé du livre
	 */
	public void createNewBook(final String title, final String isbnNumber, final String resume) {
		StringBuilder msgError = new StringBuilder("");	
		
		if(new Book().checkBook(title, new Isbn(isbnNumber), resume, msgError)) {
			if(mainPresenter.checkIfIsbnIsUnique(isbnNumber, msgError)) {
				try {
					Book book = new Book(mainPresenter.getAuthor(), title, new Isbn(isbnNumber), resume, false, null);
					storage.insertBook(book);
					mainPresenter.switchToMainView();
				} catch (StorageException e) {
					mainPresenter.displayInfo(e.getMessage());
				}			
			}		
		}
		
		mainPresenter.displayInfo(msgError.toString());
	}
			
	/**
	 * Méthode qui permet de revenir sur la vue Menu et qui remet les inforamations
	 * de la CreateNewBookView dans un état cohérent.
	 */
	public void goBack() {
		mainPresenter.switchToMainView();
	}
	
	/**
	 * Méthode qui permet de mettre à jour la vue 
	 * @param isbn String le numéro isbn 
	 * @param authorFullName String le nom complet de l'auteur
	 */
	public void updateCreateBookView(String isbn, String authorFullName) {
		createNewBookView.updateCreateBookView(isbn, authorFullName);
	}
}
