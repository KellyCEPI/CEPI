package com.example.kelly.cepi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    Button bouton_supprimer;
    TextView nom_du_dossier;

    int idd;
    Utilisateur U1;


    protected void onCreate(Bundle savedInstanceState) {

        Intent i1 = getIntent();
        U1 = i1.getParcelableExtra("user");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_dossier);


        idd = i1.getIntExtra("idd", 0);

        bouton_supprimer = (Button) findViewById(R.id.ButtonSupprimerDossier);
        bouton_supprimer.setOnClickListener(SupprimerDossierListener);

        int i = 0;
        while (i < U1.get_dossiers().size() && U1.get_dossiers().get(i).get_idd() != idd) {
            i += 1;
        }
        Dossier D1 = U1.get_dossiers().get(i);

        nom_du_dossier.setText(D1.get_nom_dos());
        registerForContextMenu(nom_du_dossier);
        nom_du_dossier.setOnLongClickListener(ModifNomDosListener);


        liste_evenements_view = findViewById(R.id.ListViewEvenements);
        liste_taches_view = findViewById(R.id.ListViewTaches);
        liste_listes_view = findViewById(R.id.ListViewListes);

        registerForContextMenu(liste_evenements_view);
        registerForContextMenu(liste_taches_view);
        registerForContextMenu(liste_listes_view);

        liste_evenements_view.setOnLongClickListener(MenuSuppressionListener);
        liste_taches_view.setOnLongClickListener(MenuSuppressionListener);
        liste_listes_view.setOnLongClickListener(MenuSuppressionListener);

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

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()) {
            case R.id.TextViewNomDossier:
                getMenuInflater().inflate(R.menu.modifier_dossier, menu);
                menu.setHeaderTitle("Modifier");
            default:
                getMenuInflater().inflate(R.menu.contextmenu, menu);
                menu.setHeaderTitle("Choose an option");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // On récupère la position de l'item concerné
        int item_pos = info.position;
        switch(item.getActionView().getId()){
            case R.id.ListViewEvenements:
                int ide = liste_evenements_id.get(item_pos);
                liste_evenements.remove(item_pos);
                U1.supprimer_ev(ide);
                adapter_evenements.notifyDataSetChanged();
                Toast.makeText(this,"Evénement supprimé", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ListViewTaches:
                int idt = liste_taches_id.get(item_pos);
                liste_taches.remove(item_pos);
                U1.supprimer_tache(idt);
                adapter_taches.notifyDataSetChanged();
                Toast.makeText(this,"Tâche supprimée", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ListViewListes:
                int idl = liste_listes_id.get(item_pos);
                liste_listes.remove(item_pos);
                U1.supprimer_liste(idl);
                adapter_listes.notifyDataSetChanged();
                Toast.makeText(this,"Liste supprimée", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.TextViewNomDossier:
                new AlertDialog.Builder(Affichage_Dossier.this)
                        .setView(R.layout.nouveau_dossier).setPositiveButton("Modifier", ModifierDossierListenerDialog)
                        .setNegativeButton("Annuler", AnnulerListener)
                        .show();
        }
        return super.onContextItemSelected(item);
    }

    public View.OnLongClickListener MenuSuppressionListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            openContextMenu(view);
            return false;
        }
    };

    public View.OnLongClickListener ModifNomDosListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            openContextMenu(view);
            return false;
        }
    };



    public View.OnClickListener SupprimerDossierListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View view) {
            new AlertDialog.Builder(Affichage_Dossier.this)
                    .setView(R.layout.suppression_dossier).setPositiveButton("Supprimer", SupprimerDossierListenerDialog)
                    .setNegativeButton("Annuler", AnnulerListener)
                    .show();
        }
    };

    public DialogInterface.OnClickListener SupprimerDossierListenerDialog = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            U1.supprimer_dossier(idd);
        }
    };

    public DialogInterface.OnClickListener AnnulerListener = new DialogInterface.OnClickListener(){

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
        }
    };

    public DialogInterface.OnClickListener ModifierDossierListenerDialog = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            EditText nouveau_nom = findViewById(R.id.NomDossier);
            U1.modifier_dossier(idd,nouveau_nom.getText().toString());
            nom_du_dossier.setText(nouveau_nom.getText().toString());
        }
    };
}