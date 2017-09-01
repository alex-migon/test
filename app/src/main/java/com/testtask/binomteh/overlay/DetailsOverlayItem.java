package com.testtask.binomteh.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;

import com.testtask.binomteh.R;
import com.testtask.binomteh.data.User;

import org.osmdroid.views.overlay.OverlayItem;

public class DetailsOverlayItem extends OverlayItem implements TypeOfOverlayItem {

    private static final int SHADOW_RADIUS_DEFAULT = 2;

    public static final int DETAILS_TYPE_OVERLAY_ITEM = 1;

    private int mWidth;
    private int mHeight;
    private float mTextSize;
    private float mRoundingRadius;
    private int mXleftRectF;
    private int mYtopRectF;
    private int mXrightRectF;
    private int mYbottomRectF;

    private String mUsername = "";
    private String mStatusGps = "";
    private BitmapDrawable mDetailsOfOverlayBitmap;

    private Paint mPaintBackground;
    private Paint mPaintTextNameUser;
    private Paint mPaintTextStatusGps;

    private RectF mRectF;

    public DetailsOverlayItem(User user, Context context) {
        super(user.getId(), user.getName(), user.getGeoPoint());
        Bitmap overlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_tracker_75dp);

        mWidth = (int) (overlay.getWidth() * 1.8f) * 2;
        mHeight = (int) overlay.getHeight() * 2;
        mTextSize = mHeight * 0.2f;
        mUsername = user.getName();
        mRoundingRadius = (mHeight - mHeight * 0.75f) * 2;

        mXleftRectF = (int) (mHeight * 0.25f);
        mYtopRectF = (int) (mHeight - mHeight * 0.75f);
        mXrightRectF = (int) mWidth - SHADOW_RADIUS_DEFAULT;
        mYbottomRectF = (int) (mHeight - mHeight * 0.75f) * 4 - SHADOW_RADIUS_DEFAULT;

        mRectF = new RectF(mXleftRectF, mYtopRectF, mXrightRectF, mYbottomRectF);

        mStatusGps = user.getStatusGPS() + ", " + user.getTime();

        mPaintBackground = new Paint();
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setColor(Color.WHITE);
        mPaintBackground.setStyle(Paint.Style.FILL);
        mPaintBackground.setShadowLayer(SHADOW_RADIUS_DEFAULT, 0, 0, Color.GRAY);

        mPaintTextNameUser = new Paint();
        mPaintTextNameUser.setAntiAlias(true);
        mPaintTextNameUser.setColor(Color.BLACK);
        mPaintTextNameUser.setTextSize(mTextSize);
        mPaintTextNameUser.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mPaintTextNameUser.setStyle(Paint.Style.FILL);

        mPaintTextStatusGps = new Paint();
        mPaintTextStatusGps.setAntiAlias(true);
        mPaintTextStatusGps.setColor(Color.GRAY);
        mPaintTextStatusGps.setTextSize(mTextSize);
        mPaintTextStatusGps.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        mPaintTextStatusGps.setStyle(Paint.Style.FILL);

        mDetailsOfOverlayBitmap = drawDetails();
        this.setMarkerHotspot(org.osmdroid.views.overlay.OverlayItem.HotspotPlace.LEFT_CENTER);
        this.setMarker(mDetailsOfOverlayBitmap);
    }

    private BitmapDrawable drawDetails() {
        Bitmap output = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(mRectF, mRoundingRadius, mRoundingRadius, mPaintBackground);
        canvas.drawText(mUsername, mHeight / 2, mHeight * 0.55f, mPaintTextNameUser);
        canvas.drawText(mStatusGps, mHeight / 2, mHeight * 0.80f, mPaintTextStatusGps);
        return new BitmapDrawable(output);
    }

    public void updateStatusGps(User user) {
        mStatusGps = user.getStatusGPS() + ", " + user.getTime();
        mDetailsOfOverlayBitmap = drawDetails();
        this.setMarker(mDetailsOfOverlayBitmap);
    }

    @Override
    public int getType() {
        return DETAILS_TYPE_OVERLAY_ITEM;
    }
}
