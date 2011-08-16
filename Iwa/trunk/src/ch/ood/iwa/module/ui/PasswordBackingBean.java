package ch.ood.iwa.module.ui;

import java.io.Serializable;

/**
 * Backing Bean for a Vaadin form
 * 
 * @author Mischa
 *
 */
public class PasswordBackingBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String oldPassword = "";
	private String newPassword = "";
	private String newPasswordRepeated = "";

	/**
	 * Clears all fields
	 */
	public void reset() {
		setOldPassword("");
		setNewPassword("");
		setNewPasswordRepeated("");
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPasswordRepeated(String newPasswordRepeated) {
		this.newPasswordRepeated = newPasswordRepeated;
	}

	public String getNewPasswordRepeated() {
		return newPasswordRepeated;
	}
}
