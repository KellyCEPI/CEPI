package Objets;

import java.util.Calendar;

/**
 * Created by Kelly on 22/03/2018.
 */

public class Evenement {

    private int ide;
    private int idu;
    private String nom_ev;
    private int idd; //id dossier
    private Calendar date_heure;
    private int repeat_inter;

    // incrementer le ide
    public Evenement(int idu, String nom_ev, int idd, Calendar date_heure, int repeat_inter) {
        this.ide = Id.get_ide_max() + 1;
        Id.set_ide_max(Id.get_ide_max() + 1);
        this.idu = idu;
        this.nom_ev = nom_ev;
        this.idd = idd;
        this.date_heure = date_heure;
        this.repeat_inter = repeat_inter;
    }

    //GETTER
    public int get_ide() {
        return ide;
    }

    public int get_idu() {
        return idu;
    }

    public String get_nom_ev() {
        return nom_ev;
    }

    public int get_idd() {
        return idd;
    }

    public Calendar get_date_heure() {
        return date_heure;
    }

    public int get_repeat_inter() {
        return repeat_inter;
    }

    //SETTER
    public void set_ide(int ide) {
        this.ide = ide;
    }

    public void set_idu(int idu) {
        this.idu = idu;
    }

    public void set_nom_ev(String nom_ev) {
        this.nom_ev = nom_ev;
    }

    public void set_idd(int idd) {
        this.idd = idd;
    }

    public void set_date_heure(Calendar date_heure) {
        this.date_heure = date_heure;
    }

    public void set_repeat_inter(int repeat_inter) {
        this.repeat_inter = repeat_inter;
    }

}