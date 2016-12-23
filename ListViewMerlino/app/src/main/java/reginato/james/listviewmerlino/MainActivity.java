package reginato.james.listviewmerlino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> mDatabase;

    private void initDb(){
        mDatabase = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            Item vItem = new Item(i);
            mDatabase.add(vItem);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDb();//qui per esempio!!

        CustomAdapter vAdapter = new CustomAdapter(this, mDatabase);
        ListView vList = (ListView) findViewById(R.id.listView);

        vList.setAdapter(vAdapter);
    }
}
