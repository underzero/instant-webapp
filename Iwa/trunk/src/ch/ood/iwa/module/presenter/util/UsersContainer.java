package ch.ood.iwa.module.presenter.util;

import java.io.Serializable;
import java.util.List;

import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.vaadin.data.util.BeanItemContainer;

/**
 * A specialized BeanItemContainer for Users, used by Tables
 * 
 * @author Mischa
 *
 */
public class UsersContainer extends BeanItemContainer<User> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Object[] VISIBLE_COLUMNS = new Object[] {
    		"username", "name", "email", "role" };
    private static final String[] COL_HEADERS = new String[] {"Username", "Name", "Email", "Role"};
	
	public UsersContainer() {
		super(User.class);		
	}

	public static Object[] getVisibleColumns() {
		return VISIBLE_COLUMNS;
	}
	
	public static String[] getColumnCaptions() {
		return COL_HEADERS;
	}
	
	public void populateContainer() {				
		removeAllItems();

		List<User> results = FacadeFactory.getFacade().list(User.class);
				                  
		if (results != null) {
			for (User user : results) {			
				addItem(user);
			}
		}
	}
}
