package team.group10.board.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team.group10.board.R;
import team.group10.board.model.UserInfo;
import team.group10.board.utils.mAdapter;
import team.group10.board.model.NewsItem;
import team.group10.board.utils.mJson;
import team.group10.board.utils.mString;

/**
 * @ProjectName: Board
 * @Package: team.group10.board
 * @ClassName: Content
 * @Description: java类作用描述
 * @Author: Tyllllll
 * @CreateDate: 2020/11/17 13:00
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/17 13:00
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ContentActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

	UserInfo userInfo;
	List<NewsItem> newsItemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);

		final ListView listView;
		newsItemList = new ArrayList<>();
		JSONObject newsJson;
		HashMap<String, Object> map;
		String newsString = mJson.getJsonFromDotJson("metadata.json", this);
		try {
			JSONArray jsonArray = new JSONArray(newsString);
			int type;
			for (int i = 0; i < jsonArray.length(); i++) {
				newsJson = jsonArray.getJSONObject(i);
				map = new HashMap<>();
				type = newsJson.getInt("type");
				switch (type) {
					case 0:
						break;
					case 4:
						JSONArray tempJson = newsJson.getJSONArray("covers");
						map.put("image1", mString.getResId(tempJson.getString(0).split("\\.")[0].toLowerCase(), R.drawable.class));
						map.put("image2", mString.getResId(tempJson.getString(1).split("\\.")[0].toLowerCase(), R.drawable.class));
						map.put("image3", mString.getResId(tempJson.getString(2).split("\\.")[0].toLowerCase(), R.drawable.class));
						map.put("image4", mString.getResId(tempJson.getString(3).split("\\.")[0].toLowerCase(), R.drawable.class));
						break;
					default:
						String image_name = newsJson.getString("cover").split("\\.")[0].toLowerCase();
						map.put("image", mString.getResId(image_name, R.drawable.class));
						break;
				}
				map.put("title", newsJson.getString("title"));
				map.put("author", newsJson.getString("author"));
				map.put("publishTime", newsJson.getString("publishTime"));
				map.put("id", newsJson.getString("id"));
				newsItemList.add(new NewsItem(type, map));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		listView = findViewById(R.id.news_item);
		listView.setAdapter(new mAdapter(this, newsItemList));
		listView.setOnItemClickListener(this);

		SharedPreferences login_info = getSharedPreferences("userInfoPreferences", MODE_PRIVATE);
		userInfo = (UserInfo) getApplication();
		userInfo.setUsername(login_info.getString("username", ""));
		userInfo.setPassword(login_info.getString("password", ""));
		userInfo.setToken(login_info.getString("token", ""));
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		// 管他鸟的直接进detailed，在里面判断有没有登录信息，这样的话能直接finish到detailed页面
		Intent intent = new Intent(this, DetailedActivity.class);
		intent.putExtra("id", (String) newsItemList.get(i).getMap().get("id"));
		intent.putExtra("title", (String) newsItemList.get(i).getMap().get("title"));
		intent.putExtra("author", (String) newsItemList.get(i).getMap().get("author"));
		intent.putExtra("publishTime", (String) newsItemList.get(i).getMap().get("publishTime"));
		startActivityForResult(intent, 100);
	}

	public void clearPreferences(View view) {
		SharedPreferences.Editor editor = getSharedPreferences("userInfoPreferences", MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
		userInfo.clear();
		Toast.makeText(this, "userInfo has been cleared", Toast.LENGTH_SHORT).show();
	}

	public void changeToken(View v) {
		SharedPreferences.Editor editor = getSharedPreferences("userInfoPreferences", MODE_PRIVATE).edit();
		// 这是只清删token的
		editor.putString("token", "123456");
		editor.apply();
		userInfo.setToken("123456");
		Toast.makeText(this, "token has been changed to invalid", Toast.LENGTH_SHORT).show();
	}
}