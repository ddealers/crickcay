package gt.com.santillana.trazos.level;

import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.android.models.LevelProperties;
import gt.com.santillana.trazos.android.models.ScalableImage;
import gt.com.santillana.trazos.utils.DisplayUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LevelsFragment extends Fragment {
	
	private LevelProperties levelInfo;
	private DisplayMetrics metrics;

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	static LevelsFragment newInstance(int num, LevelProperties levelInfo) {
		LevelsFragment levelFrag = new LevelsFragment();

		Bundle args = new Bundle();
		args.putString("tittle", levelInfo.getTittle());
		args.putInt("stagesApproved", levelInfo.getStagesApproved());
		args.putInt("stagesAmount", levelInfo.getStagesAmount());
		args.putBoolean("isCompleted", levelInfo.isCompleted());

		// drawable info
		args.putInt("drawableId", levelInfo.getLevelCoverDrawable().getDrawableId());
		args.putFloat("drawableWidth", levelInfo.getLevelCoverDrawable().getWidth());
		args.putFloat("drawableHeight", levelInfo.getLevelCoverDrawable().getHeight());
		levelFrag.setArguments(args);

		return levelFrag;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			ScalableImage levelImg = new ScalableImage();
			levelImg.setDrawableId(getArguments().getInt("drawableId"));
			levelImg.setWidth(getArguments().getFloat("drawableWidth"));
			levelImg.setHeight(getArguments().getFloat("drawableHeight"));

			levelInfo = new LevelProperties();
			levelInfo.setTittle(getArguments().getString("tittle"));
			levelInfo.setStagesApproved(getArguments().getInt("stagesApproved"));
			levelInfo.setStagesAmount(getArguments().getInt("stagesAmount"));
			levelInfo.isCompleted(getArguments().getBoolean("isCompleted"));
			levelInfo.setLevelCoverDrawable(levelImg);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View layoutView = inflater.inflate(R.layout.fragment_level_select_pager, container, false);

		TextView temp;

		String levelName = levelInfo.getTittle();

		// Set title
		TextView tvLevelName = (TextView) layoutView.findViewById(R.id.levelSelect_lblTitle);
		TextView tvLevelNameShadow = (TextView) layoutView.findViewById(R.id.levelSelect_lblTitleShadow);

		if (levelName.length() > 10) {
			float size = getResources().getDimension(R.dimen.fragment__level__font_size__little_world);
			
			tvLevelName.setTextSize(size);
			tvLevelNameShadow.setTextSize(size);
		}
		if (levelName.length() > 15) {
			float size = getResources().getDimension(R.dimen.fragment__level__font_size__dinosaurs_valley);
			
			tvLevelName.setTextSize(size);
			tvLevelNameShadow.setTextSize(size);
		}
		tvLevelName.setText(levelName);
		tvLevelNameShadow.setText(levelName);

		final ImageView notebook = (ImageView) layoutView.findViewById(R.id.levelSelect_ivBackground);

		final ScalableImage coverDetails = levelInfo.getLevelCoverDrawable();
		final ImageView imgCover = (ImageView) layoutView.findViewById(R.id.levelSelect_ivLevelImage);

		final ViewTreeObserver vto = notebook.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			private boolean hasMakeChanges = false;

			@Override
			public boolean onPreDraw() {
				if (metrics != null && !hasMakeChanges) {
					
					// Calculate new height
					int height = DisplayUtils.getPercentage(686, notebook.getMeasuredHeight(), coverDetails.getHeight());
					if(metrics.densityDpi == DisplayMetrics.DENSITY_TV && height > 300) {
						height = 300;
					}
					
					// Set view layout parameters
					ViewGroup.LayoutParams lParams = (ViewGroup.LayoutParams) imgCover.getLayoutParams();
					lParams.height = height;
					lParams.width = (int) (height * (coverDetails.getWidth() / coverDetails.getHeight()));
					imgCover.setLayoutParams(lParams);
					imgCover.setImageResource(coverDetails.getDrawableId());
					imgCover.invalidate();

					hasMakeChanges = true;
				}
				return true;
			}
		});

		// Set level status
		temp = (TextView) layoutView.findViewById(R.id.levelSelection_lblLevelProgress);
		temp.setText(levelInfo.getStagesApproved() + getString(R.string.levelSelect_separator) + levelInfo.getStagesAmount());

		// trophy
		final ImageView img = (ImageView) layoutView.findViewById(R.id.levelSelect_ivTrophy);
		img.setVisibility(levelInfo.isCompleted() ? View.VISIBLE : View.INVISIBLE);

		// updateLevelSelectionBar(levelInfo.getCorrelative(), layoutView);

		return layoutView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
	}

	private void updateLevelSelectionBar(int num, View layout) {
		layout.findViewById(R.id.levelSelection_level1).setEnabled(false);
		layout.findViewById(R.id.levelSelection_level2).setEnabled(false);
		layout.findViewById(R.id.levelSelection_level3).setEnabled(false);
		layout.findViewById(R.id.levelSelection_level4).setEnabled(false);
		layout.findViewById(R.id.levelSelection_level5).setEnabled(false);
		//layout.findViewById(R.id.levelSelection_level6).setEnabled(false);
		switch (num) {
		case 1:
			layout.findViewById(R.id.levelSelection_level1).setEnabled(true);
			break;
		case 2:
			layout.findViewById(R.id.levelSelection_level2).setEnabled(true);
			break;
		case 3:
			layout.findViewById(R.id.levelSelection_level3).setEnabled(true);
			break;
		case 4:
			layout.findViewById(R.id.levelSelection_level4).setEnabled(true);
			break;
		case 5:
			layout.findViewById(R.id.levelSelection_level5).setEnabled(true);
			break;
		case 6:
			//layout.findViewById(R.id.levelSelection_level6).setEnabled(true);
			break;
			
			
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
