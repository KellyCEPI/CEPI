package Objets;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Kelly on 22/03/2018.
 */

public class Evenement implements Parcelable{

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

    //intToCalendar

    public Calendar intToCalendar(int year, int month, int day, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute);
        return c;
    }

    //CalendarToInt

    public List<Integer> CalendarToInt(Calendar c) {
        List<Integer> l = new ArrayList<>();
        l.add(c.get(Calendar.YEAR));
        l.add(c.get(Calendar.MONTH));
        l.add(c.get(Calendar.DAY_OF_MONTH));
        l.add(c.get(Calendar.HOUR));
        l.add(c.get(Calendar.MINUTE));
        return l;
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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel event, int flags) {
        int year = date_heure.get(Calendar.YEAR);
        int month = date_heure.get(Calendar.MONTH);
        int day = date_heure.get(Calendar.DAY_OF_MONTH);
        int hour = date_heure.get(Calendar.HOUR);
        int minute = date_heure.get(Calendar.MINUTE);
        //Attention au sens!!!
        event.writeString(nom_ev);
        event.writeInt(ide);
        event.writeInt(idu);
        event.writeInt(idd);
        event.writeInt(repeat_inter);
        event.writeInt(year);
        event.writeInt(month);
        event.writeInt(day);
        event.writeInt(hour);
        event.writeInt(minute);
        System.out.println("        Write to parcel: ");
        System.out.println(year+"  "+month+"  "+day+"  ");
    }

    public final static Parcelable.Creator<Evenement> CREATOR = new Parcelable.Creator<Evenement>() {
        @Override
        public Evenement createFromParcel(Parcel source) {
            return new Evenement(source);
        }

        @Override
        public Evenement[] newArray(int size) {
            return new Evenement[0];
        }
    };

    private Evenement(Parcel in) {
        this.nom_ev = in.readString();
        this.ide = in.readInt();
        this.idu = in.readInt();
        this.idd = in.readInt();
        this.repeat_inter = in.readInt();
        int year = in.readInt();
        int month = in.readInt();
        int day = in.readInt();
        int hour = in.readInt();
        int minute = in.readInt();
        System.out.println("        Parcel in: ");
        System.out.println(year+"  "+month+"  "+day+"  ");

        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute);
        this.date_heure = c;
    }

}