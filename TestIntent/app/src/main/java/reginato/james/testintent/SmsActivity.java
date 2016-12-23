package reginato.james.testintent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        TextView txt = (TextView)findViewById(R.id.textView);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if(data != null)
            txt.setText(data.getString("smsbody"));
    }
}
