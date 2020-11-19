package team.group10.board.model;

import java.util.HashMap;

/**
 * @ProjectName: Board
 * @Package: team.group10.board.model
 * @ClassName: NewsItem
 * @Description: List中News类
 * @Author: Tyllllll
 * @CreateDate: 2020/11/17 15:29
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/17 15:29
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class NewsItem {
	private int type;
	private HashMap<String, Object> map;
	public NewsItem(int type, HashMap<String, Object> map) {
		this.type = type;
		this.map = map;
	}

	public int getType() {
		return type;
	}

	public HashMap<String, Object> getMap() {
		return map;
	}
}
