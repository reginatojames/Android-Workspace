package reginato.james.contentprovidermerlino;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Random;

import reginato.james.contentprovidermerlino.data.ContactContentProvider;
import reginato.james.contentprovidermerlino.data.ContactCursorAdapter;
import reginato.james.contentprovidermerlino.data.ContactsHelper;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{

    Button addBtn;
    ContactCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView vList = (ListView)findViewById(R.id.listView);

        adapter = new ContactCursorAdapter(this, null);
        vList.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);

        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteItem(id);
            }
        });


        addBtn = (Button) findViewById(R.id.getBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addValue();
            }
        });
    }

    private void deleteItem(long aItemID){
        Uri uriToDelete = Uri.parse(ContactContentProvider.CONTACTS_URI + "/" + aItemID);
        getContentResolver().delete(uriToDelete, null, null);
    }

    private void addValue(){
        Random vRand = new Random();
        ContentValues vValues = new ContentValues();
        int a = vRand.nextInt();
        vValues.put(ContactsHelper.NAME, "nome " + a);
        vValues.put(ContactsHelper.SURNAME, "surname " + a);

        getContentResolver().insert(ContactContentProvider.CONTACTS_URI, vValues);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(this, ContactContentProvider.CONTACTS_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
