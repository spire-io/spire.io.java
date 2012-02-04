/**
 * 
 */
package io.spire.tests;

import java.io.IOException;
import java.util.Date;

import io.spire.Spire;
import io.spire.api.Account;
import io.spire.api.BillingSubscription;
import io.spire.api.Channel;
import io.spire.api.Channel.Channels;
import io.spire.api.Session;
import io.spire.api.Api.APIDescriptionModel;
import io.spire.api.Subscription;
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

	public SpireTest() {

	}

	private Spire createSpire(APIDescriptionModel description) {
		Spire spire = new Spire(SPIRE_URL);
		if (description != null)
			spire.getApi().setApiDescription(description);
		return spire;
	}

	private String uniqueEmail() {
		Date now = new Date();
		return "test+jclient+" + now.getTime() + "@spire.io";
	}
	
	private void print(String string){
		System.out.println(string);
	}

	@Before
	public void setUp() throws Exception {
		email = uniqueEmail();
//		email = "test+1326765873.501@spire.io";
		password = "carlospants";
		spire = createSpire(null);
		spire.discover();
		description = spire.getApi().getApiDescription();
		spire.register(email, password, null);
//		spire.login(email, password);
		key = spire.getSession().getAccount().getKey();
	}

	@Test
	public void discover() throws Exception {
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
		assertNotNull(spire.getSession().getAccount());
	}

	@Test
	public void register() throws Exception {
		Spire spire = createSpire(description);
		String email = uniqueEmail();
		String password = "somepassword";
		spire.register(email, password, null);
		assertNotNull(spire.getApi());
		assertNotNull(spire.getSession());
		assertNotNull(spire.getSession().getAccount());
	}

	@Test
	public void getAccount() throws Exception {
		// account data
		Account account = spire.getSession().getAccount();
		assertNotNull(account);
		assertNotNull(account.getCapability());
		assertNotNull(account.getEmail());
		// account billing subscription data
		BillingSubscription billing = account.getBilling();
		assertNotNull(billing);
		assertNotNull(billing.getCapability());
		assertNotNull(billing.getId());
		assertNotNull(billing.getInvoices());
		assertNotNull(billing.getInvoices().getUpcomingInvoice());
	}
	
	@Test
	public void getChannels() throws Exception {
		Channels channels = spire.getSession().getChannels();
		assertNotNull(channels);
		assertNotNull(channels.getCapability());
		assertNotNull(channels.getUrl());
	}
}
