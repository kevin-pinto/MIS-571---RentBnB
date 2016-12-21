package edu.wpi.rentbnb.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.adapter.SlidingImageAdapter;
import edu.wpi.rentbnb.datamodels.Location;
import edu.wpi.rentbnb.datamodels.Photo;
import edu.wpi.rentbnb.datamodels.RentalListingDetailsDTO;
import edu.wpi.rentbnb.datamodels.Tag;
import edu.wpi.rentbnb.helper.ApplicationHelper;
import edu.wpi.rentbnb.helper.Constants;

public class RentalListingDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager pager;
    private CirclePageIndicator indicator;
    private TextView tvDescription, tvAddress, tvAvailability, tvPrice, tvTags, tvUtilities;
    private RentalListingDetailsDTO rentalDetailDTO;
    private SlidingImageAdapter adapter;
    private ArrayList<Photo> images;
    private boolean isFavourite;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rentalDetailDTO = (RentalListingDetailsDTO) getIntent().getSerializableExtra(Constants.EXTRA_RENTALSPACE_DTO);
        images = new ArrayList<>(3);
        images = ApplicationHelper.getInstance().getRandomPhotos(this);
        setTitle(getActionBarTitle());
        setContentView(R.layout.activity_rentalspace_detail);
        init();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listing_detail_menu, menu);
        this.menu = menu;
        if (getIntent().hasExtra(Constants.EXTRA_RENTALSPACE_IS_FAV)) {
            isFavourite = getIntent().getBooleanExtra(Constants.EXTRA_RENTALSPACE_IS_FAV, false);
            int imgId = isFavourite ? R.mipmap.icon_favorite_selected : R.mipmap.icon_favorite_unselected;
            menu.getItem(0).setIcon(getResources().getDrawable(imgId));
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wishlist:
                Toast.makeText(RentalListingDetailActivity.this, "Space " + (isFavourite ? "removed from " : "added to ") + "wishlist.", Toast.LENGTH_SHORT).show();
                toggleMenuIcon();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAddressTitle:
                Location location = rentalDetailDTO.getLocation();
                StringBuilder urlBuilder = new StringBuilder("http://maps.google.com/maps?saddr=");
                urlBuilder.append(String.valueOf(location.getLatitude())).append(",").append(String.valueOf(location.getLongitude())).append("&daddr=").append(Constants.WPI_MAP_LAT.toString()).append(",")
                        .append(Constants.WPI_MAP_LONG.toString());
                Intent locIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(urlBuilder.toString()));
                startActivity(locIntent);
                break;
            case R.id.tvSeller:
                showChooseModeOfContact();
                break;
            case R.id.btnReviews:
                Intent intent = new Intent(RentalListingDetailActivity.this, ReviewActivity.class);
                intent.putExtra(Constants.EXTRA_RENTALSPACE_REVIEWS, rentalDetailDTO.getReviews());
                intent.putExtra(Constants.EXTRA_RENTALSPACE_ID, rentalDetailDTO.getId());
                startActivity(intent);
                break;
        }
    }

    private void toggleMenuIcon() {
        isFavourite = !isFavourite;
        int imgId = isFavourite ? R.mipmap.icon_favorite_selected : R.mipmap.icon_favorite_unselected;
        menu.getItem(0).setIcon(getResources().getDrawable(imgId));
    }

    private void showChooseModeOfContact() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Contact Seller");
        alertDialogBuilder.setMessage("Please select the mode through which you would like to contact the seller of this listing.").setCancelable(false)
                .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                        phoneIntent.setData(Uri.parse("tel:" + rentalDetailDTO.getLandlordNumber()));
                        startActivity(phoneIntent);
                        dialog.dismiss();
                    }
                }).setNegativeButton("Message", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", rentalDetailDTO.getLandlordNumber());
                smsIntent.putExtra("sms_body",
                        "Hi, I would like to know more about your apartment listed on " + getActionBarTitle() + " Please let me know of a suitable time when we can speak. \n\nThank you.");
                startActivity(smsIntent);
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void populateViewData() {
        tvDescription.setText(rentalDetailDTO.getRentalSpaceDetails().getDescription());
        tvAvailability.setText(fetchAvailability());
        tvAddress.setText(getCompleteAddress());
        tvPrice.setText("$ " + rentalDetailDTO.getRentalSpaceDetails().getPrice().toString());
        tvTags.setText(getTags());
        tvUtilities.setText(rentalDetailDTO.getRentalSpaceDetails().isUtilitiesIncluded() ? "Included" : "Not Included");
    }

    private void populatePager() {
        ArrayList<Photo> photos = images;
        //		for (int i = 0; i < photos.size(); i++) {
        //			photos.get(i).setData(images.get(i));
        //		}
        if (photos == null || photos.isEmpty()) {
            photos = new ArrayList<>();
            photos.add(new Photo(1, 1, BitmapFactory.decodeResource(getResources(), R.mipmap.default_pager_housing), "Default image"));
        }
        adapter = new SlidingImageAdapter(this, photos);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        indicator.setRadius(5 * getResources().getDisplayMetrics().density);
    }

    private String getTags() {
        StringBuffer tags = new StringBuffer();
        for (Tag tag : rentalDetailDTO.getTags()) {
            tags.append("#").append(tag.getName().replace(" ", "")).append(", ");
        }
        return tags.toString().substring(0, tags.length() - 1);
    }

    private String getActionBarTitle() {
        StringBuffer title = new StringBuffer();
        title.append("Apt #").append(rentalDetailDTO.getApartmentDetails().getNumber().toString());
        title.append(", ").append(rentalDetailDTO.getLocation().getStreet());
        return title.toString();
    }

    private String getCompleteAddress() {
        return getActionBarTitle().concat(", Worcester, Massachusetts - 01609, USA.");
    }

    private String fetchAvailability() {
        StringBuffer availability = new StringBuffer();
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        availability.append(sdf.format(rentalDetailDTO.getRentalSpaceDetails().getAvailableFrom()));
        availability.append(" to ").append(sdf.format(rentalDetailDTO.getRentalSpaceDetails().getAvailableTo()));
        return availability.toString();
    }

    private void init() {
        pager = (ViewPager) findViewById(R.id.pager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAvailability = (TextView) findViewById(R.id.tvAvailability);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvTags = (TextView) findViewById(R.id.tvTags);
        tvUtilities = (TextView) findViewById(R.id.tvUtilities);
        findViewById(R.id.tvAddressTitle).setOnClickListener(this);
        findViewById(R.id.tvSeller).setOnClickListener(this);
        findViewById(R.id.btnReviews).setOnClickListener(this);
        populateViewData();
        populatePager();
    }
}
