package com.testtask.binomteh.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

import com.testtask.binomteh.R;
import com.testtask.binomteh.data.User;

import org.osmdroid.views.overlay.OverlayItem;

public class IconOverlayItem extends OverlayItem implements TypeOfOverlayItem {

    public static final int ICON_TYPE_OVERLAY_ITEM = 0;

    private BitmapDrawable mIconOverlay;
    private Bitmap overlay;

    private int mXleftRectF;
    private int mYtopRectF;
    private int mXrightRectF;
    private int mYbottomRectF;

    private float radiusClip;

    private Paint paint;
    private Rect mRect;

    public IconOverlayItem(User user, Context context) {
        super(user.getId(), user.getName(), null, user.getGeoPoint());
        paint = new Paint();
        paint.setAntiAlias(true);
        overlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_tracker_75dp);

        mXleftRectF = (int) (overlay.getWidth() * 0.35f);
        mYtopRectF = (int) (overlay.getHeight() * 0.19f);
        mXrightRectF = (int) (mXleftRectF + (overlay.getWidth() / 2)) * 2;
        mYbottomRectF = (int) (mYtopRectF + overlay.getHeight() / 1.70) * 2;

        radiusClip = (overlay.getWidth() / 3) * 2;

        mRect = new Rect(mXleftRectF, mYtopRectF, mXrightRectF, mYbottomRectF);

        mIconOverlay = drawIcon(user.getPhoto(), Bitmap.createScaledBitmap(overlay, overlay.getWidth() * 2,
                overlay.getHeight() * 2, false));

        this.setMarker(mIconOverlay);
    }

    private BitmapDrawable drawIcon(Bitmap photo, Bitmap overlay) {
        Bitmap output = Bitmap.createBitmap(overlay.getWidth(), overlay.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Path pathCircle = new Path();
        pathCircle.addCircle(overlay.getWidth() / 2, overlay.getWidth() / 2.35f, radiusClip, Path.Direction.CW);

        canvas.drawBitmap(overlay, 0, 0, paint);
        canvas.clipPath(pathCircle);
        canvas.drawBitmap(photo, null, mRect, paint);

        return new BitmapDrawable(output);
    }

    @Override
    public int getType() {
        return ICON_TYPE_OVERLAY_ITEM;
    }
}
