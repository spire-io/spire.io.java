# Java Spire.io client

This is a Java client for using the spire.io service.

Add the Spire.io jar file to your classpath:

  * /build/spire-io-client-1.0.0-beta.jar

The Spire.io client has the following dependencies, which should also be added to your classpath:

  * /lib/google-http-client-1.6.0-beta.jar
  * /lib/dependencies/jsr305-1.3.9.jar
  * /lib/dependencies/gson-1.7.1.jar
  * /lib/dependencies/guava-r09.jar
  * /lib/dependencies/junit-4.8.2.jar
  * /lib/dependencies/httpclient-4.0.3.jar
  * /lib/dependencies/xpp3-1.1.4c.jar
  * /lib/dependencies/protobuf-java-2.2.0.jar

All Spire.io jar dependencies can be located inside the '/lib' directory.

## Basic usage

The `Spire` class provides a simplified spire.io client with a high level interface.  Users of this class do not have to pay attention to details of the REST API.
Here's an example using the message service.  It assumes you have an account key, which you can get by registering at [www.spire.io](http://www.spire.io/register.html)

    import io.spire.Spire;
    import io.spire.api.*;

    Spire spire = new Spire();
    spire.start(accountKey);    //key is your account key
    
    Channel channelFoo = spire.createChannel("foo_channel");    //create channel
    Message message1 = channelFoo.publish("Hello Spire!");      //publish message
    
Let's create a second session and get our messages.

    Spire spire2 = new Spire();
    spire2.start(accountKey);
    
    Subscription subscriptionFoo = spire2.subscribe("subscriptionFoo", "foo_channel");
    
    Events events = subscriptionFoo.retrieveMessages();
    Message message = events.getMessages().get(0);
    System.out.println(message.getContent().toString()); // => "Hello Spire!"
    
You can also assign listener blocks to a subscription which will be called with each message received:

    Spire spire3 = new Spire();
    spire3.start(accountKey);
    
    Subscription subscriptionBar = spire3.subscribe("subscriptionBar", "foo_channel");
    
    // MyListener class implements io.spire.api.Listener#process
    MyListener myListener1 = new MyListener();
    subscriptionBar.addListener(myListener1);
    
    subscriptionBar.startListening();
    
The subscription object will continue to monitor the channel until you call `#stopListening` on it.

You can add as many listeners as you want.  They can be removed by name:

    MyListener myListener2 = new MyListener();
    int listener2Id = subscriptionBar.addListener(myListener2);
    
    subscriptionBar.removeListener(listener2Id);   // will remove myListener2
    // myListener1 is still alive...!


**Note:** Listener blocks are executed in separate threads, so please be careful when accessing shared resources.

More coding examples can be found within the '/examples' folder.