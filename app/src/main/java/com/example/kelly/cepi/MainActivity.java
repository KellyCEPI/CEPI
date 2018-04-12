package com.example.kelly.cepi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.text.TextWatcher;

import java.util.Calendar;

import Objets.Utilisateur;

public class MainActivity extends AppCompatActivity {

//https://github.com/KellyCEPI/CEPI/
    EditText email = null;
    EditText mdp = null;

    String mdpS;
    String emailS;

    CheckBox ResterConnecter = null;

    Button bouton_inscription = null;
    Button bouton_connexion = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bouton_inscription = (Button) findViewById(R.id.ButtonInscription);
        bouton_inscription.setOnClickListener(InscriptionListener);

        bouton_connexion = (Button) findViewById(R.id.ButtonConnexion);
        bouton_connexion.setOnClickListener(ConnexionListener);

        email = (EditText) findViewById(R.id.EditTextEmail);
        mdp = (EditText) findViewById(R.id.EditTextMDP);
        email.addTextChangedListener(TextWatcher1);
        mdp.addTextChangedListener(TextWatcher1);

        ResterConnecter = (CheckBox) findViewById(R.id.ResterConnecter);
    }

    public View.OnClickListener ConnexionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            emailS= email.getText().toString();
            mdpS = mdp.getText().toString();
            // Vérifier le mdp et l'identifiant
            // If c'est tout bon, établir la connexion au serveur et passage à la page d'après :
            Intent i1 = new Intent(MainActivity.this, Page_Principale.class);
            Utilisateur u = new Utilisateur("@","@");
            /*u.ajouter_ev("@",0, Calendar.getInstance(),0);
            u.ajouter_dossier("ce genre de dos");
            System.out.println("        send Intent:");
            System.out.println(u.get_evenements().get(0).get_nom_ev());
            System.out.println(u.get_dossiers().get(0).get_nom_dos());*/
            i1.putExtra("user",u);
            startActivity(i1);
            finish();
        }
    };


    public View.OnClickListener InscriptionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i2 = new Intent(MainActivity.this, Page_Inscription.class);
            startActivity(i2);
        }
    };

    public TextWatcher TextWatcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(ResterConnecter.isChecked()){
                email.setText(email.getText().toString());
                mdp.setText(mdp.getText().toString());
            }
        }
    };

}
