package gt.com.santillana.trazos.android.finalstage;

import gt.com.santillana.trazos.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class FinalStageOne extends Activity {
	private WebView wv; 
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_level1);
		wv = (WebView) findViewById(R.id.webview);
		wv.setHorizontalScrollBarEnabled(false);
		wv.setVerticalScrollBarEnabled(false);
		wv.setFocusable(false);
		
		WebSettings ws = wv.getSettings();
		ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(false);
		ws.setSupportZoom(false);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		wv.loadUrl("file:///android_asset/game4/index.html");
	}
}
