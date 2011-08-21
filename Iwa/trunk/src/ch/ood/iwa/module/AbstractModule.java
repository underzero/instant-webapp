package ch.ood.iwa.module;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.view.View;

import ch.ood.iwa.module.ui.ModuleView;

import com.vaadin.ui.Tree;

/**
 * Layer Supertype. All IWA modules should inherit from this class.
 * 
 * @author Mischa
 *
 */
public abstract class AbstractModule implements Module, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Set<ModuleView> views = new LinkedHashSet<ModuleView>();
	private String displayName; 
	private String name;
	
	/**
	 * Convenience Constructor
	 * 
	 * @param name
	 */
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
	
	/**
	 * Register a View with this module
	 */
	@Override
	public void registerView(ModuleView view) {
		views.add(view);		
	}

	/**
	 * The translated module name
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * The module name, not translated
	 */
	@Override
	public String getName() {
		return name;
	}
			
	/**
	 * Returns all (translated) view names as {@link Tree}
	 */
	@Override
	public Tree getViewDisplayNamesAsTree() {		
		Tree viewNamesTree = new Tree();
		for (ModuleView view : views) {
			String name = view.getDisplayName();
			viewNamesTree.addItem(name);			
			viewNamesTree.setChildrenAllowed(name, false);
		}
		return viewNamesTree;
	}
		
	/**
	 * Returns all view names as {@link Set}
	 */
	@Override
	public Set<String> getViewNamesAsSet() {		
		Set<String> viewNames = new HashSet<String>();
		for (ModuleView view : views) {			
			viewNames.add(view.getName());			
		}
		return viewNames;
	}	
	
	/**
	 * 
	 * Returns all (translated) view names as {@link Set}
	 */
	@Override
	public Set<String> getViewDisplayNamesAsSet() {		
		Set<String> viewNames = new HashSet<String>();
		for (ModuleView view : views) {			
			viewNames.add(view.getDisplayName());			
		}
		return viewNames;
	}		
		
	/**
	 * Returns a view by its name
	 */
	@Override
	public View getViewByName(String name) {
		for (ModuleView view : views) {
			if (view.getName().equals(name)) {
				return (View)view;
			}
		}
		return null;		
	}
		
	/**
	 * Returns a view by its translated name
	 */
	@Override
	public View getViewByDisplayName(String name) {
		for (ModuleView view : views) {
			if (view.getDisplayName().equals(name)) {
				return (View)view;
			}
		}
		return null;		
	}	
	
	/**
	 * Returns all Views
	 */
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
	
	/**
	 * Allows to set the (translated) display name
	 * 
	 * @param displayName
	 */
	protected void setDisplayName(String displayName) {
		this.displayName = displayName;		
	}	
}
