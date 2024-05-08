package org.helmo.gbeditor.views;

import org.helmo.gbeditor.presenters.CreateNewBookPresenter;
import org.helmo.gbeditor.presenters.interfaceview.CreateNewBookViewInterface;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class CreateNewBookView extends GridPane implements CreateNewBookViewInterface{
	private CreateNewBookPresenter p; 
	
	@Override
	public void setPresenter(CreateNewBookPresenter createNewBookPresenter) {
		this.p = createNewBookPresenter;
		createGridPaneCreateNewBook();
	}
	
	private TextField titleField;
	private TextField descriptionField;
	private TextField isbnText;
	
	
	
	Text authorText = new Text("");{
		authorText.getStyleClass().add("text");	
	}
	
	
	/**
	 * Méthode qui permet de créer la CreateNewBookView
	 */
	private void createGridPaneCreateNewBook() {
		Label author = new Label("Auteur :");{
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
		this.isbnText = new TextField("");{
			isbnText.getStyleClass().add("text");	
		}
		
		Label description = new Label("Description :");{
			description.getStyleClass().add("text");
		}
		this.descriptionField = new TextField();{
			descriptionField.getStyleClass().add("textField");
		}
		
		Button create = new Button("Créer");{
			create.getStyleClass().add("button");
			create.setOnAction(action -> p.createNewBook(titleField.getText(), isbnText.getText(), descriptionField.getText()));
		}
		
		Button back = new Button("Retour au Menu");{
			back.getStyleClass().add("button");
			back.setOnAction(action -> p.goBack());
		}
		
			this.setAlignment(Pos.CENTER);
			this.add(author, 0, 0);
			this.add(authorText, 1, 0);
			this.add(title, 0, 1);
			this.add(titleField, 1, 1);
			this.add(isbn, 0, 2);
			this.add(isbnText, 1, 2);
			this.add(description, 0, 3);
			this.add(descriptionField, 1, 3);
			this.add(create, 1, 5);
			this.add(back, 2, 5);
			
	}
	
	/**
	 * Méthode qui permet de mettre à jour CreateBookView
	 * Le numéro Isbn est mis à jour et les champs présents sont vidés
	 */
	@Override
	public void updateCreateBookView(String isbn, String authorFullName) {
		this.isbnText.setText(isbn);
		this.titleField.setText("");
		this.descriptionField.setText("");
		this.authorText.setText(authorFullName);
	}
	
	@Override
	public void setVisibleView(boolean isVisible, boolean isDisable) {
		this.setVisible(isVisible);
		this.setDisable(isDisable);		
	}
}
