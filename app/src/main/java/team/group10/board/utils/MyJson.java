package team.group10.board.utils;

import android.app.Activity;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ProjectName: Board
 * @Package: team.group10.board.utils
 * @ClassName: mJson
 * @Description: json操作类
 * @Author: Tyllllll
 * @CreateDate: 2020/11/18 7:57
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/18 7:57
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MyJson {
	public static String getJsonFromDotJson(String filename, Activity activity) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			AssetManager assetManager = activity.getAssets();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(filename)));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
}
