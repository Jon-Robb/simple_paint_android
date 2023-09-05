package com.example.tp2_paint;

import android.graphics.Canvas;

public class Rectangle extends Formes implements Dessins {

    private float sommetX, sommetY, largeur, hauteur;

    public Rectangle(int pColor, int pLargeur) {
        super(pColor, pLargeur);


    }

    @Override
    public void dessiner(Canvas canvas) {

        canvas.drawRect(this.sommetX, this.sommetY, this.largeur, this.hauteur, super.getCrayon());
    }

    public void setSommetX(float sommetX) {
        this.sommetX = sommetX;
    }

    public void setSommetY(float sommetY) {
        this.sommetY = sommetY;
    }

    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(float hauteur) {
        this.hauteur = hauteur;
    }
}
