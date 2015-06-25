package gt.com.santillana.trazos.utils.bitmaps;

import java.lang.ref.WeakReference;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class BitmapHandler{

	public static void loadBitmap(int resId, ImageView imageView, Resources res) {
		BitmapWorkerTask task = new BitmapHandler().new BitmapWorkerTask(imageView, res);
		task.execute(resId);
		/*if (AsyncDrawable.cancelPotentialWork(resId, imageView)) {
			final BitmapWorkerTask task = new BitmapHandler().new BitmapWorkerTask(imageView, res);
			task.execute(resId);
		}
		else
			Log.i("BITMAP_HANDLER", "******* No hizo nada! *****");*/
	}
	
	/*public static void loadBitmapForList(int resId, ImageView imageView, Resources res, Bitmap mPlaceHolderBitmap) {
		if (AsyncDrawable.cancelPotentialWork(resId, imageView)) {
			final BitmapWorkerTask task = new BitmapHandler().new BitmapWorkerTask(imageView, res);
			final AsyncDrawable asyncDrawable = new AsyncDrawable(
					res, mPlaceHolderBitmap, task);
			imageView.setImageDrawable(asyncDrawable);
			task.execute(resId);
		}
	}*/
	
	class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
		private final WeakReference<ImageView> imageViewReference;
		private int data = 0;
		private Resources res;

		public BitmapWorkerTask(ImageView imageView, Resources res) {
			// Use a WeakReference to ensure the ImageView can be garbage
			// collected
			imageViewReference = new WeakReference<ImageView>(imageView);
			this.res = res;
		}

		// Decode image in background.
		@Override
	    protected Bitmap doInBackground(Integer... params) {
	        data = params[0];
	        return BitmapManager.decodeSampledBitmapFromResource(res, data, 300, 300);//this value must be changed
	    }

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (imageViewReference != null && bitmap != null) {
	            final ImageView imageView = imageViewReference.get();
	            if (imageView != null) {
	                imageView.setImageBitmap(bitmap);
	            }
	        }

		}

	}

	/*static class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

		public AsyncDrawable(Resources res, Bitmap bitmap,
				BitmapWorkerTask bitmapWorkerTask) {
			super(res, bitmap);
			bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
					bitmapWorkerTask);
		}

		public BitmapWorkerTask getBitmapWorkerTask() {
			return bitmapWorkerTaskReference.get();
		}

		private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
			if (imageView != null) {
				final Drawable drawable = imageView.getDrawable();
				if (drawable instanceof AsyncDrawable) {
					final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
					return asyncDrawable.getBitmapWorkerTask();
				}
			}
			return null;
		}

		public static boolean cancelPotentialWork(int data, ImageView imageView) {
			final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

			if (bitmapWorkerTask != null) {
				final int bitmapData = bitmapWorkerTask.data;
				if (bitmapData != data) {
					// Cancel previous task
					bitmapWorkerTask.cancel(true);
				} else {
					// The same work is already in progress
					return false;
				}
			}
			// No task associated with the ImageView, or an existing task was
			// cancelled
			return true;
		}

	}*/

}
