package com.e_atenea.workshop.carlos.mandelbrot10;

public class ZoomMsg extends MandelbrotMsg {
	@Override
	public void run() {
		if (listener == null){
			return;
		}
		listener.onZoom();
	}
}
