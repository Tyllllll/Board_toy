package team.group10.board.model;

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
public class UserInfo {
	private String username;
	private String password;
	private String token;

	public UserInfo(String username, String password, String token) {
		this.username = username;
		this.password = password;
		this.token = token;
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

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getToken() {
		return token;
	}
}
