/**
 * 
 */
package io.spire.examples;

import java.io.IOException;
import java.util.Date;

import io.spire.Spire;
import io.spire.api.Channel;
import io.spire.api.Event;
import io.spire.api.Listener;
import io.spire.api.Message.MessageOptions;
import io.spire.api.Subscription;
import io.spire.request.ResponseException;

/**
 * This example shows how to create listeners for using Long Poll 
 * 
 * @author Jorge Gonzalez
 *
 */
public class SpireListeners {

	/**
	 * ** Important for this example **
	 * Creates a class that implements the Listener Interface
	 * 
	 * @author Jorge Gonzalez
	 *
	 */
	public static class MyListener implements Listener{
		private String name;
		
		public MyListener(String name){
			this.name = name;
		}

		@Override
		public void process(Event message) {
			try {
				// simulate some work....
				System.out.println("-- * incoming message * --");
				System.out.println("Listener name: " + this.name);
				System.out.println(message.getTimestamp());
				System.out.println(message.getContent());
				System.out.println("---\n");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		/* This is just for this test. 
		 * We don't really want to be creating accounts every time, right?
		 */
		String spireUrl = "http://build.spire.io/";
		
		/* Setup
		 * 
		 * Normally you would only need to do this:
		 * Spire spire = new Spire(); 
		 */
		Spire spire = new Spire(spireUrl);
		
		Date now = new Date();
		long timestamp = now.getTime();
		String email = String.format("spireNoob%d@spire.io", timestamp);
		String password = "spire.io.password";
		
		try {
			// Register - creates account
			spire.register(email, password);
			
			// create channels
			Channel channel1 = spire.createChannel("channel1");
			
			// create subscriptions to channel1
			Subscription subscription1 = spire.subscribe("subscription1", "channel1");
			
			// create listeners
			MyListener listener1 = new MyListener("listener1");
			MyListener listener2 = new MyListener("listener2");
			
			// register listener1
			subscription1.addListener(listener1);
			
			// define the max time to wait for messages before timing out.
			MessageOptions options = new MessageOptions();
			// *** time is in seconds...!
			options.timeout = 10;
			
			// start listening for incoming messages
			subscription1.startListening(options);
			System.out.println("started listening");
			
			// now send some messages to channel1
			for (int i = 1; i <= 3; i++) {
				channel1.publish("message " + i);
			}
			
			// wait for incoming messages....
			Thread.sleep(5*1000);
			
			// lets add another listener: listener2
			subscription1.addListener(listener2);
			System.out.println("added listener2");
			
			// send few more messages to channel1
			for (int i = 4; i <= 6; i++) {
				channel1.publish("message " + i);
			}

			// both listeners should process messages 3 to 6
			Thread.sleep(5*1000);
			
			// remove listener1
			subscription1.removeListener(listener1);
			System.out.println("removeListener listener1");
			
			// now there is one listener
			Thread.sleep(2*1000);
			
			// send few more messages to channel1
			for (int i = 7; i <= 9; i++) {
				channel1.publish("message " + i);
			}
			
			/* wait for messages 7-9 to be processed
			 * only listener2 should process messages 7 to 9
			 */
			Thread.sleep(5*1000);
			
			// stop listening subscription1
			subscription1.stopListening();
			System.out.println("stopped listening \n\n");
			
			System.out.println("Listener example complete!");
			
			return;
		}catch (ResponseException e) {
			System.out.println(e.getMessage());
			e.getResponse().ignore();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(1);
	}
}
