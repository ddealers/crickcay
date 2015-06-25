package gt.com.santillana.trazos.android.storage;

import gt.com.santillana.trazos.android.config.AppConstants;
import gt.com.santillana.trazos.android.controller.LevelController;
import gt.com.santillana.trazos.android.score.Score;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "trazos";

	// LEVELS
	public static final String LEVEL_TABLE_NAME = "level";
	public static final String LEVEL_FID = "level_id";
	public static final String LEVEL_FNAME = "level_name";
	public static final String LEVEL_FNEXTLEVEL = "level_next_level";
	public static final String LEVEL_FISCOMPLETED = "level_iscompleted";
	private static final String LEVEL_TABLE_CREATE =
			"CREATE TABLE " + LEVEL_TABLE_NAME + " (" +
			LEVEL_FID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			LEVEL_FNAME + " TEXT, " +
			LEVEL_FISCOMPLETED + " INTEGER );";
    
	// Stages
	public static final String STAGE_TABLE_NAME = "stage";
	public static final String STAGE_FID = "stage_id";
	//public static final String STAGE_FIELD_NAME = "stage_name";
	public static final String STAGE_FLEVELID = "level_id";
	public static final String STAGE_FSCORE = "score";
    private static final String STAGE_TABLE_CREATE =
	        "CREATE TABLE " + STAGE_TABLE_NAME + " (" +
			STAGE_FID + " TEXT PRIMARY KEY, " +
			STAGE_FLEVELID + " INTEGER, " +
	        STAGE_FSCORE + " NUMERIC );";
	
	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(LEVEL_TABLE_CREATE);
		db.execSQL(STAGE_TABLE_CREATE);
		
		// Load initial data
		ContentValues cv;
		String currlvlID;
		
		for(int levelNumber = 1; levelNumber<=5; levelNumber++)
		{
			currlvlID = LevelController.lvlPrefix+"0"+levelNumber;
					
			cv = new ContentValues();
			cv.put(LEVEL_FNAME, currlvlID);
			cv.put(LEVEL_FISCOMPLETED, false);
			db.insert(LEVEL_TABLE_NAME, null, cv);
			
			for(int stageNumber = 1; stageNumber<10; stageNumber++)
			{
				cv = new ContentValues();
				cv.put(STAGE_FID, AppConstants.buildStageID(levelNumber, stageNumber));
				cv.put(STAGE_FLEVELID, levelNumber);
				cv.put(STAGE_FSCORE, Score.LOCKED);
				db.insert(STAGE_TABLE_NAME, null, cv);
			}
		}
		
		cv = new ContentValues();
		cv.put(DBManager.STAGE_FSCORE, String.valueOf(Score.SCORE_ZERO));
		db.update(DBManager.STAGE_TABLE_NAME, cv, DBManager.STAGE_FID+"=?", new String[]{AppConstants.LEVEL01_STAGE_01});
	//	db.close();
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public static void resetDB(Context context)
	{
		context.deleteDatabase(DATABASE_NAME);
	}
}
