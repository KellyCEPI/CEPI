package Objets;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Utilisateur implements Serializable {
    private int idu;
    private String email;
    private String mdp;
    // pas présent dans la base
    private ArrayList<Tache> taches;
    private ArrayList<Liste> listes;
    private ArrayList<Evenement> evenements;
    private ArrayList<Dossier> dossiers;
    //incrementer le idu

    public Utilisateur(String email, String mdp) {
        this.idu = Id.get_idu_max() + 1;
        Id.set_idu_max(Id.get_idu_max() + 1);
        this.email = email;
        this.mdp = mdp;
    }

    //POUR TROUVER L'ID D'UN DOSSIER QUAND ON A SON NOM ( PERMET DE REMPLIR LES FONCTIONS AJOUT

    public int rechercher_dos(String nom_dos) {
        int i = 0;
        Boolean k = false;
        int idd = 0;
        for (i = 0; i < dossiers.size(); i++) {
            if (dossiers.get(i).get_nom_dos() == nom_dos) {
                k = true;
                idd = dossiers.get(i).get_idd();

            }
        }
        if (k) {
            return idd;
        } else {
            System.out.println("ERREUR NOM DE DOSSIER INTROUVABLE");
            return -1;
        }
    }

    //CREATION EVENEMENT
    public void ajouter_ev(String nom_ev, int idd, Calendar date_heure, int repeat_inter) {
        //il faut le ide max de la base de donnée pour prendre ide+1
        Evenement ev = new Evenement(idu, nom_ev, idd, date_heure, repeat_inter);
        evenements.add(ev);
        // Ajouter l'ev dans la base de donnée
    }

    //Les fonctions de modifications doivent s'appliquer directement sur un ev
    public void modifier_ev(int ide, String nom_ev, int idd, Calendar date_heure, int repeat_inter) {
        //
        int taille = evenements.size();
        int i = 0;
        for (i = 0; i < taille; i++) {
            if (evenements.get(i).get_ide() == ide) {
                evenements.get(i).set_nom_ev(nom_ev);
                evenements.get(i).set_idd(idd);
                evenements.get(i).set_date_heure(date_heure);
                evenements.get(i).set_repeat_inter(repeat_inter);
            }
        }
        // modifier l'évenement dans la base de donnée qui correspond à ide
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ajouter_tache(String nom_tache, int idd, int repeat_nb, int repeat_inter, int duree, int imp, int urgent) {
        Tache t = new Tache(idu, nom_tache, idd, repeat_nb, repeat_inter, duree, imp, urgent);
        taches.add(t);
        //ajouter la tache dans la base de donnée
    }

    public void modifier_tache(int idt, String nom_tache, int idd, int repeat_nb, int repeat_inter, int duree, int imp, int urgent) {
        //
        int taille = taches.size();
        int i = 0;
        for (i = 0; i < taille; i++) {
            if (taches.get(i).get_idt() == idt) {
                taches.get(i).set_nom_tache(nom_tache);
                taches.get(i).set_idd(idd);
                taches.get(i).set_repeat_nb(repeat_nb);
                taches.get(i).set_repeat_inter(repeat_inter);
                taches.get(i).set_duree(duree);
                taches.get(i).set_imp(imp);
                taches.get(i).set_urgent(urgent);
            }
        }
        // modifier la tache dans la base de donnée qui correspond à idt
    }

    //Action à effectuer sur le bouton valider de "ajouter une nouvelle liste"
    public void ajouter_liste(String nom_liste, int idd, ArrayList<String> liste_recup) {
        ArrayList<Ligne> liste = new ArrayList();
        Liste l = new Liste(idu, nom_liste, idd, liste);
        int i = 0;
        for (i = 0; i < liste_recup.size(); i++) {
            String s = liste_recup.get(i);
            Ligne ligne = new Ligne(l.get_idl(), 0, s);
            //ajouter chaque ligne dans la base de donnée ici
            liste.add(ligne);
        }
        listes.add(l);
        //ajouter la liste dans la base de donnée
    }

    // Il faut une fonction de conversion pour transformer la liste de string et de boolean en
    // arrayList de lignes pour ensuite aller modifier
    public void modifier_liste(int idl, String nom_liste, int idd, ArrayList<Ligne> liste_recup_transformee) {
        //
        int taille = listes.size();
        int i = 0;
        for (i = 0; i < taille; i++) {
            if (listes.get(i).get_idl() == idl) {
                listes.get(i).set_nom_liste(nom_liste);
                listes.get(i).set_idd(idd);
                listes.get(i).set_liste(liste_recup_transformee);
                idl = listes.get(i).get_idl();
            }
        }
        // modifier la liste dans la base de donnée qui correspond à idl
        // ATTENTION, ICI IL FAUT AUSSI MODIFIER CHAQUE LIGNE DANS LA TABLE LIGNE DE LA BASE
        // A partir de l'idl et de l'idu, on copie les idligne correspondant et on les modifie, si il y en a plus qu'avant
        // on rajoute ceux de la fin de liste_recup_transformée
    }

    public int ajouter_dossier(String nom_dos) {
        Dossier d = new Dossier(idu, nom_dos);
        dossiers.add(d);
        //ajouter à la base de donnée selon idd
        //ajouter sur l'interface
        return d.get_idd();
    }

    public void modifier_dossier(int idd, String nom_dos) {
        int taille = dossiers.size();
        int i = 0;
        for (i = 0; i < taille; i++) {
            if (dossiers.get(i).get_idd() == idd) {
                dossiers.get(i).set_nom_dos(nom_dos);
            }
            //modifier dans la base de donnée selon idd
            //modifier sur l'interface
        }
    }

    public ArrayList<Dossier> get_dossiers(){
        return dossiers;
    }

}


