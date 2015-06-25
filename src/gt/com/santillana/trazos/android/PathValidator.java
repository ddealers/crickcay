package gt.com.santillana.trazos.android;

import gt.com.santillana.trazos.android.config.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Pablo Rubio Calcula la presici�n del un trazo conrespecto a una l�nea
 *         guia en un canvas.
 */
public class PathValidator {

	private static final int SAMPLES_QUANTITY = 20;

	/**
	 * El objeto Paint que contiene al canvas.
	 */
	private Paint mPaint;

	/**
	 * El objeto canvas.
	 */
	private Canvas canvas;

	/**
	 * La información de la línea guía.
	 */
	private List<Path> guidePaths;

	private double currentValidity;

	private int bitmapWidth;

	private int bitmapHeight;

	/**
	 * Constructor del vaidador.
	 * 
	 * @param paint
	 *            el objeto paint que contiene al canvas.
	 * @param canvas
	 *            el canvas donde est�n los trazos.
	 */
	public PathValidator(Paint paint, Canvas canvas, int bitmapWidth,
			int bitmapHeight) {
		setPaint(paint, canvas);
		setBitmapSize(bitmapWidth, bitmapHeight);
		this.guidePaths = getGuidePath();
	}

	public PathValidator(Paint paint, int bitmapWidth, int bitmapHeight) {
		setPaint(paint);
		setBitmapSize(bitmapWidth, bitmapHeight);
		this.guidePaths = getGuidePath();
	}

	/**
	 * Constructor del vaidador. Hasta que se definan las variables canvas y
	 * mPaint no va a pintar nada. No es necesario pintar para validar los
	 * datos, pintar es solo para debugging.
	 */
	public PathValidator() {
		this.guidePaths = getGuidePath();
	}

	/**
	 * Setter para las variables mPaint y canvas.
	 * 
	 * @param mPaint
	 *            el objeto paint del trazo
	 * @param canvas
	 *            el canvas sobre el cual se est� trazando
	 */
	public void setPaint(Paint mPaint, Canvas canvas) {
		this.mPaint = mPaint;
		this.canvas = canvas;
	}

	public void setPaint(Paint mPaint) {
		this.mPaint = mPaint;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	/**
	 * Getter para la variable mPaint.
	 * 
	 * @return la variable mPain actual.
	 */
	public Paint getPaint() {
		return mPaint;
	}

	/**
	 * Getter para la variable canvas.
	 * 
	 * @return la variable canvas actual.
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	/**
	 * Dibuja los puntos de muestras para el trazo indicado.
	 * 
	 * @param samples
	 *            las muestras a pintar.
	 */
	public void drawSamples(float[][] samples) {
		if (canvas == null || mPaint == null) {
			return;
		}

		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(0x8800ffff);
		mPaint.setStrokeWidth(2);

		for (int i = 0; i < samples[0].length; i++) {
			canvas.drawCircle(samples[0][i], samples[1][i], 3, mPaint);
		}
	}

	/**
	 * Validar el trazo del usuario con respecto a la guia. Se construye el
	 * promedio de la longitud de las linea de medida entre el trazo del usuario
	 * y la linea guia y se normaliza el valor con la Distancia Maxima del
	 * canvas.
	 * 
	 * @param graphics
	 *            lista de trazos del usuario.
	 * @return un valor entre 0 y 100 que determina el nivel del ERROR. 0% para
	 *         un trazo perfecto y 100% porciento es el mayor error obtenible.
	 */
	public float calcScore(List<Path> userPaths) {
		
		int totalCountGuide = 0;//numero de secciones en cada trazo despues dividirlo dentro de 100
		int totalCountUser = 0;//numero de secciones en cada trazo despues dividirlo dentro de 100 (del usuario)
		int totalBandera = 0;//los puntos del uusario que se acercaron a una soluci�n.
		int totalNoBandera = 0;//los puntos del uusario que no se acercaron a una soluci�n. (solo es para debug)
		int penalizacion = 0;
		
		float[] punto1 = new float[2];
		float[] punto2 = new float[2];
		float[] tangent = new float[2];
		double v = 0;
		double x1, x2, y1, y2 = 0;
		float speedUser;
		float distanceUser;
		int countUser;
		PathMeasure measureUser = new PathMeasure();
		PathMeasure measureGuide = new PathMeasure();;
		float distanceGuide = 0;
		float speedGuide;
		boolean[] banderasUser;
		
		int countGuide = 100;
		for(int indexPath = 0; indexPath < guidePaths.size(); indexPath++) {
			measureGuide.setPath(guidePaths.get(indexPath), false);
			
			speedGuide = measureGuide.getLength() / countGuide;
			distanceGuide = 0;
			GuidePoint[] points = new GuidePoint[countGuide];
	
			measureUser.setPath(userPaths.get(indexPath), false);
			countUser = (int) (measureUser.getLength() / speedGuide);
			speedUser = measureUser.getLength() / countUser;
			distanceUser = 0;
	
			banderasUser = new boolean[countUser];
	
			// Create guide points
			for (int i = 0; i < countGuide; i++) {
				measureGuide.getPosTan(distanceGuide, punto2, tangent);//
				
				points[i] = new GuidePoint();
				points[i].x = punto2[0];
				points[i].y = punto2[1];
				
				distanceGuide += speedGuide;
			}
	
			for (int a = 0; a < countUser; a++) {
				// Get point from user
				measureUser.getPosTan(distanceUser, punto1, null);
	
				x1 = punto1[0];
				y1 = punto1[1];
	
				distanceUser += speedUser;
				distanceGuide = 0;
	
				for (int i = 0; i < countGuide; i++) {
					
					if(points[i].checked) {
						continue;
					}
					
					x2 = points[i].x;
					y2 = points[i].y;
	
					v = Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2);
					if (v < 0)
						v = (v * -1);
					v = Math.sqrt(v);
					
//					Log.i("calc", "v : " + v + "\npoint : " + 
//							points[i].checked + " " + points[i].x + " " + points[i].y);
	
					if (v < 12) {
						//calculo del angulo entre los puntos
						if(angle(x1, x2, y1, y2) <= 5){
							banderasUser[a] = true;
							points[i].checked = true;
						}

						break;	
						
					}
					else
						penalizacion+=0.5;
				}
			}
	
			// Count flags
			for (int a = 0; a < countUser; a++) {
				if (banderasUser[a])
					totalBandera++;
				else
					totalNoBandera++;
			}
			
			totalCountGuide += countGuide;
			totalCountUser += countUser;
		}

		Log.i("Calc Score",
				"************************************************************************************");
		Log.i("Calc Score", "Total de Guias: " + totalCountGuide);
		Log.i("Calc Score", "Total de Puntos: " + totalCountUser);
		Log.i("Calc Score", "Total Banderas: " + totalBandera);
		Log.i("Calc Score", "Total NO Banderas: " + totalNoBandera);
		Log.i("Calc Score", "Total Penalizacion: " + penalizacion);

		float total = 0;
		if (totalCountUser > totalCountGuide) {
			total = (totalBandera * 100) / totalCountUser;
//			total = (total * totalCountGuide) / totalCountUser;
		} else {
			total = (totalBandera * 100) / totalCountGuide;
		}

		if (totalBandera <= 25) {
			total = 0;
		}
		total = total - penalizacion;

		currentValidity = total;
		Log.i("Calc Score", "El efectividad es: " + total);

//		total = 100;
		return total;
	}
	
