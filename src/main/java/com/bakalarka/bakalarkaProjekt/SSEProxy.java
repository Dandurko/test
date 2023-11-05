package com.bakalarka.bakalarkaProjekt;

import java.net.URI;


import javax.enterprise.context.ApplicationScoped;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("SSE")
@ApplicationScoped
public class SSEProxy   {

	   @Inject
	    private Sse sse ;

	   private  SseBroadcaster sseBroadcaster;

    URI uri = URI.create("ws://localhost:8080/WebSocket/WebSocket/Denko");
    public SSEProxy() {
        // Konštruktor
    }
    @PostConstruct
    public void init(){
        this.sseBroadcaster = sse.newBroadcaster();
    }
    @GET
    @Path("register")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void register(@Context SseEventSink sink) {

    	sseBroadcaster.register(sink);
    	WebSocketClientEndpoint client  = new WebSocketClientEndpoint();
    	client.setProxy(this);
    	client.makeCon(uri);


    }
    
    public void sendEvents(String key) {
    	sseBroadcaster.broadcast(sse.newEvent(key));
    }


}
