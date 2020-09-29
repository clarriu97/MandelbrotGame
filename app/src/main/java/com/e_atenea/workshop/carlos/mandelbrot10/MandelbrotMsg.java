package com.e_atenea.workshop.carlos.mandelbrot10;

public abstract class MandelbrotMsg extends GameMsg {

	//Agregation
	protected MandelbrotListener listener;

	public MandelbrotMsg(){
		listener = null;
	}

	public void setListener(MandelbrotListener listener){
		this.listener = listener;
	}

}
