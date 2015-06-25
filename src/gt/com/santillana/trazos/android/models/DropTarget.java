package gt.com.santillana.trazos.android.models;

public class DropTarget {

	private int imgViewId;
	private int drawableId;
	private double x;
	private double y;
	private double width;
	private double height;
	
	public DropTarget(int dropTargetSrcId, double x, double y, double width, double height)
	{
		setDrawableId(dropTargetSrcId);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	/**
	 * @return the imgViewId
	 */
	public int getImgViewId() {
		return imgViewId;
	}

	/**
	 * @param imgViewId the imgViewId to set
	 */
	public void setImgViewId(int imgViewId) {
		this.imgViewId = imgViewId;
	}

	public int getDrawableId() {
		return drawableId;
	}

	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
}
