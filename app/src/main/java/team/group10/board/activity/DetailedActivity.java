package team.group10.board.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import team.group10.board.R;
import team.group10.board.model.UserInfo;
import team.group10.board.utils.mHttpRequest;

public class DetailedActivity extends AppCompatActivity {

	ProgressBar loadingProgressBar;
	UserInfo userInfo;
	String articleId;
	TextView titleTxv;
	TextView authorTxv;
	TextView publishTxv;
	TextView bodyTxv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed);

		loadingProgressBar = findViewById(R.id.loading);

		Intent superIntent = getIntent();
		titleTxv = findViewById(R.id.news_title);
		authorTxv = findViewById(R.id.author);
		publishTxv = findViewById(R.id.publish_time);
		bodyTxv = findViewById(R.id.article);

		titleTxv.setText(superIntent.getStringExtra("title"));
		authorTxv.setText(superIntent.getStringExtra("author"));
		publishTxv.setText(superIntent.getStringExtra("publishTime"));
		articleId = superIntent.getStringExtra("id");
		// 判断有没有登录信息
		userInfo = (UserInfo)getApplication();
		if (userInfo.getUsername().length() > 0 && userInfo.getToken().length() > 0) {
			// 说明有登录信息，但不保证token没过期
			getArticle();
		} else {
			// 跳转login界面
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivityForResult(loginIntent, 200);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 200) {
			if (resultCode == RESULT_OK) {
				getArticle();
			} else {
				finish();
			}
		}
	}

	protected void getArticle() {
		Callback getFirstCallback = new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				// 断开网络连接是进这里
				DetailedActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(DetailedActivity.this, "network error", Toast.LENGTH_SHORT).show();
						loadingProgressBar.setVisibility(View.GONE);
					}
				});
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				if (response.isSuccessful()) {
					try {
						JSONObject articleJson = new JSONObject(response.body().string());
						String body = articleJson.getString("data");
						DetailedActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								bodyTxv.setText(body);
								loadingProgressBar.setVisibility(View.GONE);
							}
						});
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					// token失效应该在这里，傻瓜式login拿token再请求article
					Callback loseTokenLoginCallback = new Callback() {
						@Override
						public void onFailure(@NotNull Call call, @NotNull IOException e) {
							// 其实可以不用写，断开网络连接是进上面的onFailure
							DetailedActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(DetailedActivity.this, "network error", Toast.LENGTH_SHORT).show();
									loadingProgressBar.setVisibility(View.GONE);
								}
							});
						}

						@Override
						public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
							try {
								if (response.isSuccessful()) {
									JSONObject responseJson = new JSONObject(response.body().string());
									// 这里拿到token，和username一起存sharedPreference
									userInfo.setToken(responseJson.getString("token"));
									SharedPreferences.Editor sharedPreferences = getSharedPreferences("userInfoPreferences", MODE_PRIVATE).edit();
									sharedPreferences.putString("token", userInfo.getToken());
									sharedPreferences.apply();
									getArticle();
								} else {
									DetailedActivity.this.runOnUiThread(new Runnable() {
										@Override
										public void run() {
											Toast.makeText(DetailedActivity.this, "network error. message: " + response.message() + ". code: " + response.code(), Toast.LENGTH_LONG).show();
											loadingProgressBar.setVisibility(View.GONE);
										}
									});
								}
							}catch (JSONException e) {
								e.printStackTrace();
							}
						}
					};
					mHttpRequest.postLogin(userInfo.getUsername(), userInfo.getPassword(), loseTokenLoginCallback);
				}
			}
		};
		mHttpRequest.getArticle(articleId, userInfo.getToken(), false, getFirstCallback);
	}
}