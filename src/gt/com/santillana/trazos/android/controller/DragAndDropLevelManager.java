package gt.com.santillana.trazos.android.controller;

import gt.com.santillana.trazos.android.R;
import gt.com.santillana.trazos.android.config.AppConstants;
import gt.com.santillana.trazos.android.models.DragAndDropShape;
import gt.com.santillana.trazos.android.models.DropTarget;
import gt.com.santillana.trazos.android.models.FinalStage;
import gt.com.santillana.trazos.android.models.FinalStageBackgroundImage;

import java.util.Vector;

public class DragAndDropLevelManager {

	public static FinalStageBackgroundImage getLevelBackgroundDrawableId(int levelId) {
		int width = 925;
		int height = 612;
		switch (levelId) {
		case AppConstants.LEVEL_01:
			return new FinalStageBackgroundImage(R.drawable.l01_final_back, width, height);
		case AppConstants.LEVEL_02:
			return new FinalStageBackgroundImage(R.drawable.lvl2_back_final, width, height);
		case AppConstants.LEVEL_03:
			return new FinalStageBackgroundImage(R.drawable.back, width, height);
		case AppConstants.LEVEL_04:
			return new FinalStageBackgroundImage(R.drawable.lvl4_back, width, height);
		case AppConstants.LEVEL_05:
			return new FinalStageBackgroundImage(R.drawable.l05_final_back, width, height);
		default:
			return null;
		}
	}

	public final static FinalStage getPreviewImage(int currentLevel) {
		FinalStage finalStage = new FinalStage();
		switch (currentLevel) {
		case AppConstants.LEVEL_01:
			finalStage.setPreviewImgResourceId(R.drawable.l01_final_pv);
			break;
		case AppConstants.LEVEL_02:
			finalStage.setPreviewImgResourceId(R.drawable.l_2_thumbs_final);
			break;
		case AppConstants.LEVEL_03:
			finalStage.setPreviewImgResourceId(R.drawable.l3_thumbs_final);
			break;
		case AppConstants.LEVEL_04:
			finalStage.setPreviewImgResourceId(R.drawable.l4_thumbs_final);
			break;
		case AppConstants.LEVEL_05:
			finalStage.setPreviewImgResourceId(R.drawable.l5_thumbs_final);
			break;
		}
		return finalStage;
	}

	public static Vector<DragAndDropShape> getLevelShapes(int levelId) {
		switch (levelId) {
		case AppConstants.LEVEL_01:
			return buildLevel01();
		case AppConstants.LEVEL_02:
			return buildLevel02();
		case AppConstants.LEVEL_03:
			return buildLevel03();
		case AppConstants.LEVEL_04:
			return buildLevel04();
		case AppConstants.LEVEL_05:
			return buildLevel05();
		default:
			return null;
		}
	}

