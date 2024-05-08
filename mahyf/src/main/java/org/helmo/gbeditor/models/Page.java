package org.helmo.gbeditor.models;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Représente les pages d'un livre
 * La page d'un livre est composée d'un numéro de page 
 * d'un text et d'une liste de choix.
 * @author franc
 *
 */

/**
 * JUSTIFICATION LIST<Choice>
 * J'ai utilisé une List car 
 * -Je voulais pouvoir accéder à un élément sur base de son index
 * - Mes éléments sont simplement ajoutés sur base de leur ordre d'insertion donc pas besoin
 * de trier. 
 * 
 * - Si j'avais utilisé un Set je n'aurais pas pu accéder à un élément sur base de son index
 * - Si j'avais utilisé un Map j'aurais dû supprimer et rajouter mon choix lorsque son numéro changeait
 * 
 * 
 * JUSTIFICATION LinkedList
 * En ayant utilisé List j'ai le choix entre ArrayList et LinkedList
 * J'ai utilisé une LinkedList car
 * - Je vais le plus souvent avoir besoin d'ajouter et supprimer des choix et les 
 * méthodes add et remove sont plus optimisées sur la LinkedList vu qu'on utilise des pointeurs. 
 * 
 * - Le get est en O(1) avec la ArrayList contre O(N) avec la LinkedList mais je ne vais 
 * utiliser le get que quand je dois sélectionner un choix à supprimer.  
 * 
 * 
 */
public class Page implements Comparable<Page>{
	private int numPage = 0;
	private String textPage;
	private List<Choice> choiceList;
		
	/**
	 * Constructeur d'une Page. Utiliser lors de la création de la page
	 * @param numPage int Numéro de la page
	 * @param textPage String text de la page
	 * @param idLinkedBook int Id du livre auquel est lié la page
	 */
	public Page(final int  numPage, final String textPage) {
		this.textPage = textPage != null ? textPage : "Texte de la page manquant";
		this.numPage = numPage;
		this.choiceList =  new LinkedList<Choice>(); 
	}
	
	/**
	 * Constructeur d'une Page. Utiliser lors du load d'une page 
	 * depuis la Base De Données.
	 * @param idPage int Id de la page
	 * @param numPage int Numéro de la page
	 * @param textPage String text de la page
	 * @param idLinkedBook int Id du livre auquel est lié la page
	 * @param choiceList List<Choice> Liste des choix liés à cette page
	 */
	public Page(final int numPage, final String textPage, final List<Choice> choiceList) {
		this.numPage = numPage;
		this.textPage = textPage != null ? textPage : "Texte de la page manquant";
		this.choiceList = choiceList;
	}
	
	/**
	 * Constructeur de base de la Page.
	 */
	public Page() {
		
	}
		
	public String getTextPage() {
		return this.textPage;
	}
	
	public int getNumPage() {
		return this.numPage;
	}
	
	public List<Choice> getChoiceList(){
		return this.choiceList;
	}
	
	/**
	 * Méthode qui permet de vérifier que les informations fournies pour 
	 * la création d'une page sont valides
	 * @param numPage String numéro de la page
	 * @param text String texte de la page
	 * @param msgError StringBuilder message d'erreur
	 * @return boolean True si les informations sont valides sinon false
	 */
	public boolean checkPageValidity(final String numPage, final String text,  StringBuilder msgError) {
		if(checkIfFieldNotEmpty(numPage, text, msgError)) {
			if(!Pattern.matches("[0-9]{1,2}$", numPage)) {
				msgError.append("Numéro de page non valide\n");
			}
		}		
		return msgError.toString().isEmpty();
	}
	
	/**
	 * Méthode qui permet de vérifier que les champs ne sont pas vides
	 * @param numPage String numéro de la page
	 * @param text String texte de la page
	 * @param msgError StringBuilder message d'erreur
	 * @return boolean True si les champs ne sont pas vides sinon false
	 */
	private boolean checkIfFieldNotEmpty(final String numPage, final String text, StringBuilder msgError) {
		if(numPage.isEmpty() || text.isEmpty()) {
			msgError.append("Veuillez remplir tous les champs \n");
			return false;
		}
		return true;
	}

	/**
	 * Méthode qui permet d'ajouter un choix à la page
	 */
	public void addNewChoiceToPage(Choice choice) {
		this.choiceList.add(choice);
	}
	
	/**
	 * Méthode qui permet d'augmenter le numéro de la page de 1
	 */
	public void upNumPage(){
		this.numPage += 1;
	}
	
	/**
	 * Méthode qui permet de diminuer le numéro de la page de 1
	 */
	public void downNumPage() {
		this.numPage -= 1; 
	}
	
	/**
	 * Méthode qui permet d'augmenter le NumFromPage ou le NumGoToPage
	 * des tous les choix liés à la page qui en auraient besoin.
	 * 
	 * @param numPage int qui est le numéro de la page qui a été rajoutée
	 * @param variation int qui indique de combien on doit changer le 
	 * numéro de page. Peut être 1 ou -1
	 */
	public void updateChoiceNumPage(final int numPage, int variation) {
		for(Choice choice : this.choiceList) {
			if(choice.getNumFromPage() >= numPage) {
				choice.setNumFromPage(choice.getNumFromPage() + variation);
			}
			
			if(choice.getNumGoToPage() >= numPage) {
				choice.setNumGoToPage(choice.getNumGoToPage() + variation);
			}
		}
	}
		

	/**
	 * Méthode qui permet de supprimer les choix qui renvoient vers la page
	 * @param numPageDelete int le numéro de la page qui est supprimée
	 */
	public void deleteChoiceIfPointToDeletePage(final int numPageDelete) {
		int i;
		for (i = 0; i <  this.choiceList.size(); i++) {
			if(this.choiceList.get(i).getNumGoToPage() == numPageDelete) {
				if(i+1 < this.choiceList.size()) {
					choiceList.get(i+1).downNumChoice();
				}				
				this.choiceList.remove(i);			
				i--;		
			}
		}		
	}
	
	/**
	 * Méthode qui permet de savoir, pour une page précise,
	 * le nombre de choix qui renvoie vers une autre page 
	 * @param numPage int le numéro de la page dont on souhaite connaître 
	 * le nombre de choix qui pointent vers elle. 
	 * @return int le nombre de choix de la page courrante 
	 * qui pointent vers une page précise 
	 */
	public int countNumberOfChoiceLinkToPage(int numPage) {
		int nbrLinkedChoice = 0;
		for (Choice choice : this.choiceList) {
			if(choice.getNumGoToPage() == numPage) {
				nbrLinkedChoice++;
			}
		}
		
		return nbrLinkedChoice;
	}
	
	/**
	 * Méthode qui permet de supprimer un choix et 
	 * de diminuer le numéro des choix supérieur de 1 
	 * @param numCurrentChoice int qui est le numéro du choix courrant
	 */
	public void deleteChoice(int numCurrentChoice) {		
		for (int i = numCurrentChoice ; i <  choiceList.size(); i++) {
			choiceList.get(i).downNumChoice();
		}		 
		
		this.choiceList.remove(numCurrentChoice-1);
		
	}
	
	@Override
	public int compareTo(Page page) {
		return (page == null) ? Integer.MIN_VALUE : this.numPage - page.numPage;		
	}
}
