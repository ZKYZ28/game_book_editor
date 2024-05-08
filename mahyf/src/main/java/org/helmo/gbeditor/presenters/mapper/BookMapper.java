package org.helmo.gbeditor.presenters.mapper;

import java.util.ArrayList;
import java.util.List;

import org.helmo.gbeditor.models.Author;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Choice;
import org.helmo.gbeditor.models.Isbn;
import org.helmo.gbeditor.models.Page;
import org.helmo.gbeditor.presenters.modelview.ModelViewBook;
import org.helmo.gbeditor.presenters.modelview.ModelViewChoice;
import org.helmo.gbeditor.presenters.modelview.ModelViewPage;

/**
 * Permets de convertir des ModelView vers des Model
 * et des Model vers des ModelView
 * @author franc
 *
 */
public class BookMapper {

	/**
	 * Méthode qui permet de convertir un ensemble de Book en ModelViewBook
	 * @param librabyBooks ensemble des Book
	 * @return List<ModelViewBook> ensemble des livres en ModelViewBook
	 */
	public static List<ModelViewBook> convertBookToModelView(List<Book> librabyBooks){
		List<ModelViewBook> listBookModelView = new ArrayList<ModelViewBook>();	 
		
		for(Book book : librabyBooks){
			listBookModelView.add(new ModelViewBook(book));
		}			
		return listBookModelView;
	}
	
	/**
	 * Méthode qui permet de convertir un ensemble de Page en ModelViewPage
	 * @param listPage ensemble des Page
	 * @return SortedSet<ModelViewPage> ensemble des pages convertient
	 */
	public static List<ModelViewPage> convertPageToPageModelView(List<Page> listPage){
	List<ModelViewPage> listPageViewModel = new ArrayList<ModelViewPage>();
	
	for(Page page : listPage) {
		listPageViewModel.add(new ModelViewPage(page));
	}

	return listPageViewModel;
	}
	
	/**
	 * Méthode qui permet de convertir un ensemble de Choice en ModelViewChoice
	 * @param choiceList List<Choice> ensemble des Choice
	 * @return List<ModelViewChoice> ensemble des Choice convertit
	 */
	public static List<ModelViewChoice> convertChoiceToChoiceModelView(List<Choice> choiceList){
		List<ModelViewChoice> listChoiceVM = new ArrayList<ModelViewChoice>();

			for(Choice choice : choiceList) {
				listChoiceVM.add(new ModelViewChoice(choice));
			}
	
		return listChoiceVM;
	}
	
	/**
	 * Méthode qui permet de convertir un ModelViewBook en Book
	 * @param bookV ModelViewBook le livre que l'on veut convertir
	 * @return Book qui est le livre
	 */
	public static Book convertBookModelViewToBook(ModelViewBook bookV){
		Author author = new Author(bookV.getAuthor().getFirstName(), bookV.getAuthor().getName(), Integer.parseInt(bookV.getAuthor().getMatricule()));
		String title = bookV.getTitle();
		Isbn isbn = new Isbn(bookV.getIsbn());
		String resume = bookV.getResume();
		boolean isPublished = bookV.isBookPublished();
		List<Page> listPage = converPageModelViewToPage(bookV.getListPage());
		
		return new Book(author, title, isbn, resume, isPublished, listPage);
	}
	
	/**
	 * Méthode qui permet de convertir un ensemble de ModelViewPage en Page
	 * @param listPageV ensemble des ModelViewPage
	 * @return SortedSet<Page> ensemble des pges converties
	 */
	private static List<Page> converPageModelViewToPage(List<ModelViewPage> listPageV){
		List<Page> listPage = new ArrayList<Page>();
		
		for(ModelViewPage pageV : listPageV) {
			listPage.add(new Page(pageV.getNumPage(), pageV.getTextPage(), convertChoiceModelViewToChoice(pageV.getChoiceList())));
		}
		
		return listPage;
	}
	
	/**
	 * Méthode qui permet de convertir un ensemble de ModelViewChoice en Choice
	 * @param listChoiceV ensemble ModelViewChoice
	 * @return List<Choice> ensemble des Choice convertit
	 */
	private static List<Choice> convertChoiceModelViewToChoice(List<ModelViewChoice> listChoiceV){
		List<Choice> listChoice = new ArrayList<Choice>();
		
		for(ModelViewChoice choiceV : listChoiceV) {
			listChoice.add(new Choice(choiceV.getNumChoice(), choiceV.getTextChoice(), choiceV.getNumFromPage(), choiceV.getNumGoPage()));
		}
		
		return listChoice;
	}
}
