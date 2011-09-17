package ch.ood.iwa.ui;

import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.appfoundation.view.View;
import org.vaadin.appfoundation.view.ViewContainer;
import org.vaadin.appfoundation.view.ViewHandler;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.Reindeer;

/**
 * Helper for the IWA {@link MainWindow}
 * 
 * @author Mischa
 *
 */
public class MainArea extends AbstractView<Panel> implements ViewContainer {

    private static final long serialVersionUID = 1L;
    private View currentView;

    public MainArea() {    	
        super(new Panel());
        getContent().setSizeFull();
        getContent().setStyleName(Reindeer.PANEL_LIGHT);
        getContent().addComponent(ViewHandler.getUriFragmentUtil());
    }
    
    @Override
    public void activated(Object... params) {
        // Do nothing
    }
    
    @Override
    public void deactivated(Object... params) {
        // Do nothing
    }
   
    public void activate(View view) {
        if (currentView == null) {
            getContent().addComponent((Component) view);
        } else {
            getContent().replaceComponent((Component) currentView,
                    (Component) view);
        }
        currentView = view;
    }

    public void deactivate(View view) {
        if (currentView != null) {
            getContent().removeComponent((Component) view);
        }
        currentView = null;
        requestRepaint();
    }
}

