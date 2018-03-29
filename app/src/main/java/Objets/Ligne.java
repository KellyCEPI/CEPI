package Objets;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Ligne {
    private int idl;
    private int idligne;
    private int cocher;
    private String ligne;
    //incrementer le idl
    public Ligne(int idl,int cocher,String ligne){
        this.idl=idl;
        this.idligne=Id.get_idligne_max()+1;
        Id.set_idligne_max(Id.get_idligne_max()+1);
        this.cocher=cocher;
        this.ligne=ligne;
    }

    // SETTER
    public void set_cocher(int cocher){
        this.cocher=cocher;
    }
    public void set_ligne(String ligne){
        this.ligne=ligne;
    }
    //GETTER
    public int get_idligne(){
        return idligne;
    }
    public int get_idl(){
        return idl;
    }
    public int get_cocher(){
        return cocher;
    }
    public String get_ligne(){
        return ligne;
    }
}
