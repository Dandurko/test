package com.bakalarka.bakalarkaProjekt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/giveValueToProxy/{username}")
public class Test {

	private Session session;
	private static Set<Test> chatEndpoints = new CopyOnWriteArraySet<>();
	private static HashMap<String, String> users = new HashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		this.session = session;
		chatEndpoints.add(this);
		users.put(session.getId(), username);

		// Send a welcome message to the user
		String message = "Welcome to the chat, " + username + "!";
		broadcast(message);
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		// Handle incoming messages
		String username = users.get(session.getId());
		String broadcastMessage = username + ": " + message;
		broadcast(broadcastMessage);
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		// WebSocket connection closes
		chatEndpoints.remove(this);
		String username = users.get(session.getId());
		users.remove(session.getId());

		// Notify other users that the user has left
		String message = username + " has left the chat.";
		broadcast(message);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Handle errors here
	}

	private void broadcast(String message) {
		for (Test endpoint : chatEndpoints) {
			synchronized (endpoint) {
				try {
					endpoint.session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					// Handle exception (e.g., log it)
				}
			}
		}
	}
}
