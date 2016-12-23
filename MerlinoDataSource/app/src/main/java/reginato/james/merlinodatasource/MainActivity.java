package reginato.james.merlinodatasource;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import reginato.james.merlinodatasource.data.Contact;
import reginato.james.merlinodatasource.data.DataSet;

public class MainActivity extends Activity {

    DataSet mDataSet;
    ContactsAdapter mAdapter;
    Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisContext = this;

        ListView vList = (ListView)findViewById(R.id.listView);
        Button btn = (Button)findViewById(R.id.getBtn);

        mDataSet = DataSet.Get(this);
        mAdapter = new ContactsAdapter(this, mDataSet.getContacts());

        vList.setAdapter(mAdapter);

        vList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final long selectedId = id;

                AlertDialog.Builder builder = new AlertDialog.Builder(thisContext);
                builder.setMessage("SEI SICURO DI VOLERE ELIMINARE QUESTO ELEMENTO?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mDataSet.removeContact(selectedId);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("ANNULLA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSet.addContact(Contact.createContacts());
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
