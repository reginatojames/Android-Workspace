package reginato.james.sqlitelesson;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Reginato James on 25/11/2016.
 */
public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "ilmiodb";
    private static final int VERSIONE = 1;

    public DbHelper(Context aContext){
        super(aContext, DB_NAME, null, VERSIONE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //è necessario ce la tabella abbia come indice _id
        db.execSQL(ItemHelper.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ItemHelper.DROP_QUERY);
    }
}
