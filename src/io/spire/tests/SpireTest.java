/**
 * 
 */
package io.spire.tests;

import java.io.IOException;
import java.util.Date;

import io.spire.Spire;
import io.spire.api.Api.APIDescriptionModel;
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
	private APIDescriptionModel description;
	private String key;
	private String email;
	private String password;
	
	public SpireTest(){
		
	}
	
	private Spire createSpire(APIDescriptionModel description){
		Spire spire = new Spire(SPIRE_URL);
		if(description != null)
			spire.getApi().setApiDescription(description);
		return spire;
	}
	
	private String uniqueEmail(){
		Date now = new Date();
		return "test+jclient+" + now.getTime() + "@spire.io";
	}
	
	@Before
	public void setUp() throws Exception{
		email = uniqueEmail();
		System.out.println(email);
		password = "carlospants";
		spire = createSpire(null);
		spire.discover();
		description = spire.getApi().getApiDescription();
		spire.register(email, password, null);
		key = spire.getSession().getAccount().getKey();
	}

	@Test
	public void discovery() throws Exception {
		spire.discover();
		assertNotNull(spire.getApi().getApiDescription());
	}
	
	@Test
	public void start() throws Exception {
		Spire spire = createSpire(description);
		spire.start(key);
		assertNotNull(spire.getSession());
	}
	
	@Test
	public void login() throws Exception {
		Spire spire = createSpire(description);
		spire.login(email, password);
		assertNotNull(spire.getSession());
	}
	
	@Test
	public void register() throws Exception {
		Spire spire = createSpire(description);
		String email = uniqueEmail();
		System.out.println("email " + email);
		String password = "somepassword";
		spire.register(email, password, null);
		assertNotNull(spire.getSession());
	}
}
