package com.digitalgeko.mobile.android.complements;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public final class ScreenShot{
	
	@SuppressWarnings("static-access")
	public static void saveScreenShot(String name, Context context, View layout) {
    	File path = new File(Environment.getExternalStorageDirectory(), "DCIM");
    	Resources appR = context.getResources(); 
    	String directory = appR.getText(appR.getIdentifier("app_name",  
    			"string", context.getPackageName())).toString();
    	path = new File(path, directory);
    	
    	context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
    	
    	if(!path.exists()) {
    		path.mkdir();
    	}
    	
    	try {
    		Bitmap bitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_8888);
    		Canvas canvas = new Canvas(bitmap);
    		layout.draw(canvas);
    		
    		/*View v = layout.getRootView();
        	v.setDrawingCacheEnabled(true);
        	Bitmap b = v.getDrawingCache();*/
        	
        	File f = new File(path, name + ".png");
	    	f.createNewFile();
	
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
	    	byte[] bitmapdata = bos.toByteArray();
	
	    	@SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(f);
	    	fos.write(bitmapdata);
	    	
	    	Toast.makeText(context, "Imágen guardada", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Log.e(context.NOTIFICATION_SERVICE, e.getMessage());
			e.printStackTrace();
		}
    }
    
}