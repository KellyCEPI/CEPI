package Bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 *
 * @author rg514_000
 */
public class EvenementDAO extends SQLiteOpenHelper {
    //Variable pour la base de donnée
    public static final String EVENEMENT_KEY = "idE";
    public static final String EVENEMENT_NOM = "nom";
    public static final String EVENEMENT_ANNEE = "annee";
    public static final String EVENEMENT_MOIS = "mois";
    public static final String EVENEMENT_JOUR = "jour";
    public static final String EVENEMENT_HEURE_DEBUT = "heure debut";
    public static final String EVENEMENT_MINUTE_DEBUT = "minute debut";
    public static final String EVENEMENT_REPEAT_INTER = "repeat inter";

    public static final String EVENEMENT_TABLE_NAME = "Evenement";

    // incrementer le ide
    // code base de donnée
    public static final String EVENEMENT_TABLE_CREATE = "CREATE TABLE " +
            EVENEMENT_TABLE_NAME + " (" +
            EVENEMENT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EVENEMENT_NOM + " TEXT, " +
            EVENEMENT_ANNEE + " INT," +
            EVENEMENT_MOIS + " INT," +
            EVENEMENT_JOUR + " INT," +
            EVENEMENT_HEURE_DEBUT + " INT," +
            EVENEMENT_MINUTE_DEBUT + " INT," +
            EVENEMENT_REPEAT_INTER + " INT);";

    public EvenementDAO(Context context, String name, CursorFactory factory, int version) {
        super(context, name , factory, version);
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL(EVENEMENT_TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EVENEMENT_TABLE_NAME + ";");
        onCreate(db);
    }
}