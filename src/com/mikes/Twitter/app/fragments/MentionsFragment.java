package com.mikes.Twitter.app.fragments;

import java.util.List;

import org.json.JSONArray;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.MyTwitterApp;
import com.mikes.Twitter.app.TweetsAdapter;
import com.mikes.Twitter.app.models.Tweet;

import android.os.Bundle;
import android.util.Log;

public class MentionsFragment extends TweetsListFragment {

	TweetsAdapter adapter;
	List<Tweet> tweets;
	TweetsListFragment fragmentTweets;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getMainData();
		

	}
	
	
	
	public void getMainData() {
		MyTwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONArray jsonTweets) {

				lvTweets.onRefreshComplete();
				
				tweets = Tweet.fromJson(jsonTweets);

				getAdapter().addAll(tweets);

			}
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				lvTweets.onRefreshComplete();
				showFailMessage();
				super.onFailure(arg0, arg1);
			}

		});
	}

	@Override
	public void queryTehNetz(int page, int totalItemsCount) {

		if (totalItemsCount == 1) {
			Log.d("DEBUG", "FFFFFFFFFFFFFFFFFFFFUUUUUUUUUUUUUUUUUUUUU");
		} else {
			Tweet lastTweet = (Tweet) lvTweets
					.getItemAtPosition(totalItemsCount - 1);

			// minus one in the next call to get rid of duplicate tweet
			MyTwitterApp.getRestClient().getMoreMentions(
					lastTweet.getTweetId() - 1, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONArray jsonTweets) {
							lvTweets.onRefreshComplete();
							// setProgressBarIndeterminateVisibility(Boolean.FALSE);
							showFailMessage();
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
							lvTweets.onRefreshComplete();
							// setProgressBarIndeterminateVisibility(Boolean.FALSE);
							super.onFailure(arg0, arg1);
						}

					});

		}

	}

}
