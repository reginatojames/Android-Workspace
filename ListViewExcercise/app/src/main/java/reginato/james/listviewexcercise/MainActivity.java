package reginato.james.listviewexcercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<CustomItem> mDB;

    private void initDb(){
        mDB = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            CustomItem vItem = new CustomItem(i);
            mDB.add(vItem);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDb();

        CustomAdapter vAdapter = new CustomAdapter(this, mDB);
        ListView vList = (ListView) findViewById(R.id.listView);

        vList.setAdapter(vAdapter);
    }
}
