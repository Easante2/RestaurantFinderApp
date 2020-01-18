package uk.ac.aston.restaurantfinderapp;

import android.support.v4.app.Fragment;

import uk.ac.aston.restaurantfinderapp.Fragments.SelectEateryTypeFragment;

/**
 * Created by Emmanuel on 09/04/2016.
 */
public class SelectEateryTypeActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new SelectEateryTypeFragment();
    }


}
