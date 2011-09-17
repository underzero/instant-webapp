package ch.ood.iwa.module.ui;


/**
 * Interface to integrate a view into the IWA module concept
 * 
 * @author Mischa
 *
 */
public interface ModuleView {

	/**
	 * Sets the name of this view. Will be translated for displaying
	 * and can be retrieved via {@link ModuleView#getDisplayName()}
	 * 
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * The name of this view (not translated)
	 * 
	 * @return
	 */
	String getName();
	
	/**
	 * The translated name of this view for displaying
	 * 
	 * @return
	 */
	String getDisplayName();
	
	/**
	 * Returning true shows this view "full screen" (without header and navigation panel) 
	 * 
	 * @return
	 */
	boolean isFullScreen();			
}
