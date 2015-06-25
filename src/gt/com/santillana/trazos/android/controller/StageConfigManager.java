package gt.com.santillana.trazos.android.controller;

import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.android.config.AppConstants;
import gt.com.santillana.trazos.android.models.StageInfo;

public class StageConfigManager {
	
	public static StageInfo getStageInfo(String stageID)
	{
		if(stageID == null) {
			stageID = AppConstants.LEVEL01_STAGE_01;
		}
		
		StageInfo stageP = new StageInfo();
		stageP.setStage_id(stageID);
		//---------------------------
		//-         LEVEL 1
		//--------------------------
		if(stageID.compareTo(AppConstants.LEVEL01_STAGE_01) == 0)
		{
			stageP.setExcerciseView(R.drawable.l01s01);
			stageP.setPaintingView(R.drawable.l01s01_bg);
			stageP.setPreviewImage(R.drawable.l01s01_pv);
			stageP.setSolutionPath(R.raw.l01s01);
			stageP.setNextStageId(AppConstants.LEVEL01_STAGE_02);
			stageP.setUnlocked(true);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL01_STAGE_02) == 0)
		{
			stageP.setExcerciseView(R.drawable.l01s02);
			stageP.setPaintingView(R.drawable.l01s02_bg);
			stageP.setPreviewImage(R.drawable.l01s02_pv);
			stageP.setSolutionPath(R.raw.l01s02);
			stageP.setNextStageId(AppConstants.LEVEL01_STAGE_03);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL01_STAGE_03) == 0)
		{
			stageP.setExcerciseView(R.drawable.l01s03);
			stageP.setPaintingView(R.drawable.l01s03_bg);
			stageP.setPreviewImage(R.drawable.l01s03_pv);
			stageP.setSolutionPath(R.raw.l01s03);
			stageP.setNextStageId(AppConstants.LEVEL01_STAGE_04);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL01_STAGE_04) == 0)
		{
			stageP.setExcerciseView(R.drawable.l01s04);
			stageP.setPaintingView(R.drawable.l01s04_bg);
			stageP.setPreviewImage(R.drawable.l01s04_pv);
			stageP.setSolutionPath(R.raw.l01s04);
			stageP.setNextStageId(AppConstants.LEVEL01_STAGE_05);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL01_STAGE_05) == 0)
		{
			stageP.setExcerciseView(R.drawable.l01s05);
			stageP.setPaintingView(R.drawable.l01s05_bg);
			stageP.setPreviewImage(R.drawable.l01s05_pv);
			stageP.setSolutionPath(R.raw.l01s05);
			stageP.setNextStageId(AppConstants.LEVEL01_STAGE_06);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL01_STAGE_06) == 0)
		{
			stageP.setExcerciseView(R.drawable.l01s06);
			stageP.setPaintingView(R.drawable.l01s06_bg);
			stageP.setPreviewImage(R.drawable.l01s06_pv);
			stageP.setSolutionPath(R.raw.l01s06);
			//stageP.setNextStageId(AppConstants.LEVEL01_STAGE_07);
			stageP.setNextStageId(null);
		}
		
		/*if(stageID.compareTo(AppConstants.LEVEL01_STAGE_07) == 0)
		{
			stageP.setExcerciseView(R.drawable.l01s07);
			stageP.setPaintingView(R.drawable.l01s07_bg);
			stageP.setPreviewImage(R.drawable.l01s07_pv);
			stageP.setSolutionPath(R.raw.l01s07);
			stageP.setNextStageId(AppConstants.LEVEL01_STAGE_08);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL01_STAGE_08) == 0)
		{
			stageP.setExcerciseView(R.drawable.l01s08);
			stageP.setPaintingView(R.drawable.l01s08_bg);
			stageP.setPreviewImage(R.drawable.l01s08_pv);
			stageP.setSolutionPath(R.raw.l01s08);
			stageP.setNextStageId(AppConstants.LEVEL01_STAGE_09);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL01_STAGE_09) == 0)
		{
			stageP.setExcerciseView(R.drawable.l01s09);
			stageP.setPaintingView(R.drawable.l01s09_bg);
			stageP.setPreviewImage(R.drawable.l01s09_pv);
			stageP.setSolutionPath(R.raw.l01s09);
			stageP.setNextStageId(null);
		}*/
		
		
		//---------------------------
		//-         LEVEL 2 
		//--------------------------
		if(stageID.compareTo(AppConstants.LEVEL02_STAGE_01) == 0)
		{
			stageP.setExcerciseView(R.drawable.l_2__1);
			stageP.setPaintingView(R.drawable.l_2__1_color);
			stageP.setPreviewImage(R.drawable.l_2__1_thumbs);
			stageP.setSolutionPath(R.raw.level2_21);
			stageP.setNextStageId(AppConstants.LEVEL02_STAGE_02);
			stageP.setUnlocked(true);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL02_STAGE_02) == 0)
		{
			stageP.setExcerciseView(R.drawable.l_2__2);
			stageP.setPaintingView(R.drawable.l_2__2_color);
			stageP.setPreviewImage(R.drawable.l_2__2_thumbs);
			stageP.setSolutionPath(R.raw.level2_22);
			stageP.setNextStageId(AppConstants.LEVEL02_STAGE_03);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL02_STAGE_03) == 0)
		{
			stageP.setExcerciseView(R.drawable.l_2__3);
			stageP.setPaintingView(R.drawable.l_2__3_color);
			stageP.setPreviewImage(R.drawable.l_2__3_thumbs);
			stageP.setSolutionPath(R.raw.level2_23);
			stageP.setNextStageId(AppConstants.LEVEL02_STAGE_04);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL02_STAGE_04) == 0)
		{
			stageP.setExcerciseView(R.drawable.l_2__4);
			stageP.setPaintingView(R.drawable.l_2__4_color);
			stageP.setPreviewImage(R.drawable.l_2__4_final);
			stageP.setSolutionPath(R.raw.level2_24);
			stageP.setNextStageId(AppConstants.LEVEL02_STAGE_05);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL02_STAGE_05) == 0)
		{
			stageP.setExcerciseView(R.drawable.l_2__5);
			stageP.setPaintingView(R.drawable.l_2__5_color);
			stageP.setPreviewImage(R.drawable.l_2__5_thumbs);
			stageP.setSolutionPath(R.raw.level2_25);
			stageP.setNextStageId(AppConstants.LEVEL02_STAGE_06);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL02_STAGE_06) == 0)
		{
			stageP.setExcerciseView(R.drawable.l_2__6);
			stageP.setPaintingView(R.drawable.l_2__6_color);
			stageP.setPreviewImage(R.drawable.l_2__6_final);
			stageP.setSolutionPath(R.raw.level2_26);
			//stageP.setNextStageId(AppConstants.LEVEL02_STAGE_07);
			stageP.setNextStageId(null);
		}
		
		/*if(stageID.compareTo(AppConstants.LEVEL02_STAGE_07) == 0)
		{
			stageP.setExcerciseView(R.drawable.l_2__7);
			stageP.setPaintingView(R.drawable.l_2__7_color);
			stageP.setPreviewImage(R.drawable.l_2__7_thumbs);
			stageP.setSolutionPath(R.raw.level2_27);
			stageP.setNextStageId(AppConstants.LEVEL02_STAGE_08);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL02_STAGE_08) == 0)
		{
			stageP.setExcerciseView(R.drawable.l_2__8);
			stageP.setPaintingView(R.drawable.l_2__8_color);
			stageP.setPreviewImage(R.drawable.l_2__8_thumbs);
			stageP.setSolutionPath(R.raw.level2_28);
			stageP.setNextStageId(AppConstants.LEVEL02_STAGE_09);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL02_STAGE_09) == 0)
		{
			stageP.setExcerciseView(R.drawable.l_2__9);
			stageP.setPaintingView(R.drawable.l_2__9_color);
			stageP.setPreviewImage(R.drawable.l_2__9_thumbs);
			stageP.setSolutionPath(R.raw.level2_29);
			stageP.setNextStageId(null);
		}
		*/
		
		//---------------------------
		//-         LEVEL 3
		//--------------------------
		if(stageID.compareTo(AppConstants.LEVEL03_STAGE_01) == 0)
		{
			stageP.setExcerciseView(R.drawable.l3_1);
			stageP.setPaintingView(R.drawable.l3_1_color);
			stageP.setPreviewImage(R.drawable.l3_1_thumbs);
			stageP.setSolutionPath(R.raw.level3_31);
			stageP.setNextStageId(AppConstants.LEVEL03_STAGE_02);
			stageP.setUnlocked(true);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL03_STAGE_02) == 0)
		{
			stageP.setExcerciseView(R.drawable.l3_2);
			stageP.setPaintingView(R.drawable.l3_2_color);
			stageP.setPreviewImage(R.drawable.l3_2_thumbs);
			stageP.setSolutionPath(R.raw.level3_32);
			stageP.setNextStageId(AppConstants.LEVEL03_STAGE_03);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL03_STAGE_03) == 0)
		{
			stageP.setExcerciseView(R.drawable.l3_3);
			stageP.setPaintingView(R.drawable.l3_3_color);
			stageP.setPreviewImage(R.drawable.l3_3_thumbs);
			stageP.setSolutionPath(R.raw.level3_33);
			stageP.setNextStageId(AppConstants.LEVEL03_STAGE_04);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL03_STAGE_04) == 0)
		{
			stageP.setExcerciseView(R.drawable.l3_4);
			stageP.setPaintingView(R.drawable.l3_4_color);
			stageP.setPreviewImage(R.drawable.l3_4_thumbs);
			stageP.setSolutionPath(R.raw.level3_34);
			stageP.setNextStageId(AppConstants.LEVEL03_STAGE_05);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL03_STAGE_05) == 0)
		{
			stageP.setExcerciseView(R.drawable.l3_5);
			stageP.setPaintingView(R.drawable.l3_5_color);
			stageP.setPreviewImage(R.drawable.l3_5_thumbs);
			stageP.setSolutionPath(R.raw.level3_35);
			stageP.setNextStageId(AppConstants.LEVEL03_STAGE_06);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL03_STAGE_06) == 0)
		{
			stageP.setExcerciseView(R.drawable.l3_6);
			stageP.setPaintingView(R.drawable.l3_6_color);
			stageP.setPreviewImage(R.drawable.l3_6_thumbs);
			stageP.setSolutionPath(R.raw.level3_36);
			//stageP.setNextStageId(AppConstants.LEVEL03_STAGE_07);
			stageP.setNextStageId(null);
		}
		
		/*if(stageID.compareTo(AppConstants.LEVEL03_STAGE_07) == 0)
		{
			stageP.setExcerciseView(R.drawable.l3_7);
			stageP.setPaintingView(R.drawable.l3_7_color);
			stageP.setPreviewImage(R.drawable.l3_7_thumbs);
			stageP.setSolutionPath(R.raw.level3_37);
			stageP.setNextStageId(AppConstants.LEVEL03_STAGE_08);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL03_STAGE_08) == 0)
		{
			stageP.setExcerciseView(R.drawable.l3_8);
			stageP.setPaintingView(R.drawable.l3_8_color);
			stageP.setPreviewImage(R.drawable.l3_8_thumbs);
			stageP.setSolutionPath(R.raw.level3_38);
			stageP.setNextStageId(AppConstants.LEVEL03_STAGE_09);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL03_STAGE_09) == 0)
		{
			stageP.setExcerciseView(R.drawable.l3_9);
			stageP.setPaintingView(R.drawable.l3_9_color);
			stageP.setPreviewImage(R.drawable.l3_9_thumbs);
			stageP.setSolutionPath(R.raw.level3_39);
			stageP.setNextStageId(null);
		}*/
		
		//---------------------------
		//-         LEVEL 4
		//--------------------------
		if(stageID.compareTo(AppConstants.LEVEL04_STAGE_01) == 0)
		{
			stageP.setExcerciseView(R.drawable.l4_1);
			stageP.setPaintingView(R.drawable.l4_1_color);
			stageP.setPreviewImage(R.drawable.l4_1_thumbs);
			stageP.setSolutionPath(R.raw.level4_41);
			stageP.setNextStageId(AppConstants.LEVEL04_STAGE_02);
			stageP.setUnlocked(true);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL04_STAGE_02) == 0)
		{
			stageP.setExcerciseView(R.drawable.l4_2);
			stageP.setPaintingView(R.drawable.l4_2_color);
			stageP.setPreviewImage(R.drawable.l4_2_thumbs);
			stageP.setSolutionPath(R.raw.level4_42);
			stageP.setNextStageId(AppConstants.LEVEL04_STAGE_03);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL04_STAGE_03) == 0)
		{
			stageP.setExcerciseView(R.drawable.l4_3);
			stageP.setPaintingView(R.drawable.l4_3_color);
			stageP.setPreviewImage(R.drawable.l4_3_thumbs);
			stageP.setSolutionPath(R.raw.level4_43);
			stageP.setNextStageId(AppConstants.LEVEL04_STAGE_04);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL04_STAGE_04) == 0)
		{
			stageP.setExcerciseView(R.drawable.l4_4);
			stageP.setPaintingView(R.drawable.l4_4_color);
			stageP.setPreviewImage(R.drawable.l4_4_thumbs);
			stageP.setSolutionPath(R.raw.level4_44);
			stageP.setNextStageId(AppConstants.LEVEL04_STAGE_05);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL04_STAGE_05) == 0)
		{
			stageP.setExcerciseView(R.drawable.l4_5);
			stageP.setPaintingView(R.drawable.l4_5_color);
			stageP.setPreviewImage(R.drawable.l4_5_thumbs);
			stageP.setSolutionPath(R.raw.level4_45);
			stageP.setNextStageId(AppConstants.LEVEL04_STAGE_06);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL04_STAGE_06) == 0)
		{
			stageP.setExcerciseView(R.drawable.l4_6);
			stageP.setPaintingView(R.drawable.l4_6_color);
			stageP.setPreviewImage(R.drawable.l4_6_thumbs);
			stageP.setSolutionPath(R.raw.level4_46);
			//stageP.setNextStageId(AppConstants.LEVEL04_STAGE_07);
			stageP.setNextStageId(null);
		}
		
		/*if(stageID.compareTo(AppConstants.LEVEL04_STAGE_07) == 0)
		{
			stageP.setExcerciseView(R.drawable.l4_7);
			stageP.setPaintingView(R.drawable.l4_7_color);
			stageP.setPreviewImage(R.drawable.l4_7_thumbs);
			stageP.setSolutionPath(R.raw.level4_47);
			stageP.setNextStageId(AppConstants.LEVEL04_STAGE_08);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL04_STAGE_08) == 0)
		{
			stageP.setExcerciseView(R.drawable.l4_8);
			stageP.setPaintingView(R.drawable.l4_8_color);
			stageP.setPreviewImage(R.drawable.l4_8_thumbs);
			stageP.setSolutionPath(R.raw.level4_48);
			stageP.setNextStageId(AppConstants.LEVEL04_STAGE_09);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL04_STAGE_09) == 0)
		{
			stageP.setExcerciseView(R.drawable.l4_9);
			stageP.setPaintingView(R.drawable.l4_9_color);
			stageP.setPreviewImage(R.drawable.l4_9_thumbs);
			stageP.setSolutionPath(R.raw.level4_49);
			stageP.setNextStageId(null);
		}*/
		
		//---------------------------
		//-         LEVEL 5
		//--------------------------
		if(stageID.compareTo(AppConstants.LEVEL05_STAGE_01) == 0)
		{
			stageP.setExcerciseView(R.drawable.l5_1);
			stageP.setPaintingView(R.drawable.l5_1_color);
			stageP.setPreviewImage(R.drawable.l5_1_thumbs);
			stageP.setSolutionPath(R.raw.level5_51);
			stageP.setNextStageId(AppConstants.LEVEL05_STAGE_02);
			stageP.setUnlocked(true);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL05_STAGE_02) == 0)
		{
			stageP.setExcerciseView(R.drawable.l5_2);
			stageP.setPaintingView(R.drawable.l5_2_color);
			stageP.setPreviewImage(R.drawable.l5_2_thumbs);
			stageP.setSolutionPath(R.raw.level5_52);
			stageP.setNextStageId(AppConstants.LEVEL05_STAGE_03);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL05_STAGE_03) == 0)
		{
			stageP.setExcerciseView(R.drawable.l5_3);
			stageP.setPaintingView(R.drawable.l5_3_color);
			stageP.setPreviewImage(R.drawable.l5_3_thumbs);
			stageP.setSolutionPath(R.raw.level5_53);
			stageP.setNextStageId(AppConstants.LEVEL05_STAGE_04);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL05_STAGE_04) == 0)
		{
			stageP.setExcerciseView(R.drawable.l5_4);
			stageP.setPaintingView(R.drawable.l5_4_color);
			stageP.setPreviewImage(R.drawable.l5_4_thumbs);
			stageP.setSolutionPath(R.raw.level5_54);
			stageP.setNextStageId(AppConstants.LEVEL05_STAGE_05);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL05_STAGE_05) == 0)
		{
			stageP.setExcerciseView(R.drawable.l5_5);
			stageP.setPaintingView(R.drawable.l5_5_color);
			stageP.setPreviewImage(R.drawable.l5_5_thumbs);
			stageP.setSolutionPath(R.raw.level5_55);
			stageP.setNextStageId(AppConstants.LEVEL05_STAGE_06);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL05_STAGE_06) == 0)
		{
			stageP.setExcerciseView(R.drawable.l5_6);
			stageP.setPaintingView(R.drawable.l5_6_color);
			stageP.setPreviewImage(R.drawable.l5_6_thumbs);
			stageP.setSolutionPath(R.raw.level5_56);
			//stageP.setNextStageId(AppConstants.LEVEL05_STAGE_07);
			stageP.setNextStageId(null);
		}
		
		/*if(stageID.compareTo(AppConstants.LEVEL05_STAGE_07) == 0)
		{
			stageP.setExcerciseView(R.drawable.l5_7);
			stageP.setPaintingView(R.drawable.l5_7_color);
			stageP.setPreviewImage(R.drawable.l5_7_thumbs);
			stageP.setSolutionPath(R.raw.level5_57);
			stageP.setNextStageId(AppConstants.LEVEL05_STAGE_08);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL05_STAGE_08) == 0)
		{
			stageP.setExcerciseView(R.drawable.l5_8);
			stageP.setPaintingView(R.drawable.l5_8_color);
			stageP.setPreviewImage(R.drawable.l5_8_thumbs);
			stageP.setSolutionPath(R.raw.level5_58);
			stageP.setNextStageId(AppConstants.LEVEL05_STAGE_09);
		}
		
		if(stageID.compareTo(AppConstants.LEVEL05_STAGE_09) == 0)
		{
			stageP.setExcerciseView(R.drawable.l5_9);
			stageP.setPaintingView(R.drawable.l5_9_color);
			stageP.setPreviewImage(R.drawable.l5_9_thumbs);
			stageP.setSolutionPath(R.raw.level5_59);
			stageP.setNextStageId(null);
		}
		*/
		
		
	/*	//---------------------------
				//-         LEVEL 6
				//--------------------------
				if(stageID.compareTo(AppConstants.LEVEL06_STAGE_01) == 0)
				{
					stageP.setExcerciseView(R.drawable.l6_1);
					stageP.setPaintingView(R.drawable.l6_1_color);
					stageP.setPreviewImage(R.drawable.l6_1_thumbs);
					stageP.setSolutionPath(R.raw.level6_51);
					stageP.setNextStageId(AppConstants.LEVEL05_STAGE_02);
					stageP.setUnlocked(true);
				}
				
				if(stageID.compareTo(AppConstants.LEVEL05_STAGE_02) == 0)
				{
					stageP.setExcerciseView(R.drawable.l5_2);
					stageP.setPaintingView(R.drawable.l5_2_color);
					stageP.setPreviewImage(R.drawable.l5_2_thumbs);
					stageP.setSolutionPath(R.raw.level5_52);
					stageP.setNextStageId(AppConstants.LEVEL05_STAGE_03);
				}
				
				if(stageID.compareTo(AppConstants.LEVEL05_STAGE_03) == 0)
				{
					stageP.setExcerciseView(R.drawable.l5_3);
					stageP.setPaintingView(R.drawable.l5_3_color);
					stageP.setPreviewImage(R.drawable.l5_3_thumbs);
					stageP.setSolutionPath(R.raw.level5_53);
					stageP.setNextStageId(AppConstants.LEVEL05_STAGE_04);
				}
				
				if(stageID.compareTo(AppConstants.LEVEL05_STAGE_04) == 0)
				{
					stageP.setExcerciseView(R.drawable.l5_4);
					stageP.setPaintingView(R.drawable.l5_4_color);
					stageP.setPreviewImage(R.drawable.l5_4_thumbs);
					stageP.setSolutionPath(R.raw.level5_54);
					stageP.setNextStageId(AppConstants.LEVEL05_STAGE_05);
				}
				
				if(stageID.compareTo(AppConstants.LEVEL05_STAGE_05) == 0)
				{
					stageP.setExcerciseView(R.drawable.l5_5);
					stageP.setPaintingView(R.drawable.l5_5_color);
					stageP.setPreviewImage(R.drawable.l5_5_thumbs);
					stageP.setSolutionPath(R.raw.level5_55);
					stageP.setNextStageId(AppConstants.LEVEL05_STAGE_06);
				}
				
				if(stageID.compareTo(AppConstants.LEVEL05_STAGE_06) == 0)
				{
					stageP.setExcerciseView(R.drawable.l5_6);
					stageP.setPaintingView(R.drawable.l5_6_color);
					stageP.setPreviewImage(R.drawable.l5_6_thumbs);
					stageP.setSolutionPath(R.raw.level5_56);
					stageP.setNextStageId(AppConstants.LEVEL05_STAGE_07);
				}
				
				if(stageID.compareTo(AppConstants.LEVEL05_STAGE_07) == 0)
				{
					stageP.setExcerciseView(R.drawable.l5_7);
					stageP.setPaintingView(R.drawable.l5_7_color);
					stageP.setPreviewImage(R.drawable.l5_7_thumbs);
					stageP.setSolutionPath(R.raw.level5_57);
					stageP.setNextStageId(AppConstants.LEVEL05_STAGE_08);
				}
				
				if(stageID.compareTo(AppConstants.LEVEL05_STAGE_08) == 0)
				{
					stageP.setExcerciseView(R.drawable.l5_8);
					stageP.setPaintingView(R.drawable.l5_8_color);
					stageP.setPreviewImage(R.drawable.l5_8_thumbs);
					stageP.setSolutionPath(R.raw.level5_58);
					stageP.setNextStageId(AppConstants.LEVEL05_STAGE_09);
				}
				
				if(stageID.compareTo(AppConstants.LEVEL05_STAGE_09) == 0)
				{
					stageP.setExcerciseView(R.drawable.l5_9);
					stageP.setPaintingView(R.drawable.l5_9_color);
					stageP.setPreviewImage(R.drawable.l5_9_thumbs);
					stageP.setSolutionPath(R.raw.level5_59);
					stageP.setNextStageId(null);
				}
				
				*/
		
		return stageP;
	}
}