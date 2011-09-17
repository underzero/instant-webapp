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
package org.vaadin.appfoundation.authentication.data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

/**
 * Entity class for users. This class keeps information about registered users.
 * 
 * This class is initially provided by http://code.google.com/p/vaadin-appfoundation/
 * and has been modified to use with http://code.google.com/p/instant-webapp/
 * 
 * @author Kim
 * @author Mischa
 * 
 */
@Entity
@Table(name = "appuser", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class User extends AbstractPojo {

    private static final long serialVersionUID = 4417119399127203109L;

    protected String username = "";
    
    // This field may contain the new password in clear text and must never be persisted  
	@Transient
    private String newPassword;

    protected String password = "";

    private String name = "";

    private String email = "";

    private int failedLoginAttempts = 0;

    private boolean accountLocked = false;

    private String reasonForLockedAccount;
    
    private String role;

    @Transient
    private int failedPasswordChanges = 0;

    /**
     * Get the username of the user
     * 
     * @return User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username for the user
     * 
     * @param username
     *            New username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the (encrypted) password of the user
     * 
     * @return Encrypted password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Set the (encrypted) password of the user
     * 
     * @param password
     *            New hashed password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Convenience method to use with forms, as displaying a hashed 
     * password makes most of the time no sense and displaying a clear text 
     * password would violate most security policies 
     * 
     * @return Always an empty String
     */
    public String getPasswordAsString() {
        return "";
    }
    
    /**
     * Convenience method to simplify the usage of this class with Vaadin forms.
     * The "newPassword" property is intended to be a temporary in-memory helper.
     * <p/>
     * The password set here can be retrieved with {@link User#getNewPassword()}.
     * 
     * Use {@link User#setPassword(String)} to set the (encrypted) password 
     * that will be persisted
     * 
     * @param password
     *            New hashed password
     */
    public void setPasswordAsString(String password) {
    	if (password != null && password.length() > 0) {
    		this.newPassword = password;
    	}
    }    

    /**
     * This is a convenience method to use this class with Vaadin forms.
     * Use this method to retrieve the value set with {@link User#setPasswordAsString(String)}
     * 
     * @return
     */
    public String getNewPassword() {
        return newPassword;
    }
    
    /**
     * Set the actual name of the user
     * 
     * @param name
     *            New name for the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the actual name of the user
     * 
     * @return Name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Set an email address for the user
     * 
     * @param email
     *            New email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the user's email address
     * 
     * @return User's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Increments the amount of failed login attempts by one
     */
    public void incrementFailedLoginAttempts() {
        failedLoginAttempts++;
    }

    /**
     * Define if the account should be locked
     * 
     * @param accountLocked
     *            true if account should be locked, false if account should be
     *            activated
     */
    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    /**
     * Is the current user account locked
     * 
     * @return true if account is locked, otherwise false
     */
    public boolean isAccountLocked() {
        return accountLocked;
    }

    /**
     * Resets the number of failed login attempts back to zero
     */
    public void clearFailedLoginAttempts() {
        failedLoginAttempts = 0;
    }

    /**
     * Get the number of failed login attempts
     * 
     * @return The number of failed login attempts
     */
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    /**
     * Sets the reason why the account has been locked
     * 
     * @param reasonForLockedAccount
     *            Reason for account being locked
     */
    public void setReasonForLockedAccount(String reasonForLockedAccount) {
        this.reasonForLockedAccount = reasonForLockedAccount;
    }

    /**
     * Get the reason why the account has been locked
     * 
     * @return Reason for account being locked
     */
    public String getReasonForLockedAccount() {
        return reasonForLockedAccount;
    }
    
    /**
     * Sets the role this user has
     * 
     * @param role
     */
    public void setRole(String role) {
		this.role = role;
	}
    
    /**
     * Sets the role this user has
     * 
     * @param role
     */
    public void setRole(Role role) {
		this.role = role.getIdentifier();
	}

    /**
     * Returns the role for this User. A user can only have one
     * role, but roles can have sub roles
     * 
     * @param role
     */
	public String getRole() {
		return role;
	}    

    /**
     * Increments the number of failed password change attempts. This value is
     * not persisted.
     */
    public void incrementFailedPasswordChangeAttempts() {
        failedPasswordChanges++;
    }

    /**
     * Clears the number of failed password change attempts
     */
    public void clearFailedPasswordChangeAttempts() {
        failedPasswordChanges = 0;
    }

    /**
     * Returns the number of failed password change attempts. This value is not
     * persisted.
     * 
     * @return Number of failed password change attempts for this object
     *         instance
     */
    public int getFailedPasswordChangeAttemps() {
        return failedPasswordChanges;
    }

}
