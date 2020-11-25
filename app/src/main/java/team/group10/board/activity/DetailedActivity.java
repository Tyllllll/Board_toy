package team.group10.board.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import team.group10.board.R;
import team.group10.board.model.UserInfo;
import team.group10.board.utils.mHttpRequest;
import team.group10.board.utils.mString;
import team.group10.board.utils.mImageConvert;

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

	private void getArticle() {
		Callback getFirstCallback = new Callback() {
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
						String imageName = "";
						StringBuffer newBody;
						// 匹配MD格式的图片格式
						Pattern pattern = Pattern.compile("!\\[.*\\]\\(.*\\)");
						Matcher matcher = pattern.matcher(body);
						if (matcher.find()) {
							// 原文本中的起始位置
							int start = matcher.start();
							int end = matcher.end();
							newBody = new StringBuffer();
							// 把md中图片后插入一个\n，note：这里是服务器返回数据的一个bug
							newBody.append(body.substring(0, matcher.end())).append("\n").append(body.substring(matcher.end(), body.length()));
//							// 如果[]之间没有图片alt，则加入一个image
							// 匹配[]
//							pattern = Pattern.compile("\\[.*\\]");
//							matcher = pattern.matcher(temp);
//							if (matcher.find()) {
//								if (matcher.end() - matcher.start() < 3) {
//									newBody.insert(start + 2, "cover");
//								}
//							}
							// 感叹号前插一个\n，note：这不知道是commonmark的bug还是服务器传回数据的bug，前面不加换行就识别不到是图片
							newBody.insert(start - 1, "\n");
							start += 1;
							end += 1;
							// 匹配图片名称，note：由于已知article里只会有一张图片，所以这里没有扩充匹配多张图片的过程
							String temp = body.substring(matcher.start(), matcher.end());
							pattern = Pattern.compile("\\(.*\\.");
							matcher = pattern.matcher(temp);
							if (matcher.find())
							{
								imageName = temp.substring(matcher.start() + 1, matcher.end() - 1).toLowerCase();
							}
							// 将图片名称替换为本地资源，即R.drawable.imageName
							// 匹配小括号，思路：获取temp中图片名的长度（带后缀），然后在newBody中替换
							pattern = Pattern.compile("\\(.*\\)");
							matcher = pattern.matcher(temp);
							if (matcher.find())
							{
								int imagePathLength = matcher.end() - matcher.start() - 2;
								newBody.replace(end - 1 - imagePathLength, end - 1, String.valueOf(mString.getResId(imageName, R.drawable.class)));
							}
						} else {
							newBody = new StringBuffer(body);
						}
						// md to html
						Parser parser = Parser.builder().build();
						Node document = parser.parse(newBody.toString());
						HtmlRenderer renderer = HtmlRenderer.builder().build();
						String html = renderer.render(document);
						if (imageName.length() > 0) {
							// 有图片
							DetailedActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// 显示带图片的文本
									bodyTxv.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, new Html.ImageGetter() {
										@Override
										public Drawable getDrawable(String s) {
											int id = Integer.parseInt(s);
											int width = mImageConvert.getWidth(DetailedActivity.this.getResources(), id);
											int height = mImageConvert.getHeight(DetailedActivity.this.getResources(), id);
											// 计算等比放缩比例
											float heightRatio = (float)height / (userInfo.getScreenWidth() * (float)0.618);
											float widthRatio =  (float)width / userInfo.getScreenWidth();
											float ratio = Math.max(heightRatio, widthRatio);
											BitmapDrawable drawable = new BitmapDrawable(DetailedActivity.this.getResources()
													, mImageConvert.decodeImage(DetailedActivity.this.getResources()
													, id, (int)(width / ratio), (int)(height / ratio)));
											drawable.setBounds(0, 0, (int)(width / ratio), (int)(height / ratio));
											return drawable;
										}
									}, null), null);
									loadingProgressBar.setVisibility(View.GONE);
								}
							});
						} else {
							// 没有图片
							DetailedActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// 显示文本
									bodyTxv.setText(Html.fromHtml(html, 0));
									loadingProgressBar.setVisibility(View.GONE);
								}
							});
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					// token失效应该在这里，傻瓜式login拿token再请求article
					Callback loseTokenLoginCallback = new Callback() {
						@Override
						public void onFailure(@NotNull Call call, @NotNull IOException e) {
							// 其实可以不用写，断开网络连接是进上面的onFailure
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
							try {
								if (response.isSuccessful()) {
									JSONObject responseJson = new JSONObject(response.body().string());
									// 这里拿到token，和username一起存sharedPreference
									userInfo.setToken(responseJson.getString("token"));
									SharedPreferences.Editor sharedPreferences = getSharedPreferences("userInfoPreferences", MODE_PRIVATE).edit();
									sharedPreferences.putString("token", userInfo.getToken());
									sharedPreferences.apply();
									getArticle();
								} else {
									DetailedActivity.this.runOnUiThread(new Runnable() {
										@Override
										public void run() {
											Toast.makeText(DetailedActivity.this, "network error. message: " + response.message() + ". code: " + response.code(), Toast.LENGTH_LONG).show();
											loadingProgressBar.setVisibility(View.GONE);
										}
									});
								}
							}catch (JSONException e) {
								e.printStackTrace();
							}
						}
					};
					mHttpRequest.postLogin(userInfo.getUsername(), userInfo.getPassword(), loseTokenLoginCallback);
				}
			}
		};
		mHttpRequest.getArticle(articleId, userInfo.getToken(), true, getFirstCallback);
	}
}