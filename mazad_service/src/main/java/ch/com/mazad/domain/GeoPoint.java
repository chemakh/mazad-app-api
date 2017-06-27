package ch.com.mazad.domain;

/**
 * Created by Chemakh on 22/05/2017.
 */
public class GeoPoint {

    private double lat;
    private double lon;

    private GeoPoint() {
    }

    public GeoPoint(double latitude, double longitude) {
        this.lat = latitude;
        this.lon = longitude;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

}