package org.helmo.gbeditor.infrastructures;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.helmo.gbeditor.repositories.ConnectionFailedException;
import org.helmo.gbeditor.repositories.JdbcDriverNotFoundException;


/**
 * Crée la connextion à la base de données
 * @author franc
 *
 */
public class BookStorageFactory {
    private  String db;
    private  String username;
    private  String password;
    
    /**
     * Constructeur de SqlBookStorageFactory
     * @param driverName String qui est l'adresse des drivers ???
     * @param db String qui est l'adresse de la bd 
     * @param username String qui est 
     * @param pass String qui est le mot de passe de la bd
     * @throws ClassNotFoundException 
     */   
    public BookStorageFactory(String driverName, String db, String username, String pass) throws JdbcDriverNotFoundException{
    	try {
    		Class.forName(driverName);
    		this.db = db;
    		this.username = username;
    		this.password = pass;
    	} catch(ClassNotFoundException ex) {
    		throw new JdbcDriverNotFoundException(driverName);
    	}
    }
    

    /**
     * Méthode qui permet de créer une connexion à la BD
     */
    public SqlBookStorage newStorageSession() {
    	try {
			return new SqlBookStorage(DriverManager.getConnection(db, username, password));
		} catch (SQLException e) {
			throw new ConnectionFailedException("Unable to acces db " + db, e);
		}
    }
}
