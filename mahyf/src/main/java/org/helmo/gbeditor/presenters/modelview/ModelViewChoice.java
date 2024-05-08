package org.helmo.gbeditor.presenters.modelview;

import org.helmo.gbeditor.models.Choice;

/**
 * ModelView d'un choix
 * @author franc
 *
 */
public class ModelViewChoice{	
	private final Choice choice;
	
	/**
	 * Constructeur d'un ModelViewChoice
	 * @param choice Choice qui est le choix que l'on souhaite convertir en ModelViewChoice
	 */
	public ModelViewChoice(Choice choice) {
		this.choice = choice;
	}
	
	/**
	 * Méthode qui permety de retourner le numéro du choix 
	 * @return in le numéro du choix
	 */
	public int getNumChoice() {
		return this.choice.getNumChoice();
	}


	/**
	 * Méthode qui permet de retourner le texte du choix
	 * @return String le texte du choix
	 */
	public String getTextChoice() {
		return this.choice.getTextChoice();
	}


	/**
	 * Méthode qui permet de retourner le numéro de la page d'où le 
	 * choix vient
	 * @return int le numéro de la page d'où le choix vient
	 */
	public int getNumFromPage() {
		return this.choice.getNumFromPage();
	}

	/**
	 * Méthode qui permet de retourner le numéro de la page vers où 
	 * pointe le choix 
	 * @return int le numéro de la page vers où pointe le choix
	 */
	public int getNumGoPage() {
		return this.choice.getNumGoToPage();
	}
}
