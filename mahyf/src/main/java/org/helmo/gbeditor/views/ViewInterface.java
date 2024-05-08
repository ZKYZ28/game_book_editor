package org.helmo.gbeditor.views;

/**
 * Interface qui est implémentée pour toutes les vues
 * @author franc
 *
 */
public interface ViewInterface {
	
	/**
	 * Permet de rendre al vue visible ou non 
	 * et permet d'activer la vue ou non
	 * @param isVisible boolean true si la vue doit être visible sinon false
	 * @param isDisable boolean true si la vue doit être désactivée sinon false
	 */
	public void setVisibleView(boolean isVisible, boolean isDisable);
}
