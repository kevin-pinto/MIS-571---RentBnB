package edu.wpi.rentbnb.datamodels;

/**
 * Created by nikitalondhe on 11/16/16.<br/>
 * <br/>
 * This class stores the detailed mapping between tags and the apartments
 * associated with it.
 */
public class ApartmentTags {
	/**
	 * Uniquely identifies the user.
	 */
	private Integer aptId;
	/**
	 * Uniquely identifies the tag associated with the user.
	 */
	private Integer tagId;

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
	 * Fetches the tagId
	 *
	 * @return tagId
	 */
	public Integer getTagId() {
		return tagId;
	}

	/**
	 * Sets the tagId
	 */
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	@Override
	public String toString() {
		return "ApartmentTags{" + "aptId=" + aptId + ", tagId=" + tagId + '}';
	}
}
