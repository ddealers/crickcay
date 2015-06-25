package gt.com.santillana.trazos.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MemoryManager {

	public static void unbindDrawables(View view) {
		if (view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		} else if (view instanceof ImageView) {
			Drawable drawable = ((ImageView) view).getDrawable();
			if(drawable != null) {
				drawable.setCallback(null);
			}
//			if (drawable instanceof BitmapDrawable) {
//			    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//			    Bitmap bitmap = bitmapDrawable.getBitmap();
//			    bitmap.recycle();
//			}
		}
	}
}
