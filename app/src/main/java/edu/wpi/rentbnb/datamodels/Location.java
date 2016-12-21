package edu.wpi.rentbnb.datamodels;

import java.io.Serializable;

import edu.wpi.rentbnb.helper.ApplicationHelper;

/**
 * Created by nikitalondhe on 11/16/16. <br/>
 * <br/>
 * This class contains detailed information of the location at which the
 * apartment is situated. Along with Street name, it contains latitude and
 * longitude value that can be used to be plotted on the maps.
 */
public class Location implements Serializable {
	private Integer id;
	private String street;
	private double latitude;
	private double longitude;
	private Integer pincode;
	private String city;
	private String country;
	private double distanceFromCampus;

	public Location() {
	}

	public Location(Integer id, String street, double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.street = street;
		this.id = id;
	}

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

	/**
	 * Fetches the city
	 *
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Fetches the country
	 *
	 * @return country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Fetches the pincode
	 *
	 * @return pincode
	 */
	public Integer getPincode() {
		return pincode;
	}

	/**
	 * Sets the pincode
	 */
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	/**
	 * Fetches the distanceFromCampus
	 *
	 * @return distanceFromCampus
	 */
	public double getDistanceFromCampus() {
		return ApplicationHelper.getInstance().fetchDistanceFromCampus(this);
	}

	/**
	 * Sets the distanceFromCampus
	 */
	public void setDistanceFromCampus(double distanceFromCampus) {
		this.distanceFromCampus = distanceFromCampus;
	}
}
