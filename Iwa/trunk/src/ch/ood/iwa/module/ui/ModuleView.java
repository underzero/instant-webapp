package ch.ood.iwa.module.ui;


/**
 * Interface to integrate a view into the IWA module concept
 * 
 * @author Mischa
 *
 */
public interface ModuleView {

	/**
	 * The name of this view (to be translated for displaying)
	 * 
	 * @return
	 */
	String getName();
	
	/**
	 * Returning true shows this view "full screen" (without header and navigation panel) 
	 * 
	 * @return
	 */
	boolean isFullScreen();
		
}
