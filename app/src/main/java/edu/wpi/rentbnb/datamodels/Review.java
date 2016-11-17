package edu.wpi.rentbnb.datamodels;

import java.util.Date;

/**
 * Created by nikitalondhe on 11/16/16.<br/>
 * <br/>
 * This table contains a list of review submitted by the users regarding the
 * rental spaces that have been previously visited/rented.
 */
public class Review {
	/**
	 * Uniquely identifies the review id
	 */
	private Integer id;
	/**
	 * Indicates the rental space id
	 */
	private Integer rentalId;
	/**
	 * Indicates the user id
	 */
	private Integer userId;
	/**
	 * Indicates the review comments for the apartment listing
	 */
	private String comments;
	/**
	 * Indicates the rating for the apartment listing
	 */
	private Integer rating;
	/**
	 * Indicates the option for recommendation for the apartment
	 */
	private boolean wouldRecommend;
	/**
	 * Indicates the creation date of the review comment for the apartment
	 * listing
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
	 * Fetches the rating
	 *
	 * @return rating
	 */
	public Integer getRating() {
		return rating;
	}

	/**
	 * Sets the rating
	 */
	public void setRating(Integer rating) {
		this.rating = rating;
	}

	/**
	 * Fetches the wouldRecommend
	 *
	 * @return wouldRecommend
	 */
	public boolean isWouldRecommend() {
		return wouldRecommend;
	}

	/**
	 * Sets the wouldRecommend
	 */
	public void setWouldRecommend(boolean wouldRecommend) {
		this.wouldRecommend = wouldRecommend;
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
		return "Review{" + "id=" + id + ", rentalId=" + rentalId + ", userId=" + userId + ", comments='" + comments + '\'' + ", rating=" + rating + ", wouldRecommend=" + wouldRecommend
				+ ", creationDate=" + creationDate + '}';
	}
}
