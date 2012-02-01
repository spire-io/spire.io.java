/**
 * 
 */
package io.spire.tests;

import io.spire.Spire;
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
		    	Api api = spire.discover();
		    	Session session = api.createSession("Ac-th7aFFBCFth1LSnAgEylz8g-gkY");
		        return;
		      } catch (ResponseException e) {
		        System.out.println(e.getMessage());
		      }
		    } catch (Throwable t) {
		      t.printStackTrace();
		    }
		    System.exit(1);
	}

}
