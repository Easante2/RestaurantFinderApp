package uk.ac.aston.restaurantfinderapp.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Emmanuel on 07/04/2016.
 */
public class MarkerItem implements ClusterItem {
    private String name;
    private String description;
    private LatLng location;
    private GooglePlace place;



    public MarkerItem(String name, GooglePlace place, LatLng location) {
        this.name = name;
        this.location = location;
        this.place = place;
    }

    public GooglePlace getPlace() {
        return place;
    }

    public void setPlace(GooglePlace place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setLocation(LatLng location) {
        this.location = location;
    }

    @Override
    public LatLng getPosition() {
        return location;
    }
}
