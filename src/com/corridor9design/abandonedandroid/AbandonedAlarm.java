package com.corridor9design.abandonedandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class AbandonedAlarm extends BroadcastReceiver {

	public AbandonedAlarm() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		launchNotification(context, 1);
	}
	
	private void launchNotification(Context context, int severity){
		NotificationManager nManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		
		NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
			.setAutoCancel(true)
			.setSmallIcon(R.drawable.ic_launcher)
			.setTicker("I'm getting lonely here...")
			.setContentTitle("Abandoned")
			.setLights(Color.WHITE, 1, 0)
			.setContentText("Letting you know when I'm neglected...")
			.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
		
		// OPEN APPLICATION ON CLICK
		// Creates an explicit intent for an Activity in your app
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
		
		
		nManager.notify(1232879, notification.build());
		Log.d("Notification", "Completed");

	}

}
