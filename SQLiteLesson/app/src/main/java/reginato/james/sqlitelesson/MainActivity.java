package reginato.james.sqlitelesson;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    DbHelper vHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vHelper = new DbHelper(this);
        insertData();
        SQLiteDatabase vDb = vHelper.getReadableDatabase();

        //Cursor vCursor = vDb.rawQuery("SELECT * FROM " + ItemHelper.TABLE_NAME, null);
        Cursor vCursor = vDb.query(ItemHelper.TABLE_NAME, null, null, null, null, null, null);

        while(vCursor.moveToNext()){
            int vID = vCursor.getInt(vCursor.getColumnIndex(ItemHelper._ID));
            String vName = vCursor.getString(vCursor.getColumnIndex(ItemHelper.NAME));
            int vQuantity = vCursor.getInt(vCursor.getColumnIndex(ItemHelper.QUANTITY));
            Log.d("TAG)", "" + vID + "," + vName + "," + vQuantity);
        }
        vCursor.close();
        vDb.close();
    }

    public void insertData(){
        SQLiteDatabase db = vHelper.getWritableDatabase();
        for(int i = 0; i < 100; i++){
            ContentValues values = new ContentValues();

            values.put(ItemHelper.NAME, "name " + i);
            values.put(ItemHelper.QUANTITY, i);

            db.insert(ItemHelper.TABLE_NAME, null, values);
        }
        db.close();
    }
}
