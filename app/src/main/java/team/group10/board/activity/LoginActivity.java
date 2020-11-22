package team.group10.board.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import okhttp3.Response;
import team.group10.board.R;
import team.group10.board.model.UserInfo;
import team.group10.board.utils.mHttpRequest;

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

	UserInfo userInfo;
	EditText usernameEditText;
	EditText passwordEditText;
	Button loginButton;
	ProgressBar loadingProgressBar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		SharedPreferences sharedPreferences = getSharedPreferences("userInfoPreferences", MODE_PRIVATE);
		userInfo = (UserInfo)getApplication();

		usernameEditText = findViewById(R.id.username);
		passwordEditText = findViewById(R.id.password);
		loginButton = findViewById(R.id.login);
		usernameEditText.addTextChangedListener(this);
		passwordEditText.addTextChangedListener(this);

		loadingProgressBar = findViewById(R.id.loading);

		if (userInfo.getUsername().length() > 0 && userInfo.getPassword().length() > 0) {
			usernameEditText.setText(userInfo.getUsername());
			passwordEditText.setText(userInfo.getPassword());
		}
	}

	public void btn_login(View view) {
		loadingProgressBar.setVisibility(View.VISIBLE);
		userInfo.setUsername(usernameEditText.getText().toString());
		userInfo.setPassword(passwordEditText.getText().toString());
		// 缓存数据
		SharedPreferences.Editor sharedPreferences = getSharedPreferences("userInfoPreferences", MODE_PRIVATE).edit();
		sharedPreferences.putString("username", userInfo.getUsername());
		sharedPreferences.putString("password", userInfo.getPassword());
		sharedPreferences.apply();
		// 实现回调函数
		Callback loginCallback = new Callback() {
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
					if (response.isSuccessful()) {
						JSONObject responseJson = new JSONObject(response.body().string());
						// 这里拿到token，和username一起存sharedPreference
						userInfo.setToken(responseJson.getString("token"));
						sharedPreferences.putString("token", userInfo.getToken());
						sharedPreferences.apply();
						LoginActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(LoginActivity.this, "login success!", Toast.LENGTH_SHORT).show();
								// 跳转回detailed
								setResult(RESULT_OK);
								loadingProgressBar.setVisibility(View.GONE);
								finish();
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
				}catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		// 发送网络请求
		mHttpRequest.postLogin(userInfo.getUsername(), userInfo.getPassword(), loginCallback);
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
