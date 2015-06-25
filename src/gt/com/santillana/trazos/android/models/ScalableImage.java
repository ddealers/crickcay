package gt.com.santillana.trazos.android.models;

public class ScalableImage {

	private int drawableId;
	private float width;
	private float height;
	
	public ScalableImage()
	{
		
	}
	
	public ScalableImage(int drawableId, float width, float height)
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
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}
}