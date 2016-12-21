package edu.wpi.rentbnb.datamodels;

/**
 * Created by nikitalondhe on 11/16/16.
 */
public enum Gender {
	MALE("M"), FEMALE("F"), OTHER("O");
	private String gender;

	Gender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public Gender fromString(String text) {
		if (text != null) {
			for (Gender b : Gender.values()) {
				if (text.equalsIgnoreCase(gender)) {
					return b;
				}
			}
		}
		return null;
	}
}