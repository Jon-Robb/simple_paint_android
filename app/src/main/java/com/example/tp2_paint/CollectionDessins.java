package com.example.tp2_paint;


import java.util.Collection;
import java.util.Vector;

public class CollectionDessins {

    //La classe CollectionDessins possede deux collections
    //La collection principale sert à dessiner tous les dessins qui existent
    //la collection mirroir sert à enlever/redonner un dessin à la collection principale
    //lorsque l'usager utilisera les fonctions undo/redo
    private Vector<Dessins> collectionPrincipale;
    private Vector<Dessins> collectionMirroir;


    public CollectionDessins(){
        this.collectionPrincipale = new Vector<>();
        this.collectionMirroir = new Vector<>();

    }

    //Retourner le dernier dessin ajouté dans la collection principale
    public Dessins getLastDessin(){
        return this.collectionPrincipale.lastElement();
    }

    //Retourner le dernier dessin ajouté dans la collection mirroir
   public Dessins getLastDessinMirroir(){
        return this.collectionMirroir.lastElement();
   }

   public Collection<Dessins> getCollectionPrincipale() {
        return collectionPrincipale;
    }

    public Collection<Dessins> getCollectionMirroir() {
        return collectionMirroir;
    }

}
