package com.example.kelly.cepi;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Objets.Dossier;
import Objets.Evenement;
import Objets.Utilisateur;


/**
 * Created by Kelly on 14/03/2018.
 */


public class Ajout_Evenement extends Activity {

    Button definir_date = null;
    Button definir_horaire = null;
    Button valider = null;
    TextView date = null;
    TextView horaire = null;
    EditText nom_evenement = null;
    String nomS;
    int annee;
    int mois;
    int jour;
    int heure;
    int minute;
    Spinner liste_rappels = null;
    int rappel = 0;
    Spinner liste_dossier;
    ArrayList<Integer> liste_choix_idd;
    int idd_ajout;
    Intent intent;
    Utilisateur U1;
    int ide;
    int idd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        U1 = intent.getParcelableExtra("user");
        System.out.println("        Intent reçu");
        System.out.println(U1.getEmail());

        setContentView(R.layout.ajout_evenement);

        definir_date = findViewById(R.id.ButtonDefinirDate);
        definir_date.setOnClickListener(DefinirDateListener);

        definir_horaire = findViewById(R.id.ButtonDefinirHoraire);
        definir_horaire.setOnClickListener(DefinirHoraireListener);

        valider = findViewById(R.id.ButtonValiderEvenement);


        date = findViewById(R.id.TextViewDate);
        horaire = findViewById(R.id.TextViewHeure);

        Calendar nowDate = Calendar.getInstance();
        date.setText(String.valueOf(nowDate.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(nowDate.get(Calendar.MONTH)+1)
        +"/"+String.valueOf(nowDate.get(Calendar.YEAR)));
        horaire.setText(nowDate.get(Calendar.HOUR)+":"+nowDate.get(Calendar.MINUTE));

        valider.setOnClickListener(ValiderEvenementListener);

        nom_evenement = findViewById(R.id.EditTextNomEvenement);

        liste_rappels = (Spinner) findViewById(R.id.ChoixRappel);
        List<String> choix_rappel = new ArrayList<String>();
        choix_rappel.add("Jamais");
        choix_rappel.add("Chaque jour");
        choix_rappel.add("Chaque semaine");
        choix_rappel.add("Chaque mois");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, choix_rappel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste_rappels.setAdapter(adapter);
        liste_rappels.setOnItemSelectedListener(ChoixRappelListener);

        liste_dossier = (Spinner) findViewById(R.id.SpinnerChoixDossier);
        List<String> choix_dossier = new ArrayList<String>();
        liste_choix_idd = new ArrayList<Integer>();
        ArrayList<Dossier> dossier_utilisateur = U1.get_dossiers();
        for(int i = 0; i< dossier_utilisateur.size(); i++){
            Dossier D = dossier_utilisateur.get(i);
            choix_dossier.add(D.get_nom_dos());
            liste_choix_idd.add(D.get_idd());
        }
        ArrayAdapter<String> dossier_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, choix_dossier);
        liste_dossier.setAdapter(dossier_adapter);
        liste_dossier.setOnItemSelectedListener(ChoixDossierListener);

