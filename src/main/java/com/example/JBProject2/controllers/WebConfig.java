package com.example.JBProject2.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.JBProject2.facades.ClientFacade;

@Configuration
public class WebConfig {

	@Bean
	public Map<String, ClientFacade> sessions(){
		System.out.println("creating sessions!");
		return new HashMap<String, ClientFacade>();
	}

}
