package edu.wpi.rentbnb.datamodels;

/**
 * Created by nikitalondhe on 11/16/16.<br/>
 * <br/>
 * This class contains a list of pictures of the rental spaces the have been
 * uploaded by the Leaser.
 */
public class Photo {
	/**
	 * Uniquely identifies the picture id
	 */
	private Integer id;
	/**
	 * Indicates the rental space id
	 */
	private Integer rentalId;
	/**
	 * Indicates the picture data
	 */
	private byte[] data;
	/**
	 * Indicates the picture description
	 */
	private String description;

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
	 * Fetches the rentalId
	 *
	 * @return rentalId
	 */
	public Integer getRentalId() {
		return rentalId;
	}

	/**
	 * Sets the rentalId
	 */
	public void setRentalId(Integer rentalId) {
		this.rentalId = rentalId;
	}

	/**
	 * Fetches the data
	 *
	 * @return data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Sets the data
	 */
	public void setData(byte[] data) {
		this.data = data;
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

	@Override
	public String toString() {
		return "Photo{" + "id=" + id + ", rentalId=" + rentalId + ", data=" + data + ", description='" + description + '\'' + '}';
	}
}
