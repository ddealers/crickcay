package gt.com.santillana.trazos.android.controller;

import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.android.config.AppConstants;
import gt.com.santillana.trazos.android.models.LevelProperties;
import gt.com.santillana.trazos.android.models.ScalableImage;
import android.content.Context;

public class LevelConfigManager {

	public final static LevelProperties getLevelInfo(int levelID, Context context)
	{
		LevelProperties levelp = new LevelProperties();
		int stages_per_level = 6;//for the time being all levels will have 9 stages
		switch(levelID)
		{
			case AppConstants.LEVEL_01:
				levelp.setCorrelative(AppConstants.LEVEL_01);
				levelp.setTittle(context.getString(R.string.l01_title));
				levelp.setLevelCoverDrawable(new ScalableImage(R.drawable.l01_portada, 397, 415));
				levelp.setNextLevelId(AppConstants.LEVEL_02);
				break;
			case AppConstants.LEVEL_02:
				levelp.setCorrelative(AppConstants.LEVEL_02);
				levelp.setTittle(context.getString(R.string.l02_title));
				levelp.setLevelCoverDrawable(new ScalableImage(R.drawable.l_2_portada, 488, 387));
				levelp.setNextLevelId(AppConstants.LEVEL_03);
				break;
			case AppConstants.LEVEL_03:
				levelp.setCorrelative(AppConstants.LEVEL_03);
				levelp.setTittle(context.getString(R.string.l03_title));
				levelp.setLevelCoverDrawable(new ScalableImage(R.drawable.l_3_portada, 459, 303));
				levelp.setNextLevelId(AppConstants.LEVEL_04);
				break;
			case AppConstants.LEVEL_04:
				levelp.setCorrelative(AppConstants.LEVEL_04);
				levelp.setTittle(context.getString(R.string.l04_title));
				levelp.setLevelCoverDrawable(new ScalableImage(R.drawable.l4_portada, 412, 316));
				levelp.setNextLevelId(AppConstants.LEVEL_05);
				break;
			case AppConstants.LEVEL_05:
				levelp.setCorrelative(AppConstants.LEVEL_05);
				levelp.setTittle(context.getString(R.string.l05_title));
				levelp.setLevelCoverDrawable(new ScalableImage(R.drawable.l5_portada, 326, 480));
				levelp.setNextLevelId(-1);
				break;
			case AppConstants.LEVEL_06:
				levelp.setCorrelative(AppConstants.LEVEL_06);
				levelp.setTittle(context.getString(R.string.l06_title));
				levelp.setLevelCoverDrawable(new ScalableImage(R.drawable.l01_portada, 397, 415));
				levelp.setNextLevelId(AppConstants.LEVEL_06);
				break;
			case AppConstants.LEVEL_07:
				levelp.setCorrelative(AppConstants.LEVEL_07);
				levelp.setTittle(context.getString(R.string.l07_title));
				levelp.setLevelCoverDrawable(new ScalableImage(R.drawable.l_2_portada, 397, 415));
				levelp.setNextLevelId(AppConstants.LEVEL_07);
				break;
			case AppConstants.LEVEL_08:
				levelp.setCorrelative(AppConstants.LEVEL_08);
				levelp.setTittle(context.getString(R.string.l08_title));
				levelp.setLevelCoverDrawable(new ScalableImage(R.drawable.l_3_portada, 397, 415));
				levelp.setNextLevelId(AppConstants.LEVEL_08);
				break;
//			case AppConstants.LEVEL_06:
//				levelp.setCorrelative(AppConstants.LEVEL_06);
//				//levelp.setTittle(context.getString(R.string.l06_title));
//				//levelp.setLevelCoverDrawable(new ScalableImage(R.drawable.l6_portada, 326, 480));
//				levelp.setNextLevelId(-1);
//				break;
		}
		
		levelp.setStagesApproved(LevelController.getApprovedStages(context, levelID));
		levelp.isCompleted(LevelController.isCompleted(context, levelID));
		levelp.setStagesAmount(stages_per_level);
		
		return levelp;
	}
}
