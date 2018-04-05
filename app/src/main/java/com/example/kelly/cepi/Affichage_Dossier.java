package com.example.kelly.cepi;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import Objets.Dossier;
import Objets.Evenement;
import Objets.Utilisateur;

/**
 * Created by Kelly on 27/03/2018.
 */



public class Affichage_Dossier extends AppCompatActivity {

    ListView liste_evenements_view = null;
    ListView liste_taches_view = null;
    ListView liste_listes_view = null;

    List<String> liste_evenements = new ArrayList<String>();
    List<String> liste_taches = new ArrayList<String>();
    List<String> liste_listes = new ArrayList<String>();

    List<Integer> liste_evenements_id = new ArrayList<Integer>();
    List<Integer> liste_taches_id = new ArrayList<Integer>();
    List<Integer> liste_listes_id = new ArrayList<Integer>();

    ArrayAdapter<String> adapter_evenements = null;
    ArrayAdapter<String> adapter_taches = null;
    ArrayAdapter<String> adapter_listes = null;

    Intent i1 = getIntent();
    int idd;
    Utilisateur U1;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_dossier);


        idd = i1.getIntExtra("idd", 0);

        int i = 0;
        while (i < U1.get_dossiers().size() & U1.get_dossiers().get(i).get_idd() != idd) {
            i += 1;
        }
        Dossier D1 = U1.get_dossiers().get(i);

        liste_evenements_view = findViewById(R.id.ListViewEvenements);
        liste_taches_view = findViewById(R.id.ListViewTaches);
        liste_listes_view = findViewById(R.id.ListViewListes);

        adapter_evenements = new ArrayAdapter<String>(Affichage_Dossier.this, android.R.layout.simple_list_item_multiple_choice, liste_evenements);
        liste_evenements_view.setAdapter(adapter_evenements);
        adapter_taches = new ArrayAdapter<String>(Affichage_Dossier.this, android.R.layout.simple_list_item_multiple_choice, liste_taches);
        liste_taches_view.setAdapter(adapter_taches);
        adapter_listes = new ArrayAdapter<String>(Affichage_Dossier.this, android.R.layout.simple_list_item_multiple_choice, liste_listes);
        liste_listes_view.setAdapter(adapter_listes);

        afficher_le_bon_dossier(D1);

        liste_evenements_view.setOnItemClickListener(Evenement_listview_Listener);
        liste_taches_view.setOnItemClickListener(Tache_listview_Listener);
        liste_listes_view.setOnItemClickListener(Liste_listview_Listener);


    }

    public void afficher_le_bon_dossier(Dossier D1) {
        int i = 0;
        for (i = 0; i < D1.get_evenements().size(); i++) {
            liste_evenements.add(D1.get_evenements().get(i).get_nom_ev());
            liste_evenements_id.add((Integer) D1.get_evenements().get(i).get_ide());
        }
        for (i = 0; i < D1.get_taches().size(); i++) {
            liste_taches.add(D1.get_taches().get(i).get_nom_tache());
        }
        for (i = 0; i < D1.get_listes().size(); i++) {
            liste_listes.add(D1.get_listes().get(i).get_nom_liste());
        }
    }

    public AdapterView.OnItemClickListener Evenement_listview_Listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            int ide = (int) liste_evenements_id.get(i);
            Intent itent_evenement = new Intent(Affichage_Dossier.this,Ajout_Evenement.class);
            itent_evenement.putExtra("identifiant de l'evenement", ide);
            itent_evenement.putExtra("identifiant du dossier", idd);
            itent_evenement.putExtra("consultation", 1);
            startActivity(itent_evenement);
        }
    };

    public AdapterView.OnItemClickListener Tache_listview_Listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            int idt = (int) liste_taches_id.get(i);
            Intent itent_tache = new Intent(Affichage_Dossier.this,Ajout_Tache.class);
            itent_tache.putExtra("identifiant de la tache", idt);
            itent_tache.putExtra("identifiant du dossier", idd);
            itent_tache.putExtra("consultation", 1);
            startActivity(itent_tache);
        }
    };

    public AdapterView.OnItemClickListener Liste_listview_Listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            int idl = (int) liste_listes_id.get(i);
            Intent itent_liste = new Intent(Affichage_Dossier.this,Ajout_Liste.class);
            itent_liste.putExtra("identifiant de la liste", idl);
            itent_liste.putExtra("identifiant du dossier", idd);
            itent_liste.putExtra("consultation", 1);
            startActivity(itent_liste);
        }
    };
}