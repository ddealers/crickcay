package gt.com.santillana.trazos.android;

import gt.com.santillana.trazos.android.config.AppConstants;
import gt.com.santillana.trazos.android.config.DrawingTraceActivity;
import gt.com.santillana.trazos.android.controller.AppManager;
import gt.com.santillana.trazos.android.controller.DragAndDropLevelManager;
import gt.com.santillana.trazos.android.controller.LevelConfigManager;
import gt.com.santillana.trazos.android.controller.StageConfigManager;
import gt.com.santillana.trazos.android.models.FinalStage;
import gt.com.santillana.trazos.android.models.StageInfo;
import gt.com.santillana.trazos.android.score.Score;
import gt.com.santillana.trazos.android.storage.ScoresManager;
import gt.com.santillana.trazos.utils.NavigationUtils;
import gt.com.santillana.trazos.utils.bitmaps.BitmapHandler;
import gt.com.santillana.trazos.utils.bitmaps.BitmapManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalgeko.mobile.android.ui.StageStatusView;

public class StageSelectionActivity extends Activity {
	
	private static final String TAG_LOG = "StageSelectionActivity"; 

	private AppManager appManager = AppManager.getInstance();
	private int currentLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManager mgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		mgr.getMemoryInfo(info);
		Log.w(TAG_LOG, "Create");
		Log.w(TAG_LOG + " - Memory Information", "AvialMem:" + info.availMem / (1024 * 1024) + " M");

		setContentView(R.layout.activity_stage_selection);
		
		mgr.getMemoryInfo(info);
		Log.w(TAG_LOG, "Create 2");
		Log.w(TAG_LOG + " - Memory Information", "AvialMem:" + info.availMem / (1024 * 1024) + " M");

		currentLevel = AppManager.getInstance().getCurrentLevelId();
		
		TextView levelTittle = (TextView)findViewById(R.id.levelSelect_lblTitle);
		levelTittle.setText( LevelConfigManager.getLevelInfo(currentLevel, this).getTittle());
//		if(levelTittle.getText().toString().length() > 15)
//			((ImageView)findViewById(R.id.stagescreen_ivBorder)).setImageResource(R.drawable.submenu2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.level_select, menu);
		return true;
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		for(int i=1; i<10; i++)
		{
			((StageStatusView)findViewById( getViewIdFromStageCorrelative(i)) ).releaseImagesMemory();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setStagesData();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.gc();
	}

	private void setStagesData() {
		
		boolean isFinalStageUnlocked = true;
		int score;
		StageInfo stageInf;
		String stageId;
		StageStatusView stageView;
		
		for(int i=1; i<=9; i++)
		{
			stageId = AppConstants.buildStageID(currentLevel, i); 
			stageInf = StageConfigManager.getStageInfo(stageId);
			score = ScoresManager.getStageScore(this, stageInf.getStage_id());
			
			stageView = (StageStatusView)findViewById( getViewIdFromStageCorrelative(i) );
			stageView.setScore( score );
			stageView.setImageResource(stageInf.getPreviewImage());
			if(!appManager.isInDeveloperMode() && (score == Score.LOCKED || score == Score.SCORE_ZERO))
				isFinalStageUnlocked = false;
		}
		//Log.v("Score", Score.LOCKED);
		//Log.v("Score", Score.SCORE_ZERO);
		if(isFinalStageUnlocked)
		{
			ImageView ivFinalStage = (ImageView)findViewById(R.id.stageSelection_finalStage);
			FinalStage finalStage = DragAndDropLevelManager.getPreviewImage(currentLevel);
			ivFinalStage.setImageResource( finalStage.getPreviewImgResourceId() );
			//BitmapHandler.loadBitmap(finalStage.getPreviewImgResourceId(), ivFinalStage, getResources());
			ivFinalStage.setEnabled(true);
		}
		else
		{
			findViewById(R.id.stageSelection_finalStage).setEnabled(false);
		}
	}
	
