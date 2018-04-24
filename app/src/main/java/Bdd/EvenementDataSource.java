package Bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import Objets.Evenement;

/**
 *
 * @author rg514_000
 */
public class EvenementDataSource {
    //Champs de la base de données
    private static final int  VERSION_BDD = 1;
    private static final String NOM_BDD = "evenement.db";
    private static final String EVENEMENT_TABLE_NAME = "evenement";
    private static final String EVENEMENT_KEY = "ide";
    private static final int EVENEMENT_KEY_NUM = 0;
    private static final String EVENEMENT_NOM = "nom_evenement";
    private static final int EVENEMENT_NOM_NUM = 1;
    private static final String EVENEMENT_ANNEE = "annee";
    private static final int EVENEMENT_ANNEE_NUM = 2;
    private static final String EVENEMENT_MOIS = "mois";
    private static final int EVENEMENT_MOIS_NUM = 3;
    private static final String EVENEMENT_JOUR = "jour";
    private static final int EVENEMENT_JOUR_NUM = 4;
    private static final String EVENEMENT_HEURE_DEBUT = "heure_debut";
    private static final int EVENEMENT_HEURE_DEBUT_NUM = 5;
    private static final String EVENEMENT_MINUTE_DEBUT = "minute debut";
    private static final int EVENEMENT_MINUTE_DEBUT_NUM = 6;
    private static final String EVENEMENT_REPEAT_INTER = "repeat inter";
    private static final int EVENEMENT_REPEAT_INTER_NUM = 7;

    private SQLiteDatabase bdd;

    private EvenementDAO u;



    public EvenementDataSource(Context context){
        u = new  EvenementDAO(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        bdd = u.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long createEvenement(String nom, String annee, String mois,
                                String jour, String heure_debut, String minute_debut, String repeat_inter){
        ContentValues values = new ContentValues();
        values.put(EVENEMENT_NOM, nom);
        values.put(EVENEMENT_ANNEE, annee);
        values.put(EVENEMENT_MOIS, mois);
        values.put(EVENEMENT_JOUR, jour);
        values.put(EVENEMENT_HEURE_DEBUT, heure_debut);
        values.put(EVENEMENT_MINUTE_DEBUT, minute_debut);
        values.put(EVENEMENT_REPEAT_INTER, repeat_inter);
        return bdd.insert(EVENEMENT_TABLE_NAME, null, values);
    }

    public int updateEvenement (int id, Evenement evenement, String annee, String mois, String jour, String heure, String minute){
        ContentValues values =  new ContentValues();
        values.put(EVENEMENT_NOM, evenement.get_nom_ev());
        values.put(EVENEMENT_ANNEE, annee);
        values.put(EVENEMENT_MOIS, mois);
        values.put(EVENEMENT_JOUR, jour);
        values.put(EVENEMENT_HEURE_DEBUT, heure);
        values.put(EVENEMENT_MINUTE_DEBUT, minute);
        values.put(EVENEMENT_REPEAT_INTER, evenement.get_repeat_inter());
        return bdd.update(EVENEMENT_TABLE_NAME, values, EVENEMENT_KEY + " = " +id, null);
    }

    public int deleteEvenement (Evenement evenement){
        int idu = evenement.get_ide();
        return bdd.delete(EVENEMENT_TABLE_NAME, EVENEMENT_KEY + " = " + idu, null);
    }


}

