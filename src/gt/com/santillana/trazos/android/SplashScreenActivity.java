package gt.com.santillana.trazos.android;

import gt.com.santillana.trazos.android.controller.AppManager;
import gt.com.santillana.trazos.utils.MemoryManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreenActivity extends Activity {

	private Handler mHandler;
	public int loaderNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		ActivityManager mgr = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		mgr.getMemoryInfo(info);
		Log.w("Activity", "SplashScreenActivity Create");
		Log.w("Memory Information", "AvialMem:" + info.availMem/(1024*1024) + " M");
		
		mHandler = new Handler();
		loaderNum = 0;
		findViewById(R.id.splashscreen_loading1).setEnabled(false);
		findViewById(R.id.splashscreen_loading2).setEnabled(false);
		findViewById(R.id.splashscreen_loading3).setEnabled(false);
		findViewById(R.id.splashscreen_loading4).setEnabled(false);
		findViewById(R.id.splashscreen_loading5).setEnabled(false);
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		mHandler.removeCallbacks(stopInformacionProgressBar);
		mHandler.postDelayed(stopInformacionProgressBar, 500);
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		
		MemoryManager.unbindDrawables( findViewById(R.id.splashscreen_santillanaLogo) );
		MemoryManager.unbindDrawables( findViewById(R.id.splashscreen_loading1) );
		MemoryManager.unbindDrawables( findViewById(R.id.splashscreen_loading2) );
		MemoryManager.unbindDrawables( findViewById(R.id.splashscreen_loading3) );
		MemoryManager.unbindDrawables( findViewById(R.id.splashscreen_loading4) );
		MemoryManager.unbindDrawables( findViewById(R.id.splashscreen_loading5) );
	}
	
	@Override
	public void onDestroy(){
		ActivityManager mgr = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		mgr.getMemoryInfo(info);
		Log.w("Activity", "SplashScreenActivity Destroy");
		Log.w("Memory Information", "AvialMem:" + info.availMem/(1024*1024) + " M");
		
		super.onDestroy();
	}

	private Runnable stopInformacionProgressBar = new Runnable() {
		public void run() {
			
			switch(loaderNum)
			{
				case 1:
					findViewById(R.id.splashscreen_loading1).setEnabled(true);
					break;
				case 2:
					findViewById(R.id.splashscreen_loading2).setEnabled(true);
					break;
				case 3:
					findViewById(R.id.splashscreen_loading3).setEnabled(true);
					break;
				case 4:
					findViewById(R.id.splashscreen_loading4).setEnabled(true);
					break;
				case 5:
					findViewById(R.id.splashscreen_loading5).setEnabled(true);
					break;
				case 6:
					startActivity(new Intent().setComponent(
							new ComponentName(SplashScreenActivity.this,
									MenuActivity.class)));
					finish();
					break;
			}
			if(loaderNum <6)
			{
				loaderNum++;
				if(AppManager.getInstance().isInDeveloperMode())
					mHandler.postDelayed(stopInformacionProgressBar, 5);//to reduce time to load during development 
				else
					mHandler.postDelayed(stopInformacionProgressBar, 500);
			}
		}
	};
}
