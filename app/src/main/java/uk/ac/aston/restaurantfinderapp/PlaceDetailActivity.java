package uk.ac.aston.restaurantfinderapp;

import android.support.v4.app.Fragment;

import uk.ac.aston.restaurantfinderapp.Fragments.PlaceDetailFragment;


/**
 * Created by Emmanuel on 06/04/2016.
 */
public class PlaceDetailActivity extends SingleFragmentActivity {
    private String eateryId;
    private double longitude;
    private double latitude;


    @Override
    protected Fragment createFragment() {
        String eateryId = (String) getIntent().getSerializableExtra("PLACE");
       // double longitude = (double) getIntent().getSerializableExtra("Long");
       // double latitude = (double) getIntent().getSerializableExtra("Long");

        return PlaceDetailFragment.newInstance(eateryId);
    }
}
