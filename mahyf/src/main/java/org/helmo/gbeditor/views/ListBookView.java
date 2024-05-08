package org.helmo.gbeditor.views;

import java.util.List;

import org.helmo.gbeditor.presenters.interfaceview.ListBookViewInterface;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.presenters.BookPresenter;
import org.helmo.gbeditor.presenters.ListBookPresenter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class ListBookView extends VBox implements ListBookViewInterface{
	private ListBookPresenter p;
	private BookPresenter bookP;
	
	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue
	 * @param p qui est le présenter de la ListBookView
	 */
	@Override
	public void setPresener(ListBookPresenter p) {
		this.p = p;	
		createListBookView();	
	}
	
	/**
	 * Méthode qui peremt d'assigner un le presenter à la BookView
	 * @param bookP qui est le présenter de la BookView
	 */
	@Override
	public void setPresenterBook(BookPresenter bookP) {
		this.bookP = bookP;
	}
	
	
	private FlowPane booksContainer;
	
	private Label errorMessage = new Label("");{
		errorMessage.getStyleClass().add("message");
	}
	
	/**
	 * Méthode qui permet de créer la ListBookView
	 */
	private void createListBookView() {
		ScrollPane sc = new ScrollPane();{
			sc.setFitToHeight(true);
			sc.setPrefSize(200, 200);
			sc.setMaxHeight(700);
			sc.setMaxWidth(1300);
			sc.setMinHeight(700);
		}
		
		booksContainer = new FlowPane ();{
			booksContainer.setPrefWidth(1280);	
			booksContainer.setHgap(25);
			booksContainer.setVgap(25);
			booksContainer.setAlignment(Pos.CENTER);
			booksContainer.getStyleClass().add("booksContainer");
		}
		
		Button goBack = new Button("Retour au menu"); {
			goBack.getStyleClass().add("button");
			goBack.setOnAction(action -> this.p.goBack());
		}
			
		/*AJOUT A L'ELEMENT*/	
		sc.setContent(booksContainer);	
		this.setMinWidth(1300);
		this.setMaxHeight(700);
		this.getChildren().addAll(errorMessage, sc, goBack);
		this.setAlignment(Pos.CENTER);
	}
	
	
	/**
	 * Méthode qui permet de demander au présenter le check de la list
	 * des livres
	 */
	@Override
	public void displayBooks(List<ModelViewBook> listBookModelView) {
		this.p.displayAllBook(listBookModelView);
	}
	
	
	/**
	 * Méthode qui permet de mettre à jour la liste des livres 
	 * affichées 
	 */
	@Override
	public void updateListBook(List<ModelViewBook> listBookModelView) {
			booksContainer.getChildren().clear();			
			for (int i = 0; i < listBookModelView.size(); i++) {
				BookView bookView = new BookView();{
					bookView.getStyleClass().add("bookView");
				}	
				bookP.setView(bookView);
				bookView.setPresenter(bookP);
				bookView.updateBookInformation(listBookModelView.get(i));
				
				this.booksContainer.getChildren().add(bookView);
			}								
	}
	
	/**
	 * Méthode qui permet d'afficher un message 
	 * pour indiquer qu'aucun livre n'a été trouvé
	 */
	@Override
	public void displayNoBookFoundMessage(String msg) {
		errorMessage.setText(msg);
	}
	
	@Override
	public void setVisibleView(boolean isVisible, boolean isDisable) {
		this.setVisible(isVisible);
		this.setDisable(isDisable);		
	}
}
