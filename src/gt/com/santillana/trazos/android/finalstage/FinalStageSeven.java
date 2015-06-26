package gt.com.santillana.trazos.android.finalstage;

import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.utils.NavigationUtils;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import gt.com.santillana.trazos.android.models.PuzzlePiece;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class FinalStageSeven extends Activity {
	private HashMap<String, Integer> pieces;
	private String[] cardNames;
	private ImageView previous;
	private ImageView current;
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_level7);
		
		int[] cards = {1,2,3,4,5,6,7,8};
		View[] cardViews = {
			(ImageView) findViewById(R.id.puzzle001), 
			(ImageView) findViewById(R.id.puzzle002),
			(ImageView) findViewById(R.id.puzzle003),
			(ImageView) findViewById(R.id.puzzle004),
			(ImageView) findViewById(R.id.puzzle005),
			(ImageView) findViewById(R.id.puzzle006),
			(ImageView) findViewById(R.id.puzzle007),
			(ImageView) findViewById(R.id.puzzle008)
		};
		pieces = new HashMap<String, Integer>();
		pieces.put("1_1",R.drawable.lvl7_1_1);
		pieces.put("1_2",R.drawable.lvl7_1_2);
		pieces.put("2_1",R.drawable.lvl7_2_1);
		pieces.put("2_2",R.drawable.lvl7_2_2);
		pieces.put("3_1",R.drawable.lvl7_3_1);
		pieces.put("3_2",R.drawable.lvl7_3_2);
		pieces.put("4_1",R.drawable.lvl7_4_1);
		pieces.put("4_2",R.drawable.lvl7_4_2);
		pieces.put("5_1",R.drawable.lvl7_5_1);
		pieces.put("5_2",R.drawable.lvl7_5_2);
		pieces.put("6_1",R.drawable.lvl7_6_1);
		pieces.put("6_2",R.drawable.lvl7_6_2);
		pieces.put("7_1",R.drawable.lvl7_7_1);
		pieces.put("7_2",R.drawable.lvl7_7_2);
		pieces.put("8_1",R.drawable.lvl7_8_1);
		pieces.put("8_2",R.drawable.lvl7_8_2);
		
		ShuffleArray(cards);
		
		ImageView puzzlePiece;
		cardNames = new String[8];
		
		for(int i=0; i < 8; i++){
			int j = (int) Math.floor(i/2);
			if(i % 2 > 0){
				cardNames[i] = cards[j]+"_2";
			}else{
				cardNames[i] = cards[j]+"_1";
			}
			puzzlePiece = (ImageView) cardViews[i];
			puzzlePiece.setTag( new PuzzlePiece(cards[j], false) );
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	public void onGuessClickListener(View v) throws InterruptedException
	{	
		if(current != null) previous = current;
		switch(v.getId()){
			case R.id.puzzle001:
				current = (ImageView) findViewById(R.id.puzzle001);
				current.setImageResource(pieces.get(cardNames[0]));
				break;
			case R.id.puzzle002:
				current = (ImageView) findViewById(R.id.puzzle002);
				current.setImageResource(pieces.get(cardNames[1]));
				break;
			case R.id.puzzle003:
				current = (ImageView) findViewById(R.id.puzzle003);
				current.setImageResource(pieces.get(cardNames[2]));
				break;
			case R.id.puzzle004:
				current = (ImageView) findViewById(R.id.puzzle004);
				current.setImageResource(pieces.get(cardNames[3]));
				break;
			case R.id.puzzle005:
				current = (ImageView) findViewById(R.id.puzzle005);
				current.setImageResource(pieces.get(cardNames[4]));
				break;
			case R.id.puzzle006:
				current = (ImageView) findViewById(R.id.puzzle006);
				current.setImageResource(pieces.get(cardNames[5]));
				break;
			case R.id.puzzle007:
				current = (ImageView) findViewById(R.id.puzzle007);
				current.setImageResource(pieces.get(cardNames[6]));
				break;
			case R.id.puzzle008:
				current = (ImageView) findViewById(R.id.puzzle008);
				current.setImageResource(pieces.get(cardNames[7]));
				break;
		}
		if(previous != null){
			PuzzlePiece currentpp = (PuzzlePiece) current.getTag();
			PuzzlePiece curentprev = (PuzzlePiece) previous.getTag();
			if( (currentpp.getPair_id())  != curentprev.getPair_id()){
				Thread.sleep(2000);
				current.setImageResource(R.drawable.lvl7_tarjeta);
				previous.setImageResource(R.drawable.lvl7_tarjeta);
			}
		}
		log("Tag:" + current.getTag());
	}
	
	private void ShuffleArray(int[] array)
	{
	    int index;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        if (index != i)
	        {
	            array[index] ^= array[i];
	            array[i] ^= array[index];
	            array[index] ^= array[i];
	        }
	    }
	}
	
	private static void log(String aMessage){
		System.out.println(aMessage);
	}
}
