package com.e_atenea.workshop.carlos.mandelbrot10;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private final SurfaceHolder holder;			// Alias, objeto al que se delega la sincronizacion

	public GameView(Context context) {
		super(context);
		holder = getHolder();
		holder.setFormat(PixelFormat.RGBA_8888);
	}


	public boolean transfer(GameViewDrawer drawer) {
		Canvas canvas = null;
		
		if (!holder.getSurface().isValid()) { sleep(20); return false; }
		try {
			canvas = holder.lockCanvas();
			synchronized (holder) {
				if (canvas != null) { drawer.onDraw(canvas); }
			}
		} finally {
			if (canvas != null) { holder.unlockCanvasAndPost(canvas); }
		}
		return true;
	}


	// -------------------------------------------------------------------------

	private void sleep(long ms) {
		try { Thread.sleep(ms); } catch (InterruptedException e) { }
	}


}
