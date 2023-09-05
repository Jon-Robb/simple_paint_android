package com.example.tp2_paint;

import android.graphics.Canvas;

public class Triangle extends Formes implements Dessins{

    //La logique du triangle se trouve dans le OnTouchListener du MainActivity
    public Triangle(int pColor, int pLargeur) {
        super(pColor, pLargeur);
    }

    @Override
    public void dessiner(Canvas canvas) {
        canvas.drawPath(super.getPath(), super.getCrayon());
    }
}
