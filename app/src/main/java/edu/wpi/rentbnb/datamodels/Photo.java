package edu.wpi.rentbnb.datamodels;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * Created by nikitalondhe on 11/16/16.<br/>
 * <br/>
 * This class contains a list of pictures of the rental spaces the have been
 * uploaded by the Leaser.
 */
public class Photo implements Serializable {
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
	private transient Bitmap data;
	/**
	 * Indicates the picture description
	 */
	private String description;

	public Photo(Integer id, Integer rentalId, Bitmap data, String description) {
		this.data = data;
		this.description = description;
		this.id = id;
		this.rentalId = rentalId;
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
	public Bitmap getData() {
		return data;
	}

	/**
	 * Sets the data
	 */
	public void setData(Bitmap data) {
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
