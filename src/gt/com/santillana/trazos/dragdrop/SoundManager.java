package gt.com.santillana.trazos.dragdrop;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class SoundManager {

	private static Context pContext;
	private static MediaPlayer player;
	
	public static void SoundManagerInitializer(Activity appContext){
		pContext = appContext;
		appContext.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}
	
	public static int play(int idSonido){
		stop();
		
		player = MediaPlayer.create(pContext, idSonido);
		player.setLooping(false);
		player.start();
		
		return 0;
	}
	
	public static void stop(){
		if(player != null){
			player.release();
		}
	}
	
}
