package com.mikes.Twitter.app;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.fragments.UserTimelineFragment;
import com.mikes.Twitter.app.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	TextView tvName;
	TextView tvTagline;
	TextView tvTweets;
	TextView tvFollowers;
	TextView tvFollowing;
	String screenName;
	ImageView ivProfileImage;
	ImageView ivProfileBackgroundImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_profile);

		screenName = (String) getIntent().getStringExtra("userinfo");

		showProgressBar();
		MyTwitterApp.getRestClient().getOtherUsersInfo(screenName,
				new JsonHttpResponseHandler() {
			
				
					@Override
					public void onSuccess(int arg0, JSONObject json) {
						
						hideProgressBar();
						User u = User.fromJson(json);
						getActionBar().setTitle("@" + u.getScreenName());

						populateProfileHeader(u);
						super.onSuccess(arg0, json);
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						hideProgressBar();
						Log.d("DEBUG",
								"getOtherUserInfo didn't return good things");
						super.onFailure(arg0, arg1);
					}
				});

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment fragmentTimeline = UserTimelineFragment
				.newInstance(screenName);
		ft.replace(R.id.frameUserTimeline, fragmentTimeline);
		ft.commit();

	}

	private void populateProfileHeader(User user) {
		// TODO Auto-generated method stub
		tvName = (TextView) findViewById(R.id.tvName);
		tvTagline = (TextView) findViewById(R.id.tvTagline);
		tvTweets = (TextView) findViewById(R.id.tvTweets);
		tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		ivProfileBackgroundImage = (ImageView) findViewById(R.id.ivProfileBackgroundImage);

		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		Integer number = user.getNumTweets();
		tvTweets.setText(formatNumber(number));

		tvFollowers.setText(formatNumber(user.getFollowersCount()));
		tvFollowing.setText(formatNumber(user.getFriendsCount()));
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(),
				ivProfileImage);
		ImageLoader.getInstance().displayImage(
				user.getProfileBackgroundImageUrl(), ivProfileBackgroundImage);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.profile, menu);
	// return true;
	// }

	public String formatNumber(Integer number) {
		String numberString = number.toString();
		if (number > 9999) {
			number = number / 1000;

			numberString = number + "K";
		} else if (number > 999999) {
			number = number / 1000000;

			numberString = number + "M";
		} else {
			// whatevs
		}

		return numberString;

	}

	public void showProgressBar() {
		setProgressBarIndeterminateVisibility(true);
	}

	public void hideProgressBar() {
		setProgressBarIndeterminateVisibility(false);
	}

}
