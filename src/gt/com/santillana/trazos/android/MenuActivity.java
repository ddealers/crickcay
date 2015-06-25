package gt.com.santillana.trazos.android;

import gt.com.santillana.trazos.android.config.AppConstants;
import gt.com.santillana.trazos.android.controller.AppManager;
import gt.com.santillana.trazos.android.storage.DBManager;
import gt.com.santillana.trazos.utils.MemoryManager;
import gt.com.santillana.trazos.utils.NavigationUtils;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class MenuActivity extends Activity implements DialogInterface.OnClickListener {

	ImageView ivLogoText;
	ImageView ivLogoTruck;
	ImageButton btnNext;
	ImageButton btnConfig;
	ImageButton btnCredits;
	ImageButton btnResetApp;
	
	private AppManager appManager = AppManager.getInstance();
	private ToggleButton btnSound;
	private AlertDialog.Builder dialogBuilder;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
		
		ActivityManager mgr = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		mgr.getMemoryInfo(info);
		Log.w("Activity", "MenuActivity Create");
		Log.w("Memory Information", "AvialMem:" + info.availMem/(1024*1024) + " M");
        
        ivLogoText = (ImageView)findViewById(R.id.logo_trazos);
    	ivLogoTruck = (ImageView)findViewById(R.id.imgMenuLogo);
    	btnNext = (ImageButton)findViewById(R.id.menu_btnNext);
    	btnConfig = (ImageButton)findViewById(R.id.menu_btnConfig);
    	btnCredits = (ImageButton)findViewById(R.id.menu_btnCredits);
    	btnResetApp = (ImageButton)findViewById(R.id.menu_btnResetApp);
        
        btnSound = (ToggleButton)findViewById(R.id.menu_btnSound);
        btnSound.setChecked(appManager.isSoundOn(this));
        
        appManager.setCurrentLevelId(AppConstants.NO_LEVEL_SELECTED);
        
        dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder
				.setMessage(
						getResources().getString(R.string.help_msgConfirmReset))
				.setPositiveButton(getResources().getString(R.string.base_btnYes), this)
				.setNegativeButton(getResources().getString(R.string.base_btnNo), this);
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	setupImages();
    	appManager.setCurrentLevelId(AppConstants.NO_LEVEL_SELECTED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
    	releaseImagesMemory();
    }
    
    @Override
    public void onDestroy(){
		ActivityManager mgr = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		mgr.getMemoryInfo(info);
		Log.w("Activity", "MenuActivity Destroy");
		Log.w("Memory Information", "AvialMem:" + info.availMem/(1024*1024) + " M");
		
    	super.onDestroy();
    }

    public void releaseImagesMemory()
	{
		MemoryManager.unbindDrawables( ivLogoText );
		MemoryManager.unbindDrawables( ivLogoTruck );
		MemoryManager.unbindDrawables( btnNext );
		MemoryManager.unbindDrawables( btnConfig );
		MemoryManager.unbindDrawables( btnCredits );
		MemoryManager.unbindDrawables( btnResetApp );
	}
    
    private void setupImages()
    {
    	ivLogoText.setImageResource(R.drawable.app_tittle);
    	ivLogoTruck.setImageResource(R.drawable.ic_menu_logo);
    	btnNext.setImageResource(R.drawable.icon_next_green);
    	btnConfig.setImageResource(R.drawable.icon_configuration_green);
    	btnCredits.setImageResource(R.drawable.icon_credits_green);
    	btnResetApp.setImageResource(R.drawable.repetir);
    }
    
    public void onClickListener(View v)
    {
    	switch(v.getId())
    	{
    	case R.id.menu_btnConfig:
    		int currVisibility = btnCredits.getVisibility();
    		int newVisibility = currVisibility==View.VISIBLE?View.INVISIBLE:View.VISIBLE;
    		btnSound.setVisibility(newVisibility);
    		btnCredits.setVisibility(newVisibility);
    		btnResetApp.setVisibility(newVisibility);
    		break;
    	case R.id.menu_btnCredits:
    		NavigationUtils.goToAppConfiguration(this);
    		break;
    	case R.id.menu_btnNext:
    		NavigationUtils.goToLevelSelection(this);
        	break;
    	case R.id.menu_btnSound:
    		appManager.setSoundState(btnSound.isChecked(), this);
    		break;
    	case R.id.menu_btnResetApp:
			dialogBuilder.show();
			break;
    	}
    }
    
    @Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			DBManager.resetDB(this);
			break;

		case DialogInterface.BUTTON_NEGATIVE:
			// No button clicked
			break;
		}
	}
}
