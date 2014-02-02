package com.mikes.Twitter.app;

import org.json.JSONObject;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {

	public EditText etCompose;
	public Button bTweet;
	public TextView tvCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);

		etCompose = (EditText) findViewById(R.id.etCompose);
		bTweet = (Button) findViewById(R.id.bTweet);
		tvCount = (TextView) findViewById(R.id.tvCount);

		bTweet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String tweet = etCompose.getText().toString();

				MyTwitterApp.getRestClient().postTweet(tweet,
						new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(JSONObject json) {
								Log.d("DEBUG", "Success");
								Toast.makeText(ComposeTweetActivity.this,
										"Hold on to your butts",
										Toast.LENGTH_LONG).show();
								ComposeTweetActivity.this.finish();
								super.onSuccess(json);
							}

							@Override
							public void onFailure(Throwable arg0,
									JSONObject arg1) {
								Log.d("DEBUG", "Fail sauce");
								super.onFailure(arg0, arg1);
							}
						});

			}
		});

		etCompose.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				

				Integer length = etCompose.getText().length();
				
				Integer stuffstuff = 140 - length;
				String stuff = stuffstuff.toString();
				tvCount.setText(stuff);

				if (length > 115) {
					tvCount.setTextColor(Color.RED);
				} else {
					tvCount.setTextColor(Color.BLACK);
				}

				Log.d("KeyNumber", "chars: " + length);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
			

			}
		});
	}

}
