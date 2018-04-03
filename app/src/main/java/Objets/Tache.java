package Objets;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.util.Calendar;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Tache implements Parcelable{

    private String nom_tache;
    private int idt;
    private int idu;
    private int idd;
    private int repeat_nb;
    private int repeat_inter;
    private int duree;
    private int imp;
    private int urgent;
    private int score;
    private Calendar c;

    // incrementer idt suivant base de donnee
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Tache(int idu, String nom_tache, int idd, int repeat_nb, int repeat_inter, int duree, int imp, int urgent) {
        this.idu = idu;
        this.idt = Id.get_idt_max() + 1;
        Id.set_idt_max(Id.get_idt_max() + 1);
        this.nom_tache = nom_tache;
        this.idd = idd;
        this.repeat_nb = repeat_nb;
        this.repeat_inter = repeat_inter;
        this.duree = duree;
        this.imp = imp;
        this.urgent = urgent;
        c = Calendar.getInstance();
    }

    //GETTER
    public int get_score(){
        return score;
    }
    public Calendar get_c(){
        return c;
    }
    public int get_idt() {
        return idt;
    }

    public int get_idu() {
        return idu;
    }

    public String get_nom_tache() {
        return nom_tache;
    }

    public int get_idd() {
        return idd;
    }

    public int get_duree() {
        return duree;
    }

    public int get_repeat_nb() {
        return repeat_nb;
    }

    public int get_repeat_inter() {
        return repeat_inter;
    }

    public int get_imp() {
        return imp;
    }

    public int get_urgent() {
        return urgent;
    }

    //SETTER
    public void set_score(int score){
        this.score=score;
    }
    public void set_ide(int idt) {
        this.idt = idt;
    }

    public void set_idu(int idu) {
        this.idu = idu;
    }

    public void set_nom_tache(String nom_tache) {
        this.nom_tache = nom_tache;
    }

    public void set_idd(int idd) {
        this.idd = idd;
    }

    public void set_date_heure(int duree) {
        this.duree = duree;
    }

    public void set_repeat_nb(int repeat_nb) {
        this.repeat_nb = repeat_nb;
    }

    public void set_repeat_inter(int repeat_inter) {
        this.repeat_inter = repeat_inter;
    }

    public void set_duree(int duree) {
        this.duree = duree;
    }

    public void set_imp(int imp) {
        this.imp = imp;
    }

    public void set_urgent(int urgent) {
        this.urgent = urgent;
    }
    public void set_c(){
        c = Calendar.getInstance();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel task, int flags) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        task.writeString(nom_tache);
        task.writeInt(idt);
        task.writeInt(idu);
        task.writeInt(idd);
        task.writeInt(repeat_nb);
        task.writeInt(repeat_inter);
        task.writeInt(duree);
        task.writeInt(imp);
        task.writeInt(urgent);
        task.writeInt(score);
        task.writeInt(year);
        task.writeInt(month);
        task.writeInt(day);
        task.writeInt(hour);
        task.writeInt(minute);
    }

    public final static Parcelable.Creator<Tache> CREATOR = new Parcelable.Creator<Tache>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Tache createFromParcel(Parcel source) {
            return new Tache(source);
        }

        @Override
        public Tache[] newArray(int size) {
            return new Tache[0];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Tache(Parcel in) {

        this.nom_tache = in.readString();
        this.idt = in.readInt();
        this.idu = in.readInt();
        this.idd = in.readInt();
        this.repeat_nb = in.readInt();
        this.repeat_inter = in.readInt();
        this.duree = in.readInt();
        this.imp = in.readInt();
        this.urgent = in.readInt();
        this.score = in.readInt();
        int year = in.readInt();
        int month = in.readInt();
        int day = in.readInt();
        int hour = in.readInt();
        int minute = in.readInt();

        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute);
        this.c = c;
    }
}