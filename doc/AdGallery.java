package com.aim.propertyapp.view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.ImageView;

import com.aim.propertyapp.R;
import com.aim.propertyapp.entity.Advertising;
import com.aim.propertyapp.view.AdGalleryHelper.OnGallerySwitchListener;
import com.aim.util.UtilImageCache;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AdGallery extends Gallery implements
        android.widget.AdapterView.OnItemClickListener,
        android.widget.AdapterView.OnItemSelectedListener, OnTouchListener{
    private Context mContext;
    private int mSwitchTime;
    private boolean runflag = false;
    private Timer mTimer;
    private OnGallerySwitchListener mGallerySwitchListener;
    private ArrayList<Advertising> advertiseList;
    private OnClickIntentTo onClickIntentTo;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int position = getSelectedItemPosition();
            if (position >= (getCount() - 1)) {
                setSelection(getCount() / 2, true);
                onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
            } else {
                onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
            }
        }
    };

    public AdGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        mTimer = new Timer();
        UtilImageCache.init(context);
    }

    public AdGallery(Context context) {
        super(context);
        this.mContext = context;
        mTimer = new Timer();
        UtilImageCache.init(context);
        
     
    }

    public AdGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mTimer = new Timer();
        UtilImageCache.init(context);
    }

    class ViewHolder {
        ImageView imageview;
    }

    public void init(ArrayList<Advertising> advertiseList, int switchTime,
            OnGallerySwitchListener gallerySwitchListener) {
        this.mSwitchTime = switchTime;
        this.mGallerySwitchListener = gallerySwitchListener;

        this.advertiseList = advertiseList;
        setAdapter(new AdAdapter());

        this.setOnItemClickListener(this);
        this.setOnTouchListener(this);
        this.setOnItemSelectedListener(this);
        this.setSoundEffectsEnabled(false);

        // setSpacing(10);
        setSelection(getCount() / 2);
        setFocusableInTouchMode(true);
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

    @SuppressWarnings("deprecation")
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        int kEvent;
        if (isScrollingLeft(e1, e2)) {
            kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }

        onKeyDown(kEvent, null);
        return true;

    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > (e1.getX() + 50);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    public void startAutoScroll() {
        mTimer.schedule(new TimerTask() {

            public void run() {
                if (runflag) {
                    Message msg = new Message();
                    if (getSelectedItemPosition() < (getCount() - 1)) {
                        msg.what = getSelectedItemPosition() + 1;
                    } else {
                        msg.what = 0;
                    }
                    handler.sendMessage(msg);
                }
            }
        }, mSwitchTime, mSwitchTime);

    }

    public void setRunFlag(boolean flag) {
        runflag = flag;
    }

    public boolean isAutoScrolling() {
        if (mTimer == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_UP == event.getAction()
                || MotionEvent.ACTION_CANCEL == event.getAction()) {
            setRunFlag(true);
        } else {
            setRunFlag(false);
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
            long arg3) {

        mGallerySwitchListener.onGallerySwitch(position
                % (advertiseList.size()));

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
            long arg3) {
        if (onClickIntentTo != null) {
            onClickIntentTo.intentTo(position,
                    advertiseList.get(position % advertiseList.size()));
        }
    }

    public void setOnClickIntentTo(OnClickIntentTo onClickIntentTo) {
        this.onClickIntentTo = onClickIntentTo;
    }

    public interface OnClickIntentTo {
        public void intentTo(int position, Advertising adver);
    }
}
