package com.devilsoftware.healthy.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.devilsoftware.healthy.main.App;
import com.devilsoftware.healthy.Models.Organisations.Feature;
import com.devilsoftware.healthy.Models.Organisations.Organisations;
import com.devilsoftware.healthy.R;
import com.devilsoftware.healthy.adapters.SearchListAdapter;
import com.google.android.gms.common.logging.Logger;
import com.google.gson.Gson;

import java.security.Permission;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchOrgFragment extends Fragment {

    View mainView;

    public static final int LOCATION_UPDATE_MIN_DISTANCE = 1000;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.d(String.format("%f, %f", location.getLatitude(), location.getLongitude()), "");
                updateList(location, "аптека");
                lastLoc = location;
                mLocationManager.removeUpdates(mLocationListener);
            } else {
                Log.d("Location is null", "");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private LocationManager mLocationManager;
    private Location lastLoc;

    ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mainView == null) {

            mainView = inflater.inflate(R.layout.fragment_search_org, container, false);

            listView = mainView.findViewById(R.id.resultList);

            mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            getCurrentLocation();

            mainView.findViewById(R.id.apt).setOnClickListener(view -> updateList(lastLoc, "аптека"));
            mainView.findViewById(R.id.hos).setOnClickListener(view -> updateList(lastLoc, "поликлиника"));


        }

        return mainView;
    }


    private void getCurrentLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled))
            Toast.makeText(getActivity(), "Произошла ошибка при определении местоположения.", Toast.LENGTH_LONG).show();
        else {
            if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (location != null) {
            Log.d(String.format("getCurrentLocation(%f, %f)", location.getLatitude(),
                    location.getLongitude()),"");
            lastLoc = location;
            updateList(location, "аптека");
        }

    }

    public void updateList(Location location, String find){
        App.getAPIService().initYandex();
        App.getAPIService().getYandexAPI().
                getOrganisations("c10d6ea5-bf01-4a7e-ae87-0138ce57bc91",
                        find,
                        location.getLatitude()+ ", " + location.getLongitude(),
                        "0.04,0.04", "ru_RU").enqueue(new Callback<Organisations>() {
            @Override
            public void onResponse(Call<Organisations> call, Response<Organisations> response) {
                Organisations organisations = response.body();
                if (organisations != null) {
                    for (Feature feature : organisations.features) {
                        Log.d("TAG", new Gson().toJson(feature));

                        SearchListAdapter searchListAdapter = new SearchListAdapter(getActivity(), organisations);

                        listView.setAdapter(searchListAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Feature feature = (Feature) ((SearchListAdapter)listView.getAdapter()).getItem(i);
                                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + feature.properties.companyMetaData.address);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);

                            }
                        });
                        listView.setLongClickable(true);
                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Feature feature = (Feature) ((SearchListAdapter)listView.getAdapter()).getItem(i);
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setItems(R.array.options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i==0) {
                                            if (feature.properties.companyMetaData.phones!=null){
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + feature.properties.companyMetaData.phones.get(0).formatted));
                                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PackageManager.PERMISSION_GRANTED);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(getContext(), "Номер отсутствует", Toast.LENGTH_SHORT).show();
                                            }
                                        } else if(i==1){
                                            if (feature.properties.companyMetaData.url!=null){
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(Uri.parse(feature.properties.companyMetaData.url));
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(getContext(), "Сайт отсутствует", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                                                builder.show();
                                return true;
                            }
                        });
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
}
