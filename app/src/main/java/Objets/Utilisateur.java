package Objets;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Utilisateur {

    private int idu;
    private String email;
    private String mdp;
    // pas présent dans la base
    private ArrayList<Tache> taches;
    private ArrayList<Liste> listes;
    private ArrayList<Evenement> evenements;
    private ArrayList<Dossier> dossiers;

    public Utilisateur(String email, String mdp) {
        this.idu = Id.get_idu_max() + 1;
        Id.set_idu_max(Id.get_idu_max() + 1);
        this.email = email;
        this.mdp = mdp;
    }

    //GETTER
    public int get_idu() {
        return idu;
    }

    public String get_email() {
        return email;
    }

    public String get_mdp() {
        return mdp;
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

    public ArrayList<Dossier> get_dossiers() {
        return dossiers;
    }

    //SETTER
    public void set_email(String email) {
        this.email = email;
    }

    public void set_mdp(String mdp) {
        this.mdp = mdp;
    }

    public void set_taches(ArrayList<Tache> taches) {
        this.taches = taches;
    }

    public void set_listes(ArrayList<Liste> listes) {
        this.listes = listes;
    }

    public void set_evenements(ArrayList<Evenement> evenements) {
        this.evenements = evenements;
    }

    public void set_dossiers(ArrayList<Dossier> dossiers) {
        this.dossiers = dossiers;
    }

    /**
     * Défini le score d'une tache t
     *
     * @param t
     */
    public void scoring(Tache t) {

        int a, b;
        a = t.get_imp();
        b = t.get_urgent();
        LocalDateTime datej = LocalDateTime.now();
        int temps = diffmin(datej, t.get_d());

        switch (b) {
            case 0:
                b = (int) (b + temps * (0.018 / 50));
                break;
            case 1:
                b = (int) (b + temps * (0.02 / 50));
                break;
            case 2:
                b = (int) (b + temps * (0.025 / 50));
                break;
            case 3:
                b = (int) (b + temps * (0.04 / 50));
                break;
            case 4:
                b = (int) (b + temps * (0.09 / 50));
                break;
            case 5:
                b = (int) (b + temps * (1.5 / 50));
                break;

        }

        a = (int) (a + temps * (0.07 / 50));
        t.set_score(3 * a + b);
        if (b >= 5) {
            b = 5;
        }
        t.set_urgent(b);

    }

    /**
     * Donne la différence de temps en min entre deux dates
     *
     * @param d
     * @param d2
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int diffmin(LocalDateTime d, LocalDateTime d2) {
        int annee = d.getYear() - d2.getYear();
        int jour = d.getDayOfYear() - d2.getDayOfYear();
        int heure = d.getHour() - d2.getHour();
        int min = d.getMinute() - d2.getMinute();

        return (min + heure * 60 + jour * 24 * 60 + annee * 365 * 24 * 60);
    }

    /**
     * Tri de la liste de tache suivant le score
     */
    public void tri_insertion() {
        int taille = taches.size();

        int i, j;
        for (i = 0; i < taille; ++i) {
            Tache elem = taches.get(i);
            scoring(elem);
            int scr = elem.get_score();
            for (j = i; j > 0 && taches.get(j - 1).get_score() > scr; j--) {
                taches.set(j, taches.get(j - 1));
            }
            taches.set(j, elem);
        }
        Collections.reverse(taches);
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
    //MODIFIER L UTILISATEUR

    public void modifier_utilisateur(String email, String mdp) {
        this.email = email;
        this.mdp = mdp;
    }

    //CREATION EVENEMENT
    public void ajouter_ev(String nom_ev, int idd, Calendar date_heure, int repeat_inter) {

        Evenement ev = new Evenement(idu, nom_ev, idd, date_heure, repeat_inter);
        evenements.add(ev);
        for (int i = 0; i < dossiers.size(); i++) {
            if (dossiers.get(i).get_idd() == idd) {
                dossiers.get(i).ajouter(ev);
            } else {
                System.out.println("ERREUR DOSSIER INTROUVABLE");
            }
        }
        //données préparées pour la base de données : prendre ev.ide, idu, ev.nom_ev, ev.idd, ev.repeat_inter et les
        //variables suivantes
        int annee = ev.get_date_heure().get(Calendar.YEAR);
        int mois = ev.get_date_heure().get(Calendar.MONTH); // Jan = 0, dec = 11
        int jour = ev.get_date_heure().get(Calendar.DAY_OF_MONTH);
        int heure = ev.get_date_heure().get(Calendar.HOUR_OF_DAY); // 24 hour clock
        int minute = ev.get_date_heure().get(Calendar.MINUTE);
        // Ajouter l'ev dans la base de donnée
    }

    //Les fonctions de modifications doivent s'appliquer directement sur un ev
    public void modifier_ev(int ide, String nom_ev, int idd, Calendar date_heure, int repeat_inter) {
        int i = 0;
        int k = 0;
        for (i = 0; i < evenements.size(); i++) {

            if (evenements.get(i).get_ide() == ide) {
                evenements.get(i).set_nom_ev(nom_ev);
                evenements.get(i).set_date_heure(date_heure);
                evenements.get(i).set_repeat_inter(repeat_inter);

                if (evenements.get(i).get_idd() != idd) {
                    //on supprime l'evenement dans le dossier d'avant
                    for (int j = 0; j < dossiers.size(); j++) {
                        if (dossiers.get(j).get_idd() == evenements.get(i).get_idd()) {
                            dossiers.get(j).supprimer(evenements.get(i));
                        }
                    }
                    evenements.get(i).set_idd(idd);
                    //on ajoute l'evenement dans le nouveau dossier
                    for (int p = 0; p < dossiers.size(); p++) {
                        if (dossiers.get(p).get_idd() == idd) {
                            dossiers.get(p).ajouter(evenements.get(i));
                        } else {
                            System.out.println("ERREUR DOSSIER INTROUVABLE");
                        }
                    }
                } else {
                    for (int a = 0; a < dossiers.size(); a++) {
                        if (dossiers.get(a).get_idd() == idd) {
                            dossiers.get(a).modifier(ide, nom_ev, idd, date_heure, repeat_inter);
                        }
                    }
                }
                k = i;
            }
        }
        // modifier l'évenement dans la base de donnée qui correspond à ide
        //données A MODIFIER pour la base de données : idu, evenements.get(k).get_nom_ev(), evenements.get(k).get_ev.idd(),
        //evenements.get(k).get_repeat_inter()
        //et les variables suivantes
        int annee = date_heure.get(Calendar.YEAR);
        int mois = date_heure.get(Calendar.MONTH) + 1; // Jan = 1, dec = 12
        int jour = date_heure.get(Calendar.DAY_OF_MONTH);
        int heure = date_heure.get(Calendar.HOUR_OF_DAY); // 24 hour clock
        int minute = date_heure.get(Calendar.MINUTE);
    }

    public void ajouter_tache(String nom_tache, int idd, int repeat_nb, int repeat_inter, int duree, int imp, int urgent) {
        Tache t = new Tache(idu, nom_tache, idd, repeat_nb, repeat_inter, duree, imp, urgent);
        taches.add(t);
        for (int i = 0; i < dossiers.size(); i++) {
            if (dossiers.get(i).get_idd() == idd) {
                dossiers.get(i).ajouter(t);
            } else {
                System.out.println("ERREUR DOSSIER INTROUVABLE");
            }
        }

        // ATTENTION IL FAUT MODIFIER/AJOUTER AUSSI DANS LE DOSSIER
        //ajouter la tache dans la base de donnée
        //données préparées pour la base de données : prendre t.idt, idu, t.nom_tache, t.idd, t.repeat_nb,
        //t.repeat_inter, t.duree, t.imp, t.urgent
        //variables suivantes
        int annee = t.get_d().getYear();
        int mois = t.get_d().getMonthValue(); // Jan = 1, dec = 12
        int jour = t.get_d().getDayOfMonth();
        int heure = t.get_d().getHour(); // 24 hour clock
        int minute = t.get_d().getMinute();
    }

    public void modifier_tache(int idt, String nom_tache, int idd, int repeat_nb, int repeat_inter, int duree, int imp, int urgent) {
        //PROBLEME : EST CE QUE D EST MODIFIE QUAND ON MODIFIE LA TACHE ?
        int taille = taches.size();
        int i = 0;
        int k = -1;
        for (i = 0; i < taille; i++) {
            if (taches.get(i).get_idt() == idt) {
                taches.get(i).set_nom_tache(nom_tache);
                taches.get(i).set_idd(idd);
                taches.get(i).set_repeat_nb(repeat_nb);
                taches.get(i).set_repeat_inter(repeat_inter);
                taches.get(i).set_duree(duree);
                // on verifie l'etat de base de imp et urgence
                if (taches.get(i).get_imp() != imp || taches.get(i).get_urgent() != urgent) {
                    taches.get(i).set_imp(imp);
                    taches.get(i).set_urgent(urgent);
                    taches.get(i).set_d();
                }
                if (taches.get(i).get_idd() != idd) {
                    //on supprime l'evenement dans le dossier d'avant
                    for (int j = 0; j < dossiers.size(); j++) {
                        if (dossiers.get(j).get_idd() == taches.get(i).get_idd()) {
                            dossiers.get(j).supprimer(taches.get(i));
                        }
                    }
                    taches.get(i).set_idd(idd);
                    //on ajoute l'evenement dans le nouveau dossier
                    for (int p = 0; p < dossiers.size(); p++) {
                        if (dossiers.get(p).get_idd() == idd) {
                            dossiers.get(p).ajouter(taches.get(i));
                        } else {
                            System.out.println("ERREUR DOSSIER INTROUVABLE");
                        }
                    }
                } else {
                    for (int a = 0; a < dossiers.size(); a++) {
                        if (dossiers.get(a).get_idd() == idd) {
                            dossiers.get(a).modifier(idt, nom_tache, idd, repeat_nb, repeat_inter, duree, imp, urgent);
                        }
                    }
                }
                k = i;
            }
        }
        // modifier la tache dans la base de donnée qui correspond à idt
        //ajouter la tache dans la base de donnée
        //données préparées pour la base de données : idu, taches.get(k).get.nom_tache(), taches.get(k).get_idd(),
        //taches.get(k).get_repeat_nb(),taches.get(k).get_repeat_inter(), taches.get(k).get_duree(), taches.get(k).get_imp(),
        //taches.get(k).get_urgent()
        //variables suivantes
        int annee = taches.get(k).get_d().getYear();
        int mois = taches.get(k).get_d().getMonthValue(); // Jan = 1, dec = 12
        int jour = taches.get(k).get_d().getDayOfMonth();
        int heure = taches.get(k).get_d().getHour(); // 24 hour clock
        int minute = taches.get(k).get_d().getMinute();
    }

    //Action à effectuer sur le bouton valider de "ajouter une nouvelle liste"
    public void ajouter_liste(String nom_liste, int idd, ArrayList<String> liste_recup) {
        ArrayList<Ligne> liste = new ArrayList();
        Liste l = new Liste(idu, nom_liste, idd, liste);
        int i = 0;
        for (i = 0; i < liste_recup.size(); i++) {
            String s = liste_recup.get(i);
            Ligne ligne = new Ligne(l.get_idl(), 0, s);
            liste.add(ligne);//ajouter chaque ligne dans la base de donnée ici


        }
        listes.add(l);

        for (int j = 0; j < dossiers.size(); j++) {
            if (dossiers.get(j).get_idd() == idd) {
                dossiers.get(j).ajouter(l);
            } else {
                System.out.println("ERREUR DOSSIER INTROUVABLE");
            }
        }
        //ajouter la liste dans la base de donnée
        // ET CHAQUE LIGNE DE LA LISTE
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
                if (listes.get(i).get_idd() != idd) {
                    //on supprime l'evenement dans le dossier d'avant
                    for (int j = 0; j < dossiers.size(); j++) {
                        if (dossiers.get(j).get_idd() == listes.get(i).get_idd()) {
                            dossiers.get(j).supprimer(listes.get(i));
                        }
                    }
                    listes.get(i).set_idd(idd);
                    //on ajoute l'evenement dans le nouveau dossier
                    for (int p = 0; p < dossiers.size(); p++) {
                        if (dossiers.get(p).get_idd() == idd) {
                            dossiers.get(p).ajouter(listes.get(i));
                        } else {
                            System.out.println("ERREUR DOSSIER INTROUVABLE");
                        }
                    }
                } else {
                    for (int a = 0; a < dossiers.size(); a++) {
                        if (dossiers.get(a).get_idd() == idd) {
                            dossiers.get(a).modifier(idl, nom_liste, idd, liste_recup_transformee);
                        }
                    }
                }
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
        Boolean k = false;
        for (int i = 0; i < dossiers.size(); i++) {
            if (dossiers.get(i).get_idd() == idd) {
                dossiers.get(i).set_nom_dos(nom_dos);
                k = true;
            }
            //modifier dans la base de donnée selon idd
            //modifier sur l'interface
        }
        if (k==false){
            System.out.println("ERREUR DOSSIER INTROUVABLE");
        }
    }
}