package uk.ac.aston.restaurantfinderapp.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import uk.ac.aston.restaurantfinderapp.EateryVisitStatsActivity;
import uk.ac.aston.restaurantfinderapp.FavouriteDetailActivity;
import uk.ac.aston.restaurantfinderapp.FavouriteListActivity;
import uk.ac.aston.restaurantfinderapp.Model.DatabaseHelper;
import uk.ac.aston.restaurantfinderapp.R;
import uk.ac.aston.restaurantfinderapp.RouteMapActivity;

/**
 * Created by Emmanuel on 10/04/2016.
 */
public class FavouriteDetailFragment extends Fragment {
    private String placeID;
    private String eateryName="";
    private String vicinity;
    private String address;
    private TextView TVname;
    private TextView TVaddress;
    private Button deleteFromFav;
    private Button beginRoute;
    private long returnValue = -1;
    private DatabaseHelper dbHelper;
    private double lat;
    private double lng;
    private ShareActionProvider mShareActionProvider;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Favourites");
        dbHelper = new DatabaseHelper(getContext());

            placeID = FavouriteListFragment.getrPlaceID();

            eateryName = FavouriteListFragment.getrEateryName();;

            address = FavouriteListFragment.getrEateryAd();

            lat = FavouriteListFragment.getrLat();

            lng = FavouriteListFragment.getrLong();



        this.setHasOptionsMenu(true);
        getActivity().setTitle("Favourites");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite_detail, container, false);
            TVname = (TextView) v.findViewById(R.id.e_name);
            TVaddress = (TextView) v.findViewById(R.id.e_address);

            TVname.setText(eateryName);
            TVaddress.setText(address);



            deleteFromFav = (Button) v.findViewById(R.id.deleteFav);
            deleteFromFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteFromFavourites(placeID);
                Toast.makeText(getContext(), eateryName + " deleted from favourites",
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), FavouriteListActivity.class);
                startActivity(i);
            }
        });

            beginRoute = (Button) v.findViewById(R.id.route);
            beginRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), RouteMapActivity.class);
                    i.putExtra("PID", placeID);
                    i.putExtra("latitude", lat);
                    i.putExtra("longitude", lng);
                    startActivity(i);


                }
            });



        return v;
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





}
