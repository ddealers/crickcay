package gt.com.santillana.trazos.utils.bitmaps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

public class BitmapManager {
	private static final String TAG_LOG = "BitmapManager";
	
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		
		Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
		Debug.getMemoryInfo(memoryInfo);
//		memoryInfo.;
		
		long availableMemory = Runtime.getRuntime().maxMemory() - memoryInfo.getTotalPss();
		Log.i(TAG_LOG, "Available Memory: " + availableMemory);
		
		// Raw height and width of image
		//final int height = options.outHeight;
		final int height = 200;
		final int width = options.outWidth;
		int inSampleSize = 1;
		
		//height=100;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = (height  - 500) / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2
			// and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
			Log.i("BitmapManager", " O. Size: Width = "+width+", Height = "+height+"");
			Log.i("BitmapManager", " R. Size: Width = "+reqWidth+", Height = "+reqHeight+"");
		}
		return inSampleSize;
	}

	/**
	 * Returns a scaled not inMutable Bitmap to save memory.
	 * 
	 * @param res	resources
	 * @param resId	drawableId
	 * @param reqWidth	required image width in pixels
	 * @param reqHeight	required image height in pixels
	 * @return	scaled bitmap.
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {
		
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight, boolean inMutable) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		
		if(reqWidth > options.outWidth || reqHeight > options.outHeight)
		{
			options.inJustDecodeBounds = false;
			options.inMutable = inMutable;
			return BitmapFactory.decodeResource(res, resId, options);
		}

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		options.inMutable = inMutable;
		
		Bitmap sampledBitmap = BitmapFactory.decodeResource(res, resId, options);
		
		if(sampledBitmap.getWidth() > reqWidth || sampledBitmap.getHeight() > reqHeight )
		{
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(sampledBitmap, reqWidth, reqHeight, true);
			sampledBitmap.recycle();
			System.gc();
			return scaledBitmap;
		}
		else
			return sampledBitmap;
	}
	
	/**
	 * Converts a immutable bitmap to a mutable bitmap. This operation doesn't allocates
	 * more memory that there is already allocated but requires permission to write in external storage.
	 * 
	 * @param imgIn - Source image. It will be released, and should not be used more
	 * @return a copy of imgIn, but muttable.
	 */
	public static Bitmap convertToMutable(Bitmap imgIn) {
	    try {
	        //this is the file going to use temporally to save the bytes. 
	        // This file will not be a image, it will store the raw image data.
	        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");

	        //Open an RandomAccessFile
	        //Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
	        //into AndroidManifest.xml file
	        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

	        // get the width and height of the source bitmap.
	        int width = imgIn.getWidth();
	        int height = imgIn.getHeight();
	        Config type = imgIn.getConfig();

	        //Copy the byte to the file
	        //Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
	        FileChannel channel = randomAccessFile.getChannel();
	        MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, imgIn.getRowBytes()*height);
	        imgIn.copyPixelsToBuffer(map);
	        //recycle the source bitmap, this will be no longer used.
	        imgIn.recycle();
	        System.gc();// try to force the bytes from the imgIn to be released

	        //Create a new bitmap to load the bitmap again. Probably the memory will be available. 
	        imgIn = Bitmap.createBitmap(width, height, type);
	        map.position(0);
	        //load it back from temporary 
	        imgIn.copyPixelsFromBuffer(map);
	        //close the temporary file and channel , then delete that also
	        channel.close();
	        randomAccessFile.close();

	        // delete the temp file
	        file.delete();

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } 

	    return imgIn;
	}
}
