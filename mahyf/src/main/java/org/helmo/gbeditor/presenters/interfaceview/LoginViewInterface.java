package org.helmo.gbeditor.presenters.interfaceview;

import org.helmo.gbeditor.presenters.LoginPresenter;
import org.helmo.gbeditor.views.ViewInterface;

/**
 * Interface de la LoginView
 * @author franc
 *
 */
public interface LoginViewInterface extends ViewInterface{

	/**
	 * Méthode qui peremt d'assigner un le presenter de la vue
	 * @param p qui est le présente de la LoginView
	 */
	public void setPresenter(LoginPresenter p);
}
