package reginato.james.merlinodatasource.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Reginato James on 02/12/2016.
 */
public class DataSet {
    private static DataSet mInstance = null;
    DbHelper dbHelper;

    public static DataSet Get(Context aContext){
        if(mInstance == null){
            mInstance = new DataSet(aContext);

        }
        return mInstance;
    }

    ArrayList<Contact> mContacts;
    public DataSet(Context aContext){
        mContacts = new ArrayList<>();
        dbHelper = new DbHelper(aContext);
    }

    public ArrayList<Contact> getContacts(){
        SQLiteDatabase vDB = dbHelper.getReadableDatabase();
        Cursor vCursor = vDB.query(ContactsHelper.TABLE_NAME, null, null, null, null, null, null);
        while(vCursor.moveToNext()){
            Contact vContact = new Contact();
            vContact.mId = vCursor.getLong(vCursor.getColumnIndex(ContactsHelper._ID));
            vContact.mName = vCursor.getString(vCursor.getColumnIndex(ContactsHelper.NAME));
            vContact.mSurname = vCursor.getString(vCursor.getColumnIndex(ContactsHelper.SURNAME));
            mContacts.add(vContact);
        }
        vCursor.close();
        vDB.close();
        return mContacts;
    }

    public Contact addContact(Contact aContact){
        SQLiteDatabase vDb = dbHelper.getWritableDatabase();
        ContentValues vValues = new ContentValues();
        vValues.put(ContactsHelper.NAME, aContact.mName);
        vValues.put(ContactsHelper.SURNAME, aContact.mSurname);

        long vInsertedId = vDb.insert(ContactsHelper.TABLE_NAME, null, vValues);

        aContact.mId = vInsertedId;
        vDb.close();
        mContacts.add(aContact);

        return aContact;
    }

    public boolean removeContact(long aId){
        SQLiteDatabase vDb = dbHelper.getWritableDatabase();
        int vrows = vDb.delete(ContactsHelper.TABLE_NAME, ContactsHelper._ID + " = " + aId, null);

        int vRemove = -1;
        for(int vIndex = 0; vIndex < mContacts.size(); vIndex++){
            if(mContacts.get(vIndex).mId == aId){
                vRemove = vIndex;
                break;
            }
        }

        mContacts.remove(vRemove);

        return (vrows > 0);
    }
}
