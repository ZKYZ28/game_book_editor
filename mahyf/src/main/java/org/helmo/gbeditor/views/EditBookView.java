package org.helmo.gbeditor.views;


import java.util.List;

import org.helmo.gbeditor.presenters.EditBookPresenter;
import org.helmo.gbeditor.presenters.interfaceview.EditBookViewInterface;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.presenters.modelview.ModelViewChoice;
import org.helmo.gbeditor.presenters.modelview.ModelViewPage;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditBookView extends VBox implements EditBookViewInterface{
	private EditBookPresenter editBookPresenter;
	
	private ModelViewBook book;
	
	
	public void setPresenter(EditBookPresenter editBookPresenter) {
		this.editBookPresenter = editBookPresenter;
		createEditBookView();
	}
	
	
	private Label bookTitle;
	private ListView listPages;
	private ListView listChoices;
	private Label messageNewPage;
	private Label messageNewChoice;
	private Label messageNoChoiceFound;
	private TextField positonSelectPage;
	private TextArea textFieldPage;
	private TextArea textAreaChoice;
	private TextField destinationFieldNewChoice;
	private Label messageNoPageFound;
	/**
	 * Méthode qui permet de créer la EditBookView
	 */
	private void createEditBookView() {	
		/*TOP*/
		HBox topEditBook = new HBox();{
			topEditBook.getStyleClass().add("topEditBook");
			topEditBook.setSpacing(25);
			topEditBook.setAlignment(Pos.CENTER_LEFT);
		}
		
		bookTitle = new Label("Titre du livre");
		
		Button back = new Button("Page à la list des livres");{
			back.setOnAction(action -> editBookPresenter.goBack());
		}
		
		Button publish = new Button("Publier le livre");{
			publish.setOnAction(action -> editBookPresenter.checkForPublishBook(book));
		}
		
		topEditBook.getChildren().addAll(bookTitle, publish, back);
		/*TOP END*/
		
		
		/*MAIN*/
		GridPane editContainer = new GridPane();{
			editContainer.getStyleClass().add("editContainer");
			editContainer.setAlignment(Pos.CENTER);
			editContainer.setHgap(50); 
			editContainer.setVgap(50);
		}
		
		/*PAGES DISPLAY*/
		VBox pagesContainer = new VBox();{
			pagesContainer.getStyleClass().add("editPageitem");
			pagesContainer.setMaxWidth(600);
			pagesContainer.setMinWidth(600);
			pagesContainer.setMaxHeight(250);
			pagesContainer.setMinHeight(250);
		}
				
		Label titleListPage = new Label("Liste des pages");
		
		messageNoPageFound = new Label("");{
			messageNoPageFound.getStyleClass().add("message");
		}
		
		listPages = new ListView();
		
		listPages.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			 @Override
		        public void handle(MouseEvent event) {        
		         editBookPresenter.updateDisplayChoice(listPages.getSelectionModel().getSelectedIndex());
		            
		        }
		});
		
		Button deletePage = new Button("Supprimer la page");{
			deletePage.setOnAction(action -> editBookPresenter.checkIfChoiceLinkToPage(listPages.getSelectionModel().getSelectedIndex()));
		}
		
		pagesContainer.getChildren().addAll(titleListPage, messageNoPageFound, listPages, deletePage);				
		/*PAGE DISPLAY END*/
		
		
		/*NEW PAGE*/
		VBox newPage = new VBox();{
			newPage.getStyleClass().add("editPageitem");
			newPage.setMaxWidth(600);
			newPage.setMinWidth(600);
			newPage.setMaxHeight(250);
			newPage.setMinHeight(250);
		}
		
		Label titlePageText = new Label("Ajouter une page");
		
		messageNewPage = new Label("");
		
		HBox newPageTop = new HBox(); {
			newPageTop.setSpacing(25);
			newPageTop.setAlignment(Pos.CENTER_LEFT);
		}
		
		Label numPage = new Label("Numéro : ");
		positonSelectPage = new TextField();

		Label textPage = new Label("Texte de la page : ");
		textFieldPage = new TextArea();{
			textFieldPage.setWrapText(true);
		}
		
		Button valideNewpage = new Button("Créer");{
			valideNewpage.setOnAction(action -> editBookPresenter.insertNewPage(positonSelectPage.getText(), textFieldPage.getText()));
		}
		
		newPageTop.getChildren().addAll(numPage, positonSelectPage, valideNewpage);
		
		
		newPage.getChildren().addAll(titlePageText, newPageTop, textPage, textFieldPage, messageNewPage);
		/*NEW PAGE END*/
		
		
		
		/*LIST CHOICE*/
		VBox choicesContainer = new VBox();{
			choicesContainer.getStyleClass().add("editPageitem");
			choicesContainer.setMaxWidth(600);
			choicesContainer.setMinWidth(600);
			choicesContainer.setMaxHeight(250);
			choicesContainer.setMinHeight(250);
		}
		
		messageNoChoiceFound = new Label("");{
			messageNoChoiceFound.getStyleClass().add("message");
		}
		
		Label titleListChoices = new Label("Liste des choix liés à la page");
		
		listChoices = new ListView();
		
		listChoices.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			 @Override
		        public void handle(MouseEvent event) {
				
		        }
		});
		
		Button deleteChoice = new Button("Supprimer le choix");{
			deleteChoice.setOnAction(action -> editBookPresenter.deleteChoice(listPages.getSelectionModel().getSelectedIndex(), listChoices.getSelectionModel().getSelectedIndex()));
		}
		
		choicesContainer.getChildren().addAll(titleListChoices,messageNoChoiceFound, listChoices, deleteChoice);				
		/*LIST CHOICE END*/
		
		
		/*ADD CHOICE*/
		VBox choicesAddContainer = new VBox();{
			choicesAddContainer.getStyleClass().add("editPageitem");
			choicesAddContainer.setMaxWidth(600);
			choicesAddContainer.setMinWidth(600);
			choicesAddContainer.setMaxHeight(250);
			choicesAddContainer.setMinHeight(250);
		}
		

		Label titleAddChoices = new Label("Ajouter un choix à cette page");
		
		Label textNewChoice = new Label("Text : ");
		
		textAreaChoice =  new TextArea();
		
		Label desinationNewChoice = new Label("Envoie à la page : ");
		
		destinationFieldNewChoice =  new TextField();
		
		Button saveChoice = new Button("Enregister le choix");{
			saveChoice.setOnAction(action -> editBookPresenter.insertNewChoice(listPages.getSelectionModel().getSelectedIndex(), textAreaChoice.getText(), destinationFieldNewChoice.getText()));
		}
		
		messageNewChoice = new Label("");
		
		choicesAddContainer.getChildren().addAll(titleAddChoices, textNewChoice, textAreaChoice, desinationNewChoice, destinationFieldNewChoice, saveChoice, messageNewChoice);					
		/*ADD CHOICE END*/
		
		editContainer.add(pagesContainer, 0,0);
		editContainer.add(newPage, 1, 0);
		editContainer.add(choicesContainer, 0, 1);
		editContainer.add(choicesAddContainer, 1, 1);
		
		this.getChildren().addAll(topEditBook, editContainer);
	}
	
	/**
	 * Méthode qui permet d'afficher un message relatif 
	 * à la création d'un nouvelle page
	 */
	@Override
	public void displayMessageNewPage(String msgError) {
		messageNewPage.setText(msgError);	
	}
	
	/**
	 * Message qui permet d'afficher que 
	 * aucun choix n'est encore lié à la page
	 */
	@Override
	public void displayNoChoiceFound(String msg) {
		
		messageNoChoiceFound.setText(msg);
	}
	
	@Override
	public void displayNoPageFound(String msg) {
		this.messageNoPageFound.setText(msg);;
	}
	
	/**
	 * Méthode qui permet d'afficher un message relatif 
	 * à la création d'un nouveau choix
	 */
	@Override
	public void displayMessageNewChoice(String msg) {
		messageNewChoice.setText(msg);
	}
		
	
	/**
	 * Méthode qui permet de mettre à jour les informations affichées
	 * @param book
	 */
	@Override
	public void updateDisplayInformation(ModelViewBook book) {
		this.book = book;
		this.bookTitle.setText(book.getTitle());
		editBookPresenter.setCurrentBook(book);
	}
	
	/**
	 * Méthode qui permet de clear la liste des choix affichés
	 */
	@Override
	public void clearDispayChoice() {
		listChoices.getItems().clear();
	}
	
	/**
	 * Méthode qui permet de mettre à jours les pages affichées
	 */
	@Override
	public void updateDisplayPages(List<ModelViewPage> listPage) {
		listPages.getItems().clear();
		for(ModelViewPage page : listPage) {
			listPages.getItems().add(page.getNumPage() + ". " + page.getTextPage());
		}
	}
	
	 @Override
	 public void clearDisplayPage() {
		 this.listPages.getItems().clear();
	 }
	
	/**
	 * Méthode qui permet de mettre à jours les choix affichés
	 */
	@Override
	public void updateDisplayChoice(List<ModelViewChoice> listChoice) {
		clearDispayChoice();
		for(ModelViewChoice choice : listChoice) {
			listChoices.getItems().add(choice.getNumChoice() + ". " + choice.getTextChoice() + " -> " + choice.getNumGoPage());
		}
	}

	/**
	 * Méthode qui permet d'afficher une pop qui demande la confirmation 
	 * pour la suppression d'un page
	 */
	@Override
	public void displayPopUpDeletePage(int nbrChoixLink) {
		Alert alert = new Alert(AlertType.INFORMATION, "Cette page est référencée dans " + nbrChoixLink + " autre(s) page(s), êtes-vous sûr ?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
		    editBookPresenter.deletePage(listPages.getSelectionModel().getSelectedIndex());
		}
	}

	/**
	 * Méthode qui permet de reset les champs liés 
	 * à la création d'une page
	 */
	@Override
	public void resetDisplayInfoNewPage() {
		positonSelectPage.setText("");
		textFieldPage.setText("");
	}
	
	/**
	 * Méthode qui permet de reset les champs liés
	 * à la création d'un nouveau choix
	 */
	@Override
	public void resetDisplayInfoNewChoice() {
		textAreaChoice.setText("");
		destinationFieldNewChoice.setText("");
	}
	
	@Override
	public void setVisibleView(boolean isVisible, boolean isDisable) {
		this.setVisible(isVisible);
		this.setDisable(isDisable);		
	}
}