        if(intent.getIntExtra("consultation",0) == 1){
            ide = intent.getIntExtra("identifiant de l'evenement",-1);
            idd = intent.getIntExtra("identifiant du dossier",-1);
            affichage_consultation();
        }
        else{
            valider.setOnClickListener(ValiderEvenementListener);
        }
    }

    public void affichage_consultation(){
        valider.setOnClickListener(ModifierEvenementListener);
        int i = 0;
        while(i<U1.get_dossiers().size() && U1.get_dossiers().get(i).get_idd() != idd){
            i++;
        }
        Dossier D1 = U1.get_dossiers().get(i);
        i = 0;
        while(i<D1.get_evenements().size() && D1.get_evenements().get(i).get_ide() != ide){
            i++;
        }
        Evenement E1 = D1.get_evenements().get(i);
        nom_evenement.setText(E1.get_nom_ev());
        int annee = E1.get_date_heure().get(Calendar.YEAR);
        int mois = E1.get_date_heure().get(Calendar.MONTH);
        int jour = E1.get_date_heure().get(Calendar.DAY_OF_MONTH); //nom dossier
        date.setText(String.valueOf(jour) + '/' + String.valueOf(mois+1) + '/' + String.valueOf(annee));
        int heure = E1.get_date_heure().get(Calendar.HOUR_OF_DAY);
        int minute = E1.get_date_heure().get(Calendar.MINUTE);
        horaire.setText(String.valueOf(heure) + ':' + String.valueOf(minute));
        int k = 0;
        while(k< liste_choix_idd.size() & liste_choix_idd.get(k) != idd){
            k++;
        }
        liste_dossier.setSelection(k);
        liste_rappels.setSelection(E1.get_repeat_inter());
        ide = E1.get_ide();
    }


    public View.OnClickListener ModifierEvenementListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int i = liste_dossier.getSelectedItemPosition();

            //à changer
            String dateS = String.valueOf(date.getText());
            String[] parties = dateS.split("/");
            jour = Integer.parseInt(parties[0]);
            mois = Integer.parseInt(parties[1])-1;
            annee = Integer.parseInt(parties[2]);


            String horaire_s = String.valueOf(horaire.getText());
            String[] parts = horaire_s.split(":");
            System.out.println("        Récupération nouvel horaire:");
            System.out.println(horaire_s+" "+parts[0]+parts[1]);
            heure = Integer.parseInt(parts[0]);
            minute = Integer.parseInt(parts[1]);


            /*
            Penser à récupérer le choix de rappel qui est dans l'entier "rappel"
             */


            Calendar date_heure = Calendar.getInstance();
            date_heure.set(annee,mois,jour, heure, minute);

            // CALENDAR SANS LE PICKER ALEXIS
            U1.modifier_ev(ide, nom_evenement.getText().toString(),liste_choix_idd.get(i), date_heure, rappel);

            Toast.makeText(Ajout_Evenement.this,"Modification",Toast.LENGTH_SHORT).show();

            intent.putExtra("user",U1);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    };


    public AdapterView.OnItemSelectedListener ChoixDossierListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            idd_ajout = liste_choix_idd.get(i);
        };

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public AdapterView.OnItemSelectedListener ChoixRappelListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            rappel = i;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };



    public View.OnClickListener DefinirDateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment FragmentDate = new DatePickerFragment();
            FragmentDate.show(getFragmentManager(),"DatePicker");
        }
    };

    public View.OnClickListener DefinirHoraireListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment FragmentHeure = new TimePickerFragment();
            FragmentHeure.show(getFragmentManager(),"TimePicker");
        }
    };

    public View.OnClickListener ValiderEvenementListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int i = liste_dossier.getSelectedItemPosition();
            //recuperer le calendar date_heure sans les DatePicker ALEXIS
            //à changer
            String dateS = String.valueOf(date.getText());
            String[] parties = dateS.split("/");
            //System.out.println("  @@@@@&é@&é"+dateS);
            if (dateS != "Aucune date définie") {
                jour = Integer.parseInt(parties[0]);
                //penser à enelver 1 pour correspondre au calendrier
                mois = Integer.parseInt(parties[1])-1;
                annee = Integer.parseInt(parties[2]);
            } else {
                //ici afficher message erreur car date non choisie
                // OU empêcher le click sur valider
            }

            System.out.println("        int parse: ");
            System.out.println(jour+"/"+mois+"/"+annee);

            String horaire_s = String.valueOf(horaire.getText());
            String[] parts = horaire_s.split(":");
            heure = Integer.parseInt(parts[0]);
            minute = Integer.parseInt(parts[1]);

            Calendar date_heure = Calendar.getInstance();
            date_heure.set(annee,mois,jour, heure, minute);

            //Enregistrer l'évènement
            U1.ajouter_ev(nom_evenement.getText().toString(),liste_choix_idd.get(i), date_heure, rappel);
            System.out.println("ajout evenement:");
            System.out.println(U1.get_dossiers().get(0).get_evenements().size());

            Toast.makeText(Ajout_Evenement.this,"Enregistré",Toast.LENGTH_SHORT).show();
            intent.putExtra("user",U1);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    };

}