package com.example.kelly.cepi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Objets.Evenement;
import java.util.Calendar;

import Objets.Tache;
import Objets.Utilisateur;
/**
 * Created by Kelly on 13/03/2018.
 */



public class Page_Principale extends AppCompatActivity{

    public Utilisateur U1 = new Utilisateur("Toto","mot de passe");

    private DrawerLayout menu_lateral;
    private ActionBarDrawerToggle mToggle;
    SubMenu subMenu_dossier;

    static final int EVENT_REQUEST_CODE = 1;
    static final int TASK_REQUEST_CODE = 2;

    public ListView liste_generale;
    ArrayAdapter<Evenement> adapter = null;
    List<Evenement> mliste_generale = new ArrayList<Evenement>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Si la connexion au serveur est déjà établie: on ne fait rien
        //Sinon :
        //Intent intent = new Intent(Page_Principale.this, Page_Principale.class);
        //startActivity(intent);


        super.onCreate(savedInstanceState);
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



        liste_generale = (ListView) findViewById(R.id.ListeGenerale);
        adapter  = new ArrayAdapter<Evenement>(Page_Principale.this,android.R.layout.simple_list_item_multiple_choice, mliste_generale);
        liste_generale.setAdapter(adapter);
    }

    private void setupDrawer() {
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EVENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Evenement e = data.getParcelableExtra("event");
                String eventName = e.get_nom_ev();
                int year = e.get_date_heure().get(Calendar.YEAR);
                int month = e.get_date_heure().get(Calendar.MONTH)+1;
                int day = e.get_date_heure().get(Calendar.DAY_OF_MONTH);
                System.out.println("        Résultat: ");
                System.out.println("Nom de l'envènement: "+eventName);
                System.out.println("Date choisie: "+day+"/"+month+"/"+year);
            }
        } else if (requestCode == TASK_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Tache t = data.getParcelableExtra("tache");
                String taskName = t.get_nom_tache();
                int duree = t.get_duree();
                System.out.println("        Résultat");
                System.out.println(taskName+" "+duree);
            }
        }
    }


    public DialogInterface.OnClickListener AjoutDossierListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            EditText nom_dossier = (EditText) ((AlertDialog) dialogInterface).findViewById(R.id.NomDossier);
            int idd = U1.ajouter_dossier(nom_dossier.getText().toString());
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

            if(id== R.id.item_Ajout_Tache){
                Intent i1 = new Intent(Page_Principale.this,Ajout_Tache.class);
                startActivityForResult(i1,TASK_REQUEST_CODE);
            }
            if(id == R.id.item_deconnexion){
                Toast.makeText(Page_Principale.this,"toto",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Page_Principale.this, MainActivity.class);
                startActivity(intent);
            }
            if (id == R.id.item_Ajout_Evenement){
                Intent i2 = new Intent (Page_Principale.this, Ajout_Evenement.class);
                startActivityForResult(i2,EVENT_REQUEST_CODE);
            }
            if(id == R.id.item_Ajout_Liste){
                Intent i4 = new Intent(Page_Principale.this, Ajout_Liste.class);
                startActivity(i4);
            }
            if(id == R.id.item_Nouveau_Dossier){
                new AlertDialog.Builder(Page_Principale.this)
                        .setView(R.layout.nouveau_dossier).setPositiveButton("Ajouter", AjoutDossierListener)
                        .setNegativeButton("Annuler", AnnulerListener)
                        .show();
            }
            else if (id != R.id.item_Ajout_Evenement && id != R.id.item_Ajout_Liste && id != R.id.item_Ajout_Tache
                    && id != R.id.item_deconnexion && id != R.id.item_Nouveau_Dossier){
                Intent i_dossier = new Intent(Page_Principale.this, Affichage_Dossier.class);
                i_dossier.putExtra("idd",id);
                startActivity(i_dossier);
            }

            return false;
        }
    };


}