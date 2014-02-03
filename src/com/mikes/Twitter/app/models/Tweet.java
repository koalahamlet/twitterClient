package com.mikes.Twitter.app.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweet")
public class Tweet extends BaseModel {
	
	@Column(name = "user")
	private User user;
	@Column(name = "body")
	private String body;
	@Column(name = "createdAt")
	private Date createdAt;
	@Column(name = "id")
	private Long id;
	@Column(name = "isFavorited")
	private Boolean isFavorited;
	@Column(name = "isRetweeted")
	private Boolean isRetweeted;

	
	public Tweet(){
		super();
	}
	
	
	
	public Tweet(User user, String body, Date createdAt, Long id,
			Boolean isFavorited, Boolean isRetweeted) {
		super();
		this.user = user;
		this.body = body;
		this.createdAt = createdAt;
		this.id = id;
		this.isFavorited = isFavorited;
		this.isRetweeted = isRetweeted;
	}



	public User getUser() {
		return user;
	}

	public String getBody() {
		return getString("text");
	}

	public Date getCreatedAt() throws ParseException {
		String date = getString("created_at");

		final String TWITTER = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(TWITTER, Locale.ENGLISH);
		sdf.setLenient(true);

		Date now = sdf.parse(date);
		return now;

	}

	public long getId() {
		return getLong("id");
	}

	public boolean isFavorited() {
		return getBoolean("favorited");
	}

	public boolean isRetweeted() {
		return getBoolean("retweeted");
	}

	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		try {
			tweet.jsonObject = jsonObject;
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
				
			}
		}

		return tweets;
	}
}