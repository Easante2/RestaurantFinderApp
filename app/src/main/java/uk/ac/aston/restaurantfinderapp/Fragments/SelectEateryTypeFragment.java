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

import uk.ac.aston.restaurantfinderapp.EateryListActivity;
import uk.ac.aston.restaurantfinderapp.FavouriteListActivity;
import uk.ac.aston.restaurantfinderapp.MapFragmentActivity;
import uk.ac.aston.restaurantfinderapp.R;

/**
 * Created by Emmanuel on 09/04/2016.
 */
public class SelectEateryTypeFragment extends Fragment {
    private CardView cafe;
    private CardView restaurant;
    private CardView fastFood;
    public static String e_Type="";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        getActivity().setTitle("Select Eatery Type");


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_select_eatery_type, container, false);
            cafe = (CardView) v.findViewById(R.id.cafe_card_view);
            cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_Type = "cafe";
                Intent intent = new Intent(getContext(), EateryListActivity.class);
                intent.putExtra("type", "cafe");
                startActivity(intent);
            }
        });
            restaurant = (CardView) v.findViewById(R.id.restaurant_card_view);
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_Type = "restaurant";
                Intent intent = new Intent(getContext(), EateryListActivity.class);
                intent.putExtra("type", "restaurant");
                startActivity(intent);
            }
        });
            fastFood = (CardView) v.findViewById(R.id.fastFood_card_view);
        fastFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_Type = "meal_takeaway";
                Intent intent = new Intent(getContext(), EateryListActivity.class);
                intent.putExtra("type", "meal_takeaway");
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
    public static String gete_Type(){
        return e_Type;
    }



}
