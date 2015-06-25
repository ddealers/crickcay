package gt.com.santillana.trazos.android.controller;

import gt.com.santillana.trazos.android.storage.DBManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StageController {

	public static float getScore(Context context, String stageName){
		float score = -1;
		DBManager dbManager = new DBManager(context); 
		SQLiteDatabase db = dbManager.getWritableDatabase();
		
		String selectQuery = "SELECT SUM("+DBManager.STAGE_FSCORE+") FROM "+DBManager.STAGE_TABLE_NAME+" WHERE "+DBManager.STAGE_FLEVELID+" =?";
		Cursor cursor = db.rawQuery(selectQuery,  new String[] {stageName});
		if (cursor != null)
		{
			cursor.moveToFirst();
			score = cursor.getFloat(0);
		}
		cursor.close();
		db.close();
		dbManager.close();
		return score;
	}
	
	/*public static boolean isUnlocked(Context context, String stageID) {
		boolean result = false;
			
	    DBManager dbManager = new DBManager(context);
	    SQLiteDatabase db = dbManager.getReadableDatabase();
	    
	    Cursor cursor = db.query(DBManager.STAGE_TABLE_NAME, 
	        new String[] {DBManager.STAGE_FSCORE}, 
	        DBManager.STAGE_FID+ "=?", 
	        new String[] {stageID}, null, null, null);
	    if(cursor.moveToFirst()){
	      int score = cursor.getInt(cursor.getColumnIndex(DBManager.STAGE_FSCORE));
	      result = (score >= 0);
	    }
	    
	    cursor.close();
		db.close();
		dbManager.close();
	    return result;
	}*/
}
