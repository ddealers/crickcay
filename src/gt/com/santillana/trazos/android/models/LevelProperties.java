package gt.com.santillana.trazos.android.models;

public class LevelProperties {

	private String tittle;
	private int correlative;
	private int stagesAmount;
	private int stagesApproved;
	private boolean isCompleted;
	private int nextLevelId = -1;
	
	private ScalableImage levelDrawable;
	
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	public int getCorrelative() {
		return correlative;
	}
	public void setCorrelative(int correlative) {
		this.correlative = correlative;
	}
	public int getStagesAmount() {
		return stagesAmount;
	}
	public void setStagesAmount(int stagesAmount) {
		this.stagesAmount = stagesAmount;
	}
	public int getStagesApproved() {
		return stagesApproved;
	}
	public void setStagesApproved(int stagesApproved) {
		this.stagesApproved = stagesApproved;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void isCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	public int getNextLevelId() {
		return nextLevelId;
	}
	public void setNextLevelId(int nextLevelId) {
		this.nextLevelId = nextLevelId;
	}
	/**
	 * @return the levelDrawable
	 */
	public ScalableImage getLevelCoverDrawable() {
		return levelDrawable;
	}
	/**
	 * @param levelDrawable the levelDrawable to set
	 */
	public void setLevelCoverDrawable(ScalableImage levelDrawable) {
		this.levelDrawable = levelDrawable;
	}
}
