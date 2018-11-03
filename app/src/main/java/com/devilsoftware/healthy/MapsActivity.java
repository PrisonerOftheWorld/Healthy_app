package com.devilsoftware.healthy;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.devilsoftware.healthy.Models.Organisations.Feature;
import com.devilsoftware.healthy.Models.Organisations.Organisations;
import com.devilsoftware.healthy.main.App;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        App.getAPIService().initYandex();
        App.getAPIService().getYandexAPI().
                getOrganisations("c10d6ea5-bf01-4a7e-ae87-0138ce57bc91",
                        "аптека",
                        "53.691275, 53.645321",
                        "1.0,1.0", "ru_RU").enqueue(new Callback<Organisations>() {
            @Override
            public void onResponse(Call<Organisations> call, Response<Organisations> response) {
                Organisations organisations = response.body();
                if (organisations!=null){
                    for (Feature feature : organisations.features){
                        Log.d("TAG", new Gson().toJson(feature));


                        double latitude = feature.geometry.coordinates.get(0) + 0.048008;
                        double longitude = feature.geometry.coordinates.get(1) - 0.048018;

                        // Add a marker in Sydney and move the camera


                        String label = feature.properties.name;
                        String uriBegin = "geo:" + latitude + "," + longitude;
                        String query = latitude + "," + longitude + "(" + label + ")";
                        String encodedQuery = Uri.encode(query);
                        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                        Uri uri = Uri.parse(uriString);

                        LatLng pos = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(pos).title(label));

                    }
                }
            }

            @Override
            public void onFailure(Call<Organisations> call, Throwable t) {
                Log.d("TAG", "FAIL");
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
