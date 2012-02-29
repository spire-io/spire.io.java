/**
 * 
 */
package io.spire.examples;

import java.io.IOException;
import java.util.Date;

import io.spire.Spire;
import io.spire.api.Channel;
import io.spire.api.Channel.Channels;
import io.spire.api.Events;
import io.spire.api.Message;
import io.spire.api.Subscription;
import io.spire.api.Subscription.Subscriptions;
import io.spire.request.ResponseException;

/**
 * @author Jorge Gonzalez
 *
 */
public class HelloSpire {

	/**
	 * @param args
	 */
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
		String email = String.format("spireNoob%d@noobspiretest.com", timestamp);
		String password = "spire.io.password";
		
		try {
			// Register - creates account
			spire.register(email, password);
			
			// Get session by logging in
			spire = new Spire(spireUrl);
			spire.login(email, password);
			
			// get session by using account key
			String accountKey = spire.getAccountSecret();
			spire = new Spire(spireUrl);
			spire.start(accountKey);
						
			// create a channel
			Channel channelFoo = spire.createChannel("foo_channel");
			
			// get a channels
			Channels channels = spire.getChannels();
			// print channels
			for (Channel channel : channels.values()) {
				System.out.println("Channel name: " + channel.getName());
			}
			
			// publish a message
			@SuppressWarnings("unused")
			Message message1 = channelFoo.publish("some message to foo channel");
			
			// create a subscription
			Subscription subscriptionFoo = spire.subscribe("subscriptionFoo", "foo_channel");
			
			// get a subscriptions
			Subscriptions subscriptions = spire.getSubscriptions();
			// print subscriptions
			for (Subscription subscription : subscriptions.values()) {
				System.out.println("Subscription name: " + subscription.getName());
			}
			
			// retrieve messages
			Events events = subscriptionFoo.retrieveMessages();
			// print messages
			for (Message message : events.getMessages()) {
				System.out.println("Message content: " + message.getContent().toString());
			}
			
			// publish another message
			@SuppressWarnings("unused")
			Message message2 = channelFoo.publish("message #2 to foo channel");
			
			// retrieve only the new messages
			events = subscriptionFoo.poll();
			// print messages
			for (Message message : events.getMessages()) {
				System.out.println("Message content: " + message.getContent().toString());
			}
			
			return;
		} catch (ResponseException e) {
			System.out.println(e.getMessage());
			e.getResponse().ignore();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(1);
	}

}
