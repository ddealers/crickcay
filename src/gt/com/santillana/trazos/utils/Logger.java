package gt.com.santillana.trazos.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class Logger {

	public static void AvailMemory(Context context)
	{
		ActivityManager mgr = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		mgr.getMemoryInfo(info);
		Log.w("Memory Information", "AvailMem:" + info.availMem/1048576 + " MB");// 1048576 = (1024*1024) 
	}
}
