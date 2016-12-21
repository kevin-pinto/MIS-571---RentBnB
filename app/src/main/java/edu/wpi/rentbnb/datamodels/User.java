package edu.wpi.rentbnb.datamodels;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nikitalondhe on 11/16/16. <br/>
 * <br/>
 * This class consists a list of all the details pertaining to the user. Here,
 * the user_type attribute defines the type of the student/user who could either
 * be a Tenant or a Leaser. These details are captured while registering a user
 * on the application for the first time.
 */
public class User implements Serializable {
	/**
	 * Uniquely identifies the user
	 */
	private Integer id;
	/**
	 * Indicates the first name of the user
	 */
	private String firstName;
	/**
	 * Indicates the last name of the user
	 */
	private String lastName;
	/**
	 * Indicates the gender of the user
	 */
	private Gender gender;
	/**
	 * Indicates the date of birth of the user
	 */
	private Date dob;
	/**
	 * Indicates whether the user of type: Leaser 'L' or Tenant 'T'
	 */
	private UserType type;
	/**
	 * Indicates the phone number of the user
	 */
	private String phoneNumber;
	/**
	 * Indicates the email id of the user
	 */
	private String email;
	/**
	 * Indicates the date of creation of user
	 */
	private Date creationDate;
	/**
	 * Indicates the user api key
	 */
	private String apiKey;

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
	 * Fetches the firstName
	 *
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Fetches the lastName
	 *
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Fetches the gender
	 *
	 * @return gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Fetches the dob
	 *
	 * @return dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the dob
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Fetches the type
	 *
	 * @return type
	 */
	public UserType getType() {
		return type;
	}

	/**
	 * Sets the type
	 */
	public void setType(UserType type) {
		this.type = type;
	}

	/**
	 * Fetches the phoneNumber
	 *
	 * @return phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Fetches the email
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * Fetches the apiKey
	 *
	 * @return apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * Sets the apiKey
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", gender=" + gender + ", dob=" + dob + ", type=" + type + ", phoneNumber=" + phoneNumber
				+ ", email='" + email + '\'' + ", creationDate=" + creationDate + ", apiKey='" + apiKey + '\'' + '}';
	}
}
