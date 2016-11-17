package edu.wpi.rentbnb.datamodels;

/**
 * Created by nikitalondhe on 11/16/16.
 */
public enum UserType {
	LANDLORD("L"), TENANT("T");
	private String type;

	UserType(String type) {
		this.type = type;
	}

	public String getUserType() {
		return type;
	}
}