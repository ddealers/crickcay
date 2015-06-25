package gt.com.santillana.trazos.android.storage;

import gt.com.santillana.trazos.android.controller.StageConfigManager;
import gt.com.santillana.trazos.android.score.Score;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ScoresManager {

	public static boolean isStageUnlocked(Context context, String stageId) {
		int score=-1;
			
	    DBManager databaseManager = new DBManager(context);
	    SQLiteDatabase db = databaseManager.getReadableDatabase();
	    
	    Cursor cursor = db.query(DBManager.STAGE_TABLE_NAME,
	        new String[] {DBManager.STAGE_FSCORE}, 
	        DBManager.STAGE_FID + "=?", 
	        new String[] {stageId}, null, null, null);
	    if(cursor.moveToFirst()){
	      score = cursor.getInt(0);
	    }
	    cursor.close();
	    
	    db.close();
	    databaseManager.close();
	    
	    return (score != Score.LOCKED);
	}
	
	public static int getStageScore(Context context, String stageId) {
		int result = -1;
		
		DBManager databaseManager = new DBManager(context);
		SQLiteDatabase db = databaseManager.getReadableDatabase();
		Cursor cursor = db.query(
				DBManager.STAGE_TABLE_NAME, 
				new String[] {DBManager.STAGE_FSCORE}, 
				DBManager.STAGE_FID + "=?", 
				new String[] {stageId}, null, null, null);
		if(cursor.moveToFirst()) {
			int score = cursor.getInt(cursor.getColumnIndex(DBManager.STAGE_FSCORE));
			result = score;
		}
		cursor.close();
		db.close();
		databaseManager.close();
		
		return result;
	}
	
	public static void updateScore(Context context, String stageId, int score)
	{
		DBManager databaseManager = new DBManager(context);
		SQLiteDatabase db = databaseManager.getReadableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(DBManager.STAGE_FSCORE, String.valueOf(score));
		db.update(DBManager.STAGE_TABLE_NAME, cv, DBManager.STAGE_FID+"=?", new String[]{stageId});
		
		db.close();
		databaseManager.close();
	}
	
	public static void updatedScoreAndUnlockNextStage(Context context, String stageId, int newScore)
	{
		int currentScore = getStageScore(context, stageId);
		if(currentScore < newScore)
			updateScore(context, stageId, newScore);
		if(currentScore > Score.SCORE_ZERO || newScore > Score.SCORE_ZERO)
		{
			String nextStageID = StageConfigManager.getStageInfo(stageId).getNextStageId();
			if(nextStageID != null && getStageScore(context, nextStageID) == Score.LOCKED)
				updateScore(context, nextStageID, Score.SCORE_ZERO);
		}
	}
}
