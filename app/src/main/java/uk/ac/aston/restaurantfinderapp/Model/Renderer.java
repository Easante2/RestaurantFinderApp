package uk.ac.aston.restaurantfinderapp.Model;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import uk.ac.aston.restaurantfinderapp.PlaceDetailActivity;

/**
 * Created by Emmanuel on 07/04/2016.
 */
public class Renderer extends DefaultClusterRenderer<MarkerItem> {

    public Renderer(Context context, GoogleMap map, ClusterManager<MarkerItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MarkerItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        markerOptions.title(item.getName());
    }
}
