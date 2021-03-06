package com.example.kelly.cepi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Objets.Dossier;
import Objets.Evenement;
import java.util.Calendar;

import Objets.Liste;
import Objets.Tache;
import Objets.Utilisateur;
/**
 * Created by Kelly on 13/03/2018.
 */



public class Page_Principale extends AppCompatActivity{

    public Utilisateur u;

    private DrawerLayout menu_lateral;
    private ActionBarDrawerToggle mToggle;
    SubMenu subMenu_dossier;

    static final int EVENT_REQUEST_CODE = 1;
    static final int TASK_REQUEST_CODE = 2;
    static final int LIST_REQUEST_CODE = 3;
    static final int FOLDER_REQUEST_CODE = 4;

    public SwipeMenuListView liste_generale;
    ArrayAdapter<String> adapter = null;
    ArrayList<Evenement> mliste_generale;
    ArrayList<String> liste_nom_evenements = new ArrayList<>();

    TextView nom_prochaine_tache;
    TextView duree_prochaine_tache;
    TextView dossier_prochaine_tache;

    RelativeLayout Prochaine_Tache_Layout;
    Tache prochaine_tache;
    int prochaine_tache_numero;

    CheckBox tache_terminee;
    DateFormat df;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        u = intent.getParcelableExtra("user");
        mliste_generale = u.get_evenements();

        setContentView(R.layout.page_principale);

