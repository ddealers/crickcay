package gt.com.santillana.trazos.android.dialog;

import gt.com.santillana.trazos.android.CanvasDrawingActivity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class ScoreDialogFragment extends DialogFragment {

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		ScoreDialog dialog = new ScoreDialog((CanvasDrawingActivity) getActivity());
		
		return dialog;
	}
}
