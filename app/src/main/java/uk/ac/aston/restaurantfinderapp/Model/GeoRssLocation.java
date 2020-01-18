package uk.ac.aston.restaurantfinderapp.Model;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Emmanuel on 17/02/2016.
 */
public class GeoRssLocation implements Serializable {

    private static final long serialVersionUID = -1861462299276634548L;
    private double latitude;
    private double longitude;

    /**
     * @return the lat
     */
    public double setLat(double lat) {
        return latitude=lat;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GeoRssLocation) {
            if (this == o) return true;
            else {
                GeoRssLocation other = (GeoRssLocation) o;
                return latitude == other.latitude &&
                        longitude == other.longitude;
            }
        } else {
            return false;
        }
    }


    /**
     * @return the lng
     */
    public double setLng(double lon) {
        return longitude = lon;
    }

    public LatLng getLatLon() {
        return new LatLng(this.latitude, this.longitude);
    }

 /*
    public GeoPoint getGeoPoint() {
        int latE6 = (int) (getLat() * 1e6);
        int lonE6 = (int) (getLng() * 1e6);
        return new GeoPoint(latE6, lonE6);

    }
    */

    public String toString() {
        return "(" + latitude + "," + longitude + ")";
    }
}
