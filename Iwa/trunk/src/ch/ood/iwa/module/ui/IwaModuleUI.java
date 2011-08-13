package ch.ood.iwa.module.ui;

import org.vaadin.dialogs.ConfirmDialog;

import ch.ood.iwa.ui.MainWindow;

/**
 * Things every Module UI supports
 * 
 * @author Mischa
 *
 */
public interface IwaModuleUI {
	
	/**
	 * Shows an error notification
	 * 
	 * @param caption
	 * @param message
	 */
	void showError(String caption, String message);
	
	/**
	 * Shows a "humanized" information notification 
	 * 
	 * @param caption
	 * @param message
	 */
	void showInfo(String caption, String message);
	
	/**
	 * Shows a "Yes" or "No" confirmation dialog
	 * 
	 * @param caption The dialog window caption
	 * @param message The question displayed on the dialog window
	 * @param callback The callback listener for retrieving the decision
	 */
	void showConfirmation(String caption, String message, ConfirmDialog.Listener callback);
	
	/**
	 * Provides Access to the main application window
	 * 
	 * @return
	 */
	MainWindow getMainWindow();
}
