package com.e_atenea.workshop.carlos.mandelbrot10;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

public class MandelbrotGame extends Game implements MandelbrotListener {

	private MandelbrotSet mandelbrotSet;
	private Bitmap buffer;
	private Paint paint;
	private Canvas bufferCanvas;
	private int width, height;
	private short[] xs, ys;
	private int currentPoint;
	private long lastTime;
	private Target target;
	private Matrix matrix;
	private float[] point;
	private Palette palette;

	public MandelbrotGame(GameView gameView) {
		super(gameView);
		mandelbrotSet = new MandelbrotSet();
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		buffer = null;
		lastTime = System.currentTimeMillis();
		palette = new Palette.BlackRGBBlack();
	}

	@Override
	public void onUpdate() {
		if (buffer == null){return;}
		lastTime = System.currentTimeMillis();
		while (isUpdating()) {
			int xpixel = xs[currentPoint];
			int ypixel = ys[currentPoint];
			point[0] = xpixel;
			point[1] = ypixel;
			matrix.mapPoints(point);
			float conv = (float) mandelbrotSet.iterate(point[0], point[1]);
			/*int white = (int) (conv * 255);
			//paint.setColor(conv==1.0?0xffffffff:0xff000000);
			int color = 0xff000000 + white * 0x10101;*/
			int color = palette.getColor(conv);
			paint.setColor(color);
			bufferCanvas.drawPoint(xpixel, ypixel, paint);
			currentPoint++;
			if (currentPoint == width * height) {
				currentPoint = 0;
			}
		}
	}

	@Override
	public void onDraw(Canvas c) {
		if (buffer == null){
			// Lazy init
			width = c.getWidth();
			height = c.getHeight();
			buffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			bufferCanvas = new Canvas(buffer);
			createXY();
			target = new Target(width, height);
			init();
		}

		c.drawBitmap(buffer, 0, 0, null);
	}


	private void createXY() {
		Random random = new Random();
		int l = width*height;
		xs = new short[l];
		ys = new short[l];
		int i = 0;
		for (short y = 0; y < height; y++){
			for (short x = 0; x < width; x++){
				xs[i] = x;
				ys[i] = y;
				i++;
			}
		}
		//Desordenar
		short aux;
		int j;
		for (int p = 0; p < l-1; p++){
			i = random.nextInt(l-p);
			j = l-p-1;
			aux = xs[i]; xs[i] = xs[j]; xs[j] = aux;
			aux = ys[i]; ys[i] = ys[j]; ys[j] = aux;
		}
		currentPoint = 0;
	}

	public boolean isUpdating(){
		long now = System.currentTimeMillis();
		long elapsed = now - lastTime;

		return elapsed < 40;
	}

	@Override
	public void onMotionEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		point[0] = x;
		point[1] = y;
		matrix.mapPoints(point);
		target.setTarget(point[0], point[1]);
		target.zoom();
		init();
	}

	@Override
	public void onZoom() {
		mandelbrotSet.incDepth();
		init();
	}

	private void init(){
		bufferCanvas.drawColor(0xff0000ff);
		matrix = target.getMatrix();
		currentPoint = 0;
		point = new float[2];
	}

}
