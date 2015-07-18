package com.fqy.lockscreen.utils;

import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.fqy.lockscreen.utils.AsyncImageLoader.ImageCallback;

public class CallbackImpl implements ImageCallback {
	private ImageView imageView;

	public CallbackImpl(ImageView imageView) {
		super();
		this.imageView = imageView;
	}

	public void setTag(String tag) {
		imageView.setTag(tag);
		imageView.setVisibility(View.INVISIBLE);
		// imageView.setBackgroundResource(R.drawable.icon_image);
	}

	@Override
	public void imageLoaded(Message message) {
		if (message.getData().getString("name").equals(imageView.getTag())) {
			imageView.setImageDrawable((Drawable) message.obj);
			imageView.setVisibility(View.VISIBLE);
		}
	}

}
