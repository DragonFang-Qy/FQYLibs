package com.aim.propertyapp.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.aim.propertyapp.R;
import com.aim.propertyapp.entity.Advertising;

public class AdGalleryHelper {

    private AdGallery mAdGallery;
    private TextView mPicTitle;
    private RadioGroup mRadioGroup;

    private Context mContext;
    private LayoutInflater mInflater;

    RelativeLayout galleryLayout;

    public AdGalleryHelper(Context context, final ArrayList<Advertising> advertiseList, int switchTime) {

        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        galleryLayout = (RelativeLayout) mInflater.inflate(R.layout.adgallery_hellper, null);
        mRadioGroup = (RadioGroup) galleryLayout.findViewById(R.id.home_pop_gallery_mark);

        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.agallery_icon_dot_choose);
        LayoutParams params = new LayoutParams(dpToPx(mContext, b.getWidth()), dpToPx(mContext, b.getHeight()));
        for (int i = 0; i < advertiseList.size(); i++) {
            RadioButton _rb = new RadioButton(mContext);
            _rb.setId(0x1234 + i);
            _rb.setButtonDrawable(mContext.getResources().getDrawable(R.drawable.gallery_selector));
            mRadioGroup.addView(_rb, params);
        }

        mPicTitle = (TextView) galleryLayout.findViewById(R.id.news_gallery_text);
        mAdGallery = (AdGallery) galleryLayout.findViewById(R.id.gallerypop);
        mAdGallery.init(advertiseList, switchTime, new OnGallerySwitchListener() {

            @Override
            public void onGallerySwitch(int position) {
                if (mRadioGroup != null) {
                    mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
                }
                if (mPicTitle != null) {
                    mPicTitle.setText(advertiseList.get(position).getId() + "");
                }
            }
        });
    }

    public RelativeLayout getLayout() {
        return galleryLayout;
    }

    public void startAutoSwitch() {
        mAdGallery.setRunFlag(true);
        mAdGallery.startAutoScroll();
    }

    public void stopAutoSwitch() {
        mAdGallery.setRunFlag(true);
    }

    interface OnGallerySwitchListener {
        public void onGallerySwitch(int position);
    }

    public static int dpToPx(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int pxToDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
