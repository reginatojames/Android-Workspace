package reginato.james.contentprovidermerlino.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Reginato James on 02/12/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private final static String NAME = "contacts.db";
    private final static int VERSION = 1;

    public DbHelper(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactsHelper.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
