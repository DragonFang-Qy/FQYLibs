package com.fqy.fqylibs.view;

import java.util.Timer;

import com.aim.propertyapp.R;
import com.aim.propertyapp.view.AdGallery.ViewHolder;
import com.aim.util.UtilImageCache;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * @Title: ADViewPager.java
 * @Package com.fqy.fqylibs.view
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年7月15日上午11:45:57
 * @version V1.0
 */
public class ADViewPager extends ViewPager {

	private Timer timer;

	private Context mContext;

	public ADViewPager(Context context) {
		this(context, null);

	}

	public ADViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		timer = new Timer();
		mContext = context;
		
	}

	
	class AdAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return advertiseList.size()
                    * (Integer.MAX_VALUE / advertiseList.size());
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.adgallery_image, null);
                Gallery.LayoutParams params = new Gallery.LayoutParams(
                        Gallery.LayoutParams.FILL_PARENT,
                        Gallery.LayoutParams.WRAP_CONTENT);
                convertView.setLayoutParams(params);

                viewHolder = new ViewHolder();
                viewHolder.imageview = (ImageView) convertView
                        .findViewById(R.id.gallery_image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ImageLoader.getInstance().displayImage(
                    advertiseList.get(position % advertiseList.size())
                            .getAd_image(), viewHolder.imageview,
                    UtilImageCache.OPTIONS.default_options);
//            viewHolder.imageview.setImageResource(R.drawable.ic_launcher);
            return convertView;
        }
    }
}