	private static Vector<DragAndDropShape> buildLevel01() {
		Vector<DragAndDropShape> shapes = new Vector<DragAndDropShape>();
		/*
		shapes.add(new DragAndDropShape(R.drawable.l01s01_final, new DropTarget(R.drawable.l01s01_final_white, 222.958, 430.243,
				219, 173), R.raw.pato, true));// pato
		shapes.add(new DragAndDropShape(R.drawable.l01s02_final, new DropTarget(R.drawable.l01s02_final_white, 149.251, 143.492,
				44.882, 71.417), R.raw.girasoles));// girasoles
		shapes.add(new DragAndDropShape(R.drawable.l01s03_final, new DropTarget(R.drawable.l01s03_final_white, 279.643, 0,
				322.924, 241.846), R.raw.granja));// granero
		shapes.add(new DragAndDropShape(R.drawable.l01s04_final, new DropTarget(R.drawable.l01s04_final_white, 677.405, 89.431,
				170.621, 127.602), R.raw.caballo));// caballo
		shapes.add(new DragAndDropShape(R.drawable.l01s05_final, new DropTarget(R.drawable.l01s05_final_white, 344.316, 261.772,
				139.298, 127.647), R.raw.cerdo));// cerdo
		shapes.add(new DragAndDropShape(R.drawable.l01s06_final, new DropTarget(R.drawable.l01s06_final_white, 539.115, 480.477,
				108.183, 120.064), R.raw.pollitos, true));// pollitos
		shapes.add(new DragAndDropShape(R.drawable.l01s07_final, new DropTarget(R.drawable.l01s07_final_white, 502.909, 194.5,
				369.408, 386.965), R.raw.espantapajaro));// espantapajaros
		shapes.add(new DragAndDropShape(R.drawable.l01s08_final, new DropTarget(R.drawable.l01s08_final_white, 21.198, 192.071,
				322.208, 328.407), R.raw.tractor));// tractor
		shapes.add(new DragAndDropShape(R.drawable.l01s09_final, new DropTarget(R.drawable.l01s09_final_white, 83.045, 521.18,
				62.928, 70.22), R.raw.flor));// flor
		*/
		shapes.add(new DragAndDropShape(R.drawable.l01_final_1, new DropTarget(R.drawable.l01s01_final_white, 222.958, 430.243,
				219, 173), R.raw.pato, true));// pato
		shapes.add(new DragAndDropShape(R.drawable.l01_final_2, new DropTarget(R.drawable.l01s02_final_white, 149.251, 143.492,
				44.882, 71.417), R.raw.girasoles));// girasoles
		shapes.add(new DragAndDropShape(R.drawable.l01_final_3, new DropTarget(R.drawable.l01s03_final_white, 279.643, 0,
				322.924, 241.846), R.raw.granja));// granero
		shapes.add(new DragAndDropShape(R.drawable.l01_final_4, new DropTarget(R.drawable.l01s04_final_white, 677.405, 89.431,
				170.621, 127.602), R.raw.caballo));// caballo
		shapes.add(new DragAndDropShape(R.drawable.l01_final_5, new DropTarget(R.drawable.l01s05_final_white, 344.316, 261.772,
				139.298, 127.647), R.raw.cerdo));// cerdo
		shapes.add(new DragAndDropShape(R.drawable.l01_final_6, new DropTarget(R.drawable.l01s06_final_white, 539.115, 480.477,
				108.183, 120.064), R.raw.pollitos, true));// pollitos
		shapes.add(new DragAndDropShape(R.drawable.l01_final_7, new DropTarget(R.drawable.l01s07_final_white, 502.909, 194.5,
				369.408, 386.965), R.raw.espantapajaro));// espantapajaros
		shapes.add(new DragAndDropShape(R.drawable.l01_final_8, new DropTarget(R.drawable.l01s08_final_white, 21.198, 192.071,
				322.208, 328.407), R.raw.tractor));// tractor
		shapes.add(new DragAndDropShape(R.drawable.l01_final_9, new DropTarget(R.drawable.l01s09_final_white, 83.045, 521.18,
				62.928, 70.22), R.raw.flor));// flor
		
		return shapes;
	}

	private static Vector<DragAndDropShape> buildLevel02() {
		Vector<DragAndDropShape> shapes = new Vector<DragAndDropShape>();
		shapes.add(new DragAndDropShape(R.drawable.lvl2_1, new DropTarget(R.drawable.lvl2_1_white, 375.272, 16.783,
				143.495 + 5, 101.826 + 5), R.raw.l2_1));// luciernaga
		shapes.add(new DragAndDropShape(R.drawable.lvl2_2, new DropTarget(R.drawable.lvl2_2_white, 63.68, 5.004,
				139.629 + 5, 141.021 + 5), R.raw.l2_2));// mariquita
		shapes.add(new DragAndDropShape(R.drawable.lvl2_3, new DropTarget(R.drawable.lvl2_3_white, 267.636, 109.639,
				193.055 + 5, 191.04 + 5), R.raw.l2_3));// mariposa
		shapes.add(new DragAndDropShape(R.drawable.lvl2_4, new DropTarget(R.drawable.lvl2_4_white, 648.896, 105.549,
				159.298 + 5, 137.99 + 5), R.raw.l2_4));// abeja
		shapes.add(new DragAndDropShape(R.drawable.lvl2_5, new DropTarget(R.drawable.lvl2_5_white, 382.833, 428.355,
				236.13 + 5, 137.123 + 5), R.raw.l2_5));// gusano
		shapes.add(new DragAndDropShape(R.drawable.lvl2_6, new DropTarget(R.drawable.lvl2_6_white, 497.606, 154.977,
				145.77 + 5, 177.537 + 5), R.raw.l2_6));// saltamontes
		shapes.add(new DragAndDropShape(R.drawable.lvl2_7, new DropTarget(R.drawable.lvl2_7_white, 731.65, 467.688,
				128.858 + 5, 106.233 + 5), R.raw.l2_7));// lombris
		shapes.add(new DragAndDropShape(R.drawable.lvl2_8, new DropTarget(R.drawable.lvl2_8_white, 63.4, 440.818,
				120.276 + 5, 112.258 + 5), R.raw.l2_8));// hormiga
		shapes.add(new DragAndDropShape(R.drawable.lvl2_9, new DropTarget(R.drawable.lvl2_9_white, 55.727, 236.574,
				200, 147), R.raw.l2_9));// caracol
		//shapes.add(new DragAndDropShape(R.drawable.lvl2_10, new DropTarget(R.drawable.lvl2_10, 55.727, 236.574,
		//		200, 147), R.raw.l2_9));// caracol
		//shapes.add(new DragAndDropShape(R.drawable.lvl2_11, new DropTarget(R.drawable.lvl2_11, 75.727, 236.574,
		//		200, 147), R.raw.l2_9));// caracol
		//shapes.add(new DragAndDropShape(R.drawable.lvl2_12, new DropTarget(R.drawable.lvl2_12, 95.727, 236.574,
		//		200, 147), R.raw.l2_9));// caracol

		return shapes;
	}

