package Bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import Objets.Utilisateur;

/**
 *
 * @author rg514_000
 */
public class UtilisateurDataSource {

    //Champs de la base de données
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "agenda.db";
    private static final String UTILISATEUR_TABLE_NAME = "utilisateur";
    private static final String UTILISATEUR_KEY = "idu";
    private static final int UTILISATEUR_KEY_NUM = 0;
    private static final String UTILISATEUR_EMAIL = "email";
    private static final int UTILISATEUR_EMAIL_NUM = 1;
    private static final String UTILISATEUR_MDP = "mdp";
    private static final int UTILISATEUR_MDP_NUM = 2;

    private SQLiteDatabase bdd;

    private UtilisateurDAO u;

    public UtilisateurDataSource(Context context){
        u = new  UtilisateurDAO(context, NOM_BDD, null, VERSION_BDD);
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

    public long createUtilisateur(String email, String mdp){
        ContentValues values = new ContentValues();
        values.put(UTILISATEUR_EMAIL, email);
        values.put(UTILISATEUR_MDP, mdp);
        return bdd.insert(UTILISATEUR_TABLE_NAME, null, values);
    }

    public int updateUtilisateur (int id, Utilisateur utilisateur){
        ContentValues values =  new ContentValues();
        values.put(UTILISATEUR_EMAIL, utilisateur.get_email());
        values.put(UTILISATEUR_MDP, utilisateur.get_mdp());
        return bdd.update(UTILISATEUR_TABLE_NAME, values, UTILISATEUR_KEY + " = " +id, null);
    }

    public int deleteUtilisateur (Utilisateur utilisateur){
        int idu = utilisateur.get_idu();
        return bdd.delete(UTILISATEUR_TABLE_NAME, UTILISATEUR_KEY + " = " + idu, null);
    }

    public Utilisateur getUtilisateurWithEmail(String email) {
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(UTILISATEUR_TABLE_NAME,
                new String[]{UTILISATEUR_KEY, UTILISATEUR_EMAIL, UTILISATEUR_MDP},
                UTILISATEUR_EMAIL + " LIKE \"" + email + "\"", null, null, null, null);
        return cursorToUtilisateur(c);
    };

    private Utilisateur cursorToUtilisateur(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Utilisateur u = new Utilisateur(c.getString(UTILISATEUR_EMAIL_NUM),c.getString(UTILISATEUR_MDP_NUM));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        u.setIdu(c.getInt(UTILISATEUR_KEY_NUM));
        //u.set_email(c.getString(UTILISATEUR_EMAIL_NUM));
        //u.setMdp(c.getString(UTILISATEUR_MDP_NUM));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return u;
    }
}
