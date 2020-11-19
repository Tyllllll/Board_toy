package team.group10.board.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import team.group10.board.R;

/**
 * @ProjectName: Board
 * @Package: team.group10.board.utils
 * @ClassName: mBitmap
 * @Description: 图片压缩为较小的Bitmap
 * @Author: Tyllllll
 * @CreateDate: 2020/11/18 21:06
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/18 21:06
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class mImageConvert {
	public static Bitmap decodeImage(Resources resources, int resId, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 只读图片的文件信息，不加载图像文件
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeResource(resources, resId, options);
		// 计算放缩比
		options.inSampleSize = calInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(resources, resId, options);
	}

	private static int calInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		int originHeight = options.outHeight;
		int originWidth = options.outWidth;
		int inSampleSize = 1;
		if (originHeight > reqHeight || originWidth > reqWidth) {
			int heightRatio = Math.round((float) originHeight / reqHeight);
			int widthRatio = Math.round((float) originWidth / reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
}
