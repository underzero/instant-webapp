package org.vaadin.appfoundation.authorization;

import java.util.Set;

import javax.persistence.Entity;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;

/**
 * The Role
 * 
 * @author Kim
 * @author Mischa
 * 
 */
@Entity
public class Role extends AbstractPojo {
	
	private static final long serialVersionUID = 1L;
	private String identifier;
	
	
	// Child roles (aka sub-roles)
	private Set<Role> roles;

	/**
	 * Convenience Constructor
	 * 
	 * @param identifier
	 */
	public Role(String identifier) {
		super();
		this.identifier = identifier;
	}
	
	public Role() {
		super();
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
    /**
     * A unique identifier for this role instance
     * 
     */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
     * Set all the roles which are <b>directly</b> assigned to this role.
     * 
     * @param roles
     */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
    /**
     * Roles are hierarchical, this method returns other roles that have been
     * <b>directly</b> assigned to this role.
     * 
     * @return
     */
	public Set<Role> getRoles() {
		return roles;
	}  
	
	public void addRole(Role role) {
		getRoles().add(role);
	}

	/**
	 * Equality is based on the identifier only
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
		Role other = (Role) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}	
	
	@Override
	public String toString() {
		return identifier;
	}
}
