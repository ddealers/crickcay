package gt.com.santillana.trazos.android;

import android.graphics.Rect;

public class DirtyRect {
	
	private Rect dirtyRect;
	
	public DirtyRect() {
		dirtyRect = new Rect();
	}
	
	
	/**
	 * Called when replaying history to ensure the dirty region includes all
	 * points.
	 */
	public void expandDirtyRect(float historicalX, float historicalY) {
		if (historicalX < dirtyRect.left) {
			dirtyRect.left = (int)historicalX;
		} else if (historicalX > dirtyRect.right) {
			dirtyRect.right = (int)historicalX;
		}
		if (historicalY < dirtyRect.top) {
			dirtyRect.top = (int)historicalY;
		} else if (historicalY > dirtyRect.bottom) {
			dirtyRect.bottom = (int)historicalY;
		}
	}

	/**
	 * Resets the dirty region when the motion event occurs.
	 */
	public void resetDirtyRect(float eventX, float eventY) {

		// The lastTouchX and lastTouchY were set when the ACTION_DOWN
		// motion event occurred.
		dirtyRect.left = (int)eventX;
		dirtyRect.right = (int)eventX;
		dirtyRect.top = (int)eventY;
		dirtyRect.bottom = (int)eventY;
	}
	
	public Rect getRect() {
		return dirtyRect;
	}
}