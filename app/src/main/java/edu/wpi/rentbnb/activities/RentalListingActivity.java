package edu.wpi.rentbnb.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import edu.wpi.rentbnb.BaseActivity;
import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.adapter.GridTagListingAdapter;
import edu.wpi.rentbnb.adapter.RentalListingAdapter;
import edu.wpi.rentbnb.datamodels.RentalListingDetailsDTO;
import edu.wpi.rentbnb.datamodels.RentalSpace;
import edu.wpi.rentbnb.datamodels.Tag;
import edu.wpi.rentbnb.helper.ApplicationHelper;
import edu.wpi.rentbnb.helper.Constants;
import edu.wpi.rentbnb.tools.Database;

/**
 * Created by Kevin on 29-11-2016.
 */
public class RentalListingActivity extends BaseActivity implements View.OnClickListener {
	private int TO_DATE = 0, FROM_DATE = 1;
	private ViewFlipper vfListings;
	private GoogleMap map;
	private RadioGroup listingType;
	private ListView lvListing;
	//	private LinearLayout loadingview;
	private RentalListingAdapter listingAdapter;
	private View mapItemDetailsView;
	private Calendar calendar = Calendar.getInstance();
	private DatePickerDialog.OnDateSetListener fromPickerListener, toPickerListener;
	private ArrayList<RentalListingDetailsDTO> listingsList;
	/** Filter views **/
	private ImageView ivFilterMain;
	private RelativeLayout filterView;
	private TextView tvFilterPrice, tvFilterFromDate, tvFilterToDate;
	private CrystalRangeSeekbar rsPrice;
	private GridTagListingAdapter tagsAdapter;
	private GridView gridTags;
	private String minPrice, maxPrice;
	/** Map views **/
	private HashMap<String, RentalListingDetailsDTO> tempListingMap;
	private ImageView ivMapPreview;
	private TextView tvMapAddress, tvMapMilesAway, tvMapPrice, tvMapNoOfRooms, tvMapDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rental_list);
		init();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.listing_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.logout:
			ApplicationHelper.getInstance().logout(RentalListingActivity.this);
			return true;
		case R.id.wish_lists:
			navigateToWishListPage();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void navigateToWishListPage() {
		Intent intent = new Intent(this, WishlistActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tvFromDate:
			new DatePickerDialog(this, fromPickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
			break;
		case R.id.tvToDate:
			new DatePickerDialog(this, toPickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
			break;
		case R.id.btnDoFilter:
			performFilterOnList();
			filterView.setVisibility(View.GONE);
			break;
		case R.id.ivFilterMain:
			if (filterView.getVisibility() != View.VISIBLE) {
				ObjectAnimator animX = ObjectAnimator.ofFloat(filterView, View.TRANSLATION_X, 1 * filterView.getWidth(), 0);
				animX.setDuration(1000);
				animX.start();
				filterView.setVisibility(View.VISIBLE);
			} else {
				ObjectAnimator animX = ObjectAnimator.ofFloat(filterView, View.TRANSLATION_X, 0, 1 * filterView.getWidth());
				animX.setDuration(1000);
				animX.start();
				new Handler().postDelayed(new Runnable() {
					public void run() {
						filterView.setVisibility(View.GONE);
					}
				}, 1000);
			}
			break;
		case R.id.detailsRow:
			break;
		}
	}

	private void performFilterOnList() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date fromDate = null, toDate = null;
		try {
			if (!tvFilterFromDate.getText().toString().isEmpty()) {
				fromDate = sdf.parse(tvFilterFromDate.getText().toString());
			}
			if (!tvFilterFromDate.getText().toString().isEmpty()) {
				toDate = sdf.parse(tvFilterToDate.getText().toString());
			}
		} catch (ParseException ex) {
			Log.e("ParseException", ex.toString());
		}
		int minPrice = Integer.valueOf(rsPrice.getSelectedMinValue().toString());
		int maxPrice = Integer.valueOf(rsPrice.getSelectedMaxValue().toString());
		listingsList.clear();
		listingsList = Database.getInstance().filterRentalListing(new ArrayList<Integer>(), fromDate, toDate, minPrice, maxPrice);
		listingAdapter = new RentalListingAdapter(this, listingsList);
		lvListing.setAdapter(listingAdapter);
		clearPreviousFilterConditions();
	}

	private void clearPreviousFilterConditions() {
		rsPrice.invalidate();
		tvFilterFromDate.setText(null);
		tvFilterToDate.setText(null);
	}

	private void setSelectedDate(int type) {
		String myFormat = "MM/dd/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		TextView textView = (type == TO_DATE) ? tvFilterToDate : tvFilterFromDate;
		textView.setText(sdf.format(calendar.getTime()));
	}

	private void setAdapters() {
		listingAdapter = new RentalListingAdapter(this, listingsList);
		lvListing.setAdapter(listingAdapter);
		tagsAdapter = new GridTagListingAdapter(this, fetchTags());
		gridTags.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		gridTags.setAdapter(tagsAdapter);
	}

	private ArrayList<Tag> fetchTags() {
		ArrayList<Tag> tags = Database.getInstance().fetchTags(0);
		if (tags == null || tags.isEmpty()) {
			tags = ApplicationHelper.getInstance().fetchDummyTags();
		}
		return tags;
	}

	private void displayPinsOnMap() {
		MarkerOptions markerOptions;
		LatLng latLng;
		for (RentalListingDetailsDTO detailsDTO : listingsList) {
			latLng = new LatLng(detailsDTO.getLocation().getLatitude(), detailsDTO.getLocation().getLongitude());
			markerOptions = new MarkerOptions();
			markerOptions.title(getItemTitle(detailsDTO));
			markerOptions.position(latLng);
			Marker marker = map.addMarker(markerOptions);
			storeDataToTempMap(marker.getId(), detailsDTO);
		}
		map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker paramMarker) {
				RentalListingDetailsDTO dto = fetchMarkerData(paramMarker.getId());
				dto = (dto != null) ? dto : listingsList.get(0);
				mapItemDetailsView.setTag(dto);
				setSelectedListingDetailsOnView(dto);
				return true;
			}
		});
	}

	private RentalListingDetailsDTO fetchMarkerData(String markerId) {
		if (tempListingMap.containsKey(markerId) && null != tempListingMap.get(markerId)) {
			return tempListingMap.get(markerId);
		}
		return null;
	}

	private void storeDataToTempMap(String id, RentalListingDetailsDTO detailsDTO) {
		if (tempListingMap == null) {
			tempListingMap = new HashMap<>();
		}
		if (tempListingMap != null) {
			tempListingMap.put(id, detailsDTO);
		}
	}

	private String getItemTitle(RentalListingDetailsDTO detailsDTO) {
		StringBuffer title = new StringBuffer();
		title.append("Apt #").append(detailsDTO.getApartmentDetails().getNumber().toString());
		title.append(", ").append(detailsDTO.getLocation().getStreet());
		return title.toString();
	}

	private String getAvailability(RentalSpace rentalSpace) {
		String availability = "";
		availability = availability.concat(new SimpleDateFormat("MM/dd/yyyy").format(rentalSpace.getAvailableFrom())).concat(" to ")
				.concat(new SimpleDateFormat("MM/dd/yyyy").format(rentalSpace.getAvailableTo()));
		return availability;
	}

	private Bitmap getPreview(RentalListingDetailsDTO rentalListingDetail) {
		Bitmap image;
		int randomNum = new Random().nextInt((5 - 0) + 1) + 0;
		image = ApplicationHelper.getInstance().getRandomPhotos(this).get(randomNum).getData();
		if (image == null) {
			image = BitmapFactory.decodeResource(this.getResources(), R.mipmap.default_rental_space);
		}
		return image;
	}

	private void setSelectedListingDetailsOnView(RentalListingDetailsDTO detailsDTO) {
		mapItemDetailsView.setVisibility(View.VISIBLE);
		tvMapAddress.setText(getItemTitle(detailsDTO));
		tvMapNoOfRooms.setText("No of rooms: " + detailsDTO.getApartmentDetails().getTotalRooms().toString());
		tvMapDate.setText(getAvailability(detailsDTO.getRentalSpaceDetails()));
		tvMapMilesAway.setText(String.valueOf(detailsDTO.getLocation().getDistanceFromCampus()) + " miles from campus");
		ivMapPreview.setImageBitmap(getPreview(detailsDTO));
		mapItemDetailsView.setVisibility(View.VISIBLE);
	}

	private void init() {
		listingsList = Database.getInstance().fetchRentalListings(null);
		vfListings = (ViewFlipper) findViewById(R.id.vfListings);
		ivFilterMain = (ImageView) findViewById(R.id.ivFilterMain);
		listingType = (RadioGroup) findViewById(R.id.listingType);
		lvListing = (ListView) findViewById(R.id.lvListing);
		//		loadingview = (LinearLayout) findViewById(R.id.loadingview);
		filterView = (RelativeLayout) findViewById(R.id.filterView);
		tvFilterPrice = (TextView) findViewById(R.id.tvPrice);
		tvFilterFromDate = (TextView) findViewById(R.id.tvFromDate);
		tvFilterToDate = (TextView) findViewById(R.id.tvToDate);
		gridTags = (GridView) findViewById(R.id.gridTags);
		initializeMap();
		displayPinsOnMap();
		setAdapters();
		rsPrice = (CrystalRangeSeekbar) findViewById(R.id.rsPrice);
		tvFilterFromDate.setOnClickListener(this);
		tvFilterToDate.setOnClickListener(this);
		ivFilterMain.setOnClickListener(this);
		findViewById(R.id.btnDoFilter).setOnClickListener(this);
		gridTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
			}
		});
		lvListing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(RentalListingActivity.this, RentalListingDetailActivity.class);
				intent.putExtra(Constants.EXTRA_RENTALSPACE_DTO, listingsList.get(i));
				startActivity(intent);
			}
		});
		rsPrice.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
			@Override
			public void valueChanged(Number minValue, Number maxValue) {
				tvFilterPrice.setText("$" + minValue + " - " + "$" + maxValue);
			}
		});
		toPickerListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				setSelectedDate(TO_DATE);
			}
		};
		fromPickerListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				setSelectedDate(FROM_DATE);
			}
		};
		listingType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbtnList:
					vfListings.setDisplayedChild(0);
					mapItemDetailsView.setVisibility(View.GONE);
					ivFilterMain.setVisibility(View.VISIBLE);
					break;
				case R.id.rbtnMap:
					vfListings.setDisplayedChild(1);
					ivFilterMain.setVisibility(View.GONE);
					break;
				}
			}
		});
	}

	private void initializeMap() {
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mfgListings)).getMap();
		mapItemDetailsView = findViewById(R.id.detailsRow);
		findViewById(R.id.llPrice).setVisibility(View.GONE);
		LatLng latLng = new LatLng(Constants.WPI_MAP_LAT, Constants.WPI_MAP_LONG);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.5f));
		findViewById(R.id.detailsRow).setOnClickListener(this);
		ivMapPreview = (ImageView) findViewById(R.id.ivPreview);
		tvMapAddress = (TextView) findViewById(R.id.tvAddress);
		tvMapMilesAway = (TextView) findViewById(R.id.tvMilesAway);
		tvMapPrice = (TextView) findViewById(R.id.tvPrice);
		tvMapNoOfRooms = (TextView) findViewById(R.id.tvNoOfRooms);
		tvMapDate = (TextView) findViewById(R.id.tvDate);
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng latLng) {
				mapItemDetailsView.setVisibility(View.GONE);
			}
		});
	}
}
