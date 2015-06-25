package gt.com.santillana.trazos.android.models;

public class DragAndDropShape {

	/**
	 * Identifier of the drawable element that will be used as the colored image to drag.
	 */
	private int colorImgSrcId;
	/**
	 * Info about the target on which the image will be dropped.
	 */
	private DropTarget dropTarget;
	/**
	 * Id of the ImageView component that will be used to show the image to drag.
	 */
	private int colorImgContainerid;
	/**
	 * Id of the sound in the raw folder that will be reproduced for this image.
	 */
	private int soundRawId;
	
	private boolean onTopLayer;
	
	public DragAndDropShape(int colorImgSrcId, DropTarget dropTarget, int soundRawId){
		this.colorImgSrcId = colorImgSrcId;
		this.dropTarget = dropTarget;
		this.soundRawId = soundRawId;
		this.onTopLayer = false;
	}
	
	public DragAndDropShape(int colorImgSrcId, DropTarget dropTarget, int soundRawId, boolean isOnTopLayer){
		this.colorImgSrcId = colorImgSrcId;
		this.dropTarget = dropTarget;
		this.soundRawId = soundRawId;
		this.onTopLayer = isOnTopLayer;
	}
	
	public int getColorImgSrcId() {
		return colorImgSrcId;
	}
	public void setColorImgSrcId(int colorImg) {
		this.colorImgSrcId = colorImg;
	}
	public DropTarget getDropTarget() {
		return dropTarget;
	}
	public void setDropTarget(DropTarget dropTarget) {
		this.dropTarget = dropTarget;
	}
	public int getColorImgContainerid() {
		return colorImgContainerid;
	}
	public void setColorImgContainerid(int colorImgContainerid) {
		this.colorImgContainerid = colorImgContainerid;
	}

	public int getSoundRawId() {
		return soundRawId;
	}

	public void setSoundRawId(int soundRawId) {
		this.soundRawId = soundRawId;
	}


	/**
	 * @return the onTopLayer
	 */
	public boolean isOnTopLayer() {
		return onTopLayer;
	}


	/**
	 * @param onTopLayer the onTopLayer to set
	 */
	public void setOnTopLayer(boolean onTopLayer) {
		this.onTopLayer = onTopLayer;
	}
}