	public void onMenuButtonsClickListener(View v)
	{
		switch(v.getId())
		{
		case R.id.stageSelection_btnBack:
			NavigationUtils.goBack(this);
			break;
		case R.id.stageSelection_btnHome:
			NavigationUtils.goToHomeScreen(this);
			break;
		}
	}

	public void onStageClickListener(View view) {
		StageInfo stageInf = getStageInfoFromViewId(view.getId());
		appManager.setCurrentStageID(stageInf.getStage_id());
		if (appManager.isInDeveloperMode()) {
			NavigationUtils.goToStage(this, DrawingTraceActivity.class, stageInf.getStage_id());
		} else {
			NavigationUtils.goToStage(this, CanvasDrawingActivity.class, stageInf.getStage_id());
		}
	}
	
	public void onFinalLevelClickListener(View v)
	{
		NavigationUtils.goToFinalStage(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK) {
			System.gc();
			
			Intent returnIntent = new Intent();
			returnIntent.putExtra("result", 1);
			setResult(RESULT_OK, returnIntent);
			this.finish();
		}
	}
	
	public void goToStage(Context packageContext, Class<?> cls, String stageId)
	{
		StageInfo stageInf = StageConfigManager.getStageInfo(stageId);
		Intent intent = new Intent(packageContext, CanvasDrawingActivity.class);
		intent.putExtra(MyView.SCREEN_SOLUTION_ID, stageInf.getSolutionPath());
		intent.putExtra(MyView.SCREEN_DRAW_ID, stageInf.getExcerciseView());
		intent.putExtra(MyView.SCREEN_BACKGROUND_ID, stageInf.getPaintingView());
		packageContext.startActivity(intent);
	}
	
	/**
	 * @author Carlos Ortiz
	 * @param viewId
	 * @return
	 */
	private StageInfo getStageInfoFromViewId(int viewId)
	{
		StageInfo stageInf = null;
		Log.v("viewId", String.valueOf(viewId));
		switch (viewId) {
		case R.id.stageSelection_iv01:
			stageInf = StageConfigManager
					.getStageInfo(AppConstants.buildStageID(currentLevel, 1));
			break;
		case R.id.stageSelection_iv02:
			stageInf = StageConfigManager
					.getStageInfo(AppConstants.buildStageID(currentLevel, 2));
			break;
		case R.id.stageSelection_iv03:
			stageInf = StageConfigManager
					.getStageInfo(AppConstants.buildStageID(currentLevel, 3));
			break;
		case R.id.stageSelection_iv04:
			stageInf = StageConfigManager
					.getStageInfo(AppConstants.buildStageID(currentLevel, 4));
			break;
		case R.id.stageSelection_iv05:
			stageInf = StageConfigManager
					.getStageInfo(AppConstants.buildStageID(currentLevel, 5));
			break;
		case R.id.stageSelection_iv06:
			stageInf = StageConfigManager
					.getStageInfo(AppConstants.buildStageID(currentLevel, 6));
			break;
		case R.id.stageSelection_iv07:
			stageInf = StageConfigManager
					.getStageInfo(AppConstants.buildStageID(currentLevel, 7));
			break;
		case R.id.stageSelection_iv08:
			stageInf = StageConfigManager
					.getStageInfo(AppConstants.buildStageID(currentLevel, 8));
			break;
		case R.id.stageSelection_iv09:
			stageInf = StageConfigManager
					.getStageInfo(AppConstants.buildStageID(currentLevel, 9));
			break;
		default:
			break;
		}
		return stageInf;
	}
	
	private int getViewIdFromStageCorrelative(int correlative)
	{
		switch(correlative)
		{
		case 1: return(R.id.stageSelection_iv01);
		case 2: return(R.id.stageSelection_iv02);
		case 3: return(R.id.stageSelection_iv03);
		case 4: return(R.id.stageSelection_iv04);
		case 5: return(R.id.stageSelection_iv05);
		case 6: return(R.id.stageSelection_iv06);
		case 7: return(R.id.stageSelection_iv07);
		case 8: return(R.id.stageSelection_iv08);
		case 9: return(R.id.stageSelection_iv09);
		default: throw new Error("Unknown view");
		}
	}
}