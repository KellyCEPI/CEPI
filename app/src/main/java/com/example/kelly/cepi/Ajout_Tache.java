package com.example.kelly.cepi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    Intent i_tache = getIntent();
    Utilisateur U1 = (Utilisateur) i_tache.getSerializableExtra("utilisateur");

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_tache);

        bouton_valider = (Button) findViewById(R.id.ButtonValiderTache);


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

        if(i_tache.getIntExtra("consultation",0) == 1){
            affichage_consultation();
        }
        else{
            bouton_valider.setOnClickListener(BoutonValiderListener);
        }
    }


    public void affichage_consultation(){
        int idd = i_tache.getIntExtra("idd",0);
        int idt = i_tache.getIntExtra("idt",0);
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
        //dossier
        //duree
        nb_repetition.setText(T1.get_repeat_nb());
        repetition = Integer.parseInt(nb_repetition.getText().toString());
        //rappel
        //Importance: celle mise à l'enregistrement ou actuelle ?
        //Urgence
    }

    public View.OnClickListener ModifierTacheListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(Ajout_Tache.this,"Modification",Toast.LENGTH_SHORT).show();
            Intent i1 = new Intent(Ajout_Tache.this, Page_Principale.class);
            startActivity(i1);
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
        @Override
        public void onClick(View view) {
            nomS = nom_de_la_tache.getText().toString();
            heures = nb_heures.getValue();
            minutes = nb_minutes.getValue();
            //Enregistrer la tache

            Intent i1 = new Intent(Ajout_Tache.this, Page_Principale.class);
            startActivity(i1);
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