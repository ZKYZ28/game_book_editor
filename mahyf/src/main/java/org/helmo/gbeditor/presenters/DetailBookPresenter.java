package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Isbn;
import org.helmo.gbeditor.presenters.interfaceview.DetailBookViewInterface;
import org.helmo.gbeditor.presenters.mapper.BookMapper;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.repositories.StorageInterface;

/**
 * DetailBookPresenter qui est le présenter de la DetailBookView
 * @author franc
 *
 */
public class DetailBookPresenter implements PresenterInterface{
	private final MainPresenter mainPresenter;
	private final DetailBookViewInterface detailBookView;
	
	private final StorageInterface storage;
	
	/**
	 * Constructeur du DetailBookPresenter
	 * @param detailBookView DetailBookViewInterface qui est la vue détaillée du livre
	 * @param storage StorageInterface qui est l'interface qui permet d'intéragir avec le système de stockage
	 * @param mainPresenter MainPresenter qui est le presenter principal
	 */
	public DetailBookPresenter(DetailBookViewInterface detailBookView,  StorageInterface storage, MainPresenter mainPresenter) {
		detailBookView.setPresenter(this);
		this.mainPresenter = mainPresenter;
		
		this.detailBookView = detailBookView;		
		this.storage = storage;
	}
	
	/**
	 * Méthode qui permet de revenir à la page précédente
	 */
	public void goBack() {		
		mainPresenter.switchToListBookView();
	}
	
	/**
	 * Méthode qui permet de mettre à jour les informations du livre sur la DetailBookView
	 * @param book ModelViewBook qui est le livre dont on souhaite afficher les informations
	 */
	public void updateDetailBookView(ModelViewBook book) {
		detailBookView.updateDetailBookView(book);
	}
	
	/**
	 * Méthode qui permet de passer à la EditBookView.
	 * Elle met à jour les inforamtions affichées sur
	 * la editBookView par la même occasion
	 * faites sur la page soient présentes
	 * @param book ModelViewBook le livre dont on souhaite avoir les détails
	 */
	public void switchToEditBookView(ModelViewBook book) {			
		mainPresenter.switchToEditBookView(book);
	}
	
	/**
	 * Méthode qui permet d'afficher les pages d'un livre
	 * Si il n'y a pas de page pour le livre un message sera alors affiché
	 * @param book ModelViewBook qui est le livre dont on veut afficher
	 * les pages
	 */
	public void updatePageInformation(ModelViewBook book) {		
		if(book.getListPage().size() == 0) {
			detailBookView.displayNoPageMessage();
		}else {
			detailBookView.displayAllPage(book);
		}
	}
	
	/**
	 * Méthode qui permet de mettre à jour les inforamtions du livre
	 * @param book ModelViewBook le livre que l'on veut mettre à jour
	 * @param title String le (nouveau) titre du livre
	 * @param isbnNumber le (nouveau) numéro isbn du livre
	 * @param resume le (nouveau) résumé du livre
	 */
	public void updateBookInformation(ModelViewBook book, String title, String isbnNumber, String resume) {
		Book bookToUpdate = BookMapper.convertBookModelViewToBook(book);
		StringBuilder msgError = new StringBuilder("");	
		
		if(bookToUpdate.checkBook(title, new Isbn(isbnNumber), resume, msgError)) {
			if(checkIsbnUnique(book.getIsbn(), isbnNumber, msgError)) {
				try {
					storage.updateBook(bookToUpdate, title, isbnNumber, resume);
					goBack();
				} catch (Exception e) {
					mainPresenter.displayInfo(e.getMessage());
				}
			}
		}
		mainPresenter.displayInfo(msgError.toString());	
	}
	
	
	/**
	 * Méthode qui permet de vérifier qu'un numéro isbn est bien unique
	 * @param oldIsbn String qui est l'ancien numéro isbn
	 * @param isbn  String qui est le nouveau numéro isbn
	 * @param msgError StringBuilder qui est l'éventuel message d'erreur
	 * @return True si le numéro isbn est unique sinon false
	 */
	private boolean checkIsbnUnique(String oldIsbn, final String isbn, StringBuilder msgError){	
		if(!oldIsbn.equals(isbn)) {
			mainPresenter.checkIfIsbnIsUnique(isbn, msgError);			
		}	
		return msgError.toString().isEmpty();
	}
}
