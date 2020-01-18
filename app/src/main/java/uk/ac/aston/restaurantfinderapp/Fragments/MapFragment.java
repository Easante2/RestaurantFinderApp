package uk.ac.aston.restaurantfinderapp.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import uk.ac.aston.restaurantfinderapp.Model.Eatery;
import uk.ac.aston.restaurantfinderapp.Model.GPSTracker;
import uk.ac.aston.restaurantfinderapp.Model.GeoRssLocation;
import uk.ac.aston.restaurantfinderapp.Model.GooglePlace;
import uk.ac.aston.restaurantfinderapp.Model.GooglePlaceList;
import uk.ac.aston.restaurantfinderapp.Model.GooglePlacesUtility;
import uk.ac.aston.restaurantfinderapp.Model.MarkerItem;
import uk.ac.aston.restaurantfinderapp.Model.Renderer;
import uk.ac.aston.restaurantfinderapp.PlaceDetailActivity;
import uk.ac.aston.restaurantfinderapp.R;

import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterManager;

/**
 * Created by Emmanuel on 17/02/2016.
 */
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private GoogleMap googleMap;

    private Marker itemMarker;
    private HashMap<Eatery, Marker> markers;
    private List<GooglePlace> places;
    private ClusterManager<MarkerItem> mClusterManager;
    private transient LatLng mLatLng;
    public GooglePlaceList nearby;
    private String webKey = "";
    private double latitude;
    private double longitude;
    GPSTracker gps;
    private HashMap<Marker, GooglePlace> nearby_eatery;
    private String eateryType;
    public SelectEateryTypeFragment fragment;
    public static String id;


    public MapFragment(Context context) {
        super();
        gps = new GPSTracker(context);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();


    }


    public static MapFragment newInstance(Context context) {
        MapFragment mf = new MapFragment(context);
        return mf;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Map view");
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        eateryType= SelectEateryTypeFragment.gete_Type();

        this.getMapAsync(this);
        return v;
    }



    private void setUpCluster() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14));
        mClusterManager = new ClusterManager<MarkerItem>(getActivity(), getMap());
        getMap().setOnCameraChangeListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);
        getMap().clear();
        Renderer renderer = new Renderer(getContext(), getMap(), mClusterManager);
        mClusterManager.setRenderer(renderer);




        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getMap().setMyLocationEnabled(true);

        String placesRequest = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                latitude + "," + longitude + "&radius=500&type="+eateryType+"&key=" + webKey;
        PlacesReadFeed process = new PlacesReadFeed();
        process.execute(new String[]{placesRequest});
/*        reportBack();
        addItems(places);*/



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location.getLatLon(), 9));
        //getMap().setOnCameraChangeListener(mClusterManager);
        setUpCluster();

       /* getMap().addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("Hello world"));*/

        mClusterManager.getMarkerCollection().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(GooglePlace gPlace: nearby.getResults()){
                    if(gPlace.getGeometry().getLocation().getLat()== marker.getPosition().latitude
                            && gPlace.getGeometry().getLocation().getLng() == marker.getPosition().longitude ){
                        id = gPlace.getId();
                        //GooglePlace selectedPlace = gPlace;
                        Intent i = new Intent(getActivity(), PlaceDetailActivity.class);
                        i.putExtra("PLACE",gPlace.getId());
                        i.putExtra("Long", longitude);
                        i.putExtra("Lat", latitude);
                        i.putExtra("type", eateryType);
                        startActivity(i);
                    }
                }
                return false;
            }
        });

    }

    public static String getID(){
        return id;
    }

    private Marker createMarker(LatLng ll, String title, String description, float hue) {
        /*
        CircleOptions circleOptions = new CircleOptions()
                .center(ll)
                .radius(1000); // In meters
        Circle circle = googleMap.addCircle(circleOptions);
        */
        Marker marker = googleMap.addMarker(new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.defaultMarker(hue)));
        marker.setTitle(title);
        marker.setSnippet(description);

        return marker;
    }

    private void placePin(Eatery item, boolean isItemMarker) {
        if (isItemMarker) {
            this.itemMarker = createMarker(item.getLocation().getLatLon(), item.getTitle(), item.getDescription(), BitmapDescriptorFactory.HUE_RED);
        } else {
            if (this.markers == null) {
                this.markers = new HashMap<>();
                this.markers.put(item, createMarker(item.getLocation().getLatLon(), item.getTitle(), item.getDescription(), BitmapDescriptorFactory.HUE_RED));
            } else if (this.markers.containsKey(item)) {
                this.markers.get(item).setVisible(true);
            } else {
                this.markers.put(item, createMarker(item.getLocation().getLatLon(), item.getTitle(), item.getDescription(), BitmapDescriptorFactory.HUE_RED));
            }
        }
    }

    public boolean onMarkerClick(Marker marker){

        if (marker.equals(this.itemMarker) && nearby_eatery != null) {
            for (Marker placeMarker : nearby_eatery.keySet()) {
                placeMarker.setVisible(!placeMarker.isVisible());
            }
        } /*else if (nearby != null && nearby_eatery.containsKey(marker)) {
            Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
            intent.putExtra("PLACE", nearby_eatery.get(marker));
            startActivity(intent);
            return true;
        }*/

        return false;
    }

    /*private void addItems(GooglePlaceList gPlaceList) {
        for (GooglePlace place : gPlaceList.getResults()) {
            place.setPosition(place.getGeometry().getLocation().getLatLng());
            mClusterManager.addItem(place);


        }
    }*/

    public void setPlaces(GooglePlaceList placeGPL) {
        nearby_eatery = new HashMap<>();
        this.places = placeGPL.getResults();
        for (GooglePlace place : this.places) {
              LatLng location = new LatLng(place.getGeometry().getLocation().getLat(),
                    place.getGeometry().getLocation().getLng());
            MarkerItem marker = new MarkerItem(place.getName(), place, location);
            place.setPosition(place.getGeometry().getLocation().getLatLng());
            mClusterManager.addItem(marker);

            String name = place.getName();
            List<String> types = place.getTypes();
            Log.i("Found a place called: ", name);
            GooglePlace.Geometry geometry = place.getGeometry();

        }
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

    protected void reportBack(GooglePlaceList nearby) {
        this.nearby = nearby;
        setPlaces(nearby);


    }


    private class PlacesReadFeed extends AsyncTask<String, Void, GooglePlaceList> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected GooglePlaceList doInBackground(String... urls) {
            try {
                String referer = null;
                //dialog.setMessage("Fetching Places Data");
                if (urls.length == 1) {
                    referer = null;
                } else {
                    referer = urls[1];
                }
                //InstanceID instanceID = InstanceID.getInstance(this);
                //_token = Constant.AUTH_TOKEN;
                String input = GooglePlacesUtility.readGooglePlaces(urls[0], "http://aston.ac.uk/restaurantfinderapp");
                Gson gson = new Gson();
                GooglePlaceList places = gson.fromJson(input, GooglePlaceList.class);
                Log.i("PLACES_EXAMPLE", "Number of places found is " + places.getResults().size());
                // Log.i();
                return places;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("PLACES_EXAMPLE", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Getting nearby places...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(GooglePlaceList places) {
            this.dialog.dismiss();
            reportBack(places);

        }


    }
}
