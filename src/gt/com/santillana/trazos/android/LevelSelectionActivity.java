package gt.com.santillana.trazos.android;

import gt.com.santillana.trazos.android.config.AppConstants;
import gt.com.santillana.trazos.android.controller.AppManager;
import gt.com.santillana.trazos.android.controller.LevelConfigManager;
import gt.com.santillana.trazos.android.controller.LevelController;
import gt.com.santillana.trazos.android.models.LevelProperties;
import gt.com.santillana.trazos.level.LevelAdapter;
import gt.com.santillana.trazos.utils.MemoryManager;
import gt.com.santillana.trazos.utils.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LevelSelectionActivity extends FragmentActivity {

	LevelAdapter adapter;
	ViewPager pager;
	List<LevelProperties> levelsInfo = new ArrayList<LevelProperties>();
	private int currentPage = 0;
	final Handler handler = new Handler(Looper.getMainLooper());
	final Runnable runnable = new Runnable() {
		public void run() {
			handler.removeCallbacks(runnable);
			if (pager.getCurrentItem() < 4) {
				pager.setCurrentItem(pager.getCurrentItem() + 1, true);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_select);

		ActivityManager mgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		mgr.getMemoryInfo(info);
		Log.w("Activity", "LevelSelectionActivity Create");
		Log.w("Memory Information", "AvialMem:" + info.availMem / (1024 * 1024) + " M");

		// levelsInfo.add(
		// LevelConfigManager.getLevelInfo(AppConstants.LEVEL_01, this) );
		// levelsInfo.add(
		// LevelConfigManager.getLevelInfo(AppConstants.LEVEL_02, this) );
		// levelsInfo.add(
		// LevelConfigManager.getLevelInfo(AppConstants.LEVEL_03, this) );
		// levelsInfo.add(
		// LevelConfigManager.getLevelInfo(AppConstants.LEVEL_04, this) );
		// levelsInfo.add(
		// LevelConfigManager.getLevelInfo(AppConstants.LEVEL_05, this) );
		//
		// adapter = new LevelAdapter( getSupportFragmentManager(), levelsInfo
		// );
		// pager = (ViewPager)findViewById(R.id.pager);
		// pager.setAdapter(adapter);
	}

	@Override
	public void onDestroy() {
		ActivityManager mgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		mgr.getMemoryInfo(info);
		Log.w("Activity", "LevelSelectionActivity Destroy");
		Log.w("Memory Information", "AvialMem:" + info.availMem / (1024 * 1024) + " M");

		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.i("LevelSelection", "Got result ");

		if (requestCode == 1 && resultCode == RESULT_OK) {
			System.gc();
			handler.postDelayed(runnable, 750);
		}
	}

	public void onLevelClickListener(View v) {
		System.gc();

		boolean levelIsLocked = false;
		int selectedLevelIndex = pager.getCurrentItem();
		int selectedLevelId = AppConstants.getLevelId(selectedLevelIndex + 1);

		if (selectedLevelId != AppConstants.LEVEL_01 && !AppManager.getInstance().isInDeveloperMode()) {
			levelIsLocked = !LevelController.isCompleted(this, AppConstants.getLevelId(selectedLevelIndex));
		}

		if (levelIsLocked) {
			String lockMsg = getString(R.string.levelSelect_lockMessage);
			Toast.makeText(this, lockMsg, Toast.LENGTH_SHORT).show();
		} else {
			AppManager.getInstance().setCurrentLevelId(selectedLevelId);
			NavigationUtils.goToStageSelection(this);
		}
	}

	public void onButtonsClickListener(View v) {
		switch (v.getId()) {
		case R.id.levelSelection_btnHome:
			NavigationUtils.goToHomeScreen(this);
			break;
		}
	}

	@Override
	public void onRestart() {
		super.onRestart();
		LevelProperties levelInfo = LevelConfigManager.getLevelInfo(AppConstants.LEVEL_01, this);
		int approved = levelInfo.getStagesApproved();
		TextView temp = (TextView) findViewById((R.id.levelSelection_lblLevelProgress));
		temp.setText(approved + getString(R.string.levelSelect_separator) + levelInfo.getStagesAmount());
		findViewById(R.id.levelSelect_ivTrophy).setVisibility(levelInfo.isCompleted() ? View.VISIBLE : View.INVISIBLE);
	}

	@Override
	public void onResume() {
		super.onResume();
		// setupImages();

		levelsInfo.add(LevelConfigManager.getLevelInfo(AppConstants.LEVEL_01, this));
		levelsInfo.add(LevelConfigManager.getLevelInfo(AppConstants.LEVEL_02, this));
		levelsInfo.add(LevelConfigManager.getLevelInfo(AppConstants.LEVEL_03, this));
		levelsInfo.add(LevelConfigManager.getLevelInfo(AppConstants.LEVEL_04, this));
		levelsInfo.add(LevelConfigManager.getLevelInfo(AppConstants.LEVEL_05, this));
		levelsInfo.add(LevelConfigManager.getLevelInfo(AppConstants.LEVEL_06, this));
		levelsInfo.add(LevelConfigManager.getLevelInfo(AppConstants.LEVEL_07, this));
		levelsInfo.add(LevelConfigManager.getLevelInfo(AppConstants.LEVEL_08, this));

		adapter = new LevelAdapter(getSupportFragmentManager(), levelsInfo);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		pager.setCurrentItem(currentPage);
	}

	@Override
	public void onPause() {
		super.onPause();
		releaseImagesMemory();

		levelsInfo.clear();
		currentPage = pager.getCurrentItem();
	}

	private void releaseImagesMemory() {
		MemoryManager.unbindDrawables(findViewById(R.id.levelSelect_ivBackground));
		// MemoryManager.unbindDrawables(
		// findViewById(R.id.levelSelect_ivLevelImage) );
		MemoryManager.unbindDrawables(findViewById(R.id.levelSelect_ivTrophy));
		MemoryManager.unbindDrawables(findViewById(R.id.levelmenu_trophyBackground));
		MemoryManager.unbindDrawables(findViewById(R.id.levelSelection_btnHome));

		System.gc();
	}
}
