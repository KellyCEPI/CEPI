package Objets;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Utilisateur implements Parcelable{

    private int idu;
    private String email;
    private String mdp;
    // pas présent dans la base
    private ArrayList<Tache> taches = new ArrayList<>();
    private ArrayList<Liste> listes = new ArrayList<>();
    private ArrayList<Evenement> evenements = new ArrayList<>();
    private ArrayList<Dossier> dossiers = new ArrayList<>();

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

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public ArrayList<Tache> getTaches() {
        return taches;
    }

    public void setTaches(ArrayList<Tache> taches) {
        this.taches = taches;
    }

    public ArrayList<Liste> getListes() {
        return listes;
    }

    public void setListes(ArrayList<Liste> listes) {
        this.listes = listes;
    }

    public ArrayList<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(ArrayList<Evenement> evenements) {
        this.evenements = evenements;
    }

    public ArrayList<Dossier> getDossiers() {
        return dossiers;
    }

    public void setDossiers(ArrayList<Dossier> dossiers) {
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
        Calendar datej = Calendar.getInstance();

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
    public int diffmin(Calendar d, Calendar d2) {
        int annee = d.get(Calendar.YEAR) - d2.get(Calendar.YEAR);
        int jour = d.get(Calendar.DAY_OF_YEAR) - d2.get(Calendar.DAY_OF_YEAR);
        int heure = d.get(Calendar.HOUR_OF_DAY) - d2.get(Calendar.HOUR_OF_DAY);
        int min = d.get(Calendar.MINUTE) - d2.get(Calendar.MINUTE);

        return (min + heure * 60 + jour * 24 * 60 + annee * 365 * 24 * 60);
    }

    /**
     * Tri de la liste de tache suivant le score
     */
    public void tri_taches() {
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

    public void supprimer_ev(int ide) {
        Boolean t = false;
        int dos = 0;
        for (int i = 0; i < evenements.size(); i--) {
            if (evenements.get(i).get_ide() == ide) {
                dos = evenements.get(i).get_idd();
                evenements.remove(i);
                t = true;
            }
        }
        if (t == false) {
            System.out.println("ERREUR EVENEMENT INTROUVABLE");
        }

        for (int j = 0; j < dossiers.size(); j--) {
            if (dossiers.get(j).get_idd() == dos) {
                for (int k = 0; k < dossiers.get(j).get_evenements().size(); k--) {
                    if (dossiers.get(j).get_evenements().get(k).get_ide() == ide) {
                        dossiers.get(j).get_evenements().remove(k);
                    }

                }

            }
        }
        //ICI INSERTION DE LA FONCTION SUPPRIMER DANS LA BASE DE DONNEES : ide
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        int annee = t.get_d().get(Calendar.YEAR);
        int mois = t.get_d().get(Calendar.MONTH) + 1; // Jan = 1, dec = 12
        int jour = t.get_d().get(Calendar.DAY_OF_MONTH);
        int heure = t.get_d().get(Calendar.HOUR_OF_DAY); // 24 hour clock
        int minute = t.get_d().get(Calendar.MINUTE);
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
        int annee = taches.get(k).get_d().get(Calendar.YEAR);
        int mois = taches.get(k).get_d().get(Calendar.MONTH); // Jan = 0, dec = 11
        int jour = taches.get(k).get_d().get(Calendar.DAY_OF_MONTH);
        int heure = taches.get(k).get_d().get(Calendar.HOUR_OF_DAY); // 24 hour clock
        int minute = taches.get(k).get_d().get(Calendar.MINUTE);
    }


    // NOUVEAU : SUPPRIMER TACHE
    public void supprimer_tache(int idt) {
        Boolean t = false;
        int dos = 0;
        for (int i = 0; i < taches.size(); i--) {
            if (taches.get(i).get_idt() == idt) {
                dos = taches.get(i).get_idd();
                taches.remove(i);
                t = true;
            }
        }
        if (t == false) {
            System.out.println("ERREUR TACHE INTROUVABLE");
        }

        for (int j = 0; j < dossiers.size(); j--) {
            if (dossiers.get(j).get_idd() == dos) {
                for (int k = 0; k < dossiers.get(j).get_taches().size(); k--) {
                    if (dossiers.get(j).get_taches().get(k).get_idt() == idt) {
                        dossiers.get(j).get_taches().remove(k);
                    }

                }

            }
        }
        //ICI INSERTION DE LA FONCTION SUPPRIMER DANS LA BASE DE DONNEES : idt

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

    // NOUVEAU : SUPPRIMER LISTE
    public void supprimer_liste(int idl) {
        Boolean t = false;
        int dos = 0;
        ArrayList<Integer> idlignes = new ArrayList<>();
        for (int i = 0; i < listes.size(); i--) {
            if (listes.get(i).get_idl() == idl) {
                dos = listes.get(i).get_idd();
                for (int p = 0; p < listes.get(i).get_liste().size(); p++) {
                    idlignes.add(listes.get(i).get_liste().get(p).get_idligne());
                }
                listes.remove(i);
                t = true;
            }
        }
        if (t == false) {
            System.out.println("ERREUR LISTE INTROUVABLE");
        }

        for (int j = 0; j < dossiers.size(); j--) {
            if (dossiers.get(j).get_idd() == dos) {
                for (int k = 0; k < dossiers.get(j).get_listes().size(); k--) {
                    if (dossiers.get(j).get_listes().get(k).get_idl() == idl) {
                        dossiers.get(j).get_listes().remove(k);
                    }

                }

            }
        }
        //ICI INSERTION DE LA FONCTION SUPPRIMER DANS LA BASE DE DONNEES : idl
        // SUPPRESSION DES LIGNES : les idlignes sont dans l'arraylist idlignes
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

    public void supprimer_dossier(int idd) {
        Boolean t = false;
        for (int i = 0; i < dossiers.size(); i++) {
            if (dossiers.get(i).get_idd() == idd) {
                dossiers.remove(i);
                t = true;
            }
        }
        if (t == false) {
            System.out.println("ERREUR DOSSIER INTROUVABLE");
        }

        for (int j = 0; j < evenements.size(); j--) {
            if (evenements.get(j).get_idd() == idd) {
                evenements.remove(j);
            }
        }
        for (int j = 0; j < taches.size(); j--) {
            if (taches.get(j).get_idd() == idd) {
                taches.remove(j);
            }
        }
        for (int j = 0; j < listes.size(); j--) {
            if (listes.get(j).get_idd() == idd) {
                listes.remove(j);
            }
        }

    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel user, int flags) {
        user.writeInt(idu);
        user.writeString(email);
        user.writeString(mdp);
        user.writeTypedList(evenements);
        user.writeTypedList(taches);
        user.writeTypedList(listes);
        user.writeTypedList(dossiers);
        System.out.println("        Write into:");
        System.out.println(evenements.get(0).get_nom_ev());
    }

    public final static Parcelable.Creator<Utilisateur> CREATOR = new Parcelable.Creator<Utilisateur>() {
        @Override
        public Utilisateur createFromParcel(Parcel source) {
            return new Utilisateur(source);
        }

        @Override
        public Utilisateur[] newArray(int size) {
            return new Utilisateur[0];
        }
    };

    private Utilisateur(Parcel in) {
        idu = in.readInt();
        email = in.readString();
        mdp = in.readString();
        try {
            in.readTypedList(evenements, Evenement.CREATOR);
        } catch (Exception e) {
            evenements = new ArrayList<>();
        }
        try {
            in.readTypedList(taches, Tache.CREATOR);
        } catch (Exception e) {
            taches = new ArrayList<>();
        }
        try {
            in.readTypedList(listes, Liste.CREATOR);
        } catch (Exception e) {
            listes = new ArrayList<>();
        }
        try {
            in.readTypedList(dossiers, Dossier.CREATOR);
        } catch (Exception e) {
            dossiers = new ArrayList<>();
        }
    }

}