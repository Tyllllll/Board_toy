package team.group10.board.model;

import android.app.Application;

/**
 * @ProjectName: Board
 * @Package: team.group10.board.model
 * @ClassName: UserInfo
 * @Description: 用户信息类
 * @Author: Tyllllll
 * @CreateDate: 2020/11/20 11:33
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/20 11:33
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class UserInfo extends Application {
	private String username;
	private String password;
	private String token;
	private int ScreenWidth;
	private int ScreenHeight;

	public UserInfo() {
		this.username = "";
		this.password = "";
		this.token = "";
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setScreenWidth(int screenWidth) {
		ScreenWidth = screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		ScreenHeight = screenHeight;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getToken() {
		return token;
	}

	public int getScreenWidth() {
		return ScreenWidth;
	}

	public int getScreenHeight() {
		return ScreenHeight;
	}

	public void clear() {
		this.username = "";
		this.password = "";
		this.token = "";
	}
}
