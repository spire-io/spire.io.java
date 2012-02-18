
# Synopsis

`spire.io.java` is a Java client for the [spire.io API](http://www.spire.io/).

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
    
    // MyListener class implements io.spire.api.Listener interface
    MyListener myListener1 = new MyListener();
    subscriptionBar.addListener(myListener1);
    
    // start listening
    subscriptionBar.startListening();
    
You can add as many listeners as you want.  They can be removed by name:
    
    // lets create another listener
    MyListener myListener2 = new MyListener();
    int listener2Id = subscriptionBar.addListener(myListener2);
    
    subscriptionBar.removeListener(listener2Id);   // will remove myListener2
    // myListener1 is still alive...!

The subscription object will continue to monitor the channel until you call `#stopListening` on it.
    
    // done listening
    subscriptionBar.stopListening();

**Note:** Listener blocks are executed in separate threads, so please be careful when accessing shared resources.

## Low level interface

The `io.spire.api.Api` class provides a low level spire.io client that allows you to work directly with the REST API.  The higher level `io.spire.Spire` class is a wrapper around this foundation.  Where `Spire` hides the underlying HTTP traffic from the developer, sometimes making multiple requests within a single method call, `io.spire.api.Api` typically makes one request per method and expects the developer to deal with the consequences.  It also (optionally) exposes the actual HTTP requests used to interact with spire.io.

Usage:

    Api api = Spire.SpireFactory.createApi();
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

## What is spire.io?

[spire.io](http://spire.io) is a platform as service API.

## Working with this library

* [source code](https://github.com/spire-io/spire.io.java)
* [inline documentation](http://spire-io.github.com/spire.io.java/) (via [javadoc](http://www.oracle.com/technetwork/java/javase/documentation/index-jsp-135444.html))
* [issues](https://github.com/spire-io/spire.io.java/issues)
* [contact spire.io](http://spire.io/contact.html)

# Installation

Add the Spire.io jar file to your classpath:

  * /build/spire-io-client-1.0.0-beta.1.jar

The Spire.io client has a few dependencies can be found inside the `/lib` directory. All of these library dependencies should also be added to you classpath.

# Development

## Tests

The test suite can be run via:

    rake test

# Contributing

Fork and send pull requests via github, also any [issues](https://github.com/spire-io/spire.io.java/issues) are always welcome

# License

Open Source Initiative OSI - The MIT License (MIT):Licensing

MIT LICENSE
Copyright (c) 2011 spire.io