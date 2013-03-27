package com.corridor9design.abandonedandroid;

import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		abandonMe();
	}
	
	@Override
	protected void onStop() {
	    super.onStop();  // Always call the superclass method first
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
			Calendar startDelay = Calendar.getInstance();
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					
					startDelay.add(Calendar.SECOND, 2);
					
					Log.d("LonelyAndroid Activated",""+startDelay.getTime());
						
					amanager.set(AlarmManager.RTC_WAKEUP, startDelay.getTimeInMillis(), pending);
				} else {
					amanager.cancel(pending);
					Log.d("LonelyAndroid Deactivated", ""+startDelay.getTime());
					finish();
				}
			}
		});
	}

}
