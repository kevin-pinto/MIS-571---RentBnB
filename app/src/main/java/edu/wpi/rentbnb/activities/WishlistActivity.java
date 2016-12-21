package edu.wpi.rentbnb.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.adapter.WishListAdapter;
import edu.wpi.rentbnb.datamodels.RentalListingDetailsDTO;
import edu.wpi.rentbnb.datamodels.WishList;
import edu.wpi.rentbnb.helper.ApplicationHelper;
import edu.wpi.rentbnb.helper.Constants;
import edu.wpi.rentbnb.tools.Database;

public class WishlistActivity extends AppCompatActivity {
	private ArrayList<RentalListingDetailsDTO> listingDTO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wishlist);
		populateWishlistAdapter();
	}

	private void populateWishlistAdapter() {
		ListView listview = (ListView) findViewById(R.id.lvListing);
		ArrayList<WishList> wishList = Database.getInstance().fetchUsersWishlist(Integer.valueOf(ApplicationHelper.getInstance().fetchMyDetails(this).get("id")));
		HashMap<Integer, WishList> wishMap = new HashMap<>();
		for (WishList wish : wishList) {
			wishMap.put(wish.getRentalId(), wish);
		}
		listingDTO = new ArrayList<>();
		ArrayList<Integer> rentalIds = Database.getInstance().fetchRentalsFavouritedByUser(Integer.valueOf(ApplicationHelper.getInstance().fetchMyDetails(this).get("id")));
		for (Integer id : rentalIds) {
			listingDTO.add(Database.getInstance().fetchRentalListing(id));
		}
		WishListAdapter adapter = new WishListAdapter(this, listingDTO, wishMap);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(WishlistActivity.this, RentalListingDetailActivity.class);
				intent.putExtra(Constants.EXTRA_RENTALSPACE_DTO, listingDTO.get(i));
				intent.putExtra(Constants.EXTRA_RENTALSPACE_IS_FAV, true);
				startActivity(intent);
			}
		});
	}
}
