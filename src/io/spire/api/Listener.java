/**
 * 
 */
package io.spire.api;

/**
 * Implements this for having objects processing messages in the background
 * 
 * i.e.
 * <pre>
 * {@code
 * // MyListener class implements Listener
 * // creates listener
 * MyListener listener = new MyListener();
 * 
 * // creates subscription for a channel
 * Subscription subscription1 = spire.subcribe("subcriptionName", "channelName");
 * 
 * // add your listener to the channel subscription
 * subscription1.addListener(listener1);
 * 
 * subscription1.startListening();
 * 
 * ... messages will be process in a background thread
 * 
 * // when you are done, you can either
 * // remove the listener
 * subscription1.removeListener(listener);
 * 
 * // or stop listening for messages in this channel
 * subscription1.stopListening();
 * }
 * </pre>
 * 
 * NOTE: You can add as many listeners as you want/need to.
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
public interface Listener {

	/**
	 * Override this method for processing incoming messages
	 * 
	 * @param message
	 */
	public void process(Event event);
}
