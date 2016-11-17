package edu.wpi.rentbnb.datamodels;

/**
 * Created by nikitalondhe on 11/16/16.<br/>
 * <br/>
 * This class contains a higher level detailed information regarding the
 * locality, total number of rooms in the apartment and the tags associated with
 * the apartment.
 */
public class Apartment {
	/**
	 * Uniquely identifies the apartment.
	 */
	private Integer id;
	/**
	 * Uniquely identifies the location.
	 */
	private Integer locationId;
	/**
	 * Indicates the apartment number.
	 */
	private Integer number;
	/**
	 * Indicates the description that has been provided for the apartment
	 */
	private String description;
	/**
	 * Indicates the list of rooms associated with the apartment
	 */
	private Integer totalRooms;
	/**
	 * Indicates whether the apartment is verified or not
	 */
	private boolean isVerified;

	/**
	 * Fetches the id
	 *
	 * @return id
	 */
	public Integer getId() {
		new User().getId();
		return id;
	}

	/**
	 * Sets the id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Fetches the locationId
	 *
	 * @return locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * Sets the locationId
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * Fetches the number
	 *
	 * @return number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * Sets the number
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * Fetches the description
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Fetches the totalRooms
	 *
	 * @return totalRooms
	 */
	public Integer getTotalRooms() {
		return totalRooms;
	}

	/**
	 * Sets the totalRooms
	 */
	public void setTotalRooms(Integer totalRooms) {
		this.totalRooms = totalRooms;
	}

	/**
	 * Fetches the isVerified
	 *
	 * @return isVerified
	 */
	public boolean isVerified() {
		return isVerified;
	}

	/**
	 * Sets the isVerified
	 */
	public void setVerified(boolean verified) {
		isVerified = verified;
	}

	@Override
	public String toString() {
		return "Apartment{" + "id=" + id + ", locationId=" + locationId + ", number=" + number + ", description='" + description + '\'' + ", totalRooms=" + totalRooms + ", isVerified=" + isVerified
				+ '}';
	}
}
