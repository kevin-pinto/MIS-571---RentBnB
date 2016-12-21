package edu.wpi.rentbnb.datamodels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kevin on 30-11-2016.
 */
public class RentalListingDetailsDTO implements Serializable{
	private Integer id;
	private RentalSpace rentalSpaceDetails;
	private Apartment apartmentDetails;
	private Location location;
	private String landlordNumber;
	private ArrayList<Review> reviews;
	private ArrayList<Tag> tags;
	private ArrayList<Photo> photos;

	public RentalListingDetailsDTO() {
		super();
		tags = new ArrayList<Tag>();
		photos = new ArrayList<Photo>();
		reviews = new ArrayList<Review>();
	}

	/**
	 * Fetches the apartmentDetails
	 *
	 * @return apartmentDetails
	 */
	public Apartment getApartmentDetails() {
		return apartmentDetails;
	}

	/**
	 * Sets the apartmentDetails
	 */
	public void setApartmentDetails(Apartment apartmentDetails) {
		this.apartmentDetails = apartmentDetails;
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
	 * Fetches the landlordNumber
	 *
	 * @return landlordNumber
	 */
	public String getLandlordNumber() {
		return landlordNumber;
	}

	/**
	 * Sets the landlordNumber
	 */
	public void setLandlordNumber(String landlordNumber) {
		this.landlordNumber = landlordNumber;
	}

	/**
	 * Fetches the location
	 *
	 * @return location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets the location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Fetches the reviews
	 *
	 * @return reviews
	 */
	public ArrayList<Review> getReviews() {
		return reviews;
	}

	/**
	 * Sets the reviews
	 */
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * Fetches the photos
	 *
	 * @return photos
	 */
	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	/**
	 * Sets the photos
	 */
	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}

	/**
	 * Fetches the rentalSpaceDetails
	 *
	 * @return rentalSpaceDetails
	 */
	public RentalSpace getRentalSpaceDetails() {
		return rentalSpaceDetails;
	}

	/**
	 * Sets the rentalSpaceDetails
	 */
	public void setRentalSpaceDetails(RentalSpace rentalSpaceDetails) {
		this.rentalSpaceDetails = rentalSpaceDetails;
	}

	/**
	 * Fetches the tags
	 *
	 * @return tags
	 */
	public ArrayList<Tag> getTags() {
		return tags;
	}

	/**
	 * Sets the tags
	 */
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
}
