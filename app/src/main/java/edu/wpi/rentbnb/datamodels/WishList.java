package edu.wpi.rentbnb.datamodels;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nikitalondhe on 11/16/16.<br/>
 * <br/>
 * This class saves all the rental spaces that have been marked as favorite by
 * the user and can be used for future reference.
 */
public class WishList implements Serializable {
	/**
	 * Indicates the wishlist id
	 */
	private Integer id;
	/**
	 * Indicates the rental space id
	 */
	private Integer rentalId;
	/**
	 * Indicates the users id
	 */
	private Integer userId;
	/**
	 * Indicates the comments for the apartments
	 */
	private String comments;
	/**
	 * Indicates the creation date for the apartment comments
	 */
	private Date creationDate;

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
	 * Fetches the userId
	 *
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Sets the userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * Fetches the comments
	 *
	 * @return comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Sets the comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Fetches the creationDate
	 *
	 * @return creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creationDate
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "WishList{" + "id=" + id + ", rentalId=" + rentalId + ", comments='" + comments + '\'' + ", creationDate=" + creationDate + '}';
	}
}
