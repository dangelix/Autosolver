package com.tikal.tallerWeb.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConvertidor {
	public static Object fromJson(String json, Class clase){
		Gson g= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
		
		return g.fromJson(json,clase);
	}
	
	public static String toJson(Object o){
		Gson g= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();;
		return g.toJson(o);
	}
}
