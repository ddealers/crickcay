package gt.com.santillana.trazos.utils;

import gt.com.santillana.trazos.android.CanvasDrawingActivity;
import gt.com.santillana.trazos.android.CreditsActivity;
import gt.com.santillana.trazos.android.LevelSelectionActivity;
import gt.com.santillana.trazos.android.MenuActivity;
import gt.com.santillana.trazos.android.MyView;
import gt.com.santillana.trazos.android.StageSelectionActivity;
import gt.com.santillana.trazos.android.config.AppConstants;
import gt.com.santillana.trazos.android.controller.AppManager;
import gt.com.santillana.trazos.android.controller.StageConfigManager;
import gt.com.santillana.trazos.android.finalstage.FinalStageBase;
import gt.com.santillana.trazos.android.finalstage.FinalStageOne;
import gt.com.santillana.trazos.android.finalstage.FinalStageSeven;
import gt.com.santillana.trazos.android.models.StageInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NavigationUtils {

	public static void goToStage(Context packageContext, Class<?> cls, String stageId)
	{
		System.gc();
		StageInfo stageInf = StageConfigManager.getStageInfo(stageId);
		Intent intent = new Intent(packageContext, cls);//it is not set to CanvasDrawingActivity 'cause for configuration other activity is used.
		intent.putExtra(MyView.SCREEN_SOLUTION_ID, stageInf.getSolutionPath());
		intent.putExtra(MyView.SCREEN_DRAW_ID, stageInf.getExcerciseView());
		intent.putExtra(MyView.SCREEN_BACKGROUND_ID, stageInf.getPaintingView());
		packageContext.startActivity(intent);
	}
	
	public static void goToNextStage(Context packageContext, Activity activity)
	{
		System.gc();
		StageInfo stageInf = StageConfigManager.getStageInfo( AppManager.getInstance().getCurrentStageID());
		
		Intent intent = new Intent(packageContext, CanvasDrawingActivity.class);
		intent.putExtra(MyView.SCREEN_SOLUTION_ID, stageInf.getSolutionPath());
		intent.putExtra(MyView.SCREEN_DRAW_ID, stageInf.getExcerciseView());
		intent.putExtra(MyView.SCREEN_BACKGROUND_ID, stageInf.getPaintingView());
		activity.finish();
		packageContext.startActivity(intent);
	}
	
	public static void goToHomeScreen(Activity activity)
	{
		System.gc();
		Intent intent = new Intent(activity, MenuActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
		activity.startActivity(intent);
		activity.finish();
	}
	
	public static void goToStageSelection(Activity currActivity)
	{
		System.gc();
		Intent intent = new Intent(currActivity, StageSelectionActivity.class);
		currActivity.startActivityForResult(intent, 1);
	}
	
	public static void goBack(Activity activity)
	{
		System.gc();
		activity.finish();
	}
	
	public static void resetActivity(Activity activity)
	{
		System.gc();
		Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
	}
	
	public static void goToFinalStage(Activity activity)
	{
		int levelId = AppManager.getInstance().getCurrentLevelId();
		Intent intent;
		System.gc();
		
		System.out.println("LEVEL ID "+levelId);
		
		switch (levelId) {
		case AppConstants.LEVEL_01:
			intent = new Intent(activity, FinalStageOne.class);
			activity.startActivityForResult(intent, 1);
			break;
		case AppConstants.LEVEL_02:
		case AppConstants.LEVEL_03:
		case AppConstants.LEVEL_04:
		case AppConstants.LEVEL_05:
			intent = new Intent(activity, FinalStageSeven.class);
			activity.startActivityForResult(intent, 1);
			break;
		default:
			intent = null;
			break;
		}
	}
	
	public static void goToLevelSelection(Context packageContext)
	{
		System.gc();
		Intent intent = new Intent(packageContext, LevelSelectionActivity.class);
		packageContext.startActivity(intent);
	}
	
	public static void goToAppConfiguration(Context packageContext)
	{
		System.gc();
		Intent intent = new Intent(packageContext, CreditsActivity.class);
		packageContext.startActivity(intent);
	}
	
}
