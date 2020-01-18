package uk.ac.aston.restaurantfinderapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.ac.aston.restaurantfinderapp.EateryVisitStatsActivity;
import uk.ac.aston.restaurantfinderapp.FavouriteDetailActivity;
import uk.ac.aston.restaurantfinderapp.FavouriteListActivity;
import uk.ac.aston.restaurantfinderapp.Model.DatabaseHelper;
import uk.ac.aston.restaurantfinderapp.Model.GooglePlace;
import uk.ac.aston.restaurantfinderapp.Model.GooglePlaceList;
import uk.ac.aston.restaurantfinderapp.Model.GooglePlacesUtility;
import uk.ac.aston.restaurantfinderapp.Model.PlaceDetail;
import uk.ac.aston.restaurantfinderapp.PlaceDetailActivity;
import uk.ac.aston.restaurantfinderapp.R;
import uk.ac.aston.restaurantfinderapp.RouteMapActivity;

/**
 * Created by Emmanuel on 06/04/2016.
 */
public class PlaceDetailFragment extends Fragment {

    private GooglePlace gPlaceDetail;
    private GooglePlace place;
    private GooglePlaceList eateryPlaces;
    private String eateryType;
    private String c;
    private double longitude;
    private double latitude;
    private Button btnAddToFav;
    private Button btnBeginRoute;
    private String apiKey = "" ;
    private long returnValue;
    private DatabaseHelper dbHelper;
    private ShareActionProvider mShareActionProvider;


