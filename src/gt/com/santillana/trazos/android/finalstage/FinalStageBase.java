package gt.com.santillana.trazos.android.finalstage;

import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.android.controller.AppManager;
import gt.com.santillana.trazos.android.controller.DragAndDropLevelManager;
import gt.com.santillana.trazos.android.controller.LevelController;
import gt.com.santillana.trazos.android.dialog.DialogGameCompleted;
import gt.com.santillana.trazos.android.dialog.ScoreDialogConstants;
import gt.com.santillana.trazos.android.dialog.ScoreDialogFinalStage;
import gt.com.santillana.trazos.android.models.DragAndDropShape;
import gt.com.santillana.trazos.android.models.DropTarget;
import gt.com.santillana.trazos.android.models.FinalStageBackgroundImage;
import gt.com.santillana.trazos.dragdrop.DragAndDropManager;
import gt.com.santillana.trazos.dragdrop.SoundManager;
import gt.com.santillana.trazos.utils.DisplayUtils;
import gt.com.santillana.trazos.utils.MemoryManager;
import gt.com.santillana.trazos.utils.NavigationUtils;
import gt.com.santillana.trazos.utils.bitmaps.BitmapHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.digitalgeko.mobile.android.complements.ScreenShot;
import com.digitalgeko.mobile.android.components.LockableScrollView;

public class FinalStageBase extends Activity implements AnimatorListener {

	private static final String TAG_LOG = "FinalStageBase";
	
	private DragAndDropManager imgTouchListener;
	ImageView demoView = null;
	ImageView demoTargetView = null;
	int demoTargetViewDrawableId;
	boolean animationsComplete = false;
	private Handler mHandler;
	private boolean executedAnimation = false;

	// animations
	private View hand;
	private View handShadow;

	// animation states
	private final int STATE_SEARCH_IMAGE = 0;
	private final int STATE_GRAB_IMAGE = 1;
	private final int STATE_GO_TO_TARGET = 2;
	private final int STATE_DROP_IMAGE = 3;
	private int ANIMATION_CURRENT_STATE = STATE_SEARCH_IMAGE;

	private Map<Integer, Boolean> shapesPositions = new Hashtable<Integer, Boolean>();

	private Animation currentAnimation;

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	@Override
	public void onStop() {
		super.onStop();
		releaseImagesMemory();
	}

