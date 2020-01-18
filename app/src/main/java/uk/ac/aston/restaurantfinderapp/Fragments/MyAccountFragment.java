package uk.ac.aston.restaurantfinderapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.aston.restaurantfinderapp.EateryVisitStatsActivity;
import uk.ac.aston.restaurantfinderapp.FavouriteListActivity;
import uk.ac.aston.restaurantfinderapp.R;
import uk.ac.aston.restaurantfinderapp.SetBudgetActivity;

/**
 * Created by Emmanuel on 09/04/2016.
 */
public class MyAccountFragment extends Fragment {
    private CardView favourites;
    private CardView eateryVisits;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        getActivity().setTitle("My Account");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_account, container, false);

        favourites = (CardView) v.findViewById(R.id.favourites_card_view);
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FavouriteListActivity.class);
                startActivity(intent);
            }
        });

        eateryVisits = (CardView) v.findViewById(R.id.eatery_visit_stats_card_view);
        eateryVisits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EateryVisitStatsActivity.class);
                startActivity(intent);
            }
        });

        return v;
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
