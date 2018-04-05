package com.example.kelly.cepi;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kelly on 13/03/2018.
 */

public class Page_Inscription extends Activity {

    Button sinscrire = null;
    String pseudoS;
    String emailS;
    String mdpS;
    String mdp_verifS;
    EditText pseudo;
    EditText email = null;
    EditText mdp = null;
    EditText mdp_verif = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_inscription);

        sinscrire = findViewById(R.id.ButtonInscrire);
        sinscrire.setOnClickListener(SinscrireListener);

        pseudo = findViewById(R.id.EditTextPseudo);
        email = findViewById(R.id.EditTextEmail);
        mdp = findViewById(R.id.EditTextMDP);
        mdp_verif = findViewById(R.id.EditTextMDPVerif);

    }

    public View.OnClickListener SinscrireListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pseudoS = pseudo.getText().toString();
            emailS = email.getText().toString();
            mdpS = mdp.getText().toString();
            mdp_verifS = mdp_verif.getText().toString();
            //Vérifier que les 2 mdp sont identiques
            if(mdpS != mdp_verifS){
                Toast.makeText(Page_Inscription.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            }
            else {
                //Inscrire le bonhomme dans la bdd
                Intent i1 = new Intent(Page_Inscription.this, Page_Principale.class);
                startActivity(i1);
            }
        }
    };

}
