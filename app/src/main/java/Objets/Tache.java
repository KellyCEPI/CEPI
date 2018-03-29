package Objets;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalDateTime;

/**
 * Created by Kelly on 27/03/2018.
 */

public class Tache {
    private int idt;
    private int idu;
    private String nom_tache;
    private int idd;
    private int repeat_nb;
    private int repeat_inter;
    private int duree;
    private int imp;
    private int urgent;
    private LocalDateTime d;
    // incrementer idt suivant base de donnee
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Tache(int idu, String nom_tache, int idd, int repeat_nb, int repeat_inter, int duree, int imp, int urgent){
        this.idu=idu;
        this.idt=Id.get_idt_max()+1;
        Id.set_idt_max(Id.get_idt_max()+1);
        this.nom_tache=nom_tache;
        this.idd=idd;
        this.repeat_nb=repeat_nb;
        this.repeat_inter=repeat_inter;
        this.duree=duree;
        this.imp=imp;
        this.urgent=urgent;
        d=LocalDateTime.now();
    }

    //GETTER

    public int get_idt(){
        return idt;
    }
    public int get_idu(){
        return idu;
    }
    public String get_nom_tache(){
        return nom_tache;
    }
    public int get_idd(){
        return idd;
    }
    public int get_duree(){
        return duree;
    }
    public int get_repeat_nb(){
        return repeat_nb;
    }
    public int get_repeat_inter(){
        return repeat_inter;
    }
    public int get_imp(){
        return imp;
    }
    public int get_urgent(){
        return urgent;
    }

    //SETTER

    public void set_ide(int idt){
        this.idt=idt;
    }
    public void set_idu(int idu){
        this.idu=idu;
    }
    public void set_nom_tache(String nom_tache){
        this.nom_tache=nom_tache;
    }
    public void set_idd(int idd){
        this.idd=idd;
    }
    public void set_date_heure(int duree){
        this.duree=duree;
    }
    public void set_repeat_nb(int repeat_nb){
        this.repeat_nb=repeat_nb;
    }
    public void set_repeat_inter(int repeat_inter){
        this.repeat_inter=repeat_inter;
    }
    public void set_duree(int duree){
        this.duree=duree;
    }
    public void set_imp(int imp){
        this.imp=imp;
    }
    public void set_urgent(int urgent){
        this.urgent=urgent;
    }
}
