package com.fqy.fqylibs.view;

import java.util.List;

import com.fqy.fqylibs.R;
import com.fqy.fqylibs.model.ADModel;
import com.fqy.fqylibs.utils.UtilsAsyncImageLoader;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * @Title: ADCarouselView.java
 * @Package com.fqy.fqylibs.view
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年9月7日下午2:22:02
 * @version V1.0
 */
public class ADCarouselView<T extends ADModel> extends RelativeLayout {

	private ViewFlipper adViewFlipper;// 显示广告图
	private RadioGroup adRadioGroup;// 广告位置指示器
	private TextView adTextView;// 广告描述（或标题）
	private Context myContext; // 上下文
	private int startX;// 开始滑动的位置
	private int index;// adViewFlipper的当前位置
	private int sensitive;// 灵敏度

	public static final int ADVIEWFLIPPERID = 10;
	public static final int ADRADIOGROUPID = 20;
	public static final int ADTEXTVIEWID = 30;

	private List<T> list;

	public ADCarouselView(Context context) {
		this(context, null, 0);
	}

	public ADCarouselView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ADCarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		myContext = context;
		sensitive = getResources().getDimensionPixelOffset(R.dimen.dimen10);
		createView(context);

		// // 通过ttvTa 设置自定义属性
		// TypedArray ttvTa = context.obtainStyledAttributes(attrs,
		// R.styleable.ADCarouselView);
	}

	/** 初始化控件 */
	private void createView(Context context) {

		// 设置 ViewFlipper的显示
		adViewFlipper = new ViewFlipper(context);
		adViewFlipper.setId(ADVIEWFLIPPERID);

		LayoutParams paramsVF = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		paramsVF.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

		addView(adViewFlipper, paramsVF);

		// 设置RadioGroup（位置指示器）
		adRadioGroup = new RadioGroup(context);
		adRadioGroup.setId(ADRADIOGROUPID);
		adRadioGroup.setOrientation(LinearLayout.HORIZONTAL);

		LayoutParams paramsRG = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		paramsRG.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		paramsRG.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
				RelativeLayout.TRUE);

		addView(adRadioGroup, paramsRG);

		// 设置TextView 标题
		adTextView = new TextView(context);
		adTextView.setId(ADTEXTVIEWID);
		adTextView.setBackgroundColor(getResources().getColor(
				R.color.ad_adtext_bg));
		adTextView.setTextColor(getResources().getColor(R.color.white));
		adTextView.setTextSize(getResources().getDimensionPixelOffset(
				R.dimen.dimen15));
		adTextView.setSingleLine(true);
		adTextView.setEllipsize(TruncateAt.END);

		LayoutParams paramsTV = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		paramsTV.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		paramsTV.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
				RelativeLayout.TRUE);
		paramsTV.addRule(RelativeLayout.ALIGN_LEFT, ADTEXTVIEWID);

		addView(adTextView, paramsTV);

	}

	public void setCarouselData(List<T> list, String appName) {
		if (list != null && list.size() > 0) {
			this.list = list;

			// 初始化位置
			index = 0;

			ImageView view = null;
			RadioButton radioButton = null;

			UtilsAsyncImageLoader imageLoader = new UtilsAsyncImageLoader(
					appName);

			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			for (int i = 0; i < list.size(); i++) {

				view = new ImageView(myContext);
				view.setLayoutParams(params);
				view.setScaleType(ScaleType.CENTER_CROP);
				imageLoader.loadImageFromUrl(list.get(i).getAdImage(), view);

				adViewFlipper.addView(view);

				radioButton = new RadioButton(myContext);
				radioButton
						.setButtonDrawable(R.drawable.ad_radiobutton_selector);

				adRadioGroup.addView(radioButton);

			}

			adTextView.setText(list.get(index).getAdTitle());
			((RadioButton) adRadioGroup.getChildAt(index)).setChecked(true);
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) event.getX();
			break;
		case MotionEvent.ACTION_UP:
			int dif = (int) (event.getX() - startX);

			if (dif > sensitive) { // 向右滑动

				index++;
				adViewFlipper.showNext();
				adTextView.setText(list.get(index).getAdTitle());

			} else if (dif < sensitive) { // 向左滑动

				index--;
				adViewFlipper.showPrevious();
				adTextView.setText(list.get(index).getAdTitle());

			}
			break;
		}

		return super.onTouchEvent(event);
	}
}
