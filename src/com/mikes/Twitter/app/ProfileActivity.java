package com.mikes.Twitter.app;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
		setContentView(R.layout.activity_profile);
		
		
		screenName = (String) getIntent().getStringExtra("userinfo");
		

		MyTwitterApp.getRestClient().getOtherUsersInfo(screenName, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONObject json) {
				User u = User.fromJson(json);
				getActionBar().setTitle("@" + u.getScreenName());
				
				populateProfileHeader(u);
				super.onSuccess(arg0, json);
			}
			
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Log.d("DEBUG", "getOtherUserInfo didn't return good things");
				super.onFailure(arg0, arg1);
			}
		});

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment fragmentTimeline = UserTimelineFragment.newInstance(screenName);
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
		tvTweets.setText(number + " tweets");
		tvFollowers.setText(user.getFollowersCount() + " followers");
		tvFollowing.setText(user.getFriendsCount() + " following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
		ImageLoader.getInstance().displayImage(user.getProfileBackgroundImageUrl(), ivProfileBackgroundImage);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.profile, menu);
//		return true;
//	}

}