	private static Vector<DragAndDropShape> buildLevel03() {
		Vector<DragAndDropShape> shapes = new Vector<DragAndDropShape>();
		shapes.add(new DragAndDropShape(R.drawable.l3_1_final, new DropTarget(R.drawable.l3_1_final_white, 338.919, 155.063,
				196 + 5, 223 + 5), R.raw.l3_1, true));// planta cruz
		shapes.add(new DragAndDropShape(R.drawable.l3_2_final, new DropTarget(R.drawable.l3_2_final_white, 289.064, 14.892,
				160 + 5, 118), R.raw.l3_2));// aerodactilo
		shapes.add(new DragAndDropShape(R.drawable.l3_3_final, new DropTarget(R.drawable.l3_3_final_white, 519.993, 261.103,
				126 + 5, 160), R.raw.l3_3));// triceratops
		shapes.add(new DragAndDropShape(R.drawable.l3_4_final, new DropTarget(R.drawable.l3_4_final_white, 360.413, 20.854,
				560 + 5, 278), R.raw.l3_4));// monta�as
		shapes.add(new DragAndDropShape(R.drawable.l3_5_final, new DropTarget(R.drawable.l3_5_final_white, 200.533, 407.562,
				272.523 + 5, 142.399), R.raw.l3_5));// cocodrilo deforme
		shapes.add(new DragAndDropShape(R.drawable.l3_6_final, new DropTarget(R.drawable.l3_6_final_white, 19.141, 128.744,
				331 + 5, 303 + 5), R.raw.l3_6));// dino morado
		shapes.add(new DragAndDropShape(R.drawable.l3_7_final, new DropTarget(R.drawable.l3_7_final_white, 551.773, 318.837,
				363.655 + 8, 279.62 + 5), R.raw.l3_7));// pleciosaurio
		shapes.add(new DragAndDropShape(R.drawable.l3_8_final, new DropTarget(R.drawable.l3_8_final_white, 685.213, 104.648,
				186.237 + 5, 212.573 + 5), R.raw.l3_8, true));// tiranosaurio
		shapes.add(new DragAndDropShape(R.drawable.l3_9_final, new DropTarget(R.drawable.l3_9_final_white, 15.333, 430.258,
				147 + 5, 188 + 5), R.raw.l3_9));// planta carnivora

		return shapes;
	}

