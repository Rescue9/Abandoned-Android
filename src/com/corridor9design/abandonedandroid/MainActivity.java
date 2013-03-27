package com.corridor9design.abandonedandroid;

import java.util.Calendar;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		checkToggleButtonState();
		
		abandonMe();
	}
	
	@Override
	protected void onStop() {
	    super.onStop();  // Always call the superclass method first
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private void abandonMe(){
		
		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

		toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			Intent intent = new Intent(MainActivity.this, AbandonedAlarm.class);
			PendingIntent pending = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			
			AlarmManager amanager = (AlarmManager) getSystemService(ALARM_SERVICE);
			Calendar firstAlarm = Calendar.getInstance();
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					
					firstAlarm.add(Calendar.SECOND, 10); // firstAlarm.add(Calendar.SECOND, Integer.parseInt(getPreferences("firstAlarmSpan", MainActivity.this)));
					
					Log.d("LonelyAndroid Activated",""+firstAlarm.getTime());
						
					amanager.set(AlarmManager.RTC_WAKEUP, firstAlarm.getTimeInMillis(), pending);
					
					setPreferences("ToggleState", "on", MainActivity.this);
				} else {
					amanager.cancel(pending);
					Log.d("LonelyAndroid Deactivated", ""+firstAlarm.getTime());
					setPreferences("ToggleState", "off", MainActivity.this);
					finish();
				}
			}
		});
	}

	public static void setPreferences(String key, String value, Context context) {
		SharedPreferences preferences = PreferenceManager
			.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreferences(String key, Context context) {
		SharedPreferences preferences = PreferenceManager
			.getDefaultSharedPreferences(context);
		return preferences.getString(key, "0");
	}
	
	public void checkToggleButtonState(){
		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

		if (getPreferences("ToggleState", MainActivity.this).equals("on")){
			toggleButton.setChecked(true);
		}
		

	}
}