	public double angle(double x1, double x2, double y1, double y2)
	{
		double dX = Math.abs(x2 - x1);
		double dY = Math.abs(y2 - y1); 
		return Math.atan2(dX, dY);
	}

	public void setBitmapSize(int width, int height) {
		this.bitmapWidth = width;
		this.bitmapHeight = height;
	}

	/**
	 * Genera el trazo de guia. Ahorita solo hago una funcion de Seno(x) para
	 * trazar una curva pero en un futuro aqu� podemos hacer que carge un
	 * archivo o algo asi y se usar�a esta funci�n para amarrar los niveles a un
	 * módulo creador de niveles.
	 * 
	 * @return
	 */
	public List<Path> getGuidePath() {
		// double[][] dots =
		// { { 0.440911044263, 0.547811059907834 },
		// { 0.457670820799312, 0.476958525345622 },
		// { 0.487322733132789, 0.449308755760369 },
		// { 0.5118177911474, 0.445852534562212 },
		// { 0.555651052857757, 0.45794930875576 },
		// { 0.585302965191233, 0.501152073732719 },
		// { 0.598195100988397, 0.532258064516129 },
		// { 0.61495487752471, 0.482142857142857 },
		// { 0.644606789858186, 0.452764976958525 },
		// { 0.678126342930812, 0.442396313364055 },
		// { 0.714224323162871, 0.454493087557604 },
		// { 0.74645466265578, 0.494239631336406 },
		// { 0.758057584873227, 0.530529953917051 },
		// { 0.77481736140954, 0.48905529953917 },
		// { 0.8031800601633, 0.456221198156682 },
		// { 0.839278040395359, 0.442396313364055 },
		// { 0.888268156424581, 0.466589861751152 },
		// { 0.914052428018908, 0.501152073732719 },
		// { 0.923076923076923, 0.551267281105991 }, };
		// guidePath = new Path();
		// /*
		// * int guideWidth = 40; Path guidePath = new Path(); float centerY =
		// * canvasHeight / 2; float step = (canvasWidth - 20) / guideWidth;
		// float
		// * walk = 10 + step;
		// *
		// * guidePath.moveTo(10, centerY); for (int i = 1; i < guideWidth; i++)
		// {
		// * guidePath.lineTo(walk, centerY + android.util.FloatMath.sin((float)
		// (i *
		// * 0.2)) 50); walk += step; }
		// */
		//
		// guidePath.moveTo((int) (dots[0][0] * bitmapWidth),
		// (int) (dots[0][1] * bitmapHeight));
		// for (int i = 1; i < dots.length; i++) {
		// guidePath.lineTo((int) (dots[i][0] * bitmapWidth),
		// (int) (dots[i][1] * bitmapHeight));
		// }

		return guidePaths;
	}

	public void loadGuidePathId(Context context, int screenSolutionId) {

		StringBuilder json = new StringBuilder();
		
		InputStream inputStream = context.getResources().openRawResource(screenSolutionId);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		try {
			line = reader.readLine();
			while (line != null) {  
				json.append(line);
				json.append("\n");
				line = reader.readLine(); 
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);

		List<List<Point>> dots;
		try {
			dots = mapper.readValue(json.toString(), new TypeReference< List<List<Point>> >() {});
		} catch (Exception e) {
			e.printStackTrace();
			dots = new ArrayList<List<Point>>();
		}
		guidePaths = new ArrayList<Path>();

		for(List<Point> points : dots) {
			Path guidePath = new Path();
			guidePaths.add(guidePath);
			
			float mX = points.get(0).getX() * bitmapWidth;
			float mY = points.get(0).getY() * bitmapHeight;
			guidePath.moveTo((int)mX, (int)mY);
			for(int i = 1; i < points.size(); i++) {
				Point point = points.get(i); 
				float x = point.getX() * bitmapWidth;
				float y = point.getY() * bitmapHeight;
				guidePath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);

				mX = x;
				mY = y;
			}
		}

	}

	public static class GuidePoint {
		public boolean checked;
		public double x;
		public double y;
	}
}