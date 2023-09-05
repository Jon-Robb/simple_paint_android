package com.example.tp2_paint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    //Écouteurs
    EcouteurOutils ecO;
    EcouteurCouleur ecC;
    EcouteurSurface ecS;

    //Layouts
    SurfaceDessin surface;
    ConstraintLayout conteneur;
    LinearLayout conteneurOutil, conteneurCouleur;

    //Outils
    ImageView imgTraitLibre, imgLargeurTrait, imgEfface, imgTriangle, imgRectangle,
            imgCercle, imgRemplir, imgUndo, imgRedo, imgSauvegarder, imgPipette;

    //Couleurs
    Button btnBleu, btnCorail, btnCreme, btnEmeraude, btnJaune, btnRose, btnRouge,
            btnTangerine, btnVert, btnViolet;


    //variables nécéssaires aux logiques et aux objets temporaires
    CollectionDessins collectionDessins;
    int couleurActuelle = Color.BLACK;
    int largeurActuelle = 10;
    View objectActuel;
    int couleurBackground = Color.WHITE;
    Bitmap bitmapImage;
    DialogueLargeur dl;

    //Méthodes nécéssaires à l'appel de dialogue. (setLargeur et getDialog)
    public DialogueLargeur getDl() {
        return dl;
    }
    public void setLargeurActuelle(int largeurActuelle) {
        this.largeurActuelle = largeurActuelle;
    }

    //initialisation des variables pour les dessins et leur pendant temporaire
    TraitLibre trait, traitTemp;
    Cercle cercle, cercleTemp;
    Triangle triangle, triangleTemp, traitTriangleTemp;
    Rectangle rectangle, rectangleTemp;
    Efface efface, effaceTemp;
    float cercleX, cercleY, premierSommetX, premierSommetY, deuxiemeSommetX, deuxiemeSommetY,
            troisiemeSommetX, troisiemeSommetY, rectangleSommetX, rectangleSommetY;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Queries pour aller chercher nos widgets
        conteneur = findViewById(R.id.conteneur);
        imgTraitLibre = findViewById(R.id.imgTraitLibre);
        imgLargeurTrait = findViewById(R.id.imgLargeurTrait);
        imgEfface = findViewById(R.id.imgEfface);
        imgTriangle = findViewById(R.id.imgTriangle);
        imgRectangle = findViewById(R.id.imgRectangle);
        imgCercle = findViewById(R.id.imgCercle);
        imgRemplir = findViewById(R.id.imgRemplir);
        imgUndo = findViewById(R.id.imgUndo);
        imgRedo = findViewById(R.id.imgRedo);
        imgSauvegarder = findViewById(R.id.imgSauvegarder);
        imgPipette = findViewById(R.id.imgPipette);
        btnBleu = findViewById(R.id.btnBleu);
        btnCorail = findViewById(R.id.btnCorail);
        btnCreme = findViewById(R.id.btnCreme);
        btnEmeraude = findViewById(R.id.btnEmeraude);
        btnJaune = findViewById(R.id.btnJaune);
        btnRose = findViewById(R.id.btnRose);
        btnRouge = findViewById(R.id.btnRouge);
        btnTangerine = findViewById(R.id.btnTangerine);
        btnVert = findViewById(R.id.btnVert);
        btnViolet = findViewById(R.id.btnViolet);
        conteneurCouleur = findViewById(R.id.conteneurCouleur);
        conteneurOutil = findViewById(R.id.conteneurOutil);

        collectionDessins = new CollectionDessins();
        dl = new DialogueLargeur(MainActivity.this);


        //création des écouteurs
        ecC = new EcouteurCouleur();
        ecO = new EcouteurOutils();
        ecS = new EcouteurSurface();

        //On ajoute un écouteur à tous les enfants du conteneur de couleurs
        for (int i = 0; i <= conteneurCouleur.getChildCount(); i++){
            if (conteneurCouleur.getChildAt(i) instanceof Button){
                conteneurCouleur.getChildAt(i).setOnClickListener(ecC);
            }
        }
        //On ajoute un écouteur à tous les enfants du conteneur d'outils
        for (int i = 0; i <= conteneurOutil.getChildCount(); i++){
            if (conteneurOutil.getChildAt(i) instanceof ImageView){
                conteneurOutil.getChildAt(i).setOnClickListener(ecO);
            }
        }

        //On créé la surface de dessin et on lui ajoute un écouteur
        surface = new SurfaceDessin(this);
        surface.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        conteneur.addView(surface);
        surface.setOnTouchListener(ecS);
        surface.setBackgroundColor(couleurBackground);

    }

    private class SurfaceDessin extends View{

        public SurfaceDessin(Context context) {
            super(context);
        }

        @SuppressLint("DrawAllocation")
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //Si le vecteur de dessins n'est pas vide, on dessine tout ce qu'il contient
           if (!collectionDessins.getCollectionPrincipale().isEmpty()){

               //Si le vecteur contient une efface, on change la couleur de celle-ci pour la couleur du background
                for (Dessins d : collectionDessins.getCollectionPrincipale()){
                    d.dessiner(canvas);
                    //Si l objet est une efface, on remet ajuste sa couleur a la couleur du background
                    if (d instanceof Efface){
                        ((Efface) d).setColor(couleurBackground);
                        d.dessiner(canvas);
                    }
                }
            }

           //Ici on dessine les objets temporaires s'ils existent
           if (traitTemp != null){
               traitTemp.dessiner(canvas);
           }
           else if (effaceTemp != null){
               effaceTemp.dessiner(canvas);
           }
           else if (rectangleTemp != null){
               rectangleTemp.dessiner(canvas);
           }
           else if (cercleTemp != null){
               cercleTemp.dessiner(canvas);
           }
           else if (triangleTemp != null){
               triangleTemp.dessiner(canvas);
           }
           else if (traitTriangleTemp != null){
               traitTriangleTemp.dessiner(canvas);
           }
        }

        //on va chercher le bit grâce à la fonction d'Éric
        public Bitmap getBitmapImage(){
            this.buildDrawingCache();
            bitmapImage = Bitmap.createBitmap(this.getDrawingCache());
            this.destroyDrawingCache();
            return  bitmapImage;
        }

    }

    private class EcouteurCouleur implements View.OnClickListener{

        @Override
        public void onClick(View source) {


            //On va chercher la couleur de background des boutons pour l'attribuée à la  variable couleurActuelle
            Drawable color = source.getBackground();
            ColorDrawable couleur = (ColorDrawable) color;
            if (source.getBackground() == couleur){
                couleurActuelle = couleur.getColor();
                conteneurOutil.setBackground(couleur);
            }
        }
    }

    private class EcouteurOutils implements View.OnClickListener{

        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View source){

            //L objet actuel devient la source sur laquelle l usager a clique
            objectActuel = source;

            //Si l'usager appuie sur l'outil largeur de trait, on fait afficher la boite de dialogue
            //On attribue la largeur du trait au choix
            if (source == imgLargeurTrait){
                dl.show();
                objectActuel = imgTraitLibre;
            }
            //Si l outil undo est choisi, si la collection principale n est pas vide, on ajoute le dernier
            //dessins a une collection mirroir et on detruit ce dessin
            else if(source == imgUndo){
                if (!collectionDessins.getCollectionPrincipale().isEmpty()){
                    collectionDessins.getCollectionMirroir().add(collectionDessins.getLastDessin());
                    collectionDessins.getCollectionPrincipale().remove(collectionDessins.getLastDessin());
                    surface.invalidate();
                }
            }
            //Si l outil redo est choisi, si la collection mirroir n est pas vide, on ajoute le dernier
            //dessins de la collection mirroir a la collection principale et on detruit ce dessin
            else if(source == imgRedo){
                if (!collectionDessins.getCollectionMirroir().isEmpty()){
                    collectionDessins.getCollectionPrincipale().add(collectionDessins.getLastDessinMirroir());
                    collectionDessins.getCollectionMirroir().remove(collectionDessins.getLastDessin());
                    surface.invalidate();
                }
            }
        }
    }

    private class EcouteurSurface implements View.OnTouchListener{

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View source, MotionEvent motionEvent) {

            //variables x et y pour la position du motion event
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                //Le principe général des objets dans les onTouch est d'attriubé une position lors du premier 'clic' et de changer
//                ces attributs selon la position actuelle du MOVE
//                On créé des variables temporaires pour que celles ci soit dessiner sur le champ
                    if (objectActuel == imgTraitLibre){

                        //On cree le point d ancrage du path du trait
                        trait = new TraitLibre(couleurActuelle, largeurActuelle);
                        traitTemp = trait;
                        trait.creerPoint(x, y);
                    }

                    else if (objectActuel == imgCercle){

                        //On cree le point d ancrage du cercle qui se trouve à être son point centre
                        //on garde aussi ce point dans les variables qui serviront au calcul
                        cercle = new Cercle(couleurActuelle, largeurActuelle);
                        cercleTemp = cercle;
                        cercle.setX(x);
                        cercle.setY(y);
                        cercleX = x;
                        cercleY = y;
                    }

                    else if (objectActuel == imgTriangle){

                        //si le triangle temp n existe pas on le prend en note les pos x y pour le premier sommet
                        if (triangleTemp == null){
                            premierSommetX = x;
                            premierSommetY = y;
                        }

                        //Si le triangle temp existe on relie tous les points ensemble et on assigne sa valeur a traitTriangleTemp
                       else{
                           troisiemeSommetX = x;
                           troisiemeSommetY = y;
                           triangleTemp.ajouterPoint(troisiemeSommetX,troisiemeSommetY);
                           triangleTemp.ajouterPoint(premierSommetX, premierSommetY);
                           traitTriangleTemp = triangleTemp;
                       }
                    }

                    else if (objectActuel == imgRectangle){

                        //On cree le point d ancrage du rectangle, qui est son sommet haut gauche
                        rectangle = new Rectangle(couleurActuelle, largeurActuelle);
                        rectangleTemp = rectangle;
                        rectangleSommetX = x;
                        rectangleSommetY = y;
                    }

                    else if (objectActuel == imgRemplir){
                    // le pot de peinture sert a changer la couleur du fond
                        surface.setBackgroundColor(couleurActuelle);
                        couleurBackground = couleurActuelle;
                    }

                    else if (objectActuel == imgEfface){

                        // comme le trait
                        efface = new Efface(couleurActuelle, largeurActuelle);
                        efface.getCrayon().setColor(couleurBackground);
                        effaceTemp = efface;
                        efface.creerPoint(x, y);
                    }

                    else if (objectActuel == imgPipette) {

                        //on utilise le bitMap obtenu grâce à la fonction d'Éric
                        //et on extrait la couleur du pixel de ce bitMap pour l'attribué à la variable couleurActuelle
                        Bitmap bit = surface.getBitmapImage();
                        couleurActuelle = bit.getPixel((int) x, (int) y);
                    }
                surface.invalidate();
            }

