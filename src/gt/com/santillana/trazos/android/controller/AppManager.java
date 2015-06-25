package gt.com.santillana.trazos.android.controller;

import gt.com.santillana.trazos.android.config.AppConstants;
import android.content.Context;
import android.content.SharedPreferences;

public class AppManager {

	private static AppManager instance = null;
	
	private int currentLevelId;
	private String currentStageID;
		
	public AppManager()
	{
		currentLevelId=AppConstants.LEVEL_01;
		currentStageID = null;
	}
	
	public static AppManager getInstance()
	{
		if(instance==null)
			instance = new AppManager();
		return instance;
	}
	
	public void setCurrentLevelId(int levelId) {
		this.currentLevelId = levelId;
	}
	
	public int getCurrentLevelId() {
		return currentLevelId;
	}

	public String getCurrentStageID() {
		return currentStageID;
	}

	public void setCurrentStageID(String currentStageID) {
		this.currentStageID = currentStageID;
	}
	
	public boolean isInDeveloperMode(){
		return true;//cambiar este valor para alternar entre mode de desarrollo.
	}
	
	public boolean isSoundOn(Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(AppConstants.PREFERENCES_APP, Context.MODE_PRIVATE);
		return prefs.getBoolean(AppConstants.PREFERENCES_SOUND, true);
	}
	
	public void setSoundState(boolean state, Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(AppConstants.PREFERENCES_APP, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(AppConstants.PREFERENCES_SOUND, state);
		editor.commit();
	}
}
