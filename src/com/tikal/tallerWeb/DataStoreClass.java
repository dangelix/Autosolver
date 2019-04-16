package com.tikal.tallerWeb;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class DataStoreClass {
	public static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
}
