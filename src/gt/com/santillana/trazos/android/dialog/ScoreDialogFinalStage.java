package gt.com.santillana.trazos.android.dialog;

import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.android.finalstage.FinalStageBase;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;


/**
 * 
 * @author Carlos Ortiz
 *
 */
public class ScoreDialogFinalStage extends Dialog implements android.view.View.OnClickListener {

	private ImageButton btnRestart;
	private ImageButton btnSave;
	private ImageButton btnNextStage;
	private FinalStageBase finalStage;
	
	public ScoreDialogFinalStage(FinalStageBase finalStage) {
		super(finalStage);
		this.finalStage = finalStage;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(false);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_finallevel_score);
		btnRestart = (ImageButton)findViewById(R.id.scoreDialogFinalStage_btnRestart);
		btnNextStage = (ImageButton)findViewById(R.id.scoreDialogFinalStage_bntNextStage);
		btnSave = (ImageButton)findViewById(R.id.scoreDialogFinalStage_btnSave);
		
		btnRestart.setOnClickListener(this);
		btnNextStage.setOnClickListener(this);
		btnSave.setOnClickListener(this);
	}
		
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.scoreDialogFinalStage_btnRestart:
			finalStage.reset();
			this.dismiss();
			break;
		case R.id.scoreDialogFinalStage_bntNextStage:
			finalStage.returnTolevelSelection();
			this.dismiss();
			break;
		case R.id.scoreDialogFinalStage_btnSave:
			System.gc();
			finalStage.takeScreenshot();
			break;
		}
	}
}
