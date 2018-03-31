package Objets;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Dossier {

    private int idd;
    private int idu;
    private String nom_dos;
    private ArrayList<Tache> taches;
    private ArrayList<Liste> listes;
    private ArrayList<Evenement> evenements;

    public Dossier(int idu, String nom_dos) {
        this.idd = Id.get_idd_max() + 1;
        Id.set_idd_max(Id.get_idd_max() + 1);
        this.idu = idu;
        this.nom_dos = nom_dos;
    }

    //GETTER
    public int get_idd() {
        return idd;
    }

    public int get_idu() {
        return idu;
    }

    public String get_nom_dos() {
        return nom_dos;
    }

    public ArrayList<Tache> get_taches() {
        return taches;
    }

    public ArrayList<Liste> get_listes() {
        return listes;
    }

    public ArrayList<Evenement> get_evenements() {
        return evenements;
    }

    //SETTER
    public void set_nom_dos(String nom_dos) {
        this.nom_dos = nom_dos;
    }

    // AJOUTER DES ELEMENTS AU DOSSIER
    public void ajouter(Tache t) {
        Boolean k = false;
        for (int i = 0; i < taches.size(); i++) {
            if (taches.get(i).get_idt() == t.get_idt()) {
                System.out.println("ERREUR TACHE DEJA DANS LA LISTE");
                k = true;
            }
        }
        if (k = false) {
            taches.add(t);
        }
    }

    public void ajouter(Evenement e) {
        Boolean k = false;
        for (int i = 0; i < evenements.size(); i++) {
            if (evenements.get(i).get_ide() == e.get_ide()) {
                System.out.println("ERREUR EVENEMENT DEJA DANS LA LISTE");
                k = true;
            }
        }
        if (k = false) {
            evenements.add(e);
        }
    }

    public void ajouter(Liste l) {
        Boolean k = false;
        for (int i = 0; i < listes.size(); i++) {
            if (listes.get(i).get_idl() == l.get_idl()) {
                System.out.println("ERREUR LISTE DEJA DANS LA LISTE");
                k = true;
            }
        }
        if (k = false) {
            listes.add(l);
        }
    }

    public void supprimer(Tache t) {
        for (int i = 0; i < taches.size(); i++) {
            if (taches.get(i).get_idt() == t.get_idt()) {
                taches.set(i, t);
            }
        }
    }

    public void supprimer(Evenement e) {
        for (int i = 0; i < evenements.size(); i++) {
            if (evenements.get(i).get_ide() == e.get_ide()) {
                evenements.set(i, e);
            }
        }
    }

    public void supprimer(Liste l) {
        for (int i = 0; i < listes.size(); i++) {
            if (listes.get(i).get_idl() == l.get_idl()) {
                listes.set(i, l);
            }
        }
    }
    // FONCTIONS DE MODIFICATION

    public void modifier(int idt, String nom_tache, int idd, int repeat_nb, int repeat_inter, int duree, int imp, int urgent) {
        Boolean k = false;
        for (int i = 0; i < taches.size(); i++) {
            if (taches.get(i).get_idt() == idt) {
                taches.get(i).set_nom_tache(nom_tache);
                taches.get(i).set_idd(idd);
                taches.get(i).set_repeat_nb(repeat_nb);
                taches.get(i).set_repeat_inter(repeat_inter);
                taches.get(i).set_duree(duree);
                taches.get(i).set_imp(imp);
                taches.get(i).set_urgent(urgent);
                k = true;
            }
        }
        if (k = false) {
            System.out.println("ERREUR TACHE PAS DANS LA LISTE");
        }
    }

    public void modifier(int ide, String nom_ev, int idd, Calendar date_heure, int repeat_inter) {
        Boolean k = false;
        for (int i = 0; i < evenements.size(); i++) {
            if (evenements.get(i).get_ide() == ide) {
                evenements.get(i).set_nom_ev(nom_ev);
                evenements.get(i).set_idd(idd);
                evenements.get(i).set_date_heure(date_heure);
                evenements.get(i).set_repeat_inter(repeat_inter);
                k = true;
            }
        }
        if (k = false) {
            System.out.println("ERREUR EVENEMENT PAS DANS LA LISTE");
        }
    }

    public void modifier(int idl, String nom_liste, int idd, ArrayList<Ligne> liste_recup_transformee) {
        Boolean k = false;
        for (int i = 0; i < listes.size(); i++) {
            if (listes.get(i).get_idl() == idl) {
                listes.get(i).set_nom_liste(nom_liste);
                listes.get(i).set_idd(idd);
                listes.get(i).set_liste(liste_recup_transformee);
                k = true;
            }
        }
        if (k = false) {
            System.out.println("ERREUR LISTE PAS DANS LA LISTE");
        }
    }

}