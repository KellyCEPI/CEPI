package Objets;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Liste implements Parcelable{

    private int idl;
    private int idu;
    private String nom_liste;
    private int idd;
    // pas présente dans la base de données

    private ArrayList<Ligne> liste = new ArrayList<>();
    //incrementer le idl

    public Liste(int idu, String nom_liste, int idd, ArrayList liste) {
        this.idu = idu;
        this.idl = Id.get_idl_max() + 1;
        Id.set_idl_max(Id.get_idl_max() + 1);
        this.nom_liste = nom_liste;
        this.idd = idd;
        this.liste = liste;
    }

    // METHODES POUR ENREGISTRER DE NOUVELLES LIGNES OU EN SUPPRIMER
    public void ajouter_ligne(Ligne ligne) {
        liste.add(ligne);
        //attention a revoir
    }

    //GETTER
    public int get_idl() {
        return idl;
    }

    public int get_idu() {
        return idu;
    }

    public String get_nom_liste() {
        return nom_liste;
    }

    public int get_idd() {
        return idd;
    }

    public ArrayList<Ligne> get_liste() {
        return liste;
    }

    //SETTER
    public void set_idl(int idl) {
        this.idl = idl;
    }

    public void set_idu(int idu) {
        this.idu = idu;
    }

    public void set_nom_liste(String nom_liste) {
        this.nom_liste = nom_liste;
    }

    public void set_idd(int idd) {
        this.idd = idd;
    }

    public void set_liste(ArrayList<Ligne> l) {
        this.liste = l;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel list, int flags) {
        list.writeInt(idl);
        list.writeInt(idu);
        list.writeInt(idd);
        list.writeString(nom_liste);
        list.writeTypedList(liste);
        System.out.println("        Write:");
        String s = liste.get(0).get_ligne();
        System.out.println(nom_liste+"  "+s);
    }

    public final static Parcelable.Creator<Liste> CREATOR = new Parcelable.Creator<Liste>() {
        @Override
        public Liste createFromParcel(Parcel source) {
            return new Liste(source);
        }

        @Override
        public Liste[] newArray(int size) {
            return new Liste[0];
        }
    };

    private Liste(Parcel in) {
        idl = in.readInt();
        idu = in.readInt();
        idd = in.readInt();
        nom_liste = in.readString();
        try {
            in.readTypedList(this.liste, Ligne.CREATOR);
        } catch(Exception e) {
            liste = new ArrayList<>();
        }
    }
}