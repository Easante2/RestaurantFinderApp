package uk.ac.aston.restaurantfinderapp.Model;

/**
 * Created by Emmanuel on 08/03/2016.
 */
public class EateryDetail {

    //@Key
    private GooglePlace result;

    public GooglePlace getResult() {
        return result;
    }

    public void setResult(GooglePlace result) {
        this.result = result;
    }

    @Override
    public String toString() {
        if (result!=null) {
            return result.toString();
        }
        return super.toString();
    }

}