    public PlaceDetailFragment() {
        super();
        //contact = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        returnValue = -1;
        dbHelper = new DatabaseHelper(getContext());

            c = MapFragment.getID();

            eateryType = (String) getArguments().getSerializable("type");

            longitude = (double) getArguments().getSerializable("Long");

            latitude =  (double) getArguments().getSerializable("Lat");



        place = new GooglePlace();
        gPlaceDetail = new GooglePlace();

        String placesRequest = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                latitude + "," + longitude + "&radius=500&type="+eateryType+"&key=" + apiKey;
        PlacesReadEatery process = new PlacesReadEatery();
        process.execute(new String[]{placesRequest});





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        inflater.inflate(R.menu.share, menu);
        MenuItem item = menu.findItem(R.id.menu_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
    }




    public static PlaceDetailFragment newInstance(String eateryId) {
        Bundle args = new Bundle();
        args.putSerializable("PLACE", eateryId);
        PlaceDetailFragment fragment = new PlaceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // we haven't got the data to fill in yet

        //View view =
        return inflater.inflate(R.layout.fragment_place_details, container, false);
    }



    public void runPlaceDetails(){
        String placesRequest = "https://maps.googleapis.com/maps/api/place/details/json?" +
                "key=" + apiKey + "&reference=" + place.getReference();
        PlacesDetailReadEatery detailTask = new PlacesDetailReadEatery();
        detailTask.execute(placesRequest);

    }

    protected void reportBack(GooglePlaceList eateryPlaces) {
        this.eateryPlaces = eateryPlaces;
        for(GooglePlace item: eateryPlaces.getResults()){
            if(item.getId().equals(c)){
                place = item;
                Toast.makeText(getContext(),place.getName(), Toast.LENGTH_LONG).show();
                Log.i("Name", place.getName());
            }
        }
        getActivity().setTitle(place.getName());

        runPlaceDetails();

    }


    protected void postReport(PlaceDetail eateryDetail){
        gPlaceDetail = eateryDetail.getResult();
        populateLayout(gPlaceDetail);
    }

    private class PlacesDetailReadEatery extends AsyncTask<String, Void, PlaceDetail> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected PlaceDetail doInBackground(String... urls) {
            try {
                //dialog.setMessage("Fetching Places Data");
                String input = GooglePlacesUtility.readGooglePlaces(urls[0], "http://aston.ac.uk/restaurantfinderapp");
                Gson gson = new Gson();
                PlaceDetail place = gson.fromJson(input, PlaceDetail.class);
                Log.i( "Place found is " , place.toString());
                return place;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Error", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Getting place details...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(PlaceDetail placeDetail) {
           // gPlaceDetail = placeDetail.getResult();
            postReport(placeDetail);
         //  populateLayout(place);
            this.dialog.dismiss();
        }
    }

    private void populateLayout(final GooglePlace ePlace) {
        // title element has name and types
        ImageView imageView = (ImageView) getView().findViewById(R.id.img);
        String pRef = "CmRdAAAAnJgE4i0Y5OpzT0mmGIQ4wHJEcCFMAo3ABT_hhP-oSwK_oj41jOvReQHQP7TaUn4" +
                "ni37hsoy22fO1A37nEZiPIh1F0I9Locl9ZTlPbULo7Vy7a3L9tuUK7tx__kuxUrxxEhBMC6m3-tUEbSAh6icbzLjNGhQQ9H2d1FEhmjx_Z-fOqnY9TOv58A";

        if(place.getPhotos()!=null){
            pRef = place.getPhotos().get(0).getPhoto_reference();
        }

        String pReq= "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400" +
                "&photoreference="+ pRef +"&key=AIzaSyCxVmvL44XCgSDaahjKFQvREyfV2srZclU";
        Picasso.with(getContext()).load(pReq).resize(100,100).into(imageView);

        TextView title = (TextView)getView().findViewById(R.id.pDetail_name);
        title.setText(ePlace.getName());
        Log.i("Setting title ","to:" + title.getText());
        //address
        TextView address = (TextView)getView().findViewById(R.id.pDetail_address);
        String number;
        if(place.getFormatted_phone_number()== null){
            number = "";
        }else {
            number = place.getFormatted_phone_number();
        }

        address.setText("Address:\n"+ePlace.getFormatted_address() + " " + number);
        Log.i("Setting address " , "to:" + address.getText());
        //vicinity
        TextView vicinity = (TextView)getView().findViewById(R.id.pDetail_vicinity);
        vicinity.setText("Vicinity:\n"+ePlace.getVicinity());
        Log.i("Setting vicinity  " ,"to:" + vicinity.getText());
        //rating
        TextView reviews = (TextView)getView().findViewById(R.id.pDetail_reviews);

        List<GooglePlace.Review> reviewsData = ePlace.getReviews();
        if (reviewsData != null) {
            StringBuilder sb = new StringBuilder();
            for (GooglePlace.Review r : reviewsData) {
                sb.append(r.getAuthor_name());
                sb.append(" says \"");
                sb.append(r.getText());
                sb.append("\" and rated it ");
                sb.append(r.getRating());
                sb.append("\n");
            }
            reviews.setText("Reviews:\n" + sb.toString());
        } else {
            reviews.setText("There have not been any reviews!");
        }
        Log.i("Setting rating ", "to:" + reviews.getText());
        getActivity().setTitle(place.getName());

        btnAddToFav = (Button) getView().findViewById(R.id.addToFav);
        btnAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnValue = dbHelper.addToFavourites(place.getPlace_id(), place.getName(),
                        ePlace.getFormatted_address(), ePlace.getGeometry().getLocation().getLat(),
                        ePlace.getGeometry().getLocation().getLng(),0);
                Toast.makeText(getContext(), "Successfully added to favourites", Toast.LENGTH_LONG).show();
                if (returnValue < 0) {
                    Toast.makeText(getContext(), "There was an error inserting adding to favourites",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBeginRoute = (Button) getView().findViewById(R.id.beginRoute);
        btnBeginRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RouteMapActivity.class);
                i.putExtra("latitude", ePlace.getGeometry().getLocation().getLat());
                i.putExtra("longitude", ePlace.getGeometry().getLocation().getLng());
                i.putExtra("Eatery", ePlace.getName());
                i.putExtra("PID", ePlace.getPlace_id());
                startActivity(i);
            }
        });




    }



    private class PlacesReadEatery extends AsyncTask<String, Void, GooglePlaceList> {
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
