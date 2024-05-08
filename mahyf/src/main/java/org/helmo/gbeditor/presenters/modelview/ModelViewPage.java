package org.helmo.gbeditor.presenters.modelview;

import java.util.ArrayList;
import java.util.List;
import org.helmo.gbeditor.models.Choice;
import org.helmo.gbeditor.models.Page;

/**
 * ModelView d'une page
 * @author franc
 *
 */
public class ModelViewPage implements Comparable<ModelViewPage>{
	private final  int numPage;
	private final  String textPage;
	private final List<ModelViewChoice> choiceList;
	
	/**
	 * Constructeur d'un ModelViewPage
	 * @param page Page qui est la page que l'on souhaite convertir en ModelViewPage
	 */
	public ModelViewPage(Page page) {
		this.numPage = page.getNumPage();
		this.textPage = page.getTextPage();		
		this.choiceList = initListChoice(page.getChoiceList());
	}
	
	@Override
	public int compareTo(ModelViewPage page) {
		return (page == null) ? Integer.MIN_VALUE : this.numPage - page.numPage;		
	}
	
	private List<ModelViewChoice> initListChoice(List<Choice> choiceList) {
		List<ModelViewChoice> listChoice = new ArrayList<ModelViewChoice>();
		for(Choice choice : choiceList) {
			listChoice.add(new ModelViewChoice(choice));
		}
		return listChoice;
	}

	public int getNumPage() {
		return numPage;
	}


	public String getTextPage() {
		return textPage;
	}

	public List<ModelViewChoice> getChoiceList() {
		return choiceList;
	}
}
