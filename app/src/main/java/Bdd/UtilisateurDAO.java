package Bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 *
 * @author rg514_000
 */
public class UtilisateurDAO extends SQLiteOpenHelper {
    //colonnes de la table utilisateur
    public static final String UTILISATEUR_KEY = "idu";
    public static final String UTILISATEUR_EMAIL = "email";
    public static final String UTILISATEUR_MDP = "mdp";

    public static final String UTILISATEUR_TABLE_NAME = "utilisateur";


    //création de la table
    public static final String UTILISATEUR_TABLE_CREATE = "CREATE TABLE " +
            UTILISATEUR_TABLE_NAME + " (" +
            UTILISATEUR_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            UTILISATEUR_EMAIL + " TEXT," +
            UTILISATEUR_MDP + " TEXT);";

    public UtilisateurDAO(Context context, String name, CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(UTILISATEUR_TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE " + UTILISATEUR_TABLE_NAME + ";");
        onCreate(db);
    }

}