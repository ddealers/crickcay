package gt.com.santillana.trazos.android.models;
/**
 * 
 * @author Carlos Ortiz
 *
 */
public class StageInfo extends Stage {

	private int solutionPath;
	private int paintingView;
	private int excerciseView;
	private int previewImage;
	private String nextStageId;
	private boolean isUnlocked;
	
	public int getSolutionPath() {
		return solutionPath;
	}
	public void setSolutionPath(int solutionPath) {
		this.solutionPath = solutionPath;
	}
	public int getPaintingView() {
		return paintingView;
	}
	public void setPaintingView(int paintingView) {
		this.paintingView = paintingView;
	}
	public int getExcerciseView() {
		return excerciseView;
	}
	public void setExcerciseView(int excerciseView) {
		this.excerciseView = excerciseView;
	}
	public boolean isUnlocked() {
		return isUnlocked;
	}
	public void setUnlocked(boolean isUnlocked) {
		this.isUnlocked = isUnlocked;
	}
	public int getPreviewImage() {
		return previewImage;
	}
	public void setPreviewImage(int previewImage) {
		this.previewImage = previewImage;
	}
	public String getNextStageId() {
		return nextStageId;
	}
	public void setNextStageId(String nextStageId) {
		this.nextStageId = nextStageId;
	}
}
