package org.helmo.gbeditor.repositories;

/**
 * Exception qui est lancée lorsque l'on arrive pas à se connecter 
 * à la base de données
 * @author franc
 *
 */
public class ConnectionFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de ConnectionFailedException
	 * @param s String le message d'erreur que l'on veut afficher
	 * @param ex l'exception catch
	 */
	public ConnectionFailedException(String s, Exception ex) {
        super(s, ex);
    } 
}
