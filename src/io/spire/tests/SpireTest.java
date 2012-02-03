/**
 * 
 */
package io.spire.tests;

import java.io.IOException;

import io.spire.Spire;
import io.spire.request.ResponseException;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author jorge
 *
 */
public class SpireTest {
	
	public static final String SPIRE_URL = "http://localhost:1337";

	private Spire spire;
	
	public SpireTest(){
		
	}
	
	private Spire createSpire(){
		return new Spire(SPIRE_URL);
	}
	
	@Test
	public void discovery() throws Exception {
		spire = createSpire();
		spire.discover();
		assertNotNull(spire.getApi().getApiDescription());
	}
}
