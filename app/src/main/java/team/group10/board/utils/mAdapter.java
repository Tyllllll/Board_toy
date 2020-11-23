package team.group10.board.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.group10.board.R;
import team.group10.board.model.NewsItem;
import team.group10.board.model.UserInfo;

/**
 * @ProjectName: Board
 * @Package: team.group10.board.utils
 * @ClassName: mAdapter
 * @Description: 继承base
 * @Author: Tyllllll
 * @CreateDate: 2020/11/17 16:40
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/18 10:55
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class mAdapter extends BaseAdapter {
	private List<NewsItem> newsItemList;
	private LayoutInflater layoutInflater;
	private UserInfo userInfo;

	public mAdapter(Context context, List<NewsItem> newsItemList, UserInfo userInfo) {
		this.newsItemList = newsItemList;
		layoutInflater = LayoutInflater.from(context);
		this.userInfo = userInfo;
	}

	@Override
	public int getCount() {
		return newsItemList.size();
	}

	@Override
	public Object getItem(int i) {
		return newsItemList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder1 holder0 = null;
		ViewHolder2 holder1 = null;
		ViewHolder2 holder2 = null;
		ViewHolder2 holder3 = null;
		ViewHolder3 holder4 = null;
		final ImageSize imageLayout1 = new ImageSize(userInfo.getScreenWidth() / 5, userInfo.getScreenWidth() / 5);
		final ImageSize imageLayout2 = new ImageSize(userInfo.getScreenWidth() / 5, userInfo.getScreenWidth() / 5);
		final ImageSize imageLayout3 = new ImageSize(userInfo.getScreenWidth(), (int)(userInfo.getScreenWidth() * 0.618));
		final ImageSize imageLayout4 = new ImageSize(userInfo.getScreenWidth() / 5, userInfo.getScreenWidth() / 5);
		final int type = newsItemList.get(i).getType();
		// 判断view的布局类型
		switch (type) {
			case 0:
				// 获取view对象
				if (view == null) {
					view = layoutInflater.inflate(R.layout.layout_type0, null);
					holder0 = new ViewHolder1();
					holder0.title = view.findViewById(R.id.news_title);
					holder0.author = view.findViewById(R.id.author);
					holder0.publishTime = view.findViewById(R.id.publish_time);
					view.setTag(R.id.news_type0, holder0);
				} else if (view.getTag(R.id.news_type0) == null) {
					// 这一项是为了防止多重view混用tag出错
					view = layoutInflater.inflate(R.layout.layout_type0, null);
					holder0 = new ViewHolder1();
					holder0.title = view.findViewById(R.id.news_title);
					holder0.author = view.findViewById(R.id.author);
					holder0.publishTime = view.findViewById(R.id.publish_time);
					view.setTag(R.id.news_type0, holder0);
				} else {
					holder0 = (ViewHolder1) view.getTag(R.id.news_type0);
				}
				break;
			case 1:
				if (view == null) {
					view = layoutInflater.inflate(R.layout.layout_type1, null);
					holder1 = new ViewHolder2();
					holder1.title = view.findViewById(R.id.news_title);
					holder1.author = view.findViewById(R.id.author);
					holder1.publishTime = view.findViewById(R.id.publish_time);
					holder1.image = view.findViewById(R.id.news_photo);
					view.setTag(R.id.news_type1, holder1);
				} else if (view.getTag(R.id.news_type1) == null) {
					view = layoutInflater.inflate(R.layout.layout_type1, null);
					holder1 = new ViewHolder2();
					holder1.title = view.findViewById(R.id.news_title);
					holder1.author = view.findViewById(R.id.author);
					holder1.publishTime = view.findViewById(R.id.publish_time);
					holder1.image = view.findViewById(R.id.news_photo);
					view.setTag(R.id.news_type1, holder1);
				} else {
					holder1 = (ViewHolder2) view.getTag(R.id.news_type1);
				}
				break;
			case 2:
				if (view == null) {
					view = layoutInflater.inflate(R.layout.layout_type2, null);
					holder2 = new ViewHolder2();
					holder2.title = view.findViewById(R.id.news_title);
					holder2.author = view.findViewById(R.id.author);
					holder2.publishTime = view.findViewById(R.id.publish_time);
					holder2.image = view.findViewById(R.id.news_photo);
				} else if (view.getTag(R.id.news_type2) == null) {
					view = layoutInflater.inflate(R.layout.layout_type2, null);
					holder2 = new ViewHolder2();
					holder2.title = view.findViewById(R.id.news_title);
					holder2.author = view.findViewById(R.id.author);
					holder2.publishTime = view.findViewById(R.id.publish_time);
					holder2.image = view.findViewById(R.id.news_photo);
				} else {
					holder2 = (ViewHolder2) view.getTag(R.id.news_type2);
				}
				break;
			case 3:
				if (view == null) {
					view = layoutInflater.inflate(R.layout.layout_type3, null);
					holder3 = new ViewHolder2();
					holder3.title = view.findViewById(R.id.news_title);
					holder3.author = view.findViewById(R.id.author);
					holder3.publishTime = view.findViewById(R.id.publish_time);
					holder3.image = view.findViewById(R.id.news_photo_big);
					view.setTag(R.id.news_type3, holder3);
				} else if (view.getTag(R.id.news_type3) == null) {
					view = layoutInflater.inflate(R.layout.layout_type3, null);
					holder3 = new ViewHolder2();
					holder3.title = view.findViewById(R.id.news_title);
					holder3.author = view.findViewById(R.id.author);
					holder3.publishTime = view.findViewById(R.id.publish_time);
					holder3.image = view.findViewById(R.id.news_photo_big);
					view.setTag(R.id.news_type3, holder3);
				} else {
					holder3 = (ViewHolder2) view.getTag(R.id.news_type3);
				}
				break;
			case 4:
				if (view == null) {
					view = layoutInflater.inflate(R.layout.layout_type4, null);
					holder4 = new ViewHolder3();
					holder4.title = view.findViewById(R.id.news_title);
					holder4.author = view.findViewById(R.id.author);
					holder4.publishTime = view.findViewById(R.id.publish_time);
					holder4.image1 = view.findViewById(R.id.news_photo1);
					holder4.image2 = view.findViewById(R.id.news_photo2);
					holder4.image3 = view.findViewById(R.id.news_photo3);
					holder4.image4 = view.findViewById(R.id.news_photo4);
					view.setTag(R.id.news_type4, holder4);
				} else if (view.getTag(R.id.news_type4) == null) {
					view = layoutInflater.inflate(R.layout.layout_type4, null);
					holder4 = new ViewHolder3();
					holder4.title = view.findViewById(R.id.news_title);
					holder4.author = view.findViewById(R.id.author);
					holder4.publishTime = view.findViewById(R.id.publish_time);
					holder4.image1 = view.findViewById(R.id.news_photo1);
					holder4.image2 = view.findViewById(R.id.news_photo2);
					holder4.image3 = view.findViewById(R.id.news_photo3);
					holder4.image4 = view.findViewById(R.id.news_photo4);
					view.setTag(R.id.news_type4, holder4);
				} else {
					holder4 = (ViewHolder3) view.getTag(R.id.news_type4);
				}
				break;
			default:
				break;
		}
		// 设置view内容
		switch (type) {
			case 0:
				holder0.title.setText(newsItemList.get(i).getMap().get("title").toString());
				holder0.author.setText(newsItemList.get(i).getMap().get("author").toString());
				holder0.publishTime.setText(newsItemList.get(i).getMap().get("publishTime").toString());
				break;
			case 1:
				holder1.title.setText(newsItemList.get(i).getMap().get("title").toString());
				holder1.author.setText(newsItemList.get(i).getMap().get("author").toString());
				holder1.publishTime.setText(newsItemList.get(i).getMap().get("publishTime").toString());
				holder1.image.setImageBitmap(mImageConvert.decodeImage(view.getResources(), (Integer) newsItemList.get(i).getMap().get("image"), imageLayout1.mWidth, imageLayout1.mHeight));
				break;
			case 2:
				holder2.title.setText(newsItemList.get(i).getMap().get("title").toString());
				holder2.author.setText(newsItemList.get(i).getMap().get("author").toString());
				holder2.publishTime.setText(newsItemList.get(i).getMap().get("publishTime").toString());
				holder2.image.setImageBitmap(mImageConvert.decodeImage(view.getResources(), (Integer) newsItemList.get(i).getMap().get("image"), imageLayout2.mWidth, imageLayout2.mHeight));
				break;
			case 3:
				holder3.title.setText(newsItemList.get(i).getMap().get("title").toString());
				holder3.author.setText(newsItemList.get(i).getMap().get("author").toString());
				holder3.publishTime.setText(newsItemList.get(i).getMap().get("publishTime").toString());
				holder3.image.setImageBitmap(mImageConvert.decodeImage(view.getResources(), (Integer) newsItemList.get(i).getMap().get("image"), imageLayout3.mWidth, imageLayout3.mHeight));
				break;
			case 4:
				holder4.title.setText(newsItemList.get(i).getMap().get("title").toString());
				holder4.author.setText(newsItemList.get(i).getMap().get("author").toString());
				holder4.publishTime.setText(newsItemList.get(i).getMap().get("publishTime").toString());
				holder4.image1.setImageBitmap(mImageConvert.decodeImage(view.getResources(), (Integer) newsItemList.get(i).getMap().get("image1"), imageLayout4.mWidth, imageLayout4.mHeight));
				holder4.image2.setImageBitmap(mImageConvert.decodeImage(view.getResources(), (Integer) newsItemList.get(i).getMap().get("image2"), imageLayout4.mWidth, imageLayout4.mHeight));
				holder4.image3.setImageBitmap(mImageConvert.decodeImage(view.getResources(), (Integer) newsItemList.get(i).getMap().get("image3"), imageLayout4.mWidth, imageLayout4.mHeight));
				holder4.image4.setImageBitmap(mImageConvert.decodeImage(view.getResources(), (Integer) newsItemList.get(i).getMap().get("image4"), imageLayout4.mWidth, imageLayout4.mHeight));
				break;
			default:
				break;
		}
		return view;
	}
}

class ViewHolder1 {
	TextView title;
	TextView author;
	TextView publishTime;
}

class ViewHolder2 {
	TextView title;
	TextView author;
	TextView publishTime;
	ImageView image;
}

class ViewHolder3 {
	TextView title;
	TextView author;
	TextView publishTime;
	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;
}

class ImageSize {
	int mWidth;
	int mHeight;

	public ImageSize(int mWidth, int mHeight) {
		this.mWidth = mWidth;
		this.mHeight = mHeight;
	}
}