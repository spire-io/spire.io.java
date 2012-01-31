/**
 * 
 */
package spire.tests;

import spire.Spire;
import spire.api.Api;
import spire.request.ResponseException;

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
		        return;
		      } catch (ResponseException e) {
		        System.err.println(e.getResponse().parseAsString());
		      }
		    } catch (Throwable t) {
		      t.printStackTrace();
		    }
		    System.exit(1);
	}

}
