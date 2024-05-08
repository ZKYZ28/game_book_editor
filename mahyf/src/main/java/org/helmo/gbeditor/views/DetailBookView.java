package org.helmo.gbeditor.views;


import org.helmo.gbeditor.presenters.DetailBookPresenter;
import org.helmo.gbeditor.presenters.interfaceview.DetailBookViewInterface;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.presenters.modelview.ModelViewPage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DetailBookView extends VBox implements DetailBookViewInterface{
	private DetailBookPresenter p;
	private ModelViewBook book;
	
	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue 
	 * @param p qui est le présenter de la DetailBookView
	 */
	@Override
	public void setPresenter(DetailBookPresenter p) {
		this.p = p;
		createDetailBookView();
	}
	
	private TextField titleField;
	private TextField descriptionField;
	private TextField isbnField;
	private Label authorText;
	private Label infoMessage;
	private ListView listBook;
	
	/**
	 * Méthode qui permet de créer la DetailBookView
	 */
	private void createDetailBookView() {		
		/*FORMULAIRE*/
		GridPane formBookContainer = new GridPane();{
			formBookContainer.getStyleClass().add("addPadding");
		}
		
		Label author = new Label("Auteur :");{
			author.getStyleClass().add("text");
		}	
		
		authorText= new Label("");{
			author.getStyleClass().add("text");
		}	
				
		Label title = new Label("Titre :");{
			title.getStyleClass().add("text");
		}
		this.titleField = new TextField("");{
			titleField.getStyleClass().add("textField");
			titleField.setMinWidth(350);
		}
		
		Label isbn = new Label("Numéro ISBN :");{
			isbn.getStyleClass().add("text");
		}
		this.isbnField = new TextField("");{
			isbnField.getStyleClass().add("textField");	
		}
		
		Label description = new Label("Description :");{
			description.getStyleClass().add("text");
		}
		this.descriptionField = new TextField();{
			descriptionField.getStyleClass().add("textField");
		}
		
		
		formBookContainer.setAlignment(Pos.CENTER);
		formBookContainer.add(author, 0, 0);
		formBookContainer.add(authorText, 1, 0);
		formBookContainer.add(title, 0, 1);
		formBookContainer.add(titleField, 1, 1);
		formBookContainer.add(isbn, 0, 2);
		formBookContainer.add(isbnField, 1, 2);
		formBookContainer.add(description, 0, 3);
		formBookContainer.add(descriptionField, 1, 3);
		/*FORMULAIRE END*/
		
		
		/*LISTE DES PAGES*/
		VBox listPageContainer = new VBox();{
			listPageContainer.getStyleClass().add("addPadding");
			listPageContainer.setAlignment(Pos.CENTER);
		}
		
		Label titleList = new Label("Liste des pages");{
			titleList.getStyleClass().add("text");
		}
		
		infoMessage = new Label("Ce livre ne contient aucune page");{
			infoMessage.setVisible(false);
			infoMessage.getStyleClass().add("message");
		}
		
		listBook = new ListView();{
			listBook.setMinWidth(350);
			listBook.setMaxWidth(350);
			listBook.setMinHeight(200);
			listBook.setMaxHeight(200);
		}
		
		listPageContainer.getChildren().addAll(titleList, infoMessage, listBook);{
			descriptionField.getStyleClass().add("title");
		}
		/*LISTE DES PAGES END*/
		
		/*MENU*/
		HBox buttonsContainer = new HBox();
		
		Button update = new Button("Mettre à jour");{
			update.getStyleClass().add("button");
			update.setOnAction(action -> p.updateBookInformation(book, titleField.getText(), isbnField.getText(), descriptionField.getText()));
		}
		
		Button edit = new Button("Editer");{
			edit.getStyleClass().add("button");
			edit.setOnAction(action -> p.switchToEditBookView(book));
		}
		
		
		Button back = new Button("Retour à la list des livres");{
			back.getStyleClass().add("button");
			back.setOnAction(action -> p.goBack());
		}
		
		buttonsContainer.getChildren().addAll(update,edit, back);
		buttonsContainer.setAlignment(Pos.CENTER);
		buttonsContainer.setSpacing(10);
		/*MENU END*/
				
		this.getChildren().addAll(formBookContainer, listPageContainer, buttonsContainer);
	}
	
	/**
	 * Méthode utilisée par la MainView pour mettre les infos 
	 * de la DetailBookView lors du changement de fenêtre
	 * @param book ModelViewBook livre dont on veut afficher les détails
	 */
	@Override
	public void updateDetailBookView(ModelViewBook book) {
		this.book = book;
		this.titleField.setText(book.getTitle());
		this.isbnField.setText(book.getIsbn());
		this.descriptionField.setText(book.getResume());
		authorText.setText(book.getAuthor().getFullName());
		
		p.updatePageInformation(book);
	}
	
	/**
	 * Méthode qui permet d'afficher un message qui indique 
	 * que le livre ne contient aucune page
	 */
	@Override
	public void displayNoPageMessage() {
		listBook.setVisible(false);
		infoMessage.setVisible(true);
	}
	
	/**
	 * Méthode qui permet d'afficher toutes les pages
	 * d'un livre
	 */
	@Override
	public void displayAllPage(ModelViewBook book) {
		listBook.setVisible(true);
		infoMessage.setVisible(false);		
		listBook.getItems().clear();		
		for(ModelViewPage page : book.getListPage()) {
			listBook.getItems().add(page.getNumPage() + ". " + page.getTextPage());
		}
	}	
	
	@Override
	public void setVisibleView(boolean isVisible, boolean isDisable) {
		this.setVisible(isVisible);
		this.setDisable(isDisable);		
	}
}
