package com.corridor9design.abandonedandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	Intent alarmHandlerIntent;
	ToggleButton toggleButton;
	
	public static final int ALARM_START = 123;
	public static final int REPEATING_ALARM = 234;
	
	NotificationHandler notifications = new NotificationHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		alarmHandlerIntent = new Intent(this, AlarmHandler.class);
		setContentView(R.layout.activity_main);

		// check button state to properly display
		assignToggleButton();
		checkToggleButtonState();

		// main method to start service
		abandonStart();
	}

	@Override
	protected void onStop() {
		super.onStop(); // Always call the superclass method first
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void abandonStart() {
		// give tobbleButton a listener to check for change in state this
		// new listener will implement the necessary methods if state is changed
		toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// set the toggleButton preference on
					setPreferences("ToggleState", "on", MainActivity.this);

					// set isFirstRun & userActivity
					setPreferences("isFirstRun", "yes", MainActivity.this);
					setPreferences("userActivity", "yes", MainActivity.this);
					startAlarmHandler();
				} else {
					// set the toggleButton off for next use
					setPreferences("ToggleState", "off", MainActivity.this);
					notifications.cancelNotifications(MainActivity.this);
					cancelAllAlarms();
					finish(); // exit the program if the button gets turned off
				}
			}
		});
	}

	// generic method to set the preferences
	public static void setPreferences(String key, String value, Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	// generic method to get preferences as needed
	public static String getPreferences(String key, Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(key, "0");
	}
	
	public void assignToggleButton(){
		toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
	}

	// check the toggleButton state; on or off as listed in the shared
	// preferences
	public void checkToggleButtonState() {
		toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

		if (getPreferences("ToggleState", MainActivity.this).equals("on")) {
			toggleButton.setChecked(true);
		}

	}
	
	private void startAlarmHandler(){
		this.startService(alarmHandlerIntent);
	}
	
	private void cancelAllAlarms(){
		this.stopService(alarmHandlerIntent);
	}
}
