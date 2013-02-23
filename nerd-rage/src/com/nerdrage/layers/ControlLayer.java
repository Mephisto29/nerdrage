package com.nerdrage.layers;

/**
 * This layer will be used to display the control overlay. It will accept user input and
 * relay these the user's key presses back to another layer. This layer can be switched to
 * redirect the control layer's commands to another layer. 
 */
public class ControlLayer extends AbstractLayer {

	/**
	 * Private instance variables
	 */
	private AbstractReceiverLayer receiver;
	
	/**
	 * Method to handle the drawing of the layer to the screen. The layer will be slightly 
	 * transparent and will have button controls for the user to interact with
	 */
	@Override
	public void draw() {
		
	}
	
	/**
	 * Method to update the receiver of control input
	 * 
	 * @param receiver The new layer to which the controls should be redirected
	 */
	public void setReceiver (AbstractReceiverLayer receiver) {
		this.receiver = receiver;
	}

}
