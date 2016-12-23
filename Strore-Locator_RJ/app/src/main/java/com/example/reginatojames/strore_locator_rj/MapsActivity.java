package com.example.reginatojames.strore_locator_rj;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap;
    public static final String SESSIONE = "session";
    String session;
    ArrayList <Negozio> shops = new ArrayList<>();
    JSONArray data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle vBundle = getIntent().getExtras();
        if(vBundle!=null){
            session = vBundle.getString(SESSIONE);
        }

        sendSession();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng coordinates[] = new LatLng[data.length()];

        for(int i = 0 ; i < data.length() ; i++)
            try {
                coordinates[i] = (new LatLng(Double.parseDouble(data.getJSONObject(i).getString("latitude")),
                        Double.parseDouble(data.getJSONObject(i).getString("longitude"))));
                mMap.addMarker(new MarkerOptions().position(coordinates[i]).title(data.getJSONObject(i).getString("name")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        mMap.setOnInfoWindowClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates[0]));
    }


    public void sendSession() {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader("x-bitrace-session", session);

        asyncHttpClient.get("http://its-bitrace.herokuapp.com/api/v2/stores", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                boolean success;

                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    success = jsonObject.getBoolean("success");

                    if (success) {

                        Toast.makeText(MapsActivity.this, "success", Toast.LENGTH_SHORT).show();
                        data = jsonObject.getJSONArray("data");

                        if (data != null) {
                            for(int i = 0 ; i < data.length() ; i++) {
                                shops.add(new Negozio(data.getJSONObject(i).getString("name"),
                                        data.getJSONObject(i).getString("phone"),
                                        data.getJSONObject(i).getString("guid"),
                                        data.getJSONObject(i).getString("address"),
                                        data.getJSONObject(i).getString("latitude"),
                                        data.getJSONObject(i).getString("longitude")));
                            }

                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            mapFragment.getMapAsync(MapsActivity.this);
                        }
                    } else {
                        Toast.makeText(MapsActivity.this, "Errore!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MapsActivity.this, "Errore", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
                Toast.makeText(MapsActivity.this, "Connessione fallita: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {


        String id = "";
       /* for (Negozio x : shops){
            if(x.getName() == marker.getTitle()){
                id = x.getId();
            }
        }*/
        Intent n = new Intent(this, TabPagerAdapter.class);
        //Bundle vBundle = new Bundle();
        //vBundle.putString(DetailsActivity.ID, id);
        //n.putExtras(vBundle);

        startActivity(n);
    }
}
