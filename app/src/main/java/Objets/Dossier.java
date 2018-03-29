package Objets;

import java.util.ArrayList;

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

    public ArrayList<Tache> get_taches(){
        return taches;
    }
    public ArrayList<Liste> get_listes(){
        return listes;
    }
    public ArrayList<Evenement> get_evenements(){
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
}