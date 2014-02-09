package com.mikes.Twitter.app.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikes.Twitter.app.MyTwitterApp;
import com.mikes.Twitter.app.ProfileActivity;
import com.mikes.Twitter.app.R;
import com.mikes.Twitter.app.models.Tweet;

public class UserTimelineFragment extends TweetsListFragment {

	String screenName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ProfileActivity activity = (ProfileActivity) getActivity();
		final String screenNamedizzle = activity.getScreenName();



			MyTwitterApp.getRestClient().getOtherUsersTimeline(
					screenNamedizzle, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONArray json) {

							getAdapter().addAll(Tweet.fromJson(json));


						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							Toast.makeText(getActivity(), "now you fucked up",
									Toast.LENGTH_LONG).show();
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
			Integer count = totalItemsCount;

			Log.d("DEBUG", count.toString());
			Log.d("DEBUG", lastTweet.toString());

			ProfileActivity activity = (ProfileActivity) getActivity();
			final String screenNamedizzle = activity.getScreenName();
			
			MyTwitterApp.getRestClient().getAdditionalOtherUsersTimeline(
					screenNamedizzle, lastTweet.getTweetId() - 1, new JsonHttpResponseHandler() {

						public void onSuccess(JSONArray json) {
							// Log.d("DEBUG", json.toString());
							tweets = Tweet.fromJson(json);
							getAdapter().addAll(tweets);
							// adapter.notifyDataSetChanged();

							// ActiveAndroid.beginTransaction();
							// try {
							// for (Tweet tweetInstance : Tweet.fromJson(json))
							// {
							// tweetInstance.getUser().save();
							// tweetInstance.save();
							// }
							// ActiveAndroid.setTransactionSuccessful();
							// } finally {
							// ActiveAndroid.endTransaction();
							// }
						}

						public void onFailure(Throwable e) {
							Log.d("DEBUG",
									"Fetch timeline error: " + e.toString());
						}
					});
		}
	}
}
