package edu.wpi.rentbnb.datamodels;

/**
 * Created by nikitalondhe on 11/16/16. <br/><br/>
 * This class contains detailed information of the location at which the apartment is situated. Along with
 Street name, it contains latitude and longitude value that can be used to be plotted on the maps.
 */

public class Location {

    private Integer id;
    private String street;
    private double latitude;
    private double longitude;

    /**
     * Fetches the id
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Fetches the street
     *
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Fetches the latitude
     *
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Fetches the longitude
     *
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
