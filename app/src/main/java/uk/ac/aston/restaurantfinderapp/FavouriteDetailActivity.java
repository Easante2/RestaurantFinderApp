package uk.ac.aston.restaurantfinderapp;

import android.support.v4.app.Fragment;

import uk.ac.aston.restaurantfinderapp.Fragments.FavouriteDetailFragment;

/**
 * Created by Emmanuel on 11/04/2016.
 */
public class FavouriteDetailActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new FavouriteDetailFragment();
    }
}
