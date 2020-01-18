package uk.ac.aston.restaurantfinderapp;

import android.support.v4.app.Fragment;

import uk.ac.aston.restaurantfinderapp.Fragments.MapFragment;

/**
 * Created by Emmanuel on 17/02/2016.
 */
public class MapFragmentActivity extends SingleFragmentActivity {
    public static final String ITEM = "aston.cs3040.item";


    @Override
    protected Fragment createFragment() {
       // Bundle extras = getIntent().getExtras();
        //double longitude = extras.getDouble("long");
       // double latitude = extras.getDouble("lat");
            return MapFragment.newInstance(this);
        }
    }

