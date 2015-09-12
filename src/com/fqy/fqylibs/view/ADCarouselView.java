package com.fqy.fqylibs.view;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.fqy.fqylibs.R;
import com.fqy.fqylibs.model.ADModel;
import com.fqy.fqylibs.utils.UtilsAsyncImageLoader;
import com.lidroid.xutils.BitmapUtils;

/**
 * @Title: ADCarouselView.java
 * @Package com.fqy.fqylibs.view
 * @author: Fang Qingyou
 * @date 2015年9月7日下午2:22:02
 * @version V1.0
 */
public class ADCarouselView<T extends ADModel> extends RelativeLayout {

	public static final int ADVIEWFLIPPERID = 10;
	public static final int ADINDICATELLID = 20;
	public static final int ADTEXTVIEWID = 30;
	public static final int ADLINEARLAYOUTID = 40;

	private ViewFlipper adViewFlipper;// 显示广告图
	private LinearLayout adIndicateLL;// 广告位置指示器
	private TextView adTextView;// 广告描述（或标题）
	private LinearLayout adLinearLayout;// 底部线性布局（容器）,将adRadioGroup和adTextView
										// 放到里面便于背景的管理等

	private Context myContext; // 上下文
	private int startX;// 开始滑动的位置
	private int index;// adViewFlipper的当前位置
	private int count;// adViewFlipper总共有多少个
	private int sensitive;// 灵敏度
	private int timeInterval;// 图片切换时间间隔，

	// 底部布局的padding值
	private int bottomPaddingTop;
	private int bottomPaddingBottom;
	private int bottomPaddingLeft;
	private int bottomPaddingRight;

