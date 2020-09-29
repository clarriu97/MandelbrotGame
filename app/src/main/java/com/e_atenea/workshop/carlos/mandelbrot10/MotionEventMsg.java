package com.e_atenea.workshop.carlos.mandelbrot10;

import android.view.MotionEvent;

public class MotionEventMsg extends MandelbrotMsg {

	private final MotionEvent event;

	public MotionEventMsg(MotionEvent event) {
		this.event = event;
	}

	@Override
	public void run() {
		if (listener == null){
			return;
		}
		listener.onMotionEvent(event);
	}
}
