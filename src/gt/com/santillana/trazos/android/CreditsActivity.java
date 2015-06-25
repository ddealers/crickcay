package gt.com.santillana.trazos.android;

import gt.com.santillana.trazos.utils.NavigationUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class CreditsActivity extends Activity {

	private ImageButton btnHome;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        
        btnHome = (ImageButton)findViewById(R.id.credits_btnHome);
    }
	
	public void onClickListener(View v)
	{
		switch(v.getId())
		{
		case R.id.credits_btnHome:
			NavigationUtils.goToHomeScreen(this);
			break;
		}
	}
	
}
