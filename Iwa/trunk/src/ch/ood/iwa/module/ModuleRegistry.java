package ch.ood.iwa.module;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.vaadin.appfoundation.view.View;

import ch.ood.iwa.IwaException;
import ch.ood.iwa.IwaInternalExeption;


/**
 * Registry to keep track of all modules
 * 
 * @author Mischa
 *
 */
public class ModuleRegistry implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Set<Module> modules = new LinkedHashSet<Module>(); 
	
	public void registerModule(Module module) {
		modules.add(module);
	}
	
	public Set<Module> getAllModules() {
		return modules;
	}		
	
	public View getViewByName(String name) throws IwaException {
		if (name == null) throw new IwaInternalExeption("Parameter must not be null");
		for (Module module : getAllModules()) {
			for (String viewName : module.getViewNamesAsSet()) {
				if(viewName.equals(name)) {
					return module.getViewByName(viewName);
				}
			}
		}
		return null;
	}
	
	public void clear() {
		modules.clear();
	}
}
