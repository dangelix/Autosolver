package com.tikal.tallerWeb.modelo.servicio;

import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class Person
{
	/** */
	
	/**
	 * Synthetic id
	 */
	@Id
	Long id;
	
	/** Pretty, non-normalized version */
	String email;

	/** Date user first logged in */
	@Index
	Date created;

	/** Date user last logged in */
	@Index
	Date lastLogin;
	
	public Person(){
		
	}
	
	/**
	 */
	public Person(String email) {
		this.email = email;
		this.created = new Date();
	}

	/** */
	public void loggedIn() {
		this.lastLogin = new Date();
	}

//	public Key<Person> getKey() {
//		return key(id);
//	}
}
