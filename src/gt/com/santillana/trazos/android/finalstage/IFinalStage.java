package gt.com.santillana.trazos.android.finalstage;

import gt.com.santillana.trazos.android.models.DragAndDropShape;

import java.util.Vector;

public interface IFinalStage {

	public void resizeWhiteImages(Vector<DragAndDropShape> shapes);
	public int getDragViewId(int correlative);
}
