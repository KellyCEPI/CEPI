package Bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 *
 * @author rg514_000
 */
public class LigneDAO extends SQLiteOpenHelper {
    public static final String LIGNE_IDLIGNE = "idligne";
    public static final String LIGNE_COCHER  = "cocher";
    public static final String LIGNE_TEXTE = " texte ";

    public static final String LIGNE_TABLE_NAME  = "ligne";

    private static final String DATABASE_NAME = "ligne.db";
    private static final int UTILISATEUR_VERSION = 1;

    public static final String LIGNE_TABLE_CREATE = "CREATE TABLE" +
            LIGNE_TABLE_NAME + " (" +
            LIGNE_IDLIGNE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LIGNE_COCHER + " INTEGER CHECK(LIGNE_COCHER = 1 & LIGNE_COCHER = 0),"+
            LIGNE_TEXTE + " TEXT);";

    public LigneDAO(Context context) {
        super(context, DATABASE_NAME, null, UTILISATEUR_VERSION);
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL(LIGNE_TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LigneDAO.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + LIGNE_TABLE_NAME);
        onCreate(db);
    }
}