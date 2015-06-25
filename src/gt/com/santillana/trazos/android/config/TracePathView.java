package gt.com.santillana.trazos.android.config;

import gt.com.santillana.trazos.android.PathValidator;
import gt.com.santillana.trazos.android.controller.AppManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;

import com.digitalgeko.mobile.android.accesories.GeneralMethods;

public class TracePathView extends View {
	
	public static final float MINP = 0.25f;
	public static final float MAXP = 0.75f;
	private static final float TOUCH_TOLERANCE = 5;

	public static final int MODE_DRAW = 1;

	private Activity activity;
	private Resources res;
	/**
	 * Determina el modo actual del juego: trazo o pintar.
	 */
	private int topMargin = 1;
	private int leftMargin = 1;
	private int bitmapWidth = 1;
	private int bitmapHeight = 1;
	private float mX;
	private float mY;
	/**
	 * Imágen de el gusano.
	 */
	private Bitmap modelLayerBitmap;
	/**
	 * Canvas con la imágen del gusano. aquí pintamos el trazo.
	 */
	private Canvas modelLayerCanvas;
	/**
	 * Canvas con la imágen de fondo. aquí pintamos cuando el usuario colorea
	 * (modo de pintado).
	 */
	private Path modelPath;
	private Paint modelLayerPaint;
	private Paint mPaint;
	
	// Load new 
	private int screenSolutionId;
	private int screenDrawId;
	private int screenBackgroundId;
	
	public List<Path> validPaths;
	public Path tempPath;
	
	public TracePathView(Context c) {
		super(c);
		modelPath = new Path();
		modelLayerPaint = new Paint(Paint.DITHER_FLAG);

		measure = new PathMeasure(modelPath, false);
		setDrawingCacheEnabled(false);
		
		validPaths = new ArrayList<Path>();
		tempPath = new Path();
	}

	public Paint getmPaint() {
		return mPaint;
	}

