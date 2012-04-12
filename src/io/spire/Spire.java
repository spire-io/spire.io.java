/**
 * 
 */
package io.spire;

import io.spire.api.Api;
import io.spire.api.Api.ApiDescriptionModel;
import io.spire.api.Api.ApiDescriptionModel.ApiSchemaModel;
import io.spire.api.Billing;
import io.spire.api.Capability;
import io.spire.api.Channel;
import io.spire.api.Resource;
import io.spire.api.Channel.Channels;
import io.spire.api.Subscription;
import io.spire.api.Subscription.Subscriptions;
import io.spire.api.Session;
import io.spire.request.ResponseException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Provides a main interface for authentication, session management,
 * account management and creation different Spire resources
 *  
 *  <p>i.e.</p>
 *  <pre>
 *  {@code Spire spire = new Spire();}
 *  {@code spire.start(account_key)		// starts a new session}
 *  </pre>
 *  
 * <p>
 * Instances of Spire are not safe for use by multiple threads.
 * Instead, we recommend having multiple independent Spire instances per thread
 * </p>
 * 
 * @since 1.0
 * @author Jorge Gonzalez
 *
 */
@SuppressWarnings("deprecation")
public class Spire {
	public static final String SPIRE_URL = "https://api.spire.io";
	public static final Api SPIRE_API = new Api(SPIRE_URL);
		
	private String spire_url;
	private Api api;
	private Session session;
	private Billing billing;

	/**
	 * Spire constructor
	 * 
	 * Initialize spire object with default URL and API version
	 */
	public Spire(){
		this.spire_url = SPIRE_URL;
		this.api = SPIRE_API;
	}
	
	/**
	 * Spire constructor
	 * 
	 * Initialize spire object with specified 'url' and default API 'version'
	 * 
	 * @param url the Spire Api entry point
	 */
	public Spire(String url){
		this.spire_url = url;
		this.api = new Api(spire_url);
	}
	
	/**
	 * Spire constructor
	 * 
	 * Initialize spire object with specified 'url' and API 'version'
	 * 
	 * @param url the Spire Api entry point
	 * @param version Spire Api version
	 */
	public Spire(String url, String version){
		this.spire_url = url;
		this.api = new Api(spire_url, version);
	}
	
	/**
	 * Helper method, checks that the API description {@link Api#discover()} has been initialized
	 * 
	 * @throws ResponseException
	 * @throws IOException
	 */
	private void initializeApi() throws ResponseException, IOException{
		if(api.getApiDescription() == null)
			api.discover();
	}
	
	/**
	 * Return the current Spire Api object
	 * 
	 * @return {@link io.spire.api.Api}
	 */
	public Api getApi(){
		return api;
	}
	
	/**
	 * Returns the current Spire session object
	 * 
	 * @return {@link io.spire.api.Session}
	 */
	public Session getSession(){
		return session;
	}
	
	/**
	 * Spire Api entry point. This method runs Spire Api discover process, 
	 * retrieving the Api description for all available resources
	 * 
	 * @throws ResponseException
	 * @throws IOException
	 */
	public void discover() throws ResponseException, IOException{
		api.discover();
	}
	
	/**
	 * Creates an authenticated Spire session using the Spire Account key
	 * 
	 * @param accountKey
	 * @throws ResponseException
	 * @throws IOException
	 */
	public void start(String secretKey) throws ResponseException, IOException{
		this.initializeApi();
		session  = api.createSession(secretKey);
	}
	
	/**
	 * Creates an authenticated Spire session using the account login and password.
	 * 
	 * @param email
	 * @param password
	 * @throws ResponseException
	 * @throws IOException
	 */
	public void login(String email, String password) throws ResponseException, IOException{
		this.initializeApi();
		session  = api.login(email, password);
	}
	
	/**
	 * Creates a new Spire account.
	 * 
	 * @param email
	 * @param password
	 * @param passwordConfirmation
	 * @throws ResponseException
	 * @throws IOException
	 */
	public void register(String email, String password, String passwordConfirmation) throws ResponseException, IOException{
		this.initializeApi();
		session = api.createAccount(email, password, passwordConfirmation);
	}
	
	/**
	 * Creates a new Spire account.
	 * 
	 * @param email
	 * @param password
	 * @throws ResponseException
	 * @throws IOException
	 */
	public void register(String email, String password) throws ResponseException, IOException{
		this.initializeApi();	
		session = api.createAccount(email, password, password);
	}
	
//	public void passwordReset(String email) throws ResponseException, IOException{
//		
//	}
	
	/**
	 * Deletes the current authenticated Spire account 
	 * 
	 * @throws ResponseException
	 * @throws IOException
	 */
	public void deleteAccount() throws ResponseException, IOException{
		session.getAccount().delete();
	}
	
	/**
	 * Gets the Account secret key
	 * 
	 * @return {@link String}
	 */
	public String getAccountSecret(){
		if(session == null || session.getAccount() == null)
			return null;
		return session.getAccount().getSecret();
	}
	
	/**
	 * Gets all the Spire channels available in the current session
	 * 
	 * @return {@link Channels}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Channels getChannels(){
		return session.getChannels();
	}
	
	/**
	 * Request and returns Spire Billing object with contains information about all
	 * the billing plans available
	 * 
	 * @return {@link Billing}
	 * @throws ResponseException
	 * @throws IOException
	 * @deprecated v1.1.4
	 */
	public Billing billing() throws ResponseException, IOException{
		this.initializeApi();
		billing = api.billing();
		return billing;
	}
	
	/**
	 * Request and returns all Spire Channels available for the current session.
	 * 
	 * @return {@link Channels}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Channels channels() throws ResponseException, IOException{
		return session.channels();
	}
	
	/**
	 * Request the creation of a new Spire Channel
	 * 
	 * @param name of the channel to create
	 * @return {@link Channel}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Channel createChannel(String name) throws ResponseException, IOException{
		return session.createChannel(name);
	}
	
	/**
	 * Gets all the Spire Subscriptions available in the current session
	 * 
	 * @return {@link Subscriptions}
	 */
	public Subscriptions getSubscriptions(){
		return session.getSubscriptions();
	}
	
	/**
	 * Request and returns all Spire {@link Subscriptions} available for the current session.
	 * 
	 * @return {@link Subscriptions}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Subscriptions subscriptions() throws ResponseException, IOException{
		return session.subscriptions();
	}
	
	/**
	 * 
	 * Creates a returns a new subscription.
	 * 
	 * @param name of the new {@link Subscription}
	 * @param channels is a list of channel names to subscribe to
	 * @return {@link Subscription}
	 * @throws ResponseException
	 * @throws IOException
	 */
	public Subscription subscribe(String name, String ...channels) throws ResponseException, IOException{
		return session.subscribe(name, channels);
	}
	
	/**
	 * Spire Factory
	 * Convenient for quick creation of Spire objects. 
	 * Spire Factory reuses the internal Api description object 
	 * obtained from the first originated {@link Api#discover()}
	 * 
	 * @author Jorge Gonzalez
	 *
	 */
	public static class SpireFactory{
		private static ApiDescriptionModel description;
		
		private static void initialize() throws ResponseException, IOException{
			if(SPIRE_API.getApiDescription() == null){
				SPIRE_API.discover();
				description = SPIRE_API.getApiDescription();
			}
		}
		
		/**
		 * Creates a new Api object using the default Url
		 * 
		 * @return {@link Api}
		 */
		public static Api createApi(){
			return new Api(Spire.SPIRE_URL);
		}
		
		/**
		 * Creates a new instance of a Spire Channel
		 * 
		 * @return {@link Channel}
		 * @throws ResponseException
		 * @throws IOException
		 */
		public static Channel createChannel() throws ResponseException, IOException{
			initialize();
			return new Channel(description.getSchema());
		}
		
		/**
		 * Creates a new instance of a Spire Channel
		 * The channel would be completely agnostic about the current
		 * authenticated session. However, providing a capability and a Channel Uri
		 * this channel could be used to publish messages
		 * 
		 * 
		 * @param capability
		 * @param url
		 * @return {@link Channel}
		 * @throws ResponseException
		 * @throws IOException
		 */
		public static Channel createChannel(Capability capability, String url) throws ResponseException, IOException{
			initialize();
			return createResource(capability, url, Channel.class);
		}
		
		/**
		 * Creates a new instance of a Spire Subscription
		 * The subscription would be completely agnostic about the current
		 * authenticated session (if any). However, providing a capability and a 
		 * Subscription Uri this subscription is capable of listening and retrieving messages
		 * from the channels it is subscribed to.
		 * 
		 * @param capability
		 * @param url
		 * @return {@link Subscription}
		 * @throws ResponseException
		 * @throws IOException
		 */
		public static Subscription createSubscription(Capability capability, String url) throws ResponseException, IOException{
			initialize();
			return createResource(capability, url, Subscription.class);
		}
		
		/**
		 * Creates new instances of spire resources
		 * 
		 * @param capability
		 * @param url
		 * @param T is the class of the Spire Resource to create
		 * @return T new instance
		 * @throws ResponseException
		 * @throws IOException
		 */
		public static <T>T createResource(Capability capability, String url, Class<T> T) throws ResponseException, IOException{
			initialize();
			Constructor<T> constructorT = null;
			T t = null;
			try {
				constructorT = T.getConstructor(ApiSchemaModel.class);
				t = constructorT.newInstance(description.getSchema());
				Resource r = Resource.class.cast(t);
				r.setCapability(capability);
				r.setUrl(url);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return t;
		}
	}
}

