/**
 * 
 */
package io.spire.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.spire.Spire;
import io.spire.api.Account;
import io.spire.api.Api;
import io.spire.api.Channel;
import io.spire.api.Events;
import io.spire.api.Session;
import io.spire.api.Subscription;
import io.spire.api.Message.MessageOptions;
import io.spire.request.ResponseException;

/**
 * @author jorge
 *
 */
public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Spire spire = new Spire();
		try {
		      try {
		    	spire.discover();
		    	spire.start("Ac-th7aFFBCFth1LSnAgEylz8g-gkY");
//		    	spire.login("foo@test.com", "foobarbaz");
//		    	spire.login("foo2390324joid@test.com", "foobarbaz");
//		    	spire.login("test+jclient+1328562878596@spire.io", "carlospants");
//		    	spire.login("test+1326761570.841@spire.io", "carlospants");
//		    	spire.deleteAccount();
//		    	Account account = spire.getSession().getAccount();
//		    	System.out.println(account.getMediaType());
//		    	System.out.println(account.getOrigin().getHost());
//		    	System.out.println(account.getUrl());
//		    	System.out.println(account.getCapability());
//		    	System.out.println(account.getBilling().getId());
//		    	spire.register("3242345fdswewerwerdf232@test.com", "foobarbaz", null);
		    	
//		    	Channel channel = spire.createChannel("foo_channel");
//				Subscription subscription1 = channel.subscribe("bar_subscription", spire.getSession());
//		    	Subscription subscription1 = spire.subscribe("bar_subscription", "foo_channel");
//		    	Channel channel = spire.getChannels().getChannel("foo_channel");
		        return;
		      } catch (ResponseException e) {
		        System.out.println(e.getMessage());
		        e.getResponse().close();
		      }
		    } catch (Throwable t) {
		      t.printStackTrace();
		    }
		    System.exit(1);
	}

}
