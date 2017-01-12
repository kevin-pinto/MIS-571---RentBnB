package edu.wpi.rentbnb.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import edu.wpi.rentbnb.datamodels.Apartment;
import edu.wpi.rentbnb.datamodels.Location;
import edu.wpi.rentbnb.datamodels.RentalListingDetailsDTO;
import edu.wpi.rentbnb.datamodels.RentalSpace;
import edu.wpi.rentbnb.datamodels.Review;
import edu.wpi.rentbnb.datamodels.Tag;
import edu.wpi.rentbnb.datamodels.User;
import edu.wpi.rentbnb.datamodels.WishList;
import edu.wpi.rentbnb.helper.Constants;

/**
 * Created by Kevin on 11-12-2016.
 */
public class Database {
	private static Database instance = null;
	private SQLiteDatabase db;
	private String AND = " AND ";

	private Database() {
		String path = Constants.DATABASE_PATH + "/" + Constants.DATABASE_NAME;
		db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
	}

	/*
	 * Singleton Patter	n Why should we avoid multiple instances here?
	 */
	public static Database getInstance() {
		if (instance == null)
			instance = new Database();
		return instance;
	}

	/**
	 * Copy database file From assets folder (in the project) to android folder
	 * (on device)
	 */
	public static void copyDB(Context context) throws IOException, FileNotFoundException {
		String path = Constants.DATABASE_COMPLETE_LOC;
		File file = new File(path);
		if (!file.exists()) {
			DBOpenHelper dbhelper = new DBOpenHelper(context, path, 1);
			dbhelper.getWritableDatabase();
			InputStream is = context.getAssets().open(Constants.DATABASE_NAME);
			OutputStream os = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			is.close();
			os.flush();
			os.close();
		}
	}

	/**
	 * execute sql without returning data, such as alter
	 *
	 * @param sql
	 */
	public void execSQL(String sql) throws SQLException {
		db.execSQL(sql);
	}

	/**
	 * execute sql such as update/delete/insert
	 *
	 * @param sql
	 * @param args
	 * @throws SQLException
	 */
	public void execSQL(String sql, Object[] args) throws SQLException {
		db.execSQL(sql, args);
	}

	/**
	 * execute sql query
	 *
	 * @param sql
	 * @param selectionArgs
	 * @return cursor
	 * @throws SQLException
	 */
	public Cursor execQuery(String sql, String[] selectionArgs) throws SQLException {
		return db.rawQuery(sql, selectionArgs);
	}

	/**
	 * execute query without arguments
	 *
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Cursor execQuery(String sql) throws SQLException {
		return this.execQuery(sql, null);
	}

	/**
	 * close database
	 */
	public void closeDB() {
		if (db != null)
			db.close();
	}

	public long insertUserData(User user, String password) {
		ContentValues cv = new ContentValues();
		cv.put("USER_ID", user.getId());
		cv.put("USER_FIRST_NAME", user.getFirstName());
		cv.put("USER_LAST_NAME", user.getLastName());
		cv.put("USER_GENDER", user.getGender().getGender());
		cv.put("USER_DOB", user.getDob().toString());
		cv.put("USER_TYPE", user.getType().toString());
		cv.put("USER_PH_NUMBER", user.getPhoneNumber());
		cv.put("USER_EMAIL", user.getEmail());
		cv.put("USER_PASS", password);
		cv.put("USER_CREATION_DATE", user.getCreationDate().toString());
		cv.put("USER_API_KEY", user.getApiKey());
		return db.insert("USER", null, cv);
	}

