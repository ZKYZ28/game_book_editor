/*
 * Aucune dispense
 * 
 */
package org.helmo.gbeditor;
import java.util.HashMap;

import org.helmo.gbeditor.infrastructures.LibraryBooks;
import org.helmo.gbeditor.infrastructures.SqlBookStorage;
import org.helmo.gbeditor.infrastructures.BookStorageFactory;
import org.helmo.gbeditor.presenters.BookPresenter;
import org.helmo.gbeditor.presenters.CreateNewBookPresenter;
import org.helmo.gbeditor.presenters.DetailBookPresenter;
import org.helmo.gbeditor.presenters.EditBookPresenter;
import org.helmo.gbeditor.presenters.ListBookPresenter;
import org.helmo.gbeditor.presenters.LoginPresenter;
import org.helmo.gbeditor.presenters.MainPresenter;
import org.helmo.gbeditor.presenters.PresenterInterface;
import org.helmo.gbeditor.views.CreateNewBookView;
import org.helmo.gbeditor.views.DetailBookView;
import org.helmo.gbeditor.views.EditBookView;
import org.helmo.gbeditor.views.ListBookView;
import org.helmo.gbeditor.views.LoginView;
import org.helmo.gbeditor.views.MainView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class qui est la base de l'application
 * @author franc
 *
 */
public class App extends Application{
	private SqlBookStorage storage;
	
	/**
	 * Méthode qui est le point d'entré de l'application
	 * @param args
	 */
    public static void main(String[] args) {
    	 launch(args); 
    }
    
   
	@Override
	public void start(Stage primaryStage) throws Exception {
		/*VIEW*/ 
		LoginView loginView = new LoginView();
		CreateNewBookView createNewBookView = new CreateNewBookView();
		ListBookView listBookView = new ListBookView();
		DetailBookView detailBookView = new DetailBookView();
		EditBookView editBookView = new EditBookView();
		MainView mainView = new MainView(loginView, createNewBookView, listBookView, detailBookView, editBookView);
		
		
		/*GESTIONNAIRE*/
		try {
			BookStorageFactory factory = new BookStorageFactory(
					"com.mysql.cj.jdbc.Driver",
		            "jdbc:mysql://192.168.128.13:3306/in21b20208?useSSL=false&Timezone=UTC",
		            "in21b20208",
		            "0208"	
		    );
	
			storage = factory.newStorageSession();
			LibraryBooks librabyBooks = new LibraryBooks();
			storage.setLibrabyBooks(librabyBooks);
			
			/*PRESENTER*/
			MainPresenter mainPresenter = new MainPresenter(storage, mainView);
			LoginPresenter loginPresenter = new LoginPresenter(loginView, storage, mainPresenter);
			CreateNewBookPresenter createPresenter = new CreateNewBookPresenter(createNewBookView, storage, mainPresenter);
			ListBookPresenter listBookPresenter = new ListBookPresenter(listBookView, mainPresenter);
			BookPresenter bookPresenter = new BookPresenter(listBookView, mainPresenter);
			DetailBookPresenter detailPresenter = new DetailBookPresenter(detailBookView, storage, mainPresenter);
			EditBookPresenter editBookPresenter = new EditBookPresenter(editBookView, storage,  mainPresenter);
			
			mainPresenter.addAllPresenters(new HashMap<String, PresenterInterface>(){{
				put("loginPresenter", loginPresenter);
				put("createNewBookPresenter", createPresenter);
				put("listBookPresenter", listBookPresenter);
				put("bookPresenter", bookPresenter);
				put("detailBookPresenter", detailPresenter);
				put("editBookPresenter", editBookPresenter);
			}});
			
		}catch(Exception e) {
			mainView.displayInfo("Erreur lors de la connection à la base de données");
		}	
		
		/*INIT*/
		Parent root = mainView.getRoot(); 		
		Scene scene = new Scene(root, 1300, 900);		
		scene.getStylesheets().add(	getClass().getResource("/styles.css").toExternalForm());		
		primaryStage.setTitle("AI 2022-2023");
		primaryStage.setScene(scene);			
		primaryStage.show();
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		if(storage != null) {
			storage.close();
		}
	}
}