	private void releaseImagesMemory() {
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_background));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_whiteShape1));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_whiteShape2));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_whiteShape3));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_whiteShape4));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_whiteShape5));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_whiteShape6));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_whiteShape7));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_whiteShape8));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_whiteShape9));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_shape01));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_shape02));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_shape03));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_shape04));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_shape05));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_shape06));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_shape07));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_shape08));
		MemoryManager.unbindDrawables(findViewById(R.id.finalLvl_shape09));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new Handler();
		setContentView(R.layout.activity_final_level);

		hand = (View) findViewById(R.id.demoHand);
		handShadow = findViewById(R.id.demoHandShadow);

		SoundManager.SoundManagerInitializer(this);
		SoundManager.stop();

		setEnableDragAndDrop(false);

		((LockableScrollView) findViewById(R.id.finalLvl_scrollView)).setScrollingEnabled(false);
		Vector<DragAndDropShape> shapes = DragAndDropLevelManager.getLevelShapes(AppManager.getInstance().getCurrentLevelId());

		imgTouchListener = new DragAndDropManager(this);

		Random ramdom = new Random();
		ImageView colorImg;
		ImageView dropTargetImg;

		DragAndDropShape demoShape = null;

		int correlative;
		for (DragAndDropShape shape : shapes) {
			do {
				correlative = ramdom.nextInt(9) + 1;
			} while (shapesPositions.get(correlative) != null);
			shapesPositions.put(correlative, true);

			int dragViewId = getDragViewId(correlative);
			int dropTargetViewId = getDropTargetViewId(correlative);

			dropTargetImg = (ImageView) findViewById(dropTargetViewId);
			dropTargetImg.setImageResource(shape.getDropTarget().getDrawableId());

			colorImg = (ImageView) findViewById(dragViewId);
			// colorImg.setImageResource(shape.getColorImgSrcId());
			BitmapHandler.loadBitmap(shape.getColorImgSrcId(), colorImg, getResources());

			shape.getDropTarget().setImgViewId(dropTargetViewId);
			shape.setColorImgContainerid(dragViewId);

			if (shape.isOnTopLayer()) {
				// colorImg.bringToFront();//bringing the color image to the
				// front produces a bug.
				dropTargetImg.bringToFront();
			}
			if (correlative == 1) {
				demoShape = shape;
			}

			imgTouchListener.registerShape(shape, dropTargetViewId, null);
			colorImg.setOnTouchListener(imgTouchListener);
		}

		demoView = (ImageView) findViewById(R.id.finalLvl_shape01);
		demoTargetView = (ImageView) findViewById(demoShape.getDropTarget().getImgViewId());
		demoTargetViewDrawableId = demoShape.getDropTarget().getDrawableId();

		resizeWhiteImages(shapes);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		SoundManager.stop();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && !executedAnimation)
			setDragDemoAnimation(demoView, demoTargetView);
	}

	private void setEnableDragAndDrop(boolean isEnabled) {
		LinearLayout layout = (LinearLayout) findViewById(R.id.finalLvl_scrollLayout);
		View child;
		for (int i = 0; i < layout.getChildCount(); i++) {
			child = layout.getChildAt(i);
			child.setEnabled(isEnabled);
		}
	}

	private void setDragDemoAnimation(View dragView, View targetView) {
		List<Animator> animList = new ArrayList<Animator>();
		AnimatorSet animSetXY = new AnimatorSet();
		int handLocation[] = new int[2];
		int dragViewLocation[] = new int[2];
		int targetViewLocation[] = new int[2];
		hand.getLocationOnScreen(handLocation);
		dragView.getLocationOnScreen(dragViewLocation);
		targetView.getLocationOnScreen(targetViewLocation);

		int shadowOffset = 10;

		float imageCenterX = (dragViewLocation[0] + dragView.getMeasuredWidth() / 2);
		float imageCenterY = (dragViewLocation[1] + dragView.getMeasuredHeight() / 2);
		float targetCenterX = (targetViewLocation[0] + targetView.getMeasuredWidth() / 2);
		float targetCenterY = (targetViewLocation[1] + targetView.getMeasuredHeight() / 2);

		AnimatorSet setGoToImage = new AnimatorSet();
		setGoToImage.play(ObjectAnimator.ofFloat(hand, "x", imageCenterX + shadowOffset))
				.with(ObjectAnimator.ofFloat(hand, "y", imageCenterY - shadowOffset))
				.with(ObjectAnimator.ofFloat(handShadow, "x", imageCenterX))
				.with(ObjectAnimator.ofFloat(handShadow, "y", imageCenterY));
		setGoToImage.setDuration(2000);

		AnimatorSet setGrabimage = new AnimatorSet();
		setGrabimage.play(ObjectAnimator.ofFloat(hand, "x", imageCenterX)).with(ObjectAnimator.ofFloat(hand, "y", imageCenterY));
		setGrabimage.setDuration(1000);
		setGrabimage.addListener(this);

		AnimatorSet setGoToTarget = new AnimatorSet();
		setGoToTarget.play(ObjectAnimator.ofFloat(hand, "x", targetCenterX))
				.with(ObjectAnimator.ofFloat(hand, "y", targetCenterY))
				.with(ObjectAnimator.ofFloat(handShadow, "x", targetCenterX))
				.with(ObjectAnimator.ofFloat(handShadow, "y", targetCenterY));
		setGoToTarget.setDuration(2000);
		setGoToTarget.addListener(this);

		AnimatorSet setDropImage = new AnimatorSet();
		setDropImage.play(ObjectAnimator.ofFloat(hand, "x", targetCenterX + shadowOffset)).with(
				ObjectAnimator.ofFloat(hand, "y", targetCenterY - shadowOffset));
		setDropImage.setDuration(1000);
		setDropImage.addListener(this);
		ANIMATION_CURRENT_STATE = STATE_GRAB_IMAGE;

		animList.add(setGoToImage);
		animList.add(setGrabimage);
		animList.add(setGoToTarget);
		animList.add(setDropImage);

		animSetXY.playSequentially(animList);
		animSetXY.start();
	}

	private Runnable animationsTimeHandler = new Runnable() {
		public void run() {
			mHandler.removeCallbacks(animationsTimeHandler);
			imgTouchListener.resetView(demoView);
			demoTargetView.setImageResource(demoTargetViewDrawableId);
			setEnableDragAndDrop(true);
		}
	};

	protected Dialog onCreateDialog(int id) {
		LevelController.setCompletedLevel(this, AppManager.getInstance().getCurrentLevelId());
		Dialog scoreDialog;
		if (id == ScoreDialogConstants.DIALOG_LEVEL_COMPLETED)
			scoreDialog = new ScoreDialogFinalStage(this);
		else
			scoreDialog = new DialogGameCompleted(this);
		return scoreDialog;
	}

	public void returnTolevelSelection() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", 1);
		setResult(RESULT_OK, returnIntent);
		releaseImagesMemory();
		finish();
	}

	public void reset() {
		NavigationUtils.resetActivity(this);
	}

	public void takeScreenshot() {
		Date date = new Date();
		String name = "finalLevel" + AppManager.getInstance().getCurrentLevelId() + "_" + date.getTime();
		ScreenShot.saveScreenShot(name, getBaseContext(), findViewById(R.id.finalLvl_lytImagesContainer));
	}

	public static void redimImage(ImageView container, ImageView imgView, Point containerNormalSize, Point containerActualSize, 
			double mWidth, double mHeight, double mLeftMargin, double mTopMargin) {
		RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) imgView.getLayoutParams();
		lParams.width = DisplayUtils.getPercentage(containerNormalSize.x, containerActualSize.x, mWidth);
		lParams.height = DisplayUtils.getPercentage(containerNormalSize.y, containerActualSize.y, mHeight);
		imgView.setMaxWidth(lParams.width);
		imgView.setMaxHeight(lParams.height);
		imgView.setLayoutParams(lParams);
		
		// Translations of the bitmap
		int translationX = (container.getMeasuredWidth() - containerNormalSize.x) / 2;
		int translationY = (container.getMeasuredHeight() - containerNormalSize.y) / 2;
		imgView.setTranslationX(translationX + DisplayUtils.getPercentage(containerNormalSize.x, containerActualSize.x, mLeftMargin));
		imgView.setTranslationY(translationY + DisplayUtils.getPercentage(containerNormalSize.y, containerActualSize.y, mTopMargin));
		imgView.invalidate();
		// imgView.requestLayout();
	}

	/**
	 * @author itrjwyss
	 * 
	 *         M�todo para redimensionar el tama�o de las imagenes blancas
	 *         dentro del paisaje para que se adapten correctamente a cualquier
	 *         tama�o y resoluci�n de pantalla.
	 */
	int actualWidth, actualHeight;
	private boolean drawn = false;

	public void resizeWhiteImages(final Vector<DragAndDropShape> shapes) {

		FinalStageBackgroundImage backgroundImg = DragAndDropLevelManager.getLevelBackgroundDrawableId(AppManager.getInstance()
				.getCurrentLevelId());

		final int standardWidth = (int) backgroundImg.getWidth();
		final int standardHeight = (int) backgroundImg.getHeight();
		final ImageView container = (ImageView) findViewById(R.id.finalLvl_background);
		container.setImageResource(backgroundImg.getDrawableId());

		// BitmapHandler.loadBitmap(backgroundImg.getDrawableId(), container,
		// this.getResources());

		ViewTreeObserver vto = container.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (!drawn) {
//					if(actualWidth <= 0 && actualHeight <= 0) {
						int[] measures = measureBackgroundActualSize(container);
						actualWidth = measures[0];
						actualHeight = measures[1];
//					}

					Point standardSize = new Point(actualWidth, actualHeight);
					Point actualSize = new Point(standardWidth, standardHeight);

					for (DragAndDropShape shape : shapes) {
						DropTarget dropTarget = shape.getDropTarget();
						redimImage(container, (ImageView) findViewById(dropTarget.getImgViewId()), standardSize, actualSize,
								dropTarget.getWidth(), dropTarget.getHeight(), dropTarget.getX(), dropTarget.getY() );
					}

					drawn = true;
				}
				return true;
			}
		});

	}
	
	private int[] measureBackgroundActualSize(ImageView container) {
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        
        container.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = container.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);
        
        return new int[] {actW, actH};
	}

	public int getDragViewId(int correlative) {
		switch (correlative) {
		case 1:
			return R.id.finalLvl_shape01;
		case 2:
			return R.id.finalLvl_shape02;
		case 3:
			return R.id.finalLvl_shape03;
		case 4:
			return R.id.finalLvl_shape04;
		case 5:
			return R.id.finalLvl_shape05;
		case 6:
			return R.id.finalLvl_shape06;
		case 7:
			return R.id.finalLvl_shape07;
		case 8:
			return R.id.finalLvl_shape08;
		case 9:
			return R.id.finalLvl_shape09;
		default:
			return -1;
		}
	}

	public int getDropTargetViewId(int correlative) {
		switch (correlative) {
		case 1:
			return R.id.finalLvl_whiteShape1;
		case 2:
			return R.id.finalLvl_whiteShape2;
		case 3:
			return R.id.finalLvl_whiteShape3;
		case 4:
			return R.id.finalLvl_whiteShape4;
		case 5:
			return R.id.finalLvl_whiteShape5;
		case 6:
			return R.id.finalLvl_whiteShape6;
		case 7:
			return R.id.finalLvl_whiteShape7;
		case 8:
			return R.id.finalLvl_whiteShape8;
		case 9:
			return R.id.finalLvl_whiteShape9;
		default:
			return -1;
		}
	}

	public void setDemoViewX(int x) {
		RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) demoView.getLayoutParams();
		lParams.leftMargin = x;
		demoView.setLayoutParams(lParams);
	}

	public void setDemoViewY(int y) {
		RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) demoView.getLayoutParams();
		lParams.topMargin = y;
		demoView.setLayoutParams(lParams);
	}

	@Override
	public void onAnimationStart(Animator animation) {
		final View container = findViewById(R.id.finalLvl_background);
		Log.i(TAG_LOG, "Container width: " + container.getWidth());
		Log.i(TAG_LOG, "Container Height: " + container.getHeight());
		
		switch (ANIMATION_CURRENT_STATE) {
		case STATE_GO_TO_TARGET:
			int handLocation[] = new int[2];
			int dragViewLocation[] = new int[2];
			int targetViewLocation[] = new int[2];
			hand.getLocationOnScreen(handLocation);
			demoView.getLocationOnScreen(dragViewLocation);
			demoTargetView.getLocationOnScreen(targetViewLocation);

			float imageCenterX = (dragViewLocation[0] + demoView.getMeasuredWidth() / 2);
			float imageCenterY = (dragViewLocation[1] + demoView.getMeasuredHeight() / 2);
			float targetCenterX = (targetViewLocation[0] + demoTargetView.getMeasuredWidth() / 2);
			float targetCenterY = (targetViewLocation[1] + demoTargetView.getMeasuredHeight() / 2);

			RelativeLayout.LayoutParams lParamsDemoView = (RelativeLayout.LayoutParams) demoView.getLayoutParams();

			AnimatorSet setGoToTarget = new AnimatorSet();
			setGoToTarget.play(
					ObjectAnimator.ofInt(this, "demoViewX", lParamsDemoView.leftMargin, (int) (targetCenterX - imageCenterX)))
					.with(ObjectAnimator
							.ofInt(this, "demoViewY", lParamsDemoView.topMargin, (int) (targetCenterY - imageCenterY)));
			setGoToTarget.setDuration(2000);
			setGoToTarget.start();

			break;
		}

	}

	@Override
	public void onAnimationEnd(Animator animation) {

		int handLocation[] = new int[2];
		int dragViewLocation[] = new int[2];
		int targetViewLocation[] = new int[2];
		hand.getLocationOnScreen(handLocation);
		demoView.getLocationOnScreen(dragViewLocation);
		demoTargetView.getLocationOnScreen(targetViewLocation);

		switch (ANIMATION_CURRENT_STATE) {
		case STATE_GRAB_IMAGE:
			DragAndDropManager.convertToDragable(this, demoView.getId());
			handShadow.bringToFront();
			hand.bringToFront();

			ANIMATION_CURRENT_STATE = STATE_GO_TO_TARGET;
			break;

		case STATE_GO_TO_TARGET:
			ANIMATION_CURRENT_STATE = STATE_DROP_IMAGE;
			break;

		case STATE_DROP_IMAGE:
			handShadow.clearAnimation();
			handShadow.setVisibility(View.INVISIBLE);
			handShadow.postInvalidate();

			imgTouchListener.putImageOnTarget(demoView, demoTargetView);
			demoView.clearAnimation();
			demoView.setEnabled(true);

			hand.clearAnimation();
			hand.setVisibility(View.INVISIBLE);
			hand.postInvalidate();
			mHandler.removeCallbacks(animationsTimeHandler);
			mHandler.postDelayed(animationsTimeHandler, 1000);

			currentAnimation = null;
			break;
		}

	}

	@Override
	public void onAnimationCancel(Animator animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationRepeat(Animator animation) {
		// TODO Auto-generated method stub

	}

}
