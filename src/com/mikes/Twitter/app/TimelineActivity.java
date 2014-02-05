package com.mikes.Twitter.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.models.Tweet;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {

	PullToRefreshListView lvTweets;
	TweetsAdapter adapter;
	List<Tweet> tweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_timeline);

		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		
		if (!isInternetAvailable(this)) {
			tweets = Tweet.getAll();
			adapter = new TweetsAdapter(getBaseContext(), tweets);
			lvTweets.setAdapter(adapter);

		} else {
			fetchTimelineAsync(0);
		}

		lvTweets.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				fetchTimelineAsync(0);
			}
		});
		
			
			lvTweets.setOnScrollListener(new EndlessScrollListener() {
	
				@Override
				public void onLoadMore(int page, int totalItemsCount) {
					setProgressBarIndeterminateVisibility(Boolean.TRUE); 
					Tweet lastTweet = (Tweet) lvTweets
							.getItemAtPosition(totalItemsCount - 1);
					// minus one in the next call to get rid of duplicate tweet
					MyTwitterApp.getRestClient().getMoreHomeTimeline(
							lastTweet.getTweetId() - 1,
							new JsonHttpResponseHandler() {
								@Override
								public void onSuccess(JSONArray jsonTweets) {
									setProgressBarIndeterminateVisibility(Boolean.FALSE); 
									Log.d("DEBUG", jsonTweets.toString());
									tweets = Tweet.fromJson(jsonTweets);
									adapter.addAll(tweets);
									adapter.notifyDataSetChanged();
									ActiveAndroid.beginTransaction();
									try {
										for (Tweet tweetInstance : tweets) {
											tweetInstance.getUser().save();
											tweetInstance.save();
										}
										ActiveAndroid.setTransactionSuccessful();
									} finally {
										ActiveAndroid.endTransaction();
									}
	
								}
	
								@Override
								public void onFailure(Throwable arg0, String arg1) {
									showFailMessage();
									setProgressBarIndeterminateVisibility(Boolean.FALSE); 
									super.onFailure(arg0, arg1);
								}
	
								
							});
	
				}
			});

	}

	protected void fetchTimelineAsync(int i) {
		 setProgressBarIndeterminateVisibility(Boolean.TRUE); 
		 
		 MyTwitterApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						setProgressBarIndeterminateVisibility(Boolean.FALSE); 
						Log.d("DEBUG", jsonTweets.toString());
						 tweets = Tweet.fromJson(jsonTweets);
						// for (Tweet tweet : tweets) {
						// Log.d("halper", tweet.getCreatedAt());

						// }
						adapter = new TweetsAdapter(getBaseContext(), tweets);
						lvTweets.setAdapter(adapter);
						lvTweets.onRefreshComplete();
						ActiveAndroid.beginTransaction();
						try{
						for (Tweet tweet : tweets) {
							Log.d("DEBUG", "User: "+ tweet.getUser().toString());
							Log.d("DEBUG", "Tweet: " + tweet.toString());
							tweet.getUser().save();
							tweet.save();
						}
						ActiveAndroid.setTransactionSuccessful();
						} finally {
							ActiveAndroid.endTransaction();
						}
						
						
					}
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						setProgressBarIndeterminateVisibility(Boolean.FALSE); 
						showFailMessage();
						super.onFailure(arg0, arg1);
					}

					

				});
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

	public void showFailMessage() {
		// TODO Auto-generated method stub
		Toast.makeText(TimelineActivity.this, "Whoops, we can't seem to speak to teh netz at the moment. You'll see your tweets again once you've gotten a better connection.", Toast.LENGTH_LONG).show();
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

}
