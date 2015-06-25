package gt.com.santillana.trazos.android.controller;

import gt.com.santillana.trazos.android.config.AppConstants;
import gt.com.santillana.trazos.android.models.Level;
import gt.com.santillana.trazos.android.models.LevelProperties;
import gt.com.santillana.trazos.android.score.Score;
import gt.com.santillana.trazos.android.storage.DBManager;
import gt.com.santillana.trazos.android.storage.ScoresManager;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LevelController {
	
	public final static String lvlPrefix = "lvl"; 
	
	public static void addLevel(Context context, Level level)
	{
		DBManager dbManager = new DBManager(context); 
		SQLiteDatabase db = dbManager.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(DBManager.LEVEL_FNAME, "l01");
		db.insert(DBManager.LEVEL_TABLE_NAME, null, values);
		db.close();
	}
	
	public List<Level> getAllLevels(){
		return null;
	};
	
	public static boolean isCompleted(Context context, int levelId){
		boolean result = false;
		DBManager dbManager = new DBManager(context); 
		SQLiteDatabase db = dbManager.getWritableDatabase();
		String selectQuery = "SELECT "+DBManager.LEVEL_FISCOMPLETED+" FROM "+DBManager.LEVEL_TABLE_NAME+" WHERE "+DBManager.LEVEL_FID+" =?";
		Cursor cursor = db.rawQuery(selectQuery,  new String[] {String.valueOf(levelId)});
		if(cursor.moveToFirst())
		{
			result = cursor.getInt(0) > 0;
		}
		cursor.close();
		db.close();
		dbManager.close();
		return result;
	}
	
	public static void setCompletedLevel(Context context, int levelId)
	{
		DBManager databaseManager = new DBManager(context);
		SQLiteDatabase db = databaseManager.getReadableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(DBManager.LEVEL_FISCOMPLETED, 1);
		db.update(DBManager.LEVEL_TABLE_NAME, cv, DBManager.LEVEL_FID+"=?", new String[]{String.valueOf(levelId)});
		
		AppManager appManager = AppManager.getInstance();
		LevelProperties levelP = LevelConfigManager.getLevelInfo(appManager.getCurrentLevelId(), context);
		if(levelP.getNextLevelId() != -1)
		{
			String stageId = AppConstants.buildStageID(levelP.getNextLevelId(), 1);
			if(!ScoresManager.isStageUnlocked(context, stageId))
				ScoresManager.updateScore(context, stageId, Score.SCORE_ZERO);
		}
		
		db.close();
		databaseManager.close();
	}
	
	public static float getLevelScore(Context context, int levelID){
		float score = -1;
		DBManager dbManager = new DBManager(context); 
		SQLiteDatabase db = dbManager.getWritableDatabase();
		
		String selectQuery = "SELECT "+DBManager.LEVEL_FID+" FROM "+DBManager.LEVEL_TABLE_NAME+" WHERE "+DBManager.LEVEL_FID+" =?";
		Cursor cursor = db.rawQuery(selectQuery,  new String[] {String.valueOf(levelID)});
		cursor.moveToFirst();
		String levelId = cursor.getString(0);
		
		selectQuery = "SELECT SUM(score) FROM "+DBManager.STAGE_TABLE_NAME+" WHERE "+DBManager.STAGE_FLEVELID+" =?  GROUP BY "+DBManager.LEVEL_FID;
		cursor = db.rawQuery(selectQuery,  new String[] {levelId});
		if(cursor!=null)
		{
			cursor.moveToFirst();
			score = cursor.getFloat(0);
		}
		cursor.close();
		db.close();
		dbManager.close();
		return score;
	}
	
	public static int getApprovedStages(Context context, int levelID){
		int approved = 0;
		DBManager dbManager = new DBManager(context);
		SQLiteDatabase db = dbManager.getWritableDatabase();
		
		String selectQuery = "SELECT COUNT(*) FROM (SELECT "+DBManager.STAGE_FLEVELID+" FROM "+DBManager.STAGE_TABLE_NAME+" WHERE "+DBManager.STAGE_FLEVELID+"=? AND "+DBManager.STAGE_FSCORE+">?) GROUP BY "+DBManager.STAGE_FLEVELID;
		Cursor cursor = db.rawQuery(selectQuery,  new String[] {String.valueOf(levelID), String.valueOf(Score.SCORE_ZERO)});
		
		if(cursor.moveToFirst())
		{
			approved = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		dbManager.close();
		return approved;
	}
}
