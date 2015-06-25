package gt.com.santillana.trazos.dragdrop;

import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.android.config.AppConstants;
import gt.com.santillana.trazos.android.controller.AppManager;
import gt.com.santillana.trazos.android.dialog.ScoreDialogConstants;
import gt.com.santillana.trazos.android.models.DragAndDropShape;
import gt.com.santillana.trazos.utils.DisplayUtils;
import android.animation.LayoutTransition;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.digitalgeko.mobile.android.accesories.DGMathUtils;

public class DragAndDropManager implements OnTouchListener {

	private SparseIntArray imagesMap = new SparseIntArray();
	private SparseIntArray soundsMap = new SparseIntArray();
	private SparseBooleanArray imagesSet = new SparseBooleanArray();

	final String LOG_TAG = "TOUCH!!!!";
	final double NO_SEPARATION = -1; 
	float x1, x2;
	float y1, y2;
	
	private boolean requirePinchForDragging = false;
	private double doPinchDistance;//the distance between pointers to consider a pinch
	private double cancelPinchDistance = 150;//the maximum separation between pointers to consider a pinch
	
	double originalSeparation;
	double currentSeparation;
	boolean checkDistances;
	PointF downPT; // Record Mouse Position When Pressed Down
	private double acceptedDropArea;
	private boolean allowDrag;
	
	public ImageView dropTarget;
	private Activity activity;
	private View currentView;
	
	public DragAndDropManager(Activity activity) {
		this.activity = activity;
		init();
    }

	public void init()
	{
		doPinchDistance = DisplayUtils.dpToPixels(120, activity);
		cancelPinchDistance = DisplayUtils.dpToPixels(300, activity);
		originalSeparation = NO_SEPARATION;
		checkDistances = true;
		downPT = new PointF();

		int pixels = DisplayUtils.dpToPixels(25, activity);
		acceptedDropArea = pixels * pixels;
		allowDrag = false;
	}
	
	int secondPointerId = -1;
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		int[] viewLocation = new int[2];
		view.getLocationOnScreen(viewLocation);

