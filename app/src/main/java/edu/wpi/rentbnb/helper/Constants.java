package edu.wpi.rentbnb.helper;

/**
 * Created by Kevin on 11-11-2016.
 */
public class Constants {
	//DATABASE
	public static String DATABASE_IS_LOADED = "DATABASE_IS_LOADED";
	public static int DATABASE_VERSION = 1;
	public static String DATABASE_PATH = "/data/data/edu.wpi.rentbnb/databases";
	public static String DATABASE_NAME = "rentbnb_database_1.0.0.db";
	public static String DATABASE_COMPLETE_LOC = DATABASE_PATH + "/" + DATABASE_NAME;
	//GENERIC
	public static Double WPI_MAP_LAT = 42.274229;
	public static Double WPI_MAP_LONG = -71.808022;
	//ACTIVITY
	public static final String IS_LOGGEDIN = "IS_LOGGEDIN";
	public static final String IS_FIRST_TIME = "IS_FIRST_TIME";
	public static final String EXTRA_LOGIN = "EXTRA_LOGIN";
	public static final String EXTRA_USER = "EXTRA_USER";
	public static final String EXTRA_RENTALSPACE = "EXTRA_RENTALSPACE";
	public static final String EXTRA_USER_ID = "EXTRA_USER_ID";
	public static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";
	public static final String EXTRA_RENTALSPACE_DTO = "EXTRA_RENTALSPACE_DTO";
	public static final String EXTRA_RENTALSPACE_ID = "EXTRA_RENTALSPACE_ID";
	public static final String EXTRA_RENTALSPACE_REVIEWS = "EXTRA_RENTALSPACE_REVIEWS";
	public static final String EXTRA_RENTALSPACE_IS_FAV = "EXTRA_RENTALSPACE_IS_FAV";
	public static final String EXTRA_RENTALSPACE_IMGS = "EXTRA_RENTALSPACE_IMGS";
}
