package com.mikes.Twitter.app;

import java.util.List;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.fragments.HomeTimelineFragment;
import com.mikes.Twitter.app.fragments.MentionsFragment;
import com.mikes.Twitter.app.fragments.TweetsListFragment;
import com.mikes.Twitter.app.models.Tweet;
import com.mikes.Twitter.app.models.User;

import eu.erikw.PullToRefreshListView;

public class TimelineActivity extends FragmentActivity implements TabListener {

	PullToRefreshListView lvTweets;
	TweetsAdapter adapter;
	List<Tweet> tweets;
	TweetsListFragment fragmentTweets;
	String myName;
	String myScreenName;
	String profileURL;
	private HomeTimelineFragment htlFrag;
	private MentionsFragment mFrag;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
		// fragmentTweets = (TweetsListFragment)
		// getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);

		if (!isInternetAvailable(this)) {
			// tweets = Tweet.getAll();
			// adapter = new TweetsAdapter(getBaseContext(), tweets);
			// lvTweets.setAdapter(adapter);

		} else {
			fetchTimelineAsync(0);
		}

		// lvTweets.setOnRefreshListener(new OnRefreshListener() {
		//
		// @Override
		// public void onRefresh() {
		// fetchTimelineAsync(0);
		// }
		// });

		//
	}

	
	private void setupNavigationTabs() {
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.setDisplayShowTitleEnabled(true);
		
//		TabHost th1 =(TabHost) this.findViewById(android.R.id.tabhost);
		
		Tab tabHome = actionBar.newTab().setText("Home").setTag("HomeTimelineFragment")
				.setIcon(R.drawable.home_icon).setTabListener(this);
		
//		TextView tv1 = (TextView) th1.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
//		
//		tv1.setTextColor(Color.CYAN);
		
		
		
		Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsFragment")
				.setIcon(R.drawable.at_icon).setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		
		
		actionBar.selectTab(tabHome);
		
		
//		showProgressBar();
		MyTwitterApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONObject json) {
				User u = User.fromJson(json);
				hideProgressBar();
				myName = u.getName();
				myScreenName = u.getScreenName();
				profileURL = u.getProfileImageUrl();
				
				super.onSuccess(arg0, json);
			}
			
			//TODO: put on failure here so they don't crash when tweeting
		});
		
	}


	protected void fetchTimelineAsync(int i) {

	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		fetchTimelineAsync(0);
		super.onActivityResult(requestCode, resultCode, data);
	}

	public static boolean isInternetAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}

	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager
				.beginTransaction();
		//lazy instantiation for the win
		if (tab.getTag() == "HomeTimelineFragment") {
			if (htlFrag == null) {
				htlFrag = new HomeTimelineFragment();
			}
			
			fts.replace(R.id.frameContainer, htlFrag, "HTL");
		} else {
			if (mFrag == null) {
				mFrag = new MentionsFragment();
			}
			fts.replace(R.id.frameContainer, mFrag, "MF");
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_compose:
			Intent i = new Intent(this, ComposeTweetActivity.class);
			i.putExtra("name", myName);
			i.putExtra("screen_name", myScreenName);
			i.putExtra("profile_image", profileURL);
			startActivityForResult(i, 7);
			break;

		case R.id.action_profile:
			Intent j = new Intent(this, ProfileActivity.class);
			j.putExtra("userinfo", myScreenName);			
			startActivity(j);
			break;
		default:
			break;
		}
		return true;

	}
	
	public void showProgressBar() {
		setProgressBarIndeterminateVisibility(true);
	}

	public void hideProgressBar() {
		setProgressBarIndeterminateVisibility(false);
	}
	
}
