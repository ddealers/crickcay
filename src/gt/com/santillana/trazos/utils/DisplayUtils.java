package gt.com.santillana.trazos.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public class DisplayUtils {

	private static final String TAG_LOG = "DisplayUtils";

	public final static int dpToPixels(int dp, Context context) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		int px = Math.round(dp
				* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}

	public final static int pixelsToDp(int pixels, Context context) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		int dp = Math.round(pixels
				/ (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return dp;
	}
	
	/**
	 * 
	 * @param containerStandardSize	Original size of the container in mdpi resolution.
	 * @param containerActualSize	Size of the container once it has been scaled during runtime.
	 * @param imageSize	Original size of the image in mdpi resolution.
	 * @return
	 */
	public static int getPercentage(double containerStandardSize, double containerActualSize, double imageSize){
		Log.i(TAG_LOG, "containerStandardSize: " + containerStandardSize);
		return (int) ((imageSize / containerActualSize) * containerStandardSize);
	}

	public final static void showScreenSize(Context context) {
		int screenSize = context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK;

		switch (screenSize) {
		case Configuration.SCREENLAYOUT_SIZE_XLARGE:
			Toast.makeText(context, "Large xtra screen", Toast.LENGTH_LONG)
					.show();
			break;
		case Configuration.SCREENLAYOUT_SIZE_LARGE:
			Toast.makeText(context, "Large screen", Toast.LENGTH_LONG).show();
			break;
		case Configuration.SCREENLAYOUT_SIZE_NORMAL:
			Toast.makeText(context, "Normal screen", Toast.LENGTH_LONG).show();
			break;
		case Configuration.SCREENLAYOUT_SIZE_SMALL:
			Toast.makeText(context, "Small screen", Toast.LENGTH_LONG).show();
			break;
		default:
			Toast.makeText(context,
					"Screen size is neither large, normal or small",
					Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Returns display width and height in pixels.
	 * @return Android.Graphics.Point [0] = Width, [1] = Height
	 */
	public final static Point getScreanMeasure(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		return new Point(display.getHeight(), display.getWidth());
	}
}
