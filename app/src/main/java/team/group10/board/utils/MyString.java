package team.group10.board.utils;

import java.lang.reflect.Field;

/**
 * @ProjectName: Board
 * @Package: team.group10.board.utils
 * @ClassName: mString
 * @Description: 图片文件名转资源引用
 * @Author: Tyllllll
 * @CreateDate: 2020/11/18 7:59
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/18 7:59
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MyString {
	public static int getResId(String variableName, Class<?> c) {
		try {
			Field field = c.getDeclaredField(variableName);
			return field.getInt(field);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