//            Ajustement des attributs d'objets par rapport aux positions actuelles du 'curseur'
            else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){

                if (objectActuel == imgTraitLibre){

                    trait.ajouterPoint(x,y);
                }

                else if (objectActuel == imgCercle){

                    //On calcule la longueur du rayon en soustrayant les x y actuel a ceux
                    // utilisee au point d ancrage
                    float radius;
                    radius =  Math.abs(x - cercleX + y - cercleY);
                    cercle.setRadius(radius);
                }

                else if (objectActuel == imgTriangle){

                    // si le trait traitTriangleTemp n existe pas, on cree le triangleTemp et on prend en note le deuxieme sommet
                    if (traitTriangleTemp == null){
                        deuxiemeSommetX = x;
                        deuxiemeSommetY = y;
                        triangleTemp = new Triangle(couleurActuelle,largeurActuelle);
                        triangleTemp.creerPoint(premierSommetX, premierSommetY);
                        triangleTemp.ajouterPoint(deuxiemeSommetX, deuxiemeSommetY);
                    }
                }

                else if (objectActuel == imgRectangle){

                    //On utilise les données recueillies au DOWN et on les attribuent au sommet haut gauche
                    //Ensuite, grâce aux variables x,y, on ajuste la largeur et la hauteur lors du MOVE
                    rectangle.setSommetX(rectangleSommetX);
                    rectangle.setSommetY(rectangleSommetY);
                   rectangle.setLargeur(x);
                   rectangle.setHauteur(y);
                }

                else if (objectActuel == imgEfface){

                    efface.ajouterPoint(x,y);
                }
                surface.invalidate();
            }

//            Au UP, on ajoute les desssins au vecteur de dessins
//            On remet les variables temporaires à null
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP){

                if (objectActuel == imgTraitLibre){

                  traitTemp = null;
                  collectionDessins.getCollectionPrincipale().add(trait);
                }

                else if (objectActuel == imgCercle){

                    cercleTemp = null;
                    collectionDessins.getCollectionPrincipale().add(cercle);
                }

                else if (objectActuel == imgTriangle){

                    //Si le traitTriangle existe, on assigne sa valeur a triangle, on l ajoute au vecteur et on remet les temps a null
                    if (traitTriangleTemp != null){
                        triangle = traitTriangleTemp;
                        collectionDessins.getCollectionPrincipale().add(triangle);
                        traitTriangleTemp = null;
                        triangleTemp = null;
                    }
                }

                else if (objectActuel == imgRectangle){

                    rectangleTemp = null;
                    collectionDessins.getCollectionPrincipale().add(rectangle);
                }

                else if (objectActuel == imgEfface){
                    effaceTemp = null;
                    collectionDessins.getCollectionPrincipale().add(efface);
                }

                else if (objectActuel == imgPipette){
                    objectActuel = imgTraitLibre;
                }

                surface.invalidate();
            }
            return true;
        }
    }
}