	private List<T> list; // 存放数据
	private String appName;// 文件夹名称，存放缓存下来的图片
	private boolean infiniteLoop; // 循环标记
	private boolean timerFlag = infiniteLoop; // TimerTask 控制标记
	private int adIndicateLLResIdNoSelect;// 未选中图片资源id
	private int adIndicateLLResIdSelect;// 选中图片资源id
	private Timer timer = new Timer();
	private TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			if (timerFlag) {
				Message message = new Message();
				message.obj = "setShowNext";
				handler.sendMessage(message);
			}
		}
	};

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if ("setShowNext".equals(msg.obj)) {
				setShowNext();
			}
		}
	};

	private OnADCarouselViewListerner listerner;

	public ADCarouselView(Context context) {
		this(context, null, 0);
	}

	public ADCarouselView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ADCarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		myContext = context;
		sensitive = getResources().getDimensionPixelOffset(R.dimen.dimen20);
		createView(context);

		// 通过ttvTa 设置自定义属性
		TypedArray adcvTa = context.obtainStyledAttributes(attrs,
				R.styleable.ADCarouselView);

		setBottomBackground(adcvTa
				.getResourceId(R.styleable.ADCarouselView_Background_adll,
						R.color.transparent));

		setADTextViewSize(adcvTa.getResourceId(
				R.styleable.ADCarouselView_adText_Size, R.dimen.dimen10));

		setADTextViewColor(adcvTa.getResourceId(
				R.styleable.ADCarouselView_adText_Color, R.color.white));

		setBottomPadding(adcvTa.getResourceId(
				R.styleable.ADCarouselView_Bottom_Padding, R.dimen.dimen10));

		setBottomLLPaddingTop(adcvTa.getResourceId(
				R.styleable.ADCarouselView_Bottom_PaddingTop, R.dimen.dimen10),
				false);

		setBottomLLPaddingBottom(adcvTa.getResourceId(
				R.styleable.ADCarouselView_Bottom_PaddingBottom,
				R.dimen.dimen10), false);

		setBottomLLPaddingLeft(
				adcvTa.getResourceId(
						R.styleable.ADCarouselView_Bottom_PaddingLeft,
						R.dimen.dimen10), false);

		setBottomLLPaddingRight(
				adcvTa.getResourceId(
						R.styleable.ADCarouselView_Bottom_PaddingRight,
						R.dimen.dimen10), false);

		adIndicateLLResIdNoSelect = adcvTa.getResourceId(
				R.styleable.ADCarouselView_Bottom_adIndicateLLRes_NoSelect,
				adIndicateLLResIdSelect);

		adIndicateLLResIdSelect = adcvTa.getResourceId(
				R.styleable.ADCarouselView_Bottom_adIndicateLLRes_Select,
				R.drawable.ad_redio_bg_select);

		// 资源回收
		adcvTa.recycle();
	}

	/** 初始化控件 */
	private void createView(Context context) {

		// 设置 ViewFlipper的显示
		adViewFlipper = new ViewFlipper(context);
		adViewFlipper.setId(ADVIEWFLIPPERID);
		adViewFlipper.setVisibility(View.INVISIBLE);
		// adViewFlipper.setOnClickListener(this);

		LayoutParams paramsVF = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		paramsVF.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

		addView(adViewFlipper, paramsVF);

		// 底部线性布局
		adLinearLayout = new LinearLayout(myContext);
		adLinearLayout.setId(ADLINEARLAYOUTID);
		adLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
		adLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

		// 设置线性布局在相对布局中的位置
		LayoutParams paramsLL = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		paramsLL.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
				RelativeLayout.TRUE);
		addView(adLinearLayout, paramsLL);

		// 设置TextView 标题
		adTextView = new TextView(context);
		adTextView.setId(ADTEXTVIEWID);
		adTextView.setPadding(10, 10, 0, 10);
		adTextView.setTextColor(getResources().getColor(R.color.white));
		adTextView.setTextSize(getResources().getDimensionPixelOffset(
				R.dimen.dimen10));
		adTextView.setSingleLine(true);
		adTextView.setEllipsize(TruncateAt.END);

		// 设置adTextView 在线性布局中的位置
		LinearLayout.LayoutParams paramsTV = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		paramsTV.weight = 1;
		adLinearLayout.addView(adTextView, paramsTV);

		// 设置RadioGroup（位置指示器）
		adIndicateLL = new RadioGroup(context);
		adIndicateLL.setId(ADINDICATELLID);
		adIndicateLL.setPadding(0, 10, 10, 10);
		adIndicateLL.setGravity(Gravity.CENTER);
		adIndicateLL.setFocusable(false);
		adIndicateLL.setFocusableInTouchMode(false);
		adIndicateLL.setOrientation(LinearLayout.HORIZONTAL);

		// 设置adRadioGroup 在线性布局中的位置
		LayoutParams paramsRG = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		adLinearLayout.addView(adIndicateLL, paramsRG);

	}

	/**
	 * 图片时间间隔
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午3:58:49
	 * @return
	 */
	public int getTimeInterval() {
		return timeInterval;
	}

	/**
	 * 得到总的图片个数
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午3:59:24
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 得到图片切换灵敏度
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午3:59:50
	 * @return
	 */
	public int getSensitive() {
		return sensitive;
	}

	/**
	 * 得到当前图片位置，从0开始
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午4:00:23
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置底部布局背景颜色
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午3:43:36
	 * @param resId
	 */
	public void setBottomBackground(int resId) {
		adTextView.setBackgroundResource(resId);
		adIndicateLL.setBackgroundResource(resId);
	}

	/**
	 * 设置底部布局的paddingTop值
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午6:01:31
	 * @param value
	 */
	public void setBottomPaddingTop(int value) {
		this.bottomPaddingTop = value;
		setBottomLLPaddingTop(bottomPaddingTop, true);
	}

	/**
	 * 设置底部布局的paddingBottom值
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午6:01:31
	 * @param value
	 */
	public void setBottomPaddingBottom(int value) {
		this.bottomPaddingBottom = value;
		setBottomLLPaddingBottom(bottomPaddingBottom, true);
	}

	/**
	 * 设置底部布局的paddingLeft值
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午6:01:31
	 * @param value
	 */
	public void setBottomPaddingLeft(int value) {
		this.bottomPaddingLeft = value;
		setBottomLLPaddingLeft(bottomPaddingLeft, true);
	}

	/**
	 * 设置底部布局的paddingRight值
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午6:01:31
	 * @param value
	 */
	public void setBottomPaddingRight(int value) {
		this.bottomPaddingRight = value;
		setBottomLLPaddingRight(bottomPaddingRight, true);

	}

	/**
	 * 设置文本字体大小
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午3:43:52
	 * @param resId
	 */
	public void setADTextViewSize(int resId) {
		adTextView.setTextSize(getResources().getDimension(resId));
	}

	/**
	 * 设置文本字体大小
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午3:43:52
	 * @param resId
	 */
	public void setADTextViewColor(int resId) {
		adTextView.setTextColor(getResources().getColor(resId));
	}

	/**
	 * 设置底部布局的 PaddingTop值
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午6:04:56
	 * @param resourceId
	 */
	private void setBottomLLPaddingTop(int resourceId, boolean flag) {

		if (flag) {
			bottomPaddingTop = resourceId;
		} else {
			bottomPaddingTop = getResources().getDimensionPixelOffset(
					resourceId);
		}
		adTextView.setPadding(bottomPaddingLeft, bottomPaddingTop,
				bottomPaddingRight, bottomPaddingBottom);
		adIndicateLL.setPadding(bottomPaddingLeft, bottomPaddingTop,
				bottomPaddingRight, bottomPaddingBottom);

	}

	/**
	 * 设置底部布局的 PaddingBottom值
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午6:04:56
	 * @param resourceId
	 */
	private void setBottomLLPaddingBottom(int resourceId, boolean flag) {

		if (flag) {
			bottomPaddingBottom = resourceId;
		} else {
			bottomPaddingBottom = getResources().getDimensionPixelOffset(
					resourceId);
		}
		adTextView.setPadding(bottomPaddingLeft, bottomPaddingTop,
				bottomPaddingRight, bottomPaddingBottom);
		adIndicateLL.setPadding(bottomPaddingLeft, bottomPaddingTop,
				bottomPaddingRight, bottomPaddingBottom);

	}

	/**
	 * 设置底部布局的 PaddingLeft值
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午6:04:56
	 * @param resourceId
	 */
	private void setBottomLLPaddingLeft(int resourceId, boolean flag) {

		if (flag) {
			bottomPaddingLeft = resourceId;
		} else {
			bottomPaddingLeft = getResources().getDimensionPixelOffset(
					resourceId);
		}
		adTextView.setPadding(bottomPaddingLeft, bottomPaddingTop,
				bottomPaddingRight, bottomPaddingBottom);
		adIndicateLL.setPadding(bottomPaddingLeft, bottomPaddingTop,
				bottomPaddingRight, bottomPaddingBottom);

	}

	/**
	 * 设置底部布局的 PaddingRight值
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午6:04:56
	 * @param resourceId
	 */
	private void setBottomLLPaddingRight(int resourceId, boolean flag) {

		if (flag) {
			bottomPaddingRight = resourceId;
		} else {
			bottomPaddingRight = getResources().getDimensionPixelOffset(
					resourceId);
		}
		adTextView.setPadding(bottomPaddingLeft, bottomPaddingTop,
				bottomPaddingRight, bottomPaddingBottom);
		adIndicateLL.setPadding(bottomPaddingLeft, bottomPaddingTop,
				bottomPaddingRight, bottomPaddingBottom);

	}

	/**
	 * 设置图片切换时间间隔
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午4:00:55
	 * @param timeInterval
	 */
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	/**
	 * 设置图片切换灵敏度
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午4:01:26
	 * @param sensitive
	 */
	public void setSensitive(int sensitive) {
		this.sensitive = sensitive;
	}

	/**
	 * 设置无限循环
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日上午11:42:44
	 * @param timeInterval
	 *            时间间隔，单位：秒
	 */
	public void startInfiniteLoop(int timeInterval) throws Throwable {

		infiniteLoop = true;
		timerFlag = infiniteLoop;
		this.timeInterval = timeInterval;
		timer.schedule(timerTask, timeInterval * 1000, timeInterval * 1000);
	}

	/**
	 * 停止无限循环
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午4:01:49
	 */
	public void stopInfiniteLoop() {
		infiniteLoop = false;
		if (timer != null) {
			timerFlag = infiniteLoop;
			timer.cancel();
		}
	}

	/**
	 * 设置BottomPadding
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月12日下午1:57:34
	 * @param resourceId
	 */
	public void setBottomPadding(int resourceId) {

		bottomPaddingRight = bottomPaddingLeft = bottomPaddingBottom = bottomPaddingTop = getResources()
				.getDimensionPixelOffset(resourceId);

		adTextView.setPadding(bottomPaddingLeft, bottomPaddingTop, 0,
				bottomPaddingBottom);

		adIndicateLL.setPadding(0, bottomPaddingTop, bottomPaddingRight,
				bottomPaddingBottom);
	}

	/**
	 * 设置轮播的数据
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月11日下午4:02:39
	 * @param list
	 * @param appName
	 */
	@SuppressLint("NewApi")
	public void setCarouselData(List<T> list, String appName) {

		this.list = list;
		this.appName = appName;

		// 初始化位置
		index = 0;
		count = list.size();

		// handler.sendMessage(message);

		if (list != null && list.size() > 0) {

			ImageView view = null;
			ImageView imageView = null;

			UtilsAsyncImageLoader imageLoader = new UtilsAsyncImageLoader(
					appName);

			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			for (int i = 0; i < list.size(); i++) {

				view = new ImageView(myContext);
				view.setLayoutParams(params);
				view.setScaleType(ScaleType.CENTER_CROP);
				imageLoader.loadImageFromUrl(this.list.get(i).getAdImage(),
						view);
				adViewFlipper.addView(view);

				imageView = new ImageView(myContext);

				if (adIndicateLLResIdNoSelect != 0) {

					imageView.setBackgroundResource(adIndicateLLResIdNoSelect);
				} else {

					imageView.setBackgroundResource(adIndicateLLResIdSelect);
				}

				adIndicateLL.addView(imageView);

			}
			adViewFlipper.setVisibility(View.VISIBLE);
			adTextView.setText(list.get(index).getAdTitle());
			adIndicateLL.getChildAt(index).setBackgroundResource(
					R.drawable.ad_redio_bg_select);
		}

	}

	/**
	 * 显示下一个
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月12日下午1:57:14
	 */
	public void setShowNext() {

		if (infiniteLoop && index >= count - 1) {

			// 无限循环状态下，到达最后一个，再次执行setShowNext 需要回到第一个
			adIndicateLL.getChildAt(index).setBackgroundResource(
					adIndicateLLResIdNoSelect);
			index = 0;

		} else if (index >= count - 1) {

			// 不循环状态下执行到最后一个，再次执行setShowNext 需要回到最后一个，不然会有数组下标越界异常
			adIndicateLL.getChildAt(index).setBackgroundResource(
					adIndicateLLResIdNoSelect);
			index = count - 1;

		} else {

			// 不论循环、不循环中间位置执行setShowNext ，都需要跳到下一个
			adIndicateLL.getChildAt(index).setBackgroundResource(
					adIndicateLLResIdNoSelect);
			index++;
		}

		adViewFlipper.showNext();
		adTextView.setText(list.get(index).getAdTitle());
		adIndicateLL.getChildAt(index).setBackgroundResource(
				R.drawable.ad_redio_bg_select);
	}

	/**
	 * 显示上一个
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月12日下午1:56:52
	 */
	public void setShowPrevious() {
		if (index <= 0) {
			adIndicateLL.getChildAt(index).setBackgroundResource(
					adIndicateLLResIdNoSelect);
			index = 0;
		} else {
			adIndicateLL.getChildAt(index).setBackgroundResource(
					adIndicateLLResIdNoSelect);
			index--;
		}

		adViewFlipper.showPrevious();
		adTextView.setText(list.get(index).getAdTitle());
		adIndicateLL.getChildAt(index).setBackgroundResource(
				R.drawable.ad_redio_bg_select);
	}

	/**
	 * 滑动事件
	 */
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			timerFlag = false;
			startX = (int) event.getX();
			break;
		case MotionEvent.ACTION_UP:
			timerFlag = infiniteLoop;
			int dif = (int) (event.getX() - startX);
			Log.e("test", dif + "== dif  ," + sensitive + " ==sensitive");
			if (dif < -sensitive) {

				// 向右滑动
				setShowNext();

			} else if (dif > sensitive) {

				// 向左滑动
				setShowPrevious();

			} else if (dif == 0) {
				if (listerner != null) {
					listerner.onADCarouselViewClickListener(index);
				}
			}

			break;
		}
		return true;
	}

	/**
	 * 点击监听接口
	 * 
	 * @Title: ADCarouselView.java
	 * @Package com.fqy.fqylibs.view
	 * @Description: TODO 欢迎页
	 * @author: Fang Qingyou
	 * @date 2015年9月12日下午1:55:34
	 * @version V1.0
	 */
	public interface OnADCarouselViewListerner {

		public void onADCarouselViewClickListener(int index);
	}

	/**
	 * 设置点击监听
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年9月12日下午1:55:17
	 * @param listerner
	 */
	public void setOnADCarouselViewListerner(OnADCarouselViewListerner listerner) {
		this.listerner = listerner;
	}

}
