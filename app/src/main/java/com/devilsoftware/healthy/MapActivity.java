package com.devilsoftware.healthy;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.devilsoftware.healthy.Models.Organisations.Feature;
import com.devilsoftware.healthy.Models.Organisations.Organisations;
import com.google.gson.Gson;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Circle;
import com.yandex.mapkit.geometry.LinearRing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CircleMapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PatternRepeatMode;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolygonMapObject;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.AnimatedImageProvider;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity {

    private final String MAPKIT_API_KEY = "be2e5bf9-71fa-4c4a-a489-3344f49eb324";
    private final Point TARGET_LOCATION = new Point(59.945933, 30.320045);

    private MapView mapView;
    private MapObjectCollection mapObjects;


    private final Point CAMERA_TARGET = new Point(59.952, 30.318);
    private final Point ANIMATED_RECTANGLE_CENTER = new Point(59.956, 30.313);
    private final Point TRIANGLE_CENTER = new Point(59.948, 30.313);
    private final Point POLYLINE_CENTER = CAMERA_TARGET;
    private final Point CIRCLE_CENTER = new Point(59.956, 30.323);
    private final Point DRAGGABLE_PLACEMARK_CENTER = new Point(59.948, 30.323);
    private final double OBJECT_SIZE = 0.0015;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Set the api key before calling initialize on MapKitFactory.
         * It is recommended to set api key in the Application.onCreate method,
         * but here we do it in each activity to make examples isolated.
         */
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        /**
         * Initialize the library to load required native libraries.
         * It is recommended to initialize the MapKit library in the Activity.onCreate method
         * Initializing in the Application.onCreate method may lead to extra calls and increased battery use.
         */
        MapKitFactory.initialize(this);
        // Now MapView can be created.
        setContentView(R.layout.fragment_map);
        super.onCreate(savedInstanceState);
        mapView = (MapView)findViewById(R.id.map_view);

        // And to show what can be done with it, we move the camera to the center of Saint Petersburg.
        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);

        mapObjects = mapView.getMap().getMapObjects().addCollection();



    }

    @Override
    protected void onStop() {
        // Activity onStop call must be passed to both MapView and MapKit instance.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        // Activity onStart call must be passed to both MapView and MapKit instance.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
}
