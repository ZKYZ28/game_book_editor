package org.helmo.gbeditor.presenters;

import java.sql.SQLException;
import java.util.List;

import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Choice;
import org.helmo.gbeditor.models.Page;
import org.helmo.gbeditor.presenters.interfaceview.EditBookViewInterface;
import org.helmo.gbeditor.presenters.mapper.BookMapper;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.presenters.modelview.ModelViewChoice;
import org.helmo.gbeditor.presenters.modelview.ModelViewPage;
import org.helmo.gbeditor.repositories.StorageException;
import org.helmo.gbeditor.repositories.StorageInterface;

/**
 * EditBookPresenter qui est le présenter de la EditBookView
 * @author franc
 *
 */
public class EditBookPresenter implements PresenterInterface{
	private final MainPresenter mainPresenter;
	private final EditBookViewInterface editBookView;
	
	private final StorageInterface storage;
	private Book currentBook;
	
	/**
	 * Constructeur de EditBookPresenter.
	 * @param editBookView EditBookViewInterface qui est la vue qui permet d'éditer un livre
	 * @param storage StorageInterface qui est l'interface qui permet d'intéragir avec le système de stockage
	 * @param mainPresenter MainPresenter qui est le presenter principal
	 */
	public EditBookPresenter(EditBookViewInterface editBookView, StorageInterface storage, MainPresenter mainPresenter) {
		editBookView.setPresenter(this);
		this.editBookView = editBookView;
		this.mainPresenter = mainPresenter;
		this.storage = storage;
	}
	
	/**
	 * Méthode qui permet de mettre à jour le Book 
	 * @param bookV ModelViewBook qui est le book 
	 */
	public void setCurrentBook(ModelViewBook bookV) {
		this.currentBook = BookMapper.convertBookModelViewToBook(bookV);
	}
	
	/**
	 * Méthode qui permet de mettre à jour les informations affichées
	 * sur la EditBookView
	 * @param book ModelViewBook qui est le livre dont on souhaite afficher les inforamtions
	 */
	public void updateDisplayInformation(ModelViewBook book) {
		editBookView.updateDisplayInformation(book);
		this.updateDisplayPage();
	}
	
	/**
	 * Méthode qui permet d'insérer une page dans le livre. 
	 * Elle va vérifier que les inforamtions sont valides si 
	 * oui elle ajoute sinon elle affiche un message d'erreur
	 * @param numPage String qui est le numéro de la page à insérer
	 * @param textPage String qui est le texte de la page 
	 */
	public void insertNewPage(String numPage, String textPage) {
		try {				
				StringBuilder msgError = new StringBuilder();
				if(new Page().checkPageValidity(numPage, textPage, msgError)) {
					managerInsertNewPage(numPage, textPage, msgError);	
				}				
				editBookView.displayMessageNewPage(msgError.toString());
			}catch (StorageException e) {
				editBookView.displayMessageNewPage("Erreur lors de la sauvegarde la page" + e.getMessage());
			}
	}
	
	/**
	 * Méthode qui permet d'insérer le livre dans le model et la BD
	 * si le numéro de la page est correct.
	 * @param numPage String qui est le numéor de la nouvelle page à insérer
	 * @param textPage String qui est le texte de la nouvelle page à insérer
	 * @param msgError StringBuilder qui est l'éventuel message d'erreur
	 * @throws SQLException Exception lancée lors d'une erreur sql
	 */
	private void managerInsertNewPage(String numPage, String textPage, StringBuilder msgError) throws StorageException {								
		if(currentBook.managerInsertPage(Integer.parseInt(numPage), textPage, msgError)) {
			storage.updatePages(currentBook);
			insertDoneNewPage();
		}						
	}
	
	/**
	 * Méthode qui permet de remettre l'affichage dans un état cohérent 
	 * après l'ajout d'une page
	 */
	private void insertDoneNewPage(){					
		this.updateDisplayPage();
		editBookView.clearDispayChoice();
		editBookView.resetDisplayInfoNewPage();
	}

	/**
	 * Méthode qui permet de supprimer la page d'un livre 
	 * @param indexPage int qui est l'index de la page qu'on veut supprimer
	 */
	public void deletePage(int indexPage) {
		try {
				currentBook.deletePage(indexPage);
				storage.updatePages(currentBook);							
				this.updateDisplayPage();												
				editBookView.clearDispayChoice();	
		} catch (StorageException e) {
			editBookView.displayNoChoiceFound("Erreur lors de la suppresion de la page");
		}		
	}
	
	/**
	 * Méthode qui permet de vérifier si des choix sont 
	 * liés à une page. Si c'est le cas on affiche une popUp
	 * @param int indexPage qui est l'index de la page de laquelle on veut checker si un choix existe
	 */
	public void checkIfChoiceLinkToPage(int indexPage) {
		if(!currentBook.checkIfChoiceLinkToPage(indexPage +1)) {
			deletePage(indexPage);
		}else {
			editBookView.displayPopUpDeletePage(currentBook.getNbrLinkedChoice(indexPage +1));
		}
	}
	
