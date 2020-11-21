package team.group10.board.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.group10.board.R;
import team.group10.board.model.UserInfo;

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
//		loadingProgressBar.setVisibility(View.VISIBLE);

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
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://vcapi.lvdaqian.cn/article/" + articleId)
				.header("Authorization", "Bearer " + userInfo.getToken())
				.build();
		client.newCall(request).enqueue(new Callback() {
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
					// token失效应该在这里，暂定跳转login界面点一下login
					Looper.prepare();
					Toast.makeText(DetailedActivity.this, response.body().string() + "\n please login again", Toast.LENGTH_SHORT).show();
					Looper.loop();

					startActivity(new Intent(DetailedActivity.this, LoginActivity.class));
				}
			}
		});
	}
}