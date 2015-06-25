package gt.com.santillana.trazos.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

public class BaseActivity extends Activity {
	protected BroadcastReceiver lockScreenReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i("LOCK_SCREEN_RECEIVER", "Registering receiver");

		lockScreenReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.i("LOCK_SCREEN_RECEIVER", "Moving to background " );
				
				moveTaskToBack(true);
			}
		};
		
		IntentFilter filter = new IntentFilter();
		
		filter.addAction( Intent.ACTION_SCREEN_OFF );
//		filter.addAction( Intent.ACTION_SCREEN_ON );

		registerReceiver( lockScreenReceiver, filter );

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("LOCK_SCREEN_RECEIVER", "Unegistering receiver");
		
		unregisterReceiver(lockScreenReceiver);
	}

}
