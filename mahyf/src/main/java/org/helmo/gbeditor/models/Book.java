package org.helmo.gbeditor.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Représente un livre.
 * Un livre est composé d'un auteur, d'un titre,
 * d'un isbn, d'un résumé et d'une liste de pages
 * 
 * On peut déterminé si le livre est publié ou non 
 * avec isPublished.
 * @author franc
 *
 */

/**
 * JUSTIFICATION LIST<Page>
 * Pour la liste des pages j'ai utilisé un List car :
 * - Je devais pouvoir accéder à un élément précis sur base de son index, donc pas de Set
 * - La SortedMap est une bonne option aussi mais dans le cas d'une inséretion de page
 * à un autre endroit que à la fin il aurait fallu que je supprime les pages qui changent de numéro
 * de map pour les rajouter. 
 * 
 * J'ai donc utilisé un simple List ce qui me permet d'ajouter une page et dans le cas où elle n'est 
 * pas à la fin je fais un Collection.sort() vu que j'ai redéfini compareTo dans l'objet page. 
 * 
 * Si j'avais utilisé une map j'aurais dû supprimer les pages et les rajouter mais le sort aurait été plus optimisé
 * et avec la List je n'ai pas besoin de supprimer mes pages et de les rajouter mais le sort est moins optimisé.
 * 
 * JUSTIFICATION ArrayList
 * En ayant utilisé List j'ai le choix entre ArrayList et LinkedList
 * J'ai utilisé une ArrayList car
 * - Je vais le plus souvent avoir besoin de récupérer une page précise pour lui ajouter un choix 
 * et le get d'une ArrayList est en O(1) contre O(N) pour la LinkedList
 * 
 * -Le remove est en O(N) pour les deux et le add aussi. Cependant le add est plus optimisé sur la LinkedList. 
 * 
 * 
 */
public class Book {
	private Author author;
	private String title;
	private Isbn isbn;
	private String resume;
	private boolean isPublished;
	private List<Page> listPage;
	
	/**
	 * Constructeur pour copier un livre.
	 * @param book Book le livre que l'on veut copier
	 */
	public Book(Book book) {
		this.author = book.getAuthor();
		this.title = book.getTitle();
		this.isbn = new Isbn(book.getIsbn().getIsbnNumber());
		this.resume = book.getResume();
		this.listPage = new ArrayList<Page>(book.getListPage()) ;
	} 
	
	/**
	 * Constructeur d'un Book.Utiliser lors du load en Bd des 
	 * informations complètes du livre.
	 * @param idBook int qui est l'id du livre
	 * @param author Author qui est l'auteur du livre
	 * @param title String qui est le titre du livre
	 * @param isbn Isbn qui est le numéro isbn du livre
	 * @param resume String qui est le résumé du livre
	 * @param isPublished Boolean qui indique si le livre est publié ou non
	 * @param listPage SortedSet qui est l'ensemble des pages du livre
	 */
	public Book(final Author author, final String title, final Isbn isbn, final String resume, final boolean isPublished,final List<Page> listPage) {
		this.author = author;
		this.title = title.length() > 150 ? title.substring(0, 150) : title;
		this.isbn = isbn;
		this.resume = resume.length() > 500 ? resume.substring(0, 500) : resume;
		this.isPublished = isPublished;
		this.listPage = listPage == null ? new ArrayList<Page>() : listPage ;
	}
	
	/**
	 * Constructeur d'un Book. Utiliser pour les vérifications
	 */
	public Book() {
	}
	
	public List<Page> getListPage(){
		return this.listPage;
	}
		
