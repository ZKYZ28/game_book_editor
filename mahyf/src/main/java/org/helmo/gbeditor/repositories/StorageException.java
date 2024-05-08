package org.helmo.gbeditor.repositories;

/**
 * Exception qui est lancée lorsque l'on rencontre un problème lors 
 * d'une interaction avec la base de données
 * @author franc
 *
 */
public class StorageException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur de StorageException
	 * @param errormsg String qui est le message d'erreur
	 */
	public StorageException(String errormsg) {
        super(errormsg);
    }
}
