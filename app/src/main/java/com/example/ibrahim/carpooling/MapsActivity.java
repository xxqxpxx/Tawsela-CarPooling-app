package com.example.ibrahim.carpooling;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UiSettings mUiSettings;
    LatLng latLng2, latLng;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        onSearch();
        onSearch2();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
        }
        // Map settings
        mMap.setMyLocationEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMapToolbarEnabled(false);
        mUiSettings.setIndoorLevelPickerEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
        //------------------------------------------------------------------------------------------

        // Set the source and destination as boundaries (camera)
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        boundsBuilder.include(latLng);
        boundsBuilder.include(latLng2);
        LatLngBounds bounds = boundsBuilder.build();
        //------------------------------------------------------------------------------------------

        try {
            // Add Markers at source and destination, animate the camera and zoom on the markers
            mMap.addMarker(new MarkerOptions().position(latLng).title("Source"));
            mMap.addMarker(new MarkerOptions().position(latLng2).title("Destination"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,140));
            //----------------------------------------------------------------------------------------------

            // Add lines between multiple points to show the driving route using directions api
            String serverKey = "AIzaSyC0-lAJoLeIWjd2PHQIpPJQ5ovjVoq3RPQ";
            LatLng source = latLng;
            LatLng destination = latLng2;
            GoogleDirection.withServerKey(serverKey)
                    .from(source)
                    .to(destination)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            if (direction.isOK()) {
                                Route route = direction.getRouteList().get(0);
                                Leg leg = route.getLegList().get(0);
                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.BLUE);
                                mMap.addPolyline(polylineOptions);
                            }
                        }
                        @Override
                        public void onDirectionFailure(Throwable t) {
                            Toast.makeText(getApplicationContext(),"Could not find a valid route",Toast.LENGTH_LONG).show();
                        }
                    });
            //----------------------------------------------------------------------------------------------
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.ibrahim.carpooling/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    tripdetails t=new tripdetails();

    public void onSearch()
    {
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(this);

        try
        {
            addressList = geocoder.getFromLocationName(t.from , 1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Place1:" + t.from+"  "+e.toString(), Toast.LENGTH_LONG).show();
        }

        try
        {
            if (addressList.size() > 0 && addressList != null)
            {
                Address address = addressList.get(0);
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
                //   mMap.clear();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "/nError:" +e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onSearch2()
    {
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(this);

        try
        {
            addressList = geocoder.getFromLocationName(t.to , 1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            if (addressList.size() > 0 && addressList != null)
            {
                Address address = addressList.get(0);
                latLng2 = new LatLng(address.getLatitude(), address.getLongitude());
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "/nError:" +e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.ibrahim.carpooling/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
