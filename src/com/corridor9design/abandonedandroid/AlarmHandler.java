package com.corridor9design.abandonedandroid;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmHandler extends Service {

	Intent intent;
	PendingIntent pending;
	AlarmManager amanager;
	Calendar firstAlarm = Calendar.getInstance();

	public AlarmHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCreate() {
		intent  = new Intent(AlarmHandler.this, AbandonedAlarm.class);
		pending = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		amanager = (AlarmManager) getSystemService(ALARM_SERVICE);
				
		startAlarming();
	}
	
	public void onDestroy(){
		stopAlarming();
	}

	public void startAlarming() {
		// first alarm will activate 10 seconds after toggleButton
		// change to let user know of activation. Alarm is activated.
		firstAlarm.add(Calendar.SECOND, 1);
		Log.d("LonelyAndroid Activated", "" + firstAlarm.getTime());
		amanager.set(AlarmManager.RTC_WAKEUP, firstAlarm.getTimeInMillis(), pending);
	}

	public void stopAlarming() {
		// if toggleButton is off, cancel any alarm we've previously set
		// even if there are none due to first-start
		amanager.cancel(pending);
		Log.d("LonelyAndroid Deactivated", "" + firstAlarm.getTime());
	}

}
