package Bdd;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import Objets.Ligne;

/**
 *
 * @author rg514_000
 */
public class LigneDataSource {
    //Champs de la base de données
    private SQLiteDatabase database;
    private LigneDAO dbHelper;
    private String[] allColumns = {LigneDAO.LIGNE_IDLIGNE, LigneDAO.LIGNE_COCHER,
            LigneDAO.LIGNE_TEXTE};

    public LigneDataSource(Context contect){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public LigneDAO createLigne(String cocher, String texte){
        ContentValues values = new ContentValues();
        values.put(LigneDAO.LIGNE_COCHER, cocher);
        values.put(LigneDAO.LIGNE_TEXTE, texte);
        long insertId = database.insert(LigneDAO.LIGNE_TABLE_NAME, null, values); //MODIF
        Cursor cursor = database.query(LigneDAO.LIGNE_TABLE_NAME, allColumns,
                LigneDAO.LIGNE_IDLIGNE + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        LigneDAO newLigne =  cursorToLigne(cursor);
        cursor.close();
        return newLigne;
    }

    public void deleteLigne (Ligne ligne){
        int idligne = ligne.getIdligne();
        System.out.println("Email deleted with id : " + idligne);
        database.delete(LigneDAO.LIGNE_TABLE_NAME, LigneDAO.LIGNE_IDLIGNE +
                " = " + idligne, null);
    }

    public List<Ligne> getAllLigne(){
        List<Ligne> ligneList = new ArrayList<Ligne>();

        Cursor cursor = database.query(LigneDAO.LIGNE_TABLE_NAME, allColumns, null,
                null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Ligne ligne = cursorToLigne(cursor);
            ligneList.add(ligne);
            cursor.moveToNext();
        }
        cursor.close();
        return ligneList;
    }

    private Ligne cursorToLigne(Cursor cursor){
        Ligne ligne= new Ligne(cursor.getInt(0),cursor.getInt(0),cursor.getString(1));
        //ligne.setIdligne(cursor.Int(0));
        //ligne.set_cocher(cursor.Int(0));
        //ligne.set_ligne(cursor.getString(1));
        return ligne;
    }
}