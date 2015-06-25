package gt.com.santillana.trazos.android;

import gt.com.santillana.trazos.android.ColorPickerDialog.OnColorChangedListener;
import gt.com.santillana.trazos.android.MyView.OnGameModeChangeListener;
import gt.com.santillana.trazos.android.controller.AppManager;
import gt.com.santillana.trazos.android.controller.StageConfigManager;
import gt.com.santillana.trazos.android.dialog.ScoreDialog;
import gt.com.santillana.trazos.android.dialog.ScoreDialogConstants;
import gt.com.santillana.trazos.android.models.StageInfo;
import gt.com.santillana.trazos.android.score.Score;
import gt.com.santillana.trazos.android.storage.ScoresManager;
import gt.com.santillana.trazos.utils.MemoryManager;
import gt.com.santillana.trazos.utils.NavigationUtils;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.digitalgeko.mobile.android.complements.ScreenShot;

/**
 * 
 */
public class CanvasDrawingActivity extends Activity implements
		OnColorChangedListener, OnGameModeChangeListener {

	private static final String TAG_LOG = "CanvasDrawingActivity";
	
	private FrameLayout frame;
	private Paint mPaint;
	private MyView viewDraw;
	private int selectedCrayon = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupDrawingPath();

		findViewById(R.id.layoutCrayons).setVisibility(View.INVISIBLE);
		findViewById(R.id.drawing_btnSave).setVisibility(View.INVISIBLE);
		findViewById(R.id.drawing_btnNext).setVisibility(View.INVISIBLE);
		findViewById(R.id.drawing_btnSave).setVisibility(View.INVISIBLE);
		findViewById(R.id.drawing_btnClean).setVisibility(View.INVISIBLE);
	}
	
	/**
	 * @author Carlos Ortiz
	 * Loads the next stage based on the info from the current stage. If the nextStageId of the current
	 * stage is null then the app will return to the stage selection screen, otherwise the data of the new
	 * stage is set in AppManager.
	 * @see AppManager
	 */
	public void loadNextStage() {
		System.gc();
		String currStageId = AppManager.getInstance().getCurrentStageID();
		StageInfo currStageInf = StageConfigManager.getStageInfo(currStageId);
		String nextStageId = currStageInf.getNextStageId();
		
		if(nextStageId != null) {
			AppManager.getInstance().setCurrentStageID(nextStageId);
			StageInfo stageInf = StageConfigManager.getStageInfo( nextStageId );
			findViewById(R.id.layoutCrayons).setVisibility(View.INVISIBLE);
			
			viewDraw.setScreenSolutionId(stageInf.getSolutionPath());
			viewDraw.setScreenDrawId(stageInf.getExcerciseView());
			viewDraw.setScreenBackgroundId(stageInf.getPaintingView());
			
			viewDraw.reload();
		} else {
			Log.i("FinalStageCalled", "Final Stage: " + this);
			NavigationUtils.goToFinalStage(this);
			this.finish();
		}
	}
	
	public void reset() {
		System.gc();
		//setupDrawingPath();
		viewDraw.resetCurrentStage();
		System.gc();
		findViewById(R.id.drawing_btnSave).setVisibility(View.INVISIBLE);
		findViewById(R.id.layoutCrayons).setVisibility(View.INVISIBLE);
		findViewById(R.id.drawing_btnSave).setVisibility(View.INVISIBLE);
		findViewById(R.id.drawing_btnNext).setVisibility(View.INVISIBLE);
		findViewById(R.id.drawing_btnSave).setVisibility(View.INVISIBLE);
		findViewById(R.id.drawing_btnClean).setVisibility(View.INVISIBLE);
	}
	
	private void setupDrawingPath() {
		if(viewDraw != null)
			viewDraw.cleanAllBitmaps();
		
		viewDraw = new MyView(this);
		viewDraw.setActivity(this);
		viewDraw.setOnGameModeChangeListener(this);
		
		StageInfo stageInf = StageConfigManager.getStageInfo( AppManager.getInstance().getCurrentStageID() );
		viewDraw.setScreenSolutionId(stageInf.getSolutionPath());
		viewDraw.setScreenDrawId(stageInf.getExcerciseView());
		viewDraw.setScreenBackgroundId(stageInf.getPaintingView());
			
		View layout = LayoutInflater.from(this).inflate(R.layout.activity_canvas_drawing, null);
		frame = (FrameLayout) layout.findViewById(R.id.canvas_frame);

		LayoutParams params = new LayoutParams();
		params.gravity = Gravity.CENTER;
		viewDraw.setLayoutParams(params);

		setContentView(layout);
		frame.addView(viewDraw);

		// Paint object holds info about style and color to draw bitmap
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xff444444);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(12);
		viewDraw.setmPaint(mPaint);
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case ScoreDialogConstants.DIALOG_GAME_FINISH:
			ScoreDialog scoreDialog = new ScoreDialog(this);
			return scoreDialog;
		}
		return null;
	}

	List<SoftReference<ScoreDialog>> dialogs = new ArrayList<SoftReference<ScoreDialog>>();
	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) 
	{
//		super.onPrepareDialog(id, dialog, args);
		if(id == ScoreDialogConstants.DIALOG_GAME_FINISH) {
			ScoreDialog scoreDialog = (ScoreDialog) dialog;
			
			Log.i(TAG_LOG, "Dialogs: " + dialogs.size());
			int count = 0;
			for(int i = 0; i<dialogs.size(); i++) {
				if(dialogs.get(i) != null) {
					count++;
				}
			}
			Log.i(TAG_LOG, "Dialogs active: " + count);
			dialogs.add(new SoftReference<ScoreDialog>(scoreDialog));
			
			
			int score = Score.getScoreFromPoints(viewDraw.getScore());
			ScoresManager.updatedScoreAndUnlockNextStage(viewDraw.getActivity(), AppManager.getInstance().getCurrentStageID(), score);
			scoreDialog.setScore(this, score);
		}
	}
	
	public void onButtonClickListener(View v)
	{
		switch(v.getId())
		{
		case R.id.drawing_btnNext:
			this.loadNextStage();
			break;
		case R.id.drawing_btnBack:
			NavigationUtils.goBack(this);
			break;
		case R.id.drawing_btnClean:
			viewDraw.clearScreen();
			break;
		case R.id.drawing_btnSave:
			Date date = new Date();
			String name = getResources().getResourceEntryName(
					this.viewDraw.getScreenSolutionId())
					+ "_" + date.getTime();
			ScreenShot.saveScreenShot(name, getBaseContext(), this.viewDraw);
			break;
		}
	}

	public void enterPaintMode() {
		LinearLayout crayonsLayout = (LinearLayout)findViewById(R.id.layoutCrayons);
		crayonsLayout.setVisibility(View.VISIBLE);
		
		findViewById(R.id.drawing_btnNext).setVisibility(View.VISIBLE);
		findViewById(R.id.drawing_btnSave).setVisibility(View.VISIBLE);
		findViewById(R.id.drawing_btnClean).setVisibility(View.VISIBLE);
		
		viewDraw.enterPaintMode();
		crayonClickListener(findViewById(R.id.button_palette_color_green));
		viewDraw.postInvalidate();
	}

	public void onEraserClick(View view) {
		this.viewDraw.changeEraserState();
	}

	public void crayonClickListener(View view) {

		if(selectedCrayon != -1)
			changeCrayonLeftMargin(selectedCrayon, 0);
		
		selectedCrayon = view.getId();
		changeCrayonLeftMargin(selectedCrayon, 20);
		
		switch (selectedCrayon) {
		case R.id.button_palette_color_black:
			colorChanged(this.viewDraw.blackBitmapShaders);
			break;
		case R.id.button_palette_color_blue:
			colorChanged(this.viewDraw.blueBitmapShaders);
			break;
		case R.id.button_palette_color_yellow:
			colorChanged(this.viewDraw.yellowBitmapShaders);
			break;
		case R.id.button_palette_color_orange:
			colorChanged(this.viewDraw.orangeBitmapShaders);
			break;
		case R.id.button_palette_color_green:
			colorChanged(this.viewDraw.greenBitmapShaders);
			break;
		case R.id.button_palette_color_purple:
			colorChanged(this.viewDraw.purpleBitmapShaders);
			break;
		case R.id.button_palette_color_red:
			colorChanged(this.viewDraw.redBitmapShaders);
			break;
		case R.id.button_palette_color_brown:
			colorChanged(this.viewDraw.brownBitmapShaders);
			break;
		case R.id.button_palette_color_pink:
			colorChanged(this.viewDraw.pinkBitmapShaders);
			break;
		case R.id.imgEraser:
			this.viewDraw.changeEraserState();
			break;
		}
	}
	
	private void changeCrayonLeftMargin(int crayonId, int margin)
	{
		ImageButton crayon = (ImageButton)findViewById(selectedCrayon);
		LinearLayout.LayoutParams layoutParams;
		layoutParams = (LinearLayout.LayoutParams)crayon.getLayoutParams();
		layoutParams.setMargins(margin, 0, 0, 0);
		crayon.setLayoutParams(layoutParams);
	}
	
	public void colorChanged(BitmapShader[] shaders) {
		viewDraw.setCurrentBitmapShaders(shaders);
	}

	@Override
	public void onPause(){
		super.onPause();
		releaseImagesMemory();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();	
		frame.removeAllViews();
		
		releaseImagesMemory();
		mPaint = null;
		viewDraw.cleanAllBitmaps();
		viewDraw.setActivity(null);
		viewDraw = null;
		System.gc();
	}

	private void releaseImagesMemory()
	{
		//viewDraw.cleanAllBitmaps();
		MemoryManager.unbindDrawables( findViewById(R.id.button_palette_color_black) );
		MemoryManager.unbindDrawables( findViewById(R.id.button_palette_color_blue) );
		MemoryManager.unbindDrawables( findViewById(R.id.button_palette_color_green) );
		MemoryManager.unbindDrawables( findViewById(R.id.button_palette_color_orange) );
		MemoryManager.unbindDrawables( findViewById(R.id.button_palette_color_purple) );
		MemoryManager.unbindDrawables( findViewById(R.id.button_palette_color_red) );
		MemoryManager.unbindDrawables( findViewById(R.id.button_palette_color_yellow) );
		MemoryManager.unbindDrawables( findViewById(R.id.imgEraser) );
		MemoryManager.unbindDrawables( findViewById(R.id.drawing_btnBack) );
		MemoryManager.unbindDrawables( findViewById(R.id.drawing_btnNext) );
		MemoryManager.unbindDrawables( findViewById(R.id.drawing_btnClean) );
		
		System.gc();
	}
	
	@Override
	public void colorChanged(int color) {
		viewDraw.getmPaint().setColor(color);
	}

	@Override
	public void onGameModeChanged(int gameMode) {
		/*switch (gameMode) {
		case MyView.MODE_PAINT:
			View view = findViewById(R.id.imgEraser);
			view.setVisibility(View.VISIBLE);
			break;
		}*/
	}
	
	
}
