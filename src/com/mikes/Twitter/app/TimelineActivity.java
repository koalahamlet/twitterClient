package com.mikes.Twitter.app;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.mikes.Twitter.app.fragments.HomeTimelineFragment;
import com.mikes.Twitter.app.fragments.MentionsFragment;
import com.mikes.Twitter.app.fragments.TweetsListFragment;
import com.mikes.Twitter.app.models.Tweet;

import eu.erikw.PullToRefreshListView;

public class TimelineActivity extends FragmentActivity implements TabListener {

	PullToRefreshListView lvTweets;
	TweetsAdapter adapter;
	List<Tweet> tweets;
	TweetsListFragment fragmentTweets;
	
	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Tab tabHome = actionBar.newTab().setText("Home").setTag("HomeTimelineFragment")
				.setIcon(R.drawable.home_icon).setTabListener(this);
		
		Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsFragment")
				.setIcon(R.drawable.at_icon).setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		
		actionBar.selectTab(tabHome);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
//		 fragmentTweets = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);

		
		
		if (!isInternetAvailable(this)) {
//			tweets = Tweet.getAll();
//			adapter = new TweetsAdapter(getBaseContext(), tweets);
//			lvTweets.setAdapter(adapter);

		} else {
			fetchTimelineAsync(0);
		}

//		lvTweets.setOnRefreshListener(new OnRefreshListener() {
//
//			@Override
//			public void onRefresh() {
//				fetchTimelineAsync(0);
//			}
//		});
		
			
//			
	}

	

	protected void fetchTimelineAsync(int i) {
		 setProgressBarIndeterminateVisibility(Boolean.TRUE); 
		 
		 setProgressBarIndeterminateVisibility(Boolean.FALSE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
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
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_compose:
			Intent i = new Intent(this, ComposeTweetActivity.class);
			startActivityForResult(i, 7);
			break;
		default:
			break;
		}
		return true;

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		
		if (tab.getTag() == "HomeTimelineFragment"){
			fts.replace(R.id.frameContainer, new HomeTimelineFragment());
		}else{
			fts.replace(R.id.frameContainer, new MentionsFragment());
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
