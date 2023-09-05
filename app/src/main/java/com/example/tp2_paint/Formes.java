package com.example.tp2_paint;

import android.graphics.Paint;
import android.graphics.Path;

public class Formes {

    //Comme on avait vu ensemble, la largeur n'est jamais vraiment utilisée puisqu'elle est stockée
    //dans le crayon Paint, je la laisse quand même pour la clarté de la correction
    //J'initialise un crayon Paint directement dans la classe,
    private int color;
    private int largeur;
    private Path path;
    private final Paint crayon = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Formes(int pColor, int pLargeur){
        this.color = pColor;
        this.largeur = pLargeur;
        this.crayon.setStyle(Paint.Style.STROKE);
        this.crayon.setColor(pColor);
        this.crayon.setStrokeWidth(pLargeur);
    }

    public void creerPoint(float x, float y){
        this.path = new Path();
        this.path.moveTo(x, y);
    }

    public void ajouterPoint(float x, float y){
        this.path.lineTo(x,y);
    }

    public Path getPath() {
        return path;
    }

    public Paint getCrayon() {
        return crayon;
    }

    public void setColor(int color) {
        this.crayon.setColor(color);
    }


}