        menu_lateral = (DrawerLayout) findViewById(R.id.menu_lateral);
        mToggle = new ActionBarDrawerToggle(this,menu_lateral,R.string.open,R.string.close);
        menu_lateral.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.Nav_View);
        Menu menu = navigationView.getMenu();
        subMenu_dossier = menu.addSubMenu("Dossiers");
        navigationView.setNavigationItemSelectedListener(NavigationListener);

        nom_prochaine_tache = findViewById(R.id.TextViewNomProchaineTache);
        duree_prochaine_tache = findViewById(R.id.TextViewDureeProchaineTache);
        dossier_prochaine_tache = findViewById(R.id.TextViewDossierProchaineTache);
        tache_terminee = (CheckBox)findViewById(R.id.CheckBoxTacheTerminee);
        tache_terminee.setOnClickListener(TacheTermineeListener);


        if(u.getTaches().size() != 0) {

            tache_terminee.setVisibility(View.VISIBLE);
            prochaine_tache = u.get_taches().get(0);
            prochaine_tache_numero = 0;
            nom_prochaine_tache.setText(prochaine_tache.get_nom_tache());
            int duree = prochaine_tache.get_duree();
            int heures = duree / 60;
            int minutes = duree - heures * 60;
            duree_prochaine_tache.setText(String.valueOf(heures) + ":" + String.valueOf(minutes));
            int idd = prochaine_tache.get_idd();
            int i = 0;
            while (i < u.get_dossiers().size() & u.get_dossiers().get(i).get_idd() != idd) {
                i++;
            }
            Dossier D1 = u.get_dossiers().get(i);
            dossier_prochaine_tache.setText(D1.get_nom_dos());
        }
        else{
            nom_prochaine_tache.setText("Aucune tâche en cours");
            tache_terminee.setVisibility(View.INVISIBLE);
        }

        Prochaine_Tache_Layout = findViewById(R.id.RelativeLayoutProchaineTache);
        Prochaine_Tache_Layout.setOnTouchListener(SwipeListener);

        df = new SimpleDateFormat("dd/MM/yyyy   H:m");

        for(int ev = 0; ev < mliste_generale.size();ev ++){
            Evenement Ev = mliste_generale.get(ev);
            liste_nom_evenements.add(Ev.get_nom_ev() + "\n" + "\n" + df.format(Ev.get_date_heure().getTime()));
        }

        liste_generale = (SwipeMenuListView) findViewById(R.id.ListeGenerale);
        adapter  = new ArrayAdapter<String>(Page_Principale.this,android.R.layout.simple_list_item_1, liste_nom_evenements);
        liste_generale.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

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

        liste_generale.setMenuCreator(creator);
        liste_generale.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Evenement ev = mliste_generale.get(position);
                        mliste_generale.remove(position);
                        liste_nom_evenements.remove(position);
                        u.supprimer_ev(ev.get_ide());
                        adapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        registerForContextMenu(liste_generale);
        liste_generale.setOnLongClickListener(MenuSuppressionListener);



    }

    @Override
    public void onResume() {
        super.onResume();
        try {

        } catch (Exception e) {

        }
    }

    private void setupDrawer() {
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            u = data.getParcelableExtra("user");
            if (data.getIntExtra("suppression", 0) == 1) {
                int id_item = data.getIntExtra("item dossier", 0);
                Toast.makeText(Page_Principale.this, String.valueOf(id_item), Toast.LENGTH_SHORT).show();
                subMenu_dossier.removeItem(id_item);
            }
        }
        if (requestCode == EVENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Evenement ev = u.get_evenements().get(u.get_evenements().size() - 1);
                mliste_generale.add(ev);
                liste_nom_evenements.add(ev.get_nom_ev()+ "\n" + "\n" + df.format(ev.get_date_heure().getTime()));
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == TASK_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (u.getTaches().size() != 0) {
                    tache_terminee.setVisibility(View.VISIBLE);
                    prochaine_tache = u.get_taches().get(0);
                    prochaine_tache_numero = 0;
                    nom_prochaine_tache.setText(prochaine_tache.get_nom_tache());
                    int duree = prochaine_tache.get_duree();
                    int heures = duree / 60;
                    int minutes = duree - heures * 60;
                    duree_prochaine_tache.setText(String.valueOf(heures) + ":" + String.valueOf(minutes));
                    int idd = prochaine_tache.get_idd();
                    int i = 0;
                    while (i < u.get_dossiers().size() & u.get_dossiers().get(i).get_idd() != idd) {
                        i++;
                    }
                    Dossier D1 = u.get_dossiers().get(i);
                    dossier_prochaine_tache.setText(D1.get_nom_dos());
                } else {
                    nom_prochaine_tache.setText("Aucune tâche en cours");
                    tache_terminee.setVisibility(View.INVISIBLE);
                }
            }
        } else if (requestCode == LIST_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            }
        } else if (requestCode == FOLDER_REQUEST_CODE) {
            mliste_generale = u.get_evenements();
            liste_nom_evenements.clear();
            for (int ev = 0; ev < mliste_generale.size(); ev++) {
                Evenement Ev = mliste_generale.get(ev);
                liste_nom_evenements.add(Ev.get_nom_ev() + df.format(Ev.get_date_heure().getTime()));
                }
                adapter.notifyDataSetChanged();
            if(u.getTaches().size() != 0) {

                tache_terminee.setVisibility(View.VISIBLE);
                prochaine_tache = u.get_taches().get(0);
                prochaine_tache_numero = 0;
                nom_prochaine_tache.setText(prochaine_tache.get_nom_tache());
                int duree = prochaine_tache.get_duree();
                int heures = duree / 60;
                int minutes = duree - heures * 60;
                duree_prochaine_tache.setText(String.valueOf(heures) + ":" + String.valueOf(minutes));
                int idd = prochaine_tache.get_idd();
                int i = 0;
                while (i < u.get_dossiers().size() & u.get_dossiers().get(i).get_idd() != idd) {
                    i++;
                }
                Dossier D1 = u.get_dossiers().get(i);
                dossier_prochaine_tache.setText(D1.get_nom_dos());
            }
            else{
                nom_prochaine_tache.setText("Aucune tâche en cours");
                tache_terminee.setVisibility(View.INVISIBLE);
            }
                }
            }




    public View.OnClickListener TacheTermineeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int t_num = prochaine_tache_numero;
            int idt = u.getTaches().get(prochaine_tache_numero).get_idt();
            u.supprimer_tache(idt);
            tache_terminee.setChecked(false);
            if(u.getTaches().size() != 0) {

                tache_terminee.setVisibility(View.VISIBLE);
                prochaine_tache = u.get_taches().get(0);
                prochaine_tache_numero = 0;
                nom_prochaine_tache.setText(prochaine_tache.get_nom_tache());
                int duree = prochaine_tache.get_duree();
                int heures = duree / 60;
                int minutes = duree - heures * 60;
                duree_prochaine_tache.setText(String.valueOf(heures) + ":" + String.valueOf(minutes));
                int idd = prochaine_tache.get_idd();
                int i = 0;
                while (i < u.get_dossiers().size() & u.get_dossiers().get(i).get_idd() != idd) {
                    i++;
                }
                Dossier D1 = u.get_dossiers().get(i);
                dossier_prochaine_tache.setText(D1.get_nom_dos());
            }
            else{
                nom_prochaine_tache.setText("Aucune tâche en cours");
                tache_terminee.setVisibility(View.INVISIBLE);
            }

        }
    };



    public View.OnTouchListener SwipeListener = new View.OnTouchListener() {
        int downX, upX;
        @Override

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                downX = (int) motionEvent.getX();
                Log.i("event.getX()","downX" + downX);
                return true;
            }
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                upX=(int) motionEvent.getX();
                Log.i("event.getX()","upX"+ upX);

                if(downX - upX > - 100){
                    prochaine_tache_numero += 1;
                    if(u.getTaches().size()> prochaine_tache_numero){
                        tache_terminee.setVisibility(View.VISIBLE);
                        prochaine_tache = u.get_taches().get(prochaine_tache_numero);
                        nom_prochaine_tache.setText(prochaine_tache.get_nom_tache());
                        int duree = prochaine_tache.get_duree();
                        int heures = duree / 60;
                        int minutes = duree - heures * 60;
                        duree_prochaine_tache.setText(String.valueOf(heures) + ":" + String.valueOf(minutes));
                        int idd = prochaine_tache.get_idd();
                        int i = 0;
                        while (i < u.get_dossiers().size() & u.get_dossiers().get(i).get_idd() != idd) {
                            i++;
                        }
                        Dossier D1 = u.get_dossiers().get(i);
                        dossier_prochaine_tache.setText(D1.get_nom_dos());
                        //u.supprimer_tache(u.get_taches().get(0).get_idt());
                    }
                    else if(u.getTaches().size() == prochaine_tache_numero){
                        nom_prochaine_tache.setText("Aucune tâche en cours");
                        duree_prochaine_tache.setText("");
                        dossier_prochaine_tache.setText("");
                        tache_terminee.setVisibility(View.INVISIBLE);
                        //u.supprimer_tache(u.get_taches().get(0).get_idt());
                    }
                    else if(prochaine_tache_numero > u.getTaches().size()){
                        prochaine_tache_numero -= 1;
                        tache_terminee.setVisibility(View.INVISIBLE);
                    }
                }
                else if(upX-downX > 100){
                    prochaine_tache_numero-=1;
                    if(prochaine_tache_numero>= 0){
                        tache_terminee.setVisibility(View.VISIBLE);
                        prochaine_tache = u.get_taches().get(prochaine_tache_numero);
                        nom_prochaine_tache.setText(prochaine_tache.get_nom_tache());
                        int duree = prochaine_tache.get_duree();
                        int heures = duree / 60;
                        int minutes = duree - heures * 60;
                        duree_prochaine_tache.setText(String.valueOf(heures) + ":" + String.valueOf(minutes));
                        int idd = prochaine_tache.get_idd();
                        int i = 0;
                        while (i < u.get_dossiers().size() & u.get_dossiers().get(i).get_idd() != idd) {
                            i++;
                        }
                        Dossier D1 = u.get_dossiers().get(i);
                        dossier_prochaine_tache.setText(D1.get_nom_dos());
                    }
                    else if(prochaine_tache_numero == -1){
                        prochaine_tache_numero+=1;
                    }
                }
                return true;

            }
            return false;
        }
    };

    public DialogInterface.OnClickListener AjoutDossierListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            EditText nom_dossier = (EditText) ((AlertDialog) dialogInterface).findViewById(R.id.NomDossier);
            int idd = u.ajouter_dossier(nom_dossier.getText().toString());
            subMenu_dossier.add(0,idd,0,nom_dossier.getText().toString());

        }
    };

    public DialogInterface.OnClickListener AnnulerListener = new DialogInterface.OnClickListener(){

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
        }
    };

    public boolean onOptionsItemSelected(MenuItem menuitem){
        if(mToggle.onOptionsItemSelected(menuitem)){
            return true;
        }

        return super.onOptionsItemSelected(menuitem);
    }

    public NavigationView.OnNavigationItemSelectedListener NavigationListener = new NavigationView.OnNavigationItemSelectedListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if(id == R.id.item_Ajout_Tache){
                Intent i1 = new Intent(Page_Principale.this,Ajout_Tache.class);
                i1.putExtra("user",u);
                startActivityForResult(i1,TASK_REQUEST_CODE);
            }
            if(id == R.id.item_deconnexion){
                Intent intent = new Intent(Page_Principale.this, MainActivity.class);
                startActivity(intent);
            }
            if (id == R.id.item_Ajout_Evenement){
                Intent i2 = new Intent (Page_Principale.this, Ajout_Evenement.class);
                i2.putExtra("user",u);
                startActivityForResult(i2,EVENT_REQUEST_CODE);
            }
            if(id == R.id.item_Ajout_Liste){

                Intent i4 = new Intent(Page_Principale.this, Ajout_Liste.class);
                i4.putExtra("user",u);
                startActivityForResult(i4,LIST_REQUEST_CODE);
            }
            if(id == R.id.item_Nouveau_Dossier){
                new AlertDialog.Builder(Page_Principale.this)
                        .setView(R.layout.nouveau_dossier).setPositiveButton("Ajouter", AjoutDossierListener)
                        .setNegativeButton("Annuler", AnnulerListener)
                        .show();
            }
            else if (id != R.id.item_Ajout_Evenement && id != R.id.item_Ajout_Liste && id != R.id.item_Ajout_Tache
                    && id != R.id.item_deconnexion && id != R.id.item_Nouveau_Dossier){
                Intent i5 = new Intent(Page_Principale.this, Affichage_Dossier.class);
                i5.putExtra("idd",id);
                i5.putExtra("user",u);
                i5.putExtra("item dossier",id);
                startActivityForResult(i5, FOLDER_REQUEST_CODE);
            }

            return false;
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
                int ide = mliste_generale.get(item_liste_pos).get_ide();
                liste_nom_evenements.remove(item_liste_pos);
                adapter.notifyDataSetChanged();
                Toast.makeText(this,"Evénement supprimé", Toast.LENGTH_SHORT).show();
                return true;
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


}