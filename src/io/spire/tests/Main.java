/**
 * 
 */
package io.spire.tests;

import io.spire.Spire;
import io.spire.api.Account;
import io.spire.api.Api;
import io.spire.api.Session;
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
//		    	spire.login("test+1326761570.841@spire.io", "carlospants");
//		    	spire.deleteAccount();
//		    	Account account = spire.getSession().getAccount();
//		    	System.out.println(account.getMediaType());
//		    	System.out.println(account.getUrl());
//		    	System.out.println(account.getCapability());
//		    	System.out.println(account.getBilling().getId());
//		    	spire.register("3242345fdswewerwerdf232@test.com", "foobarbaz", null);
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
