package team.group10.board.utils;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @ProjectName: Board
 * @Package: team.group10.board.utils
 * @ClassName: mHttpRequest
 * @Description: http请求类
 * @Author: Tyllllll
 * @CreateDate: 2020/11/22 10:03
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/22 10:03
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class mHttpRequest {
	public static void postLogin(String username, String password, Callback callback) {
		OkHttpClient client = new OkHttpClient();
		FormBody formBody = new FormBody.Builder()
				.add("username", username)
				.add("password", password)
				.build();
		Request request = new Request.Builder()
				.url("https://vcapi.lvdaqian.cn/login")
				.post(formBody)
				.build();
		client.newCall(request).enqueue(callback);
	}

	public static void getArticle(String articleId, String token, boolean isMarkdown, Callback callback) {
		OkHttpClient client = new OkHttpClient();
		System.out.println("https://vcapi.lvdaqian.cn/article/" + articleId + "?markdown=" + isMarkdown);
		Request request = new Request.Builder()
				.url("https://vcapi.lvdaqian.cn/article/" + articleId + "?markdown=" + isMarkdown)
				.header("Authorization", "Bearer " + token)
				.build();
		client.newCall(request).enqueue(callback);
	}
}
