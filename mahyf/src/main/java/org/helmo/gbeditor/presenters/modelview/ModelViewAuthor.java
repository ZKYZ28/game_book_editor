package org.helmo.gbeditor.presenters.modelview;

import org.helmo.gbeditor.models.Author;

/**
 * ModelView d'un auteur
 * @author franc
 *
 */
public class ModelViewAuthor {	
	private final Author author;
	
	/**
	 * Constructeur d'un ModelViewAuthor
	 * @param author Author qui est l'auteur que l'on souhaite convertir en ModelView
	 */
	public ModelViewAuthor(Author author) {		
		this.author = author;
	}

	/**
	 * Méthode qui permet de retourner le matricule de l'autuer
	 * @return String le matricule de l'auteur
	 */
	public String getMatricule() {
		return String.valueOf(this.author.getMatricule());
	}

	/**
	 * Méthode qui permet de retourner le prénom de l'autuer
	 * @return String le prénom de l'auteur
	 */
	public String getFirstName() {
		return this.author.getFirstName();
	}

	/**
	 * Méhtode qui permet de retourner le nom de l'auteur
	 * @return String le nom de l'auteur
	 */
	public String getName() {
		return this.author.getName();
	}
	
	/**
	 * Méthode qui permet d'obtenir le prénom + nom de l'auteur
	 * @return String qui est le prénom plus le nom de l'auteur
	 */
	public String getFullName() {
		return this.author.getAuthorFullName();
	}

}
