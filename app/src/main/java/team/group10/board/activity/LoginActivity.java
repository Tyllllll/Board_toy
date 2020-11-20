package team.group10.board.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.group10.board.R;
import team.group10.board.model.UserInfo;

/**
 * @ProjectName: Board
 * @Package: team.group10.board
 * @ClassName: Login
 * @Description: java类作用描述
 * @Author: Tyllllll
 * @CreateDate: 2020/11/19 20:11
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/19 20:11
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class LoginActivity extends AppCompatActivity implements TextWatcher {

	EditText usernameEditText;
	EditText passwordEditText;
	Button loginButton;
	ProgressBar loadingProgressBar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		SharedPreferences sharedPreferences = getSharedPreferences("userInfoPreferences", MODE_PRIVATE);
		UserInfo userInfo = new UserInfo(sharedPreferences.getString("username", ""), sharedPreferences.getString("password", ""), sharedPreferences.getString("token", ""));

		usernameEditText = findViewById(R.id.username);
		passwordEditText = findViewById(R.id.password);
		loginButton = findViewById(R.id.login);
		usernameEditText.addTextChangedListener(this);
		passwordEditText.addTextChangedListener(this);

		loadingProgressBar = findViewById(R.id.loading);
	}

	public void btn_login(View view) {
		loadingProgressBar.setVisibility(View.VISIBLE);
		// 发送网络请求
		OkHttpClient client = new OkHttpClient();
		FormBody formBody = new FormBody.Builder()
				.add("username", usernameEditText.getText().toString())
				.add("password", passwordEditText.getText().toString())
				.build();
		Request request = new Request.Builder().url(this.getString(R.string.login_url)).post(formBody).build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				// 断开网络连接是进这里
				LoginActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(LoginActivity.this, "network error", Toast.LENGTH_SHORT).show();
						loadingProgressBar.setVisibility(View.GONE);
					}
				});
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				try {
					// 缓存数据
					SharedPreferences.Editor sharedPreferences = getSharedPreferences("userInfoPreferences", MODE_PRIVATE).edit();
					sharedPreferences.putString("username", usernameEditText.getText().toString());
					sharedPreferences.putString("password", passwordEditText.getText().toString());
					if (response.isSuccessful()) {
						JSONObject responseJson = new JSONObject(response.body().string());
						// 这里拿到token，和username一起存sharedPreference
						sharedPreferences.putString("token", responseJson.getString("token"));
						LoginActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loadingProgressBar.setVisibility(View.GONE);
								Toast.makeText(LoginActivity.this, "login success!\n这里添加跳转下一界面", Toast.LENGTH_SHORT).show();
								// 跳转到下一个activity
							}
						});
					} else {
						LoginActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(LoginActivity.this, "network error. message: " + response.message() + ". code: " + response.code(), Toast.LENGTH_LONG).show();
								loadingProgressBar.setVisibility(View.GONE);
							}
						});
					}
					// 缓存提交
					sharedPreferences.commit();
				}catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override
	public void afterTextChanged(Editable editable) {
		// 用户名密码都非空时，允许登录
		if (usernameEditText.getText().length() > 0 && passwordEditText.getText().length() > 0 ) {
			loginButton.setEnabled(true);
		} else {
			loginButton.setEnabled(false);
		}
	}
}
