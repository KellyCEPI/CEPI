package com.example.kelly.cepi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import Objets.Dossier;
import Objets.Evenement;
import Objets.Liste;
import Objets.Tache;
import Objets.Utilisateur;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Affichage_Dossier extends AppCompatActivity {

    SwipeMenuListView liste_evenements_view = null;
    SwipeMenuListView liste_taches_view = null;
    SwipeMenuListView liste_listes_view = null;

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
    int id_item_subdossier;

    Intent intent;

    static final int CHANGE_EVENT_REQUEST_CODE = 1;
    static final int CHANGE_TASK_REQUEST_CODE = 2;
    static final int CHANGE_LIST_REQUEST_CODE = 3;

    DateFormat df;


    protected void onCreate(Bundle savedInstanceState) {

        intent = getIntent();
        U1 = intent.getParcelableExtra("user");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_dossier);


        idd = intent.getIntExtra("idd", 0);
        id_item_subdossier=intent.getIntExtra("item dossier",0);

        df = new SimpleDateFormat("dd/MM/yyyy   H:m");

        bouton_supprimer = (Button) findViewById(R.id.ButtonSupprimerDossier);
        bouton_supprimer.setOnClickListener(SupprimerDossierListener);

        nom_du_dossier = (TextView) findViewById(R.id.TextViewNomDossier);

        int i = 0;
        while (i < U1.get_dossiers().size() && U1.get_dossiers().get(i).get_idd() != idd) {
            i += 1;
            System.out.println("        Ajout Doss:");
            System.out.println(i);
            System.out.println("        Indentifiant du Doss:");
            System.out.println(idd+" : "+U1.get_dossiers().get(i).get_idd());
        }
        final Dossier D1 = U1.get_dossiers().get(i);

        nom_du_dossier.setText(D1.get_nom_dos());
        registerForContextMenu(nom_du_dossier);
        nom_du_dossier.setOnLongClickListener(ModifNomDosListener);


        liste_evenements_view = (SwipeMenuListView) findViewById(R.id.ListViewEvenements);
        liste_taches_view = (SwipeMenuListView) findViewById(R.id.ListViewTaches);
        liste_listes_view = (SwipeMenuListView) findViewById(R.id.ListViewListes);



        adapter_evenements = new ArrayAdapter<String>(Affichage_Dossier.this, android.R.layout.simple_list_item_1, liste_evenements);
        liste_evenements_view.setAdapter(adapter_evenements);
        adapter_taches = new ArrayAdapter<String>(Affichage_Dossier.this, android.R.layout.simple_list_item_1, liste_taches);
        liste_taches_view.setAdapter(adapter_taches);
        adapter_listes = new ArrayAdapter<String>(Affichage_Dossier.this, android.R.layout.simple_list_item_1, liste_listes);
        liste_listes_view.setAdapter(adapter_listes);

        afficher_le_bon_dossier(D1);
        liste_evenements_view.setOnItemClickListener(Evenement_listview_Listener);
        liste_taches_view.setOnItemClickListener(Tache_listview_Listener);
        liste_listes_view.setOnItemClickListener(Liste_listview_Listener);

        SwipeMenuCreator creator1 = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "done" item
                SwipeMenuItem doneItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                doneItem.setBackground(new ColorDrawable(Color.rgb(0x00,
                        0x66, 0xff)));
                // set item width
                doneItem.setWidth(170);
                // set a icon
                doneItem.setIcon(R.drawable.ic_done);
                // add to menu
                menu.addMenuItem(doneItem);
            }
        };

        SwipeMenuCreator creator2 = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "supprimer" item
                SwipeMenuItem clearItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                clearItem.setBackground(new ColorDrawable(Color.rgb(0xf4,
                        0x42, 0x42)));
                // set item width
                clearItem.setWidth(170);
                // set a icon
                clearItem.setIcon(R.drawable.ic_clear);
                // add to menu
                menu.addMenuItem(clearItem);
            }
        };

        liste_taches_view.setMenuCreator(creator1);
        liste_listes_view.setMenuCreator(creator2);
        liste_evenements_view.setMenuCreator(creator1);

        liste_evenements_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Evenement E = D1.get_evenements().get(position);
                        liste_evenements.remove(position);
                        liste_evenements_id.remove(position);
                        U1.supprimer_ev(E.get_ide());
                        adapter_evenements.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        liste_taches_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Tache T = D1.get_taches().get(position);
                        liste_taches.remove(position);
                        liste_taches_id.remove(position);
                        U1.supprimer_tache(T.get_idt());
                        adapter_taches.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        liste_listes_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Liste L = D1.get_listes().get(position);
                        liste_listes.remove(position);
                        liste_listes_id.remove(position);
                        U1.supprimer_liste(L.get_idl());
                        adapter_listes.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

    }


    public void afficher_le_bon_dossier(Dossier D1) {
        int i = 0;
        for (i = 0; i < D1.get_evenements().size(); i++) {
            Evenement Ev = D1.get_evenements().get(i);
            liste_evenements.add(Ev.get_nom_ev() + "\n" + df.format(Ev.get_date_heure().getTime()));
            liste_evenements_id.add((Integer) D1.get_evenements().get(i).get_ide());
        }
        for (i = 0; i < D1.get_taches().size(); i++) {
            liste_taches.add(D1.get_taches().get(i).get_nom_tache());
            liste_taches_id.add((Integer) D1.get_taches().get(i).get_idt());
        }
        for (i = 0; i < D1.get_listes().size(); i++) {
            liste_listes.add(D1.get_listes().get(i).get_nom_liste());
            liste_listes_id.add(D1.get_listes().get(i).get_idl());
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
            itent_evenement.putExtra("user",U1);
            startActivityForResult(itent_evenement, CHANGE_EVENT_REQUEST_CODE);
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
            itent_tache.putExtra("user",U1);
            startActivityForResult(itent_tache, CHANGE_TASK_REQUEST_CODE);
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
            itent_liste.putExtra("user",U1);
            startActivityForResult(itent_liste, CHANGE_LIST_REQUEST_CODE);
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
        switch(item.getItemId()){
            case R.id.ListViewEvenements:
                int ide = liste_evenements_id.get(item_pos);
                liste_evenements.remove(item_pos);
                liste_evenements_id.remove(item_pos);
                U1.supprimer_ev(ide);
                adapter_evenements.notifyDataSetChanged();
                Toast.makeText(this,"Evénement supprimé", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ListViewTaches:
                Toast.makeText(Affichage_Dossier.this,"passage",Toast.LENGTH_SHORT).show();
                int idt = liste_taches_id.get(item_pos);
                liste_taches.remove(item_pos);
                liste_taches_id.remove(item_pos);
                U1.supprimer_tache(idt);
                adapter_taches.notifyDataSetChanged();
                Toast.makeText(this,"Tâche supprimée", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ListViewListes:
                int idl = liste_listes_id.get(item_pos);
                liste_listes.remove(item_pos);
                liste_listes_id.remove(item_pos);
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
            System.out.println("        Supprimer doss");
            System.out.println(idd);
            U1.supprimer_dossier(idd);
            intent.putExtra("user",U1);
            intent.putExtra("idd",idd);
            intent.putExtra("suppression",1);
            intent.putExtra("item_dossier", id_item_subdossier);
            setResult(Activity.RESULT_OK, intent);
            finish();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            U1 = data.getParcelableExtra("user");
            int i = 0;
            while (i < U1.get_dossiers().size() && U1.get_dossiers().get(i).get_idd() != idd) {
                i += 1;
            }
            Dossier D1 = U1.get_dossiers().get(i);


            if (requestCode == CHANGE_EVENT_REQUEST_CODE) {
                liste_evenements.clear();
                liste_evenements_id.clear();
                for (i = 0; i < D1.get_evenements().size(); i++) {
                    Evenement Ev = D1.get_evenements().get(i);
                    liste_evenements.add(Ev.get_nom_ev() + "\n" + df.format(Ev.get_date_heure().getTime()));
                    liste_evenements_id.add((Integer) D1.get_evenements().get(i).get_ide());
                }
                adapter_evenements.notifyDataSetChanged();

            } else if (requestCode == CHANGE_TASK_REQUEST_CODE) {
                liste_taches.clear();
                liste_taches_id.clear();
                for (i = 0; i < D1.get_taches().size(); i++) {
                    liste_taches.add(D1.get_taches().get(i).get_nom_tache());
                    liste_taches_id.add((Integer) D1.get_taches().get(i).get_idt());
                }
                adapter_taches.notifyDataSetChanged();


            } else if (requestCode == CHANGE_LIST_REQUEST_CODE) {
                liste_listes.clear();
                liste_listes_id.clear();
                for (i = 0; i < D1.get_listes().size(); i++) {
                    liste_listes.add(D1.get_listes().get(i).get_nom_liste());
                    liste_listes_id.add(D1.get_listes().get(i).get_idl());
                }
                adapter_listes.notifyDataSetChanged();
            }
        }
    }
    public void onBackPressed(){
        intent.putExtra("user",U1);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}