package gt.com.santillana.trazos.android;

import gt.com.santillana.trazos.android.dialog.ScoreDialogConstants;
import gt.com.santillana.trazos.utils.Logger;
import gt.com.santillana.trazos.utils.bitmaps.BitmapManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
	
	public static final String SCREEN_SOLUTION_ID = "screen_solution_id";
	public static final String SCREEN_DRAW_ID = "screen_draw_id";
	public static final String SCREEN_BACKGROUND_ID = "screen_background_id";

	public static final float MINP = 0.25f;
	public static final float MAXP = 0.75f;
	private static final float TOUCH_TOLERANCE = 5;

	public static final int MODE_ANIMATION = 0;
	public static final int MODE_DRAW = 1;
	public static final int MODE_PAINT = 2;
	public static final int MODE_NO_DRAW = 3;
	
	public final BitmapShader[] blueBitmapShaders = new BitmapShader[] {
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_azul), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR),
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_azul2), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR) };
	public final BitmapShader[] blackBitmapShaders = new BitmapShader[] {
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_negro), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR),
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_negro2), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR) };
	public final BitmapShader[] redBitmapShaders = new BitmapShader[] {
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_rojo), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR),
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_rojo2), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR) };
	public final BitmapShader[] purpleBitmapShaders = new BitmapShader[] {
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_morada), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR),
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_morada2), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR) };
	public final BitmapShader[] greenBitmapShaders = new BitmapShader[] {
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_verde), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR),
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_verde2), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR) };
	public final BitmapShader[] yellowBitmapShaders = new BitmapShader[] {
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_amarilla), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR),
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_amarilla2), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR) };
	public final BitmapShader[] orangeBitmapShaders = new BitmapShader[] {
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_naraja), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR),
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_naraja2), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR) };
	public final BitmapShader[] brownBitmapShaders = new BitmapShader[] {
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_cafe), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR),
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_cafe2), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR) };
	public final BitmapShader[] pinkBitmapShaders = new BitmapShader[] {
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_rosa), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR),
			new BitmapShader(BitmapFactory.decodeResource(getResources(),
					R.drawable.cera_rosa2), Shader.TileMode.MIRROR,
					Shader.TileMode.MIRROR) };

	private float score = 0;
	
	/**
	 * Determina el modo actual del juego: trazo o pintar.
	 */
	private int gameMode;
	private boolean hasChangeGameMode;
	private int topMargin = 1;
	private int leftMargin = 1;
	private int bitmapWidth = 1;
	private int bitmapHeight = 1;
	private float mX;
	private float mY;
	/**
	 * Imagen de el trazo
	 */
	private Bitmap modelLayerBitmap;//imagen del trazo
	/**
	 * Imagen de fondo.
	 */
	private Bitmap backgroundLayerBitmap;
	/**
	 * Canvas en donde se dibujan los trazos.
	 */
	private Canvas trazoCanvas;
	
	private Bitmap animateBitmap; //bitmap que contine la imagen de la mano
	private Bitmap animationLayerBitmap; // bitmap en donde se va dibujando la mano para que parezca que se mueve
	private Canvas animationLayerCanvas; //canvas usado para pintar la mano
	private Bitmap paintLayerBitmap;// the crayon to paint
	private Canvas paintLayerCanvas;// el canvas usado para que los crayones pinten en un bitmap vacio
	private List<Path> modelPaths;
	private Path currentModelPath;
	private List<Path> guidePaths;
	private int currentGuidePath;
	private Paint modelLayerPaint;
	private PathValidator pathValidator;
	private Paint mPaint;
	
	private Activity activity;
	private Resources res;

	// Load new 
	private int screenSolutionId;
	private int screenDrawId;
	private int screenBgDrawableId;
	
	public void cleanAllBitmaps(){
		
		//Stop drawing
		gameMode = MODE_NO_DRAW;
		
		ActivityManager mgr = (ActivityManager)getContext().getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		mgr.getMemoryInfo(info);
		Log.w("Activity", "MyView");
		Log.w("Memory Information", "AvialMem:" + info.availMem/(1024*1024) + " M");
		
		if(modelLayerBitmap != null) 
			modelLayerBitmap.recycle();
		
		if(backgroundLayerBitmap != null)
			backgroundLayerBitmap.recycle();
		
		if(animateBitmap != null)
			animateBitmap.recycle();
		
		if(animationLayerBitmap != null)
			animationLayerBitmap.recycle();
		
		if(paintLayerBitmap != null)
			paintLayerBitmap.recycle();
		
		System.gc();
		
		mgr = (ActivityManager)getContext().getSystemService(Context.ACTIVITY_SERVICE);
		info = new ActivityManager.MemoryInfo();
		mgr.getMemoryInfo(info);
		Log.w("Memory Information", "AvialMem:" + info.availMem/(1024*1024) + " M");
	}
	
	// Game change listener
	private OnGameModeChangeListener onGameModeChangeListener;  

	public MyView(Context c) {
		super(c);
		
		// Set game configuration
		gameMode = MODE_ANIMATION;
		
		// Load first path to draw
		modelPaths = new ArrayList<Path>(); 
		modelLayerPaint = new Paint(Paint.DITHER_FLAG);
		
		setPencilBrush();

		setDrawingCacheEnabled(false);
	}
	
	public float getScore(){
		return score;
	}

	public Paint getmPaint() {
		return mPaint;
	}

	public void setmPaint(Paint mPaint) {
		this.mPaint = mPaint;
	}

	public BitmapShader[] getCurrentBitmapShaders() {
		return currentBitmapShaders;
	}

	public void setCurrentBitmapShaders(BitmapShader[] currentBitmapShaders) {
		this.currentBitmapShaders = currentBitmapShaders;
		changeEraserState(false);
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
	
	public void setScreenDrawId(int screenDrawId) {
		this.screenDrawId = screenDrawId;
	}

	public void setScreenBackgroundId(int screenBackgroundId) {
		this.screenBgDrawableId = screenBackgroundId;
	}

	public void enterPaintMode() {
		gameMode = MODE_PAINT;
		setCrayonBrush();
		paintPath.reset();
		if(onGameModeChangeListener != null)
			onGameModeChangeListener.onGameModeChanged(gameMode);
	}

	
	public OnGameModeChangeListener getOnGameModeChangeListener() {
		return onGameModeChangeListener;
	}

	public void setOnGameModeChangeListener(
			OnGameModeChangeListener onGameModeChangeListener) {
		this.onGameModeChangeListener = onGameModeChangeListener;
	}
	
	public void changeEraserState() {
		changeEraserState(!isErasing);
	}

	public void changeEraserState(boolean state) {
		isErasing = state;
		if (isErasing) {
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		} else {
			mPaint.setXfermode(null);
		}
	}
	
	public void resetCurrentStage()
	{
		if(modelLayerBitmap != null) {
			modelLayerBitmap.recycle();
		}
		//modelLayerBitmap = BitmapManager.decodeSampledBitmapFromResource(res, screenDrawId, bitmapWidth, bitmapHeight, true);
		
		modelLayerBitmap = BitmapManager.decodeSampledBitmapFromResource(res, screenDrawId, bitmapWidth, bitmapHeight, true);
		
		setupCanvas();
		setupGuidePath();
		postInvalidate();
		initAnimation();
		
		System.out.println("La imagen");
		System.out.println(bitmapWidth);
	}
	
	/**
	 * Loads all the images. Should be used when images change, e.g. Going to a new level.
	 */
	public void reload()
	{
		gameMode = MODE_DRAW;
		setPencilBrush();
		clearScreen();
		loadImages();
		setupCanvas();
		setupGuidePath();
		paintPath.reset();
		postInvalidate();
		
		initAnimation();
	}
	
	public void clearScreen()
	{
		Paint clearPaint = new Paint();
		clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		paintLayerCanvas.drawRect(0,0, bitmapWidth, bitmapHeight, clearPaint);
		postInvalidate();
	}
	
	private void loadImages()
	{
		if(modelLayerBitmap != null)
			modelLayerBitmap.recycle();
		if(modelLayerBitmap != null)
			modelLayerBitmap.recycle();
//		if(paintLayerBitmap != null)
//			paintLayerBitmap.recycle();
		
		modelLayerBitmap = BitmapManager.decodeSampledBitmapFromResource(res, screenDrawId, bitmapWidth, bitmapHeight, true);
		backgroundLayerBitmap = BitmapManager.decodeSampledBitmapFromResource(res, screenBgDrawableId, bitmapWidth, bitmapHeight, true);
		System.out.println("La imagen");
		System.out.println(bitmapWidth);


		
		Log.i("Imagen", "End by touch");
		
		if(paintLayerBitmap != null) {
			paintLayerBitmap.eraseColor(Color.TRANSPARENT);
		} else {
			paintLayerBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
		}
		if(animationLayerBitmap != null) {
			animationLayerBitmap.eraseColor(Color.TRANSPARENT);
		} 
		
		System.gc();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		Logger.AvailMemory(getContext());
		
		try{
			Logger.AvailMemory(getContext());
			res = getResources();
	
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(res, screenDrawId, options);
			
			float bmpHeight = options.outHeight;
			float bmpWidth = options.outWidth;
			
			// Set bitmaps sizes
			bitmapHeight = h;
			bitmapWidth = (int) ((h / bmpHeight) * bmpWidth);
			
			leftMargin = w - bitmapWidth;
			tempTop = topMargin = (h - bitmapHeight) / 2;
	
			loadImages();
			
			BitmapFactory.decodeResource(res, R.drawable.hand, options);
			
			int animateWidth = (int) (bitmapWidth * .05);
			int animateHeight = options.outHeight / options.outWidth * animateWidth;
			this.animateBitmap = BitmapManager.decodeSampledBitmapFromResource(res, R.drawable.hand, animateWidth, animateHeight, true);
			animationLayerBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
			
			setupCanvas();
			initAnimation();
			System.gc();
			
			Logger.AvailMemory(getContext());
		}catch(Exception e){
			e.printStackTrace();
			
			Logger.AvailMemory(getContext());
		}
		//destroy();
	}
	
	public void destroy()
	{
		animationLayerBitmap.recycle();
		animateBitmap.recycle();
		modelLayerBitmap.recycle();
		backgroundLayerBitmap.recycle();
		paintLayerBitmap.recycle();
		System.gc();
	}
	
	public void setupCanvas(){
		Logger.AvailMemory(getContext());
		
//		if(trazoCanvas == null)
		// Create always a new canvas
		trazoCanvas = new Canvas(modelLayerBitmap);
		if(paintLayerCanvas == null)
			paintLayerCanvas = new Canvas(paintLayerBitmap);
		if(animationLayerCanvas == null)
			animationLayerCanvas = new Canvas(animationLayerBitmap);
		Logger.AvailMemory(getContext());

		// Animation
		// Validators
		
		setupGuidePath();
		// Global vars
		if(pos == null)
			pos = new float[2];
		if(tan == null)
			tan = new float[2];
	}
	
	private void setupGuidePath(){
		modelPaths.clear();
		currentModelPath = new Path();
		modelPaths.add(currentModelPath);
		measure = new PathMeasure(currentModelPath, false);
		
		pathValidator = new PathValidator(mPaint, bitmapWidth, bitmapHeight);
		pathValidator.loadGuidePathId(getContext(), getScreenSolutionId());
		guidePaths = pathValidator.getGuidePath();
	}
	
	/**
	 * Starts the animation
	 */
	public void initAnimation() {
		// Set screen to start animation
		gameMode = MODE_ANIMATION;
		
		// Start animation thread
		AnimationRunnable animationRunnable = new AnimationRunnable();
		Thread animationThread = new Thread(animationRunnable);
		animationThread.setPriority(Thread.MAX_PRIORITY);
		animationThread.start();	
	}
	
	private int tempTop;

	private Path paintPath = new Path();
	private float distancePaintPath = 0;
	private PathMeasure measure;
	private boolean isErasing = false;

	private BitmapShader[] currentBitmapShaders = blueBitmapShaders;
	private Random random = new Random();
	private boolean setPaint = true;

	private float[] pos, tan;
	private float speed, distance;

	@Override
	protected void onDraw(Canvas canvas) {
		
		// mover el canvas al centro.
		canvas.save();
		//topMargin = (canvas.getHeight() - bitmapHeight) / 2;
		//topMargin = tempTop;
		
		topMargin = 0;
		
		leftMargin=0;
		
		int coso=canvas.getWidth();
		
		canvas.translate(leftMargin, topMargin);
		System.out.println(leftMargin);
		
		// de aquí en adelande se dibuja en el canvas.

		// se dan cuenta, se pone el "x" y "y" como 0, porque el translate() ya
		// movio todo el canvas por nosotros...

		if (gameMode == MODE_NO_DRAW) {
			return;
		}
		
		if (gameMode == MODE_ANIMATION) {

			modelLayerPaint.setColor(0xffffffff);
			canvas.drawRect(0, 0, bitmapWidth, bitmapHeight, modelLayerPaint);
			canvas.drawBitmap(modelLayerBitmap, 0, 0, modelLayerPaint);
			
			animationLayerCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//			animationLayerCanvas.drawPath(guidePaths.get(currentGuidePath), mPaint);
			animationLayerCanvas.drawBitmap(animateBitmap, pos[0], pos[1],
					modelLayerPaint);
			canvas.drawBitmap(animationLayerBitmap, 0, 0, modelLayerPaint);

		} else if (gameMode == MODE_DRAW) {

			modelLayerPaint.setColor(0xffffffff);
			canvas.drawRect(0, 0, bitmapWidth, bitmapHeight, modelLayerPaint);
			// mBitmapPaint.setColor(0xffaaaaaa);
			mPaint.setStrokeWidth(5);
			synchronized (paintPath) {
				trazoCanvas.drawPath(paintPath, mPaint);
				distancePaintPath = measure.getLength();
			}
			
			distancePaintPath = 0;
			canvas.drawBitmap(modelLayerBitmap, 0, 0, modelLayerPaint);

		} else if (gameMode == MODE_PAINT) {

			mPaint.setShader(currentBitmapShaders[random
					.nextInt(currentBitmapShaders.length)]);

			synchronized (paintPath) {
				paintLayerCanvas.drawPath(paintPath, mPaint);
				distancePaintPath = measure.getLength();
				// Log.i("Draw region: ", "OnDraw - Asi de profesional");
			}

			canvas.drawBitmap(backgroundLayerBitmap, 0, 0, modelLayerPaint);
			canvas.drawBitmap(paintLayerBitmap, 0, 0, modelLayerPaint);
			canvas.drawBitmap(modelLayerBitmap, 0, 0, modelLayerPaint);
		}

		// restaurar el estado anterior del canvas
		canvas.restore();

		// Set canvas in the validator
		if (pathValidator.getCanvas() == null) {
			pathValidator.setCanvas(canvas);
		}
	}
	
	private void setPencilBrush() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xff444444);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(12);
	}
	
	private void setCrayonBrush()
	{
		mPaint.setStrokeWidth(20);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setPathEffect(null);
		mPaint.setMaskFilter(null);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);

		setPaint = false;
	}

	private void touch_start(float x, float y) {
//		distancePaintPath = 0;
		currentModelPath.reset();
		currentModelPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);

		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			currentModelPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);

			mX = x;
			mY = y;

			synchronized (paintPath) {
				paintPath.reset();
				measure.setPath(currentModelPath, false);
				measure.getSegment(distancePaintPath, measure.getLength(),
						paintPath, true);
			}
		}
	}

	private void touch_up() {
		touch_move(mX, mY);

		if (gameMode == MODE_DRAW) {
			// commit the path to our offscreen
			//modelLayerCanvas.drawPath(modelPath, mPaint);
			// validate the user path
			if(modelPaths.size() < guidePaths.size()) {
				currentModelPath = new Path();
				modelPaths.add(currentModelPath);
				return;
			}
			score = pathValidator.calcScore(modelPaths);
			if (getActivity() != null)
				//NavigationUtils.goToStage(getActivity(), CanvasDrawingActivity.class, AppConstants.LEVEL01_STAGE_03);
				getActivity().showDialog(ScoreDialogConstants.DIALOG_GAME_FINISH);
		} else if (gameMode == MODE_PAINT) {
			// commit the path to our offscreen
			// bCanvas.drawPath(mPath, mPaint);
		}

		// kill this so we don't double draw
		currentModelPath.reset();
		paintPath.reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX() - leftMargin;
		float y = event.getY() - topMargin;

		if (gameMode == MODE_ANIMATION) {
			if(event.getAction() == MotionEvent.ACTION_UP) {
				Log.i("Animation", "End by touch");
				touch_start(x, y);
				gameMode = MODE_DRAW;
				invalidate();
			}
			return true;
		}
		
		if(hasChangeGameMode) {
			event.setAction(MotionEvent.ACTION_DOWN);
			hasChangeGameMode = false;
		}

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
				// expandDirtyRect(historicalX, historicalY);
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
	
	public class AnimationRunnable implements Runnable {
		@Override
		public void run() {
			Log.i("Animation", "Start");
			
			// Wait
			try {
				Thread.sleep(000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			final DirtyRect invalidateRect = new DirtyRect();
			PathMeasure guideMeasure;
			for(int i = 0; i < guidePaths.size() && gameMode == MODE_ANIMATION; i++) {
				
				guideMeasure = new PathMeasure(guidePaths.get(i), false);

				// Here, we're dividing the whole length of the path by 30.
				speed = guideMeasure.getLength() / 100;
				distance = 0;

				while(gameMode == MODE_ANIMATION && distance < guideMeasure.getLength()) {
					// Save current point
					invalidateRect.resetDirtyRect(pos[0] + leftMargin, pos[1] + topMargin);
					invalidateRect.expandDirtyRect(pos[0] + animateBitmap.getWidth() + 5 + leftMargin, 
							pos[1] + animateBitmap.getHeight() + 5 + topMargin);
					
					// Load new point
					guideMeasure.getPosTan(distance, pos, tan);
					distance += speed; // Traversal
					
					// Save new point
					invalidateRect.expandDirtyRect(pos[0] + leftMargin, pos[1] + topMargin);
					invalidateRect.expandDirtyRect(pos[0] + animateBitmap.getWidth() + 5 + leftMargin, 
							pos[1] + animateBitmap.getHeight() + 5 + topMargin);
					
					// Make move
//					Log.i("Animation", "Move to (" + pos[0] + "," + pos[1] + ")");
					activity.runOnUiThread(new Runnable() {
						public void run() {
							invalidate(invalidateRect.getRect());
						}
					});
					
					// Wait
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			Log.i("Animation", "End");

			if (gameMode == MODE_ANIMATION) {
				activity.runOnUiThread(new Runnable() {
					public void run() {
						gameMode = MODE_DRAW;
						hasChangeGameMode = true;
//						MotionEvent event = MotionEvent.obtain(0, 0, 
//								MotionEvent.ACTION_DOWN, mX, mY,  
//								0, 0, 0, 0, 0, 0, 0);
//						onTouchEvent(event);
						invalidate();
					}
				});
			}
		}
	}
	
	public interface OnGameModeChangeListener {
		
		public void onGameModeChanged(int gameMode);
	}
}
