package uk.ac.aston.restaurantfinderapp.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import uk.ac.aston.restaurantfinderapp.FavouriteDetailActivity;
import uk.ac.aston.restaurantfinderapp.Model.DatabaseHelper;
import uk.ac.aston.restaurantfinderapp.R;

/**
 * Created by Emmanuel on 09/04/2016.
 */
public class FavouriteListFragment extends Fragment {
    private long returnValue = -1;
    private DatabaseHelper dbHelper;

    public static String rPlaceID;
    public static String rEateryName;
    public static String rEateryAd;
    public static double rLat;
    public static double rLong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // (handlerRequest);
        getActivity().setTitle("Favourites");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourites, container, false);

        dbHelper = new DatabaseHelper(getContext());
        ListView list = (ListView) v.findViewById(R.id.List);
        final Cursor c =dbHelper.getAllFavourites();
            getActivity().startManagingCursor(c);

        String[] from= new String []{
                DatabaseHelper.eateryName,DatabaseHelper.eateryAddress,DatabaseHelper.placeID, "_id", DatabaseHelper.latitude, DatabaseHelper.longitude};

        int[] to= new int [] {
                R.id.eName,R.id.eAddress};

        SimpleCursorAdapter sca=new SimpleCursorAdapter(getContext(),R.layout.fragment_favourites_list_layout,c,from,to);
        list.setAdapter(sca);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Cursor cursor = (Cursor) parent.getItem(position);
                c.moveToPosition(position);

                String rowPlaceID = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.placeID));
                String rowName = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.eateryName));
               // String rowVicinity = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.eateryVicinity));
                String rowAd = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.eateryAddress));
                double lat = c.getDouble(c.getColumnIndexOrThrow(DatabaseHelper.latitude));
                double lng = c.getDouble(c.getColumnIndexOrThrow(DatabaseHelper.longitude));

                rPlaceID = rowPlaceID;
                rEateryName= rowName;
                rEateryAd = rowAd;
                rLat = lat;
                rLong = lng;
                Intent i = new Intent(getActivity(), FavouriteDetailActivity.class);
                startActivity(i);

            }
        });


        return v;
    }


    public static double getrLong() {
        return rLong;
    }
    public static double getrLat() {
        return rLat;
    }


    public static String getrEateryAd() {
        return rEateryAd;
    }


    public static String getrEateryName() {
        return rEateryName;
    }
    public static String getrPlaceID() {
        return rPlaceID;
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
  /*  public void handlerRequest(){
        CardView removeItem = new (CardView)v.findViewById(R.id.remove_card_view);

    }*/
}
