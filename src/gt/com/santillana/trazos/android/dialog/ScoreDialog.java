package gt.com.santillana.trazos.android.dialog;

import gt.com.santillana.trazos.android.CanvasDrawingActivity;
import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.android.score.Score;
import gt.com.santillana.trazos.utils.MemoryManager;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * 
 * @author Carlos Ortiz
 * 
 */
public class ScoreDialog extends Dialog implements android.view.View.OnClickListener {

	private ImageButton btnRestart;
	private ImageButton btnPaintMode;
	private ImageButton btnNextStage;
	private CanvasDrawingActivity drawingActivity;

	public ScoreDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(false);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_dialog);
		btnRestart = (ImageButton) findViewById(R.id.scoreDialog_btnRestart);
		btnNextStage = (ImageButton) findViewById(R.id.scoreDialog_bntNextStage);
		btnPaintMode = (ImageButton) findViewById(R.id.scoreDialog_btnPaintMode);

		btnRestart.setOnClickListener(this);
		btnNextStage.setOnClickListener(this);
		btnPaintMode.setOnClickListener(this);
	}

	/**
	 * 
	 * @param score
	 */
	public void setScore(CanvasDrawingActivity drawingActivity, int score) {
		this.drawingActivity = drawingActivity;
		
		if (score >= Score.SCORE_GOOD) {
			((ImageView) findViewById(R.id.scoreDialog_ivStar1)).setImageResource(R.drawable.estrella_1_rellena);
			findViewById(R.id.scoreDialog_bntNextStage).setEnabled(true);
			findViewById(R.id.scoreDialog_btnPaintMode).setEnabled(true);
		} else {
			((ImageView) findViewById(R.id.scoreDialog_ivStar1)).setImageResource(R.drawable.estrella_1_silueta);
			findViewById(R.id.scoreDialog_bntNextStage).setEnabled(false);
			findViewById(R.id.scoreDialog_btnPaintMode).setEnabled(false);
		}

		if (score >= Score.SCORE_VERY_GOOD) {
			((ImageView) findViewById(R.id.scoreDialog_ivStar2)).setImageResource(R.drawable.estrella_2_rellena);
		} else {
			((ImageView) findViewById(R.id.scoreDialog_ivStar2)).setImageResource(R.drawable.estrella_2_silueta);
		}

		if (score >= Score.SCORE_PERFECT) {
			((ImageView) findViewById(R.id.scoreDialog_ivStar3)).setImageResource(R.drawable.estrella_3_rellena);
		} else {
			((ImageView) findViewById(R.id.scoreDialog_ivStar3)).setImageResource(R.drawable.estrella_3_silueta);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		MemoryManager.unbindDrawables(btnNextStage);
		MemoryManager.unbindDrawables(btnPaintMode);
		MemoryManager.unbindDrawables(btnRestart);
		MemoryManager.unbindDrawables(findViewById(R.id.scoreDialog_bgCircle));
		MemoryManager.unbindDrawables(findViewById(R.id.scoreDialog_ivStar1));
		MemoryManager.unbindDrawables(findViewById(R.id.scoreDialog_ivStar2));
		MemoryManager.unbindDrawables(findViewById(R.id.scoreDialog_ivStar3));
		
		// Set internal callbacks to null
		drawingActivity = null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scoreDialog_btnRestart:
			drawingActivity.reset();
			break;
		case R.id.scoreDialog_bntNextStage:
			drawingActivity.loadNextStage();
			break;
		case R.id.scoreDialog_btnPaintMode:
			System.gc();
			drawingActivity.enterPaintMode();
			break;
		}
		this.dismiss();
	}
}