	private static Vector<DragAndDropShape> buildLevel04() {
		Vector<DragAndDropShape> shapes = new Vector<DragAndDropShape>();
		shapes.add(new DragAndDropShape(R.drawable.lvl4_1, new DropTarget(R.drawable.lvl4_1_white, 471.012, 450.458,
				221.442 + 5, 131.998 + 5), R.raw.l4_1, true));// planta cruz
		shapes.add(new DragAndDropShape(R.drawable.lvl4_2, new DropTarget(R.drawable.lvl4_2_white, 218.555, 0,
				222.18 + 5, 213.298), R.raw.l4_2));// aerodactilo
		shapes.add(new DragAndDropShape(R.drawable.lvl4_3, new DropTarget(R.drawable.lvl4_3_white, 192.957, 231.482,
				244.013 + 5, 205.626), R.raw.l4_3));// triceratops
		shapes.add(new DragAndDropShape(R.drawable.lvl4_4, new DropTarget(R.drawable.lvl4_4_white, 741.221, 442.575,
				154.921 + 5, 130.1), R.raw.l4_4));// monta�as
		shapes.add(new DragAndDropShape(R.drawable.lvl4_5, new DropTarget(R.drawable.lvl4_5_white, 74.18, 475.07,
				88.806 + 5, 89.511), R.raw.l4_5));// cocodrilo deforme
		shapes.add(new DragAndDropShape(R.drawable.lvl4_6, new DropTarget(R.drawable.lvl4_6_white, 676.303, 435.335,
				89.666 + 5, 100.355 + 5), R.raw.l4_6));// dino morado
		shapes.add(new DragAndDropShape(R.drawable.lvl4_7, new DropTarget(R.drawable.lvl4_7_white, 223.519, 393.94,
				250.429 + 8, 188.941 + 5), R.raw.l4_7));// pleciosaurio
		shapes.add(new DragAndDropShape(R.drawable.lvl4_8, new DropTarget(R.drawable.lvl4_8_white, 36.897, 228.957,
				163.986 + 5, 231.747 + 5), R.raw.l4_8, true));// tiranosaurio
		shapes.add(new DragAndDropShape(R.drawable.lvl4_9, new DropTarget(R.drawable.lvl4_9_white, 504.202, 280.029,
				348.212 + 5, 149.926 + 5), R.raw.l4_9));// planta carnivora

		return shapes;
	}

	private static Vector<DragAndDropShape> buildLevel05() {
		Vector<DragAndDropShape> shapes = new Vector<DragAndDropShape>();
		shapes.add(new DragAndDropShape(R.drawable.l5_1_final, new DropTarget(R.drawable.l5_1_white, 65.122, 36.781, 56.968 + 5,
				117.114 + 5), R.raw.l5_1));// caballito de mar
		shapes.add(new DragAndDropShape(R.drawable.l5_2_final, new DropTarget(R.drawable.l5_2_white, 199.845, 22.486,
				234.533 + 5, 176.003), R.raw.l5_2));// serpiente
		shapes.add(new DragAndDropShape(R.drawable.l5_3_final, new DropTarget(R.drawable.l5_3_white, 670.408, 375.289,
				120.814 + 5, 91.043), R.raw.l5_3));// trinemoceratops
		shapes.add(new DragAndDropShape(R.drawable.l5_4_final, new DropTarget(R.drawable.l5_4_white, 10.593, 190.821,
				168.585 + 5, 161.608), R.raw.l5_4));// angel
		shapes.add(new DragAndDropShape(R.drawable.l5_5_final, new DropTarget(R.drawable.l5_5_white, 756.248, 89.747,
				148.019 + 5, 254.636), R.raw.l5_5));// pulpo
		shapes.add(new DragAndDropShape(R.drawable.l5_6_final, new DropTarget(R.drawable.l5_6_white, 364.015, 178.2, 267.574 + 5,
				261.351 + 5), R.raw.l5_6));// raya
		shapes.add(new DragAndDropShape(R.drawable.l5_7_final, new DropTarget(R.drawable.l5_7_white, 527.331, 459.082,
				134.312 + 8, 126.657 + 5), R.raw.l5_7));// cangrejo
		shapes.add(new DragAndDropShape(R.drawable.l5_8_final, new DropTarget(R.drawable.l5_8_white, 73.722, 323.855,
				335.482 + 5, 254.499 + 5), R.raw.l5_8));// tiburon
		shapes.add(new DragAndDropShape(R.drawable.l5_9_final, new DropTarget(R.drawable.l5_9_white, 565.953, 20.557,
				149.904 + 5, 172.309 + 5), R.raw.l5_9));// medusa

		return shapes;
	}

}
