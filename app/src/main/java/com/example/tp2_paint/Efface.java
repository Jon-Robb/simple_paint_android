package com.example.tp2_paint;

import android.graphics.Canvas;

public class Efface extends Formes implements Dessins {

    //Logique pareille à celle du traitLibre
    public Efface(int pColor, int pLargeur) {
        super(pColor, pLargeur);
    }

    @Override
    public void dessiner(Canvas canvas) {
        canvas.drawPath(super.getPath(),super.getCrayon());
    }
}
