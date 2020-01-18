package uk.ac.aston.restaurantfinderapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import uk.ac.aston.restaurantfinderapp.Fragments.EateryListFragment;

/**
 * Created by Emmanuel on 09/03/2016.
 */
public class EateryListActivity extends SingleFragmentActivity {
    private String eateryType;
    @Override
    protected Fragment createFragment() {

        return new EateryListFragment();
    }

}
