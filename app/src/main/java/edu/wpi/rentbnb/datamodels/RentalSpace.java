package edu.wpi.rentbnb.datamodels;

import java.util.Date;

/**
 * Created by nikitalondhe on 11/16/16.<br/><br/>
 * This class contains a lower level detailed information regarding rooms within an apartment up for sale.
 */

public class RentalSpace {
    /**
     * Uniquely identifies the rental space
     */
    private Integer id;
    /**
     * Indicates the apartment id that has been provided for the given rental space
     */
    private Integer aptId;
    /**
     * Indicates the user id of the landlord
     */
    private Integer landlordId;
    /**
     * Indicates the price that has been provided for the given rental space
     */
    private Integer price;
    /**
     * Indicates the description that has been provided for the given rental space
     */
    private String description;
    /**
     * Indicates the availability from that has been provided for the given rental space
     */
    private Date availableFrom;
    /**
     * Indicates the availability to that has been provided for the given rental space
     */
    private Date availableTo;
    /**
     * Date when the rental space was created to be listed.
     */
    private Date creationDate;
    /**
     * Indicates the status whether the apartment is currently rented or not
     */
    private boolean isRented;
    /**
     * Indicates the status whether the apartment has been verified or not
     */
    private boolean isVerified;
    /**
     * Indicates the whether the apartment includes the utilities or not
     */
    private boolean isUtilitiesIncluded;

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
     * Fetches the aptId
     *
     * @return aptId
     */
    public Integer getAptId() {
        return aptId;
    }

    /**
     * Sets the aptId
     */
    public void setAptId(Integer aptId) {
        this.aptId = aptId;
    }

    /**
     * Fetches the landlordId
     *
     * @return landlordId
     */
    public Integer getLandlordId() {
        return landlordId;
    }

    /**
     * Sets the landlordId
     */
    public void setLandlordId(Integer landlordId) {
        this.landlordId = landlordId;
    }

    /**
     * Fetches the price
     *
     * @return price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets the price
     */
    public void setPrice(Integer price) {
        this.price = price;
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
     * Fetches the availableFrom
     *
     * @return availableFrom
     */
    public Date getAvailableFrom() {
        return availableFrom;
    }

    /**
     * Sets the availableFrom
     */
    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    /**
     * Fetches the availableTo
     *
     * @return availableTo
     */
    public Date getAvailableTo() {
        return availableTo;
    }

    /**
     * Sets the availableTo
     */
    public void setAvailableTo(Date availableTo) {
        this.availableTo = availableTo;
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

    /**
     * Fetches the isRented
     *
     * @return isRented
     */
    public boolean isRented() {
        return isRented;
    }

    /**
     * Sets the isRented
     */
    public void setRented(boolean rented) {
        isRented = rented;
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

    /**
     * Fetches the isUtilitiesIncluded
     *
     * @return isUtilitiesIncluded
     */
    public boolean isUtilitiesIncluded() {
        return isUtilitiesIncluded;
    }

    /**
     * Sets the isUtilitiesIncluded
     */
    public void setUtilitiesIncluded(boolean utilitiesIncluded) {
        isUtilitiesIncluded = utilitiesIncluded;
    }

    @Override
    public String toString() {
        return "RentalSpace{" +
                "id=" + id +
                ", aptId=" + aptId +
                ", landlordId=" + landlordId +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", availableFrom=" + availableFrom +
                ", availableTo=" + availableTo +
                ", creationDate=" + creationDate +
                ", isRented=" + isRented +
                ", isVerified=" + isVerified +
                ", isUtilitiesIncluded=" + isUtilitiesIncluded +
                '}';
    }
}

