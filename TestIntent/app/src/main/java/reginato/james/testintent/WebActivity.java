package reginato.james.testintent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eb);
        TextView txt = (TextView)findViewById(R.id.textView);
        Intent intent = getIntent();
        Uri data = intent.getData();
        if(data != null)
            txt.setText(data.toString());
    }
}
