package com.bakalarka.bakalarkaProjekt;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;
import jakarta.ws.rs.sse.SseEventSource;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("SSE")
public class Proxy {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	Client client = ClientBuilder.newClient();
	WebTarget webTarget;


	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void getIt(@Context SseEventSink sseEventSink, @Context Sse sse) {
	    webTarget = client.target("http://localhost:8080/bakalarkaProjekt/webapi/SSE/info");
	}

	
	@GET
	@Path("info")
    @Produces(MediaType.SERVER_SENT_EVENTS)
	public void getSseEvents(@Context SseEventSink sseEventSink,@Context Sse sse) {
		
        OutboundSseEvent sseEvent = sse.newEventBuilder()
                .name("message")
                .id("idcko")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data("WAAAAAAAAAA")
                .reconnectDelay(5000)
                .comment("price change")
                .build();
        sseEventSink.send(sseEvent);
        sseEventSink.close();
	}

}
