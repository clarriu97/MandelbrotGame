package com.e_atenea.workshop.carlos.mandelbrot10;

public class MandelbrotSet {
    private int maxIter;

    public MandelbrotSet() {
        maxIter = 50;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }


    public double iterate(double x, double y) {
        int i;
        double cx = x;
        double cy = y;
        double t;
        for (i = 0; i < maxIter; i++) {
            // z = z^2 + c
            t = x * x - y * y + cx;
            y = 2 * x * y + cy;
            x = t;
            // Radio mayor que 2?
            if (x * x + y * y >= 4.0) { return (double) i / (double) maxIter; }
        }
        return (double) i / (double) maxIter;
    }

    public void incDepth() {
        maxIter = (int) (maxIter * 1.5);
    }
}
