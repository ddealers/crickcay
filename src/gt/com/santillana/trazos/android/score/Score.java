package gt.com.santillana.trazos.android.score;

import android.util.Log;

public class Score {
	
	public final static int LOCKED = -1;
	public final static int SCORE_ZERO = 0;
	public final static int SCORE_GOOD = 1;
	public final static int SCORE_VERY_GOOD = 2;
	public final static int SCORE_PERFECT = 3;

	public static int getScoreFromPoints(float points)
	{
		Log.i("Puntaje ", "Puntaje = "+points);
		int score = SCORE_ZERO;
		if (points >= 90)
			score = Score.SCORE_PERFECT;
		else if (points >= 75)
			score = Score.SCORE_VERY_GOOD;
		else if (points >= 60)
			score = Score.SCORE_GOOD;
		return score;
	}
}