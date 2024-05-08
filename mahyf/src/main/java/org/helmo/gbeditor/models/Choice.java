package org.helmo.gbeditor.models;

import java.util.regex.Pattern;

/**
 * Représente un choix. 
 * Un choix est composé d'un numéro,
 * de texte, d'un numéro de page source
 * et du numéro de la page vers lequelle il renvoie
 * @author franc
 *
 */
public class Choice {
	private int numChoice;
	private String textChoice;
	private int numFromPage;
	private int numGoPage;
		
	/**
	 * Constructeur de base pour Choice.
	 */
	public Choice() {
	}
		
	/**
	 * Consrtructeur de Choice
	 * @param numChoice int qui est le numéro du choix
	 * @param textChoice String qui est le text du choix
	 * @param numFromPage int qui est le numéro de la page d'où vient le choix
	 * @param numGoPage int qui est le numéro de la page vers où pointe le choix
	 */
	public Choice(int numChoice, String textChoice, int numFromPage, int numGoPage) {
		this.numChoice = numChoice;
		this.textChoice = textChoice;
		this.numFromPage = numFromPage;
		this.numGoPage = numGoPage;
	}
	
	public int getNumChoice() {
		return this.numChoice;
	}
	
	public String getTextChoice() {
		return textChoice;
	}

	public int getNumFromPage() {
		return numFromPage;
	}
	
	/**
	 * Méthode qui return le numéro de la page vers laquelle le choix renvoie
	 * @return int numéro de la page vers laquelle le choix renvoie
	 */
	public int getNumGoToPage() {
		return numGoPage;
	}
	
	/**
	 * Méthode qui permet de diminer le numéro du choix de 1
	 */
	public void downNumChoice() {
		this.numChoice -= 1;
	}
	
	/**
	 * Méthode qui permet de modifer le numéro de la page
	 * vers laquelle le choix renvoie
	 * @param numGoToPage int le nouveau numéro de la page
	 */
	public void setNumGoToPage(int numGoToPage) {
		this.numGoPage = numGoToPage;
	}
	
	/**
	 * Méthode qui permet de modifer le numéro de la page
	 * de laquelle le choix vient
	 * @param numGoToPage int le nouveau numéro de la page
	 */
	public void setNumFromPage(int numFromPage) {
		this.numFromPage = numFromPage;
	}

	/**
	 * Méthode qui permet de vérifier que les inforamtions du Choice
	 * sont correctes
	 * @param textChoice String qui est le texte du Choice
	 * @param numGoToPage String qui est le numéro de la Page vers où le Choice va
	 * @param numCurrentPage int numéro de la page auquelle le Choice est lié
	 * @param msgError StringBuilder le message d'erreur
	 * @return True si les inforamtions sont correctes sinon false
	 */
	public boolean checkChoiceValidity(String textChoice, String numGoToPage, int numCurrentPage, StringBuilder msgError) {
		if(checkIfCurrentPageIsNotZero(numCurrentPage, msgError)) {
			checkIfFieldNotEmpty(textChoice, numGoToPage, numCurrentPage, msgError); 												
		}	
		
		return msgError.toString().isEmpty();
	}
	
	/**
	 * Méthode qui permet de vérifier que les champs ne sont pas vides
	 * @param textChoice String qui est le texte du Choice
	 * @param numGoToPage String qui est le numéro de la Page vers où le Choice va
	 * @param msgError StringBuilder le message d'erreur
	 * @return True si les champs ne sont pas vides sinon false
	 */
	private void checkIfFieldNotEmpty(String textChoice, String numGoToPage, int numCurrentPage, StringBuilder msgError) {
		if(textChoice.isEmpty() || numGoToPage.isEmpty()) {
			msgError.append("Veuillez remplir tous les champs \n");
		}
		
		if(msgError.toString().isEmpty()) {
			if(checkNumGoToPageFormat(numGoToPage, msgError)) {
				checkIfPagesAreNotSame(numGoToPage, numCurrentPage, msgError);
			}	
		}
	}
	
	/**
	 * Méthode qui permet de vérifier que la page de départ du Choice est indiquée
	 * @param numCurrentPage int numéro de la page auquelle le Choice est lié
	 * @param msgError StringBuilder le message d'erreur
	 * @return True si la page courante est correctement indiquée sinon false
	 */
	private boolean checkIfCurrentPageIsNotZero(int numCurrentPage, StringBuilder msgError) {
		if(numCurrentPage == 0) {
			msgError.append("Veuillez sélectionner la page à laquelle vous voulez ajouter le choix \n");
		}
		
		return msgError.toString().isEmpty();
	}
	
	/**
	 * Méthode qui permet de vérifier que le format de page vers laquelle on veut aller 
	 * est correct
	 * @param numGoToPage String qui est le numéro de la Page vers où le Choice va
	 * @param msgError StringBuilder le message d'erreur
	 * @return True si le numéro de page est correctement formaté sinon false
	 */
	private boolean checkNumGoToPageFormat(String numGoToPage, StringBuilder msgError) {
		if(!Pattern.matches("[0-9]{1,2}$", numGoToPage)) {
			msgError.append("Envoie à la page : doit uniqument contenir des chiffres\n");
		}
		return msgError.toString().isEmpty();
	}
	
	/**
	 * Méthode qui permet de vérifier que la page de départ est 
	 * différente de la page d'arrivée
	 * @param numGoToPage String qui est le numéro de la Page vers où le Choice va
	 * @param numCurrentPage int numéro de la page auquelle le Choice est lié
	 * @param msgError StringBuilder le message d'erreur
	 */
	private void checkIfPagesAreNotSame(String numGoToPage, int numCurrentPage, StringBuilder msgError) {
		if(Integer.parseInt(numGoToPage) == numCurrentPage) {
			msgError.append("Une page ne peut pas avoir un choix qui la référence elle-même\n");
		}
	}
}