	public Author getAuthor() {
		return this.author;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Isbn getIsbn() {
		return this.isbn;
	}
	
	public String getResume() {
		return this.resume;
	}
	
	/**
	 * Méthode qui permet de savoir si le livre est publié ou non
	 * @return true si le livre est publié, sinon false
	 */
	public boolean isBookPublished() {
		return this.isPublished;
	}
	
	/**
	 * Méthode qui permet d'ajouter une List<Page> au Book
	 * @param listPage List<Page> qui est la liste des pages du Book
	 */
	public void addListPage(List<Page> listPage) {
		this.listPage = listPage;
	}
	
	/**
	 * Méthode qui permet de passer le livre en statut publié
	 */
	public void publishBook() {
		this.isPublished = true;
	}
	
	/**
	 * Méthode qui renvoie la page courante sur base de son index
	 * @param indexCurrentPage int qui est l'index de la page courante
	 * @return Page qui est la page courante
	 */
	public Page getCurrentPage(int indexCurrentPage) {
		return this.listPage.get(indexCurrentPage);
	}
	
	/**
	 * Méthode qui permet d'ajouter une page au livre
	 * @param page Page qui est la page que l'on veut ajouter
	 */
	public void addPage(Page page) {
		this.listPage.add(page);
	}
	
	/**
	 * Méthode qui permet de mettre à jour les 
	 * informations d'un livre
	 * @param title String le titre du livre
	 * @param isbn String l'isbn du livre
	 * @param resume String le résumé du livre
	 */
	public void updateBookInforamtion(String title, String isbn, String resume) {
		this.title = title;
		this.isbn.setIsbnNumber(isbn);
		this.resume = resume;
	}
	
	/**
	 * Méthode qui permet de vérifier que le titre et la 
	 * description du livre sont valides
	 * @param title String qui est le titre du livre
	 * @param isbn Isbn qui est le nouveau numéro isbn du livre
	 * @param resume String qui est la description du livre
	 * @param msgError StringBuilder qui est le message d'erreur
	 */
	public boolean checkBook(final String title, final Isbn isbn, final String resume, StringBuilder msgError) {
		checkIfContentNotEmpty(title, resume, msgError);
		checkSizeContent(title, resume, msgError);	
		isbn.checkIsbnNumber(msgError);
		
		return msgError.toString().isEmpty();
	}
	
	
	/**
	 * Méthode qui permet de vérifier que les champs ne sont pas vides
	 * @param title String qui est l titre du livre
	 * @param isbn String qui est le numéro isbn du livre
	 * @param resume String qui est la description du livre
	 * @param msgError String qui est le message d'erreur. 
	 * @return Boolean true si les champs ne sont pas vides, sinon false.
	 */
	private void checkIfContentNotEmpty(final String title, final String resume, StringBuilder msgError) {
		if(title.isEmpty() || resume.isEmpty()) {msgError.append("Veuillez remplir tous les champs. \n");}
	}
	
	/**
	 * Méthode qui permet de vérifier la taille des champs. 
	 * @param title String qui est l titre du livre
	 * @param resume String qui est la description du livre
	 * @param msgError StringBuilder qui est le message d'erreur. 
	 */
	private void checkSizeContent(final String title, final String resume, StringBuilder msgError) {
		if((title.length() > 150)) {
			msgError.append("Maximum 150 caractères pour le titre. \n");
		}	
		
		if((resume.length() > 500)) {
			msgError.append("Maximum 500 caractères pour la description. \n");
		}		
	}	
	
	
	/**
	 * Méthode qui permet de gérer l'insertion d'une page dans le livre.
	 * Si les informations sont valides la page est ajoutée.
	 * @param numPage int le numéro de la page qu'on souhaite insérer
	 * @param textPage String qui est le text de la page que l'on souhaite insérer
	 * @param msgError StringBuilder qui est le message d'erreur
	 * @return boolean True si la page est insérer avec succès sinon false.
	 */
	public boolean managerInsertPage(final int numPage, final String textPage, final StringBuilder msgError) {
		if(this.listPage.size() == 0) {
			checkIfPageIsFirst(numPage, msgError);		
		}else {
			if(!isSideOfAnotherPage(numPage)) {
				msgError.append("La page doit se trouver avant ou après une page existante \n");
			}
		}
		if(msgError.toString().isEmpty()) {this.listPage.add(new Page(numPage, textPage));}
		
		checkIfNeedToSortPages(numPage);
		return msgError.toString().isEmpty();
	}
	
	/**
	 * Méthode qui vérifie que la première page du livre porte bien le numéro 1.
	 * @param numPage
	 * @param msgError
	 * @return
	 */
	private boolean checkIfPageIsFirst(final int numPage, final StringBuilder msgError) {
		if((numPage != 1)) {
			msgError.append("La première page doit porter le numéro 1 \n");
		}			
		
		return msgError.toString().isEmpty();
	}
	
	
	/**
	 * Méthode qui vérifie si la page qu'on souhaite insérer se trouve
	 * à coté d'une autre.
	 * @param numPage
	 * @param textPage
	 * @return
	 */
	private boolean isSideOfAnotherPage(final int numPage) {
		boolean sideOfAnother = false;
		
		for(Page page : this.listPage) {
			if(page.getNumPage() == numPage -1 || page.getNumPage() == numPage + 1) {								
				sideOfAnother = true;
			}
			
			page.updateChoiceNumPage(numPage, 1);			
			
			if(page.getNumPage() >= numPage) {
				page.upNumPage();
			}
		}
		
		return sideOfAnother;
	}
	
	
	/**
	 * Méthode qui permet de vérifier que si des pages 
	 * renvoient vers la page en question
	 * @param numPage int : Le numéro de la page de laquelle on veut savoir si des choix sont liés
	 * @return Boolean true si des choix sont liés sinon false
	 */
	public boolean checkIfChoiceLinkToPage(final int numPage) {
		return getNbrLinkedChoice(numPage) > 0;
	}

	/**
	 * Méthode qui permet de savoir combien de choix renvoie vers un page 
	 * @param numPage int le numéro de la page
	 * @return int le nombre de choix qui renvoie à cette page
	 */
	public int getNbrLinkedChoice(int numPage) {
		int nbrLinkedChoice = 0;
		for(Page page : this.listPage) {
			nbrLinkedChoice += page.countNumberOfChoiceLinkToPage(numPage);
		}
		return nbrLinkedChoice;
	}

	/**
	 * Méthode qui permet de supprimer la page d'un livre
	 * Elle va appeller deleteChoiceIfPointToDeletePage pour 
	 * supprimer les éventuels choix liés.
	 * Elle va appeller downNumPage pour diminuer le numéro 
	 * de page de 1 pour les page ayant un
	 * numéro de page supérieur à numPage.
	 *  
	 * @param numPage int qui est le numéro de la page que l'on souhaite supprimer 
	 */ 
	public void deletePage(final int indexPage) {
		for(final Page page : this.listPage) {		
			page.deleteChoiceIfPointToDeletePage(indexPage + 1);
		}		
		this.listPage.remove(indexPage);					
		downNumPage(indexPage +1);		
		checkIfNeedToSortPages(indexPage +1);
	}
	
	/**
	 * Méthode qui permet de réduire le numéro de page de 1 de toutes les pages 
	 * qui ont un numéro de page supérieur à numPage
	 * @param numPage
	 */
	private void downNumPage(final int numPage) {
		for(final Page page : this.listPage) {	
			if(page.getChoiceList().size() != 0) {					
				page.updateChoiceNumPage(numPage, -1);
			}	
			
			if(page.getNumPage() > numPage) {							
				page.downNumPage();
			}
		}
	}

	/**
	 * Méthode qui vérifie si une page existe.
	 * @param numPage int : le numéro de la page dont on veut vérifier l'existence
	 * @return boolean true si la page existe sinon false
	 */
	public boolean checkIfPageExist(final int numPage, StringBuilder msgError) {
		boolean pageExist = false;
		for(final Page page : this.listPage) {
			if(page.getNumPage() == numPage) {				
				pageExist = true;
			}
		}
		
		if(!pageExist) {
			msgError.append("La page vers laquelle le choix pointe n'existe pas. \n");
		}
		
		return pageExist;
	}
	

	/**
	 * Méthode qui permet de savoir si le livre contient au moins une page
	 * @return boolean true si le livre contient au moins une page sinon false
	 */
	public boolean checkIfBookCanBePublished() {
		return this.listPage.size() > 0;
	}
	
	/**
	 * Méthode qui permet de vérifier si les choix doivent être triés 
	 * Si c'est le cas elle va les trier sinon elle ne va rien faire. 
	 * @param numPage int qui est le numéro de la page dont on souhaite 
	 * trier les choix
	 */
	private void checkIfNeedToSortPages(int numPage) {
		if(this.listPage.size()+1 != numPage) {
			Collections.sort(this.listPage);
		}
	}

	@Override
	public int hashCode() {
		return isbn.hashCode();
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
		Book other = (Book) obj;
		return isbn.equals(other.isbn);
	}
}
