package Objets;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Ligne implements Parcelable{

    private int idl;
    private int idligne;
    private int cocher;
    private String ligne;

    //incrementer le idl
    public Ligne(int idl, int cocher, String ligne) {
        this.idl = idl;
        this.idligne = Id.get_idligne_max() + 1;
        Id.set_idligne_max(Id.get_idligne_max() + 1);
        this.cocher = cocher;
        this.ligne = ligne;
    }

    // SETTER
    public void set_cocher(int cocher) {
        this.cocher = cocher;
    }

    public void set_ligne(String ligne) {
        this.ligne = ligne;
    }

    //GETTER
    public int get_idligne() {
        return idligne;
    }

    public int get_idl() {
        return idl;
    }

    public int get_cocher() {
        return cocher;
    }

    public String get_ligne() {
        return ligne;
    }

    public int getIdl() {
        return idl;
    }

    public void setIdl(int idl) {
        this.idl = idl;
    }

    public int getIdligne() {
        return idligne;
    }

    public void setIdligne(int idligne) {
        this.idligne = idligne;
    }

    public int getCocher() {
        return cocher;
    }

    public void setCocher(int cocher) {
        this.cocher = cocher;
    }

    public String getLigne() {
        return ligne;
    }

    public void setLigne(String ligne) {
        this.ligne = ligne;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcelLigne, int flags) {
        parcelLigne.writeInt(idl);
        parcelLigne.writeInt(idligne);
        parcelLigne.writeInt(cocher);
        parcelLigne.writeString(ligne);
    }

    public final static Parcelable.Creator<Ligne> CREATOR = new Parcelable.Creator<Ligne>() {
        @Override
        public Ligne createFromParcel(Parcel source) {
            return new Ligne(source);
        }

        @Override
        public Ligne[] newArray(int size) {
            return new Ligne[0];
        }
    };

    private Ligne(Parcel in) {
        this.idl = in.readInt();
        this.idligne = in.readInt();
        this.cocher = in.readInt();
        this.ligne = in.readString();
    }
}