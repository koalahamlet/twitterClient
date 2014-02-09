package com.mikes.Twitter.app;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "XisTYf2K56alRjBD9U2Kpw";       // Change this
    public static final String REST_CONSUMER_SECRET = "CIixNLolQgUhLZCNpX6YKts5Qi5nUr4nD6Z8RMmtoo"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterapp"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    
    
    
    
	public void getHomeTimeline(AsyncHttpResponseHandler handler){
    	
    	String url = getApiUrl("statuses/home_timeline.json");
    	client.get(url, new RequestParams("count", "25"), handler);	
    	
    }
	
	public void getMoreHomeTimeline( Long id, AsyncHttpResponseHandler handler ){
    	
		RequestParams params = new  RequestParams(); 
		
		params.put("count", "25");
		params.put("max_id", id.toString());
		
    	String url = getApiUrl("statuses/home_timeline.json");
    	client.get(url, params, handler);	
    	
    }
	
	
	public void getMentions(AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/mentions_timeline.json");
		client.get(url, null,handler);
	}
	
	public void getMyInfo(AsyncHttpResponseHandler handler) {

        String url = getApiUrl("account/verify_credentials.json");

        client.get(url, null, handler);

    }
	
	public void getOtherUsersInfo(String name, AsyncHttpResponseHandler handler){
		
        String url = getApiUrl("users/show.json");

        client.get(url, new RequestParams("screen_name", name), handler);
	}
	
	public void getOtherUsersTimeline(String name, AsyncHttpResponseHandler handler){
		String url = getApiUrl("statuses/user_timeline.json");
		
		client.get(url, new RequestParams("screen_name", name), handler);
	}
	
	public void getUserTimeline(AsyncHttpResponseHandler handler) {

        String url = getApiUrl("statuses/user_timeline.json");

        client.get(url, new RequestParams("count", 25), handler);

    }

    

    public void getAdditionalUserTimeline(Long id, AsyncHttpResponseHandler handler) {

        String url = getApiUrl("statuses/user_timeline.json");

        client.get(url, new RequestParams("max_id", id.toString()), handler);

    }
    
	public void getMoreMentions(Long id, AsyncHttpResponseHandler handler) {
		RequestParams params = new  RequestParams(); 
		
		params.put("count", "6");
		params.put("max_id", id.toString());
		
		String url = getApiUrl("statuses/mentions_timeline.json");
    	client.get(url, params, handler);
	}
	
    public void postTweet(String tweet, AsyncHttpResponseHandler handler){
    	
    	String url = getApiUrl("statuses/update.json");
    	client.post(url, new RequestParams("status", tweet), handler);
    	
    }
    
    
    
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}