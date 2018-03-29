package Objets;

import java.util.ArrayList;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Liste {
    private int idl;
    private int idu;
    private String nom_liste;
    private int idd;
    // pas présente dans la base de données

    private ArrayList<Ligne> liste;
    //incrementer le idl

    public Liste(int idu,String nom_liste,int idd,ArrayList liste){
        this.idu=idu;
        this.idl=Id.get_idl_max()+1;
        Id.set_idl_max(Id.get_idl_max()+1);
        this.nom_liste=nom_liste;
        this.idd=idd;
        this.liste=liste;
    }

    // METHODES POUR ENREGISTRER DE NOUVELLES LIGNES OU EN SUPPRIMER

    public void ajouter_ligne(Ligne ligne){
        liste.add(ligne);
        //attention a revoir
    }

    //GETTER

    public int get_idl(){
        return idl;
    }
    public int get_idu(){
        return idu;
    }
    public String get_nom_liste(){
        return nom_liste;
    }
    public int get_idd(){
        return idd;
    }
    public ArrayList<Ligne> get_liste(){
        return liste;
    }

    //SETTER

    public void set_idl(int idl){
        this.idl=idl;
    }
    public void set_idu(int idu){
        this.idu=idu;
    }
    public void set_nom_liste(String nom_liste){
        this.nom_liste=nom_liste;
    }
    public void set_idd(int idd){
        this.idd=idd;
    }
    public void set_liste(ArrayList<Ligne> l){
        this.liste=l;
    }
}
