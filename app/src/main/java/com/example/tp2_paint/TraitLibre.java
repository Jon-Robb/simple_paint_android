package com.example.tp2_paint;

import android.graphics.Canvas;

public class TraitLibre extends Formes implements Dessins{

    //On dessine un trait libre en suivant un path
    public TraitLibre(int pColor, int pLargeur) {
        super(pColor, pLargeur);
    }

    @Override
    public void dessiner(Canvas canvas) {
        canvas.drawPath(super.getPath(),super.getCrayon());
    }
}
