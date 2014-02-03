package com.mikes.Twitter.app.models;

import org.json.JSONObject;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User")
public class User extends BaseModel {
	
	@Column(name = "name")
    public String name;
	@Column(name = "id")
    public Long id;
	@Column(name = "screenName")
	public String screenName;
	@Column(name = "profileImageUrl")
    public String profileImageUrl;
	@Column(name = "profileBackgroundImageUrl")
    public String profileBackgroundImageUrl;
	@Column(name = "numTweets")
    public int numTweets;
	@Column(name = "followersCount")
    public int followersCount;
    @Column(name = "friendsCount")
    public int friendsCount;
	
    public User(){
    	super();
    }
    
    
    public User(String name, Long id, String screenName,
			String profileImageUrl, String profileBackgroundImageUrl,
			int numTweets, int followersCount, int friendsCount) {
		super();
		this.name = name;
		this.id = id;
		this.screenName = screenName;
		this.profileImageUrl = profileImageUrl;
		this.profileBackgroundImageUrl = profileBackgroundImageUrl;
		this.numTweets = numTweets;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
	}


	public String getName() {
        return getString("name");
    }

    
    public long getId() {
    	
        return getLong("id");
    }

    public String getScreenName() {
        return getString("screen_name");
    }

    public String getProfileImageUrl() {
    	return getString("profile_image_url");
    }
    
    public String getProfileBackgroundImageUrl() {
        return getString("profile_background_image_url");
    }

    public int getNumTweets() {
        return getInt("statuses_count");
    }

    public int getFollowersCount() {
        return getInt("followers_count");
    }

    public int getFriendsCount() {
        return getInt("friends_count");
    }

    public static User fromJson(JSONObject json) {
        User u = new User();

        try {
            u.jsonObject = json;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }


}