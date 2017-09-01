package com.testtask.binomteh.overlay;


import android.content.Context;

import com.testtask.binomteh.data.User;

import org.osmdroid.views.overlay.OverlayItem;

public class OverlayItemFactory {

    public OverlayItem getOverlayItem(User user, int type, Context context) {
        switch (type) {
            case IconOverlayItem.ICON_TYPE_OVERLAY_ITEM:
                return new IconOverlayItem(user, context);
            case DetailsOverlayItem.DETAILS_TYPE_OVERLAY_ITEM:
                return new DetailsOverlayItem(user, context);
            default:
                return null;
        }
    }

}
