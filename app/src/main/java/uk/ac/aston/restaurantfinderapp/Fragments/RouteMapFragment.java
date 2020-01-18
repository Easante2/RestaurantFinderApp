package uk.ac.aston.restaurantfinderapp.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;


import java.util.Arrays;
import java.util.List;

import uk.ac.aston.restaurantfinderapp.Model.DatabaseHelper;
import uk.ac.aston.restaurantfinderapp.Model.GPSTracker;
import uk.ac.aston.restaurantfinderapp.Model.GeoRssLocation;
import uk.ac.aston.restaurantfinderapp.R;

/**
 * Created by Emmanuel on 11/04/2016.
 */
public class RouteMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private GoogleMap googleMap;
    private Marker mMarker1;
    private Marker mMarker2;
    private Polyline mPolyline;
    protected LatLng mCenterLocation = new LatLng( 39.7392, -104.9903 );
    private double userLatitude;
    private double userLongitude;
    private double eateryLatitude;
    private double eateryLongitude;
    private String eateryName;
    GPSTracker gps;
    private static final String polyline = "gsqqFxxu_SyRlTys@npAkhAzY{MsVc`AuHwbB}Lil@}[goCqGe|BnUa`A~MkbG?eq@hRq}@_N}vKdB";
    private GeoRssLocation location;
    private FavouriteDetailFragment fragment;
    private DatabaseHelper dbHelper;
    private String pID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Route to restaurant");
        this.setHasOptionsMenu(true);

            eateryName = (String) getArguments().getSerializable("Eatery");
            pID = (String) getArguments().getSerializable("PID");


        dbHelper = new DatabaseHelper(getContext());

      String dbPlaceID = dbHelper.getPlaceID(pID);

            if(pID.equals(dbPlaceID)){
                dbHelper.updateVisits(pID);

            }
    }

    public RouteMapFragment(Context context, double latitude, double longitude) {
        super();
        location = new GeoRssLocation();
        // location of Aston University
        gps = new GPSTracker(context);
        userLatitude = gps.getLatitude();
        userLongitude = gps.getLongitude();
        eateryLatitude = latitude;
        eateryLongitude = longitude;

    }

    public static RouteMapFragment newInstance(Context context, double latitude, double longitude) {
        RouteMapFragment mf = new RouteMapFragment(context, latitude, longitude);
        return mf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        this.getMapAsync(this);
        /* */
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLatitude, userLongitude), 14));


        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(userLatitude, userLongitude));
        //options.draggable(true);
        options.icon(BitmapDescriptorFactory.defaultMarker());
        options.title("You");
        mMarker1 = googleMap.addMarker(options);


        options = new MarkerOptions();
        options.position(new LatLng(eateryLatitude, eateryLongitude));
        options.draggable(true);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        options.title(eateryName);
        mMarker2 = googleMap.addMarker( options );

        mPolyline = googleMap.addPolyline(new PolylineOptions().geodesic(true));
        updateLine();
        showDistance();
    }

    private void updateLine() {
        mPolyline.setPoints(Arrays.asList(mMarker1.getPosition(), mMarker2.getPosition()));
    }

    private void showDistance() {
        double distance = SphericalUtil.computeDistanceBetween(mMarker1.getPosition(), mMarker2.getPosition());
        if( distance < 1000 ) {
            Toast.makeText(getContext(), String.format( "Distance %4.2f%s", distance, "m" ), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), String.format("Distance %4.3f%s", distance/1000, "km"), Toast.LENGTH_LONG).show();
        }
    }

    protected void initMapSettings() {
        List<LatLng> decodedPath = PolyUtil.decode(polyline);

        mGoogleMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }




}
