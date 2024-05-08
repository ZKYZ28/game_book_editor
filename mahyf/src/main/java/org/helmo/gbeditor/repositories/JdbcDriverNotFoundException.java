package org.helmo.gbeditor.repositories;

/**
 * Exception qui est lancée lorsque l'on arrive pas à trouver les 
 * drivers de la base de données
 * @author franc
 *
 */
public class JdbcDriverNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de JdbcDriverNotFoundException
	 * @param driverName String qui est le nom des drivers
	 */
	public JdbcDriverNotFoundException(String driverName) {
        super("Unable to load driver "+driverName+". Is it available from the class path?");
    }
}
