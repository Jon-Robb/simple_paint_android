package com.example.tp2_paint;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class DialogueLargeur extends Dialog {

    private SeekBar seekBar;
    private TextView champLargeur;
    private EcouteurLargeur ecL;
    private Button btnValider;
    private MainActivity mainActivity;


    //On passe le context du parent (MainActivity) pour y avoir accès dans notre OnClick du bouton valider
    public DialogueLargeur(@NonNull Context context) {
        super(context);
        this.mainActivity = (MainActivity ) context;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue_largeur);

        // Queries pour nos widgets
        champLargeur = findViewById(R.id.champLargeur);
        seekBar = findViewById(R.id.seekBar);
        btnValider = findViewById(R.id.btnValider);

        //Trois etapes d Eric
        ecL = new EcouteurLargeur();

        btnValider.setOnClickListener(ecL);
        seekBar.setOnSeekBarChangeListener(ecL);
    }

    //3e etape
    private class EcouteurLargeur implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            //On affiche le progres de la seekBar dans le champLargeur
            champLargeur.setText(String.valueOf(seekBar.getProgress()));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onClick(View source) {

            //Lorsque l'usager clique sur le bouton valider, on va modifier la valeur de largeurActuelle dans
            //la MainActivity et on dismiss la boite de dialogue
            if (source == btnValider){
                mainActivity.setLargeurActuelle(seekBar.getProgress());
                mainActivity.getDl().dismiss();
            }
        }
    }
}




