package uk.ac.aston.restaurantfinderapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import uk.ac.aston.restaurantfinderapp.Fragments.RouteMapFragment;

/**
 * Created by Emmanuel on 11/04/2016.
 */
public class RouteMapActivity extends SingleFragmentActivity {
        private double lat = 0;
        private double lng = 0;

    @Override
    protected Fragment createFragment() {
        Bundle extras = getIntent().getExtras();
        lat = extras.getDouble("latitude");
        lng = extras.getDouble("longitude");
        return  RouteMapFragment.newInstance(this, lat, lng);
    }
}
