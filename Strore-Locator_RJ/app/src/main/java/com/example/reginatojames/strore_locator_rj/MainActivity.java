package com.example.reginatojames.strore_locator_rj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {
    EditText pass, email;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText)findViewById(R.id.editText);
        pass = (EditText)findViewById(R.id.editText2);
        login = (Button)findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        RequestParams params = new RequestParams();
        params.add("email", email.getText().toString());
        params.add("password", encryptPassword(pass.getText().toString()));

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post("http://its-bitrace.herokuapp.com/api/public/v2/login", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                boolean success;
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));

                    success = jsonObject.getBoolean("success");

                    if (success) {

                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();

                        JSONObject data = jsonObject.getJSONObject("data");

                        if (data != null) {

                            String session = data.getString("session");

                            Bundle bund = new Bundle();
                            bund.putString(MapsActivity.SESSIONE, session);
                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                            intent.putExtras(bund);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Errore!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Errore", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Connessione fallita: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String encryptPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md != null) {
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            String base64 = Base64.encodeToString(byteData, Base64.DEFAULT);

            base64 = base64.replace("\n", "");
            return base64;
        }
        return password;
    }
}
