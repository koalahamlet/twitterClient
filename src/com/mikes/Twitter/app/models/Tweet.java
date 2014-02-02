package com.mikes.Twitter.app.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mikes.Twitter.app.TimelineActivity;

import android.text.format.DateUtils;

public class Tweet extends BaseModel {
    private User user;

    public User getUser() {
        return user;
    }

    public String getBody() {
        return getString("text");
    }
    
    public Date getCreatedAt() throws ParseException {
    	String date = getString("created_at");
    	

  		  final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
  		  SimpleDateFormat sdf = new SimpleDateFormat(TWITTER,Locale.ENGLISH);
  		  sdf.setLenient(true);
  		  
  		  
  		  
  		Date now = sdf.parse(date);
  		
  		
//  		String date1 = (String) DateUtils.getRelativeDateTimeString(this,
//  				now.getTime(),
//  				DateUtils.MINUTE_IN_MILLIS,
//  				DateUtils.WEEK_IN_MILLIS,
//  				0); // Eventual flags
  		  
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

        for (int i=0; i < jsonArray.length(); i++) {
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