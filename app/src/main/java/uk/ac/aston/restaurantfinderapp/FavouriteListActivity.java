package uk.ac.aston.restaurantfinderapp;

import android.support.v4.app.Fragment;

import uk.ac.aston.restaurantfinderapp.Fragments.FavouriteListFragment;

/**
 * Created by Emmanuel on 09/04/2016.
 */
public class FavouriteListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new FavouriteListFragment();
    }


}
