/**
 * 
 */
package io.spire.examples;

import java.io.IOException;
import java.util.Date;

import io.spire.Spire;
import io.spire.api.*;
import io.spire.api.Message.MessageOptions;
import io.spire.request.ResponseException;

/**
 * @author Jorge Gonzalez
 *
 */
public class SpireApi {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ResponseException 
	 */
	public static void main(String[] args) throws ResponseException, IOException {
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
		
		// Register - creates account
		spire.register(email, password);
		String accounKey = spire.getAccountSecret();
		
		/* 
		 * Here is how you can use the Api class
		 * Can also do:
		 * 
		 * 	Api api = Spire.SpireFactory.createApi();
		 * 
		 * which points to the default Api Url
		 */
	    Api api = new Api(spireUrl);
	    api.discover();
	    // create a session
	    Session session = api.createSession(accounKey);
	    // retrieve channels
	    session.channels();
	    // channels are now cached. Now, try looking for channel by name
	    Channel channel = session.getChannels().getChannel("foo");
	    
	    if(channel == null){
	    	try {
	    		// if the channel named "foo" already exists, raises an error.
				channel = session.createChannel("foo");
			} catch (ResponseException e) {
				e.getResponse().ignore();
				channel = session.getChannels().getChannel("foo");
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
    	Subscription subscription = session.createSubscription("subscriptionName", "foo");
    	channel.publish("message content");
    	Events events = subscription.retrieveMessages();
    	int lastMessageIndex = events.getMessages().size() - 1;
    	String lastTimestamp = events.getMessages().get(lastMessageIndex).getTimestamp();
    	
    	channel.publish("another message");
    	MessageOptions options =  new MessageOptions();
    	options.timestamp = lastTimestamp;
    	events = subscription.retrieveMessages(options);
	}

}