	public void setmPaint(Paint mPaint) {
		this.mPaint = mPaint;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public int getScreenSolutionId() {
		return screenSolutionId;
	}
	
	public void setScreenSolutionId(int screenSolutionId) {
		this.screenSolutionId = screenSolutionId;
	}
	
	public int getScreenDrawId() {
		return screenDrawId;
	}

	public void setScreenDrawId(int screenDrawId) {
		this.screenDrawId = screenDrawId;
	}

	public int getScreenBackgroundId() {
		return screenBackgroundId;
	}

	public void setScreenBackgroundId(int screenBackgroundId) {
		this.screenBackgroundId = screenBackgroundId;
	}

	public void changeEraserState(boolean state) {
		isErasing = state;
		if (isErasing) {
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		} else {
			mPaint.setXfermode(null);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		res = getResources();

		Bitmap mainBitmap = BitmapFactory.decodeResource(res,
				getScreenDrawId());

		h = h - 20;
		float bmpHeight = mainBitmap.getHeight();
		float bmpWidth = mainBitmap.getWidth();

		float factor = h / bmpHeight;
		float newHeight = h;
		float newWidth = bmpWidth * factor;

		if (newWidth > w) {
			factor = w / bmpWidth;
			newWidth = w;
			newHeight = bmpHeight * factor;
		}

		// Set bitmaps sizes
		bitmapHeight = (int) newHeight;
		bitmapWidth = (int) newWidth;
		
		leftMargin = (w - bitmapWidth) / 2;
		topMargin = (h - bitmapHeight) / 2;

		modelLayerBitmap = Bitmap.createScaledBitmap(mainBitmap, bitmapWidth,
				bitmapHeight, true);
		modelLayerCanvas = new Canvas(modelLayerBitmap);
	}

	private Path paintPath = new Path();
	private float distancePaintPath = 0;
	private PathMeasure measure;
	private boolean isErasing = false;

	@Override
	protected void onDraw(Canvas canvas) {
		// mover el canvas al centro.
		canvas.save();
		canvas.translate(leftMargin, 0);
		// de aquí en adelande se dibuja en el canvas.

		// se dan cuenta, se pone el "x" y "y" como 0, porque el translate() ya
		// movio todo el canvas por nosotros...
		
		modelLayerPaint.setColor(0xffffffff);
		canvas.drawRect(0, 0, bitmapWidth, bitmapHeight, modelLayerPaint);
		canvas.drawBitmap(modelLayerBitmap, 0, 0, modelLayerPaint);
		mPaint.setStrokeWidth(5);
		modelLayerCanvas.drawPath(modelPath, mPaint);
		distancePaintPath = 0;

		// restaurar el estado anterior del canvas
		canvas.restore();
	}

	private void touch_start(float x, float y) {
		modelPath.reset();
		modelPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);

		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			modelPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);

			mX = x;
			mY = y;

			synchronized (paintPath) {
				paintPath.reset();
				measure.setPath(modelPath, false);
				measure.getSegment(distancePaintPath, measure.getLength(),
						paintPath, true);
			}
		}
	}

	private void touch_up() {
		touch_move(mX, mY);

		// String result
		PathMeasure measure = new PathMeasure(modelPath, false);
		tempPath.reset();
		measure.getSegment(0, measure.getLength(), tempPath, true); 
		GeneralMethods.showDialogYesNo("�Agregar?", getContext(), getActivity(), new Runnable() {
			@Override
			public void run() {
				addToValidPaths();
			}
		});

		// kill this so we don't double draw
		modelPath.reset();
		paintPath.reset();
	}
	
	public void addToValidPaths() {
		PathMeasure measure = new PathMeasure(this.tempPath, false);
		Path tempPath = new Path();
		measure.getSegment(0, measure.getLength(), tempPath, true); 
		validPaths.add(tempPath);
	}
	
	public void saveValidPaths() {
		// Build strings
		String s = generateJSON();

		// File
		try {
			File dirs = new File(Environment.getExternalStorageDirectory(), "Trazos/");
			if(!dirs.exists()) {
				dirs.mkdirs();
			}
			AppManager appManager = AppManager.getInstance();
			String fileName = "level"+appManager.getCurrentLevelId() + "_"+appManager.getCurrentStageID();
			File file = new File(dirs, fileName + ".json");
			FileOutputStream filecon = new FileOutputStream(file);
			byte[] myByte = s.getBytes("UTF-8");
			filecon.write(myByte);
			filecon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX() - leftMargin;
		float y = event.getY() - topMargin;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			invalidate(new Rect((int)mX - 20 + leftMargin, (int)mY - 20 + topMargin, 
					(int)mX + 20 + leftMargin, (int)mY + 20 + topMargin));
			break;
		case MotionEvent.ACTION_MOVE:
			// Load previes movements
			for (int i = 0; i < event.getHistorySize(); i++) {
				float historicalX = event.getHistoricalX(i) - leftMargin;
				float historicalY = event.getHistoricalY(i) - topMargin;
				touch_move(historicalX, historicalY);
			}
			// Load current move
			touch_move(x, y);
			
			// Region region = new Region();
			// region.setPath(paintPath, clip);
			// Rect rect = region.getBounds();
			// invalidate(rect);

			// RectF rectf = dirtyRect;
			// Rect rect = new Rect((int)rectf.left - 20, (int)rectf.top - 20,
			// (int)rectf.right + 20, (int)rectf.bottom + 20);
			// // Log.i("Draw region: ", rect.toShortString());
			// invalidate(rect);

			RectF rectf = new RectF();
			paintPath.computeBounds(rectf, true);
			Rect rect = new Rect(
					(int) rectf.left - 25 + leftMargin, (int) rectf.top - 25 + topMargin,
					(int) rectf.right + 25 + leftMargin, (int) rectf.bottom + 25 + topMargin);
			invalidate(rect);
			
			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			// invalidate();
			invalidate(new Rect((int) mX - 5 + leftMargin, (int) mY - 5 + topMargin, 
					(int) mX + 5 + leftMargin, (int) mY + 5 + topMargin));
			break;
		}
		return true;
	}
	
	public String generateJSON() {
		// validate the user path
		StringBuilder builder = new StringBuilder();
		for(Path path : validPaths) {
			PathMeasure measure = new PathMeasure(path, false);
			int count = 100;
			float distance = 0;
			float speed = measure.getLength() / count;
			double[][] points = new double[count][2];
			float[] pos = new float[2];
			float sumX = 0;
			float sumY = 0;
			builder.append("{\"normal\":[\n");
			for(int i = 0; i < count && distance < measure.getLength(); i++) {
				measure.getPosTan(distance, pos, null);
				points[i][0] = pos[0] / bitmapWidth;
				points[i][1] = pos[1] / bitmapHeight;
				
				sumX += points[i][0];
				sumY += points[i][1];
				
				builder.append("{\"x\":\"");
				builder.append(points[i][0]);
				builder.append("\",\"y\":\"");
				builder.append(points[i][1]);
				builder.append("\"}");
				if(i != count-1) builder.append(",");
				builder.append("\n");
				
				distance += speed;
			}
			builder.append("],");
			builder.append("\n");
			
			// Average x
			float averagex = sumX / count;
			builder.append("\n");
			builder.append("\"average_x\": [\n");
			for(int i = 0; i < count; i++) {
				builder.append("{\"x\":\"");
				builder.append(averagex);
				builder.append("\",\"y\":\"");
				builder.append(points[i][1]);
				builder.append("\"}");
				if(i != count-1) builder.append(",");
				builder.append("\n");
			}
			builder.append("],");
			builder.append("\n");
			
			// Average y
			float averageY = sumY / count; 
			builder.append("\n");
			builder.append("\"average_y\": [\n");
			for(int i = 0; i < count; i++) {
				builder.append("{\"x\":\"");
				builder.append(points[i][0]);
				builder.append("\",\"y\":\"");
				builder.append(averageY);
				builder.append("\"}");
				if(i != count-1) builder.append(",");
				builder.append("\n");
			}
			builder.append("]");
			builder.append("\n");
			builder.append("}");
		}
		
		return builder.toString();
	}
	
	public String generateString() {
		// validate the user path
		PathMeasure measure = new PathMeasure(modelPath, false);
		int count = 100;
		float distance = 0;
		float speed = measure.getLength() / count;
		double[][] points = new double[count][2];
		float[] pos = new float[2];
		StringBuilder builder = new StringBuilder();
		float sumX = 0;
		float sumY = 0;
		builder.append("normal\n");
		for(int i = 0; i < count && distance < measure.getLength(); i++) {
			measure.getPosTan(distance, pos, null);
			points[i][0] = pos[0] / bitmapWidth;
			points[i][1] = pos[1] / bitmapHeight;
			
			sumX += points[i][0];
			sumY += points[i][1];
			
			builder.append(points[i][0]);
			builder.append(",");
			builder.append(points[i][1]);
			builder.append("\n");
			
			distance += speed;
		}
		
		// Average x
		float averagex = sumX / count;
		builder.append("\n");
		builder.append("average_x\n");
		for(int i = 0; i < count; i++) {
			builder.append(averagex);
			builder.append(",");
			builder.append(points[i][1]);
			builder.append("\n");
		}
		
		// Average y
		float averageY = sumY / count; 
		builder.append("\n");
		builder.append("average_y\n");
		for(int i = 0; i < count; i++) {
			builder.append(points[i][0]);
			builder.append(",");
			builder.append(averageY);
			builder.append("\n");
		}
		builder.append("\n");
		
		// Foot note
		builder.append("\n");
		builder.append("Average X: ");
		builder.append(sumX / count);
		builder.append("\n");
		builder.append("Average Y: ");
		builder.append(sumY / count);
		builder.append("\n");
		
		return builder.toString();
	}
}
