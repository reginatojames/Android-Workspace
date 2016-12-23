package reginato.james.sqlitelesson;

import android.provider.BaseColumns;

/**
 * Created by Reginato James on 25/11/2016.
 */
public class ItemHelper implements BaseColumns{
    public static final String TABLE_NAME = "items";
    public static final String NAME = "name";
    public static final String QUANTITY = "quantity";

    public static  final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT NOT NULL," + QUANTITY + " INTEGER NOT NULL);";
    public static  final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
