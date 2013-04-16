package com.corridor9design.abandonedandroid;

import java.util.Calendar;
import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AbandonedAlarm extends BroadcastReceiver {
	
	Calendar nextAlarm = Calendar.getInstance();
	NotificationHandler notifications = new NotificationHandler();
	
	public AbandonedAlarm() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		// reset the alarm if user interacts with device
		if (intent.getAction()!=null){
			if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
				//nextAlarm(context, randomizeAlarm(30, 1, 1)); // TODO create interface that allows user to set longestSpan, shortestSpan, and severity
				
				context.stopService(MainActivity.alarmHandlerIntent);
				return;
			}
		}
		
		notifications.launchNotification(context, 1);
		
		// get the number of seconds until the next alarm
		nextAlarm(context, randomizeAlarm(30, 1, 1)); // TODO create interface that allows user to set longestSpan, shortestSpan, and severity
	}
	
	private void nextAlarm(Context context, int seconds){
		Intent intent = new Intent(context, AbandonedAlarm.class);
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager amanager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		nextAlarm.add(Calendar.SECOND, seconds);
		
		amanager.set(AlarmManager.RTC_WAKEUP, nextAlarm.getTimeInMillis(), pending);
		
	}
	
	private int randomizeAlarm(int longestSpan, int shortestSpan, double severity){
		Random rand = new Random();
		// longestSpan = the longest length of time the device will go without alarming
		// shortestSpan = the shortest length of time the device will go before alarming again
		// severity = Divider that will shorten the longestSpan depending on sliding scale
		
		int initialLength = rand.nextInt((longestSpan - shortestSpan + 1) + shortestSpan);
		int finalCountdown = (int) (initialLength / severity);
		
		
		System.out.println(initialLength + "     " + severity);
		Log.d("Next Alarm", finalCountdown + " seconds remaining");
		return finalCountdown;
	}
}
