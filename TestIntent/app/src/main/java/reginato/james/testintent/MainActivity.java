package reginato.james.testintent;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static final int PICK_CONTACT_REQUEST = 1;

    protected void onActivityResult(int requestCode, int resultCode, Bundle bundle){
        if(requestCode == PICK_CONTACT_REQUEST && resultCode== RESULT_OK){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupGUI();
    }

    private void setupGUI(){
        Button vWeb = (Button)findViewById(R.id.button);
        vWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickWeb();
            }
        });

        Button vSms = (Button)findViewById(R.id.smsBtn);
        vSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });

        Button vPick = (Button)findViewById(R.id.pickBtn);
        vPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickContact();
            }
        });
    }

    private void clickWeb(){
        Uri vUri = Uri.parse("https://www.google.com");
        Intent vIntent = new Intent(Intent.ACTION_VIEW);
        vIntent.setData(vUri);
        Intent vChooser = Intent.createChooser(vIntent, "my title");
        if(vIntent.resolveActivity(getPackageManager()) != null)
            startActivity(vChooser);
    }

    private void sendSms(){
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.putExtra("sms_body", "ciao");
        if(smsIntent.resolveActivity(getPackageManager()) != null)
            startActivity(smsIntent);
    }

    private void pickContact(){
        Intent vIntent = new Intent(Intent.ACTION_PICK);
        vIntent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        if(vIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(vIntent, PICK_CONTACT_REQUEST);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
