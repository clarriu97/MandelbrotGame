package com.e_atenea.workshop.carlos.mandelbrot10;

import java.util.ArrayList;
import java.util.List;

public class Palette {
    public static final int WHITE = 0xFFFFFFFF;
    public static final int BLACK = 0xFF000000;
    public static final int RED = 0xFFFF0000;
    public static final int GREEN = 0xFF00FF00;
    public static final int BLUE = 0xFF0000FF;


    private List<Integer> colors;

    // Look-up data (redundante, por velocidad)
    private int[] red, green, blue;     // Componentes RGB
    private float dx;                   // Longitud de intervalo de color

    public Palette() {
        colors = new ArrayList<>();
    }


    public final void addColor(int color) { colors.add(color); fix(); }

    // Entrega el color de la paleta por interpolación lineal. x -> [0,1)
    public int getColor(float x) {
        int c = (int) Math.floor(x / dx);   // c = color de la izda, c+1 color de la dcha
        if (c >= red.length - 1) { c = red.length - 2; }    // Por si x >= 1.0
        float k = (x - c * dx) / dx;        // k � [0,1), entre los colores c y c+1
        int r = interpolateColor(red, c, k);
        int g = interpolateColor(green, c, k);
        int b = interpolateColor(blue, c, k);
        return 0xFF000000 + (r << 16) + (g << 8) + b;
    }

    private int interpolateColor(int[] component, int c, float k) {
        int a = component[c];
        int b = component[c+1];
        return (int) (k * (b - a) + a);
    }

    // ------------------------------------------------------------------------------

    private final void fix() {
        int numColors = colors.size();
        // Componentes RGB
        red = new int[numColors];
        green = new int[numColors];
        blue = new int[numColors];
        for (int i = 0; i < numColors; i++) {
            int c = colors.get(i);
            red[i] = (c & 0x00FF0000) >> 16;
            green[i] = (c & 0x0000FF00) >> 8;
            blue[i] = (c & 0x000000FF);
        }
        // Longitud del intervalo entre colores
        dx = 1.0f / (numColors - 1);
    }

    // ---------------------------------------------------------------------------------
    // PALETAS PREDEFINIDAS
    // ---------------------------------------------------------------------------------

    public static class WhiteBlack extends Palette {
        public WhiteBlack() {
            addColor(WHITE);
            addColor(BLACK);
        }
    }

    public static class BlackWhiteBlack extends Palette {
        public BlackWhiteBlack() {
            addColor(BLACK);
            addColor(WHITE);
            addColor(BLACK);
        }
    }

    public static class BlackRGBBlack extends Palette {
        public BlackRGBBlack() {
            addColor(BLACK);
            addColor(RED);
            addColor(GREEN);
            addColor(BLUE);
            addColor(BLACK);
        }
    }

}
