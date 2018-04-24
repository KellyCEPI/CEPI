package com.example.kelly.cepi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Objets.Dossier;
import Objets.Ligne;
import Objets.Liste;
import Objets.Utilisateur;

/**
 * Created by Kelly on 20/03/2018.
 */

public class Ajout_Liste extends Activity {

    EditText nom_liste =null;
    ArrayList<String> mliste = new ArrayList<String>();
    ListView liste = null;
    ArrayList<Integer> liste_coche = new ArrayList<Integer>();
    EditText nouvel_element = null;
    ArrayAdapter<String> adapter = null;
    Button valider = null;
    Spinner liste_dossier;
    ArrayList<Integer> liste_choix_idd;
    int idd_ajout;
    Intent intent;
    int idl;
    int idd;
    Utilisateur U1;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        intent = getIntent();
        U1 = intent.getParcelableExtra("user");
        setContentView(R.layout.ajout_liste);

        valider = findViewById(R.id.ButtonAjouterListe);
        valider.setOnClickListener(ValiderListeListener);

        nom_liste = findViewById(R.id.EditTextNomListe);
        //mliste.add("Lait");
        //mliste.add("Oeufs");

        liste = (ListView) findViewById(R.id.ListView);
        liste.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new ArrayAdapter<String>(Ajout_Liste.this,android.R.layout.simple_list_item_multiple_choice,mliste);
        liste.setAdapter(adapter);
        liste.setOnItemClickListener(ItemListeListener);

        nom_liste.setOnKeyListener(FinNomListe);



        nouvel_element = (EditText) findViewById(R.id.EditTextNouvelElement);
        nouvel_element.setOnKeyListener(Appuye_entree);

        registerForContextMenu(liste);
        liste.setOnLongClickListener(OuvrirMenu);


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

        if(this.intent.getIntExtra("consultation",0) == 1){
            idl = intent.getIntExtra("identifiant de la liste",-1);
            idd = intent.getIntExtra("identifiant du dossier",-1);
            affichage_consultation();
        }
        else{
            valider.setOnClickListener(ValiderListeListener);
        }
    }

    public void affichage_consultation(){
        valider.setOnClickListener(ModifierListeListener);
        int i = 0;
        while(i<U1.get_dossiers().size() && U1.get_dossiers().get(i).get_idd() != idd){
            i++;
        }
        Dossier D1 = U1.get_dossiers().get(i);
        i = 0;
        while(i<D1.get_listes().size() && D1.get_listes().get(i).get_idl() != idl){
            i++;
        }
        Liste L1 = D1.get_listes().get(i);
        nom_liste.setText(L1.get_nom_liste());
        int k = 0;
        while(k< liste_choix_idd.size() && liste_choix_idd.get(k) != idd){
            k++;
        }
        liste_dossier.setSelection(k);
        ArrayList<Ligne> liste_ligne = (ArrayList<Ligne>) L1.get_liste();
        for(int ligne = 0; ligne< liste_ligne.size();ligne ++) {
            mliste.add(liste_ligne.get(ligne).get_ligne());

            //AJOUTE POUR QUE CA MARCHE MAIS FAUX
            liste_coche.add(0);


            if (liste_ligne.get(ligne).get_cocher() == 1) {
                liste.setItemChecked(ligne,true);
            }
        }
        idl = L1.get_idl();
    }

    public AdapterView.OnItemClickListener ItemListeListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (liste_coche.get(i) == 1) {
                liste.setItemChecked(i, false);
                adapter.notifyDataSetChanged();
                liste_coche.set(i,0);
            } else {
                liste.setItemChecked(i, true);
                adapter.notifyDataSetChanged();
                liste_coche.set(i,1);
            }
        }
    };


    public View.OnClickListener ModifierListeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int i = liste_dossier.getSelectedItemPosition();
            ArrayList<Ligne> al = new ArrayList<Ligne>();
            System.out.println("trdfghjklmkjhgfds"+mliste.size());
            for (int k=0; k<mliste.size();k++){
                Ligne L = new Ligne(idl,liste_coche.get(k),mliste.get(k));
                al.add(L);
            }

            U1.modifier_liste(idl, nom_liste.getText().toString(), liste_choix_idd.get(i), al);
            Toast.makeText(Ajout_Liste.this,"Modification enregistrée",Toast.LENGTH_SHORT).show();
            intent.putExtra("user",U1);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    };


    public View.OnKeyListener Appuye_entree = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                mliste.add(nouvel_element.getText().toString());
                liste_coche.add(0);
                nouvel_element.setText("");

                return true;
            }
            return false;
        }
    };

    public View.OnKeyListener FinNomListe = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                if(view !=null){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                }
                return true;
            }
            return false;
        }
    };


    View.OnClickListener ValiderListeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (liste_choix_idd.size() == 0) {
                Toast.makeText(Ajout_Liste.this, "Vous devez d'abord créer un dossier", Toast.LENGTH_SHORT).show();
            } else {
                if (mliste.size() == 0) {
                    Toast.makeText(Ajout_Liste.this, "Remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    int i = liste_dossier.getSelectedItemPosition();
                    U1.ajouter_liste(nom_liste.getText().toString(), liste_choix_idd.get(i), mliste);
                    intent.putExtra("user", U1);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        }
    };

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
        menu.setHeaderTitle("Choose an option");
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // On récupère la position de l'item concerné
        int item_liste_pos = info.position;
        switch(item.getItemId()){
            case R.id.Supprimer:
                mliste.remove(item_liste_pos);
                liste_coche.remove(item_liste_pos);
                adapter.notifyDataSetChanged();
                Toast.makeText(this,"Item supprimé", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public View.OnLongClickListener OuvrirMenu = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            openContextMenu(view);
            return false;
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
}
