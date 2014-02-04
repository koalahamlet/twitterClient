package com.mikes.Twitter.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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

import com.activeandroid.query.Select;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.models.Tweet;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {

	PullToRefreshListView lvTweets;
	TweetsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_timeline);

		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);

		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {

				Tweet lastTweet = (Tweet) lvTweets
						.getItemAtPosition(totalItemsCount - 1);
				// minus one in the next call to get rid of duplicate tweet
				MyTwitterApp.getRestClient().getMoreHomeTimeline(
						lastTweet.getTweetId() - 1,
						new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(JSONArray jsonTweets) {
								Log.d("DEBUG", jsonTweets.toString());
								ArrayList<Tweet> tweets = Tweet
										.fromJson(jsonTweets);
								adapter.addAll(tweets);
								adapter.notifyDataSetChanged();


							}

							@Override
							public void onFailure(Throwable arg0,
									JSONArray jsonTweets) {

								
								super.onFailure(arg0, jsonTweets);
							}

						});

			}
		});

		lvTweets.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				fetchTimelineAsync(0);
			}
		});

		fetchTimelineAsync(0);

	}

	protected void fetchTimelineAsync(int i) {
		 setProgressBarIndeterminateVisibility(Boolean.TRUE); 
		MyTwitterApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						setProgressBarIndeterminateVisibility(Boolean.FALSE); 
						Log.d("DEBUG", jsonTweets.toString());
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
						// for (Tweet tweet : tweets) {
						// Log.d("halper", tweet.getCreatedAt());

						// }
						adapter = new TweetsAdapter(getBaseContext(), tweets);
						lvTweets.setAdapter(adapter);
						lvTweets.onRefreshComplete();
						for (Tweet tweet : tweets) {
							Log.d("DEBUG", "User: "+ tweet.getUser().toString());
							Log.d("DEBUG", "Tweet: " + tweet.toString());
							tweet.getUser().save();
							tweet.save();
						}
						// super.onSuccess(jsonTweets);
					}
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						setProgressBarIndeterminateVisibility(Boolean.FALSE);
						Log.d("DEBUG", "did I get in here?");
						List<Tweet> tweets = new Select("*")
								.from(Tweet.class)
								.orderBy("uid ASC").execute();
						Log.d("DEBUG", tweets.toString());
						adapter.addAll(tweets);
						adapter.notifyDataSetChanged();
						Log.d("DEBUG", "stringstringstringstring");
						super.onFailure(arg0, arg1);
					}
//					@Override
//					public void onFailure(Throwable arg0) {
//						setProgressBarIndeterminateVisibility(Boolean.FALSE); 
//						Log.d("DEBUG", "watwatwatwatwatwat");
//						super.onFailure(arg0);
//					}
//					@Override
//					public void onFailure(Throwable arg0, JSONObject arg1) {
//						Log.d("DEBUG", "JSONJSONJSONJSONJSONJSON");
//						setProgressBarIndeterminateVisibility(Boolean.FALSE); 
//						super.onFailure(arg0, arg1);
//					}
					@Override
					public void onFailure(Throwable arg0, JSONArray arg1) {

						Log.d("DEBUG", "[][][][][][][][][][][][][]");
						setProgressBarIndeterminateVisibility(Boolean.FALSE); 
						ConnectivityManager cm = (ConnectivityManager) TimelineActivity.this
								.getSystemService(Context.CONNECTIVITY_SERVICE);

//						NetworkInfo activeNetwork = cm
//								.getActiveNetworkInfo();
//						boolean isConnected = activeNetwork != null
//								&& activeNetwork
//										.isConnectedOrConnecting();
//
//						if (!isConnected) {
							

//						}
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
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		fetchTimelineAsync(0);
		super.onActivityResult(requestCode, resultCode, data);
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
