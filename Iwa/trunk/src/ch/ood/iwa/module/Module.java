package ch.ood.iwa.module;

import java.util.Set;

import org.vaadin.appfoundation.view.View;

import ch.ood.iwa.module.ui.ModuleView;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Tree;

/**
 * The public interface of every module.
 * A module mainly groups Views for the menu.
 * 
 * @author Mischa
 *
 */
public interface Module {
	
	/**
	 * Registers a view with the module
	 * 
	 * @param view
	 */
	void registerView(ModuleView view);
	
	/**
	 * Returns the translated name of the module
	 * 
	 * @return
	 */
	String getDisplayName();
	
	/**
	 * Return the (not translated) name of the module 
	 * 
	 * @return
	 */
	String getName();
	

	/**
	 * Returns the icon for the module
	 * 
	 * @return
	 */
	ThemeResource getIcon();		
	
	/**
	 * Returns all view names as {@link Tree}
	 * 
	 * @return
	 */
	Tree getViewNamesAsTree();
	
	/**
	 * Returns all view names a {@link Set}
	 * 
	 * @return
	 */
	Set<String> getViewNamesAsSet();
	
	/**
	 * Returns a view by its name
	 * 
	 * @param name
	 * @return
	 */
	View getViewByName(String name);
		
	/**
	 * Returns all views of this module
	 */
	Set<View> getAllViews();
}
