/*  
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.appfoundation.authorization;

import java.util.Set;

import javax.persistence.Entity;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;

/**
 * The Role
 * 
 * This class is initially provided by http://code.google.com/p/vaadin-appfoundation/
 * and has been modified to use with http://code.google.com/p/instant-webapp/
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
