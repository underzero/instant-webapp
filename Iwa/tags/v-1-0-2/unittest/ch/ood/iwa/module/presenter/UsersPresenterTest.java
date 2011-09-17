package ch.ood.iwa.module.presenter;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.IAnswer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;
import org.vaadin.appfoundation.persistence.facade.FacadeStub;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.Table;

/**
 * Demonstrates the testing of a presenter when the UI interface 
 * contains Frameworks components like Forms, Tables, Buttons etc.
 *  
 * @see EventPresenterTest
 * 
 * @author Mischa
 * 
 */
public class UsersPresenterTest {
			
	// Class Under Test
	private UsersPresenter cut;
	UsersPresenter.UI ui;
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FacadeFactory.registerFacade(FacadeStub.class, "test", true);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		FacadeFactory.clear();
	}
	
	@Before
	public void setUp() throws Exception {
		cut = new UsersPresenter();
		ui = createNiceMock(UsersPresenter.UI.class);		
		cut.setUi(ui);						
	}

	@Test
	public void testHandleNewUserButtonClicked() {
		// Set up expectations
		expect(ui.getBtnSave()).andStubAnswer(new IAnswer<Button>() {
			@Override
			public Button answer() throws Throwable {
				return new Button();
			}
		});
						
		expect(ui.getTable()).andStubAnswer(new IAnswer<Table>() {
			@Override
			public Table answer() throws Throwable {
				return new Table();
			}
		});

		expect(ui.getForm()).andStubAnswer(new IAnswer<Form>() {
			@Override
			public Form answer() throws Throwable {
				return new Form();
			}
		});
		replay(ui);			
				
		cut.handleNewButtonClicked();
		
		// just verify the expected calls on the mocked UI
		verify(ui);
	}	
	
	@Test
	public void testHandleSaveUserButtonClicked() {
		// Set up expectations						
		expect(ui.getTable()).andStubAnswer(new IAnswer<Table>() {
			@Override
			public Table answer() throws Throwable {
				return new Table();
			}
		});
		expect(ui.getForm()).andStubAnswer(new IAnswer<Form>() {
			@Override
			public Form answer() throws Throwable {
				return new Form() {
					private static final long serialVersionUID = 1L;
				    public Item getItemDataSource() {
				    	return new BeanItem<User>(new User());
				    }					
				};
			}
		});		
		replay(ui);							
		cut.handleSaveButtonClicked();		
		assertEquals(1, ((FacadeStub)FacadeFactory.getFacade()).getStoreCallsCounter());			
	}	
	
	@Test
	public void testDeleteUser() {
		// Set up expectations
		expect(ui.getTable()).andStubAnswer(new IAnswer<Table>() {
			@Override
			public Table answer() throws Throwable {
				return new Table() {
					private static final long serialVersionUID = 1L;
				    public Item getItem(Object itemId) {
				    	return new BeanItem<User>(new User());
				    }					
				};
			}
		});		
		replay(ui);							
		cut.deleteUser();
		// Verify
		assertEquals(1, ((FacadeStub)FacadeFactory.getFacade()).getDeleteCallsCounter());			
	}		
}
