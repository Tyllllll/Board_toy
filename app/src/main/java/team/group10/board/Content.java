package team.group10.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team.group10.board.utils.mAdapter;
import team.group10.board.model.NewsItem;
import team.group10.board.utils.mJson;
import team.group10.board.utils.mString;

public class Content extends AppCompatActivity {
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);

		List<NewsItem> newsItemList = new ArrayList<>();
		JSONObject newsJson;
		HashMap<String, Object> map;
		String newsString = mJson.getJson("metadata.json", this);
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
				newsItemList.add(new NewsItem(type, map));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		listView = findViewById(R.id.news_item);
		listView.setAdapter(new mAdapter(this, newsItemList));
	}
}