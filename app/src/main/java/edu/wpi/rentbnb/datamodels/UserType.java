package edu.wpi.rentbnb.datamodels;
public enum UserType {
	LANDLORD("L"), TENANT("T");
	private String type;

	UserType(String type) {
		this.type = type;
	}

	public String getUserType() {
		return type;
	}

	public UserType fromString(String text) {
		if (text != null) {
			for (UserType b : UserType.values()) {
				if (text.equalsIgnoreCase(type)) {
					return b;
				}
			}
		}
		return null;
	}
}