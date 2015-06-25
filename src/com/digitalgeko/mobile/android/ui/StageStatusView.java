package com.digitalgeko.mobile.android.ui;

import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.android.controller.AppManager;
import gt.com.santillana.trazos.android.score.Score;
import gt.com.santillana.trazos.utils.MemoryManager;
import gt.com.santillana.trazos.utils.bitmaps.BitmapHandler;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class StageStatusView extends RelativeLayout implements ViewTreeObserver.OnPreDrawListener {

	private RelativeLayout unlockedViewLayout;
	private ImageView stageImage;
	private ImageView star01;
	private ImageView star02;
	private ImageView star03;
	private ImageView franRoja;
	private int stageImageDrawableId = R.drawable.ic_lock;
	ViewTreeObserver vto;
	Resources res;
	
	RelativeLayout.LayoutParams lParams;
	
	public StageStatusView(Context context){
		super(context);
		init();
	}
	
	public StageStatusView(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}
	
	private void init(){
		this.setEnabled(false);
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.stagestatusview, this, true);
		
		vto = this.getViewTreeObserver();
		vto.addOnPreDrawListener(this);
		
		unlockedViewLayout = (RelativeLayout)findViewById(R.id.stageStatus_UnlockedViewLayout);
		stageImage = (ImageView)findViewById(R.id.stageStatus_ivPreviewImage);
		star01 = (ImageView)findViewById(R.id.stageStatus_ivStar01);
		star02 = (ImageView)findViewById(R.id.stageStatus_ivStar02);
		star03 = (ImageView)findViewById(R.id.stageStatus_ivStar03);
		franRoja = (ImageView)findViewById(R.id.stageStatus_ivFranja_roja);
		
		res = this.getContext().getResources();
	}
	
	public void releaseImagesMemory()
	{
		MemoryManager.unbindDrawables( stageImage );
		MemoryManager.unbindDrawables( star01 );
		MemoryManager.unbindDrawables( star02 );
		MemoryManager.unbindDrawables( star03 );
		MemoryManager.unbindDrawables( franRoja );
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		vto = this.getViewTreeObserver();
	}
	
	private void setupImages()
	{
		/*star01.setImageResource(R.drawable.score_stars);
		star02.setImageResource(R.drawable.score_stars);
		star03.setImageResource(R.drawable.score_stars);
		franRoja.setImageResource(R.drawable.scores_franja_roja);
		stageImage.setImageResource(stageImageDrawableId);*/
		
		BitmapHandler.loadBitmap(R.drawable.score_stars, star01, res);
		BitmapHandler.loadBitmap(R.drawable.score_stars, star02, res);
		BitmapHandler.loadBitmap(R.drawable.score_stars, star03, res);
		BitmapHandler.loadBitmap(R.drawable.scores_franja_roja, franRoja, res);
		BitmapHandler.loadBitmap(stageImageDrawableId, stageImage, res);
	}
	
	private void alignStars()
	{
		int starHeight = (int)(franRoja.getMeasuredHeight()*0.2);
		int starWidth = (int)(franRoja.getMeasuredHeight()*0.2);
		
		//star01
		lParams = (RelativeLayout.LayoutParams)star01.getLayoutParams();
		lParams.height = starHeight;
		lParams.width = starWidth;
		star01.setMaxHeight(starHeight);
		star01.setMaxWidth(starWidth);
		lParams.leftMargin = getPxPercentage(franRoja.getMeasuredWidth(), 45);
		lParams.bottomMargin = getPxPercentage(franRoja.getMeasuredHeight(), 4);
		star01.setLayoutParams(lParams);
		
		//star02
		RelativeLayout.LayoutParams lParams2 = (RelativeLayout.LayoutParams)star02.getLayoutParams();
		lParams2.height = starHeight;
		lParams2.width = starWidth;
		lParams2.leftMargin = (int)(lParams.leftMargin + (starWidth*0.80));
		star02.setLayoutParams(lParams2);
		star02.setMaxHeight(starHeight);
		star02.setMaxWidth(starWidth);
		
		//star03
		RelativeLayout.LayoutParams lParams3 = (RelativeLayout.LayoutParams)star03.getLayoutParams();
		lParams3.height = starHeight;
		lParams3.width = starWidth;
		lParams3.leftMargin = lParams2.leftMargin + (int)(starWidth*0.70);
		star03.setMaxHeight(starHeight);
		star03.setMaxWidth(starWidth);
		star03.setLayoutParams(lParams3);
	}
	
	private int getPxPercentage(int measure, double percentaje){
		return (int) (percentaje * measure / 100);
	}
	
	public void setScore(int score)
	{
		if(AppManager.getInstance().isInDeveloperMode() || score != Score.LOCKED)
		{
			unlockedViewLayout.setVisibility(View.VISIBLE);
			ImageView lock = (ImageView)findViewById(R.id.stageStatus_ivLock);
			lock.setVisibility(View.INVISIBLE);
			this.setEnabled(true);
		}
		
		star03.setEnabled(score>=Score.SCORE_PERFECT);
		star02.setEnabled(score>=Score.SCORE_VERY_GOOD);
		star01.setEnabled(score>=Score.SCORE_GOOD);
	}
	
	public void setImageResource(int resId)
	{
		this.stageImageDrawableId = resId;
		BitmapHandler.loadBitmap(resId, stageImage, res);
	}

	@Override
	public boolean onPreDraw() {
		setupImages();
		alignStars();
		vto.removeOnPreDrawListener(this);
		
		return true;
	}
}
