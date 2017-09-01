package com.testtask.binomteh.fragments;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testtask.binomteh.R;

public class DetailsFragment extends Fragment {

    private LinearLayout mLinearLayout;

    private ImageView mImgIconUser;
    private TextView mTxtUsername;
    private TextView mTxtStatusGps;
    private TextView mTxtDate;
    private TextView mTxtTime;

    private Bitmap mBitmapIconUser;
    private String mStsUsername;
    private String mStrStatusGps;
    private String mStrDate;
    private String mStrTime;

    private Animation animationShowDate;
    private Animation animationHideDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        setRetainInstance(true);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLinearLayout = view.findViewById(R.id.linear_layout_for_animation);
        mImgIconUser = view.findViewById(R.id.username_icon);
        mTxtUsername = view.findViewById(R.id.username);
        mTxtStatusGps = view.findViewById(R.id.status_gps);
        mTxtDate = view.findViewById(R.id.date);
        mTxtTime = view.findViewById(R.id.time);

        fillInView();
        initAnimation();
    }

    private void initAnimation() {
        animationShowDate = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_show);
        animationHideDate = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_hide);
    }

    public void setIconUser(Bitmap mBitmapIconUser) {
        this.mBitmapIconUser = mBitmapIconUser;
    }

    public void setUsername(String mStsUsername) {
        this.mStsUsername = mStsUsername;
    }

    public void setStatusGps(String mStrStatusGps) {
        this.mStrStatusGps = mStrStatusGps;
    }

    public void setDate(String mStrDate) {
        this.mStrDate = mStrDate;
    }

    public void setTime(String mStrTime) {
        this.mStrTime = mStrTime;
    }

    public void animationForChangingData() {
        mLinearLayout.startAnimation(animationHideDate);
        fillInView();
        mLinearLayout.startAnimation(animationShowDate);
    }

    public void fillInView() {
        mImgIconUser.setImageBitmap(mBitmapIconUser);
        mTxtUsername.setText(mStsUsername);
        mTxtStatusGps.setText(mStrStatusGps);
        mTxtDate.setText(mStrDate);
        mTxtTime.setText(mStrTime);
    }
}
