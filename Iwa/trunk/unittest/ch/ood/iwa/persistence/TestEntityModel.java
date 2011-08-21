package ch.ood.iwa.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;
import org.vaadin.appfoundation.persistence.facade.IFacade;
import org.vaadin.appfoundation.view.View;

import ch.ood.iwa.authorization.ModulePermissionManager;
import ch.ood.iwa.module.Module;
import ch.ood.iwa.module.ui.ModuleView;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Tree;

/**
 * Tests the model classes
 * 
 * @author Mischa
 *
 */
public abstract class TestEntityModel {
	
	public final static String TEST_EMAIL_ADDRESS_1 = "testrecipient1@ood.ch";
	public final static String TEST_FIRST_NAME = "Honk";
	public final static String TEST_LAST_NAME = "McFly";
	public final static String TEST_MODULE_NAME = "TestModuleName";
	public final static String TEST_ADMIN_ROLE = "Admin";
	
	private IFacade f = FacadeFactory.getFacade();
				
	@Before
	public void clearDb() {		

		Collection<org.vaadin.appfoundation.authentication.data.User> users = f.list(User.class);
		f.deleteAll(users);
		
		Collection<org.vaadin.appfoundation.authorization.Role> roles = f.list(org.vaadin.appfoundation.authorization.Role.class);
		f.deleteAll(roles);
	}
	
	
	@Test
	public void testAuthorization() {
		// Setup and store User and its Role
		User user = new User();
		user.setName(TEST_FIRST_NAME);
		user.setUsername(TEST_EMAIL_ADDRESS_1);
		user.setPassword(TEST_EMAIL_ADDRESS_1);
		Role role = new Role(TEST_ADMIN_ROLE);		
		user.setRole(role);
		f.store(user);
	
		// Setup Permission
		ModulePermissionManager pm = new ModulePermissionManager();
		pm.addPermission(role, createModule()); 
	
		// Look up the user
		user = f.find(User.class, user.getId());
		
		// Verify Role
		assertEquals(role.getIdentifier(), user.getRole());
		
		// Verify Permission
		assertTrue(pm.hasPermission(user.getRole(), createModule()));
		
		// Remove Permission
		pm.removePermission(user.getRole(), createModule());
		
		// Verify Permission
		assertFalse(pm.hasPermission(user.getRole(), createModule()));
	}
	
	
	/**
	 * Creates a module for test purposes
	 * 
	 * @return
	 */
	private Module createModule() {
		return new Module() {
			
			@Override
			public void registerView(ModuleView view) {
				// nop
			}
						
			
			@Override
			public Set<String> getViewNamesAsSet() {
				return null;
			}
			
			@Override
			public View getViewByName(String name) {
				return null;
			}
			
			@Override
			public String getName() {
				return "TestModule";
			}
			
			@Override
			public ThemeResource getIcon() {
				return null;
			}
			
			@Override
			public String getDisplayName() {
				return null;
			}
			
			@Override
			public Set<View> getAllViews() {
				return null;
			}

			@Override
			public Tree getViewDisplayNamesAsTree() {
				return null;
			}

			@Override
			public Set<String> getViewDisplayNamesAsSet() {				
				return null;
			}

			@Override
			public View getViewByDisplayName(String name) {			
				return null;
			}
		};
	}
}