	public ArrayList<Tag> fetchTags(int aptId) {
		ArrayList<Tag> tagsList = new ArrayList<>();
		if (aptId != 0) {
			String[] columns = new String[] { String.valueOf(aptId) };
			Cursor cTagId = db.rawQuery("SELECT TAG_ID FROM apartmenttags where APT_ID = ?", columns);
			ArrayList<Integer> tagIds = new ArrayList<>();
			while (cTagId.moveToNext()) {
				tagIds.add(cTagId.getInt(0));
			}
			cTagId.close();
			Cursor cTags = null;
			for (Integer id : tagIds) {
				String[] tagId = new String[] { String.valueOf(id) };
				cTags = db.rawQuery("SELECT * FROM tag where TAG_ID = ?", tagId);
				while (cTags.moveToNext()) {
					tagsList.add(fetchTag(cTags));
				}
			}
			cTags.close();
		} else {
			Cursor c = db.rawQuery("SELECT * FROM tag", new String[] {});
			while (c.moveToNext()) {
				tagsList.add(fetchTag(c));
			}
			c.close();
		}
		return tagsList;
	}

	private Tag fetchTag(Cursor c) {
		Tag tag = new Tag();
		tag.setId(c.getInt(c.getColumnIndex("TAG_ID")));
		tag.setName(c.getString(c.getColumnIndex("TAG_NAME")));
		tag.setDescription(c.getString(c.getColumnIndex("TAG_DESC")));
		return tag;
	}

	public RentalListingDetailsDTO fetchRentalListing(int rsId) {
		String[] columns = new String[] { String.valueOf(rsId) };
		Cursor c = db.rawQuery("SELECT * FROM rentalspace where RS_ID = ?", columns);
		ArrayList<RentalListingDetailsDTO> dtosList = fetchRentalListings(c);
		c.close();
		return (dtosList == null || dtosList.isEmpty() ? new RentalListingDetailsDTO() : dtosList.get(0));
	}

	public ArrayList<RentalListingDetailsDTO> filterRentalListing(ArrayList<Integer> tagIds, Date fromDate, Date toDate, int fromPrice, int toPrice) {
		List<String> arg = new ArrayList<>();
		int count = 0;
		boolean isDateAvailable = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer query = new StringBuffer("SELECT * FROM rentalspace WHERE ");
		if (fromDate != null && toDate != null) {// ****** date range ********
			query.append("(RS_AVAILABLE_FROM >= ?").append(AND).append("RS_AVAILABLE_TO <= ?)").append(AND);
			arg.add(sdf.format(fromDate));
			arg.add(sdf.format(toDate));
			isDateAvailable = true;
		} else if (fromDate != null && toDate == null) {
			query.append("RS_AVAILABLE_FROM >= ?").append(AND);
			arg.add(sdf.format(fromDate));
			isDateAvailable = true;
		} else if (fromDate == null && toDate != null) {
			query.append("RS_AVAILABLE_TO <= ?").append(AND);
			arg.add(sdf.format(toDate));
			isDateAvailable = true;
		}
		if (fromPrice != 0 && toPrice != 0) {//****** price range ********
			query.append("(RS_PRICE BETWEEN ? AND ?)");
			arg.add(String.valueOf(fromPrice));
			arg.add(String.valueOf(toPrice));
		} else if (fromPrice != 0 && toDate == null) {
			query.append("RS_PRICE >= ?");
			arg.add(String.valueOf(fromPrice));
		} else if (fromDate == null && toDate != null) {
			query.append("RS_PRICE <= ?");
			arg.add(String.valueOf(toPrice));
		}
		if (isDateAvailable) {
			query.append(" ORDER BY RS_AVAILABLE_FROM ASC");
		} else {
			query.append(" ORDER BY RS_PRICE ASC");
		}
		String[] col = arg.toArray(new String[arg.size()]);
		Cursor c = db.rawQuery(query.toString(), col);
		Log.d("FILTER QUERY", c.toString());
		ArrayList<RentalListingDetailsDTO> dtosList = fetchRentalListings(c);
		c.close();
		return (dtosList != null || !dtosList.isEmpty()) ? dtosList : new ArrayList<RentalListingDetailsDTO>();
	}