		Log.i("TOUCH ", "touching!! ");
		switch(event.getActionMasked())
		{
			case MotionEvent.ACTION_DOWN:
			{
				if(currentView == null)
					currentView = view;
				else if(currentView.getId() != view.getId())
					return false;//prevents drag and drop on multiple images.
				
				if(!requirePinchForDragging)
				{
					allowDrag = true;
					convertToDragable(activity, view.getId());
				}
				
				x1 = viewLocation[0] - event.getX();
				y1 = viewLocation[1] - event.getY();
				Log.i(LOG_TAG, "Coordenates set, TouchX = "+x1+" TouchY = "+y1);
				downPT.x = event.getX();
				downPT.y = event.getY();
				
				break;
			}
			case MotionEvent.ACTION_POINTER_DOWN:
			{
				if(requirePinchForDragging)
				{
					if(event.getPointerCount() == 2)
					{
						Log.i(LOG_TAG, "touch with second finger");
						secondPointerId = event.getPointerId(1);
		
						x2 = event.getX(1);
						y2 = event.getY(1);
		
						originalSeparation = DGMathUtils.distanceBetweenPoints(x1, y1, x2, y2);
						
						if(originalSeparation <= doPinchDistance)
						{
							allowDrag = true;
							convertToDragable(activity, view.getId());
						}
					}
				}
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				if(allowDrag)
				{
					Point screenSize = DisplayUtils.getScreanMeasure(activity);
					int sWidth = screenSize.y;
					int sHeight = screenSize.x;
					
					PointF mv = new PointF( event.getRawX(), event.getRawY());//the first finger is used as a reference
					RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
					
					//Allows moving images only insideteh screen bounds
					if(sWidth - event.getRawX() >= view.getMeasuredWidth()/2 && event.getRawX() > 0 + view.getMeasuredWidth()/2)
						lParams.leftMargin = (int)(event.getRawX()-downPT.x);
					if(sHeight - event.getRawY() >= view.getMeasuredHeight() && event.getRawY() > 0 + view.getMeasuredHeight()/2)
						lParams.topMargin = (int)(event.getRawY()-downPT.y);
					
					view.setLayoutParams(lParams);
				}
				else if(requirePinchForDragging)
				{
					if(event.getPointerCount() >= 2)//calculating pinch
					{
						/*xPos1 = event.getX(0);
						yPos1 = event.getY(0);
		
						xPos2 = event.getX(1);
						yPos2 = event.getY(1);
		
						currentSeparation = DGMathUtils.distanceBetweenPoints(xPos1, yPos1, xPos2, yPos2);
						if(currentSeparation <= originalSeparation || currentSeparation <= maxSeparation)
						{
							Log.i(LOG_TAG, "It's doing pinch!!");
							originalSeparation = currentSeparation;
							if(currentSeparation <= maxSeparation)
							{
								stopAnimations(view);*/
								allowDrag = true;
								/*convertToDragable(view);
							}
						}*/
					}
				}
				break;
			}
			case MotionEvent.ACTION_UP://first pointer up
			{
				ImageView dropped = (ImageView) view;
				ImageView dropTarget = (ImageView)activity.findViewById(imagesMap.get(dropped.getId()));
	
				int[] dropViewLocation = new int[2];
				dropped.getLocationOnScreen(dropViewLocation);
				
				if(inViewBounds(dropTarget, dropViewLocation[0] + dropped.getMeasuredWidth()/2, dropViewLocation[1]+ dropped.getMeasuredHeight()/2 ) || (sharedArea(dropTarget, dropped) >= acceptedDropArea) )
				//if(sharedArea(dropTarget, dropped) >= acceptedDropArea)
				{
					putImageOnTarget(dropped, dropTarget);
					
					if(levelCompleted())
					{
						if(AppManager.getInstance().getCurrentLevelId() == AppConstants.getLevelId(5))
						{
							activity.showDialog(ScoreDialogConstants.DIALOG_GAME_COMPLETED);
						}
							
						else
							activity.showDialog(ScoreDialogConstants.DIALOG_LEVEL_COMPLETED);
					}
				}
				else
				{
					Log.i(LOG_TAG, "Image is not in bounds");
					resetView(dropped);
					
				}
				allowDrag = false;
				currentView = null;
				break;
			}
			case MotionEvent.ACTION_POINTER_UP:
			{
				if(requirePinchForDragging && event.getPointerId(event.getActionIndex()) == secondPointerId)
				{
					allowDrag = false;
					Log.i(LOG_TAG, "Second finger up");
				}
				break;
			}
		}
		return true;
	}
	
	public void resetView(View view)
	{
		view.setVisibility(View.VISIBLE);
		view.setEnabled(true);
		view.clearAnimation();
		view.setOnTouchListener(this);
		((RelativeLayout)view.getParent()).removeView(view);
		ViewGroup scrollLayout = (ViewGroup)activity.findViewById(R.id.finalLvl_scrollLayout);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150,0);
		params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		params.width = 150;
		params.bottomMargin = 4;
		params.topMargin = 4;
		view.setLayoutParams(params);
		scrollLayout.addView(view, 0);
		imagesSet.delete(view.getId());
	}
	
	public static void convertToDragable(Activity activity, int viewId)
	{
		final int[] location = new int[2];
		final View view = activity.findViewById(viewId);
		view.getLocationOnScreen(location);

		LinearLayout parent = ((LinearLayout)view.getParent());

		parent.getLayoutTransition().setAnimator(LayoutTransition.APPEARING, null);
		parent.getLayoutTransition().setAnimator(LayoutTransition.DISAPPEARING, null);
		
		parent.removeView(view);
		
		RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(view.getLayoutParams());
		
		lParams.leftMargin = location[0];
		lParams.topMargin = location[1];
		lParams.width = 150;
		
		lParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
		view.setLayoutParams(lParams);
		
		((RelativeLayout)activity.findViewById(R.id.finalLvl_mainLayout)).addView(view);
	}
	
	public void putImageOnTarget(ImageView dropped, ImageView dropTarget)
	{
		dropped.setVisibility(View.INVISIBLE);
		dropped.setEnabled(false);
		dropTarget.setImageDrawable(dropped.getDrawable());
		imagesSet.put(dropped.getId(), true);

		if (AppManager.getInstance().isSoundOn(activity))
		{
			SoundManager.play(soundsMap.get(dropped.getId()));
		}
	}
	
	Rect outRect = new Rect();
	int[] location = new int[2];
	private boolean inViewBounds(View view, int x, int y){
		view.getDrawingRect(outRect);
		view.getLocationOnScreen(location);
		outRect.offset(location[0], location[1]);
		return outRect.contains(x, y);
	}
	
	public void registerShape(DragAndDropShape shape, int dropTargetId, Animation animation) {
		int imgId = shape.getColorImgContainerid();
		imagesMap.put(imgId, dropTargetId);
		soundsMap.put(imgId, shape.getSoundRawId());
	}
	
	private boolean levelCompleted()
	{
		return imagesMap.size() == imagesSet.size();
	}
	
	/**
	 * 
	 * @param dropTarget view on which others views will be dropped
	 * @param dropped view that was dropped
	 * @return	shared area in pixeles
	 */
	public float sharedArea(View dropTarget, View dropped)
	{
		int[] location1 = new int[2];
		int[] location2 = new int[2];
		dropTarget.getLocationOnScreen(location1);
		dropped.getLocationOnScreen(location2);
	
		float x1 = location1[0];
		float y1 = location1[1];
		float h1 = dropTarget.getHeight();
		float w1 = dropTarget.getWidth();
	
		float x2 = location2[0];
		float y2 = location2[1];
		float h2 = dropped.getHeight();
		float w2 = dropped.getWidth();
	
		float xNew;
		float yNew;
		float wNew = 0;
		float hNew = 0;
		float area;
	
		if(x2<x1 && x2+w2<x1+w1)//view2 está del lado izquierdo 
		{
			xNew = x1;
			wNew = x2+w2 - x1; 
			if(y2<y1 && y2+h2>y1)//view2 está en esquina superior izquierda
			{
				yNew = y1;
				hNew = y2+h2 - y1; 
			}
			else if(y2>y1 && y2 < y1+h1)//view2 está en esquina inferior izquierda
			{
				yNew = y2;
				hNew = y1+h1 - y2;
			}
		}
		else if(x2>x1 && x2 < x1+w1 && x2+w2>=x1+w1)//view2 está del lado derecho
		{
			xNew = x2;
			wNew = x1+w1 - x2;
			if(y1>y2 && y2+h2>y1)//view2 está en esquina superior derecha
			{
				yNew = y1;
				hNew = y2+h2 - y1;
			}
			else if(y2>y1 && y2<y1+h1)//view2 está en esquina inferior derecha
			{
				yNew = y2;
				hNew = y1+h1 - y2;
			}
		}
	
		area = wNew * hNew;
	
		return area;
	}

}
