package com.mikes.Twitter.app.fragments;

import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.EndlessScrollListener;
import com.mikes.Twitter.app.MyTwitterApp;
import com.mikes.Twitter.app.R;
import com.mikes.Twitter.app.TweetsAdapter;
import com.mikes.Twitter.app.models.Tweet;

import eu.erikw.PullToRefreshListView;

public class HomeTimelineFragment extends TweetsListFragment {

	TweetsAdapter adapter;
	List<Tweet> tweets;
	TweetsListFragment fragmentTweets;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getMainData();

		

	}

	@Override
	public void getMainData() {
		MyTwitterApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						lvTweets.onRefreshComplete();
						tweets = Tweet.fromJson(jsonTweets);
						getAdapter().addAll(tweets);
						// breaky breaky active android
						// ActiveAndroid.beginTransaction();
						// try{
						// for (Tweet tweet : tweets) {
						// Log.d("DEBUG", "User: "+ tweet.getUser().toString());
						// Log.d("DEBUG", "Tweet: " + tweet.toString());
						// tweet.getUser().save();
						// tweet.save();
						// }
						// ActiveAndroid.setTransactionSuccessful();
						// } finally {
						// ActiveAndroid.endTransaction();
						// }

					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// setProgressBarIndeterminateVisibility(Boolean.FALSE);
						lvTweets.onRefreshComplete();
						showFailMessage();
						super.onFailure(arg0, arg1);
					}
				});
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		if (getView() == null) {
			Log.d("DEBUG", "getView was null, wat?");
		}

//		final PullToRefreshListView lvTweets = (PullToRefreshListView) getView()
//				.findViewById(R.id.lvTweets);

	}

	@Override
	public void queryTehNetz(int page, int totalItemsCount) {

		if (totalItemsCount == 1) {
			Log.d("DEBUG", "FFFFFFFFFFFFFFFFFFFFUUUUUUUUUUUUUUUUUUUUU");
		} else {
			Tweet lastTweet = (Tweet) lvTweets
					.getItemAtPosition(totalItemsCount - 1);
			Integer count = totalItemsCount;

			Log.d("DEBUG", count.toString());
			Log.d("DEBUG", lastTweet.toString());

			// minus one in the next call to get rid of duplicate tweet
			MyTwitterApp.getRestClient().getMoreHomeTimeline(
					lastTweet.getTweetId() - 1, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONArray jsonTweets) {
							// setProgressBarIndeterminateVisibility(Boolean.FALSE);
							Log.d("DEBUG", jsonTweets.toString());
							tweets = Tweet.fromJson(jsonTweets);
							getAdapter().addAll(tweets);
							// getAdapter().notifyDataSetChanged();
							// ActiveAndroid.beginTransaction();
							// try {
							// for (Tweet tweetInstance : tweets) {
							// tweetInstance.getUser().save();
							// tweetInstance.save();
							// }
							// ActiveAndroid.setTransactionSuccessful();
							// } finally {
							// ActiveAndroid.endTransaction();
							// }

						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							showFailMessage();
							// setProgressBarIndeterminateVisibility(Boolean.FALSE);
							super.onFailure(arg0, arg1);
						}

					});

		}

	}

}
