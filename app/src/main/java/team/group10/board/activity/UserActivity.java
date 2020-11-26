package team.group10.board.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import team.group10.board.R;
import team.group10.board.model.UserInfo;

/**
 * @ProjectName: Board
 * @Package: team.group10.board.activity
 * @ClassName: UserActivity
 * @Description: java类作用描述
 * @Author: Tyllllll
 * @CreateDate: 2020/11/25 21:38
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/25 21:38
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class UserActivity extends AppCompatActivity {

	UserInfo userInfo;
	TextView username;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		userInfo = (UserInfo)getApplication();
		if (userInfo.getUsername().length() == 0) {
			startActivityForResult(new Intent(this, LoginActivity.class), 201);
		}
		username = findViewById(R.id.username);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 201) {
			if (resultCode == RESULT_OK) {
				username.setText(userInfo.getUsername());
			} else {
				finish();
			}
		}
	}

	public void exit(View v) {
		SharedPreferences.Editor editor = getSharedPreferences("userInfoPreferences", MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
		userInfo.clear();
		Toast.makeText(this, "exit success", Toast.LENGTH_SHORT).show();
		finish();
	}

	public void about(View v) {
		startActivity(new Intent(this, AboutActivity.class));
	}
}
