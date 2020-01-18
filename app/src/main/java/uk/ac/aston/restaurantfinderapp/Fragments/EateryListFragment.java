package uk.ac.aston.restaurantfinderapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import uk.ac.aston.restaurantfinderapp.EateryListActivity;
import uk.ac.aston.restaurantfinderapp.ListAdapter;
import uk.ac.aston.restaurantfinderapp.MapFragmentActivity;
import uk.ac.aston.restaurantfinderapp.Model.GPSTracker;
import uk.ac.aston.restaurantfinderapp.Model.GooglePlace;
import uk.ac.aston.restaurantfinderapp.Model.GooglePlaceList;
import uk.ac.aston.restaurantfinderapp.Model.GooglePlacesUtility;
import uk.ac.aston.restaurantfinderapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link EateryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EateryListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private GooglePlaceList nearby;
    private GooglePlace eateryItem;
    private ListView list;
    private Button mapButton;
    private String placesKey = "" ;
    private double latitude;
    private double longitude;
    private String budget;
    public SelectEateryTypeFragment fragment;
    public String eateryType;
    private Context context ;
    GPSTracker gps;

    public EateryListFragment() {
        // Required empty public constructor
    }

    public static EateryListFragment newInstance(String param1, String param2) {

        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.onSaveInstanceState(savedInstanceState)
        //eateryType = (String) getArguments().getSerializable("type");
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eatery_list, container, false);
        eateryType= SelectEateryTypeFragment.gete_Type();
        context = getActivity();
        gps = new GPSTracker(context);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        list = (ListView) view.findViewById(R.id.list);
        mapButton = (Button) view.findViewById(R.id.btnMap);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MapFragmentActivity.class);
                i.putExtra("type", eateryType);
                startActivity(i);
            }
        });
        getActivity().setTitle("Search results");
       this.setHasOptionsMenu(true);

            String placesRequest = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                    latitude + "," + longitude + "&radius=500&type="+eateryType+"&key=" + placesKey;
            PlacesReadEatery process = new PlacesReadEatery();
            process.execute(new String[]{placesRequest});
            getView();
        return view ;

    }


    public String getEateryType() {
        return eateryType;
    }


    protected void reportBack(GooglePlaceList nearby) {
        if (this.nearby == null) {
            this.nearby = nearby;

        } else {
            this.nearby.getResults().addAll(nearby.getResults());
        }


        ListAdapter adapter = new ListAdapter(getContext(), nearby.getResults());
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
