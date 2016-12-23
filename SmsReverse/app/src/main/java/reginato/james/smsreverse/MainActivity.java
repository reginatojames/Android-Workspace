package reginato.james.smsreverse;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (EditText) findViewById(R.id.txt);
        Button btn = (Button)findViewById(R.id.sndBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = txt.getText().toString();
                text = new StringBuffer(text).reverse().toString();
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:"));
                smsIntent.putExtra("sms_body", text);
                if(smsIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(smsIntent);
            }
        });
    }
}
