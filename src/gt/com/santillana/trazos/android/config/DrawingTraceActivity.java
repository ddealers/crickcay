package gt.com.santillana.trazos.android.config;

import gt.com.santillana.trazos.android.ColorPickerDialog;
import gt.com.santillana.trazos.android.ColorPickerDialog.OnColorChangedListener;
import gt.com.santillana.trazos.android.MenuActivity;
import gt.com.santillana.trazos.android.MyView;
import gt.com.santillana.trazos.android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;

/**
 * 
 */
public class DrawingTraceActivity extends Activity implements OnColorChangedListener {

	private static final int COLOR_MENU_ID = Menu.FIRST;
	private static final int EMBOSS_MENU_ID = Menu.FIRST + 1;
	private static final int BLUR_MENU_ID = Menu.FIRST + 2;
	private static final int ERASE_MENU_ID = Menu.FIRST + 3;
	private static final int SRCATOP_MENU_ID = Menu.FIRST + 4;

	public static final int DIALOG_COLOR_PICKER_ID = 0;
	public static final int DIALOG_GAME_FINISH = 1;

	private int activated_button_id = -1;
	private Paint mPaint;
	private MaskFilter mEmboss;
	private MaskFilter mBlur;
	private TracePathView view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new TracePathView(this);
		view.setActivity(this);
		view.setScreenSolutionId(getIntent().getExtras().getInt(MyView.SCREEN_SOLUTION_ID));
		view.setScreenDrawId(getIntent().getExtras().getInt(MyView.SCREEN_DRAW_ID));
		view.setScreenBackgroundId(getIntent().getExtras().getInt(MyView.SCREEN_BACKGROUND_ID));

		View layout = LayoutInflater.from(this).inflate(
				R.layout.activity_config_drawing, null);
		FrameLayout frame = (FrameLayout) layout
				.findViewById(R.id.canvas_frame);

		LayoutParams params = new LayoutParams();
		params.gravity = Gravity.CENTER;
		view.setLayoutParams(params);

		setContentView(layout);
		frame.addView(view);

		// Paint object holds info about style and color to draw bitmap
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xff444444);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(12);
		view.setmPaint(mPaint);

		mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f);
		mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
	}
	
	protected Dialog onCreateDialog(int id) {
		final Dialog dialog;
		switch (id) {
		case DIALOG_COLOR_PICKER_ID:
			dialog = new ColorPickerDialog(this, this, mPaint.getColor());
			break;
		case DIALOG_GAME_FINISH:
			dialog = new Dialog(this);
			ImageButton restart = (ImageButton) dialog
					.findViewById(R.id.scoreDialog_btnRestart);
			restart.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					resetCanvas(v);
					dialog.dismiss();
					view.invalidate();
				}
			});
			ImageButton toMenu = (ImageButton) dialog
					.findViewById(R.id.scoreDialog_bntNextStage);
			toMenu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					returnToMenu(v);
					dialog.dismiss();
					view.invalidate();
				}
			});
//			ImageButton paintMode = (ImageButton) dialog
//					.findViewById(R.id.button_paint_mode);
//			paintMode.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//				}
//			});
			break;
		default:
			dialog = null;
		}
		return dialog;
	}

	public void paletteColor(View view) {

		int id = view.getId();

		if (activated_button_id != -1) {
			View last_button = findViewById(activated_button_id);
			last_button.setFocusable(false);
		}
		activated_button_id = id;
		view.setFocusable(true);

	}

	public void resetCanvas(View view) {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	public void returnToMenu(View view) {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
	}

	public void showColorPickerDialog(View view) {
		showDialog(DIALOG_COLOR_PICKER_ID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, COLOR_MENU_ID, 0, "Color").setShortcut('3', 'c');
		menu.add(0, EMBOSS_MENU_ID, 0, "Emboss").setShortcut('4', 's');
		menu.add(0, BLUR_MENU_ID, 0, "Blur").setShortcut('5', 'z');
		menu.add(0, ERASE_MENU_ID, 0, "Erase").setShortcut('5', 'z');
		menu.add(0, SRCATOP_MENU_ID, 0, "SrcATop").setShortcut('5', 'z');

		/****
		 * Is this the mechanism to extend with filter effects? Intent intent =
		 * new Intent(null, getIntent().getData());
		 * intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		 * menu.addIntentOptions( Menu.ALTERNATIVE, 0, new ComponentName(this,
		 * NotesList.class), null, intent, 0, null);
		 *****/
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mPaint.setXfermode(null);
		mPaint.setAlpha(0xFF);

		switch (item.getItemId()) {
		case COLOR_MENU_ID:
			new ColorPickerDialog(this, this, mPaint.getColor()).show();
			return true;
		case EMBOSS_MENU_ID:
			if (mPaint.getMaskFilter() != mEmboss) {
				mPaint.setMaskFilter(mEmboss);
			} else {
				mPaint.setMaskFilter(null);
			}
			return true;
		case BLUR_MENU_ID:
			if (mPaint.getMaskFilter() != mBlur) {
				mPaint.setMaskFilter(mBlur);
			} else {
				mPaint.setMaskFilter(null);
			}
			return true;
		case ERASE_MENU_ID:
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			return true;
		case SRCATOP_MENU_ID:
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
			mPaint.setAlpha(0x80);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void colorChanged(int color) {
		mPaint.setColor(color);
	}
	
	public void onSaveClick(View view) {
		this.view.saveValidPaths();
	}
}
