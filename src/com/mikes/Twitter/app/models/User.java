package com.mikes.Twitter.app.models;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "user")
public class User extends Model {
    
	//get rid of all indexes but one. 
	@Column(name = "name")
    private String name;
    
    @Column(name = "uid")
    private long uid;
    
    @Column(name = "screen_name")
    private String screenName;
    
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "num_tweets")
    private int numTweets;
    
    @Column(name = "followers_count")
    private int followersCount;
    
    @Column(name = "friends_count")
    private int friendsCount;
    

    
    public User() {
        super();
    }

    public User(String name, long uid, String screenName,
            String profileImageUrl, String description,int numTweets, int followersCount,
            int friendsCount) {
        super();
        this.name = name;
        this.uid = uid;
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
        this.numTweets = numTweets;
        this.followersCount = followersCount;
        this.friendsCount = friendsCount;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getNumTweets() {
        return numTweets;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }
    
    public String toString() {
    	return this.getName();
    }
    
    public String getTagline() {
    	return description;
    }

    public static User fromJson(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.numTweets = json.getInt("statuses_count");
            u.followersCount = json.getInt("followers_count");
            u.friendsCount = json.getInt("friends_count");
            u.description = json.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }


}