package com.e_atenea.workshop.carlos.mandelbrot10;

import android.graphics.Matrix;
import android.graphics.RectF;

public class Target {

	private float span, x, y;
	private int width, height;

	public Target(int width, int height) {
		this.width = width;
		this.height = height;
		x = -0.5f;
		y = 0f;
		span = 3f;
	}

	public void setTarget(float x, float y){
		setX(x);
		setY(y);
	}

	public float getSpan() {
		return span;
	}

	public void setSpan(float span) {
		this.span = span;
	}

	public void zoom(){
		span /= 1.5f;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Matrix getMatrix(){
		Matrix matrix = new Matrix();
		matrix.setRectToRect(getSourceRect(), getDestRect(), Matrix.ScaleToFit.FILL);
		matrix.postScale(1, -1);
		return matrix;
	}

	private RectF getSourceRect(){
		return new RectF(0, 0, width, height);
	}

	private RectF getDestRect(){
		float ar = (float)width/((float)height);
		return new RectF(x-span/2, -(y+span/(2*ar)), x+span/2, -(y-span/(2*ar)));
	}
}
