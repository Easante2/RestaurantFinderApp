package uk.ac.aston.restaurantfinderapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uk.ac.aston.restaurantfinderapp.R;

public class SetBudgetFragment extends Fragment {

    public String maxBudget;
    private Button btnConfirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        getActivity().setTitle("Set Budget");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set_budget, container, false);
        final EditText mBudget = (EditText) v.findViewById(R.id.budget);
        btnConfirm = (Button) v.findViewById(R.id.confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxBudget = mBudget.getText().toString();
                Toast.makeText(getContext(), "Max budget set",
                        Toast.LENGTH_LONG).show();

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
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }




    public String getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(String maxBudget) {
        this.maxBudget = maxBudget;
    }
}
