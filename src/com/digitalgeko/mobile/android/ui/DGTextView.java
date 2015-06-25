package com.digitalgeko.mobile.android.ui;

import gt.com.santillana.trazos.android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class DGTextView extends TextView {

	private String mFont;
	private int strokeWidth;
	private int strokeColor;
	private boolean enableCutOffBorderFix;

	public DGTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if(!isInEditMode()) {
				init(context);
		}
		strokeColor = Color.BLACK;
		strokeWidth = 0;
	}

	public DGTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(!isInEditMode()) {
			TypedArray a = context.getTheme().obtainStyledAttributes(
			        attrs,
			        R.styleable.DGTextView,
			        0, 0);
			try {
				mFont = a.getString(R.styleable.DGTextView_font);
				strokeColor = a.getInt(R.styleable.DGTextView_strokeColor, Color.BLACK);
				strokeWidth = a.getInt(R.styleable.DGTextView_strokeWidth, 0);
				enableCutOffBorderFix = a.getBoolean(R.styleable.DGTextView_enableCutOffBorderFix, true); 
			} finally {
				a.recycle();
			}
	
			init(context);
		}
	}

	public DGTextView(Context context) {
		super(context);
		init(context);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		//if(strokeWidth>0 || mFont != null)
		if(enableCutOffBorderFix)
		{
			text = " "+text.toString()+" ";//Jib for showing the text completely when it has a special style, otherwise the first and last letters look cut off
		}
	    super.setText(text, type);
	}
	
	protected void init(Context context) {
		if (mFont != null) {
			Typeface tf = Typeface.createFromAsset(
					context.getAssets(),mFont);
			setTypeface(tf);
			if(enableCutOffBorderFix)
			{
				String jibText = " "+this.getText().toString()+" ";//Jib for showing the text completely when it has a special style, otherwise the first and last letters look cut off
				this.setText(jibText);
			}
		}
		
	}
	
	 @Override
	 public void draw(Canvas canvas) {
		 
		 if(strokeWidth > 0)
		 {
			 Paint textPaint = this.getPaint();
			 int oColor = this.getCurrentTextColor();
			 Paint.Style oStyle = textPaint.getStyle();
		     textPaint.setStyle(Style.STROKE);
		     textPaint.setStrokeWidth(strokeWidth);
		     this.setTextColor(strokeColor);
		     super.draw(canvas);
		     
		     this.setTextColor(oColor);
		     textPaint.setStyle(oStyle);
		 }
		 
	     super.draw(canvas);
	 }

	 /**
	  * Stroke width in pixels
	  */
	public int getStrokeWidth() {
		return strokeWidth;
	}

	/**
	 * 
	 * @param strokeWidth width in pixels
	 */
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public int getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
	}
	
	public void enableCutOffBorderFix(boolean isEnabled){
		this.enableCutOffBorderFix = isEnabled;
	}
}
