package tripleh.helpme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.util.ArrayList;
import java.util.Locale;

import tripleh.helpme.FloatingMarkerOverlalyTitle.FloatingMarkerTitlesOverlay;
import tripleh.helpme.FloatingMarkerOverlalyTitle.MarkerInfo;
import tripleh.helpme.model.MarkerData;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    boolean mapReady;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    public static final float INITIAL_ZOOM = 17f;
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<MarkerData> markerDatas=new ArrayList<>();
    private FloatingMarkerTitlesOverlay floatingMarkersOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_test);
        markerDatas.add(new MarkerData(21.350666, 95.090478,"lwin"));
        markerDatas.add(new MarkerData(21.350661, 95.090339,"Moe"));
        markerDatas.add(new MarkerData(21.350651, 95.090291,"Kyaw"));
        markerDatas.add(new MarkerData(21.350594, 95.090318,"Thi"));
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.map, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.map_options,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        mapReady=true;
        //Initializing FloatingMarkerTitlesOverlay
        floatingMarkersOverlay = findViewById(R.id.map_floating_markers_overlay);
        floatingMarkersOverlay.setSource(googleMap);
        LatLng home = new LatLng(21.350648,95.090549);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, INITIAL_ZOOM));
        setMapLongClick(mMap);
        enableMyLocation(mMap);
        for(MarkerData markerData:markerDatas){
            createMarker(markerData.getLat(),markerData.getLon(),markerData.getTitle());
        }


    }
    /**
     * Checks for location permissions, and requests them if they are missing.
     * Otherwise, enables the location layer.
     */
    private void enableMyLocation(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }


    /**
     * Adds a blue marker to the map when the user long clicks on it.
     *
     * @param map The GoogleMap to attach the listener to.
     */
    private void setMapLongClick(final GoogleMap map) {

        // Add a blue marker to the map when the user performs a long click.
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String snippet = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f",
                        latLng.latitude,
                        latLng.longitude);

                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Dropped Pin")
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_BLUE)));
            }
        });
    }
    protected void createMarker(double lat,double lon,String title){
        String snippet = String.format(Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                lat,
                lon);
        final long id = 0;
        final LatLng latLng = new LatLng(lat, lon);
        final int color = Color.GREEN;
        final MarkerInfo mi = new MarkerInfo(latLng, title, color);
        mMap.addMarker(new MarkerOptions().position(mi.getCoordinates()).title(title).snippet("hello world"));
//Adding the marker to track by the overlay
//To remove that marker, you will need to call floatingMarkersOverlay.removeMarker(id)
        floatingMarkersOverlay.addMarker(id, mi);
    }




}
