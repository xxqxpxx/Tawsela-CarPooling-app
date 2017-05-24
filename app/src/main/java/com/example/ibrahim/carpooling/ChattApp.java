package com.example.ibrahim.carpooling;

import android.app.Application;

import com.parse.Parse;

/**
 * The Class ChattApp is the Main Application class of this app. The onCreate
 * method of this class initializes the Parse.
 */
public class ChattApp extends Application
{

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();

		Parse.initialize(this, "JuQL8yEJ27ubNqZCZzI0lHauKBH1t9lthN8RC9UJ", "qYFNXqaxtk6OQHSvcEVi7ek5gRRiW6A5nssJTYAr");

	}
}
