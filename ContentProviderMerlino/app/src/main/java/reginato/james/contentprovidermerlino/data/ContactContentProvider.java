package reginato.james.contentprovidermerlino.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Reginato James on 16/12/2016.
 */
public class ContactContentProvider extends ContentProvider{

    public static final String AUTHORITY = "reginato.james.contentprovidermerlino.data.ContactContentProvider";
    public static final String BASE_PATH_CONTACTS = "contacts";
    public static final Uri CONTACTS_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_CONTACTS);

    //configuraziuone uri matcher
    private static final int CONTACTS = 10;
    private static final int CONTACTS_ID = 20;
    private static final UriMatcher sURIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMATCHER.addURI(AUTHORITY,BASE_PATH_CONTACTS, CONTACTS);
        sURIMATCHER.addURI(AUTHORITY,BASE_PATH_CONTACTS + "/#", CONTACTS_ID);
    }

    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder vQueryBuillder = new SQLiteQueryBuilder();
        vQueryBuillder.setTables(ContactsHelper.TABLE_NAME);

        int vUriType = sURIMATCHER.match(uri);

        switch (vUriType){
            case CONTACTS:
                vQueryBuillder.setTables(ContactsHelper.TABLE_NAME);
                break;
            case CONTACTS_ID:
                vQueryBuillder.setTables(ContactsHelper.TABLE_NAME);
                vQueryBuillder.appendWhere(ContactsHelper._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = vQueryBuillder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        //db.close();
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMATCHER.match(uri);
        SQLiteDatabase vSqlDb = dbHelper.getWritableDatabase();
        long vId = 0;
        switch (uriType){
            case CONTACTS:
                vId = vSqlDb.insert(ContactsHelper.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Uknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        //vSqlDb.close();
        if(vId >= 0)
            return Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_CONTACTS + "/" + vId);
        else
            return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMATCHER.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType){
            case CONTACTS:
                rowsDeleted = sqlDB.delete(ContactsHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case CONTACTS_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    rowsDeleted = sqlDB.delete(ContactsHelper.TABLE_NAME, ContactsHelper._ID + "=" + id, null);
                }else{
                    rowsDeleted = sqlDB.delete(ContactsHelper.TABLE_NAME, ContactsHelper._ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMATCHER.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType){
            case CONTACTS:
                rowsUpdated = sqlDB.update(ContactsHelper.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CONTACTS_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    rowsUpdated = sqlDB.update(ContactsHelper.TABLE_NAME, values, ContactsHelper._ID + "=" + id, null);
                }else{
                    rowsUpdated = sqlDB.update(ContactsHelper.TABLE_NAME, values, ContactsHelper._ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if(rowsUpdated > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
