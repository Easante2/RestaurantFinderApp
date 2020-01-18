package uk.ac.aston.restaurantfinderapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.ac.aston.restaurantfinderapp.Model.GooglePlace;

/**
 * Created by Emmanuel on 22/03/2016.
 */
public class ListAdapter extends ArrayAdapter<GooglePlace> {

    public Context context;
    public List<GooglePlace> values;
    public String key= "";

    public ListAdapter(Context context, List<GooglePlace> values) {
        super(context, R.layout.fragment_eatery_custom_list, values);
        this.context = context;
        this.values = values;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final GooglePlace place = values.get(position);
        final View view = inflater.inflate(R.layout.fragment_eatery_custom_list, parent, false);
        TextView e_name = (TextView) view.findViewById(R.id.name);
        TextView e_vicinity = (TextView) view.findViewById(R.id.vicinity);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        RatingBar e_bar = (RatingBar) view.findViewById(R.id.ratingBar);
        e_name.setText(place.getName());
        e_vicinity.setText(place.getVicinity());
        e_bar.setRating(place.getRating());

        String pRef = "CmRdAAAAnJgE4i0Y5OpzT0mmGIQ4wHJEcCFMAo3ABT_hhP-oSwK_oj41jOvReQHQP7TaUn4ni37hsoy22fO1A37nEZiPIh1F0I9Locl9ZTlPbULo7Vy7a3L9tuUK7tx__kuxUrxxEhBMC6m3-tUEbSAh6icbzLjNGhQQ9H2d1FEhmjx_Z-fOqnY9TOv58A";

        if(place.getPhotos()!=null){
            pRef = place.getPhotos().get(0).getPhoto_reference();
        }



       String pReq= "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400" +
               "&photoreference="+ pRef +"&key=AIzaSyCxVmvL44XCgSDaahjKFQvREyfV2srZclU";
        Picasso.with(context).load(pReq).resize(150,150).into(imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Item clicked","list");
                // GooglePlace item = (GooglePlace) list.getItemAtPosition(position);
                Toast.makeText(getContext(), values.get(position).getName(),
                        Toast.LENGTH_LONG).show();
                Log.i("PLACES_EXAMPLE", values.get(position).getName());
            }
        });
        //TextView
        return view;
    }





}
