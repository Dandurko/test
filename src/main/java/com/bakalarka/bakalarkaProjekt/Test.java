package com.bakalarka.bakalarkaProjekt;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("WebSocket")
public class Test {
	  @GET
	    @Produces(MediaType.TEXT_PLAIN)
	    public String getIt() {
	        return "Got!";
	    }
}
