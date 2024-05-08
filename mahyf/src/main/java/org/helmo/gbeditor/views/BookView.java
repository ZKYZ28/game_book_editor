package org.helmo.gbeditor.views;

import org.helmo.gbeditor.presenters.interfaceview.BookViewInterface;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.presenters.BookPresenter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class BookView extends GridPane implements BookViewInterface{
	private BookPresenter p;
	private ModelViewBook modelViewBook;
	
	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue 
	 * @param p BookPresenter qui est le présenter de BookView
	 */
	@Override
	public void setPresenter(BookPresenter p) {
		this.p = p;	
		createDetailBookView();
	}
	
	Label isbnValue;
	Label titleValue;
	Label authorValue;
	
	/**
	 * Méthode qui permet de créer la BookView
	 */
	private void createDetailBookView() {
		Label isbn = new Label("ISBN :");{
			isbn.getStyleClass().add("text");
		}
		
		isbnValue = new Label("");{
			isbn.getStyleClass().add("text");
		}
		
		Label title = new Label("Titre :");{
			isbn.getStyleClass().add("text");
		}
		
		titleValue = new Label("");{
			isbn.getStyleClass().add("text");
		}
		
		Label author = new Label("Auteur :");{
			isbn.getStyleClass().add("text");
		}
		
		authorValue = new Label("");{
			isbn.getStyleClass().add("text");
		}
				
		this.setAlignment(Pos.CENTER);
		this.add(isbn, 0, 0);
		this.add(isbnValue, 1, 0);
		this.add(title, 0, 1);
		this.add(titleValue, 1, 1);
		this.add(author, 0, 2);
		this.add(authorValue, 1, 2);
		
	}
	
	/**
	 * Méthode qui permet d'afficher les 
	 * informations de base d'un livre
	 */
	@Override
	public void updateBookInformation(ModelViewBook modelViewBook) {		
		this.modelViewBook = modelViewBook;				
		isbnValue.setText(modelViewBook.getIsbn());		
		authorValue.setText(modelViewBook.getAuthor().getFullName());
		this.p.checkIfTitleIsNotTooLong(modelViewBook.getTitle());
		this.p.checkIfBookIsPublished(modelViewBook);
	}
	
	/**
	 * Méthode qui permet d'afficher le titre du livre
	 */
	@Override
	public void displayTitle(String title) {
		titleValue.setText(title);
	}
	
	/***
	 * Méthode qui permet d'afficher un bouton qui 
	 * permet d'éditer d'un livre
	 */
	@Override
	public void displayEditButton() {
		Button buttonDetail = new Button("Voir");{
			buttonDetail.getStyleClass().add("button");
			buttonDetail.setOnAction(action -> this.p.switchToDetailBookView(modelViewBook));
		}	
		
		this.add(buttonDetail, 1, 3);
	}
	
	/**
	 * Méthode qui permet d'afficher un message
	 * qui indique que le livre est publié
	 */
	@Override
	public void displayIsPublishedText() {
		Label isPublishedText = new Label("Publié");{
			isPublishedText.getStyleClass().add("button");
		}
		
		this.add(isPublishedText, 1, 3);
	}

	@Override
	public void setVisibleView(boolean isVisible, boolean isDisable) {
		this.setVisible(isVisible);
		this.setDisable(isDisable);		
	}
}
