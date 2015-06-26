package gt.com.santillana.trazos.android.models;

public class PuzzlePiece {

	private int pair_id;
	private boolean open;
	
	public PuzzlePiece(int pair_id, boolean open){
		this.pair_id = pair_id;
		this.open = open;
	}
	
	public int getPair_id() {
		return pair_id;
	}
	public void setPair_id(int pair_id) {
		this.pair_id = pair_id;
	}
	public Boolean getPair_isOpen() {
		return open;
	}
	public void setPair_isOpen(Boolean open) {
		this.open = open;
	}

}