	public ArrayList<RentalListingDetailsDTO> fetchRentalListings(Cursor cursor) {
		ArrayList<RentalListingDetailsDTO> listingDTOList = new ArrayList<>();
		Cursor c = cursor;
		if (c == null) {
			c = db.rawQuery("SELECT * FROM rentalspace", new String[] {});
		}
		while (c.moveToNext()) {
			RentalListingDetailsDTO detailsDTO = new RentalListingDetailsDTO();
			RentalSpace rentalSpace = new RentalSpace();
			rentalSpace.setId(c.getInt(c.getColumnIndex("RS_ID")));
			rentalSpace.setAptId(c.getInt(c.getColumnIndex("APT_ID")));
			rentalSpace.setLandlordId(c.getInt(c.getColumnIndex("LANDLORD_ID")));
			rentalSpace.setPrice(c.getInt(c.getColumnIndex("RS_PRICE")));
			rentalSpace.setDescription(c.getString(c.getColumnIndex("RS_DESC")));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				rentalSpace.setAvailableFrom(sdf.parse(c.getString(c.getColumnIndex("RS_AVAILABLE_FROM"))));
				rentalSpace.setAvailableTo(sdf.parse(c.getString(c.getColumnIndex("RS_AVAILABLE_TO"))));
			} catch (ParseException px) {
				Log.e("ParseException", px.getMessage());
			}
			rentalSpace.setRented((c.getString(c.getColumnIndex("RS_IS_RENTED"))).equals("N") ? false : true);
			rentalSpace.setVerified((c.getString(c.getColumnIndex("RS_IS_VERIFIED"))).equals("N") ? false : true);
			rentalSpace.setUtilitiesIncluded((c.getString(c.getColumnIndex("RS_IS_UTILITIES_INCLUDED"))).equals("N") ? false : true);
			detailsDTO.setId(rentalSpace.getId());
			detailsDTO.setRentalSpaceDetails(rentalSpace);
			detailsDTO.setApartmentDetails(fetchApartmentDetails(rentalSpace.getAptId()).get(0));
			detailsDTO.setReviews(fetchReviewsForRentalSpace(rentalSpace.getId()));
			detailsDTO.setLocation(fetchLocation(detailsDTO.getApartmentDetails().getLocationId()));
			detailsDTO.setTags(fetchTags(rentalSpace.getAptId()));
			detailsDTO.setLandlordNumber(getUserDetails(rentalSpace.getLandlordId()).get("NUMBER"));
			listingDTOList.add(detailsDTO);
		}
		c.close();
		return listingDTOList;
	}

	public ArrayList<Apartment> fetchApartmentDetails(int aptId) {
		ArrayList<Apartment> aptList = new ArrayList<>();
		Cursor c;
		if (aptId == 0) {
			c = db.rawQuery("SELECT * FROM apartment", new String[] {});
		} else {
			String[] columns = new String[] { String.valueOf(aptId) };
			c = db.rawQuery("SELECT * FROM apartment where APT_ID = ?", columns);
		}
		while (c.moveToNext()) {
			Apartment apartment = new Apartment();
			apartment.setId(c.getInt(c.getColumnIndex("APT_ID")));
			apartment.setLocationId(c.getInt(c.getColumnIndex("LOC_ID")));
			apartment.setNumber(c.getInt(c.getColumnIndex("APT_NO")));
			apartment.setDescription(c.getString(c.getColumnIndex("APT_DESC")));
			apartment.setTotalRooms(c.getInt(c.getColumnIndex("APT_TOTAL_ROOMS")));
			apartment.setVerified((c.getString(c.getColumnIndex("APT_IS_VERIFIED"))).equals("N") ? false : true);
			aptList.add(apartment);
		}
		c.close();
		return aptList;
	}

	public ArrayList<Review> fetchReviewsForRentalSpace(int rsId) {
		ArrayList<Review> reviewList = new ArrayList<>();
		String[] id = new String[] { String.valueOf(rsId) };
		Cursor c = db.rawQuery("SELECT * FROM review where RS_ID = ?", id);
		while (c.moveToNext()) {
			Review review = new Review();
			review.setId(c.getInt(c.getColumnIndex("RV_ID")));
			review.setRentalId(rsId);
			review.setUserId(c.getInt(c.getColumnIndex("USER_ID")));
			review.setUserName(getUserDetails(review.getUserId()).get("NAME"));
			review.setComments(c.getString(c.getColumnIndex("RV_COMMENTS")));
			review.setRating(c.getInt(c.getColumnIndex("RV_RATING")));
			review.setWouldRecommend((c.getString(c.getColumnIndex("RV_WOULD_REC"))).equals("N") ? false : true);
			String creationDate = c.getString(c.getColumnIndex("RC_CREATION_DATE")).substring(0, 10);
			try {
				review.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").parse(creationDate));
			} catch (ParseException px) {
				Log.e("ParseException", px.getMessage());
			}
			reviewList.add(review);
		}
		c.close();
		return reviewList;
	}

	private HashMap<String, String> getUserDetails(int userId) {
		HashMap<String, String> userDetails = new HashMap<>();
		userDetails.put("NAME", "Anonymous");
		String[] id = new String[] { String.valueOf(userId) };
		Cursor c = db.rawQuery("SELECT * FROM user where USER_ID = ?", id);
		if (c.getCount() == 1) {
			c.moveToNext();
			String userName = c.getString(c.getColumnIndex("USER_FIRST_NAME"));
			userName = userName.concat(" ").concat(c.getString(c.getColumnIndex("USER_LAST_NAME")));
			userDetails.put("NAME", userName);
			userDetails.put("NUMBER", c.getString(c.getColumnIndex("USER_PH_NUMBER")));
		}
		c.close();
		return userDetails;
	}

	private Location fetchLocation(int locId) {
		Location location = new Location();
		String[] id = new String[] { String.valueOf(locId) };
		Cursor c = db.rawQuery("SELECT * FROM location where LOC_ID = ?", id);
		if (c.getCount() == 1) {
			c.moveToNext();
			location.setId(locId);
			location.setStreet(c.getString(c.getColumnIndex("LOC_STREET")));
			location.setLatitude(c.getDouble(c.getColumnIndex("LOC_LATITUDE")));
			location.setLongitude(c.getDouble(c.getColumnIndex("LOC_LONGITUDE")));
		}
		c.close();
		return location;
	}

	public ArrayList<Integer> fetchRentalsFavouritedByUser(int userId) {
		ArrayList<Integer> favouriteRentalsList = new ArrayList<>();
		String[] id = new String[] { String.valueOf(userId) };
		Cursor c = db.rawQuery("SELECT RS_ID FROM wishlist where USER_ID = ?", id);
		while (c.moveToNext()) {
			favouriteRentalsList.add(c.getInt(0));
		}
		c.close();
		return favouriteRentalsList;
	}

	public ArrayList<WishList> fetchUsersWishlist(int userId) {
		ArrayList<WishList> favouriteRentalsList = new ArrayList<>();
		String[] id = new String[] { String.valueOf(userId) };
		Cursor c = db.rawQuery("SELECT * FROM wishlist where USER_ID = ?", id);
		while (c.moveToNext()) {
			WishList wish = new WishList();
			wish.setId(c.getInt(c.getColumnIndex("WSLT_ID")));
			wish.setUserId(userId);
			wish.setRentalId(c.getInt(c.getColumnIndex("RS_ID")));
			String creationDate = c.getString(c.getColumnIndex("WSLT_CREATION_DATE")).substring(0, 10);
			try {
				wish.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").parse(creationDate));
			} catch (ParseException px) {
				Log.e("ParseException", px.getMessage());
			}
			wish.setComments(c.getString(c.getColumnIndex("WSLT_COMMENTS")));
			favouriteRentalsList.add(wish);
		}
		c.close();
		return favouriteRentalsList;
	}

	public long saveReviewToDatabase(Review review) {
		ContentValues cv = new ContentValues();
		cv.put("RS_ID", review.getRentalId());
		cv.put("USER_ID", review.getUserId());
		cv.put("RV_COMMENTS", review.getComments());
		cv.put("RV_RATING", review.getRating());
		cv.put("RV_WOULD_REC", review.isWouldRecommend() ? "Y" : "N");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cv.put("RC_CREATION_DATE", sdf.format(new Date()));
		if (review.getId() == null) {
			return db.insert("REVIEW", null, cv);
		} else {
			return db.update("REVIEW", cv, "RV_ID" + "=" + review.getId(), null);
		}
	}
}
