package org.helmo.gbeditor.views;

import org.helmo.gbeditor.presenters.interfaceview.LoginViewInterface;
import org.helmo.gbeditor.presenters.modelview.ModelViewAuthor;
import org.helmo.gbeditor.presenters.LoginPresenter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginView extends GridPane implements LoginViewInterface{
	private LoginPresenter p;

	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue
	 * @param p qui est le présente de la LoginView
	 */
	@Override
	public void setPresenter(LoginPresenter loginPresenter) {
		this.p = loginPresenter;
		createGridPaneLogin(); 
	}
	
	/**
	 * Méthode qui permet de créer la LoginView
	 */
	private void createGridPaneLogin() {
		Label firstName = new Label("Prénom :");{
			firstName.getStyleClass().add("text");
		}
		
		TextField firstNameField = new TextField("Francois");{
			firstNameField.getStyleClass().add("textField");
			firstNameField.setMinWidth(250);
		}
		
		Label name = new Label("Nom :");{ 
			name.getStyleClass().add("text");
		}
		
		TextField nameField = new TextField("Mahy");{
			nameField.getStyleClass().add("textField");
		}
		
		Label matri = new Label("Matricule :");{
			matri.getStyleClass().add("text");
		}
		
		TextField matriField = new TextField("210208");{
			matriField.getStyleClass().add("textField");
		}
		
		Button login = new Button("Valider");{
			login.getStyleClass().add("button");
			login.setOnAction(action -> this.p.logUser(firstNameField.getText(), nameField.getText(), matriField.getText()));
		}	
		
		
		this.setAlignment(Pos.CENTER);
		this.add(firstName, 0, 0);
		this.add(firstNameField, 1, 0);
		this.add(name, 0, 1);
		this.add(nameField, 1, 1);
		this.add(matri, 0, 2);
		this.add(matriField, 1, 2);
		this.add(login, 1, 3);
	}
	
	@Override
	public void setVisibleView(boolean isVisible, boolean isDisable) {
		this.setVisible(isVisible);
		this.setDisable(isDisable);		
	}
}
