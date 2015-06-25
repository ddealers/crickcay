package gt.com.santillana.trazos.level;

import gt.com.santillana.trazos.android.models.LevelProperties;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class LevelAdapter extends FragmentStatePagerAdapter {

	private static List<LevelProperties> levelsInfo;

	public LevelAdapter(FragmentManager fragmentManager, List<LevelProperties> levelsInfo) {
		super(fragmentManager);
		LevelAdapter.levelsInfo = levelsInfo;
	}
	
	public void setLevelsInfo(List<LevelProperties> levelsInfo)
	{
		LevelAdapter.levelsInfo = levelsInfo;
	}

	@Override
	public int getCount() {
		return levelsInfo.size();
	}

	@Override
	public Fragment getItem(int position) {
		Log.i("LevelAdapter", "getItem " + position );

		return LevelsFragment.newInstance(position, levelsInfo.get(position));
	}
}
