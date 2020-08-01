package com.example.JBProject2.beans;

import com.example.JBProject2.facades.ClientFacade;

public class Session {
	
	private ClientFacade facade;
	private long lastActive;
	
	public Session(ClientFacade facade, long lastActive) {
		super();
		this.facade = facade;
		this.lastActive = lastActive;
	}

	public ClientFacade getFacade() {
		return facade;
	}

	public void setFacade(ClientFacade facade) {
		this.facade = facade;
	}

	public long getLastActive() {
		return lastActive;
	}

	public void setLastActive(long loginTime) {
		this.lastActive = loginTime;
	}
	
	

}
