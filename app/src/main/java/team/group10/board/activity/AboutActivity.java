package team.group10.board.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import team.group10.board.R;

/**
 * @ProjectName: Board
 * @Package: team.group10.board.activity
 * @ClassName: AboutActivity
 * @Description: java类作用描述
 * @Author: Tyllllll
 * @CreateDate: 2020/11/26 11:08
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/26 11:08
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class AboutActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	public void update(View view) {
		Toast.makeText(this, getResources().getText(R.string.toastMsg), Toast.LENGTH_SHORT).show();
	}
}
