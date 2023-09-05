package com.example.tp2_paint;

import android.graphics.Canvas;

public class Cercle extends Formes implements Dessins {

    //Les variables x et y représentent le point d'ancrage du cercle
    //Le radius (rayon) donnera la grosseur du cercle
    private float x,y, rayon;

    public Cercle(int pColor, int pLargeur) {
        super(pColor, pLargeur);
    }

    @Override
    public void dessiner(Canvas canvas) {
        canvas.drawCircle(this.x, this.y, this.rayon, super.getCrayon());
    }

    public void setRadius(float radius) {
        this.rayon = radius;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
