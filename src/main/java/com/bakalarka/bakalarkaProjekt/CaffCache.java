package com.bakalarka.bakalarkaProjekt;

import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("BasicResource")
@ApplicationScoped
public class CaffCache {
	public Cache<String, String> cache;
	
	@Inject
	Proxy proxy;

	@GET
	public void init() {
		cache = Caffeine.newBuilder()
				.expireAfterWrite(5, TimeUnit.SECONDS)
				.maximumSize(100)
				.build();
		cache.put("Kluc", "hodnota");
		cache.put("Kluc2", "hodnota2");
		loop(cache);
	}
	
	public void loop(Cache<String,String> cache) {
	     for (Entry<String, String> entry : cache.asMap().entrySet()) {
	    	 proxy.sendEvents(entry.getKey());
	    } 
	}
	
}