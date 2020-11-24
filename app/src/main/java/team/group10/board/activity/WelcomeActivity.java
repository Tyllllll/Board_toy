package team.group10.board.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import team.group10.board.R;

public class WelcomeActivity extends AppCompatActivity {

	private View contentView;
	private final Handler handler = new Handler();
	private TextView skipTxv;
	private int counter;
	Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		contentView = findViewById(R.id.fullscreen_content);
		skipTxv = findViewById(R.id.skip);
		skipTxv.setText("Skip");
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				ActionBar actionBar = getSupportActionBar();
				if (actionBar != null) {
					actionBar.hide();
				}
			}
		}, 100);

		// 3秒计时
		counter = 4;
		runnable = new Runnable() {
			@Override
			public void run() {
				// 计时改秒数
				if (--counter == 0) {
					handler.removeCallbacks(runnable);
					finish();
				}
				skipTxv.setText("Skip(" + counter + ")");
				handler.postDelayed(this, 1000);
			}
		};
	}

	@Override
	protected void onResume() {
		super.onResume();

		handler.postDelayed(HideUIRunnable, 300);
		handler.postDelayed(runnable, 1000);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			return true;
		} else {
			return super.dispatchKeyEvent(event);
		}
	}

	Runnable HideUIRunnable = new Runnable() {
		@Override
		public void run() {
			// Delayed removal of status and navigation bar

			// Note that some of these constants are new as of API 16 (Jelly Bean)
			// and API 19 (KitKat). It is safe to use them, as they are inlined
			// at compile-time and do nothing on earlier devices.
			contentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
			                                  | View.SYSTEM_UI_FLAG_LOW_PROFILE
			                                  // 不知道为什么开这个会有布局bug
//			                                  | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
			                                  | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
			                                  | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
			                                  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
			);
		}
	};

	public void skip(View v) {
		handler.removeCallbacks(runnable);
		finish();
	}

	public void openWeb(View v) {
		counter = 3;
		handler.removeCallbacks(runnable);
	}
}