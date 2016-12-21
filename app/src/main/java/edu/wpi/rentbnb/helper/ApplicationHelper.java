package edu.wpi.rentbnb.helper;

import static android.content.Context.MODE_PRIVATE;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.activities.LoginActivity;
import edu.wpi.rentbnb.datamodels.Apartment;
import edu.wpi.rentbnb.datamodels.Location;
import edu.wpi.rentbnb.datamodels.Photo;
import edu.wpi.rentbnb.datamodels.RentalListingDetailsDTO;
import edu.wpi.rentbnb.datamodels.RentalSpace;
import edu.wpi.rentbnb.datamodels.Review;
import edu.wpi.rentbnb.datamodels.Tag;

/**
 * Created by Kevin on 08-12-2016.
 */
public class ApplicationHelper {
	private static ApplicationHelper applicationHelper;

	private ApplicationHelper() {
	}

	public static ApplicationHelper getInstance() {
		if (applicationHelper == null) {
			synchronized (ApplicationHelper.class) {
				if (applicationHelper == null) {
					applicationHelper = new ApplicationHelper();
				}
			}
		}
		return applicationHelper;
	}

	public ArrayList<Tag> fetchDummyTags() {
		ArrayList<Tag> dto = new ArrayList<Tag>();
		dto.add(new Tag(1, "No Smoking", "Some random shit"));
		dto.add(new Tag(2, "Utilities Included", "Some random shit"));
		dto.add(new Tag(3, "No Pets", "Some random shit"));
		dto.add(new Tag(4, "Furnished", "Some random shit"));
		dto.add(new Tag(5, "Has Parking", "Some random shit"));
		dto.add(new Tag(6, "Laundry Available", "Some random shit"));
		dto.add(new Tag(7, "Pets Allowed", "Some random shit"));
		return dto;
	}

	public ArrayList<Review> fetchDummyReviews() {
		String TAG = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin placerat placerat dolor, eu faucibus nisl porttitor pulvinar. Phasellus eu justo tortor. Nulla venenatis est ut neque sagittis, id tincidunt arcu gravida. Donec congue suscipit mi, in imperdiet arcu varius at. Vestibulum cursus mattis risus euismod volutpat. Aenean ut felis quam. Etiam ex tellus, cursus tempor nisi sed, dignissim ornare augue. Etiam ullamcorper at lectus elementum pharetra. Praesent fringilla arcu ac leo porta malesuada.";
		ArrayList<Review> reviewsList = new ArrayList<Review>();
		reviewsList.add(new Review(TAG, new Date(), 5, "Kevin Pinto", false));
		reviewsList.add(new Review(TAG, new Date(), 2, "Nikita Pinto", true));
		reviewsList.add(new Review(TAG, new Date(), 4, "Shia Pinto", false));
		reviewsList.add(new Review(TAG, new Date(), 1, "Jagan Pinto", true));
		reviewsList.add(new Review(TAG, new Date(), 3, "Maanaas Pinto", true));
		return reviewsList;
	}

	public ArrayList<Location> getDummyLocation() {
		ArrayList<Location> locationList = new ArrayList<>();
		locationList.add(new Location(1, "Highland Street", 42.271094, -71.808600));
		locationList.add(new Location(2, "Pratt Street", 42.282792, -71.809768));
		locationList.add(new Location(3, "Park Avenue", 42.273720, -71.813543));
		locationList.add(new Location(4, "William Street", 42.267789, -71.814679));
		return locationList;
	}

	public ArrayList<Photo> getRandomPhotos(Context context) {
		ArrayList<Photo> photoList = new ArrayList<>();
		photoList.add(new Photo(1, 1, BitmapFactory.decodeResource(context.getResources(), R.mipmap.house_one_one), "One"));
		photoList.add(new Photo(2, 1, BitmapFactory.decodeResource(context.getResources(), R.mipmap.house_one_two), "One"));
		photoList.add(new Photo(3, 1, BitmapFactory.decodeResource(context.getResources(), R.mipmap.house_one_three), "One"));
		photoList.add(new Photo(4, 2, BitmapFactory.decodeResource(context.getResources(), R.mipmap.house_two_one), "two"));
		photoList.add(new Photo(5, 2, BitmapFactory.decodeResource(context.getResources(), R.mipmap.house_two_two), "two"));
		photoList.add(new Photo(6, 3, BitmapFactory.decodeResource(context.getResources(), R.mipmap.house_three), "three"));
		return photoList;
	}

