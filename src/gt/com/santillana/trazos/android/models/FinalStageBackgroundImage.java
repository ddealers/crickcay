package gt.com.santillana.trazos.android.models;

public class FinalStageBackgroundImage {
	
	private int drawableId;
	private double height;
	private double width;
	
	public FinalStageBackgroundImage(int drawableId, double width, double height)
	{
		this.drawableId = drawableId;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @return the drawableId
	 */
	public int getDrawableId() {
		return drawableId;
	}
	/**
	 * @param drawableId the drawableId to set
	 */
	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}
	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}
}
