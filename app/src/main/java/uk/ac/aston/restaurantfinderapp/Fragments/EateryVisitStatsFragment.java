package uk.ac.aston.restaurantfinderapp.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import uk.ac.aston.restaurantfinderapp.Model.DatabaseHelper;
import uk.ac.aston.restaurantfinderapp.R;

/**
 * Created by Emmanuel on 09/04/2016.
 */
public class EateryVisitStatsFragment extends Fragment {
    private String eatery;
    private int numOfVisits =0;
    private DatabaseHelper dbHelper;
    ArrayList<String> a;
    int[] b;
    private FavouriteDetailFragment fragment;
    private String placeID;

   /* public EateryVisitStatsFragment(String eatery) {
        this.eatery = eatery;
    }*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        getActivity().setTitle("My Restaurant Visits");


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_eatery_visit_stats, container, false);
       /* dbHelper = new DatabaseHelper(getContext());
        final Cursor c =dbHelper.getAllFavourites();
        getActivity().startManagingCursor(c);
        String[] ids= new String []{
                "_id"};

        for(String i : ids){
            if(placeID.equals(i)){
                dbHelper.updateVisits(placeID);
            }
        }*/
        dbHelper = new DatabaseHelper(getContext());
        ListView gridView = (ListView) v.findViewById(R.id.grid);
        final Cursor c =dbHelper.getAllFavourites();
        String[] from= new String []{
                DatabaseHelper.eateryName,DatabaseHelper.visits, "_id"};
        int[] to = new int []{
                R.id.colName, R.id.colVisit};
        SimpleCursorAdapter sca=new SimpleCursorAdapter(getContext(),R.layout.fragment_eatery_visit_stats_grid_layout,c,from,to);
        gridView.setAdapter(sca);

        createGridView();
        return v;
    }



    public void createGridView(){
        //dbHelper = new DatabaseHelper(getContext());

        final Cursor c =dbHelper.getAllFavourites();
        getActivity().startManagingCursor(c);


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
