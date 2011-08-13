package ch.ood.iwa.module;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.view.View;

import ch.ood.iwa.module.ui.ModuleView;

import com.vaadin.ui.Tree;

/**
 * Layer Supertype
 * 
 * @author Mischa
 *
 */
public abstract class AbstractModule implements Module, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Set<ModuleView> views = new HashSet<ModuleView>();
	private String displayName; 
	private String name;
	
	public AbstractModule(String name) {
		super();
		this.name = name;
		
		// Workaround to be able to unit-test this class (no application set) 
		try {
			setDisplayName(Lang.getMessage(name));	
		} catch (NullPointerException e) {
			setDisplayName(name);
		}
	}
	
	@Override
	public void registerView(ModuleView view) {
		views.add(view);		
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
	
	@Override
	public String getName() {
		return name;
	}
			
	@Override
	public Tree getViewNamesAsTree() {		
		Tree viewNamesTree = new Tree();
		for (ModuleView view : views) {
			String name = view.getName();
			viewNamesTree.addItem(name);			
			viewNamesTree.setChildrenAllowed(name, false);
		}
		return viewNamesTree;
	}
		
	@Override
	public Set<String> getViewNamesAsSet() {		
		Set<String> viewNames = new HashSet<String>();
		for (ModuleView view : views) {			
			viewNames.add(view.getName());			
		}
		return viewNames;
	}	
		
	@Override
	public View getViewByName(String name) {
		for (ModuleView view : views) {
			if (view.getName().equals(name)) {
				return (View)view;
			}
		}
		return null;		
	}
	
	protected void setDisplayName(String displayName) {
		this.displayName = displayName;		
	}
	
	@Override
	public Set<View> getAllViews() {		
		Set<View> appFoundationViews = new HashSet<View>();		
		for (ModuleView view : views) {
			appFoundationViews.add((View)view);
		}		
		return appFoundationViews;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractModule other = (AbstractModule) obj;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		return true;
	}
}
