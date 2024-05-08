package org.helmo.gbeditor.models;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Représente un numéro isbn
 * 
 *	Un numéro ISBN-10 est composé par 10 chiffres. Il commence par le numéro du groupe linguistique 
 *	visé (2 dans notre cas pour le français) suivi par l’identifiant de l’auteur qui correspondra aux 6 
 *	chiffres de votre matricule, suivi par 2 chiffres identifiant le livre. Le dernier chiffre est un code de 
 *	vérification calculé sur ce principe : On prend les 9 premiers chiffres de l’ISBN et on les multiplie par un poids allant de 10 à 2.
 * @author franc
 *
 */
public class Isbn {
	private String isbnNumber = "2-";
	
	/**
	 * Constructeur pour le numéro Isbn modifié par l'utilisateur
	 * @param isbnNumber String qui est le numéro isbn
	 */
	public Isbn(String isbnNumber) {
		this.isbnNumber = isbnNumber; 
	}
	
	/**
	 * Constructeur d'un numéro ISBN
	 * @param manager ManagerRepository qui va me permettre le lire le fichier json
	 */
	public Isbn(int nbrBook, int matricule){
		generateIsbn(nbrBook, matricule);
	}
	
	/**
	 * Méthode qui permet de changer le numéro Isbn
	 * @param isbnNumber String qui est le nouveau numéro Isbn
	 */
	public void setIsbnNumber(String isbnNumber) {
		this.isbnNumber = isbnNumber;
	}
	
	public String getIsbnNumber() {
		return this.isbnNumber;
	}
	
	/**
	 * Méthode qui permet de définir la suite du num�ro Isbn. 
	 * La méthode va lire dans le fichier json pour d�terminer le nombre de livre. 
	 * Les deux chiffres avant le numéro de contrôle seront le nombre de livre +1. 
	 * A la fin elle appelle la méthode generateLastNumberIsbn.
	 *
	 * @param nbrBook int qui est le nombre de livre créé par un auteur
	 * @param matricule int qui est le matricule de l'auteur
	 */
	private void generateIsbn(int nbrBook, int matricule){
		this.isbnNumber += String.valueOf(matricule) + "-";
		this.isbnNumber += nbrBook < 9 ? "0" + String.valueOf(nbrBook +1) + "-" :  String.valueOf(nbrBook +1) + "-";		
		this.isbnNumber += generateLastNumberIsbn();
	}
	
	/**
	 * Méthode qui permet de générer le dernier numéro du Isbn.
	 * Elle va prendre tous les numéros qui composent le numéro Isbn,
	 * Elle va multplier chaque chiffre en commençant par 10 puis faire -1 à
	 * chaque fois. 
	 * 
	 * return string qui est le dernier numéro isbn
	 */
	private String generateLastNumberIsbn(){
		int last = 0;
		int max = 10;
		String isbnNumeric = convertIsbnStringToInt();
		
		for (int i = 0; i < isbnNumeric.length(); i++) {
				last += Character.getNumericValue(isbnNumeric.charAt(i)) * max;
				max--;				
		}	
			
		last = 11 - (last % 11);
		return (last == 11 ? "0" : last == 10 ? "X" : last + "");
	}
	
	/**
	 * Méthode qui permet de retirer les tirets du numéro isbn
	 * @return String qui est le numéro isbn sans tirets
	 */
	private String convertIsbnStringToInt() {
		String isbnNumeric = "";
		
		for (int i = 0; i < isbnNumber.length()-1; i++) {
			if(i != 1 && i != 8 && i != 11) {
				isbnNumeric += Character.toString(isbnNumber.charAt(i));
			}
		}
		return isbnNumeric;
	}			
	
	/**
	 * Méthode qui donne la valeur réelle du dernier chiffre du numéro isbn
	 * 0 => 9 = nbr
	 * X = 10
	 * Y = 11
	 * @param isbnNumber String qui est le numéro isbn
	 * @return int qui est la valeur du dernier chiffre du num�ro isbn
	 */
	private int determinateLastNumber(final String isbnNumber) {
		char last = isbnNumber.charAt(isbnNumber.length() -1);
		int lastNumeric = 0;
		
		if(Character.compare(last, 'X') == 0) {
			lastNumeric =  10;
		}else {
			lastNumeric =  Character.getNumericValue(last);
		}	
		
		return lastNumeric;
	}
	
	/**
	 * Méthode qui permet de vérifier la validité d'un numéro Isbn
	 * @param msgError StringBuilder qui est l'éventuel message d'erreur
	 */
	public boolean checkIsbnNumber(StringBuilder msgError) {
		if(!this.isbnNumber.isEmpty()) {
			checkFormatIsbnNumber(msgError);
		}else {
			msgError.append("Numéro Isbn non valide \n");	
		}
		
		return msgError.toString().isEmpty();
	}
	
	/**
	 * Méthode qui permet de vérifier la validité du numéro Isbn
	 * @param msgError StringBuilder qui est le message d'erreur. 
	 */
	private void checkFormatIsbnNumber(final StringBuilder msgError) {
		if(!Pattern.matches("[0-9]-[0-9]{6}-[0-9]{2}-[0-9|X|Y]", this.isbnNumber)) {
			msgError.append("Format Isbn non valide \n");			
		}else {
			String lastCtrl = generateLastNumberIsbn();
			int last = determinateLastNumber(isbnNumber);
			compareLastNumberIsbn(determinateLastNumber(lastCtrl), last, msgError);
		}	
	}
	
	/**
	 * Méthode qui permet de comparer les derniers chiffres des nombres isbn pour vérifier si ils sont bien égaux.
	 * @param lastCtrl int qui est le dernier chiffre isbn calulé
	 * @param last int qui est le dernier chiffre isbn rentré par l'utilisateur
	 * @param msgError StringBuilder qui est le message d'erreur
	 */
	private void compareLastNumberIsbn(int lastCtrl, final int last, StringBuilder msgError) {
		if(lastCtrl != last) {msgError.append("Numéro de controle Isbn non valide \n");}
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(isbnNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}			
		if (obj == null) {
			return false;
		}			
		if (getClass() != obj.getClass()) {
			return false;
		}			
		Isbn other = (Isbn) obj;
		return isbnNumber.equals(other.getIsbnNumber());
	}
	
}
