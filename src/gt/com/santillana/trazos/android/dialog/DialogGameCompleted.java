package gt.com.santillana.trazos.android.dialog;

import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.android.finalstage.FinalStageBase;
import gt.com.santillana.trazos.utils.NavigationUtils;
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
public class DialogGameCompleted extends Dialog implements android.view.View.OnClickListener {

	private ImageButton btnRestart;
	private ImageButton btnSave;
	private ImageButton btnHome;
	private FinalStageBase finalStage;
	
	public DialogGameCompleted(FinalStageBase finalStage) {
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
		setContentView(R.layout.dialog_game_completed);
		btnRestart = (ImageButton)findViewById(R.id.dialogGameCompleted_btnRestart);
		btnHome = (ImageButton)findViewById(R.id.dialogGameCompleted_bntHome);
		btnSave = (ImageButton)findViewById(R.id.dialogGameCompleted_btnSave);
		
		btnRestart.setOnClickListener(this);
		btnHome.setOnClickListener(this);
		btnSave.setOnClickListener(this);
	}
		
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.dialogGameCompleted_btnRestart:
			finalStage.reset();
			this.dismiss();
			break;
		case R.id.dialogGameCompleted_bntHome:
			NavigationUtils.goToHomeScreen(this.getOwnerActivity());
			this.dismiss();
			break;
		case R.id.dialogGameCompleted_btnSave:
			System.gc();
			finalStage.takeScreenshot();
			break;
		}
	}
}
