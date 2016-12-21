package edu.wpi.rentbnb.datamodels;

import java.io.Serializable;

/**
 * Created by nikitalondhe on 11/16/16.<br/>
 * <br/>
 * This class contains a list of tags that can be associated with a user or the
 * apartment.
 */
public class Tag implements Serializable {
	/**
	 * Uniquely identifies the tag id
	 */
	private Integer id;
	/**
	 * Indicates the tag name
	 */
	private String name;
	/**
	 * Indicates the tag description
	 */
	private String description;

	public Tag() {
	}

	public Tag(Integer id, String name, String description) {
		this.description = description;
		this.id = id;
		this.name = name;
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
	 * Fetches the name
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 */
	public void setName(String name) {
		this.name = name;
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
		return "Tag{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + '}';
	}
}
