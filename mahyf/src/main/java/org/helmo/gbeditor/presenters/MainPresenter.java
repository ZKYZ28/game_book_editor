package org.helmo.gbeditor.presenters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.helmo.gbeditor.models.Author;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Isbn;
import org.helmo.gbeditor.presenters.interfaceview.MainViewInterface;
import org.helmo.gbeditor.presenters.mapper.BookMapper;
import org.helmo.gbeditor.presenters.modelview.ModelViewAuthor;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.repositories.StorageException;
import org.helmo.gbeditor.repositories.StorageInterface;

/**
 * MainPresenter qui est le prsénter de la MainView
 * @author franc
 *
 */
public class MainPresenter implements PresenterInterface{
	private  Map<String, PresenterInterface> presenters;
	private final MainViewInterface mainView;
	
	private final StorageInterface storage;
	private Author author;
		 
	/**
	 * Constructeur du MainPresenter
	 * @param storage StorageInterface qui est l'interface qui permet d'intéragir avec le système de stockage
	 * @param mainView MainView qui est l'interface de la vue principale
	 */
	public MainPresenter(final StorageInterface storage, final MainViewInterface mainView) {
		this.presenters = new HashMap<String, PresenterInterface>();
		presenters.put("mainPresenter", this);
				
		mainView.setPresenter(this);
		this.storage = storage;
		this.mainView = mainView;
	}
	
	public Author getAuthor() {
		return this.author;
	}
	
	/**
	 * Méthode qui permet d'afficher un message à l'utilisateur
	 * @param msg String qui est le message que l'on souhaite afficher
	 */
	public void displayInfo(String msg) {
		this.mainView.displayInfo(msg);
	}
	
	/**
	 * Méthode qui permet d'ajouter un présenter au MainPresenter
	 * @param presenters Map<String, PresenterInterface> qui est l'ensemble des presenters
	 */
	public void addAllPresenters(Map<String, PresenterInterface> presenters) {
		this.presenters = presenters;
	}
		
	
	public void setAuthor(Author author) {
		this.author = author;
		this.mainView.upDateAuthor(new ModelViewAuthor(author));
	}
	
	/**
	 * Méthode qui permet de savoir si un numéro Isnb n'est pas déjà utilisé
	 * @param isbn String qui est le numéro Isbn
	 * @param msgError StringBuilder qui est l'éventuel message d'erreur
	 * @return True si le numéro isbn est unique sinon false
	 */
	public boolean checkIfIsbnIsUnique(String isbn, StringBuilder msgError) {
		try {
			if(storage.checkIfIsbnExist(isbn)) {
				msgError.append("Ce numéro Isbn est déjà utilisé \n");
				return false;
			}
		}catch(StorageException e) {
			mainView.displayInfo(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Méthode qui permet de passer à la MainView 
	 */
	public void switchToMainView() {
		mainView.goTo("mainView", "Menu");
	}
	
	/**
	 * Méthode qui permet de passer de demander à la vue de passer sur la CreateNewBookView
	 */
	public void switchToCreateBookView() throws Exception{		
			 int nbrBook = storage.getListBooks().size();
			 String isbn = new Isbn(nbrBook, this.author.getMatricule()).getIsbnNumber();	
			 
			((CreateNewBookPresenter)presenters.get("createNewBookPresenter")).updateCreateBookView(isbn, author.getAuthorFullName()); 
		
			this.mainView.goTo("createNewBookView", "Création du livre");	
	}
	
	/**
	 * Méthode qui permet d'aller à la DetailBookView.
	 * Les informations complètes du livre vont être chargées. 
	 * @param book ModelViewBook qui est le livre dont on souhaite afficher les détails
	 */
	public void switchToDetailBookView(ModelViewBook book) {		
		try {
			Book bookL = BookMapper.convertBookModelViewToBook(book);	
			bookL.addListPage(storage.loadPages(bookL));
			storage.updateBookLibrary(bookL);
			((DetailBookPresenter)presenters.get("detailBookPresenter")).updateDetailBookView(new ModelViewBook(bookL));
			mainView.goTo("detailBookView", "Détails du livre");
		} catch (StorageException e) {
			mainView.displayInfo(e.getMessage());
		}	
	}
	
	/**
	 * Méthode qui permet d'aller à la listBookView
	 */
	public void switchToListBookView(){		 	
		List<ModelViewBook> listBookModelView = BookMapper.convertBookToModelView(storage.getListBooks());
		
		((ListBookPresenter)presenters.get("listBookPresenter")).displayAllBook(listBookModelView);
		
		mainView.goTo("listBookView", "Liste des livres");
	}
	
	/**
	 * Méthode qui permet de passer à la EditBookView.
	 * Elle met à jour les inforamtions affichées sur
	 * la editBookView par la même occasion
	 * faites sur la page soient présentes
	 * @param book ModelViewBook le livre que l'on souhaite éditer
	 */
	public void switchToEditBookView(ModelViewBook book) {
		((EditBookPresenter)presenters.get("editBookPresenter")).updateDisplayInformation(book);
		mainView.goTo("editBookView", "Edition du livre");		
	}

}
