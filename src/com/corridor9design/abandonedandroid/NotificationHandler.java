package com.corridor9design.abandonedandroid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

public class NotificationHandler {

	public void launchNotification(Context context, int severity) {
		
		if(MainActivity.getPreferences("isFirstRun", context).equals("yes")){
			Toast.makeText(context, "Don't forget about me...", Toast.LENGTH_SHORT).show();
			MainActivity.setPreferences("isFirstRun", "no", context);
			return;
		}
		
		@SuppressWarnings("deprecation")
		WakeLock screenOn = ((PowerManager)context.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "ShowLoneliness");
		screenOn.acquire();
		
		NotificationManager nManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		
		NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
			.setAutoCancel(true)
			.setSmallIcon(R.drawable.ic_launcher)
			.setTicker("I'm getting lonely here...")
			.setContentTitle("Abandoned")
			.setContentText("Letting you know when I'm neglected...");
			//.setDefaults(Notification.DEFAULT_ALL);
		
		// OPEN APPLICATION ON CLICK
		// Creates an explicit alarmHandlerIntent for an Activity in your app
		Intent resultIntent = new Intent(context, MainActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
		notification.setContentIntent(resultPendingIntent);
		
		
		nManager.notify(6810293, notification.build());
		Log.d("Notification", "Completed");
		screenOn.release();

	}
	
	public void cancelNotifications(Context context) {
		// cancel all notifications
		NotificationManager nManager = (NotificationManager) context.getApplicationContext().getSystemService(
				Context.NOTIFICATION_SERVICE);
		nManager.cancelAll();

	}


}
