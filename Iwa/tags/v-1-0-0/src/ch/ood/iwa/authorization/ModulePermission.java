package ch.ood.iwa.authorization;

import javax.persistence.Entity;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;

/**
 * Entity to store Module Permissions
 * 
 * @author Mischa
 *
 */
@Entity
public class ModulePermission extends AbstractPojo {

	private static final long serialVersionUID = 1L;
	private String roleName = "";
	private String moduleName = "";
	
	public ModulePermission() {
		super();
	}	
	
	public ModulePermission(String roleName, String moduleName) {
		super();
		this.roleName = roleName;
		this.moduleName = moduleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * Equality is based on the moduleName and the roleName only 
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModulePermission other = (ModulePermission) obj;
		if (moduleName == null) {
			if (other.moduleName != null)
				return false;
		} else if (!moduleName.equals(other.moduleName))
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		return result;
	}
}