	/**
	 * Méthode qui permet d'insérer un nouveau choix à une page
	 * @param indexPage int qui est l'index de la page à laquelle on veut ajouter le choix
	 * @param textChoice String qui est le texte du choix
	 * @param numGoToPage String qui est le numéro de la page vers laquelle le choix renvoie
	 */
	public void insertNewChoice(int indexPage, String textChoice, String numGoToPage) {
		try {			
			StringBuilder msgError = new StringBuilder();
			if(new Choice().checkChoiceValidity(textChoice, numGoToPage, indexPage +1, msgError)) {
				managerInsertNewChoice(indexPage, textChoice, numGoToPage, msgError);
			}	
			editBookView.displayMessageNewChoice(msgError.toString());
		} catch (StorageException e) {
			editBookView.displayMessageNewChoice(e.getMessage());
		}
	}
	
	/**
	 * Méthode qui permet de gérer l'insertion d'un nouveau choix
	 * @param indexPage int qui est l'index de la page à laquelle on veut ajouter le choix
	 * @param textChoice String qui est le texte du choix
	 * @param numGoToPage String qui est le numéro de la page vers laquelle le choix renvoie
	 * @param msgError StringBuilder les éventuels messages d'erreur
	 * @throws SQLException Exception lancée lors d'une erreur sql 
	 */
	private void managerInsertNewChoice(int indexPage, String textChoice, String numGoToPage, StringBuilder msgError)throws StorageException {
		int numChoice = currentBook.getCurrentPage(indexPage).getChoiceList().size() +1 ;
		
		if(currentBook.checkIfPageExist(Integer.parseInt(numGoToPage), msgError)) {
			Choice choice = new Choice(numChoice, textChoice, indexPage + 1, Integer.parseInt(numGoToPage));
			currentBook.getCurrentPage(indexPage).addNewChoiceToPage(choice);
			storage.insertChoice(choice, currentBook);
			insertDoneNewChoice(indexPage);
		}
	}
	
	/**
	 * Méthode qui permet de reset les données mises pour la créer le choix 
	 * et mettre à jour l'affichage des choix
	 * @param indexPage int qui est l'index de la page à laquelle on a ajouté le choix
	 */
	private void insertDoneNewChoice(int indexPage) {
		editBookView.resetDisplayInfoNewChoice();
		updateDisplayChoice(indexPage);
	}
	
	/**
	 * Méthode qui permet de supprimer un choix
	 * @param indexPage int qui est l'index de la page de laquelle on supprime le choix
	 * @param indexChoice int qui est l'index du choix que l'on veut supprimer
	 */
	public void deleteChoice(int indexPage, int indexChoice) {				
		try {	
			currentBook.getCurrentPage(indexPage).deleteChoice(indexChoice +1);
			storage.updateChoices(indexPage +1 , currentBook, currentBook.getCurrentPage(indexPage).getChoiceList());
			updateDisplayChoice(indexPage);	
		} catch (StorageException e) {
			editBookView.displayNoChoiceFound("Erreur lors de la suppresion du choix");
		}
	}
		
	/**
	 * Méthode qui permet de mettre à jour les choix affichés
	 * @param indexPage int qui est l'index de la page dont on veut update les choix affichés
	 */
	public void updateDisplayChoice(int indexPage) {
		if(indexPage >= 0) {
			List<ModelViewChoice> listChoice = BookMapper.convertChoiceToChoiceModelView(currentBook.getCurrentPage(indexPage).getChoiceList());
			editBookView.clearDispayChoice();
			editBookView.displayNoChoiceFound("");
			if(listChoice.size() == 0) {
				editBookView.displayNoChoiceFound("Aucun choix n'est encore lié à cette page");
			}else {
				editBookView.updateDisplayChoice(listChoice);
			}		
		}				
	}
	
	/**
	 * Méthode qui permet de mettre à jour l'affichage des pages
	 */
	public void updateDisplayPage() {
		List<ModelViewPage> listPage = BookMapper.convertPageToPageModelView(currentBook.getListPage());		
		if(listPage.size() == 0) {
			editBookView.clearDisplayPage();
			editBookView.clearDispayChoice();
			editBookView.displayNoPageFound("Aucune page n'est encore liée à ce livre");
		}else {
			editBookView.displayNoPageFound("");
			editBookView.updateDisplayPages(listPage);
		}
	}
	
	/**
	 * Méthode qui permet de passer le livre en Publié
	 * @param ModelViewBook le livre que l'on souhaite publié
	 */
	public void checkForPublishBook(ModelViewBook book) {		
		try {
			if(currentBook.checkIfBookCanBePublished()) {			
				storage.getBook(book.getIsbn()).publishBook();			
				storage.updateBookPusblished(BookMapper.convertBookModelViewToBook(book));
				goBack();				
			}else {
				mainPresenter.displayInfo("Pour être publié, le livre doit contenir au minimum une page");
			}
		} catch (StorageException e) {
			mainPresenter.displayInfo("Erreur lors de la publication du livre " + e.getMessage());
		}				
	}
	
	/**
	 * Méthode qui permet de revenir à la listBookView
	 */
	public void goBack() {		
		resetDisplay();
		mainPresenter.switchToListBookView();
	}
	
	/**
	 * Méthode qui permet de clear les messages de la vue
	 */
	private void resetDisplay() {			
			editBookView.clearDispayChoice();
			editBookView.displayMessageNewChoice("");
			editBookView.displayMessageNewPage("");
			editBookView.displayNoChoiceFound("");
	} 	
}
