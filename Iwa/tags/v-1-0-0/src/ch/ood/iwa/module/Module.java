package ch.ood.iwa.module;


import java.util.Set;

import org.vaadin.appfoundation.view.View;

import ch.ood.iwa.module.ui.ModuleView;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Tree;


/**
 * The public interface of every module.
 * A module mainly groups Views for the menu
 * 
 * @author Mischa
 *
 */
public interface Module {
	
	void registerView(ModuleView view);
	
	String getDisplayName();
	
	String getName();
	
	ThemeResource getIcon();		
	
	Tree getViewNamesAsTree();
	
	Set<String> getViewNamesAsSet();
	
	View getViewByName(String name);
			
	Set<View> getAllViews();
	
}
