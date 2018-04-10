package com.example.kelly.cepi;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Objets.Dossier;
import Objets.Tache;
import Objets.Utilisateur;

/**
 * Created by Kelly on 15/03/2018.
 */

public class Ajout_Tache extends Activity{

    Button bouton_valider = null;
    EditText nom_de_la_tache = null;
    EditText duree_de_la_tache = null;
    NumberPicker nb_heures = null;
    NumberPicker nb_minutes = null;
    String nomS;
    int heures;
    int minutes;
    Spinner liste_repetition;
    int repetition;
    EditText nb_repetition;
    SeekBar bar_important;
    SeekBar bar_urgent;
    int importance;
    int urgence;
    Spinner liste_dossier;
    List<Integer> liste_choix_idd;
    int idd_ajout;
    int idt;
    int idd;
    Utilisateur U1;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        U1 = intent.getParcelableExtra("user");
        setContentView(R.layout.ajout_tache);

        bouton_valider = (Button) findViewById(R.id.ButtonValiderTache);

        bouton_valider.setOnClickListener(BoutonValiderListener);


        nom_de_la_tache = (EditText) findViewById(R.id.EditTextTache);
        nom_de_la_tache.addTextChangedListener(TextWatcherTache);

        nb_heures = (NumberPicker) findViewById(R.id.NumberPickerHeure);
        nb_minutes = (NumberPicker) findViewById(R.id.NumberPickerMinute);

        nb_heures.setMinValue(0);
        nb_minutes.setMinValue(0);
        nb_heures.setMaxValue(23);
        nb_minutes.setMaxValue(59);


        nb_repetition = (EditText) findViewById(R.id.EditTextRepetition);

        bar_important = (SeekBar) findViewById(R.id.SeekBarImportant);
        bar_urgent = (SeekBar) findViewById(R.id.SeekBarUrgent);
        bar_important.setOnSeekBarChangeListener(ImportantListener);
        bar_urgent.setOnSeekBarChangeListener(UrgentListener);



        liste_repetition = (Spinner) findViewById(R.id.SpinnerRepetition);
        List<String> choix_repetition = new ArrayList<String>();
        choix_repetition.add("Non");
        choix_repetition.add("Par jour");
        choix_repetition.add("Par semaine");
        choix_repetition.add("Par mois");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, choix_repetition);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste_repetition.setAdapter(adapter);
        liste_repetition.setOnItemSelectedListener(ChoixRepetitionListener);

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


        /*if(i_tache.getIntExtra("consultation",0) == 1){
            affichage_consultation();
        }
        else{
            bouton_valider.setOnClickListener(BoutonValiderListener);
        }*/
    }


    public void affichage_consultation(){
        bouton_valider.setOnClickListener(ModifierTacheListener);
        int i = 0;
        while(i<U1.get_dossiers().size() & U1.get_dossiers().get(i).get_idd() != idd){
            i++;
        }
        Dossier D1 = U1.get_dossiers().get(i);
        i = 0;
        while(i<D1.get_taches().size() & D1.get_taches().get(i).get_idt() != idt){
            i++;
        }
        Tache T1 = D1.get_taches().get(i);
        nom_de_la_tache.setText(T1.get_nom_tache());
        int k = 0;
        while(k< liste_choix_idd.size() & liste_choix_idd.get(k) != idd){
            k++;
        }
        liste_dossier.setSelection(k);
        int duree = T1.get_duree();
        int H = duree/60;
        nb_heures.setValue(H);
        nb_minutes.setValue(duree-H*60);
        nb_repetition.setText(T1.get_repeat_nb());
        repetition = Integer.parseInt(nb_repetition.getText().toString());
        liste_repetition.setSelection(T1.get_repeat_inter());
        bar_important.setProgress(T1.get_imp());
        bar_urgent.setProgress(T1.get_urgent());
        importance = T1.get_imp();
        urgence = T1.get_urgent();
        idt = T1.get_idt();
    }

    public View.OnClickListener ModifierTacheListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(Ajout_Tache.this,"Modification",Toast.LENGTH_SHORT).show();
            Intent i1 = new Intent(Ajout_Tache.this, Page_Principale.class);
            startActivity(i1);
            int i = liste_dossier.getSelectedItemPosition();
            heures = nb_heures.getValue();
            minutes = nb_minutes.getValue();
            U1.modifier_tache(idt, nom_de_la_tache.getText().toString(),liste_choix_idd.get(i),  Integer.parseInt(nb_repetition.getText().toString()), repetition, heures*60+minutes, importance, urgence);
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

    public SeekBar.OnSeekBarChangeListener ImportantListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            importance = i;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public SeekBar.OnSeekBarChangeListener UrgentListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            urgence = i;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public AdapterView.OnItemSelectedListener ChoixRepetitionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            repetition = i;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
    }
    };

    public View.OnClickListener BoutonValiderListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            int i = liste_dossier.getSelectedItemPosition();
            heures = nb_heures.getValue();
            minutes = nb_minutes.getValue();
            //Enregistrer la tache
            U1.ajouter_tache(nom_de_la_tache.getText().toString(),liste_choix_idd.get(i), Integer.parseInt(nb_repetition.getText().toString()), repetition, heures*60+minutes, importance, urgence);

            Tache task = new Tache(0, nomS, 0, 0, 0, heures*60+minutes, 0, 0);
            Intent intent = getIntent();
            intent.putExtra("user",U1);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    };

    public TextWatcher TextWatcherTache = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}