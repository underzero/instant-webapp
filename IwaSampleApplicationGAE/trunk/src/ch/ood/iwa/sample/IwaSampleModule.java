package ch.ood.iwa.sample;

import java.io.Serializable;

import ch.ood.iwa.module.AbstractModule;

import com.vaadin.terminal.ThemeResource;


public class IwaSampleModule extends AbstractModule implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	private static final ThemeResource icon = new ThemeResource("../runo/icons/16/note.png");
	
	public IwaSampleModule()  {
		super("Sample");
		
		// This is the place to add more views	
		super.registerView(new IwaSampleView());		
	}

	@Override
	public ThemeResource getIcon() {
		return icon;
	}	
}
