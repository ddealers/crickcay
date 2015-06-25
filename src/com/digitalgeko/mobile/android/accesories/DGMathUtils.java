package com.digitalgeko.mobile.android.accesories;

public class DGMathUtils {

	public static double distanceBetweenPoints(float x1, float y1, float x2, float y2)
	{
		double distance = Math.sqrt( Math.pow((x2-x1),2) +  Math.pow((y2-y1),2) );
		return distance;
	}
}
