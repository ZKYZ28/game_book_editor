package org.helmo.gbeditor.models;

import java.util.regex.Pattern;

/**
 * Représente un auteur, 
 * Un auteur est composé d'un prénom, d'un nom et d'un matricule.
 * @author franc
 *
 */
public class Author {
	private  String firstName;
	private  String name;
	private  int matricule;
	
	/**
	 * Constructeur de l'Author. 
	 * @param firstName String qui est le prénom de l'auteur
	 * @param name String qui est le nom de l'auteur
	 * @param matricule int qui est le matricule de l'auteur
	 */
	public Author(final String firstName, final String name, final int matricule) {
		this.firstName = firstName == null ? "Auteur" : firstName;
		this.name = name == null ? "Anonyme" : name; 
		this.matricule = matricule;
	}
	
	/**
	 * Constructeur de base de l'auteur.
	 */
	public Author(){
	}
	
	/**
	 * Méthode qui retourne le nom complet de l'auteur
	 * @return String qui est la concaténation du prénom et du nom de l'auteur 
	 */
	public String getAuthorFullName() {
		return this.getFirstName() + " " + this.getName();
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getMatricule() {
		return this.matricule;
	}
	
	
	/**
	 * Méthode qui permet de vérifier la validité du nom et prénom de l'utilisateur
	 * @param firstName String qui est le prénom de l'auteur
	 * @param name String qui est le nom de l'auteur
	 * @return Boolean true si les informations sont valides sinon false
	 */
	public boolean checkValidityUser(String firstName, String name, String matricule, StringBuilder msgError) {
		if(checkIfFieldNotEmpty(firstName, name, matricule, msgError)) {
			checkFullName(firstName, name, msgError);
			checkMatricule(matricule, msgError);
		}	
		return msgError.toString().isEmpty();
	}
	
	
	/**
	 * Méthode qui permet de vérifier que les champs de 
	 * l'utilisateur ne sont pas vides. 
	 * @param firstName String qui est le prénom de l'auteur
	 * @param name String qui est le nom de l'auteur
	 * @param msgError StringBuilder qui est le message d'erreur.
	 * @return Boolean true si les champs ne sont pas vides sinon false
	 */
	private boolean checkIfFieldNotEmpty(String firstName, String name, String matricule, StringBuilder msgError) {
		if(firstName.isEmpty() || name.isEmpty() || matricule.isEmpty()) {
			msgError.append("Veuillez remplir tous les champs \n");
			return false;
		}
		return true;
	}
	
	/**
	 * Méthode qui permet de vérifier que la matricule de l'utilisateur est bien valide
	 * @param matricule String qui est le matricule de l'auteur
	 * @param msgError StringBuilder qui est le message d'erreur. 
	 */
	private void checkMatricule(String matricule, StringBuilder msgError) {
		if(!Pattern.matches("[0-9]{6}", matricule)) {
			msgError.append("Matricule non valide, 6 chiffres \n");
		}
	}
	
	/**
	 * Méthode qui permet de vérifier la validité du nom et prénom de l'auteur
	 * @param firstName String qui est le prénom de l'auteur
	 * @param name String qui est le nom de l'auteur
	 * @param msgError StringBuilder qui est le message d'erreur.
	 */
	private void checkFullName(String firstName, String name, StringBuilder msgError){
		if(!Pattern.matches("(?i)(^[a-z])((?![-]$)[a-z-ç]){0,24}$", firstName)) {
			msgError.append("Prénom non valide\n");
		}
		
		if(!Pattern.matches("(?i)(^[a-z])((?![-]$)[a-z-]){0,24}$", name)) {
			msgError.append("Nom non valide\n");
		}
	}

}