	public ArrayList<RentalListingDetailsDTO> fetchDummyListings(Context context) {
		ArrayList<RentalListingDetailsDTO> dtoList = new ArrayList<RentalListingDetailsDTO>();
		/** ONE **/
		RentalListingDetailsDTO dtoOne = new RentalListingDetailsDTO();
		ArrayList photos = getRandomPhotos(context);
		photos.remove(3);
		photos.remove(4);
		//		photos.remove(5);
		//----
		Apartment apartment = new Apartment();
		apartment.setNumber(3);
		apartment.setTotalRooms(2);
		RentalSpace rentalSpace = new RentalSpace();
		rentalSpace.setDescription(
				"Located in the heart of downtown Worcester next to Union Station we are centrally located to all colleges, restaurants and all the nightlife that Worcester has to offer. We offer 1, 2 and 4 bedroom apartments with a private bathroom in each bedroom. Perfect for roommates!");
		rentalSpace.setPrice(975);
		rentalSpace.setAvailableFrom(new Date(1480568400L));
		rentalSpace.setAvailableTo(new Date(1481086800L));
		rentalSpace.setAptId(5);
		rentalSpace.setUtilitiesIncluded(false);
		//----
		dtoOne.setRentalSpaceDetails(rentalSpace);
		dtoOne.setLandlordNumber("7747012207");
		dtoOne.setTags(fetchDummyTags());
		dtoOne.setReviews(fetchDummyReviews());
		dtoOne.setLocation(getDummyLocation().get(1));
		dtoOne.setApartmentDetails(apartment);
		dtoOne.setPhotos(photos);
		dtoList.add(dtoOne);
		/** TWO **/
		RentalListingDetailsDTO dtoTwo = new RentalListingDetailsDTO();
		Apartment apart = new Apartment();
		apart.setNumber(7);
		apart.setTotalRooms(3);
		ArrayList photo = getRandomPhotos(context);
		photos.remove(0);
		photos.remove(1);
		//		photos.remove(2);
		//		photos.remove(5);
		//----
		RentalSpace rs = new RentalSpace();
		rs.setDescription(
				"Located in the heart of downtown Worcester next to Union Station we are centrally located to all colleges, restaurants and all the nightlife that Worcester has to offer. We offer 1, 2 and 4 bedroom apartments with a private bathroom in each bedroom. Perfect for roommates!");
		rs.setPrice(1200);
		rs.setAvailableFrom(new Date(1480568400L));
		rs.setAvailableTo(new Date(1481086800L));
		rs.setAptId(2);
		rs.setUtilitiesIncluded(false);
		//----
		dtoTwo.setLandlordNumber("7745021241");
		dtoTwo.setTags(fetchDummyTags());
		dtoTwo.setReviews(fetchDummyReviews());
		dtoTwo.setLocation(getDummyLocation().get(2));
		dtoTwo.setPhotos(photo);
		dtoTwo.setRentalSpaceDetails(rs);
		dtoTwo.setApartmentDetails(apart);
		dtoList.add(dtoTwo);
		return dtoList;
	}

	public void logout(Activity activity) {
		activity.getSharedPreferences(Constants.EXTRA_LOGIN, MODE_PRIVATE).edit().putBoolean(Constants.IS_LOGGEDIN, false).commit();
		Intent intent = new Intent(activity, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		activity.startActivity(intent);
		activity.finish();
	}

	public double fetchDistanceFromCampus(Location location) {
		double distance = 0.00;
		if (location != null && location.getLatitude() != 0.00 && location.getLongitude() != 0.00) {
			android.location.Location startPoint = new android.location.Location("campus");
			startPoint.setLatitude(Constants.WPI_MAP_LAT);
			startPoint.setLongitude(Constants.WPI_MAP_LONG);
			android.location.Location endPoint = new android.location.Location("apartment");
			endPoint.setLatitude(location.getLatitude());
			endPoint.setLongitude(location.getLongitude());
			distance = startPoint.distanceTo(endPoint);
			distance = distance * 0.000621371;
			DecimalFormat decimalFormat = new DecimalFormat(".##");
			String formattedDecimal = decimalFormat.format(distance);
			return Double.valueOf(formattedDecimal);
		}
		return distance;
	}

	public HashMap<String, String> fetchMyDetails(Activity activity) {
		HashMap<String, String> userData = new HashMap<>();
		String id = String.valueOf(activity.getSharedPreferences(Constants.EXTRA_USER, MODE_PRIVATE).getInt(Constants.EXTRA_USER_ID, -1));
		String name = activity.getSharedPreferences(Constants.EXTRA_USER, MODE_PRIVATE).getString(Constants.EXTRA_USER_NAME, "Anonymous");
		userData.put("id", id);
		userData.put("name", name);
		return userData;
	}
}
