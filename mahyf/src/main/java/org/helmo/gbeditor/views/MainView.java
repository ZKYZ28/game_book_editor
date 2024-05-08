package org.helmo.gbeditor.views;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.helmo.gbeditor.presenters.interfaceview.MainViewInterface;
import org.helmo.gbeditor.presenters.modelview.ModelViewAuthor;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.presenters.MainPresenter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainView extends VBox implements MainViewInterface{
	private MainPresenter mainPresenter;
	private Map<String, ViewInterface> mapView = new HashMap<String, ViewInterface>();
	
	
	/**
	 * Construceur de la MainView
	 * @param loginView LoginView qui est la vue de login
	 * @param createNewBookView CreateNewBookView qui est la vue pour cr�er un livre
	 */
	public MainView(LoginView loginView, CreateNewBookView createNewBookView, ListBookView listBookView, DetailBookView detailBookPane, EditBookView editBookView) {
		mapView.put("loginView",loginView);
		mapView.put("createNewBookView",createNewBookView);
		mapView.put("listBookView",listBookView);
		mapView.put("detailBookView",detailBookPane);
		mapView.put("editBookView",editBookView);
		createMainView();
		mapView.put("mainView", this);
		initComponent(loginView, createNewBookView, listBookView, detailBookPane, editBookView);
	}
	 
	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue
	 * @param mainPresenter qui est le présenter de la MainView
	 */
	@Override
	public void setPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}
	
	/**
	 * Méthode qui permet de changer de vue 
	 * @param pageName String qui est le nom de la page vers laquelle on veut aller
	 * @param pageTitle String qui est le titre de la page vers laquelle on veut aller
	 */
	@Override
	public void goTo(String pageName,String pageTitle) {
		for(Map.Entry<String, ViewInterface> entry: mapView.entrySet()) {
			if(entry.getKey().equals(pageName)) {
				entry.getValue().setVisibleView(true, false);
			}else {
				entry.getValue().setVisibleView(false, true);
			}
		}
		updateHeaderAndMessage(pageTitle);
	}
	
	private void updateHeaderAndMessage(String pageTitle) {
		titlePage.setText(pageTitle);
		message.setText("");
	}
	

	//MESSAGE
	Label message = new Label("");{
		message.getStyleClass().add("message");
	}
	
	//HEADER
	Label titlePage = new Label("Identification");{
		titlePage.getStyleClass().add("title");
	}
	 Label userName = new Label("");{
		userName.getStyleClass().add("userName");
	}
	 VBox header = new VBox();{	
		header.getChildren().addAll(titlePage, userName);
		header.setAlignment(Pos.CENTER);
		header.setPadding(new Insets(0, 0, 20, 0));
		header.getStyleClass().add("header");	
	}
	
	private void createMainView() {							
		//MENU
		 Button createBook = new Button("Créer un livre");{
			createBook.getStyleClass().add("button");
			createBook.setOnAction(action -> {
				try {
					mainPresenter.switchToCreateBookView();
				} catch (Exception e) {
					this.displayInfo("Erreur lors de la génération du num�ro Isbn");
				}
			});
		}	
		
		 Button listBook = new Button("Liste des livres");{
			listBook.getStyleClass().add("button");
			listBook.setOnAction(action -> mainPresenter.switchToListBookView());
		}	
		
		 Button exit = new Button("Quitter");{
			exit.getStyleClass().add("button");
			exit.setOnAction(action -> System.exit(0));
		}	
		
		 
		this.setAlignment(Pos.CENTER);
		this.setSpacing(15);
		this.getChildren().addAll(createBook, listBook, exit);
	}
		
		
	/**
	 * Méthode qui permet d'afficher quelque chose à l'utilisateur
	 * @param info String qui est ce que l'on souhaite afficher à l'utilisateur
	 */	
	@Override
	public void displayInfo(String info) {
		message.setText(info);
	}
					
	/**
	 * Méthode qui permet de mettre � jour le nom complet de l'auteur dans le header et dans le CreaNewBookView
	 * @param authorFullName
	 */
	@Override
	public void upDateAuthor(ModelViewAuthor author) {
		userName.setText(author.getFullName());
	}
	
	 private StackPane mainStackPane = new StackPane();
		
	 private BorderPane mainPage = new BorderPane();{
		 mainPage.setTop(header);
		 mainPage.setCenter(mainStackPane);
		 mainPage.setBottom(message);
	 }
	
	 /**
	  * Méthode qui permet d'ajouter les composants de ma page
	  * et de gérer le premier affichage
	  */
	private void initComponent(LoginView loginView, CreateNewBookView createNewBookView, ListBookView listBookView, DetailBookView detailBookView, EditBookView editBookView) {		
		mainStackPane.getChildren().addAll(createNewBookView, loginView, this, listBookView, detailBookView, editBookView);
		createNewBookView.setDisable(true);
		createNewBookView.setVisible(false);
		this.setDisable(true);
		this.setVisible(false);
		listBookView.setDisable(true);
		listBookView.setVisible(false);
		detailBookView.setDisable(true);
		detailBookView.setVisible(false);
		editBookView.setDisable(true);
		editBookView.setVisible(false);
	}
		
	public Parent getRoot() {		
		return mainPage;
	}

	@Override
	public void setVisibleView(boolean isVisible, boolean isDisable) {
		this.setVisible(isVisible);	
		this.setDisable(isDisable);
	}
}
